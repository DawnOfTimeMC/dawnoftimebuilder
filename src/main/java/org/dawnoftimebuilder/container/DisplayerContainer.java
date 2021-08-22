package org.dawnoftimebuilder.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.dawnoftimebuilder.tileentity.DisplayerTileEntity;
import static org.dawnoftimebuilder.registries.DoTBContainersRegistry.DISPLAYER_CONTAINER;

public class DisplayerContainer extends Container {

	private final DisplayerTileEntity tileEntity;

	public DisplayerContainer(int windowId, PlayerInventory playerInventory, World world, BlockPos pos) {
		super(DISPLAYER_CONTAINER, windowId);
		this.tileEntity = (world.getTileEntity(pos) instanceof DisplayerTileEntity) ? (DisplayerTileEntity) world.getTileEntity(pos) : null;
		if(this.tileEntity != null){
			this.tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
				for(int i = 0; i < 3; i++){
					for(int j = 0; j < 3; j++){
						this.addSlot(new SlotItemHandler(h, j + i * 3, 19 + j * 18, 168 + i * 18));
					}
				}
			});

			for(int y = 0; y < 3; y++){
				for (int x = 0; x < 9; x++){
					this.addSlot(new Slot(playerInventory, 9 + x + y * 9, 88 + x * 18, 157 + y * 18));
				}
			}

			for(int x = 0; x < 9; x++){
				this.addSlot(new Slot(playerInventory, x, 88 + x * 18, 215));
			}
		}
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		if(this.tileEntity.getWorld() == null) return false;
		return isWithinUsableDistance(IWorldPosCallable.of(this.tileEntity.getWorld(), this.tileEntity.getPos()), playerIn, this.tileEntity.getBlockState().getBlock());
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
		return itemStack;
	}
}