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
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import org.dawnoftimebuilder.enums.DoTBBlockStateProperties;

public class DoTBBlockSupportBeam extends DoTBBlock {

	private static final EnumProperty<DoTBBlockStateProperties.PillarConnection> PILLAR_CONNECTION = DoTBBlockStateProperties.PILLAR_CONNECTION;
	public static final EnumProperty<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;
	private static final BooleanProperty SUBAXIS = DoTBBlockStateProperties.SUBAXIS;
	private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	private static final VoxelShape[] SHAPES = makeShapes();

	public DoTBBlockSupportBeam(String name, Material materialIn, float hardness, float resistance){
		super(name, materialIn, hardness, resistance);
		this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_AXIS, Direction.Axis.X).with(SUBAXIS, false).with(PILLAR_CONNECTION, DoTBBlockStateProperties.PillarConnection.NOTHING));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(PILLAR_CONNECTION, HORIZONTAL_AXIS, SUBAXIS, WATERLOGGED);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		int index = 0;
		switch (state.get(PILLAR_CONNECTION)) {
			case FOUR_PX:
				index = 3;
				break;
			case EIGHT_PX:
				index = 6;
				break;
			case TEN_PX:
				index = 9;
				break;
			default:
		}
		if(state.get(SUBAXIS)) index += 2;
		else if(state.get(HORIZONTAL_AXIS) == Direction.Axis.Z) index++;
		return SHAPES[index];
	}

	/**
	 * @return Stores VoxelShape with index : <p/>
	 * 0 : Axis X <p/>
	 * 1 : Axis Z <p/>
	 * 2 : Axis X + Z <p/>
	 * 3 : Axis X + 4px <p/>
	 * 4 : Axis Z + 4px <p/>
	 * 5 : Axis X + Z + 4px <p/>
	 * 6 : Axis X + 8px <p/>
	 * 7 : Axis Z + 8px <p/>
	 * 8 : Axis X + Z + 8px <p/>
	 * 9 : Axis X + 10px <p/>
	 * 10 : Axis Z + 10px <p/>
	 * 11 : Axis X + Z + 10px <p/>
	 */
	private static VoxelShape[] makeShapes() {
		VoxelShape vs = Block.makeCuboidShape(0.0D, 12.0D, 0.0D, 16.0D, 16.0D, 16.0D);
		VoxelShape vs_axis_x = VoxelShapes.or(vs, Block.makeCuboidShape(0.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D));
		VoxelShape vs_axis_z = VoxelShapes.or(vs, Block.makeCuboidShape(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 16.0D));
		VoxelShape vs_axis_x_z = VoxelShapes.or(vs_axis_x, Block.makeCuboidShape(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 16.0D));
		VoxelShape vs_axis_4px = Block.makeCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);
		VoxelShape vs_axis_8px = Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
		VoxelShape vs_axis_10px = Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);
		return new VoxelShape[]{
				vs_axis_x,
				vs_axis_z,
				vs_axis_x_z,
				VoxelShapes.or(vs_axis_x, vs_axis_4px),
				VoxelShapes.or(vs_axis_z, vs_axis_4px),
				VoxelShapes.or(vs_axis_x_z, vs_axis_4px),
				VoxelShapes.or(vs_axis_x, vs_axis_8px),
				VoxelShapes.or(vs_axis_z, vs_axis_8px),
				VoxelShapes.or(vs_axis_x_z, vs_axis_8px),
				VoxelShapes.or(vs_axis_x, vs_axis_10px),
				VoxelShapes.or(vs_axis_z, vs_axis_10px),
				VoxelShapes.or(vs_axis_x_z, vs_axis_10px)
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
		return this.getDefaultState().with(HORIZONTAL_AXIS, context.getPlacementHorizontalFacing().getAxis()).with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.get(WATERLOGGED)) worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		if (stateIn.get(HORIZONTAL_AXIS) == Direction.Axis.X){
			if (canConnect(worldIn, currentPos, Direction.NORTH) || canConnect(worldIn, currentPos, Direction.SOUTH)) stateIn = stateIn.with(SUBAXIS, true);
		}else{
			if (canConnect(worldIn, currentPos, Direction.EAST) || canConnect(worldIn, currentPos, Direction.WEST)) stateIn = stateIn.with(SUBAXIS, true);
		}
		Block blockUnder = worldIn.getBlockState(currentPos.down()).getBlock();
		if(blockUnder instanceof FenceBlock) return stateIn.with(PILLAR_CONNECTION, DoTBBlockStateProperties.PillarConnection.FOUR_PX);
		if(blockUnder instanceof WallBlock) stateIn.with(PILLAR_CONNECTION, DoTBBlockStateProperties.PillarConnection.EIGHT_PX);
		if(blockUnder instanceof DoTBBlockBeam) if(worldIn.getBlockState(currentPos.down()).get(DoTBBlockBeam.MAIN_AXIS).isVertical()) stateIn.with(PILLAR_CONNECTION, DoTBBlockStateProperties.PillarConnection.TEN_PX);

		return stateIn;
	}

	private boolean canConnect(IWorld world, BlockPos pos, Direction direction) {
		BlockState state = world.getBlockState(pos.offset(direction));
		return isConnectibleBeam(state, direction) || isConnectibleSupportBeam(state, direction) || isConnectibleBeam(state, direction) || isConnectibleSupportBeam(state, direction);
	}

	private boolean isConnectibleBeam(BlockState state, Direction direction) {
		if (state.getBlock() instanceof DoTBBlockBeam)
			return state.get(DoTBBlockBeam.MAIN_AXIS) == direction.getAxis() || state.get(DoTBBlockBeam.MAIN_AXIS) == Direction.Axis.Y;
		else return false;
	}

	private boolean isConnectibleSupportBeam(BlockState state, Direction direction) {
		if (state.getBlock() instanceof DoTBBlockSupportBeam)
			return state.get(DoTBBlockSupportBeam.HORIZONTAL_AXIS) == direction.getAxis();
		else return false;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		if(rot == Rotation.CLOCKWISE_90 || rot == Rotation.COUNTERCLOCKWISE_90) return state.with(HORIZONTAL_AXIS, (state.get(HORIZONTAL_AXIS) == Direction.Axis.X) ? Direction.Axis.Z : Direction.Axis.X);
		else return super.rotate(state, rot);
	}
}