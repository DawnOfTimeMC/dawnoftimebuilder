package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.block.templates.WaterloggedHorizontalBlock;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA;
import org.dawnoftime.dawnoftime.util.Utils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import static net.minecraft.core.Direction.NORTH;
import static org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA.HorizontalConnection.NONE;

public class ConnectedHorizontalBlock extends WaterloggedHorizontalBlock {
    public static final EnumProperty<BlockStatePropertiesAA.HorizontalConnection> HORIZONTAL_CONNECTION = BlockStatePropertiesAA.HORIZONTAL_CONNECTION;

    public ConnectedHorizontalBlock(Properties properties, VoxelShape[] shapes) {
        super(properties, shapes);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, NORTH).setValue(HORIZONTAL_CONNECTION, NONE).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HORIZONTAL_CONNECTION);
    }

    @Override
    public int getShapeIndex(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        int index = state.getValue(HORIZONTAL_CONNECTION).getIndex();
        return index + state.getValue(FACING).get2DDataValue() * 4;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        return state.setValue(HORIZONTAL_CONNECTION, this.getLineState(context.getLevel(), context.getClickedPos(), state));
    }

    @Override
    public @NotNull BlockState updateShape(BlockState stateIn, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor worldIn, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        return (facing.getAxis() == stateIn.getValue(FACING).getClockWise().getAxis()) ? stateIn.setValue(HORIZONTAL_CONNECTION, this.getLineState(worldIn, currentPos, stateIn)) : stateIn;
    }

    private BlockStatePropertiesAA.HorizontalConnection getLineState(LevelAccessor worldIn, BlockPos pos, BlockState stateIn) {
        Direction direction = stateIn.getValue(FACING).getClockWise();
        if(isConnectible(worldIn, pos.relative(direction, -1), stateIn)) {
            return (isConnectible(worldIn, pos.relative(direction), stateIn)) ? BlockStatePropertiesAA.HorizontalConnection.BOTH : BlockStatePropertiesAA.HorizontalConnection.LEFT;
        } else {
            return (isConnectible(worldIn, pos.relative(direction), stateIn)) ? BlockStatePropertiesAA.HorizontalConnection.RIGHT : NONE;
        }
    }

    private boolean isConnectible(LevelAccessor worldIn, BlockPos offset, BlockState stateIn) {
        BlockState state = worldIn.getBlockState(offset);
        if(state.getBlock() == this) {
            return state.getValue(FACING) == stateIn.getValue(FACING);
        }
        return false;
    }
}
