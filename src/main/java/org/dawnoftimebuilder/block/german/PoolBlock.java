/**
 *
 */
package org.dawnoftimebuilder.block.german;

import org.dawnoftimebuilder.block.templates.WaterloggedBlock;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

/**
 * @author seyro
 *
 */
public class PoolBlock extends WaterloggedBlock {
	/**
	 * @param propertiesIn
	 */
	public PoolBlock(final Properties propertiesIn) {
		super(propertiesIn);
		this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.NORTH, false).setValue(BlockStateProperties.EAST, false).setValue(BlockStateProperties.SOUTH, false).setValue(BlockStateProperties.WEST, false).setValue(DoTBBlockStateProperties.HAS_WALL, false).setValue(BlockStateProperties.WATERLOGGED, false));
	}

	@Override
	protected void createBlockStateDefinition(final StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(BlockStateProperties.NORTH).add(BlockStateProperties.EAST).add(BlockStateProperties.SOUTH).add(BlockStateProperties.WEST).add(DoTBBlockStateProperties.HAS_WALL).add(BlockStateProperties.WATERLOGGED);
	}

	@Override
	public ActionResultType use(final BlockState p_225533_1_In, final World p_225533_2_In, final BlockPos p_225533_3_In, final PlayerEntity p_225533_4_In, final Hand p_225533_5_In, final BlockRayTraceResult p_225533_6_In) {
		final ItemStack itemStack = p_225533_4_In.getMainHandItem();

		if (!p_225533_2_In.isClientSide() && itemStack != null && !(itemStack.getItem() instanceof BucketItem)) {
			p_225533_1_In.setValue(DoTBBlockStateProperties.HAS_WALL, !p_225533_1_In.getValue(DoTBBlockStateProperties.HAS_WALL));
		}

		return super.use(p_225533_1_In, p_225533_2_In, p_225533_3_In, p_225533_4_In, p_225533_5_In, p_225533_6_In);
	}
}