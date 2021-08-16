package org.dawnoftimebuilder.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.dawnoftimebuilder.items.IItemCanBeDried;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.Objects;
import java.util.Random;

import static net.minecraft.block.Block.spawnAsEntity;
import static org.dawnoftimebuilder.registries.DoTBTileEntitiesRegistry.DRYER_TE;

public class DryerTileEntity extends TileEntity implements ITickableTileEntity {

	private final ItemStackHandler itemHandler = new ItemStackHandler(2);
	private int[] craftingTimes = new int[2];
	private int[] currentTimes = new int[2];

	private int tickCount = 0;

	public DryerTileEntity() {
		super(DRYER_TE);
	}

	@Override
	public void tick() {
		if(this.getWorld() != null){
			if(!this.getWorld().isRemote()) {
				this.tickCount++;
				if(this.tickCount == 20){
					this.tickCount = 0;
					boolean updateModel = false;
					if(this.craftingTimes[0] > this.currentTimes[0]){
						this.currentTimes[0]++;
						if(this.craftingTimes[0] == this.currentTimes[0]){
							updateModel = true;
							this.itemHandler.setStackInSlot(0, new ItemStack(this.getItemInSlot(0).getDriedItem(), this.getItemInSlot(0).getDriedItemQuantity()));
						}
					}
					if(this.craftingTimes[1] > this.currentTimes[1]){
						this.currentTimes[1]++;
						if(this.craftingTimes[1] == this.currentTimes[1]){
							updateModel = true;
							this.itemHandler.setStackInSlot(1, new ItemStack(this.getItemInSlot(1).getDriedItem(), this.getItemInSlot(1).getDriedItemQuantity()));
						}
					}
					if(updateModel){
						BlockState state = this.getWorld().getBlockState(pos);
						this.getWorld().notifyBlockUpdate(pos, state, state, 2);
					}
				}
			}
		}
	}

	private IItemCanBeDried getItemInSlot(int index){
		return (IItemCanBeDried)this.itemHandler.getStackInSlot(index).getItem();
	}

	@Override
	public void remove() {
		if(this.getWorld() != null){
			TileEntity te = this.getWorld().getTileEntity(this.pos);
			if(te instanceof DryerTileEntity){
				DryerTileEntity newTE = (DryerTileEntity) te;
				newTE.itemHandler.setStackInSlot(0, this.itemHandler.getStackInSlot(0));
				newTE.itemHandler.setStackInSlot(1, this.itemHandler.getStackInSlot(1));
				newTE.craftingTimes = this.craftingTimes;
				newTE.currentTimes = this.currentTimes;
			}
		}
		super.remove();
	}

	public boolean putUndriedItem(IItemCanBeDried item, boolean simple, World worldIn, BlockPos pos){
		if(this.putItemInFreeSpace(item, simple)) return true;
		if(simple){
			if(this.itemIsDried(0)){
				this.dropItemIndex(0, worldIn, pos);
				this.putItemIndex(0, item);
				return true;
			}else return false;
		}else{
			int index = this.dropOneDriedItem(worldIn, pos);
			if(index < 0) return false;
			else{
				this.putItemIndex(index, item);
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
	 * @return currentTime == craftingTime, and false if there is no Item for this index
	 */
	private boolean itemIsDried(int index){
		if(this.itemHandler.getStackInSlot(index).isEmpty()) return false;
		return this.currentTimes[index] == this.craftingTimes[index];
	}

	private boolean putItemInFreeSpace(IItemCanBeDried item, boolean simple){
		if(this.itemHandler.getStackInSlot(0).isEmpty()){
			this.putItemIndex(0, item);
			return true;
		}else if(!simple){
			if(this.itemHandler.getStackInSlot(1).isEmpty()) {
				this.putItemIndex(1, item);
				return true;
			}
		}
		return false;
	}

	private void putItemIndex(int index, IItemCanBeDried item){
		if(this.getWorld() != null){
			this.itemHandler.setStackInSlot(index, new ItemStack(item.getItem(), item.getItemQuantity()));
			this.currentTimes[index] = 0;
			this.craftingTimes[index] = (int) (item.getDryingTime() * (0.8d + new Random().nextInt(5) * 0.1d));
			BlockState state = this.getWorld().getBlockState(this.pos);
			this.getWorld().notifyBlockUpdate(this.pos, state, state, 2);
		}
	}

	private void dropItemIndex(int index, World worldIn, BlockPos pos){
		spawnAsEntity(worldIn, pos, this.itemHandler.extractItem(index, 64, false));
		this.resetIndex(index);
	}

	private void resetIndex(int index){
		this.craftingTimes[index] = 0;
		this.currentTimes[index] = 0;
		if(this.getWorld() != null){
			BlockState state = this.getWorld().getBlockState(pos);
			this.getWorld().notifyBlockUpdate(this.pos, state, state, 2);
		}
	}

	//TODO Check good practice with sided class like this one.
	@OnlyIn(Dist.CLIENT)
	public IBakedModel getItemCustomModel(int index){
		ModelResourceLocation test = new ModelResourceLocation(IItemCanBeDried.getResourceLocation(Objects.requireNonNull(this.itemHandler.getStackInSlot(index).getItem().getRegistryName()).getPath()).toString());
		return Minecraft.getInstance().getModelManager().getModel(new ModelResourceLocation(IItemCanBeDried.getResourceLocation(Objects.requireNonNull(this.itemHandler.getStackInSlot(index).getItem().getRegistryName()).getPath()).toString()));
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
		super.handleUpdateTag(tag);
	}

	@Override
	public CompoundNBT write(CompoundNBT tag) {
		tag.put("inv", itemHandler.serializeNBT());
		for(int index = 0; index < 2; index++){
			tag.putInt("crafting_time_" + index, this.craftingTimes[index]);
			tag.putInt("current_time_" + index, this.currentTimes[index]);
		}
		return super.write(tag);
	}

	@Override
	public void read(CompoundNBT tag) {
		itemHandler.deserializeNBT(tag.getCompound("inv"));
		for(int index = 0; index < 2; index++){
			this.craftingTimes[index] = tag.getInt("crafting_time_" + index);
			this.currentTimes[index] = tag.getInt("current_time_" + index);
		}
		super.read(tag);
	}
}