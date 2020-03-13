package org.dawnoftimebuilder.blocks.general;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraftforge.common.PlantType;

public class DoTBBlockWaterDoubleCrops extends DoTBBlockDoubleCrops implements IWaterLoggable {

	private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public DoTBBlockWaterDoubleCrops(String name, String seedName, int growingAge) {
		super(name, seedName, PlantType.Water, growingAge);
		this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false));
	}

	public DoTBBlockWaterDoubleCrops(String name, int growingAge) {
		this(name, name, growingAge);
	}

	/**
	 * @return Stores VoxelShape with index : <p/>
	 * 0 : 8px <p/>
	 * 1 : 12px <p/>
	 * 2 : 16px <p/>
	 * 3 : 1px <p/>
	 * 4 : 5px <p/>
	 * 5 : 8px <p/>
	 * 6 : 11px <p/>
	 * 7 : 14px <p/>
	 * 8 : 16px <p/>
	 */
	@Override
	public VoxelShape[] makeShapes() {
		return new VoxelShape[]{
				Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
				Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
				Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
				Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D),
				Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D),
				Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
				Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 11.0D, 16.0D),
				Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
				Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)
		};
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HALF, AGE, WATERLOGGED);
	}

	@Override
	public IFluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockPos pos = context.getPos();
		IWorld world = context.getWorld();
		return this.getDefaultState().with(WATERLOGGED, world.getFluidState(pos).getFluid() == Fluids.WATER);
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.get(WATERLOGGED)) worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}
}
