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
import net.minecraft.world.World;
import org.dawnoftimebuilder.blocks.japanese.BlockPaperDoor;

import static org.dawnoftimebuilder.blocks.DoTBBlocks.paper_door;

public class ItemPaperDoor extends ItemBlock {

    public ItemPaperDoor() {
        super(paper_door);
    }

    /**
     * Called when a Block is right-clicked with this Item
     */
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (facing != EnumFacing.UP) return EnumActionResult.FAIL;
        else {
            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();

            if (!block.isReplaceable(worldIn, pos)) pos = pos.offset(facing);

            ItemStack itemstack = player.getHeldItem(hand);

            if (player.canPlayerEdit(pos, facing, itemstack) && this.block.canPlaceBlockAt(worldIn, pos)) {
               
            	EnumFacing enumfacing = EnumFacing.fromAngle((double)player.rotationYaw);
                int i = enumfacing.getXOffset();
                int j = enumfacing.getZOffset();
                boolean flag = i < 0 && hitZ < 0.5F || i > 0 && hitZ > 0.5F || j < 0 && hitX > 0.5F || j > 0 && hitX < 0.5F;
                placeDoor(worldIn, pos, enumfacing, this.block, flag);
                SoundType soundtype = worldIn.getBlockState(pos).getBlock().getSoundType(worldIn.getBlockState(pos), worldIn, pos, player);
                worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                itemstack.shrink(1);
                
                return EnumActionResult.SUCCESS;
            
            } else return EnumActionResult.FAIL;
        }
    }

    private static void placeDoor(World worldIn, BlockPos pos, EnumFacing facing, Block door, boolean isRightHinge) {
        BlockPos posLeft = pos.offset(facing.rotateY());
        BlockPos posRight = pos.offset(facing.rotateYCCW());
        int i = (worldIn.getBlockState(posRight).isNormalCube() ? 1 : 0) + (worldIn.getBlockState(posRight.up()).isNormalCube() ? 1 : 0);
        int j = (worldIn.getBlockState(posLeft).isNormalCube() ? 1 : 0) + (worldIn.getBlockState(posLeft.up()).isNormalCube() ? 1 : 0);
        boolean flag = worldIn.getBlockState(posRight).getBlock() == door || worldIn.getBlockState(posRight.up()).getBlock() == door;
        boolean flag1 = worldIn.getBlockState(posLeft).getBlock() == door || worldIn.getBlockState(posLeft.up()).getBlock() == door;

        if ((!flag || flag1) && j <= i){
            if (flag1 && !flag || j < i) isRightHinge = false;
        } else isRightHinge = true;

        BlockPos posUp = pos.up();
        boolean flag2 = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(posUp);
        IBlockState iblockstate = door.getDefaultState().withProperty(BlockPaperDoor.FACING, facing).withProperty(BlockPaperDoor.HINGE, isRightHinge ? BlockPaperDoor.EnumHingePosition.RIGHT : BlockPaperDoor.EnumHingePosition.LEFT).withProperty(BlockPaperDoor.POWERED, Boolean.valueOf(flag2)).withProperty(BlockPaperDoor.OPEN, Boolean.valueOf(flag2));
        worldIn.setBlockState(pos, iblockstate.withProperty(BlockPaperDoor.HALF, BlockPaperDoor.EnumDoorHalf.LOWER), 2);
        worldIn.setBlockState(posUp, iblockstate.withProperty(BlockPaperDoor.HALF, BlockPaperDoor.EnumDoorHalf.UPPER), 2);
        worldIn.notifyNeighborsOfStateChange(pos, door, false);
        worldIn.notifyNeighborsOfStateChange(posUp, door, false);
    }
}