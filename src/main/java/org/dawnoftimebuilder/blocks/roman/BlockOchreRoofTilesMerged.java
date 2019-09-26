package org.dawnoftimebuilder.blocks.roman;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import org.dawnoftimebuilder.blocks.DoTBBlocks;
import org.dawnoftimebuilder.blocks.IBlockCustomItem;
import org.dawnoftimebuilder.blocks.general.DoTBBlock;

public class BlockOchreRoofTilesMerged extends DoTBBlock implements IBlockCustomItem {
	public BlockOchreRoofTilesMerged() {
		super("ochre_roof_tiles_merged", Material.ROCK, 2.0F, SoundType.STONE);
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
		drops.add(new ItemStack(Item.getItemFromBlock(Blocks.STONE_SLAB), 1, 1));
		drops.add(new ItemStack(Item.getItemFromBlock(DoTBBlocks.ochre_roof_tiles_slab), 1));
	}
}
