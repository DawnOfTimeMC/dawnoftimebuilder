package org.dawnoftimebuilder.blocks.global;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class DoTBBlock extends Block {

	public DoTBBlock(String name, Material materialIn) {
		super(materialIn);

		this.setRegistryName(MOD_ID, name);
		this.setTranslationKey(MOD_ID + "." + name);
		this.setCreativeTab(DOTB_TAB);
	}

	public DoTBBlock(String name, Material materialIn, float hardness, SoundType sound){
		this(name, materialIn);

		this.setHardness(hardness);
		this.setSoundType(sound);
	}

	public Block setBurnable() {
		Blocks.FIRE.setFireInfo(this, 5, 20);
		return this;
	}
/*
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		Random rand = world instanceof World ? ((World)world).rand : RANDOM;
		Item item = this.getItemDropped(state, rand, fortune);
		ItemStack stack = getItemStackDropped(world, pos, item);

		if(stack == ItemStack.EMPTY) {
			super.getDrops(drops, world, pos, state, fortune);
			return;
		}

		int count = quantityDropped(state, fortune, rand);
		for (int i = 0; i < count; i++) {
			if (item != Items.AIR) {
				drops.add(stack);
			}
		}
	}

	public ItemStack getItemStackDropped(IBlockAccess world, BlockPos pos, Item item) {
		return ItemStack.EMPTY;
	}*/
}
