package org.dawnoftimebuilder.items.japanese;

import net.minecraft.item.ItemBlock;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.dawnoftimebuilder.blocks.japanese.BlockFuton;

import static org.dawnoftimebuilder.blocks.DoTBBlocks.futon;

public class ItemFuton extends ItemBlock {

    public ItemFuton() {
        super(futon);
    }

    /**
     * Called when a Block is right-clicked with this Item
     */
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) return EnumActionResult.SUCCESS;
        else if (facing != EnumFacing.UP) return EnumActionResult.FAIL;
        else {
            IBlockState state = worldIn.getBlockState(pos);
            Block block = state.getBlock();
            if (!block.isReplaceable(worldIn, pos)) pos = pos.up();

            int i = MathHelper.floor((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            EnumFacing direction = EnumFacing.byHorizontalIndex(i);
            ItemStack itemstack = player.getHeldItem(hand);
            BlockPos posTwo = pos.offset(direction);

            if (player.canPlayerEdit(pos, facing, itemstack) && player.canPlayerEdit(posTwo, facing, itemstack)) {
                IBlockState stateTwo = worldIn.getBlockState(posTwo);
                boolean canPutBlock = block.isReplaceable(worldIn, pos) || worldIn.isAirBlock(pos);
                boolean canPutBlockTwo = stateTwo.getBlock().isReplaceable(worldIn, posTwo) || worldIn.isAirBlock(posTwo);

                if (canPutBlock && canPutBlockTwo && !worldIn.isAirBlock(pos.down()) && !worldIn.isAirBlock(posTwo.down())) {
                    stateTwo = futon.getDefaultState().withProperty(BlockFuton.FACING, direction).withProperty(BlockFuton.PART, BlockFuton.EnumPartType.FOOT);
                    worldIn.setBlockState(pos, stateTwo, 10);
                    worldIn.setBlockState(posTwo, stateTwo.withProperty(BlockFuton.PART, BlockFuton.EnumPartType.HEAD), 10);
                    SoundType soundtype = stateTwo.getBlock().getSoundType(stateTwo, worldIn, pos, player);
                    worldIn.playSound(null, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);

                    itemstack.shrink(1);
                    return EnumActionResult.SUCCESS;
                } else return EnumActionResult.FAIL;
            } else return EnumActionResult.FAIL;
        }
    }
}