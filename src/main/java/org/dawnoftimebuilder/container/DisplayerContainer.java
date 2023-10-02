package org.dawnoftimebuilder.container;

/*import net.minecraft.block.Block;
import net.minecraft.core.BlockPos;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.World;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.dawnoftimebuilder.block.IBlockSpecialDisplay;
import org.dawnoftimebuilder.blockentity.DisplayerTileEntity;
import org.dawnoftimebuilder.tileentity.DisplayerTileEntity;

import static org.dawnoftimebuilder.block.templates.DisplayerBlock.LIT;
import static org.dawnoftimebuilder.registry.DoTBContainersRegistry.DISPLAYER_CONTAINER;

public class DisplayerContainer implements Container {

	private final DisplayerTileEntity tileEntity;

	public DisplayerContainer(int windowId, Container playerInventory, Level world, BlockPos pos) {
		super(DISPLAYER_CONTAINER.get(), windowId);
		this.tileEntity = (world.getBlockEntity(pos) instanceof DisplayerTileEntity) ? (DisplayerTileEntity) world.getBlockEntity(pos) : null;
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
	public boolean stillValid(Player playerIn) {
		if(this.tileEntity.getLevel() == null) return false;
		return stillValid(IWorldPosCallable.create(this.tileEntity.getLevel(), this.tileEntity.getBlockPos()), playerIn, this.tileEntity.getBlockState().getBlock());
	}

	@Override
	public void broadcastChanges() {
		super.broadcastChanges();
		//Update light level
		if(this.tileEntity.getLevel() != null && !this.tileEntity.getLevel().isClientSide()){
			this.tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
				boolean lit = false;
				for(int index = 0; index < h.getSlots(); index++) {
					ItemStack itemstack = h.getStackInSlot(index).getStack();
					if (!itemstack.isEmpty()) {
						Item item = itemstack.getItem();
						if (item instanceof BlockItem) {
							Block block = ((BlockItem) item).getBlock();
							if (block instanceof IBlockSpecialDisplay) {
								lit = ((IBlockSpecialDisplay) block).emitsLight();
							} else lit = block.getLightValue(block.defaultBlockState(), this.tileEntity.getLevel(), this.tileEntity.getBlockPos()) > 0;
						}
					}
					if (lit) break;
				}
				if(this.tileEntity.getBlockState().getValue(LIT) != lit){
					this.tileEntity.getLevel().setBlock(this.tileEntity.getBlockPos(), this.tileEntity.getBlockState().setValue(LIT, lit), 10);
				}
			});
		}
	}

	@Override
	public ItemStack quickMoveStack(Player playerIn, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if(slot != null && slot.hasItem()){
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
}*/