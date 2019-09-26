package org.dawnoftimebuilder.blocks.japanese;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dawnoftimebuilder.blocks.DoTBBlocks;
import org.dawnoftimebuilder.blocks.IBlockCustomItem;
import org.dawnoftimebuilder.blocks.general.DoTBBlock;

import javax.annotation.Nullable;
import java.util.List;

public class BlockSmallTatamiFloor extends DoTBBlock implements IBlockCustomItem {

	private AxisAlignedBB AABB_COLLISION = new AxisAlignedBB(0.0d, 0.0D, 0.0d, 1.0d, 1.0625d, 1.0d);

	public BlockSmallTatamiFloor() {
		super("small_tatami_floor", Material.WOOD);
		this.setBurnable();
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_COLLISION);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)  {
		return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos);
	}

	private boolean canBlockStay(World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos.up());
		return !state.isFullBlock();
	}

	/**
	 * Called when a neighboring blocks was changed and marks that this state should perform any checks during a neighbor
	 * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
	 * blocks, etc.
	 */
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if(!canBlockStay(worldIn, pos)){
			spawnAsEntity(worldIn, pos, new ItemStack(Item.getItemFromBlock(DoTBBlocks.small_tatami_mat), 1));
			worldIn.setBlockState(pos, Blocks.PLANKS.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.SPRUCE));
		}
	}

	@Override
	protected boolean canSilkHarvest(){
		return false;
	}

	@Override
	public Item getCustomItemBlock(){
		return null;
	}

	@Override
	public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, net.minecraft.world.IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		drops.add(new ItemStack(Item.getItemFromBlock(Blocks.PLANKS), 1, 1));
		drops.add(new ItemStack(Item.getItemFromBlock(DoTBBlocks.small_tatami_mat), 1));
	}
}
