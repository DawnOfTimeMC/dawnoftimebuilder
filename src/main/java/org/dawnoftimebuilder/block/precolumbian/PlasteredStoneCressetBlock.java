package org.dawnoftimebuilder.block.precolumbian;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.block.templates.WaterloggedBlock;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

import java.util.Random;

public class PlasteredStoneCressetBlock extends WaterloggedBlock {

    private static final IntegerProperty HEAT = DoTBBlockStateProperties.HEAT_0_4;
    private static final BooleanProperty LIT = BlockStateProperties.LIT;
    private static final VoxelShape VS = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 14.0D, 13.0D);

    public PlasteredStoneCressetBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(LIT, false).setValue(HEAT, 0).setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VS;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LIT, HEAT);
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit){
        if (state.getValue(LIT)) {
            worldIn.setBlock(pos, state.setValue(HEAT, 3), 10);
            worldIn.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
            return ActionResultType.SUCCESS;
        } else {
            if(state.getValue(WATERLOGGED)) return ActionResultType.PASS;

            if(DoTBBlockUtils.useLighter(worldIn, pos, player, handIn)){
                worldIn.setBlock(pos, state.setValue(LIT, true).setValue(HEAT, 4), 10);
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
                worldIn.setBlock(pos, state.setValue(LIT, true).setValue(HEAT, 4), 10);
                worldIn.playSound(null, pos, SoundEvents.FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
        }
    }

    @Override
    public boolean placeLiquid(IWorld world, BlockPos pos, BlockState state, FluidState fluid) {
        if (!state.getValue(WATERLOGGED) && fluid.getType() == Fluids.WATER) {
            if (state.getValue(LIT) || state.getValue(HEAT) > 0) {
                world.playSound(null, pos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            world.setBlock(pos, state.setValue(WATERLOGGED, true).setValue(LIT, false).setValue(HEAT, 0), 10);
            world.getLiquidTicks().scheduleTick(pos, fluid.getType(), fluid.getType().getTickDelay(world));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(LIT) && state.getValue(HEAT) < 4;
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        super.tick(state, worldIn, pos, rand);
        int heat = state.getValue(HEAT);
        if(state.getValue(LIT) && heat < 4){
            if(rand.nextInt(10) == 0) {
                heat = Math.max(heat - 1, 0);
                worldIn.setBlock(pos, state.setValue(HEAT, heat).setValue(LIT, heat > 0), 2);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        int currentHeat = stateIn.getValue(HEAT);
        if (currentHeat == 4) {
            if (rand.nextInt(10) == 0) {
                worldIn.playLocalSound((float)pos.getX() + 0.5F, (float)pos.getY() + 0.5F, (float)pos.getZ() + 0.5F, SoundEvents.CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, 0.5F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.6F, false);
            }
            if (rand.nextInt(10) == 0) {
                for(int i = 0; i < rand.nextInt(1) + 1; ++i) {
                    worldIn.addParticle(ParticleTypes.LAVA, (float)pos.getX() + 0.5F, (float)pos.getY() + 0.5F, (float)pos.getZ() + 0.5F, rand.nextFloat() / 4.0F, 2.5E-5D, rand.nextFloat() / 4.0F);
                }
            }
            if(rand.nextInt(2) == 0) {
                worldIn.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, (double)pos.getX() + 0.5D + rand.nextDouble() / 4.0D * (double)(rand.nextBoolean() ? 1 : -1), (double)pos.getY() + 0.8D, (double)pos.getZ() + 0.5D + rand.nextDouble() / 4.0D * (double)(rand.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
            }
        }else if(currentHeat > 0){
            if(rand.nextInt((4 - currentHeat) * 2) == 0) {
                worldIn.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,  (double)pos.getX() + 0.5D + rand.nextDouble() / 4.0D * (double)(rand.nextBoolean() ? 1 : -1), (double)pos.getY() + 0.8D, (double)pos.getZ() + 0.5D + rand.nextDouble() / 4.0D * (double)(rand.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
            }
        }
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        if(state.getValue(WATERLOGGED) || !state.getValue(LIT)) return 0;
        return (state.getValue(HEAT) == 4) ? 15 : state.getValue(HEAT) * 2;
    }
}