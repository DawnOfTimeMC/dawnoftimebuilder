package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.block.IBlockPillar;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import static org.dawnoftime.dawnoftime.util.VoxelShapes.SUPPORT_BEAM_SHAPES;

public class SupportBeamBlock extends WaterloggedBlock {
    private static final EnumProperty<BlockStatePropertiesAA.PillarConnection> PILLAR_CONNECTION = BlockStatePropertiesAA.PILLAR_CONNECTION;
    public static final EnumProperty<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    private static final BooleanProperty SUBAXIS = BlockStatePropertiesAA.SUBAXIS;

    public SupportBeamBlock(Properties properties) {
        super(properties, SUPPORT_BEAM_SHAPES);
        this.registerDefaultState(this.defaultBlockState().setValue(HORIZONTAL_AXIS, Direction.Axis.X).setValue(SUBAXIS, false).setValue(PILLAR_CONNECTION, BlockStatePropertiesAA.PillarConnection.NOTHING));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(PILLAR_CONNECTION, HORIZONTAL_AXIS, SUBAXIS);
    }

    @Override
    public int getShapeIndex(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        int index = switch (state.getValue(PILLAR_CONNECTION)) {
            case FOUR_PX -> 3;
            case EIGHT_PX -> 6;
            case TEN_PX -> 9;
            default -> 0;
        };
        if(state.getValue(SUBAXIS)) {
            return index + 2;
        }else{
            return state.getValue(HORIZONTAL_AXIS) == Direction.Axis.Z ? index + 1 : index;
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context).setValue(HORIZONTAL_AXIS, context.getHorizontalDirection().getAxis());
        return this.getCurrentState(state, context.getLevel(), context.getClickedPos());
    }

    @Override
    public @NotNull BlockState updateShape(BlockState stateIn, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor worldIn, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        return this.getCurrentState(stateIn, worldIn, currentPos);
    }

    private BlockState getCurrentState(BlockState stateIn, LevelAccessor worldIn, BlockPos currentPos) {
        if(stateIn.getValue(HORIZONTAL_AXIS) == Direction.Axis.X) {
            if(canConnect(worldIn, currentPos, Direction.NORTH) || canConnect(worldIn, currentPos, Direction.SOUTH))
                stateIn = stateIn.setValue(SUBAXIS, true);
        } else {
            if(canConnect(worldIn, currentPos, Direction.EAST) || canConnect(worldIn, currentPos, Direction.WEST))
                stateIn = stateIn.setValue(SUBAXIS, true);
        }
        return stateIn.setValue(PILLAR_CONNECTION, IBlockPillar.getPillarConnectionAbove(worldIn, currentPos.below()));
    }

    private boolean canConnect(LevelAccessor world, BlockPos pos, Direction direction) {
        BlockState state = world.getBlockState(pos.relative(direction));
        return isConnectibleBeam(state, direction) || isConnectibleSupportBeam(state, direction) || isConnectibleBeam(state, direction) || isConnectibleSupportBeam(state, direction);
    }

    private boolean isConnectibleBeam(BlockState state, Direction direction) {
        if(state.getBlock() instanceof BeamBlock)
            return (direction.getAxis() == Direction.Axis.X) ? state.getValue(BeamBlock.AXIS_X) : state.getValue(BeamBlock.AXIS_Z);
        else
            return false;
    }

    private boolean isConnectibleSupportBeam(BlockState state, Direction direction) {
        if(state.getBlock() instanceof SupportBeamBlock)
            return state.getValue(SupportBeamBlock.HORIZONTAL_AXIS) == direction.getAxis();
        else
            return false;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        if(rot == Rotation.CLOCKWISE_90 || rot == Rotation.COUNTERCLOCKWISE_90)
            return state.setValue(HORIZONTAL_AXIS, (state.getValue(HORIZONTAL_AXIS) == Direction.Axis.X) ? Direction.Axis.Z : Direction.Axis.X);
        else
            return super.rotate(state, rot);
    }
}