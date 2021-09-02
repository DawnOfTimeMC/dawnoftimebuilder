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
		super(Properties.create(materialIn).hardnessAndResistance(hardness, resistance).sound(soundType));
		this.setDefaultState(this.getDefaultState().with(INVERTED, false));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(INVERTED);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockPos pos = context.getPos();
		int coordinateSum = (pos.getX() + pos.getY() + pos.getZ() ) % 2;
		return this.getDefaultState().with(INVERTED, coordinateSum != 0);
	}
}
