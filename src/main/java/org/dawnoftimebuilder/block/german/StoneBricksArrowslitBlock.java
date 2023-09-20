package org.dawnoftimebuilder.block.german;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import org.dawnoftimebuilder.block.templates.WaterloggedBlock;
import org.dawnoftimebuilder.util.DoTBUtils;

import javax.annotation.Nonnull;

import static net.minecraft.util.Direction.NORTH;

public class StoneBricksArrowslitBlock extends WaterloggedBlock {

	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	private static final VoxelShape[] SHAPES = DoTBUtils.GenerateHorizontalShapes(new VoxelShape[]{
			VoxelShapes.or(
					Block.box(0.0D, 0.0D, 14.0D, 7.0D, 16.0D, 16.0D),
					Block.box(9.0D, 0.0D, 14.0D, 16.0D, 16.0D, 16.0D),
					Block.box(0.0D, 0.0D, 8.0D, 2.0D, 16.0D, 14.0D),
					Block.box(14.0D, 0.0D, 8.0D, 16.0D, 16.0D, 14.0D),
					Block.box(2.0D, 0.0D, 13.0D, 4.5D, 16.0D, 14.0D),
					Block.box(11.5D, 0.0D, 13.0D, 14.0D, 16.0D, 14.0D)
			)
	});

	public StoneBricksArrowslitBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(FACING, NORTH).setValue(WATERLOGGED, false));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPES[state.getValue(FACING).get2DDataValue()];
	}

	@Nonnull
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection());
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot){
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return this.rotate(state, Rotation.CLOCKWISE_180);
	}
}
