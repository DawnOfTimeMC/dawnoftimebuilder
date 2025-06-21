package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import static org.dawnoftime.dawnoftime.util.VoxelShapes.SMALL_POOL_COLLISION_SHAPES;
import static org.dawnoftime.dawnoftime.util.VoxelShapes.SMALL_POOL_SHAPES;

public class SmallPoolBlock extends PoolBlock {
    public SmallPoolBlock(final Properties propertiesIn) {
        super(propertiesIn, 6, 4, SMALL_POOL_SHAPES);
        this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.BOTTOM, true));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.BOTTOM);
    }

    @Override
    public int getShapeIndex(final @NotNull BlockState state, final @NotNull BlockGetter worldIn, final @NotNull BlockPos pos, final @NotNull CollisionContext context) {
        int index = 0;
        if(state.getValue(BlockStateProperties.NORTH)) {
            index += 1;
        }
        if(state.getValue(BlockStateProperties.EAST)) {
            index += 2;
        }
        if(state.getValue(BlockStateProperties.SOUTH)) {
            index += 4;
        }
        if(state.getValue(BlockStateProperties.WEST)) {
            index += 8;
        }
        if(state.getValue(BlockStatePropertiesAA.HAS_PILLAR)) {
            index += 16;
        }
        if(state.getValue(BlockStateProperties.BOTTOM)) {
            index += 32;
        }
        return index;
    }

    @Override
    public VoxelShape getCollisionShape(final BlockState state, final BlockGetter worldIn, final BlockPos pos, final CollisionContext context) {
        int index = 0;
        if(state.getValue(BlockStateProperties.NORTH)) {
            index += 1;
        }
        if(state.getValue(BlockStateProperties.EAST)) {
            index += 2;
        }
        if(state.getValue(BlockStateProperties.SOUTH)) {
            index += 4;
        }
        if(state.getValue(BlockStateProperties.WEST)) {
            index += 8;
        }
        if(state.getValue(BlockStatePropertiesAA.HAS_PILLAR)) {
            index += 16;
        }
        return SMALL_POOL_COLLISION_SHAPES[index];
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        if(state != null) {
            if(!canSupportCenter(context.getLevel(), context.getClickedPos().below(), Direction.UP)) {
                state = state.setValue(BlockStateProperties.BOTTOM, false);
            }
        }
        return state;
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction directionIn, BlockState facingStateIn, LevelAccessor worldIn, BlockPos currentPosIn, BlockPos facingPosIn) {
        stateIn = super.updateShape(stateIn, directionIn, facingStateIn, worldIn, currentPosIn, facingPosIn);
        if(directionIn == Direction.DOWN) {
            stateIn = stateIn.setValue(BlockStateProperties.BOTTOM, canSupportCenter(worldIn, facingPosIn, Direction.UP));
        }
        return stateIn;
    }
}