package org.dawnoftimebuilder.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.dawnoftimebuilder.recipe.DryerRecipe;
import org.dawnoftimebuilder.util.DoTBConfig;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.Random;

import static net.minecraft.block.Block.spawnAsEntity;
import static org.dawnoftimebuilder.registry.DoTBTileEntitiesRegistry.DRYER_TE;

public class DryerTileEntity extends TileEntity implements ITickableTileEntity {

	private final ItemStackHandler itemHandler = new ItemStackHandler(2);
	private int[] remainingTicks = new int[2];

	public DryerTileEntity() {
		super(DRYER_TE);
	}

	@Override
	public void tick() {
		if(this.getWorld() != null){
			if(!this.getWorld().isRemote()) {
				if(this.remainingTicks[0] > 0){
					this.remainingTicks[0]--;
					if(this.remainingTicks[0] == 0){
						//Item dried, we replace it with the recipe result, and clear the recipe cached.
						DryerRecipe recipe = this.getDryerRecipe(new Inventory(this.itemHandler.getStackInSlot(0)));
						if(recipe != null) this.itemHandler.setStackInSlot(0, recipe.getRecipeOutput());
					}
				}
				if(this.remainingTicks[1] > 0){
					this.remainingTicks[1]--;
					if(this.remainingTicks[1] == 0){
						DryerRecipe recipe = this.getDryerRecipe(new Inventory(this.itemHandler.getStackInSlot(1)));
						if(recipe != null) this.itemHandler.setStackInSlot(1, recipe.getRecipeOutput());
					}
				}
			}
		}
	}

	@Override
	public void remove() {//TODO Check if useful
		if(this.getWorld() != null){
			TileEntity te = this.getWorld().getTileEntity(this.pos);
			if(te instanceof DryerTileEntity){
				DryerTileEntity newTE = (DryerTileEntity) te;
				newTE.itemHandler.setStackInSlot(0, this.itemHandler.getStackInSlot(0));
				newTE.itemHandler.setStackInSlot(1, this.itemHandler.getStackInSlot(1));
				newTE.remainingTicks = this.remainingTicks;
			}
		}
		super.remove();
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
		if(this.getWorld() != null) return this.getWorld().getRecipeManager().getRecipe(DryerRecipe.DRYING, ingredientInventory, this.getWorld()).orElse(null);
		return null;
	}

	private boolean putItemStackInIndex(int index, ItemStack itemStack, PlayerEntity player){
		//Tries to put the itemStack in a dryer : first we check if there is a corresponding recipe, then we set the variables.
		if(this.getWorld() != null){
			IInventory invInHand = new Inventory(itemStack);
			DryerRecipe recipe = this.getDryerRecipe(invInHand);
			if(recipe != null && recipe.matches(invInHand, this.getWorld())){
				this.itemHandler.setStackInSlot(index, recipe.getIngredients().get(0).getMatchingStacks()[0].copy());
				if(!player.isCreative()) itemStack.shrink(recipe.getIngredients().get(0).getMatchingStacks()[0].getCount());
				float timeVariation = new Random().nextFloat() * 2.0F - 1.0F;
				int range = (timeVariation >= 0) ? DoTBConfig.DRYING_TIME_VARIATION.get() : 10000 / (100 + DoTBConfig.DRYING_TIME_VARIATION.get());
				this.remainingTicks[index] = (int) (recipe.getDryingTime() * (100 + timeVariation * range) / 100);
				//BlockState state = this.getWorld().getBlockState(this.pos);
				//this.getWorld().notifyBlockUpdate(this.pos, state, state, 2);
			}
		}
		return false;
	}

	private void dropItemIndex(int index, World worldIn, BlockPos pos){
		spawnAsEntity(worldIn, pos, this.itemHandler.extractItem(index, 64, false));
		this.resetIndex(index);
	}

	private void resetIndex(int index){
		this.remainingTicks[index] = 0;
		if(this.getWorld() != null){
			BlockState state = this.getWorld().getBlockState(pos);
			this.getWorld().notifyBlockUpdate(this.pos, state, state, 2);
		}
	}
/*
	@OnlyIn(Dist.CLIENT)
	public IBakedModel getItemCustomModel(int index){
		ModelResourceLocation test = new ModelResourceLocation(IItemCanBeDried.getResourceLocation(Objects.requireNonNull(this.itemHandler.getStackInSlot(index).getItem().getRegistryName()).getPath()).toString());
		return Minecraft.getInstance().getModelManager().getModel(new ModelResourceLocation(IItemCanBeDried.getResourceLocation(Objects.requireNonNull(this.itemHandler.getStackInSlot(index).getItem().getRegistryName()).getPath()).toString()));
	}
*/
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
		super.handleUpdateTag(tag);
	}

	@Override
	public CompoundNBT write(CompoundNBT tag) {
		tag.put("inv", itemHandler.serializeNBT());
		for(int index = 0; index < 2; index++){
			tag.putInt("remainingTime" + index, this.remainingTicks[index]);
		}
		return super.write(tag);
	}

	@Override
	public void read(CompoundNBT tag) {
		itemHandler.deserializeNBT(tag.getCompound("inv"));
		for(int index = 0; index < 2; index++){
			this.remainingTicks[index] = tag.getInt("remainingTime" + index);
		}
		super.read(tag);
	}
}