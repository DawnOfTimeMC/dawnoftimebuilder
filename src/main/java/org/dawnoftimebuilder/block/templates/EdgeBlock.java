package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class EdgeBlock extends WaterloggedBlock {

	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
	public static final EnumProperty<StairsShape> SHAPE = BlockStateProperties.STAIRS_SHAPE;
	private static final VoxelShape[] SHAPES_TOP = makeShapes(false);
	private static final VoxelShape[] SHAPES_BOTTOM = makeShapes(true);

	public EdgeBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(HALF, Half.BOTTOM).setValue(SHAPE, StairsShape.STRAIGHT));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING, HALF, SHAPE);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		int index = (state.getValue(FACING).get2DDataValue() + 2) % 4;
		index *= 3;
		switch (state.getValue(SHAPE)) {
			default:
			case OUTER_LEFT:
				break;
			case OUTER_RIGHT:
				index += 3;
				break;
			case STRAIGHT:
				index += 1;
				break;
			case INNER_LEFT:
				index += 2;
				break;
			case INNER_RIGHT:
				index += 5;
				break;
		}
		index %= 12;
		return state.getValue(HALF) == Half.BOTTOM ? SHAPES_BOTTOM[index] : SHAPES_TOP[index];
	}

	/**
	 * @return Stores VoxelShape with index : <p/>
	 * 0 : NW Outer <p/>
	 * 1 : NW Default <p/>
	 * 2 : NW Inner <p/>
	 * 3 : NE Outer <p/>
	 * 4 : NE Default <p/>
	 * 5 : NE Inner <p/>
	 * 6 : SE Outer <p/>
	 * 7 : SE Default <p/>
	 * 8 : SE Inner <p/>
	 * 9 : SW Outer <p/>
	 * 10 : SW Default <p/>
	 * 11 : SW Inner <p/>
	 */
	private static VoxelShape[] makeShapes(boolean bottom) {
		VoxelShape vs_north_flat = net.minecraft.block.Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D);
		VoxelShape vs_east_flat = net.minecraft.block.Block.box(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
		VoxelShape vs_south_flat = net.minecraft.block.Block.box(0.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D);
		VoxelShape vs_west_flat = net.minecraft.block.Block.box(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 16.0D);
		VoxelShape vs_nw_corner = net.minecraft.block.Block.box(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 8.0D);
		VoxelShape vs_ne_corner = net.minecraft.block.Block.box(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D);
		VoxelShape vs_se_corner = net.minecraft.block.Block.box(8.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D);
		VoxelShape vs_sw_corner = net.minecraft.block.Block.box(0.0D, 0.0D, 8.0D, 8.0D, 8.0D, 16.0D);
		VoxelShape[] vss = new VoxelShape[]{
				vs_nw_corner,
				vs_north_flat,
				VoxelShapes.or(vs_north_flat, vs_sw_corner),
				vs_ne_corner,
				vs_east_flat,
				VoxelShapes.or(vs_east_flat, vs_nw_corner),
				vs_se_corner,
				vs_south_flat,
				VoxelShapes.or(vs_south_flat, vs_ne_corner),
				vs_sw_corner,
				vs_west_flat,
				VoxelShapes.or(vs_west_flat, vs_se_corner),
		};
		if(bottom) return vss;
		for(int i = 0; i < vss.length; i++) {
			vss[i] = vss[i].move(0.0D, 0.5D, 0.0D);
		}
		return vss;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState state = super.getStateForPlacement(context);
		Direction direction = context.getClickedFace();
		BlockPos pos = context.getClickedPos();
		state = state.setValue(FACING, context.getHorizontalDirection()).setValue(HALF, direction != Direction.DOWN && (direction == Direction.UP || !(context.getClickLocation().y - (double)pos.getY() > 0.5D)) ? Half.BOTTOM : Half.TOP);
		return state.setValue(SHAPE, getShapeProperty(state, context.getLevel(), pos));
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		return facing.getAxis().isHorizontal() ? stateIn.setValue(SHAPE, getShapeProperty(stateIn, worldIn, currentPos)) : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	/**
	 * Returns a stair shape property based on the surrounding stairs from the given blockstate and position
	 */
	private static StairsShape getShapeProperty(BlockState state, IBlockReader worldIn, BlockPos pos) {
		Direction direction = state.getValue(FACING);

		BlockState adjacentState = worldIn.getBlockState(pos.relative(direction));
		if (isBlockEdge(adjacentState) && state.getValue(HALF) == adjacentState.getValue(HALF)) {
			Direction adjacentDirection = adjacentState.getValue(FACING);
			if (adjacentDirection.getAxis() != state.getValue(FACING).getAxis() && isDifferentEdge(state, worldIn, pos, adjacentDirection.getOpposite())) {
				return (adjacentDirection == direction.getCounterClockWise()) ? StairsShape.OUTER_LEFT : StairsShape.OUTER_RIGHT;
			}
		}

		adjacentState = worldIn.getBlockState(pos.relative(direction.getOpposite()));
		if (isBlockEdge(adjacentState) && state.getValue(HALF) == adjacentState.getValue(HALF)) {
			Direction adjacentDirection = adjacentState.getValue(FACING);
			if (adjacentDirection.getAxis() != state.getValue(FACING).getAxis() && isDifferentEdge(state, worldIn, pos, adjacentDirection)) {
				return (adjacentDirection == direction.getCounterClockWise()) ? StairsShape.INNER_LEFT : StairsShape.INNER_RIGHT;
			}
		}

		return StairsShape.STRAIGHT;
	}

	private static boolean isDifferentEdge(BlockState state, IBlockReader worldIn, BlockPos pos, Direction face) {
		BlockState adjacentState = worldIn.getBlockState(pos.relative(face));
		return !isBlockEdge(adjacentState) || adjacentState.getValue(FACING) != state.getValue(FACING) || adjacentState.getValue(HALF) != state.getValue(HALF);
	}

	public static boolean isBlockEdge(BlockState state) {
		return state.getBlock() instanceof EdgeBlock;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		Direction direction = state.getValue(FACING);
		StairsShape stairsshape = state.getValue(SHAPE);
		switch(mirrorIn) {
			case LEFT_RIGHT:
				if (direction.getAxis() == Direction.Axis.Z) {
					switch(stairsshape) {
						case INNER_LEFT:
							return state.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.INNER_RIGHT);
						case INNER_RIGHT:
							return state.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.INNER_LEFT);
						case OUTER_LEFT:
							return state.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.OUTER_RIGHT);
						case OUTER_RIGHT:
							return state.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.OUTER_LEFT);
						default:
							return state.rotate(Rotation.CLOCKWISE_180);
					}
				}
				break;
			case FRONT_BACK:
				if (direction.getAxis() == Direction.Axis.X) {
					switch(stairsshape) {
						case INNER_LEFT:
							return state.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.INNER_LEFT);
						case INNER_RIGHT:
							return state.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.INNER_RIGHT);
						case OUTER_LEFT:
							return state.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.OUTER_RIGHT);
						case OUTER_RIGHT:
							return state.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.OUTER_LEFT);
						case STRAIGHT:
							return state.rotate(Rotation.CLOCKWISE_180);
					}
				}
		}

		return super.mirror(state, mirrorIn);
	}
}