package org.dawnoftimebuilder.items.japanese;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dawnoftimebuilder.blocks.japanese.BlockStickBundle;
import org.dawnoftimebuilder.enums.EnumsBlock;

import static org.dawnoftimebuilder.blocks.DoTBBlocks.*;

public class ItemStickBundle extends ItemBlock {

	public ItemStickBundle() {
		super(stick_bundle);
	}

	/**
	 * Called when a Block is right-clicked with this Item
	 */
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) return EnumActionResult.SUCCESS;
		else if (facing != EnumFacing.DOWN) return EnumActionResult.FAIL;
		else {
			IBlockState state = worldIn.getBlockState(pos);
			Block block = state.getBlock();
			if (!block.isReplaceable(worldIn, pos)) pos = pos.down();
			BlockPos posDown = pos.down();
			ItemStack itemstack = player.getHeldItem(hand);

			if (player.canPlayerEdit(pos, facing, itemstack) && player.canPlayerEdit(posDown, facing, itemstack)) {
				IBlockState stateDown = worldIn.getBlockState(posDown);
				boolean canPutBlock = block.isReplaceable(worldIn, pos) || worldIn.isAirBlock(pos);
				boolean canPutBlockDown = stateDown.getBlock().isReplaceable(worldIn, posDown) || worldIn.isAirBlock(posDown);
				if (canPutBlock && canPutBlockDown){
					worldIn.setBlockState(pos, stick_bundle.getDefaultState().withProperty(BlockStickBundle.HALF, EnumsBlock.EnumHalf.TOP), 10);
					worldIn.setBlockState(posDown, stick_bundle.getDefaultState(), 10);
					SoundType soundtype = stick_bundle.getSoundType(stateDown, worldIn, pos, player);
					worldIn.playSound(null, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
					itemstack.shrink(1);
					return EnumActionResult.SUCCESS;
				}
			}
		}
		return EnumActionResult.FAIL;
	}
}