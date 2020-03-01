package org.dawnoftimebuilder.items.japanese;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dawnoftimebuilder.items.general.DoTBItemSoilSeeds;

import static org.dawnoftimebuilder.blocks.DoTBBlocks.rice;

public class ItemRice extends DoTBItemSoilSeeds {

	public ItemRice() {
		super(rice);
	}

    protected boolean hasEnoughAir(World world, BlockPos soilPos){
    	return world.isAirBlock(soilPos.up()) && world.getBlockState(soilPos.down()).getBlock() != Blocks.WATER;
    }
}
