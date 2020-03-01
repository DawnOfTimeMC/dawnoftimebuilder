package org.dawnoftimebuilder.items.general;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import org.dawnoftimebuilder.blocks.general.DoTBBlockSoilCrops;

public class DoTBItemSoilSeeds extends ItemBlock implements IPlantable {
    private final DoTBBlockSoilCrops crops;

    public DoTBItemSoilSeeds(DoTBBlockSoilCrops crops) {
        super(crops);
        this.crops = crops;
    }

    @Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemstack = player.getHeldItem(hand);
        Block block = worldIn.getBlockState(pos).getBlock();

        if(facing == EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, itemstack)) {
            if(this.crops.getSoilBlock() == Blocks.WATER){
                if(block == Blocks.DIRT || block == Blocks.CLAY || block == Blocks.GRAVEL){
                    pos = pos.up();
                    block = worldIn.getBlockState(pos).getBlock();
                }
            }
            if (block == this.crops.getSoilBlock() && this.hasEnoughAir(worldIn, pos.up())){
                this.spawnCrops(worldIn, pos.up());
                if (player instanceof EntityPlayerMP) CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos.up(), itemstack);
                itemstack.shrink(1);
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.FAIL;
    }
    
    protected boolean hasEnoughAir(World world, BlockPos soilPos){
    	return world.isAirBlock(soilPos);
    }

    protected void spawnCrops(World world, BlockPos cropPos){
    	world.setBlockState(cropPos, this.crops.getDefaultState());
    }
    
    @Override
    public EnumPlantType getPlantType(net.minecraft.world.IBlockAccess world, BlockPos pos){
        return EnumPlantType.Crop;
    }

    @Override
    public IBlockState getPlant(net.minecraft.world.IBlockAccess world, BlockPos pos) {
        return this.crops.getDefaultState();
    }
}