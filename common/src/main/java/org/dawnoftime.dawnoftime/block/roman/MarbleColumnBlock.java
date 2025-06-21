package org.dawnoftime.dawnoftime.block.roman;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.dawnoftime.dawnoftime.block.IBlockPillar;
import org.dawnoftime.dawnoftime.block.templates.ConnectedVerticalBlock;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static org.dawnoftime.dawnoftime.util.VoxelShapes.MARBLE_COLUMN_SHAPES;

public class MarbleColumnBlock extends ConnectedVerticalBlock implements IBlockPillar {
    public static final BooleanProperty AXIS_X = BlockStatePropertiesAA.AXIS_X;

    public MarbleColumnBlock(Properties properties) {
        super(properties, MARBLE_COLUMN_SHAPES);
        this.registerDefaultState(this.defaultBlockState().setValue(AXIS_X, true));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AXIS_X);
    }

    @Override
    public int getShapeIndex(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return switch (state.getValue(VERTICAL_CONNECTION)) {
            case ABOVE -> 1;
            case UNDER -> state.getValue(AXIS_X) ? 2 : 3;
            default -> 0;
        };
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(AXIS_X, context.getNearestLookingDirection().getAxis() == Direction.Axis.X);
    }

    @Nonnull
    @Override
    public BlockStatePropertiesAA.PillarConnection getBlockPillarConnectionAbove(BlockState state) {
        return BlockStatePropertiesAA.PillarConnection.EIGHT_PX;
    }

    @Override
    public boolean isConnectible(BlockState stateIn, LevelAccessor worldIn, BlockPos pos, Direction faceToConnect) {
        BlockState testedState = worldIn.getBlockState(pos);
        if (faceToConnect == Direction.DOWN && IBlockPillar.getPillarConnectionUnder(worldIn, pos) == BlockStatePropertiesAA.PillarConnection.EIGHT_PX) {
            return true;
        }
        if (faceToConnect == Direction.UP && IBlockPillar.getPillarConnectionAbove(worldIn, pos) == BlockStatePropertiesAA.PillarConnection.EIGHT_PX) {
            return true;
        }
        return testedState.getBlock() == this;
    }
}
