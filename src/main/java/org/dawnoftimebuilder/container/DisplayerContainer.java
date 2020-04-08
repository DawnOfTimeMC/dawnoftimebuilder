package org.dawnoftimebuilder.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import org.dawnoftimebuilder.tileentity.DisplayerTileEntity;

import javax.annotation.Nullable;

import static org.dawnoftimebuilder.registries.DoTBContainersRegistry.DISPLAYER_CONTAINER;

public class DisplayerContainer extends Container {

	private DisplayerTileEntity tileEntity;

	public DisplayerContainer(int windowId, PlayerInventory playerInventory, DisplayerTileEntity tileEntity) {
		super(DISPLAYER_CONTAINER, windowId);
		this.tileEntity = tileEntity;
		tileEntity.openInventory(playerInventory.player);

		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				this.addSlot(new Slot(tileEntity, j + i * 3, 19 + j * 18, 168 + i * 18));
			}
		}

		for(int y = 0; y < 3; y++){
			for (int x = 0; x < 9; x++){
				this.addSlot(new Slot(playerInventory, 9 + x + y * 9, 88 + x * 18, 157 + y * 18));
			}
		}

		for(int x = 0; x < 9; x++){
			this.addSlot(new Slot(playerInventory, x, 88 + x * 18, 215));
		}
	}



	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return this.tileEntity.isUsableByPlayer(playerIn);
	}

	@Override
	public void onContainerClosed(PlayerEntity playerIn){
		super.onContainerClosed(playerIn);
		tileEntity.closeInventory(playerIn);
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if(slot != null && slot.getHasStack()){
			ItemStack stackFromSlot = slot.getStack();
			itemStack = stackFromSlot.copy();

			if(index < 9){
				if(!this.mergeItemStack(stackFromSlot, 9, this.inventorySlots.size(), true)) return ItemStack.EMPTY;
			}else if(!this.mergeItemStack(stackFromSlot, 0, 9, false)) return ItemStack.EMPTY;

			if(stackFromSlot.isEmpty()) slot.putStack(ItemStack.EMPTY);
			else slot.onSlotChanged();
		}
		//tileEntity.world.notifyBlockUpdate(tileEntity.pos, tileEntity.getBlockState(), tileEntity.getBlockState(), 2);
		return itemStack;
	}
}
