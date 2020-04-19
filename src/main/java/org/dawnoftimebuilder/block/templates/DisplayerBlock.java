package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import org.dawnoftimebuilder.tileentity.DisplayerTileEntity;

import javax.annotation.Nullable;

import static org.dawnoftimebuilder.registries.DoTBTileEntitiesRegistry.DISPLAYER_TE;

public abstract class DisplayerBlock extends BlockDoTB {

	public DisplayerBlock(String name, Material materialIn, float hardness, float resistance) {
		super(name, materialIn, hardness, resistance);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world){
		return DISPLAYER_TE.create();
	}

	@Override
	public boolean isSolid(final BlockState state) {
		return false;
	}

	@Override
	public boolean onBlockActivated(BlockState blockState, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult rayTraceResult) {
		/*
		if(!world.isRemote()){
			TileEntity tileEntity = world.getTileEntity(pos);
			if(tileEntity instanceof DisplayerTileEntity){
				playerEntity.openContainer((INamedContainerProvider) tileEntity);
				return true;
			}
		}
		*/
		return false;
	}

	@Override
	public boolean eventReceived(BlockState state, World worldIn, BlockPos pos, int id, int param) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if(tileentity == null) return super.eventReceived(state, worldIn, pos, id, param);
		return tileentity.receiveClientEvent(id, param);
	}

	@Override
	public void onReplaced(BlockState oldState, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (oldState.getBlock() != newState.getBlock()) {
			TileEntity tileEntity = worldIn.getTileEntity(pos);
			if (tileEntity instanceof DisplayerTileEntity) {
				final NonNullList<ItemStack> inventory = ((DisplayerTileEntity) tileEntity).getItems();
				for(ItemStack item : inventory){
					InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), item);
				}
			}
		}
		super.onReplaced(oldState, worldIn, pos, newState, isMoving);
	}

	public abstract double getDisplayerX(BlockState state);

	public abstract double getDisplayerY(BlockState state);

	public abstract double getDisplayerZ(BlockState state);
}
