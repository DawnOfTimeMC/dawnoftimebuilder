package org.dawnoftime.dawnoftime.container;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.dawnoftime.dawnoftime.block.IBlockSpecialDisplay;
import org.dawnoftime.dawnoftime.blockentity.DisplayerBlockEntity;
import org.dawnoftime.dawnoftime.registry.DoTBMenuTypesRegistry;

import static org.dawnoftime.dawnoftime.block.templates.DisplayerBlock.LIT;

public class DisplayerMenu extends AbstractContainerMenu {
	private DisplayerBlockEntity blockEntity;
	private final ContainerLevelAccess levelAccess;

	//Client constructor
	public DisplayerMenu(int windowId, Inventory playerInventory, FriendlyByteBuf additionalData) {
		this(windowId, playerInventory, playerInventory.player.level().getBlockEntity(additionalData.readBlockPos()));
	}

	//Server constructor
	public DisplayerMenu(int windowId, Inventory playerInventory, BlockEntity blockEntity) {
		super(DoTBMenuTypesRegistry.INSTANCE.DISPLAYER.get(), windowId);

		if(blockEntity instanceof DisplayerBlockEntity displayerBlockEntity) {
			this.blockEntity = displayerBlockEntity;
		} else {
			throw new IllegalStateException(String.format("Incorrect block entity class (%s) passed into DisplayerMenu.",
					blockEntity.getClass().getCanonicalName()));
		}

		this.levelAccess = ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos());

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				this.addSlot(new Slot(this.blockEntity.itemHandler, j + i * 3, 19 + j * 18, 168 + i * 18));
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
	public boolean stillValid(Player playerIn) {
		if(this.blockEntity.getLevel() == null) return false;
		return stillValid(this.levelAccess, playerIn, this.blockEntity.getBlockState().getBlock());
	}

	@Override
	public void broadcastChanges() {
		super.broadcastChanges();
		//Update light level
		if(this.blockEntity.getLevel() != null && !this.blockEntity.getLevel().isClientSide()){
				boolean lit = false;
				for(int index = 0; index < this.blockEntity.itemHandler.getContainerSize(); index++) {
					ItemStack itemstack = this.blockEntity.itemHandler.getItem(index);
					if (!itemstack.isEmpty()) {
						Item item = itemstack.getItem();
						if (item instanceof BlockItem) {
							Block block = ((BlockItem) item).getBlock();
							if (block instanceof IBlockSpecialDisplay) {
								lit = ((IBlockSpecialDisplay) block).emitsLight();
							} else {
								lit = block.getLightBlock(block.defaultBlockState(), this.blockEntity.getLevel(), this.blockEntity.getBlockPos()) > 0;
							}
						}
					}
					if (lit)
						break;
				}
				if(this.blockEntity.getBlockState().getValue(LIT) != lit){
					this.blockEntity.getLevel().setBlock(this.blockEntity.getBlockPos(), this.blockEntity.getBlockState().setValue(LIT, lit), 10);
				}
		}
	}

	@Override
	public ItemStack quickMoveStack(Player playerIn, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if(slot.hasItem()) {
			ItemStack stackFromSlot = slot.getItem();
			itemStack = stackFromSlot.copy();

			if(index < 9){
				if(!this.moveItemStackTo(stackFromSlot, 9, this.slots.size(), true)) return ItemStack.EMPTY;
			}else if(!this.moveItemStackTo(stackFromSlot, 0, 9, false)) return ItemStack.EMPTY;

			if(stackFromSlot.isEmpty()) slot.set(ItemStack.EMPTY);
			else slot.setChanged();
		}
		return itemStack;
	}
}