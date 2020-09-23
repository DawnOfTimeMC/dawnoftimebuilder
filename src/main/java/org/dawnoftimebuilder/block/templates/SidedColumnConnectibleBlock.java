package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public abstract class SidedColumnConnectibleBlock extends ColumnConnectibleBlock {

	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

	public SidedColumnConnectibleBlock(Material materialIn, float hardness, float resistance) {
		this(Properties.create(materialIn).hardnessAndResistance(hardness, resistance));
	}

	public SidedColumnConnectibleBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.getStateContainer().getBaseState().with(FACING, Direction.NORTH));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return super.getStateForPlacement(context).with(FACING, context.getPlacementHorizontalFacing().getOpposite());
	}

	public boolean isConnectible(IWorld worldIn, BlockPos pos, BlockState stateIn){
		boolean isSameBlock = super.isConnectible(worldIn, pos, stateIn);
		if(!isSameBlock) return false;
		return worldIn.getBlockState(pos).get(FACING) == stateIn.get(FACING);
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(FACING, rot.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(Rotation.CLOCKWISE_180);
	}
}