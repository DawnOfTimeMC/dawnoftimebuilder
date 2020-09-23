package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import org.dawnoftimebuilder.utils.DoTBBlockStateProperties;

public class PathBlock extends WaterloggedBlock {

	private static final BooleanProperty FULL = DoTBBlockStateProperties.FULL;
	private static final VoxelShape PATH_VS = net.minecraft.block.Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);

	public PathBlock() {
		super(Material.EARTH, 0.65F, 0.65F);
		this.setDefaultState(this.getStateContainer().getBaseState().with(FULL, false));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<net.minecraft.block.Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(FULL);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return (state.get(FULL) ? VoxelShapes.fullCube() : PATH_VS);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return isFull(context.getWorld(), context.getPos()) ? this.getDefaultState().with(FULL, true) : super.getStateForPlacement(context);
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		stateIn = super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		if(facing == Direction.UP) return isFull(worldIn, currentPos) ? stateIn.with(FULL, true).with(WATERLOGGED, false) : stateIn.with(FULL, false);
		return stateIn;
	}

	private static boolean isFull(IWorld worldIn, BlockPos pos) {
		Block block = worldIn.getBlockState(pos.up()).getBlock();
		return !(block instanceof AirBlock || block instanceof LeavesBlock);
	}

	@Override
	public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, IFluidState fluidStateIn) {
		if(state.get(FULL)) return false;
		return super.receiveFluid(worldIn, pos, state, fluidStateIn);
	}

	@Override
	public boolean canContainFluid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
		if(state.get(FULL)) return false;
		return super.canContainFluid(worldIn, pos, state, fluidIn);
	}

	@Override
	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		return false;
	}
}