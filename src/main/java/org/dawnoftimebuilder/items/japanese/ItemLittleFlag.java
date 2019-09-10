package org.dawnoftimebuilder.items.japanese;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dawnoftimebuilder.tileentity.TileEntityLittleFlag;

import static org.dawnoftimebuilder.blocks.DoTBBlocks.little_flag;

public class ItemLittleFlag extends ItemBlock {

	public ItemLittleFlag() {
		super(little_flag);
	}

    /**
     * Called when a Block is right-clicked with this Item
     */
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    	
        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();

        if (!block.isReplaceable(worldIn, pos)) pos = pos.offset(facing);

        ItemStack itemstack = player.getHeldItem(hand);
        if (!itemstack.isEmpty() && player.canPlayerEdit(pos, facing, itemstack) && worldIn.mayPlace(this.block, pos, false, facing, null)) {
            int i = this.getMetadata(itemstack.getMetadata());
            IBlockState state = this.block.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, i, player, hand);

            if (placeBlockAt(itemstack, player, worldIn, pos, facing, hitX, hitY, hitZ, state)) {
                SoundType soundtype = worldIn.getBlockState(pos).getBlock().getSoundType(worldIn.getBlockState(pos), worldIn, pos, player);
                worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                TileEntity tileentity = worldIn.getTileEntity(pos);
                if (tileentity instanceof TileEntityLittleFlag) ((TileEntityLittleFlag)tileentity).setItemColor(this.getItemColor(itemstack));
                itemstack.shrink(1);
            }
            return EnumActionResult.SUCCESS;
        }
        else return EnumActionResult.FAIL;
    }

	public int getItemColor(ItemStack item){
		
		NBTTagCompound nbt = item.getSubCompound("BlockEntityTag");
		if(nbt != null)
			return nbt.getInteger("Color");
		else{
			return 0xFFFFFF;
		}
	}

	public void setColor(ItemStack itemstack, int k2) {
		itemstack.getOrCreateSubCompound("BlockEntityTag").setInteger("Color", k2);
	}
}
