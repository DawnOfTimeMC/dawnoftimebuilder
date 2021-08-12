package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.block.IBlockSpecialDisplay;
import org.dawnoftimebuilder.utils.DoTBBlockUtils;

import javax.annotation.Nonnull;
import java.util.Random;

public class CandlestickBlock extends WaterloggedBlock implements IBlockSpecialDisplay {

    private static final VoxelShape VS_BOTTOM = makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 15.0D, 11.0D);
    private static final VoxelShape[] VS_SIDE = DoTBBlockUtils.GenerateHorizontalShapes(new VoxelShape[]{makeCuboidShape(2.0D, 0.0D, 0.0D, 14.0D, 15.0D, 14.0D)});
    public static final DirectionProperty FACING = BlockStateProperties.FACING_EXCEPT_UP;
    private static final BooleanProperty LIT = BlockStateProperties.LIT;

    public CandlestickBlock(Material materialIn, float hardness, float resistance) {
        super(materialIn, hardness, resistance);
        this.setDefaultState(this.getStateContainer().getBaseState().with(WATERLOGGED,false).with(FACING, Direction.DOWN).with(LIT, false));
    }

    @Nonnull
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction facing = context.getFace();
        return super.getStateForPlacement(context).with(FACING, facing == Direction.UP ? Direction.DOWN : facing);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(FACING, LIT);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit){
        if (state.get(LIT)) {
            worldIn.setBlockState(pos, state.with(LIT, false), 10);
            worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
            return true;
        } else {
            if(state.get(WATERLOGGED)) return false;
            if (DoTBBlockUtils.lightFireBlock(worldIn, pos, player, handIn)) {
                worldIn.setBlockState(pos, state.with(LIT, true), 10);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onProjectileCollision(World worldIn, BlockState state, BlockRayTraceResult hit, Entity projectile) {
        if (!worldIn.isRemote && projectile instanceof AbstractArrowEntity) {
            AbstractArrowEntity abstractarrowentity = (AbstractArrowEntity)projectile;
            if (abstractarrowentity.isBurning() && !state.get(LIT) && !state.get(WATERLOGGED)) {
                BlockPos pos = hit.getPos();
                worldIn.setBlockState(pos, state.with(LIT, true), 10);
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
            worldIn.setBlockState(pos, state.with(WATERLOGGED, true).with(LIT, false), 10);
            worldIn.getPendingFluidTicks().scheduleTick(pos, fluidStateIn.getFluid(), fluidStateIn.getFluid().getTickRate(worldIn));
            return true;
        } else {
            return false;
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (stateIn.get(LIT)) {
            double d0 = (double)pos.getX() + 0.5D;
            double d1 = (double)pos.getY() + 1.0D;
            double d2 = (double)pos.getZ() + 0.5D;
            worldIn.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            worldIn.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.01D, 0.0D);
        }
    }

    @Override
    public int getLightValue(BlockState state) {
        return state.get(LIT) ? 10 : 0;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction facing = state.get(FACING);
        return facing == Direction.DOWN ? VS_BOTTOM : VS_SIDE[facing.getHorizontalIndex()];
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return rotate(state, Rotation.CLOCKWISE_180);
    }

    @Override
    public int getDisplayedLightLevel() {
        return 10;
    }
}
