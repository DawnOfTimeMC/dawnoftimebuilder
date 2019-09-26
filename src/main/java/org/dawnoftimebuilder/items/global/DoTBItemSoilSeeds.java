package org.dawnoftimebuilder.items.global;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
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

    /**
     * Called when a Block is right-clicked with this Item
     */
    @SuppressWarnings("deprecation")
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemstack = player.getHeldItem(hand);
        net.minecraft.block.state.IBlockState state = worldIn.getBlockState(pos);
        
    	if(this.crops.getSoilBlock() == Blocks.WATER){
    		RayTraceResult raytraceresult = this.rayTrace(worldIn, player, true);
            if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK){
                pos = raytraceresult.getBlockPos();
                state = worldIn.getBlockState(pos);
            }
        }
    	
        if (facing == EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, itemstack) && state.getBlock() == this.crops.getSoilBlock() && this.isEnoughAir(worldIn, pos) && (state.getBlock().getMaterial(state) != Material.WATER || state.getValue(BlockLiquid.LEVEL) == 0)){
            this.spawnCrops(worldIn, pos.up());

            if (player instanceof EntityPlayerMP) CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos.up(), itemstack);

            itemstack.shrink(1);
            return EnumActionResult.SUCCESS;

        }else return EnumActionResult.FAIL;
    }
    
    protected boolean isEnoughAir(World world, BlockPos soilPos){
    	return world.isAirBlock(soilPos.up());
    }

    protected void spawnCrops(World world, BlockPos cropPos){
    	world.setBlockState(cropPos, this.crops.getDefaultState());
    }
    
    @Override
    public EnumPlantType getPlantType(net.minecraft.world.IBlockAccess world, BlockPos pos){
        return net.minecraftforge.common.EnumPlantType.Crop;
    }

    @Override
    public IBlockState getPlant(net.minecraft.world.IBlockAccess world, BlockPos pos) {
        return this.crops.getDefaultState();
    }
}