package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
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
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED,false).setValue(LIT, false));
    }

    public CandleLampBlock(Material materialIn, float hardness, float resistance, SoundType soundType) {
        this(Properties.of(materialIn).strength(hardness, resistance).sound(soundType));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LIT);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateLitCandle(BlockState stateIn, World worldIn, BlockPos pos, double x, double y, double z){
        if (stateIn.get(LIT)) {
            double d0 = (double)pos.getX() + x;
            double d1 = (double)pos.getY() + y;
            double d2 = (double)pos.getZ() + z;
            worldIn.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            worldIn.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.01D, 0.0D);
        }
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit){
        if (state.get(LIT)) {
            worldIn.setBlock(pos, state.setValue(LIT, false), 10);
            worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
            return true;
        } else {
            if(state.get(WATERLOGGED)) return false;
            if (DoTBBlockUtils.useLighter(worldIn, pos, player, handIn)) {
                worldIn.setBlock(pos, state.setValue(LIT, true), 10);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onProjectileCollision(World worldIn, BlockState state, BlockRayTraceResult hit, Entity projectile) {
        if (!worldIn.isClientSide && projectile instanceof AbstractArrowEntity) {
            AbstractArrowEntity abstractarrowentity = (AbstractArrowEntity)projectile;
            if (abstractarrowentity.isBurning() && !state.get(LIT) && !state.get(WATERLOGGED)) {
                BlockPos pos = hit.getPos();
                worldIn.setBlock(pos, state.setValue(LIT, true), 10);
                worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
        }
    }

    @Override
    public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, IFluidState fluidStateIn) {
        if (!state.get(WATERLOGGED) && fluidStateIn.getFluid() == Fluids.WATER) {
            if (state.get(LIT)) {
                worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            worldIn.setBlock(pos, state.setValue(WATERLOGGED, true).setValue(LIT, false), 10);
            worldIn.getLiquidTicks().scheduleTick(pos, fluidStateIn.getFluid(), fluidStateIn.getFluid().getTickRate(worldIn));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getLightValue(BlockState state) {
        return state.get(LIT) ? this.getLitLightValue() : 0;
    }

    public abstract int getLitLightValue();

    @Override
    public boolean emitsLight() {
        return true;
    }
}
