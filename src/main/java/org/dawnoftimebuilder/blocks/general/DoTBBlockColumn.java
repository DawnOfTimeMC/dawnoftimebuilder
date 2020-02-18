package org.dawnoftimebuilder.blocks.general;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;

import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import org.dawnoftimebuilder.utils.DoTBBlockStateProperties;

public abstract class DoTBBlockColumn extends DoTBBlock implements IWaterLoggable {

	public static final EnumProperty<DoTBBlockStateProperties.VerticalConnection> VERTICAL_CONNECTION = DoTBBlockStateProperties.VERTICAL_CONNECTION;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public DoTBBlockColumn(String name, Material materialIn, float hardness, float resistance) {
		this(name, Block.Properties.create(materialIn).hardnessAndResistance(hardness, resistance));
	}

	public DoTBBlockColumn(String name, Properties properties) {
		super(name, properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(VERTICAL_CONNECTION, DoTBBlockStateProperties.VerticalConnection.NONE).with(WATERLOGGED,false));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(VERTICAL_CONNECTION, WATERLOGGED);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return this.getShapeFromIndex(state.get(VERTICAL_CONNECTION).getIndex());
	}

	/**
	 * @return Stores VoxelShape with index : <p/>
	 * 0 : None <p/>
	 * 1 : Under <p/>
	 * 2 : Above <p/>
	 * 3 : Both <p/>
	 */
	public abstract VoxelShape getShapeFromIndex(int index);

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.get(WATERLOGGED)) worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		return stateIn.with(VERTICAL_CONNECTION, this.getColumnState(worldIn, currentPos));
	}

	@Override
	public IFluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	public DoTBBlockStateProperties.VerticalConnection getColumnState(IWorld worldIn, BlockPos pos){
		if(isSameColumn(worldIn, pos.up())){
			return (isSameColumn(worldIn, pos.down())) ? DoTBBlockStateProperties.VerticalConnection.BOTH : DoTBBlockStateProperties.VerticalConnection.ABOVE;
		}else{
			return (isSameColumn(worldIn, pos.down())) ? DoTBBlockStateProperties.VerticalConnection.UNDER : DoTBBlockStateProperties.VerticalConnection.NONE;
		}
	}

	public boolean isSameColumn(IWorld worldIn, BlockPos pos){
		return worldIn.getBlockState(pos).getBlock() instanceof DoTBBlockColumn;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(WATERLOGGED, context.getWorld().getFluidState(context.getPos()).getFluid() == Fluids.WATER);
	}
}