package org.dawnoftimebuilder.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
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

import static org.dawnoftimebuilder.registries.DoTBTileEntitiesRegistry.DRYER_TE;

public class DryerTileEntity extends TileEntity {

	private final ItemStackHandler itemHandler = createHandler();
	private IItemCanBeDried[] undriedItem = new IItemCanBeDried[2];
	private int[] craftingTimes = new int[2];
	private int[] currentTimes = new int[2];

	public DryerTileEntity() {
		super(DRYER_TE);
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

	//TODO Check good practice with sided class like this one.
	@OnlyIn(Dist.CLIENT)
	public IBakedModel getItemCustomModel(int index){
		Item item = (currentTimes[index] >= craftingTimes[index]) ? undriedItem[index].getDriedItem() : undriedItem[index].getItem();
		return Minecraft.getInstance().getModelManager().getModel(new ModelResourceLocation(IItemCanBeDried.getResourceLocation(Objects.requireNonNull(item.getRegistryName()).getPath()), ""));
	}

	@Override
	public void handleUpdateTag(CompoundNBT tag) {
		itemHandler.deserializeNBT(tag.getCompound("inv"));
		/*
		int lightLevel = 0;
		for(int index = 0; index < itemHandler.getSlots(); index++){
			if(itemHandler.getStackInSlot(index) != ItemStack.EMPTY){
				Item item = itemHandler.getStackInSlot(index).getItem();
				if(item instanceof BlockItem){
					Block block = ((BlockItem) item).getBlock();
					int testLight = (block instanceof IBlockSpecialDisplay) ? ((IBlockSpecialDisplay) block).getDisplayedLightLevel() : block.getLightValue(block.getDefaultState());
					if(testLight > lightLevel) lightLevel = testLight;
				}
			}
		}
		((DisplayerBlock) this.getBlockState().getBlock()).setLightCurrentValue(lightLevel);
		*/
		super.handleUpdateTag(tag);
	}

	@Override
	public CompoundNBT write(CompoundNBT tag) {
		tag.put("inv", itemHandler.serializeNBT());
		return super.write(tag);
	}

	@Override
	public void read(CompoundNBT tag) {
		itemHandler.deserializeNBT(tag.getCompound("inv"));
		super.read(tag);
	}

	private ItemStackHandler createHandler() {
		return new ItemStackHandler(9) {
			@Override
			public int getSlotLimit(int slot) {
				return 1;
			}
		};
	}
}