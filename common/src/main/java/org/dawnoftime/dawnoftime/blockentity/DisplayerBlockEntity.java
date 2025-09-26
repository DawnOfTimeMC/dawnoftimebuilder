package org.dawnoftime.dawnoftime.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.dawnoftime.dawnoftime.block.IBlockSpecialDisplay;
import org.dawnoftime.dawnoftime.registry.DoTBBlockEntitiesRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class DisplayerBlockEntity extends BlockEntity implements Container {
	private static final int SIZE = 9;

	private final NonNullList<ItemStack> items = NonNullList.withSize(SIZE, ItemStack.EMPTY);

	public DisplayerBlockEntity(BlockPos pPos, BlockState pBlockState) {
		super(DoTBBlockEntitiesRegistry.INSTANCE.DISPLAYER.get(), pPos, pBlockState);
	}

	@Override
	public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
		CompoundTag tag = super.getUpdateTag(registries);
		ContainerHelper.saveAllItems(tag, this.items, registries);
		return tag;
	}

	@Override
	public void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
		super.saveAdditional(tag, registries);
		ContainerHelper.saveAllItems(tag, this.items, registries);
	}

	@Override
	public void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
		super.loadAdditional(tag, registries);
		this.items.clear();
		ContainerHelper.loadAllItems(tag, this.items, registries);
	}

	@Override
	public int getContainerSize() {
		return SIZE;
	}

	@Override
	public boolean isEmpty() {
		return this.items.stream().allMatch(ItemStack::isEmpty);
	}

	@Override
	public @NotNull ItemStack getItem(int slot) {
		if (slot >= SIZE) {
			return ItemStack.EMPTY;
		}
		return this.items.get(slot);
	}



	@Override
	public @NotNull ItemStack removeItem(int slot, int amount) {
		ItemStack itemstack = ContainerHelper.removeItem(this.items, slot, amount);
		if (!itemstack.isEmpty()) {
			this.setChanged();
			this.synchroniseWithClient();
		}
		return itemstack;
	}

	@Override
	public @NotNull ItemStack removeItemNoUpdate(int slot) {
		return ContainerHelper.takeItem(this.items, slot);
	}

	@Override
	public void setItem(int slot, @NotNull ItemStack stack) {
		int quantity = stack.getCount();
		this.items.set(slot, new ItemStack(stack.getItem(), 1));
		if (quantity > 0) {
			stack.setCount(quantity - 1);
		}
		this.setChanged();
		this.synchroniseWithClient();
	}

	@Override
	public boolean stillValid(@NotNull Player player) {
		return true;
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public void clearContent() {
		this.items.clear();
		this.setChanged();
		this.synchroniseWithClient();
	}

	public List<ItemStack> removeAllItems() {
		List<ItemStack> list = this.items.stream().filter((stack) -> !stack.isEmpty()).collect(Collectors.toList());
		this.clearContent();
		return list;
	}

	private void synchroniseWithClient() {
		if (this.level != null && !this.level.isClientSide()) {
			this.level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
		}
	}

	@Override
	public int getMaxStackSize() {
		return 1;
	}

	public boolean isLit() {
		if (this.getLevel() == null) {
			return false;
		}
		for(int slot = 0; slot < 9; slot++) {
			ItemStack itemstack = this.getItem(slot);
			if (!itemstack.isEmpty()) {
				if (itemstack.getItem() instanceof BlockItem blockItem) {
					Block block = blockItem.getBlock();
					if (block instanceof IBlockSpecialDisplay displayItem) {
						if (displayItem.emitsLight()) {
							return true;
						}
					} else {
						BlockState defaultState = block.defaultBlockState();
						if (defaultState.getLightEmission() > 1) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}