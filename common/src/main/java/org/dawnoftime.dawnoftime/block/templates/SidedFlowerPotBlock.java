package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

import javax.annotation.Nullable;
import java.util.Random;

public class SidedFlowerPotBlock extends FlowerPotBlockDoT {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public SidedFlowerPotBlock(@Nullable Item itemInPot) {
        super(itemInPot);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    public BlockState getRandomState() {
        return this.defaultBlockState().setValue(FACING, Direction.Plane.HORIZONTAL.getRandomDirection((RandomSource) new Random()));
    }

    @Override
    public float getDisplayScale() {
        return 1.0F;
    }
}
