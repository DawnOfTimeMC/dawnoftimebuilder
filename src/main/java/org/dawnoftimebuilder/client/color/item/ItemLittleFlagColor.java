package org.dawnoftimebuilder.client.color.item;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemLittleFlagColor implements IItemColor{

	private int getItemColor(ItemStack item){
		
		NBTTagCompound nbt = item.getSubCompound("BlockEntityTag");
		
		if(nbt != null)
			return nbt.getInteger("Color");
		else{
			return 0xFFFFFF;
		}
	}

	@Override
	public int colorMultiplier(ItemStack stack, int tintIndex) {
		return this.getItemColor(stack);
	}
}
