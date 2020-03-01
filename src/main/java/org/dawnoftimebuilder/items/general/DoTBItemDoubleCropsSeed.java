package org.dawnoftimebuilder.items.general;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dawnoftimebuilder.blocks.general.DoTBBlockDoubleCrops;
import org.dawnoftimebuilder.enums.EnumsBlock;

public class DoTBItemDoubleCropsSeed extends DoTBItemSoilSeeds {

	private DoTBBlockDoubleCrops crops;
	
	public DoTBItemDoubleCropsSeed(DoTBBlockDoubleCrops crops) {
		super(crops);
		this.crops = crops;
	}

	@Override
    protected boolean hasEnoughAir(World world, BlockPos soilPos){
		return world.isAirBlock(soilPos.up()) && (crops.getAgeReachingTopBlock() > 0 || world.isAirBlock(soilPos.up(2)));
	}
	
	@Override
	protected void spawnCrops(World world, BlockPos cropPos){
		boolean flag = this.crops.getAgeReachingTopBlock() == 0;
		
		if(flag){
			world.setBlockState(cropPos, this.crops.getDefaultState().withProperty(DoTBBlockDoubleCrops.HALF, EnumsBlock.EnumHalf.BOTTOM));
			world.setBlockState(cropPos.up(), this.crops.getDefaultState().withProperty(DoTBBlockDoubleCrops.HALF, EnumsBlock.EnumHalf.TOP));
		}else{
			world.setBlockState(cropPos, this.crops.getDefaultState());
		}

	}
	    
}
