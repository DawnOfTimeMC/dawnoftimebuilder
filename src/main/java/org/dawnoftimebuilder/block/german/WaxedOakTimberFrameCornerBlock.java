package org.dawnoftimebuilder.block.german;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.dawnoftimebuilder.block.templates.BlockDoTB;

public class WaxedOakTimberFrameCornerBlock extends BlockDoTB {

	public static final BooleanProperty INVERTED = BlockStateProperties.INVERTED;

	public WaxedOakTimberFrameCornerBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(INVERTED, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(INVERTED);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockPos pos = context.getClickedPos();
		int coordinateSum = (pos.getX() + pos.getY() + pos.getZ() ) % 2;
		return this.defaultBlockState().setValue(INVERTED, coordinateSum != 0);
	}
}
