package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class BottomPaneBlock extends PillarPaneBlock {
    private static final BooleanProperty BOTTOM = BlockStateProperties.BOTTOM;

    public BottomPaneBlock(final Properties propertiesIn) {
        super(propertiesIn);
        this.registerDefaultState(this.defaultBlockState().setValue(BOTTOM, true));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BOTTOM);
    }

    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext contextIn) {
        Level world = contextIn.getLevel();
        BlockPos pos = contextIn.getClickedPos();
        BlockState state = super.getStateForPlacement(contextIn);
        if(state != null) {
            state = state.setValue(BOTTOM, !(world.getBlockState(pos.below()).getBlock() instanceof BottomPaneBlock));
        }
        return state;
    }

    @Override
    protected BlockState updateShape(BlockState stateIn, LevelReader worldIn, ScheduledTickAccess scheduledTickAccess, BlockPos currentPos, Direction facing, BlockPos facingPos, BlockState facingState, RandomSource random) {
        stateIn = super.updateShape(stateIn, worldIn, scheduledTickAccess, currentPos, facing, facingPos, facingState, random);
        if(facing == Direction.DOWN) {
            stateIn = stateIn.setValue(BOTTOM, !(facingState.getBlock() instanceof BottomPaneBlock));
        }
        return stateIn;
    }
}