package org.dawnoftimebuilder;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.config.Configuration;
import org.dawnoftimebuilder.enums.EArmorMaterial;
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

        int durability;
        int[] reductionPoints;
        int enchantability;
        float toughness;
        for (EArmorMaterial armorMaterial : EArmorMaterial.values()) {
            configName = armorMaterial.getName();
            config.setCategoryComment(configName, "Set properties of the " + configName + " armor in this group.");
            durability = config.get(configName, "durability", armorMaterial.getDurability()).getInt();
            reductionPoints = armorMaterial.getReductionPoints();
            reductionPoints[0] = config.get(configName, "helmet_reduction_points", reductionPoints[0]).getInt();
            reductionPoints[2] = config.get(configName, "chest_reduction_points", reductionPoints[2]).getInt();
            reductionPoints[1] = config.get(configName, "leggings_reduction_points", reductionPoints[1]).getInt();
            reductionPoints[3] = config.get(configName, "feet_reduction_points", reductionPoints[3]).getInt();
            enchantability = config.get(configName, "enchantability", armorMaterial.getEnchantability()).getInt();
            toughness = (float) config.get(configName, "toughness", armorMaterial.getToughness()).getDouble();
            armorMaterial.loadConfig(durability, reductionPoints, enchantability, toughness);
        }

        if(config.hasChanged()){
            config.save();
        }
    }
}
