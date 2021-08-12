package org.dawnoftimebuilder.tileentity;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.dawnoftimebuilder.block.IBlockSpecialDisplay;
import org.dawnoftimebuilder.block.templates.DisplayerBlock;
import org.dawnoftimebuilder.container.DisplayerContainer;
import org.dawnoftimebuilder.network.PacketHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static org.dawnoftimebuilder.registries.DoTBTileEntitiesRegistry.DISPLAYER_TE;

public class DisplayerTileEntity extends TileEntity implements INamedContainerProvider {

	private final ItemStackHandler itemHandler = createHandler();

	public DisplayerTileEntity() {
		super(DISPLAYER_TE);
	}

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return LazyOptional.of(() -> itemHandler).cast();
		}
		return super.getCapability(cap, side);
	}

	@Override
	public CompoundNBT getUpdateTag() {
		CompoundNBT tag = super.getUpdateTag();
		tag.put("inv", itemHandler.serializeNBT());
		return tag;
	}

	@Override
	public void handleUpdateTag(CompoundNBT tag) {
		itemHandler.deserializeNBT(tag.getCompound("inv"));
		/*
		int lightLevel = 0;
		for(int index = 0; index < itemHandler.getSlots(); index++){
			if(itemHandler.getStackInSlot(index) != ItemStack.EMPTY){
				Item item = itemHandler.getStackInSlot(index).getItem();
				if(item instanceof BlockItem){
					Block block = ((BlockItem) item).getBlock();
					int testLight = (block instanceof IBlockSpecialDisplay) ? ((IBlockSpecialDisplay) block).getDisplayedLightLevel() : block.getLightValue(block.getDefaultState());
					if(testLight > lightLevel) lightLevel = testLight;
				}
			}
		}
		((DisplayerBlock) this.getBlockState().getBlock()).setLightCurrentValue(lightLevel);
		*/
		super.handleUpdateTag(tag);
	}

	@Override
	public CompoundNBT write(CompoundNBT tag) {
		tag.put("inv", itemHandler.serializeNBT());
		return super.write(tag);
	}

	@Override
	public void read(CompoundNBT tag) {
		itemHandler.deserializeNBT(tag.getCompound("inv"));
		super.read(tag);
	}

	private ItemStackHandler createHandler() {
		return new ItemStackHandler(9) {
			@Override
			public int getSlotLimit(int slot) {
				return 1;
			}
		};
	}

	@Override
	public ITextComponent getDisplayName() {
		return new StringTextComponent(getType().getRegistryName().getPath());
	}

	@Nullable
	@Override
	public Container createMenu(int windowID, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		if(this.getWorld() == null) return null;
		return new DisplayerContainer(windowID, playerInventory, this.getWorld(), this.getPos());
	}
}