package org.dawnoftimebuilder.block.precolumbian;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.FluidState;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.BlockRenderLayer;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.block.templates.WaterloggedBlock;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

import java.util.Random;

public class PlasteredStoneCressetBlock extends WaterloggedBlock {

    private static final IntegerProperty HEAT = DoTBBlockStateProperties.HEAT_0_3;
    private static final BooleanProperty BURNING = DoTBBlockStateProperties.BURNING;
    private static final VoxelShape VS = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 14.0D, 13.0D);

    public PlasteredStoneCressetBlock(Material materialIn, float hardness, float resistance, SoundType soundType) {
        super(Properties.of(materialIn).strength(hardness, resistance).sound(soundType));
        this.registerDefaultState(this.defaultBlockState().setValue(BURNING, false).setValue(HEAT, 0).setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VS;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BURNING, HEAT);
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit){
        if (state.getValue(BURNING)) {
            worldIn.setBlock(pos, state.setValue(BURNING, false), 10);
            worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
            return true;
        } else {
            if(state.getValue(WATERLOGGED)) return false;

            if(DoTBBlockUtils.useLighter(worldIn, pos, player, handIn)){
                worldIn.setBlock(pos, state.setValue(BURNING, true), 10);
            }
        }
        return false;
    }

    @Override
    public void onProjectileCollision(World worldIn, BlockState state, BlockRayTraceResult hit, Entity projectile) {
        if (!worldIn.isClientSide && projectile instanceof AbstractArrowEntity) {
            AbstractArrowEntity abstractarrowentity = (AbstractArrowEntity)projectile;
            if (abstractarrowentity.isBurning() && !state.getValue(BURNING) && !state.getValue(WATERLOGGED)) {
                BlockPos pos = hit.getPos();
                worldIn.setBlock(pos, state.setValue(BURNING, true), 10);
                worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
        }
    }

    @Override
    public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
        if (!state.getValue(WATERLOGGED) && fluidStateIn.getFluid() == Fluids.WATER) {
            if (state.getValue(BURNING) || state.getValue(HEAT) > 0) {
                worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            worldIn.setBlock(pos, state.setValue(WATERLOGGED, true).setValue(BURNING, false).setValue(HEAT, 0), 10);
            worldIn.getLiquidTicks().scheduleTick(pos, fluidStateIn.getFluid(), fluidStateIn.getFluid().getTickRate(worldIn));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return (state.getValue(BURNING)) ? state.getValue(HEAT) < 3 : state.getValue(HEAT) > 0;
    }

    @Override
    public void tick(BlockState state, World worldIn, BlockPos pos, Random rand) {
        super.tick(state, worldIn, pos, rand);
        int currentHeat = state.getValue(HEAT);
        if (state.getValue(BURNING)) {
            if (rand.nextInt(10) == 0) {
                if(currentHeat < 3) worldIn.setBlock(pos, state.setValue(HEAT, currentHeat + 1), 2);
            }
        }else if(currentHeat > 0){
            if(rand.nextInt(10) == 0) {
                worldIn.setBlock(pos, state.setValue(HEAT, currentHeat - 1), 2);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        int currentHeat = stateIn.getValue(HEAT);
        if (stateIn.getValue(BURNING)) {
            if (rand.nextInt(10) == 0) {
                worldIn.playSound((float)pos.getX() + 0.5F, (float)pos.getY() + 0.5F, (float)pos.getZ() + 0.5F, SoundEvents.BLOCK_CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, 0.5F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.6F, false);
            }
            if (rand.nextInt(10) == 0) {
                for(int i = 0; i < rand.nextInt(1) + 1; ++i) {
                    worldIn.addParticle(ParticleTypes.LAVA, (float)pos.getX() + 0.5F, (float)pos.getY() + 0.5F, (float)pos.getZ() + 0.5F, rand.nextFloat() / 4.0F, 2.5E-5D, rand.nextFloat() / 4.0F);
                }
            }
            if(rand.nextInt(2) == 0) {
                worldIn.addOptionalParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, (double)pos.getX() + 0.5D + rand.nextDouble() / 4.0D * (double)(rand.nextBoolean() ? 1 : -1), (double)pos.getY() + 0.8D, (double)pos.getZ() + 0.5D + rand.nextDouble() / 4.0D * (double)(rand.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
            }
        } else {
            if(currentHeat > 0){
                if(rand.nextInt((4 - currentHeat) * 2) == 0) {
                    worldIn.addOptionalParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,  (double)pos.getX() + 0.5D + rand.nextDouble() / 4.0D * (double)(rand.nextBoolean() ? 1 : -1), (double)pos.getY() + 0.8D, (double)pos.getZ() + 0.5D + rand.nextDouble() / 4.0D * (double)(rand.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
                }
            }
        }
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public int getLightValue(BlockState state) {
        if(state.getValue(WATERLOGGED)) return 0;
        if(state.getValue(BURNING)) return 15;
        return state.getValue(HEAT) * 2;
    }
}