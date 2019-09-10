package org.dawnoftimebuilder.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.dawnoftimebuilder.tileentity.DoTBTileEntityDisplayer;

public class ContainerDisplayer extends Container {

	private final DoTBTileEntityDisplayer inventoryDisplayer;

	public ContainerDisplayer(InventoryPlayer playerInv, DoTBTileEntityDisplayer inventory, EntityPlayer player){
		this.inventoryDisplayer = inventory;
		inventory.openInventory(player);

		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				this.addSlotToContainer(new Slot(inventory, j + i * 3, 19 + j * 18, 168 + i * 18));
			}
		}

		for(int y = 0; y < 3; y++){
			for (int x = 0; x < 9; x++){
				this.addSlotToContainer(new Slot(playerInv, 9 + x + y * 9, 88 + x * 18, 157 + y * 18));
			}
		}

		for(int x = 0; x < 9; x++){
			this.addSlotToContainer(new Slot(playerInv, x, 88 + x * 18, 215));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.inventoryDisplayer.isUsableByPlayer(playerIn);
	}

	@Override
	public void onContainerClosed(EntityPlayer playerIn){
		super.onContainerClosed(playerIn);
		inventoryDisplayer.closeInventory(playerIn);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if(slot != null && slot.getHasStack()){
			ItemStack itemStack1 = slot.getStack();
			itemStack = itemStack1.copy();

			if(index < 9){
				if(!this.mergeItemStack(itemStack1, 9, this.inventorySlots.size(), true)) return ItemStack.EMPTY;
			}else if(!this.mergeItemStack(itemStack1, 0, 9, false)) return ItemStack.EMPTY;

			if(itemStack1.isEmpty()) slot.putStack(ItemStack.EMPTY);
			else slot.onSlotChanged();
		}
		return itemStack;
	}
}
