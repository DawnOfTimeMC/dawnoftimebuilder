package org.dawnoftimebuilder.crafts;

import net.minecraftforge.oredict.OreDictionary;

import static org.dawnoftimebuilder.blocks.DoTBBlocks.cobbled_limestone;
import static org.dawnoftimebuilder.blocks.DoTBBlocks.limestone_brick;

public class OreDictionaryHandler {

    public static void init(){
        OreDictionary.registerOre("stoneLimestone", limestone_brick);
        OreDictionary.registerOre("stoneLimestone", cobbled_limestone);
    }
}
