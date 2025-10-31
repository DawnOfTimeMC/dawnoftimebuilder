package org.dawnoftime.dawnoftime.block.japanese;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.block.templates.PaneBlockDoT;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA;
import org.jetbrains.annotations.NotNull;

public class LittleFlagBlock extends PaneBlockDoT {
    public static final BooleanProperty AXIS_Y = BlockStatePropertiesAA.AXIS_Y;
    private final VoxelShape VS_PILLAR = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);

    public LittleFlagBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(AXIS_Y, true).setValue(NORTH, true).setValue(WEST, true).setValue(SOUTH, true).setValue(EAST, true).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AXIS_Y);
    }

    @Override
    public @NotNull BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState newState = super.getStateForPlacement(context);
        if(newState == null)
            newState = this.defaultBlockState();
        if(this.hasNoConnection(newState))
            newState = this.defaultBlockState().setValue(WATERLOGGED, newState.getValue(WATERLOGGED));
        return newState.setValue(AXIS_Y, context.getClickedFace().getAxis().isVertical());
    }

    @Override
    protected BlockState updateShape(BlockState stateIn, LevelReader worldIn, ScheduledTickAccess scheduledTickAccess, BlockPos currentPos, Direction facing, BlockPos facingPos, BlockState facingState, RandomSource random) {
        if(stateIn.getValue(WATERLOGGED))
            scheduledTickAccess.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        if(facing.getAxis().isHorizontal()) {
            if(this.hasAllConnections(stateIn)) {
                //We must check connections on all sides
                BlockState northState = worldIn.getBlockState(currentPos.north());
                BlockState westState = worldIn.getBlockState(currentPos.west());
                BlockState southState = worldIn.getBlockState(currentPos.south());
                BlockState eastState = worldIn.getBlockState(currentPos.east());
                return stateIn
                        .setValue(NORTH, this.canAttachPane(worldIn, currentPos.north(),  Direction.SOUTH, northState))
                        .setValue(WEST, this.canAttachPane(worldIn, currentPos.west(), Direction.EAST, westState))
                        .setValue(SOUTH, this.canAttachPane(worldIn, currentPos.south(), Direction.NORTH, southState))
                        .setValue(EAST, this.canAttachPane(worldIn, currentPos.east(), Direction.WEST, eastState));
            } else {
                stateIn = stateIn.setValue(PROPERTY_BY_DIRECTION.get(facing), this.canAttachPane(worldIn, facingPos, facing.getOpposite(), facingState));
                if(this.hasNoConnection(stateIn))
                    return stateIn.setValue(NORTH, true).setValue(WEST, true).setValue(SOUTH, true).setValue(EAST, true);
            }
        }
        return stateIn;
    }

    private boolean hasNoConnection(BlockState state) {
        return !state.getValue(NORTH) && !state.getValue(WEST) && !state.getValue(SOUTH) && !state.getValue(EAST);
    }

    private boolean hasAllConnections(BlockState state) {
        return state.getValue(NORTH) && state.getValue(WEST) && state.getValue(SOUTH) && state.getValue(EAST);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        if(state.getValue(AXIS_Y))
            return Shapes.or(super.getShape(state, worldIn, pos, context), VS_PILLAR);

        return super.getShape(state, worldIn, pos, context);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }
}
