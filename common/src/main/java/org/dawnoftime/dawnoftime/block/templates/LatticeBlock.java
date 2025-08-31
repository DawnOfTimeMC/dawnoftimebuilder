package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.dawnoftime.dawnoftime.util.VoxelShapes;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class LatticeBlock extends WaterloggedBlock {
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;

    public LatticeBlock(Properties properties) {
        super(properties, VoxelShapes.LATTICE_FOUR_SIDES);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false).setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(NORTH, EAST, SOUTH, WEST);
    }

    @Override
    public int getShapeIndex(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        int index = 0;
        if(state.getValue(SOUTH))
            index += 1;
        if(state.getValue(WEST))
            index += 2;
        if(state.getValue(NORTH))
            index += 4;
        if(state.getValue(EAST))
            index += 8;
        if(index > 14)
            index = 0;
        return index;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = context.getLevel().getBlockState(context.getClickedPos());
        if(state.getBlock() != this)
            state = super.getStateForPlacement(context);
        if (state == null) {
            return null;
        }
        return switch (context.getHorizontalDirection()) {
            case WEST -> state.setValue(WEST, true);
            case NORTH -> state.setValue(NORTH, true);
            case EAST -> state.setValue(EAST, true);
            default -> state.setValue(SOUTH, true);
        };
    }

    @Override
    public boolean canBeReplaced(@NotNull BlockState state, BlockPlaceContext useContext) {
        ItemStack itemstack = useContext.getItemInHand();
        if(useContext.getPlayer() != null && useContext.getPlayer().isCrouching())
            return false;
        if(itemstack.getItem() == this.asItem()) {
            Direction newDirection = useContext.getHorizontalDirection();
            return switch (newDirection) {
                case WEST -> !state.getValue(WEST);
                case NORTH -> !state.getValue(NORTH);
                case EAST -> !state.getValue(EAST);
                default -> !state.getValue(SOUTH);
            };
        }
        return false;
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        boolean n = state.getValue(NORTH);
        boolean e = state.getValue(EAST);
        boolean s = state.getValue(SOUTH);
        boolean w = state.getValue(WEST);

        return switch (rotation) {
            case CLOCKWISE_90 -> state.setValue(NORTH, w).setValue(EAST, n).setValue(SOUTH, e).setValue(WEST, s);
            case CLOCKWISE_180 -> state.setValue(NORTH, s).setValue(EAST, w).setValue(SOUTH, n).setValue(WEST, e);
            case COUNTERCLOCKWISE_90 -> state.setValue(NORTH, e).setValue(EAST, s).setValue(SOUTH, w).setValue(WEST, n);
            default -> state;
        };
    }

    @Override
    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        boolean n = state.getValue(NORTH);
        boolean e = state.getValue(EAST);
        boolean s = state.getValue(SOUTH);
        boolean w = state.getValue(WEST);

        return switch (mirror) {
            case LEFT_RIGHT -> state.setValue(NORTH, s).setValue(SOUTH, n).setValue(EAST, e).setValue(WEST, w);
            case FRONT_BACK -> state.setValue(EAST, w).setValue(WEST, e).setValue(NORTH, n).setValue(SOUTH, s);
            default -> state;
        };
    }
}
