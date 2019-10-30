package org.dawnoftimebuilder.items.roman;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dawnoftimebuilder.blocks.DoTBBlocks;
import org.dawnoftimebuilder.blocks.general.DoTBBlockSlab;
import org.dawnoftimebuilder.items.general.DoTBItemSlab;

import static net.minecraft.block.Block.FULL_BLOCK_AABB;

public class ItemOchreRoofTilesSlab extends DoTBItemSlab {

	public ItemOchreRoofTilesSlab(DoTBBlockSlab slab) {
		super(slab);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack itemstack = player.getHeldItem(hand);
		if (!itemstack.isEmpty() && player.canPlayerEdit(pos.offset(facing), facing, itemstack) && facing == EnumFacing.UP) {
			IBlockState state = worldIn.getBlockState(pos);
			if (state.getBlock() == Blocks.STONE_SLAB) {
				if (state.getValue(BlockStoneSlab.VARIANT) == BlockStoneSlab.EnumType.SAND && !((BlockSlab) state.getBlock()).isDouble()) {
					if (state.getValue(BlockSlab.HALF) == BlockSlab.EnumBlockHalf.BOTTOM) {
						IBlockState madeState = DoTBBlocks.ochre_roof_tiles_merged.getDefaultState();
						if (worldIn.checkNoEntityCollision(FULL_BLOCK_AABB.offset(pos)) && worldIn.setBlockState(pos, madeState, 11)) {
							SoundType soundtype = DoTBBlocks.ochre_roof_tiles_merged.getSoundType(madeState, worldIn, pos, player);
							worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
							itemstack.shrink(1);
							if (player instanceof EntityPlayerMP) {
								CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, pos, itemstack);
							}
						}
						return EnumActionResult.SUCCESS;
					}
				}
			}
		}
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public boolean tryPlace(EntityPlayer player, ItemStack stack, World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);
		if(state.getBlock() == Blocks.STONE_SLAB) {
			if (!((BlockSlab) state.getBlock()).isDouble()) {
				IBlockState madeState = DoTBBlocks.ochre_roof_tiles_merged.getDefaultState();
				if (worldIn.checkNoEntityCollision(FULL_BLOCK_AABB.offset(pos)) && worldIn.setBlockState(pos, madeState, 11)) {
					SoundType soundtype = DoTBBlocks.ochre_roof_tiles_merged.getSoundType(madeState, worldIn, pos, player);
					worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
					stack.shrink(1);
				}
				return true;
			}
		}
		return super.tryPlace(player, stack, worldIn, pos);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
		BlockPos blockpos = pos;
		IBlockState state = worldIn.getBlockState(pos);
		if(state.getBlock() == Blocks.STONE_SLAB) {
			if(state.getValue(BlockStoneSlab.VARIANT) == BlockStoneSlab.EnumType.SAND && !((BlockSlab) state.getBlock()).isDouble()){
				if((state.getValue(BlockSlab.HALF) == BlockSlab.EnumBlockHalf.BOTTOM && side == EnumFacing.UP) || (state.getValue(BlockSlab.HALF) == BlockSlab.EnumBlockHalf.TOP && side == EnumFacing.DOWN))
					return true;
			}
		}

		pos = pos.offset(side);
		state = worldIn.getBlockState(pos);
		if(state.getBlock() == Blocks.STONE_SLAB) {
			if(state.getValue(BlockStoneSlab.VARIANT) == BlockStoneSlab.EnumType.SAND && ((BlockSlab) state.getBlock()).isDouble()) return true;
		}

		return super.canPlaceBlockOnSide(worldIn, blockpos, side, player, stack);
	}
}
