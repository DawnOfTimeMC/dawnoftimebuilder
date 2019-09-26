package org.dawnoftimebuilder.items.japanese;

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

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;
import static org.dawnoftimebuilder.blocks.DoTBBlocks.*;
import static org.dawnoftimebuilder.blocks.japanese.BlockSpruceRoofSupport.FACING;
import static org.dawnoftimebuilder.blocks.japanese.BlockSpruceRoofSupportMerged.AABB_FULL;

public class ItemSpruceRoofSupport extends ItemBlock {

	public ItemSpruceRoofSupport() {
		super(spruce_roof_support);

		this.setRegistryName(MOD_ID, "spruce_roof_support");
		this.setTranslationKey(MOD_ID + ".spruce_roof_support");
	}

	/**
	 * Called when a Block is right-clicked with this Item
	 */
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack itemstack = player.getHeldItem(hand);

		if (!itemstack.isEmpty() && player.canPlayerEdit(pos.offset(facing), facing, itemstack)) {
			IBlockState state = worldIn.getBlockState(pos);
			if (state.getBlock() == grey_roof_tiles_slab) {
				DoTBBlockSlab slab = (DoTBBlockSlab) state.getBlock();
				if(slab.getSlabState(state) == DoTBBlockSlab.EnumSlab.TOP){
					IBlockState madeState = spruce_roof_support_merged.getDefaultState().withProperty(FACING, player.getHorizontalFacing());
					if (worldIn.checkNoEntityCollision(AABB_FULL.offset(pos)) && worldIn.setBlockState(pos, madeState, 11)) {
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

	@SideOnly(Side.CLIENT)
	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
		IBlockState state = worldIn.getBlockState(pos);
		if(state.getBlock() == grey_roof_tiles_slab) {
			DoTBBlockSlab slab = (DoTBBlockSlab) state.getBlock();
			if(slab.getSlabState(state) == DoTBBlockSlab.EnumSlab.BOTTOM) return true;
		}

		return super.canPlaceBlockOnSide(worldIn, pos, side, player, stack);
	}

	private boolean tryPlace(EntityPlayer player, ItemStack stack, World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);

		if(state.getBlock() == grey_roof_tiles_slab) {
			DoTBBlockSlab slab = (DoTBBlockSlab) state.getBlock();
			if (slab.getSlabState(state) == DoTBBlockSlab.EnumSlab.TOP) {
				IBlockState madeState = spruce_roof_support_merged.getDefaultState().withProperty(FACING, player.getHorizontalFacing());
				if (worldIn.checkNoEntityCollision(AABB_FULL.offset(pos)) && worldIn.setBlockState(pos, madeState, 11)) {
					SoundType soundtype = this.block.getSoundType(madeState, worldIn, pos, player);
					worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
					stack.shrink(1);
				}
				return true;
			}
		}
		return false;
	}
}