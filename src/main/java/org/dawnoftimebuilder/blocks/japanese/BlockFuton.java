package org.dawnoftimebuilder.blocks.japanese;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.dawnoftimebuilder.items.japanese.ItemFuton;
import org.dawnoftimebuilder.blocks.IBlockCustomItem;
import org.dawnoftimebuilder.tileentity.DoTBTileEntityBed;

import javax.annotation.Nullable;
import java.util.Random;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;
import static org.dawnoftimebuilder.items.DoTBItems.futon;

public class BlockFuton extends BlockBed implements IBlockCustomItem {

	private static final AxisAlignedBB FUTON_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D);

	public BlockFuton() {
		super();
		this.setHardness(0.2F);
		this.setRegistryName(MOD_ID, "futon");
		this.setTranslationKey(MOD_ID + "." + "futon");
		this.setCreativeTab(DOTB_TAB);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return FUTON_AABB;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public TileEntity createNewTileEntity(final World worldIn, final int meta) {
		return new DoTBTileEntityBed();
	}

	@Override
	public boolean isBed(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable Entity player) {
		return true;
	}

	/**
	 * Get the MapColor for this Block and the given BlockState
	 */
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return MapColor.BROWN;
	}

	/**
	 * Called when a neighboring blocks was changed and marks that this state should perform any checks during a neighbor
	 * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
	 * blocks, etc.
	 */
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		EnumFacing facing = state.getValue(FACING);
		if (state.getValue(PART) == BlockBed.EnumPartType.FOOT){
			if (worldIn.getBlockState(pos.offset(facing)).getBlock() != this) worldIn.setBlockToAir(pos);
		} else if (worldIn.getBlockState(pos.offset(facing.getOpposite())).getBlock() != this) {
			if (!worldIn.isRemote) this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return state.getValue(PART) == BlockBed.EnumPartType.FOOT ? Items.AIR : futon;
	}

	/**
	 * Spawns this Block's drops into the World as EntityItems.
	 */
	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
		if (state.getValue(PART) == BlockBed.EnumPartType.HEAD) {
			spawnAsEntity(worldIn, pos, new ItemStack(futon, 1));
		}
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(futon);
	}

	@Override
	public Item getCustomItemBlock() {
		return new ItemFuton()
				.setRegistryName(this.getRegistryName())
				.setTranslationKey(this.getTranslationKey());
	}

	/**
	 * Spawns the blocks's drops in the world. By the time this is called the Block has possibly been set to air via
	 * Block.removedByPlayer
	 */
	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity tileEntity, ItemStack stack) {
		if (state.getValue(PART) == BlockBed.EnumPartType.HEAD) {
			spawnAsEntity(worldIn, pos, this.getItem(worldIn, pos, state));
		} else {
			super.harvestBlock(worldIn, player, pos, state, null, stack);
		}
	}
}