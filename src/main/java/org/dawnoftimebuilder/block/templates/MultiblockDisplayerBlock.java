package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;

public class MultiblockDisplayerBlock extends DisplayerBlock {

    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTH = BlockStateProperties.NORTH;
    public static final BooleanProperty WEST = BlockStateProperties.NORTH;

    public MultiblockDisplayerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(NORTH, false).setValue(WATERLOGGED, Boolean.FALSE).setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<net.minecraft.block.Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(NORTH, EAST, SOUTH, WEST);
    }

    @Override
    public double getDisplayerX(BlockState state) {
        return 0;
    }

    @Override
    public double getDisplayerY(BlockState state) {
        return 0;
    }

    @Override
    public double getDisplayerZ(BlockState state) {
        return 0;
    }
}
