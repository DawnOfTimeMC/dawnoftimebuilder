package org.dawnoftimebuilder.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class DryerTileEntity extends BlockEntity {
    public DryerTileEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    /*private final ItemStackHandler itemHandler = new ItemStackHandler(2);
    private final int[] remainingTicks = new int[2];
    private boolean isInOperation;

    public DryerTileEntity(BlockPos pPos, BlockState pBlockState) {
        super(DoTBTileEntitiesRegistry.DRYER_TE.get(), pPos, pBlockState);
    }

    @Override
    public void tick() {
        if (this.getLevel() != null && !this.getLevel().isClientSide() && this.isInOperation) {
            int finish = 0;
            boolean success = false;
            for (int i = 0; i < this.remainingTicks.length; i++) {
                this.remainingTicks[i]--;

                if (this.remainingTicks[i] <= 0) {
                    this.remainingTicks[i] = 0;
                    //Item dried, we replace it with the recipe result, and clear the recipe cached.
                    final DryerRecipe recipe = this.getDryerRecipe(new Inventory(this.itemHandler.getStackInSlot(i)));

                    if (recipe != null) {
                        this.itemHandler.setStackInSlot(i, recipe.getResultItem(this.getLevel().registryAccess()).copy());
                        success = true;
                    }
                    finish++;
                }
            }

            if (success) {
                this.getLevel().sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3 + 2);
            }

            if (finish >= 2) {
                this.isInOperation = false;
            }
        }
    }

    @Override
    public BlockPos getPos() {
        return null;
    }

    public InteractionResult tryInsertItemStack(final ItemStack itemStack, final boolean simple, final Level worldIn, final BlockPos pos, final Player player) {

        //Try to put the itemStack in an empty dryer
        if (this.putItemStackInFreeSpace(itemStack, simple, player)) {
            return InteractionResult.SUCCESS;
        }
        //No empty dryer, let's see if we could replace a dried item with ours
        if (simple) {
            if (this.itemIsDried(0)) {
                this.dropItemIndex(0, worldIn, pos);
                this.putItemStackInIndex(0, itemStack, player);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }
        final int index = this.dropOneDriedItem(worldIn, pos);
        if (index < 0) {
            return InteractionResult.PASS;
        }
        this.putItemStackInIndex(index, itemStack, player);
        return InteractionResult.SUCCESS;
    }

    public InteractionResult dropOneItem(final Level worldIn, final BlockPos pos) {
        if (this.dropOneDriedItem(worldIn, pos) > -1) {
            return InteractionResult.SUCCESS;
        }
        if (!this.itemHandler.getStackInSlot(0).isEmpty()) {
            this.dropItemIndex(0, worldIn, pos);
            return InteractionResult.SUCCESS;
        }
        if (!this.itemHandler.getStackInSlot(1).isEmpty()) {
            this.dropItemIndex(1, worldIn, pos);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public int dropOneDriedItem(final Level worldIn, final BlockPos pos) {
        if (this.itemIsDried(0)) {
            this.dropItemIndex(0, worldIn, pos);
            return 0;
        }
        if (this.itemIsDried(1)) {
            this.dropItemIndex(1, worldIn, pos);
            return 1;
        }
        return -1;
    }

    private boolean itemIsDried(final int index) {
        if (this.itemHandler.getStackInSlot(index).isEmpty()) {
            return false;
        }
        return this.remainingTicks[index] <= 0;
    }

    private boolean putItemStackInFreeSpace(final ItemStack itemStack, final boolean simple, final Player player) {

        if (this.itemHandler.getStackInSlot(0).isEmpty() && this.putItemStackInIndex(0, itemStack, player)) {
            this.isInOperation = true;
            return true;
        }
        if (!simple && this.itemHandler.getStackInSlot(1).isEmpty() && this.putItemStackInIndex(1, itemStack, player)) {
            this.isInOperation = true;
            return true;
        }
        return false;
    }

    @Nullable
    private DryerRecipe getDryerRecipe(final Inventory ingredientInventory) {
        if (this.getLevel() != null && !this.getLevel().isClientSide) {
            return this.getLevel().getRecipeManager().getRecipeFor(DryerRecipe.DRYING, ingredientInventory, this.getLevel()).orElse(null);
        }
        return null;
    }

    private boolean putItemStackInIndex(final int index, final ItemStack itemStack, final Player player) {
        //Tries to put the itemStack in a dryer : first we check if there is a corresponding recipe, then we set the variables.
        if (this.getLevel() != null) {
            final Inventory invInHand = new Inventory(player);
            final DryerRecipe recipe = this.getDryerRecipe(invInHand);
            if (recipe != null && recipe.matches(invInHand, this.getLevel())) {
                this.itemHandler.setStackInSlot(index, recipe.getIngredients().get(0).getItems()[0].copy());
                if (!player.isCreative()) {
                    itemStack.shrink(recipe.getIngredients().get(0).getItems()[0].getCount());
                }
                final float timeVariation = new Random().nextFloat() * 2.0F - 1.0F;
                final int range = timeVariation >= 0 ? DoTBConfig.DRYING_TIME_VARIATION.get() : 10000 / (100 + DoTBConfig.DRYING_TIME_VARIATION.get());
                this.remainingTicks[index] = (int) (recipe.getDryingTime() * (100 + timeVariation * range) / 100);
                this.getLevel().sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3 + 2);

                return true;
            }
        }
        return false;
    }

    private void dropItemIndex(final int index, final Level worldIn, final BlockPos pos) {
        Block.popResource(worldIn, pos, this.itemHandler.extractItem(index, 64, false));
        this.remainingTicks[index] = 0;
        if (this.getLevel() != null) {
            final BlockState state = this.getLevel().getBlockState(pos);
            this.getLevel().sendBlockUpdated(this.worldPosition, state, state, 2);
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, @Nullable final Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return LazyOptional.of(() -> this.itemHandler).cast();
        }
        return super.getCapability(cap, side);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        final CompoundTag tag = super.getUpdateTag();
        tag.put("inv", this.itemHandler.serializeNBT());
        return super.getUpdatePacket();
    }
    
    @Override
    public void onDataPacket(final Connection net, final ClientboundBlockEntityDataPacket pkt) {
        final CompoundTag tag = pkt.getTag();
        if (tag.contains("inv") && this.getLevel() != null) {
            this.itemHandler.deserializeNBT(tag.getCompound("inv"));
            this.getLevel().sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3 + 2);
        }
    }

    @Override
    public void handleUpdateTag(final CompoundTag tag) {
        this.itemHandler.deserializeNBT(tag.getCompound("inv"));
        super.handleUpdateTag(tag);
    }

    @Override
    public CompoundTag getUpdateTag() {
        final CompoundTag tag = super.getUpdateTag();
        tag.put("inv", this.itemHandler.serializeNBT());
        return tag;
    }

    @Override
    public void saveAdditional(final CompoundTag tag) {
        tag.put("inv", this.itemHandler.serializeNBT());
        for (int index = 0; index < 2; index++) {
            tag.putInt("remainingTime" + index, this.remainingTicks[index]);
        }
        tag.putBoolean("isInOperation", this.isInOperation);

        super.saveAdditional(tag);
    }

    @Override
    public void load(final CompoundTag tag) {
        this.itemHandler.deserializeNBT(tag.getCompound("inv"));
        for (int index = 0; index < 2; index++) {
            this.remainingTicks[index] = tag.getInt("remainingTime" + index);
        }
        this.isInOperation = tag.getBoolean("isInOperation");

        super.load(tag);
    }*/
}