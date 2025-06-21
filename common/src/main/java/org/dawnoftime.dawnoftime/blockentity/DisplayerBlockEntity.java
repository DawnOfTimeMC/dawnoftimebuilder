package org.dawnoftime.dawnoftime.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.dawnoftime.dawnoftime.container.DisplayerMenu;
import org.dawnoftime.dawnoftime.registry.DoTBBlockEntitiesRegistry;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class DisplayerBlockEntity extends BlockEntity implements MenuProvider {
	public final SimpleContainer itemHandler = new SimpleContainer(9);

	public DisplayerBlockEntity(BlockPos pPos, BlockState pBlockState) {
		super(DoTBBlockEntitiesRegistry.INSTANCE.DISPLAYER.get(), pPos, pBlockState);
	}

	@Override
	public @NotNull CompoundTag getUpdateTag() {
		CompoundTag tag = super.getUpdateTag();
		tag.put("inv", itemHandler.createTag());
		return tag;
	}

	@Override
	public void saveAdditional(CompoundTag tag) {
		tag.put("inv", itemHandler.createTag());
		super.saveAdditional(tag);
	}

	@Override
	public void load(CompoundTag tag) {
		itemHandler.fromTag(tag.getList("inv", CompoundTag.TAG_COMPOUND));
		super.load(tag);
	}

	@NotNull
	@Override
	public Component getDisplayName() {
		return Component.nullToEmpty(null);
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
		if(this.getLevel() == null)
			return null;
		return new DisplayerMenu(pContainerId, pPlayerInventory, this);
	}
}