/*
package org.dawnoftimebuilder.tileentity;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.dawnoftimebuilder.container.DisplayerContainer;

import javax.annotation.Nullable;

import static org.dawnoftimebuilder.registries.DoTBTileEntitiesRegistry.DISPLAYER_TE;

public class DisplayerTileEntity extends LockableLootTileEntity {

	private NonNullList<ItemStack> displayerContents = NonNullList.withSize(9, ItemStack.EMPTY);

	public DisplayerTileEntity() {
		super(DISPLAYER_TE);
	}

	@Override
	public NonNullList<ItemStack> getItems() {
		return this.displayerContents;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> itemsIn) {
		this.displayerContents = itemsIn;
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
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent(this.getBlockState().getBlock().getTranslationKey());
	}

	@Override
	protected Container createMenu(int id, PlayerInventory inventory) {
		return new DisplayerContainer(id, inventory, this);
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		if(!this.checkLootAndWrite(compound)) ItemStackHelper.saveAllItems(compound, this.displayerContents);
		return compound;
	}

	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
		this.displayerContents = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		if(!this.checkLootAndRead(compound)) ItemStackHelper.loadAllItems(compound, this.displayerContents);
	}

	@Nullable
	@Override
	public SUpdateTileEntityPacket getUpdatePacket(){
		CompoundNBT compound = new CompoundNBT();
		ItemStackHelper.saveAllItems(compound, this.displayerContents);
		return new SUpdateTileEntityPacket(this.pos, 0, compound);
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		CompoundNBT compound = pkt.getNbtCompound();
		ItemStackHelper.loadAllItems(compound, this.displayerContents);
	}

	@Override
	public CompoundNBT getUpdateTag() {
		return this.write(new CompoundNBT());
	}

	public void receiveMessageFromServer(NonNullList<ItemStack> stacks) {
		this.displayerContents = stacks;
	}
}
*/