package org.dawnoftimebuilder;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import org.dawnoftimebuilder.registries.DoTBBlocksRegistry;
import org.dawnoftimebuilder.registries.DoTBItemsRegistry;

import java.io.File;
import java.util.HashMap;

public class DoTBConfigs {

    public static Configuration config;

    public static HashMap<String, Boolean> enabledMap = new HashMap<>();

    public static void init(File configFile){
        if(config == null) {
            config = new Configuration(configFile);
            loadConfig();
        }
    }

    public static void loadConfig(){
        String configName = "Blocks";
        config.setCategoryComment(configName, "Disable Blocks from this group.");
        for (Block block : DoTBBlocksRegistry.blocks_list) {
            String name = block.getRegistryName().getPath();
            Boolean enabled = config.get(configName, name, true).getBoolean();
            enabledMap.put(name, enabled);
        }

        configName = "Items";
        config.setCategoryComment(configName, "Disable Items from this group.");
        for (Item item : DoTBItemsRegistry.items_list) {
            String name = item.getRegistryName().getPath();
            if(enabledMap.containsKey(name)) continue; //Avoid adding a block and a customItemBlock with the same name
            Boolean enabled = config.get(configName, name, true).getBoolean();
            enabledMap.put(name, enabled);
        }

        if(config.hasChanged()){
            config.save();
        }
    }
}
