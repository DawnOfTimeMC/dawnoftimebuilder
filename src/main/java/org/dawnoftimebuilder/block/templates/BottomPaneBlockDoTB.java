package org.dawnoftimebuilder.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class BottomPaneBlockDoTB extends PillarPaneBlock {
    private static final BooleanProperty BOTTOM = BlockStateProperties.BOTTOM;

    public BottomPaneBlockDoTB(DyeColor dyeColor, final Properties propertiesIn) {
        super(dyeColor, propertiesIn);
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
            state = state.setValue(BOTTOM, !(world.getBlockState(pos.below()).getBlock() instanceof BottomPaneBlockDoTB));
        }
        return state;
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        if(facing == Direction.DOWN) {
            stateIn = stateIn.setValue(BOTTOM, !(facingState.getBlock() instanceof BottomPaneBlockDoTB));
        }
        return stateIn;
    }
}