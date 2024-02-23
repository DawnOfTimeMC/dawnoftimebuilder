package org.dawnoftimebuilder.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class DisplayerTileEntity extends BlockEntity {
    public DisplayerTileEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

	/*private final ItemStackHandler itemHandler = createHandler();

	public DisplayerTileEntity() {
		super(DISPLAYER_TE.get());
	}

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		if (cap == ForgeCapabilities.ITEM_HANDLER) {
			return LazyOptional.of(() -> itemHandler).cast();
		}
		return super.getCapability(cap, side);
	}

	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag tag = super.getUpdateTag();
		tag.put("inv", itemHandler.serializeNBT());
		return tag;
	}

	@Override
	public void handleUpdateTag(CompoundTag tag) {
		//Only on client side
		itemHandler.deserializeNBT(tag.getCompound("inv"));
		super.handleUpdateTag(tag);
	}

	@Override
	public void saveAdditional(CompoundTag tag) {
		tag.put("inv", itemHandler.serializeNBT());
		super.saveAdditional(tag);
	}

	@Override
	public void load(CompoundTag tag) {
		itemHandler.deserializeNBT(tag.getCompound("inv"));
		super.load(tag);
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
	public Component getDisplayName() {
		return null;
	}

	@Override
	protected Component getDefaultName() {
		return null;
	}

	@Override
	public Component getName() {
		return super.getName();
	}

	@Override
	protected AbstractContainerMenu createMenu(int windowID, Inventory inventory) {
		if(this.getLevel() == null) return null;
		return new DisplayerContainer(windowID, inventory, this.getLevel(), this.getBlockPos());
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return null;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> p_59625_) {

	}

	@Override
	public int getContainerSize() {
		return 0;
	}*/
}