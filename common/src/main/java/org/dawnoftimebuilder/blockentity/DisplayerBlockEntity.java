package org.dawnoftimebuilder.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.dawnoftimebuilder.container.DisplayerMenu;
import org.dawnoftimebuilder.registry.DoTBBlockEntitiesRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DisplayerBlockEntity extends BlockEntity implements MenuProvider {
	public final SimpleContainer itemHandler = new SimpleContainer(9);

	public DisplayerBlockEntity(BlockPos pPos, BlockState pBlockState) {
		super(DoTBBlockEntitiesRegistry.INSTANCE.DISPLAYER.get(), pPos, pBlockState);
	}

	@Override
	public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
		CompoundTag tag = super.getUpdateTag(registries);
		tag.put("inv", itemHandler.createTag(registries));
		return tag;
	}

	@Override
	public void saveAdditional(CompoundTag tag, HolderLookup.@NotNull Provider registries) {
		tag.put("inv", itemHandler.createTag(registries));
		super.saveAdditional(tag, registries);
	}

	@Override
	protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
		itemHandler.fromTag(tag.getList("inv", CompoundTag.TAG_COMPOUND), registries);
		super.loadAdditional(tag, registries);
	}

	@NotNull
	@Override
	public Component getDisplayName() {
		return Component.nullToEmpty(null);
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pPlayerInventory, @NotNull Player pPlayer) {
		if(this.getLevel() == null)
			return null;
		return new DisplayerMenu(pContainerId, pPlayerInventory, this);
	}
}