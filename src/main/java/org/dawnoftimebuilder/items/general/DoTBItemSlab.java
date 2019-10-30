package org.dawnoftimebuilder.items.general;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dawnoftimebuilder.blocks.general.DoTBBlockSlab;

import static net.minecraft.block.Block.FULL_BLOCK_AABB;

public class DoTBItemSlab extends ItemBlock {

	public DoTBItemSlab(DoTBBlockSlab slab) {
		super(slab);

		this.setTranslationKey(slab.getTranslationKey());
		this.setRegistryName(slab.getRegistryName());
	}

	/**
	 * Called when a Block is right-clicked with this Item
	 */
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack itemstack = player.getHeldItem(hand);

		if (!itemstack.isEmpty() && player.canPlayerEdit(pos.offset(facing), facing, itemstack)) {
			IBlockState state = worldIn.getBlockState(pos);
			if (state.getBlock() == this.block) {
				DoTBBlockSlab slab = (DoTBBlockSlab) this.block;
				if((slab.getSlabState(state) == DoTBBlockSlab.EnumSlab.BOTTOM && facing == EnumFacing.UP) || (slab.getSlabState(state) == DoTBBlockSlab.EnumSlab.TOP && facing == EnumFacing.DOWN)){
					IBlockState madeState = slab.getDoubleSlabDefaultState();
					if (worldIn.checkNoEntityCollision(FULL_BLOCK_AABB.offset(pos)) && worldIn.setBlockState(pos, madeState, 11)) {
						SoundType soundtype = this.block.getSoundType(madeState, worldIn, pos, player);
						worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
						itemstack.shrink(1);
						if (player instanceof EntityPlayerMP){
							CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos, itemstack);
						}
					}

					return EnumActionResult.SUCCESS;
				}
			}

			return this.tryPlace(player, itemstack, worldIn, pos.offset(facing)) ? EnumActionResult.SUCCESS : super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
		}else return EnumActionResult.FAIL;
	}

	public boolean tryPlace(EntityPlayer player, ItemStack stack, World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);

		if(state.getBlock() == this.block) {
			DoTBBlockSlab slab = (DoTBBlockSlab) this.block;
			if (slab.getSlabState(state) != DoTBBlockSlab.EnumSlab.DOUBLE) {
				IBlockState madeState = slab.getDoubleSlabDefaultState();
				if (worldIn.checkNoEntityCollision(FULL_BLOCK_AABB.offset(pos)) && worldIn.setBlockState(pos, madeState, 11)) {
					SoundType soundtype = this.block.getSoundType(madeState, worldIn, pos, player);
					worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
					stack.shrink(1);
				}
				return true;
			}
		}
		return false;
	}

	@SideOnly(Side.CLIENT)
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {

		BlockPos blockpos = pos;
		IBlockState state = worldIn.getBlockState(pos);
		if(state.getBlock() == this.block) {
			DoTBBlockSlab slab = (DoTBBlockSlab) this.block;
			if((slab.getSlabState(state) == DoTBBlockSlab.EnumSlab.BOTTOM && side == EnumFacing.UP) || (slab.getSlabState(state) == DoTBBlockSlab.EnumSlab.TOP && side == EnumFacing.DOWN))
				return true;
		}

		pos = pos.offset(side);
		state = worldIn.getBlockState(pos);
		if(state.getBlock() == this.block) {
			DoTBBlockSlab slab = (DoTBBlockSlab) this.block;
			if(slab.getSlabState(state) != DoTBBlockSlab.EnumSlab.DOUBLE) return true;
		}

		return super.canPlaceBlockOnSide(worldIn, blockpos, side, player, stack);
	}
}