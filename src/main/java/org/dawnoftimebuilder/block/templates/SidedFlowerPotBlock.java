package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;

import javax.annotation.Nullable;
import java.util.Random;

public class SidedFlowerPotBlock extends FlowerPotBlockDoTB {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

	public SidedFlowerPotBlock(@Nullable Item itemInPot) {
        super(itemInPot);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    public BlockState getRandomState() {
        return this.defaultBlockState().setValue(FACING, Direction.Plane.HORIZONTAL.getRandomDirection(new Random()));
    }

    @Override
    public float getDisplayScale() {
        return 1.0F;
    }
}
