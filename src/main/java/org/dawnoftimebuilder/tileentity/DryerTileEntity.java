package org.dawnoftimebuilder.tileentity;

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
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.dawnoftimebuilder.DoTBConfig;
import org.dawnoftimebuilder.recipe.DryerRecipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

import static net.minecraft.block.Block.popResource;
import static org.dawnoftimebuilder.registry.DoTBTileEntitiesRegistry.DRYER_TE;

public class DryerTileEntity extends TileEntity implements ITickableTileEntity {

	private final ItemStackHandler itemHandler = new ItemStackHandler(2);
	private final int[] remainingTicks = new int[2];

	public DryerTileEntity() {
		super(DRYER_TE.get());
	}

	@Override
	public void tick() {
		if(this.getLevel() != null){
			if(!this.getLevel().isClientSide()) {
				boolean shouldUpdate = false;
				if(this.remainingTicks[0] > 0){
					this.remainingTicks[0]--;
					if(this.remainingTicks[0] == 0){
						//Item dried, we replace it with the recipe result, and clear the recipe cached.
						DryerRecipe recipe = this.getDryerRecipe(new Inventory(this.itemHandler.getStackInSlot(0)));
						if(recipe != null) {
							this.itemHandler.setStackInSlot(0, recipe.getResultItem());
							shouldUpdate = true;
						}
					}
				}
				if(this.remainingTicks[1] > 0){
					this.remainingTicks[1]--;
					if(this.remainingTicks[1] == 0){
						DryerRecipe recipe = this.getDryerRecipe(new Inventory(this.itemHandler.getStackInSlot(1)));
						if(recipe != null) {
							this.itemHandler.setStackInSlot(1, recipe.getResultItem());
							shouldUpdate = true;
						}
					}
				}
				if(shouldUpdate) this.getLevel().sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.NOTIFY_NEIGHBORS);
			}
		}
	}

	public boolean tryInsertItemStack(ItemStack itemStack, boolean simple, World worldIn, BlockPos pos, PlayerEntity player){
		//Try to put the itemStack in an empty dryer
		if(this.putItemStackInFreeSpace(itemStack, simple, player)) return true;
		//No empty dryer, let's see if we could replace a dried item with ours
		if(simple){
			if(this.itemIsDried(0)){
				this.dropItemIndex(0, worldIn, pos);
				this.putItemStackInIndex(0, itemStack, player);
				return true;
			}else return false;
		}else{
			int index = this.dropOneDriedItem(worldIn, pos);
			if(index < 0) return false;
			else{
				this.putItemStackInIndex(index, itemStack, player);
				return true;
			}
		}
	}

	public boolean dropOneItem(World worldIn, BlockPos pos){
		if(this.dropOneDriedItem(worldIn, pos) > -1) return true;
		if(!this.itemHandler.getStackInSlot(0).isEmpty()){
			this.dropItemIndex(0, worldIn, pos);
			return true;
		}
		if(!this.itemHandler.getStackInSlot(1).isEmpty()){
			this.dropItemIndex(1, worldIn, pos);
			return true;
		}
		return false;
	}

	/**
	 * @return the index of the dried item dropped, else return -1.
	 */
	public int dropOneDriedItem(World worldIn, BlockPos pos){
		if(this.itemIsDried(0)){
			this.dropItemIndex(0, worldIn, pos);
			return 0;
		}
		if(this.itemIsDried(1)){
			this.dropItemIndex(1, worldIn, pos);
			return 1;
		}
		return -1;
	}

	/**
	 * @return True if there is an item and 0 remainingTicks, and false if there is no Item for this index
	 */
	private boolean itemIsDried(int index){
		if(this.itemHandler.getStackInSlot(index).isEmpty()) return false;
		return this.remainingTicks[index] == 0;
	}

	private boolean putItemStackInFreeSpace(ItemStack itemStack, boolean simple, PlayerEntity player){
		if(this.itemHandler.getStackInSlot(0).isEmpty()){
			return this.putItemStackInIndex(0, itemStack, player);
		}else if(!simple){
			if(this.itemHandler.getStackInSlot(1).isEmpty()) {
				return this.putItemStackInIndex(1, itemStack, player);
			}
		}
		return false;
	}

	@Nullable
	private DryerRecipe getDryerRecipe(IInventory ingredientInventory){
		if(this.getLevel() != null) return this.getLevel().getRecipeManager().getRecipeFor(DryerRecipe.DRYING, ingredientInventory, this.getLevel()).orElse(null);
		return null;
	}

	private boolean putItemStackInIndex(int index, ItemStack itemStack, PlayerEntity player){
		//Tries to put the itemStack in a dryer : first we check if there is a corresponding recipe, then we set the variables.
		if(this.getLevel() != null){
			IInventory invInHand = new Inventory(itemStack);
			DryerRecipe recipe = this.getDryerRecipe(invInHand);
			if(recipe != null && recipe.matches(invInHand, this.getLevel())){
				this.itemHandler.setStackInSlot(index, recipe.getIngredients().get(0).getItems()[0].copy());
				if(!player.isCreative()) itemStack.shrink(recipe.getIngredients().get(0).getItems()[0].getCount());
				float timeVariation = new Random().nextFloat() * 2.0F - 1.0F;
				int range = (timeVariation >= 0) ? DoTBConfig.DRYING_TIME_VARIATION.get() : 10000 / (100 + DoTBConfig.DRYING_TIME_VARIATION.get());
				this.remainingTicks[index] = (int) (recipe.getDryingTime() * (100 + timeVariation * range) / 100);
				this.getLevel().sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.NOTIFY_NEIGHBORS);
			}
		}
		return false;
	}

	private void dropItemIndex(int index, World worldIn, BlockPos pos){
		popResource(worldIn, pos, this.itemHandler.extractItem(index, 64, false));
		this.remainingTicks[index] = 0;
		if(this.getLevel() != null){
			BlockState state = this.getLevel().getBlockState(pos);
			this.getLevel().sendBlockUpdated(this.worldPosition, state, state, 2);
		}
	}

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return LazyOptional.of(() -> itemHandler).cast();
		}
		return super.getCapability(cap, side);
	}

	@Nullable
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT tag = super.getUpdateTag();
		tag.put("inv", itemHandler.serializeNBT());
		return new SUpdateTileEntityPacket(this.worldPosition, 1, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		CompoundNBT tag = pkt.getTag();
		if(tag.contains("inv") && this.getLevel() !=  null) {
			this.itemHandler.deserializeNBT(tag.getCompound("inv"));
			this.getLevel().sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.NOTIFY_NEIGHBORS);
		}
	}

	@Override
	public void handleUpdateTag(BlockState state, CompoundNBT tag) {
		this.itemHandler.deserializeNBT(tag.getCompound("inv"));
		super.handleUpdateTag(state, tag);
	}

	@Override
	public CompoundNBT getUpdateTag() {
		CompoundNBT tag = super.getUpdateTag();
		tag.put("inv", this.itemHandler.serializeNBT());
		return tag;
	}

	@Override
	public CompoundNBT save(CompoundNBT tag) {
		tag.put("inv", itemHandler.serializeNBT());
		for(int index = 0; index < 2; index++){
			tag.putInt("remainingTime" + index, this.remainingTicks[index]);
		}
		return super.save(tag);
	}

	@Override
	public void load(BlockState state, CompoundNBT tag) {
		itemHandler.deserializeNBT(tag.getCompound("inv"));
		for(int index = 0; index < 2; index++){
			this.remainingTicks[index] = tag.getInt("remainingTime" + index);
		}
		super.load(state, tag);
	}
}