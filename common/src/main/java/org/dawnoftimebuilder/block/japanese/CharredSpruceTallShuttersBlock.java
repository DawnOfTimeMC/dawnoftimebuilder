package org.dawnoftimebuilder.block.japanese;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
import org.dawnoftimebuilder.util.BlockStatePropertiesAA;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import static org.dawnoftimebuilder.util.BlockStatePropertiesAA.SquareCorners;
import static org.dawnoftimebuilder.util.VoxelShapes.CHARRED_SPRUCE_TALL_SHUTTERS_SHAPES;

public class CharredSpruceTallShuttersBlock extends CharredSpruceShuttersBlock {
    public static final EnumProperty<SquareCorners> CORNER = BlockStatePropertiesAA.CORNER;

    public CharredSpruceTallShuttersBlock(Properties properties) {
        super(properties, CHARRED_SPRUCE_TALL_SHUTTERS_SHAPES);
        this.registerDefaultState(this.defaultBlockState().setValue(CORNER, SquareCorners.TOP_LEFT));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(CORNER);
    }

    @Override
    public int getShapeIndex(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        int index = state.getValue(OPEN) ? state.getValue(CORNER).isTopCorner() ? 1 : 2 : 0;
        return state.getValue(FACING).get2DDataValue() * 3 + index;
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
    public @NotNull BlockState updateShape(BlockState stateIn, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor worldIn, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
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

    @Override
    public BlockState playerWillDestroy(Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        // Prevents item from dropping in creative by removing the part that gives the item with a setBlock.
        if (!level.isClientSide() && player.isCreative()) {
            SquareCorners corner = state.getValue(CORNER);
            if (corner != SquareCorners.TOP_LEFT) {
                BlockPos cornerPos = pos.above(SquareCorners.TOP_LEFT.getVerticalOffset(corner)).relative(state.getValue(FACING).getClockWise(), SquareCorners.TOP_LEFT.getHorizontalOffset(corner));
                BlockState cornerState = level.getBlockState(cornerPos);
                if (cornerState.is(this) && cornerState.getValue(FACING) == state.getValue(FACING) && cornerState.getValue(CORNER) == SquareCorners.TOP_LEFT) {
                    level.setBlock(cornerPos, Blocks.AIR.defaultBlockState(), 35);
                    // Event that plays the "break block" sound.
                    level.levelEvent(player, 2001, cornerPos, Block.getId(state));
                }
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }
}