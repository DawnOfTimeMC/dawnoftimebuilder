/**
 *
 */
package org.dawnoftimebuilder.block.german;

import org.dawnoftimebuilder.block.templates.BlockDoTB;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

/**
 * @author seyro
 *
 */
public class WaterJetBlock extends BlockDoTB {
	/**
	 * @param propertiesIn
	 */
	public WaterJetBlock(final Properties propertiesIn) {
		super(propertiesIn);
		this.registerDefaultState(this.defaultBlockState().setValue(DoTBBlockStateProperties.LATERAL, false));
	}

	@Override
	protected void createBlockStateDefinition(final StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(DoTBBlockStateProperties.LATERAL);
	}

	@Override
	public ActionResultType use(final BlockState p_225533_1_In, final World p_225533_2_In, final BlockPos p_225533_3_In, final PlayerEntity p_225533_4_In, final Hand p_225533_5_In, final BlockRayTraceResult p_225533_6_In) {
		if (!p_225533_2_In.isClientSide()) {
			p_225533_1_In.setValue(DoTBBlockStateProperties.LATERAL, !p_225533_1_In.getValue(DoTBBlockStateProperties.LATERAL));
		}

		return super.use(p_225533_1_In, p_225533_2_In, p_225533_3_In, p_225533_4_In, p_225533_5_In, p_225533_6_In);
	}
}