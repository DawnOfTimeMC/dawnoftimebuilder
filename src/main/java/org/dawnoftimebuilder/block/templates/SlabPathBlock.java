package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import org.dawnoftimebuilder.utils.DoTBBlockStateProperties;

public class SlabPathBlock extends SlabBlockDoTB {

	private static final BooleanProperty FULL = DoTBBlockStateProperties.FULL;

	static final VoxelShape VS_PATH_BOTTOM = BlockDoTB.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D);
	static final VoxelShape VS_PATH_TOP = BlockDoTB.makeCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 15.0D, 16.0D);
	static final VoxelShape VS_PATH_DOUBLE = BlockDoTB.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);

	public SlabPathBlock(String name) {
		super(name, Material.EARTH, 0.65F, 0.65F);
		this.setDefaultState(this.getStateContainer().getBaseState().with(TYPE, SlabType.BOTTOM).with(FULL, false));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(FULL);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		if(!state.get(FULL)){
			switch (state.get(TYPE)) {
				default:
				case BOTTOM:
					return VS_PATH_BOTTOM;
				case TOP:
					return VS_PATH_TOP;
				case DOUBLE:
					return VS_PATH_DOUBLE;
			}
		}else return super.getShape(state, worldIn, pos, context);
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
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState state = super.getStateForPlacement(context);
		if(state == null) state = this.getDefaultState();
		return isFull(state, context.getWorld(), context.getPos()) ? state.with(FULL, true).with(WATERLOGGED, false) : state.with(FULL, false);
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		stateIn = super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		if(facing == Direction.UP){
			if(isFull(stateIn, worldIn, currentPos)) return stateIn.with(FULL, true).with(WATERLOGGED, false);
			else stateIn = stateIn.with(FULL, false);
		}
		return stateIn;
	}

	private static boolean isFull(BlockState state, IWorld worldIn, BlockPos pos) {
		if(state.get(TYPE) == SlabType.BOTTOM) return false;
		Block block = worldIn.getBlockState(pos.up()).getBlock();
		return !(block instanceof AirBlock || block instanceof LeavesBlock);
	}
}
