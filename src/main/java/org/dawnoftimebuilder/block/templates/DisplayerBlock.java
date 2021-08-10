package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import org.dawnoftimebuilder.tileentity.DisplayerTileEntity;

import javax.annotation.Nullable;

import static org.dawnoftimebuilder.registries.DoTBTileEntitiesRegistry.DISPLAYER_TE;

public abstract class DisplayerBlock extends WaterloggedBlock {

	public DisplayerBlock(Material materialIn, float hardness, float resistance) {
		super(materialIn, hardness, resistance);
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
		if(!world.isRemote()){
			TileEntity tileEntity = world.getTileEntity(pos);
			if(tileEntity instanceof INamedContainerProvider){
				NetworkHooks.openGui((ServerPlayerEntity) playerEntity, (INamedContainerProvider) tileEntity, tileEntity.getPos());
			}
		}
		return true;
	}

	//TODO Check if the inventory is properly dropped
	/*
	@Override
	public void onReplaced(BlockState oldState, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (oldState.getBlock() != newState.getBlock()) {
			TileEntity tileEntity = worldIn.getTileEntity(pos);
			if (tileEntity instanceof DisplayerTileEntity) {
				final NonNullList<ItemStack> inventory = ((DisplayerTileEntity) tileEntity).;
				for(ItemStack item : inventory){
					InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), item);
				}
			}
		}
		super.onReplaced(oldState, worldIn, pos, newState, isMoving);
	}*/

	public abstract double getDisplayerX(BlockState state);

	public abstract double getDisplayerY(BlockState state);

	public abstract double getDisplayerZ(BlockState state);
}