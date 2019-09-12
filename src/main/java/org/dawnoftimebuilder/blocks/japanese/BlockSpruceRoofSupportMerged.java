package org.dawnoftimebuilder.blocks.japanese;

import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.dawnoftimebuilder.blocks.DoTBBlocks;

import java.util.Random;

public class BlockSpruceRoofSupportMerged extends BlockSpruceRoofSupport {

	public static final AxisAlignedBB AABB_FULL = new AxisAlignedBB(0.0D, 0.1875D, 0.0D, 1.0D, 1.0D, 1.0D);

	public BlockSpruceRoofSupportMerged() {
		super("spruce_roof_support_merged");
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB_FULL;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing facing) {
		return (facing == EnumFacing.UP) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
	}

	@Override
	protected boolean canSilkHarvest()
	{
		return false;
	}

	@Override
	public Item getCustomItemBlock() {
		return null;
	}

	@Override
	public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, net.minecraft.world.IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		drops.add(new ItemStack(Item.getItemFromBlock(DoTBBlocks.spruce_roof_support), 1));
		drops.add(new ItemStack(Item.getItemFromBlock(DoTBBlocks.grey_roof_tiles_slab), 1));
	}
}
