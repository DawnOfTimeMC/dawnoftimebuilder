package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BottomPaneBlockDoTB extends PaneBlockDoTB {
	private static final BooleanProperty BOTTOM = BlockStateProperties.BOTTOM;

	public BottomPaneBlockDoTB(final Properties propertiesIn) {
		super(propertiesIn);
		this.registerDefaultState(this.defaultBlockState().setValue(BOTTOM, true));
	}

	@Override
	protected void createBlockStateDefinition(final StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(BOTTOM);
	}

	@Override
	public BlockState getStateForPlacement(final BlockItemUseContext contextIn) {
		World world = contextIn.getLevel();
		BlockPos pos = contextIn.getClickedPos();
		BlockState state = super.getStateForPlacement(contextIn);
		if(state != null){
			state = state.setValue(BOTTOM, !(world.getBlockState(pos.below()).getBlock() instanceof BottomPaneBlockDoTB));
		}
		return state;
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		if(facing == Direction.DOWN){
			stateIn = stateIn.setValue(BOTTOM, !(facingState.getBlock() instanceof BottomPaneBlockDoTB));
		}
		return stateIn;
	}
}