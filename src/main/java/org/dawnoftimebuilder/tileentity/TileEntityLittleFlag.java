package org.dawnoftimebuilder.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import static org.dawnoftimebuilder.items.DoTBItems.little_flag;

public class TileEntityLittleFlag extends DoTBTileEntityBase{

	private int color = 0xFFFFFF;
	
	public TileEntityLittleFlag(){}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		this.setItemColor(nbt.getInteger("Color"));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		nbt.setInteger("Color", this.color);
		return nbt;
	}
	
	public void setItemColor(int color){
		this.color = color;
	}
	
	public ItemStack getItem(){
		ItemStack stack = new ItemStack(little_flag);
		stack.getOrCreateSubCompound("BlockEntityTag").setInteger("Color", this.color);
		return stack;
	}

	public int getItemColor() {
		return this.color;
	}
}
