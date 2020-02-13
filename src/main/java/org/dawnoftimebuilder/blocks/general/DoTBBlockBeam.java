package org.dawnoftimebuilder.blocks.general;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import org.dawnoftimebuilder.enums.DoTBBlockStateProperties;

public class DoTBBlockBeam extends DoTBBlock implements IWaterLoggable {

	private static final BooleanProperty BOTTOM = BlockStateProperties.BOTTOM;
	public static final EnumProperty<Direction.Axis> MAIN_AXIS = BlockStateProperties.AXIS;
	private static final BooleanProperty SUBAXIS_X = DoTBBlockStateProperties.SUBAXIS_X;
	private static final BooleanProperty SUBAXIS_Z = DoTBBlockStateProperties.SUBAXIS_Z;
	private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	private static final VoxelShape[] SHAPES = makeShapes();

	public DoTBBlockBeam(String name, Material materialIn, float hardness, float resistance) {
		super(name, materialIn, hardness, resistance);
		this.setDefaultState(this.stateContainer.getBaseState().with(BOTTOM, false).with(MAIN_AXIS, Direction.Axis.Y).with(SUBAXIS_X, false).with(SUBAXIS_Z, false).with(WATERLOGGED, false));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(BOTTOM, MAIN_AXIS, SUBAXIS_X, SUBAXIS_Z, WATERLOGGED);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch (state.get(MAIN_AXIS)) {
			case Z:
				return SHAPES[state.get(SUBAXIS_X) ? 2 : 1];
			case X:
				return SHAPES[state.get(SUBAXIS_Z) ? 2 : 0];
			default:
				break;
		}
		int index = 3;
		if (state.get(BOTTOM)) index += 1;
		if (state.get(SUBAXIS_X)) index += 2;
		if (state.get(SUBAXIS_Z)) index += 4;
		return SHAPES[index];
	}

	/**
	 * @return Stores VoxelShape with index : <p/>
	 * 0 : Axis X <p/>
	 * 1 : Axis Z <p/>
	 * 2 : Axis X + Z <p/>
	 * 3 : Axis Y <p/>
	 * 4 : Axis Y + Bottom <p/>
	 * 5 : Axis Y + X <p/>
	 * 6 : Axis Y + X + Bottom <p/>
	 * 7 : Axis Y + Z <p/>
	 * 8 : Axis Y + Z + Bottom <p/>
	 * 9 : Axis Y + X + Z <p/>
	 * 10 : Axis Y + X + Z + Bottom
	 */
	private static VoxelShape[] makeShapes() {
		VoxelShape vs_axis_x = Block.makeCuboidShape(0.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D);
		VoxelShape vs_axis_z = Block.makeCuboidShape(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 16.0D);
		VoxelShape vs_axis_x_z = VoxelShapes.or(vs_axis_x, vs_axis_z);
		VoxelShape vs_axis_y = Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);
		VoxelShape vs_axis_y_bottom = VoxelShapes.or(vs_axis_y, Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D));
		return new VoxelShape[]{
				vs_axis_x,
				vs_axis_z,
				vs_axis_x_z,
				vs_axis_y,
				vs_axis_y_bottom,
				VoxelShapes.or(vs_axis_y, vs_axis_x),
				VoxelShapes.or(vs_axis_y_bottom, vs_axis_x),
				VoxelShapes.or(vs_axis_y, vs_axis_z),
				VoxelShapes.or(vs_axis_y_bottom, vs_axis_z),
				VoxelShapes.or(vs_axis_y, vs_axis_x_z),
				VoxelShapes.or(vs_axis_y_bottom, vs_axis_x_z)
		};
	}

	@Override
	public IFluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockPos pos = context.getPos();
		IFluidState ifluidstate = context.getWorld().getFluidState(pos);
		return this.getCurrentState(this.getDefaultState().with(MAIN_AXIS, context.getFace().getAxis()), context.getWorld(), pos).with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.get(WATERLOGGED)) worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		return this.getCurrentState(stateIn, worldIn, currentPos);
	}

	private BlockState getCurrentState(BlockState stateIn, IWorld worldIn, BlockPos pos){
		boolean subAxisX = false;
		boolean subAxisZ = false;
		if (canConnect(worldIn, pos, Direction.EAST) || canConnect(worldIn, pos, Direction.WEST)) subAxisX = true;
		if (canConnect(worldIn, pos, Direction.NORTH) || canConnect(worldIn, pos, Direction.SOUTH)) subAxisZ = true;
		if (stateIn.get(MAIN_AXIS).isHorizontal()){
			if(stateIn.get(MAIN_AXIS) == Direction.Axis.X) return stateIn.with(SUBAXIS_X, false).with(SUBAXIS_Z, subAxisZ);
			else return stateIn.with(SUBAXIS_X, subAxisX).with(SUBAXIS_Z, false);
		} else {
			boolean bottom = false;
			if (!isConnectibleBeam(worldIn.getBlockState(pos.down()), Direction.DOWN)) bottom = true;
			return stateIn.with(BOTTOM, bottom).with(SUBAXIS_X, subAxisX).with(SUBAXIS_Z, subAxisZ);
		}
	}

	private boolean canConnect(IWorld world, BlockPos pos, Direction direction) {
		BlockState state = world.getBlockState(pos.offset(direction));
		return isConnectibleBeam(state, direction) || isConnectibleSupportBeam(state, direction) || isConnectibleBeam(state, direction) || isConnectibleSupportBeam(state, direction);
	}

	private boolean isConnectibleBeam(BlockState state, Direction direction) {
		if (state.getBlock() instanceof DoTBBlockBeam)
			return state.get(MAIN_AXIS) == direction.getAxis() || state.get(MAIN_AXIS) == Direction.Axis.Y;
		else return false;
	}

	private boolean isConnectibleSupportBeam(BlockState state, Direction direction) {
		if (state.getBlock() instanceof DoTBBlockSupportBeam)
			return state.get(DoTBBlockSupportBeam.HORIZONTAL_AXIS) == direction.getAxis();
		else return false;
	}
}
