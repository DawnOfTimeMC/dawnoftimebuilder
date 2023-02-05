/**
 *
 */
package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

/**
 * @author seyro
 *
 */
public class BottomPaneBlockDoTB extends BlockDoTB {
	private final BooleanProperty BOTTOM = BlockStateProperties.BOTTOM;

	/**
	 * @param propertiesIn
	 */
	public BottomPaneBlockDoTB(final Properties propertiesIn) {
		super(propertiesIn);
	}

	@Override
	protected void createBlockStateDefinition(final StateContainer.Builder<Block, BlockState> stateContainerBuilderIn) {
		super.createBlockStateDefinition(stateContainerBuilderIn.add(this.BOTTOM));
	}

	@Override
	public BlockState getStateForPlacement(final BlockItemUseContext contextIn)
	{
	      final BlockPos blockpos = contextIn.getClickedPos();
	      final BlockState blockstate = this.defaultBlockState().setValue(this.BOTTOM, true);
	      final Direction direction = contextIn.getClickedFace();
	      return direction != Direction.DOWN && (direction == Direction.UP || (contextIn.getClickLocation().y - blockpos.getY() <= 0.5D)) ? blockstate : blockstate.setValue(this.BOTTOM, false);
	}
}