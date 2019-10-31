package org.dawnoftimebuilder.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dawnoftimebuilder.registries.DoTBItemsRegistry;
import org.dawnoftimebuilder.items.IItemCanBeDried;

import java.util.Objects;
import java.util.Random;

import static net.minecraft.block.Block.spawnAsEntity;

public class DoTBTileEntityDryer extends TileEntity implements ITickable {

	private IItemCanBeDried[] undriedItem = new IItemCanBeDried[2];
	private int[] craftingTimes = new int[2];
	private int[] currentTimes = new int[2];

	private int tickCount = 0;

	@Override
	public void update() {
		if(!this.getWorld().isRemote) {
			this.tickCount++;
			if(this.tickCount == 20){
				this.tickCount = 0;
				if(this.craftingTimes[0] > this.currentTimes[0]){
					this.currentTimes[0]++;
					if(this.craftingTimes[0] == this.currentTimes[0]){
						IBlockState state = world.getBlockState(pos);
						world.notifyBlockUpdate(pos, state, state, 2);
					}
				}
				if(this.craftingTimes[1] > this.currentTimes[1]){
					this.currentTimes[1]++;
					if(this.craftingTimes[1] == this.currentTimes[1]){
						IBlockState state = world.getBlockState(pos);
						world.notifyBlockUpdate(pos, state, state, 2);
					}
				}
			}
		}
	}

	public void upgrade(DoTBTileEntityDryer oldTileEntity){
		this.undriedItem = oldTileEntity.undriedItem;
		this.craftingTimes = oldTileEntity.craftingTimes;
		this.currentTimes = oldTileEntity.currentTimes;
	}

	public boolean containsItemInSlot(int index){
		return undriedItem[index] != null;
	}

	public IBakedModel getItemCustomModel(int index){
		String name;
		if(currentTimes[index] >= craftingTimes[index]) name = Objects.requireNonNull(undriedItem[index].getDriedItem().getRegistryName()).getPath();
		else name = Objects.requireNonNull(undriedItem[index].getItem().getRegistryName()).getPath();
		return DoTBItemsRegistry.getModel(name);
	}

	public boolean putUndriedItem(IItemCanBeDried item, boolean simple, World worldIn, BlockPos pos){
		if(this.putItemInFreeSpace(item, simple)) return true;
		if(simple){
			if(this.itemIsDried(0)){
				this.dropItemIndex(0, true, worldIn, pos);
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
		if(this.undriedItem[0] != null){
			this.dropItemIndex(0, false, worldIn, pos);
			return true;
		}
		if(this.undriedItem[1] != null){
			this.dropItemIndex(1, false, worldIn, pos);
			return true;
		}
		return false;
	}

	/**
	 * @return the index of the dried item dropped, else return -1.
	 */
	public int dropOneDriedItem(World worldIn, BlockPos pos){
		if(this.itemIsDried(0)){
			this.dropItemIndex(0, true, worldIn, pos);
			return 0;
		}
		if(this.itemIsDried(1)){
			this.dropItemIndex(1, true, worldIn, pos);
			return 1;
		}
		return -1;
	}

	/**
	 * @return currentTime == craftingTime, and false if there is no Item for this index
	 */
	private boolean itemIsDried(int index){
		if(this.undriedItem[index] == null) return false;
		return this.currentTimes[index] == this.craftingTimes[index];
	}

	private boolean putItemInFreeSpace(IItemCanBeDried item, boolean simple){
		if(this.undriedItem[0] == null){
			this.putItemIndex(0, item);
			return true;
		}else if(!simple){
			if(this.undriedItem[1] == null) {
				this.putItemIndex(1, item);
				return true;
			}
		}
		return false;
	}

	private void putItemIndex(int index, IItemCanBeDried item){
		this.undriedItem[index] = item;
		this.currentTimes[index] = 0;
		this.craftingTimes[index] = (int) (item.getDryingTime() * (0.8d + new Random().nextInt(5) * 0.1d));
		IBlockState state = world.getBlockState(pos);
		world.notifyBlockUpdate(pos, state, state, 2);
	}

	private void dropItemIndex(int index, boolean isDried, World worldIn, BlockPos pos){
		Item item = isDried ? this.undriedItem[index].getDriedItem() : this.undriedItem[index].getItem();
		int quantity = isDried ? this.undriedItem[index].getDriedItemQuantity() : this.undriedItem[index].getItemQuantity();
		for(int i = 0; i < quantity; i++){
			spawnAsEntity(worldIn, pos, new ItemStack(item, 1));
		}
		this.resetIndex(index);
	}

	private void resetIndex(int index){
		this.undriedItem[index] = null;
		this.craftingTimes[index] = 0;
		this.currentTimes[index] = 0;
		IBlockState state = world.getBlockState(pos);
		world.notifyBlockUpdate(pos, state, state, 2);
	}

	@Override
	public NBTTagCompound getUpdateTag(){
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag){
		this.readFromNBT(tag);
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket(){
		return new SPacketUpdateTileEntity(this.pos, 0, this.getUpdateTag());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		super.onDataPacket(net, packet);
		this.handleUpdateTag(packet.getNbtCompound());
	}

	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);

		for(int index = 0; index < 2; index++){
			this.undriedItem[index] = compound.hasKey("id_" + index, 8) ? ((IItemCanBeDried)Item.getByNameOrId(compound.getString("id_" + index))) : null;
			this.craftingTimes[index] = compound.getInteger("crafting_time_" + index);
			this.currentTimes[index] = compound.getInteger("current_time_" + index);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);

		for(int index = 0; index < 2; index++){
			if(this.undriedItem[index] != null){
				ResourceLocation resourcelocation = Item.REGISTRY.getNameForObject(this.undriedItem[index].getItem());
				if(resourcelocation != null) nbt.setString("id_" + index, resourcelocation.toString());
			}
			nbt.setInteger("crafting_time_" + index, (short)this.craftingTimes[index]);
			nbt.setInteger("current_time_" + index, (short)this.currentTimes[index]);
		}
		return nbt;
	}
}
