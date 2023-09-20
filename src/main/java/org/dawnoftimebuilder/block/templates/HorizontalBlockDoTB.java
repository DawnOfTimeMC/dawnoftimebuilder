package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;

import javax.annotation.Nullable;

public class HorizontalBlockDoTB extends HorizontalBlock {
    public HorizontalBlockDoTB(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction clicked = context.getClickedFace();
        if(clicked.getAxis().isHorizontal()){
            return this.defaultBlockState().setValue(FACING, clicked);
        }
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<net.minecraft.block.Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }
}
