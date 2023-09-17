package org.dawnoftimebuilder.block.german;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.block.templates.WaterloggedBlock;
import org.dawnoftimebuilder.util.DoTBUtils;

import javax.annotation.Nonnull;

import static net.minecraft.core.Direction.NORTH;

public class StoneBricksArrowslitBlock extends WaterloggedBlock {

	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	private static final VoxelShape[] SHAPES = DoTBUtils.GenerateHorizontalShapes(new VoxelShape[]{
			Shapes.or(
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
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPES[state.getValue(FACING).get2DDataValue()];
	}

	@Nonnull
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
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
