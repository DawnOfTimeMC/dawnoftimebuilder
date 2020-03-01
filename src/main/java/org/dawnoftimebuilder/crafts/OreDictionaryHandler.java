package org.dawnoftimebuilder.crafts;

import net.minecraftforge.oredict.OreDictionary;
import org.dawnoftimebuilder.items.DoTBItems;
import org.dawnoftimebuilder.blocks.DoTBBlocks;

public class OreDictionaryHandler {

	public static void init(){
		OreDictionary.registerOre("materialWax", DoTBItems.wax);
		OreDictionary.registerOre("materialBeesWax", DoTBItems.wax);
		OreDictionary.registerOre("materialPressedWax", DoTBItems.wax);
		OreDictionary.registerOre("itemBeeswax", DoTBItems.wax);
		OreDictionary.registerOre("stoneLimestone", DoTBBlocks.cobbled_limestone);
		OreDictionary.registerOre("stoneLimestone", DoTBBlocks.limestone_brick);
		OreDictionary.registerOre("cropRice", DoTBItems.rice);
		OreDictionary.registerOre("seedRice", DoTBItems.rice);
		OreDictionary.registerOre("foodRice", DoTBItems.rice);
	}
}
