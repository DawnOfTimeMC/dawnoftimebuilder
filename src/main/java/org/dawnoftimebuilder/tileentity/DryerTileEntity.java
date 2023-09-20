package org.dawnoftimebuilder.tileentity;

import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.dawnoftimebuilder.DoTBConfig;
import org.dawnoftimebuilder.recipe.DryerRecipe;
import org.dawnoftimebuilder.registry.DoTBTileEntitiesRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class DryerTileEntity extends TileEntity implements ITickableTileEntity {

	private final ItemStackHandler	itemHandler		= new ItemStackHandler(2);
	private final int[]				remainingTicks	= new int[2];
	private boolean					isInOperation;

	public DryerTileEntity() {
		super(DoTBTileEntitiesRegistry.DRYER_TE.get());
	}

	@Override
	public void tick() {
		if (this.getLevel() != null && !this.getLevel().isClientSide() && this.isInOperation) {
			int		finish	= 0;
			boolean	success	= false;
			for (int i = 0; i < this.remainingTicks.length; i++) {
				this.remainingTicks[i]--;

				if (this.remainingTicks[i] <= 0) {
					this.remainingTicks[i] = 0;
					//Item dried, we replace it with the recipe result, and clear the recipe cached.
					final DryerRecipe recipe = this.getDryerRecipe(new Inventory(this.itemHandler.getStackInSlot(i)));

					if (recipe != null) {
						this.itemHandler.setStackInSlot(i, recipe.getResultItem().copy());
						success = true;
					}
					finish++;
				}
			}

			if (success) {
				this.getLevel().sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.NOTIFY_NEIGHBORS);
			}

			if (finish >= 2) {
				this.isInOperation = false;
			}
		}
	}

	public ActionResultType tryInsertItemStack(final ItemStack itemStack, final boolean simple, final World worldIn, final BlockPos pos, final PlayerEntity player) {

		//Try to put the itemStack in an empty dryer
		if (this.putItemStackInFreeSpace(itemStack, simple, player)) {
			return ActionResultType.SUCCESS;
		}
		//No empty dryer, let's see if we could replace a dried item with ours
		if (simple) {
			if (this.itemIsDried(0)) {
				this.dropItemIndex(0, worldIn, pos);
				this.putItemStackInIndex(0, itemStack, player);
				return ActionResultType.SUCCESS;
			}
			return ActionResultType.PASS;
		}
		final int index = this.dropOneDriedItem(worldIn, pos);
		if (index < 0) {
			return ActionResultType.PASS;
		}
		this.putItemStackInIndex(index, itemStack, player);
		return ActionResultType.SUCCESS;
	}

	public ActionResultType dropOneItem(final World worldIn, final BlockPos pos) {
		if (this.dropOneDriedItem(worldIn, pos) > -1) {
			return ActionResultType.SUCCESS;
		}
		if (!this.itemHandler.getStackInSlot(0).isEmpty()) {
			this.dropItemIndex(0, worldIn, pos);
			return ActionResultType.SUCCESS;
		}
		if (!this.itemHandler.getStackInSlot(1).isEmpty()) {
			this.dropItemIndex(1, worldIn, pos);
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.PASS;
	}

	/**
	 * @return the index of the dried item dropped, else return -1.
	 */
	public int dropOneDriedItem(final World worldIn, final BlockPos pos) {
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

	/**
	 * @return True if there is an item and 0 remainingTicks, and false if there is no Item for this index
	 */
	private boolean itemIsDried(final int index) {
		if (this.itemHandler.getStackInSlot(index).isEmpty()) {
			return false;
		}
		return this.remainingTicks[index] <= 0;
	}

	private boolean putItemStackInFreeSpace(final ItemStack itemStack, final boolean simple, final PlayerEntity player) {

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
	private DryerRecipe getDryerRecipe(final IInventory ingredientInventory) {
		if (this.getLevel() != null && !this.getLevel().isClientSide) {
			return this.getLevel().getRecipeManager().getRecipeFor(DryerRecipe.DRYING, ingredientInventory, this.getLevel()).orElse(null);
		}
		return null;
	}

	private boolean putItemStackInIndex(final int index, final ItemStack itemStack, final PlayerEntity player) {
		//Tries to put the itemStack in a dryer : first we check if there is a corresponding recipe, then we set the variables.
		if (this.getLevel() != null) {
			final IInventory	invInHand	= new Inventory(itemStack);
			final DryerRecipe	recipe		= this.getDryerRecipe(invInHand);
			if (recipe != null && recipe.matches(invInHand, this.getLevel())) {
				this.itemHandler.setStackInSlot(index, recipe.getIngredients().get(0).getItems()[0].copy());
				if (!player.isCreative()) {
					itemStack.shrink(recipe.getIngredients().get(0).getItems()[0].getCount());
				}
				final float	timeVariation	= new Random().nextFloat() * 2.0F - 1.0F;
				final int	range			= timeVariation >= 0 ? DoTBConfig.DRYING_TIME_VARIATION.get() : 10000 / (100 + DoTBConfig.DRYING_TIME_VARIATION.get());
				this.remainingTicks[index] = (int) (recipe.getDryingTime() * (100 + timeVariation * range) / 100);
				this.getLevel().sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.NOTIFY_NEIGHBORS);

				return true;
			}
		}
		return false;
	}

	private void dropItemIndex(final int index, final World worldIn, final BlockPos pos) {
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
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return LazyOptional.of(() -> this.itemHandler).cast();
		}
		return super.getCapability(cap, side);
	}

	@Nullable
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		final CompoundNBT tag = super.getUpdateTag();
		tag.put("inv", this.itemHandler.serializeNBT());
		return new SUpdateTileEntityPacket(this.worldPosition, 1, tag);
	}

	@Override
	public void onDataPacket(final NetworkManager net, final SUpdateTileEntityPacket pkt) {
		final CompoundNBT tag = pkt.getTag();
		if (tag.contains("inv") && this.getLevel() != null) {
			this.itemHandler.deserializeNBT(tag.getCompound("inv"));
			this.getLevel().sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.NOTIFY_NEIGHBORS);
		}
	}

	@Override
	public void handleUpdateTag(final BlockState state, final CompoundNBT tag) {
		this.itemHandler.deserializeNBT(tag.getCompound("inv"));
		super.handleUpdateTag(state, tag);
	}

	@Override
	public CompoundNBT getUpdateTag() {
		final CompoundNBT tag = super.getUpdateTag();
		tag.put("inv", this.itemHandler.serializeNBT());
		return tag;
	}

	@Override
	public CompoundNBT save(final CompoundNBT tag) {
		tag.put("inv", this.itemHandler.serializeNBT());
		for (int index = 0; index < 2; index++) {
			tag.putInt("remainingTime" + index, this.remainingTicks[index]);
		}
		tag.putBoolean("isInOperation", this.isInOperation);

		return super.save(tag);
	}

	@Override
	public void load(final BlockState state, final CompoundNBT tag) {
		this.itemHandler.deserializeNBT(tag.getCompound("inv"));
		for (int index = 0; index < 2; index++) {
			this.remainingTicks[index] = tag.getInt("remainingTime" + index);
		}
		this.isInOperation = tag.getBoolean("isInOperation");

		super.load(state, tag);
	}
}