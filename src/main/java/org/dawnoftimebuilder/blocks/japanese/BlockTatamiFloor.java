package org.dawnoftimebuilder.blocks.japanese;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.dawnoftimebuilder.items.DoTBItems;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static org.dawnoftimebuilder.items.DoTBItems.tatami_mat;

public class BlockTatamiFloor extends BlockTatamiMat {

	private AxisAlignedBB AABB_COLLISION = new AxisAlignedBB(0.0d, 0.0D, 0.0d, 1.0d, 1.0625d, 1.0d);

	public BlockTatamiFloor() {
		super("tatami_floor");
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return FULL_BLOCK_AABB;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_COLLISION);
	}

	/**
	 * Called when a neighboring blocks was changed and marks that this state should perform any checks during a neighbor
	 * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
	 * blocks, etc.
	 */
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		EnumFacing facing = state.getValue(FACING);
		if(!canBlockStay(worldIn, pos)){
			if(state.getValue(PART) == EnumPartType.HEAD) spawnAsEntity(worldIn, pos, new ItemStack(DoTBItems.tatami_mat, 1));
			worldIn.setBlockState(pos, Blocks.PLANKS.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.SPRUCE));
			worldIn.setBlockState(pos.offset(facing), Blocks.PLANKS.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.SPRUCE));
		}
		if (state.getValue(PART) == BlockTatamiFloor.EnumPartType.FOOT) {
			if (worldIn.getBlockState(pos.offset(facing)).getBlock() != this) worldIn.setBlockState(pos, Blocks.PLANKS.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.SPRUCE));
		} else if (worldIn.getBlockState(pos.offset(facing.getOpposite())).getBlock() != this) {
			if (!worldIn.isRemote) spawnAsEntity(worldIn, pos, new ItemStack(DoTBItems.tatami_mat, 1));
			worldIn.setBlockState(pos, Blocks.PLANKS.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.SPRUCE));
		}
	}

	@Override
	public boolean canBlockStay(World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos.up());
		return !state.isFullBlock();
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return state.getValue(PART) == BlockTatamiFloor.EnumPartType.FOOT ? Items.AIR : tatami_mat;
	}

	@Override
	public EnumPushReaction getPushReaction(IBlockState state) {
		return this.material.getPushReaction();
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing facing) {
		return BlockFaceShape.SOLID;
	}

	/**
	 * Called before the Block is set to air in the world. Called regardless of if the player's tool can actually
	 * collect this blocks
	 */
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		if (player.capabilities.isCreativeMode && state.getValue(PART) == BlockTatamiFloor.EnumPartType.FOOT) {
			pos = pos.offset(state.getValue(FACING));
			if (worldIn.getBlockState(pos).getBlock() == this) worldIn.setBlockState(pos, Blocks.PLANKS.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.SPRUCE));

		}
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return true;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return true;
	}

	@Override
	public Item getCustomItemBlock() {
		return null;
	}

	@Override
	protected boolean canSilkHarvest(){
		return false;
	}

	@Override
	public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, net.minecraft.world.IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		drops.add(new ItemStack(Item.getItemFromBlock(Blocks.PLANKS), 1, 1));
		drops.add(new ItemStack(state.getValue(PART) == BlockTatamiMat.EnumPartType.FOOT ? Items.AIR : tatami_mat));
	}
}