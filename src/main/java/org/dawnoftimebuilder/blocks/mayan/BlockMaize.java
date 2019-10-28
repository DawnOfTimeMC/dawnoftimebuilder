package org.dawnoftimebuilder.blocks.mayan;

import org.dawnoftimebuilder.blocks.general.DoTBBlockDoubleCrops;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

import static org.dawnoftimebuilder.items.DoTBItems.maize;

public class BlockMaize extends DoTBBlockDoubleCrops {

	public BlockMaize() {
		super("maize", Blocks.FARMLAND);
	}
	
    protected Item getSeed()
    {
        return maize;
    }

    protected Item getCrop()
    {
        return maize;
    }
	
	@Override
	public int getAgeReachingTopBlock() {
		return 0;
	}
}
