package org.dawnoftimebuilder.items.french;

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
import org.dawnoftimebuilder.blocks.DoTBBlocks;
import org.dawnoftimebuilder.blocks.french.BlockOakShutters;

public class ItemOakShutters extends ItemBlock {

    private final Block block;

    public ItemOakShutters() {
        super(DoTBBlocks.oak_shutters);
        this.block = DoTBBlocks.oak_shutters;
    }

    /**
     * Called when a Block is right-clicked with this Item
     */
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if(!worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos)) pos = pos.offset(facing);
		if(!worldIn.getBlockState(pos.up()).getBlock().isReplaceable(worldIn, pos.up())) return EnumActionResult.FAIL;

        ItemStack itemstack = player.getHeldItem(hand);

        if (player.canPlayerEdit(pos, facing, itemstack) && pos.getY() < worldIn.getHeight() - 1) {
        	EnumFacing enumfacing = EnumFacing.fromAngle((double)player.rotationYaw);
        	boolean canSupportLeft = BlockOakShutters.canSupportShutters(worldIn, pos.offset(enumfacing).offset(enumfacing.rotateYCCW()), enumfacing, BlockOakShutters.EnumHingePosition.LEFT);
        	if(BlockOakShutters.canSupportShutters(worldIn, pos.offset(enumfacing).offset(enumfacing.rotateY()), enumfacing, BlockOakShutters.EnumHingePosition.RIGHT)) {
            	if(canSupportLeft){
                    int i = enumfacing.getXOffset();
                    int j = enumfacing.getZOffset();
                    placeDoor(worldIn, pos, enumfacing, this.block, i < 0 && hitZ < 0.5F || i > 0 && hitZ > 0.5F || j < 0 && hitX > 0.5F || j > 0 && hitX < 0.5F);
            	} else {
            		placeDoor(worldIn, pos, enumfacing, this.block, true);
            	}
                SoundType soundtype = worldIn.getBlockState(pos).getBlock().getSoundType(worldIn.getBlockState(pos), worldIn, pos, player);
                worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                itemstack.shrink(1);
                return EnumActionResult.SUCCESS;
                
            }else if(canSupportLeft){
            	
        		placeDoor(worldIn, pos, enumfacing, this.block, false);
                SoundType soundtype = worldIn.getBlockState(pos).getBlock().getSoundType(worldIn.getBlockState(pos), worldIn, pos, player);
                worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                itemstack.shrink(1);
                return EnumActionResult.SUCCESS;
                
            }
        }
        return EnumActionResult.FAIL;
    }

    public static void placeDoor(World worldIn, BlockPos pos, EnumFacing facing, Block door, boolean isRightHinge) {
		BlockPos top = pos.up();
		
		boolean isPowered = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(top);
		
		IBlockState iblockstate = door.getDefaultState().withProperty(BlockOakShutters.FACING, facing.getOpposite()).withProperty(BlockOakShutters.HINGE, isRightHinge ? BlockOakShutters.EnumHingePosition.RIGHT : BlockOakShutters.EnumHingePosition.LEFT).withProperty(BlockOakShutters.POWERED, Boolean.valueOf(isPowered));
		iblockstate = iblockstate.withProperty(BlockOakShutters.OPEN, isPowered ? BlockOakShutters.getOpenPosition(iblockstate, worldIn, pos) : BlockOakShutters.EnumOpenPosition.CLOSED);
		worldIn.setBlockState(pos, iblockstate.withProperty(BlockOakShutters.HALF, BlockOakShutters.EnumDoorHalf.LOWER), 2);
		worldIn.setBlockState(top, iblockstate.withProperty(BlockOakShutters.HALF, BlockOakShutters.EnumDoorHalf.UPPER), 2);
    }
}