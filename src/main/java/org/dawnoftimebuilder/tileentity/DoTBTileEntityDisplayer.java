package org.dawnoftimebuilder.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.NonNullList;
import org.dawnoftimebuilder.inventory.ContainerDisplayer;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class DoTBTileEntityDisplayer extends TileEntityLockableLoot {

	private NonNullList<ItemStack> displayerContents = NonNullList.withSize(9, ItemStack.EMPTY);

	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.displayerContents;
	}

	@Override
	public int getSizeInventory() {
		return 9;
	}

	@Override
	public boolean isEmpty() {
		for(ItemStack stack : this.displayerContents) {
			if(!stack.isEmpty()) return false;
		}
		return true;
	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.customName : "container.displayer";
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerDisplayer(playerInventory, this, playerIn);
	}

	@Override
	public String getGuiID() {
		return MOD_ID + ":displayer";
	}

	@Override
	public NBTTagCompound getUpdateTag()
	{
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag)
	{
		readFromNBT(tag);
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		return new SPacketUpdateTileEntity(this.pos, 0, getUpdateTag());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		super.onDataPacket(net, packet);
		handleUpdateTag(packet.getNbtCompound());
	}

	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);

		this.displayerContents = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);

		if(!this.checkLootAndRead(compound)) ItemStackHelper.loadAllItems(compound, displayerContents);
		if(compound.hasKey("CustomName", 8)) this.customName = compound.getString("CustomName");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);

		if(!checkLootAndWrite(compound)) ItemStackHelper.saveAllItems(compound, displayerContents);
		if(compound.hasKey("CustomName",8)) compound.setString("CustomName", this.customName);

		return compound;
	}
}
