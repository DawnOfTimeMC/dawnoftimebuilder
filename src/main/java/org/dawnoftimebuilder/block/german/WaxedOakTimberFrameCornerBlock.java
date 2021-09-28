package org.dawnoftimebuilder.block.german;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import org.dawnoftimebuilder.block.templates.BlockDoTB;

public class WaxedOakTimberFrameCornerBlock extends BlockDoTB {

	public static final BooleanProperty INVERTED = BlockStateProperties.INVERTED;

	public WaxedOakTimberFrameCornerBlock(Material materialIn, float hardness, float resistance, SoundType soundType) {
		super(Properties.of(materialIn).strength(hardness, resistance).sound(soundType));
		this.registerDefaultState(this.defaultBlockState().setValue(INVERTED, false));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(INVERTED);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockPos pos = context.getClickedPos();
		int coordinateSum = (pos.getX() + pos.getY() + pos.getZ() ) % 2;
		return this.defaultBlockState().setValue(INVERTED, coordinateSum != 0);
	}
}
