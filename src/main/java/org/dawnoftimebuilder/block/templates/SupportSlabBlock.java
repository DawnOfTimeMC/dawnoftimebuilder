package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import org.dawnoftimebuilder.block.IBlockPillar;
import org.dawnoftimebuilder.utils.DoTBBlockStateProperties;

public class SupportSlabBlock extends WaterloggedBlock {

    private static final VoxelShape VS = makeCuboidShape(0.0D, 12.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	private static final VoxelShape VS_FOUR_PX = VoxelShapes.or(VS, makeCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D));
	private static final VoxelShape VS_EIGHT_PX = VoxelShapes.or(VS, makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D));
	private static final VoxelShape VS_TEN_PX = VoxelShapes.or(VS, makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D));

	private static final EnumProperty<DoTBBlockStateProperties.PillarConnection> PILLAR_CONNECTION = DoTBBlockStateProperties.PILLAR_CONNECTION;

	public SupportSlabBlock(Material materialIn, float hardness, float resistance) {
		super(materialIn, hardness, resistance);
		this.setDefaultState(this.getStateContainer().getBaseState().with(PILLAR_CONNECTION, DoTBBlockStateProperties.PillarConnection.NOTHING));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<net.minecraft.block.Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(PILLAR_CONNECTION);
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
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return super.getStateForPlacement(context).with(PILLAR_CONNECTION, IBlockPillar.getPillarConnectionAbove(context.getWorld(), context.getPos().down()));
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		stateIn = super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		return facing == Direction.DOWN ? stateIn.with(PILLAR_CONNECTION, IBlockPillar.getPillarConnectionAbove(worldIn, currentPos.down())) : stateIn;
	}
}