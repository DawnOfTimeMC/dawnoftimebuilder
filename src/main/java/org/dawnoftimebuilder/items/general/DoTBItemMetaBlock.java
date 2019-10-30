package org.dawnoftimebuilder.items.general;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import org.dawnoftimebuilder.blocks.IBlockMeta;

public class DoTBItemMetaBlock extends ItemBlock {

	private IBlockMeta metaBlock;
	
	public DoTBItemMetaBlock(IBlockMeta block) {
		super(block.getBlock());
		
		this.metaBlock = block;
		
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
	}
	
    /**
     * Converts the given ItemStack damage value into a metadata value to be placed in the world when this Item is
     * placed as a Block (mostly used with ItemBlocks).
     */
	@Override
    public int getMetadata(int damage){
        return damage;
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
	@Override
	public String getTranslationKey(ItemStack stack){
		if(stack.getItemDamage() >= this.metaBlock.getVariants().length) return super.getTranslationKey(stack);
		return super.getTranslationKey(stack) + "_" + this.metaBlock.getVariants()[stack.getItemDamage()].getName();
	}
}
