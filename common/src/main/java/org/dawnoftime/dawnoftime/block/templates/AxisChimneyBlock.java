package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class AxisChimneyBlock extends ChimneyBlockDoT {
    public static final EnumProperty<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;

    public AxisChimneyBlock(final Properties properties, VoxelShape[] shapes) {
        super(properties, shapes);
        this.registerDefaultState(this.defaultBlockState().setValue(AxisChimneyBlock.HORIZONTAL_AXIS, Direction.Axis.X).setValue(ChimneyBlockDoT.LIT, true));
    }

    @Override
    public int getShapeIndex(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return super.getShapeIndex(state, worldIn, pos, context);
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AxisChimneyBlock.HORIZONTAL_AXIS);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext context) {
        final BlockState state = super.getStateForPlacement(context);
        return state.setValue(AxisChimneyBlock.HORIZONTAL_AXIS, context.getHorizontalDirection().getAxis() == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X);
    }
}
