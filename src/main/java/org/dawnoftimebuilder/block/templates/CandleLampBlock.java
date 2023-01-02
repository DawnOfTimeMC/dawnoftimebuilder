package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.block.IBlockSpecialDisplay;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

public abstract class CandleLampBlock extends WaterloggedBlock implements IBlockSpecialDisplay {

    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public CandleLampBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LIT);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateLitCandle(BlockState stateIn, World worldIn, BlockPos pos, double x, double y, double z){
        if (stateIn.getValue(LIT)) {
            double d0 = (double)pos.getX() + x;
            double d1 = (double)pos.getY() + y;
            double d2 = (double)pos.getZ() + z;
            worldIn.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            worldIn.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.01D, 0.0D);
        }
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit){
        if (state.getValue(LIT)) {
            worldIn.setBlock(pos, state.setValue(LIT, false), 10);
            worldIn.playSound(null, pos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            return ActionResultType.SUCCESS;
        } else {
            if(state.getValue(WATERLOGGED)) return ActionResultType.PASS;
            if (DoTBBlockUtils.useLighter(worldIn, pos, player, handIn)) {
                worldIn.setBlock(pos, state.setValue(LIT, true), 10);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }

    @Override
    public void onProjectileHit(World worldIn, BlockState state, BlockRayTraceResult hit, ProjectileEntity projectile) {
        if (!worldIn.isClientSide && projectile instanceof AbstractArrowEntity) {
            AbstractArrowEntity abstractarrowentity = (AbstractArrowEntity)projectile;
            if (abstractarrowentity.isOnFire() && !state.getValue(LIT) && !state.getValue(WATERLOGGED)) {
                BlockPos pos = hit.getBlockPos();
                worldIn.setBlock(pos, state.setValue(LIT, true), 10);
                worldIn.playSound(null, pos, SoundEvents.FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
        }
    }

    @Override
    public boolean placeLiquid(IWorld world, BlockPos pos, BlockState state, FluidState fluid) {
        if (!state.getValue(WATERLOGGED) && fluid.getType() == Fluids.WATER) {
            if (state.getValue(LIT)) {
                world.playSound(null, pos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            world.setBlock(pos, state.setValue(WATERLOGGED, true).setValue(LIT, false), 10);
            world.getLiquidTicks().scheduleTick(pos, fluid.getType(), fluid.getType().getTickDelay(world));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean emitsLight() {
        return true;
    }
}
