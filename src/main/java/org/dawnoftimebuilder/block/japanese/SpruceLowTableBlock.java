package org.dawnoftimebuilder.block.japanese;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.block.templates.DisplayerBlock;

import javax.annotation.Nonnull;

public class SpruceLowTableBlock extends DisplayerBlock {

	private static final VoxelShape X_AXIS_VS = Block.box(0.0D, 0.0D, 2.0D, 16.0D, 8.0D, 14.0D);
	private static final VoxelShape Z_AXIS_VS = Block.box(2.0D, 0.0D, 0.0D, 14.0D, 8.0D, 16.0D);
	public static final EnumProperty<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;

	public SpruceLowTableBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(HORIZONTAL_AXIS, Direction.Axis.X).setValue(WATERLOGGED, Boolean.FALSE).setValue(LIT, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(HORIZONTAL_AXIS);
	}

	@Nonnull
	@Override
	public VoxelShape getShape(BlockState state, @Nonnull BlockGetter worldIn, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
		return (state.getValue(HORIZONTAL_AXIS) == Direction.Axis.X) ? X_AXIS_VS : Z_AXIS_VS;
	}

	@Nonnull
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(HORIZONTAL_AXIS, context.getHorizontalDirection().getAxis() == Direction.Axis.Z ? Direction.Axis.X : Direction.Axis.Z);
	}

	@Override
	public double getDisplayerX(BlockState state){
		return (state.getValue(HORIZONTAL_AXIS) == Direction.Axis.X) ? 0.1875D : 0.3125D;
	}

	@Override
	public double getDisplayerY(BlockState state){
		return 0.46875D;
	}

	@Override
	public double getDisplayerZ(BlockState state){
		return (state.getValue(HORIZONTAL_AXIS) == Direction.Axis.X) ? 0.3125D : 0.1875D;
	}

	@Nonnull
	@Override
	public BlockState rotate(@Nonnull BlockState state, @Nonnull Rotation rot) {
		if(rot == Rotation.CLOCKWISE_90 || rot == Rotation.COUNTERCLOCKWISE_90) return state.setValue(HORIZONTAL_AXIS, (state.getValue(HORIZONTAL_AXIS) == Direction.Axis.X) ? Direction.Axis.Z : Direction.Axis.X);
		else return super.rotate(state, rot);
	}
}