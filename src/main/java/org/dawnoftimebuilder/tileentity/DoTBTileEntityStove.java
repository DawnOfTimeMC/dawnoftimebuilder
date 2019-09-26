package org.dawnoftimebuilder.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dawnoftimebuilder.blocks.japanese.BlockIrori;

public class DoTBTileEntityStove extends DoTBTileEntityBase {

	private ItemStack content = ItemStack.EMPTY;

	public ItemStack getItemStack() {
		return this.content;
	}

	public void putItemStack(Item item, int meta){
		this.content = new ItemStack(item, 1, meta);
	}

	public void removeItemStack(){
		this.content = ItemStack.EMPTY;
	}

	public boolean isEmpty() {
		return this.content == ItemStack.EMPTY;
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return !(oldState.getBlock() instanceof BlockIrori && newSate.getBlock() instanceof BlockIrori);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.content = new ItemStack(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		this.content.writeToNBT(compound);
		return compound;
	}
}
