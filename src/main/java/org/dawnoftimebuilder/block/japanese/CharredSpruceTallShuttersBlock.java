package org.dawnoftimebuilder.block.japanese;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBUtils;

import javax.annotation.Nullable;

import static org.dawnoftimebuilder.util.DoTBBlockStateProperties.SquareCorners;

public class CharredSpruceTallShuttersBlock extends CharredSpruceShuttersBlock {
    public static final EnumProperty<SquareCorners> CORNER = DoTBBlockStateProperties.CORNER;
    private static final VoxelShape[] SHAPES = DoTBUtils.GenerateHorizontalShapes(new VoxelShape[] {
            Block.box(0.0D, 0.0D, 14.0D, 16.0D, 16.0D, 16.0D),
            Shapes.or(
                    Block.box(0.0D, 10.0D, 11.0D, 16.0D, 16.0D, 16.0D),
                    Block.box(0.0D, 5.0D, 9.0D, 16.0D, 10.0D, 14.0D),
                    Block.box(0.0D, 0.0D, 7.0D, 16.0D, 5.0D, 12.0D)),
            Shapes.or(
                    Block.box(0.0D, 11.0D, 5.0D, 16.0D, 16.0D, 10.0D),
                    Block.box(0.0D, 6.0D, 3.0D, 16.0D, 11.0D, 8.0D),
                    Block.box(0.0D, 1.0D, 1.0D, 16.0D, 6.0D, 6.0D)) });

    public CharredSpruceTallShuttersBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(CORNER, DoTBBlockStateProperties.SquareCorners.TOP_LEFT));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(CORNER);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        int index = state.getValue(OPEN) ? state.getValue(CORNER).isTopCorner() ? 1 : 2 : 0;
        return SHAPES[state.getValue(FACING).get2DDataValue() * 3 + index];
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction facing = context.getHorizontalDirection();

        BlockPos posRight = pos.relative(facing.getClockWise());
        boolean right = world.getBlockState(posRight).canBeReplaced(context);
        BlockPos posLeft = pos.relative(facing.getCounterClockWise());
        boolean left = world.getBlockState(posLeft).canBeReplaced(context);
        if(!right && !left)
            return null;

        if(world.getBlockState(pos.above()).canBeReplaced(context)) {
            if(right) {
                if(world.getBlockState(posRight.above()).canBeReplaced(context)) {
                    return super.getStateForPlacement(context).setValue(CORNER, SquareCorners.BOTTOM_LEFT).setValue(FACING, facing).setValue(POWERED, world.hasNeighborSignal(pos));
                }
            }
            if(left) {
                if(world.getBlockState(posLeft.above()).canBeReplaced(context)) {
                    return super.getStateForPlacement(context).setValue(CORNER, SquareCorners.BOTTOM_RIGHT).setValue(FACING, facing).setValue(POWERED, world.hasNeighborSignal(pos));
                }
            }
        }
        //The block from top row of the shutters can't be put above. The top of the shutters must be on this Y.
        // We need to check if the currentPos can support shutters.
        if(world.getBlockState(pos.below()).canBeReplaced(context)) {
            if(right) {
                if(world.getBlockState(posRight.below()).canBeReplaced(context)) {
                    return super.getStateForPlacement(context).setValue(CORNER, SquareCorners.TOP_LEFT).setValue(FACING, facing).setValue(POWERED, world.hasNeighborSignal(pos));
                }
            }
            if(left) {
                if(world.getBlockState(posLeft.below()).canBeReplaced(context)) {
                    return super.getStateForPlacement(context).setValue(CORNER, SquareCorners.TOP_RIGHT).setValue(FACING, facing).setValue(POWERED, world.hasNeighborSignal(pos));
                }
            }
        }
        return null;
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        SquareCorners thisCorner = state.getValue(CORNER);
        Direction facing = state.getValue(FACING);
        //Let's set the 3 other corners
        for(SquareCorners corner : SquareCorners.values()) {
            if(thisCorner != corner) {
                BlockPos cornerPos = pos.above(corner.getVerticalOffset(thisCorner)).relative(facing.getClockWise(), corner.getHorizontalOffset(thisCorner));
                worldIn.setBlock(cornerPos, state.setValue(CORNER, corner).setValue(WATERLOGGED, worldIn.getFluidState(cornerPos).getType() == Fluids.WATER), 10);
            }
        }
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        SquareCorners thisCorner = stateIn.getValue(CORNER);
        if(stateIn.getValue(WATERLOGGED))
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        Direction currentFacing = stateIn.getValue(FACING);
        SquareCorners expectedCorner = thisCorner.getAdjacentCorner(facing.getAxis().isVertical());
        if(currentPos.above(expectedCorner.getVerticalOffset(thisCorner)).relative(currentFacing.getClockWise(), expectedCorner.getHorizontalOffset(thisCorner)).equals(facingPos)) {
            //The pos of the updated block is supposed to contain a part of the shutter : let's check it
            if(facingState.getBlock() == this) {
                if(facingState.getValue(CORNER) == expectedCorner && facingState.getValue(FACING) == currentFacing) {
                    return stateIn.setValue(OPEN, facingState.getValue(OPEN));
                }
            }
            return Blocks.AIR.defaultBlockState();
        }
        return stateIn;
    }
}