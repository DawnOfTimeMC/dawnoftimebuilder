package org.dawnoftimebuilder.block.japanese;

import org.dawnoftimebuilder.block.ICustomBlockItem;
import org.dawnoftimebuilder.block.templates.BushBlockDoT;
import org.dawnoftimebuilder.registry.DoTBBlocksRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PausedMapleSaplingBlock extends BushBlockDoT implements ICustomBlockItem {
	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);

	public PausedMapleSaplingBlock(final Properties properties) {
		super(properties);
	}

	@Override
	public VoxelShape getShape(final BlockState p_220053_1_In, final IBlockReader p_220053_2_In, final BlockPos p_220053_3_In, final ISelectionContext p_220053_4_In) {
		return PausedMapleSaplingBlock.SHAPE;
	}

	@Override
	public Item getCustomBlockItem() {
		return null;
	}

	@Override
	public ItemStack getPickBlock(final BlockState stateIn, final RayTraceResult targetIn, final IBlockReader worldIn, final BlockPos posIn, final PlayerEntity playerIn) {
		return new ItemStack(DoTBBlocksRegistry.MAPLE_RED_SAPLING.get().asItem());
	}

	@Override
	public ActionResultType use(final BlockState p_225533_1_In, final World p_225533_2_In, final BlockPos p_225533_3_In, final PlayerEntity p_225533_4_In, final Hand p_225533_5_In, final BlockRayTraceResult p_225533_6_In) {
		if (p_225533_4_In.getMainHandItem().getItem() instanceof FlintAndSteelItem) {
			p_225533_2_In.setBlock(p_225533_3_In, DoTBBlocksRegistry.MAPLE_RED_SAPLING.get().defaultBlockState(), 35);
			p_225533_2_In.levelEvent(p_225533_4_In, 2001, p_225533_3_In, Block.getId(p_225533_1_In));

			return ActionResultType.SUCCESS;
		}

		return ActionResultType.FAIL;
	}

	/**
	 * Lights methods
	 */

	@Override
	public boolean useShapeForLightOcclusion(final BlockState p_220074_1_In) {
		return false;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public float getShadeBrightness(final BlockState p_220080_1_, final IBlockReader p_220080_2_, final BlockPos p_220080_3_) {
		return 1.0F;
	}

	@Override
	public boolean propagatesSkylightDown(final BlockState p_200123_1_, final IBlockReader p_200123_2_, final BlockPos p_200123_3_) {
		return true;
	}
}
