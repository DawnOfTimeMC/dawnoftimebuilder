package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.util.*;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.*;
import net.minecraft.util.math.BlockPos;

public class PlateBlock extends WaterloggedBlock {

	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	public static final EnumProperty<StairsShape> SHAPE = BlockStateProperties.STAIRS_SHAPE;
	private static final VoxelShape[] SHAPES = makeShapes();

	public PlateBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.getStateContainer().getBaseState().with(FACING, Direction.NORTH).with(SHAPE, StairsShape.STRAIGHT));
	}

	public PlateBlock(Material materialIn, float hardness, float resistance, SoundType soundType) {
		this(Properties.create(materialIn).hardnessAndResistance(hardness, resistance).sound(soundType));
	}

	public PlateBlock(Block block) {
		this(Properties.from(block));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(FACING, SHAPE);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		int index = (state.get(FACING).getHorizontalIndex() + 2) % 4;
		index *= 3;
		switch (state.get(SHAPE)) {
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
		return SHAPES[index];
	}

	/**
	 * @return Stores VoxelShape with index : <p/>
	 * 0 : NW Outer <p/>
	 * 1 : N Default <p/>
	 * 2 : NW Inner <p/>
	 * 3 : NE Outer <p/>
	 * 4 : N Default <p/>
	 * 5 : NE Inner <p/>
	 * 6 : SE Outer <p/>
	 * 7 : S Default <p/>
	 * 8 : SE Inner <p/>
	 * 9 : SW Outer <p/>
	 * 10 : S Default <p/>
	 * 11 : SW Inner <p/>
	 */
	private static VoxelShape[] makeShapes() {
		VoxelShape vs_north_flat = makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);
		VoxelShape vs_east_flat = makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
		VoxelShape vs_south_flat = makeCuboidShape(0.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D);
		VoxelShape vs_west_flat = makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
		VoxelShape vs_nw_corner = makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 8.0D);
		VoxelShape vs_ne_corner = makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);
		VoxelShape vs_se_corner = makeCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D);
		VoxelShape vs_sw_corner = makeCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 16.0D, 16.0D);
		return new VoxelShape[]{
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
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState state = super.getStateForPlacement(context).with(FACING, context.getPlacementHorizontalFacing());
		return state.with(SHAPE, getShapeProperty(state, context.getWorld(), context.getPos()));
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		stateIn = super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		return facing.getAxis().isHorizontal() ? stateIn.with(SHAPE, getShapeProperty(stateIn, worldIn, currentPos)) : stateIn;
	}

	/**
	 * Returns a plate shape property based on the surrounding plates from the given blockstate and position
	 */
	private static StairsShape getShapeProperty(BlockState state, IBlockReader worldIn, BlockPos pos) {
		Direction direction = state.get(FACING);

		BlockState adjacentState = worldIn.getBlockState(pos.offset(direction));
		if (isBlockPlate(adjacentState)) {
			Direction adjacentDirection = adjacentState.get(FACING);
			if (adjacentDirection.getAxis() != state.get(FACING).getAxis() && isDifferentPlate(state, worldIn, pos, adjacentDirection.getOpposite())) {
				return adjacentDirection == direction.rotateYCCW() ? StairsShape.OUTER_LEFT : StairsShape.OUTER_RIGHT;
			}
		}

		adjacentState = worldIn.getBlockState(pos.offset(direction.getOpposite()));
		if (isBlockPlate(adjacentState)) {
			Direction adjacentDirection = adjacentState.get(FACING);
			if (adjacentDirection.getAxis() != state.get(FACING).getAxis() && isDifferentPlate(state, worldIn, pos, adjacentDirection)) {
				return adjacentDirection == direction.rotateYCCW() ? StairsShape.INNER_LEFT : StairsShape.INNER_RIGHT;
			}
		}

		return StairsShape.STRAIGHT;
	}

	private static boolean isDifferentPlate(BlockState state, IBlockReader worldIn, BlockPos pos, Direction face) {
		BlockState adjacentState = worldIn.getBlockState(pos.offset(face));
		return !isBlockPlate(adjacentState) || adjacentState.get(FACING) != state.get(FACING);
	}

	public static boolean isBlockPlate(BlockState state) {
		return state.getBlock() instanceof PlateBlock;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(FACING, rot.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		Direction direction = state.get(FACING);
		StairsShape stairsshape = state.get(SHAPE);
		switch(mirrorIn) {
			case LEFT_RIGHT:
				if (direction.getAxis() == Direction.Axis.Z) {
					switch(stairsshape) {
						case INNER_LEFT:
							return state.rotate(Rotation.CLOCKWISE_180).with(SHAPE, StairsShape.INNER_RIGHT);
						case INNER_RIGHT:
							return state.rotate(Rotation.CLOCKWISE_180).with(SHAPE, StairsShape.INNER_LEFT);
						case OUTER_LEFT:
							return state.rotate(Rotation.CLOCKWISE_180).with(SHAPE, StairsShape.OUTER_RIGHT);
						case OUTER_RIGHT:
							return state.rotate(Rotation.CLOCKWISE_180).with(SHAPE, StairsShape.OUTER_LEFT);
						default:
							return state.rotate(Rotation.CLOCKWISE_180);
					}
				}
				break;
			case FRONT_BACK:
				if (direction.getAxis() == Direction.Axis.X) {
					switch(stairsshape) {
						case INNER_LEFT:
							return state.rotate(Rotation.CLOCKWISE_180).with(SHAPE, StairsShape.INNER_LEFT);
						case INNER_RIGHT:
							return state.rotate(Rotation.CLOCKWISE_180).with(SHAPE, StairsShape.INNER_RIGHT);
						case OUTER_LEFT:
							return state.rotate(Rotation.CLOCKWISE_180).with(SHAPE, StairsShape.OUTER_RIGHT);
						case OUTER_RIGHT:
							return state.rotate(Rotation.CLOCKWISE_180).with(SHAPE, StairsShape.OUTER_LEFT);
						case STRAIGHT:
							return state.rotate(Rotation.CLOCKWISE_180);
					}
				}
		}

		return super.mirror(state, mirrorIn);
	}

	@Override
	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		return false;
	}
}