package org.dawnoftimebuilder.blocks.general;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
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
import org.dawnoftimebuilder.blocks.IBlockPillar;
import org.dawnoftimebuilder.utils.DoTBBlockStateProperties;

public class DoTBBlockSupportSlab extends DoTBBlock implements IWaterLoggable {

    private static final VoxelShape VS = Block.makeCuboidShape(0.0D, 12.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	private static final VoxelShape VS_FOUR_PX = VoxelShapes.or(VS, Block.makeCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D));
	private static final VoxelShape VS_EIGHT_PX = VoxelShapes.or(VS, Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D));
	private static final VoxelShape VS_TEN_PX = VoxelShapes.or(VS, Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D));

	private static final EnumProperty<DoTBBlockStateProperties.PillarConnection> PILLAR_CONNECTION = DoTBBlockStateProperties.PILLAR_CONNECTION;
	private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public DoTBBlockSupportSlab(String name, Material materialIn, float hardness, float resistance) {
		super(name, materialIn, hardness, resistance);
		this.setDefaultState(this.stateContainer.getBaseState().with(PILLAR_CONNECTION, DoTBBlockStateProperties.PillarConnection.NOTHING).with(WATERLOGGED, false));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(PILLAR_CONNECTION, WATERLOGGED);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch (state.get(PILLAR_CONNECTION)) {
			case FOUR_PX:
				return VS_FOUR_PX;
			case EIGHT_PX:
				return VS_EIGHT_PX;
			case TEN_PX:
				return VS_TEN_PX;
			default:
				return VS;
		}
	}

	@Override
	public IFluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockPos pos = context.getPos();
		IFluidState ifluidstate = context.getWorld().getFluidState(pos);
		return this.getDefaultState().with(PILLAR_CONNECTION, IBlockPillar.getPillarConnection(context.getWorld(), pos.down())).with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.get(WATERLOGGED)) worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		return stateIn.with(PILLAR_CONNECTION, IBlockPillar.getPillarConnection(worldIn, currentPos.down()));
	}
}