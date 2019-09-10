package org.dawnoftimebuilder.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import org.dawnoftimebuilder.DoTBConfigs;
import org.dawnoftimebuilder.items.french.ItemIronPlateArmor;
import org.dawnoftimebuilder.items.global.DoTBItem;
import org.dawnoftimebuilder.items.global.DoTBItemCanBeDried;
import org.dawnoftimebuilder.items.global.DoTBItemHat;
import org.dawnoftimebuilder.items.japanese.ItemJapaneseLightArmor;
import org.dawnoftimebuilder.items.japanese.ItemOYoroiArmor;
import org.dawnoftimebuilder.items.japanese.ItemRaijinArmor;
import org.dawnoftimebuilder.items.japanese.ItemTachiSword;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.dawnoftimebuilder.items.DoTBItems.silk;
import static org.dawnoftimebuilder.items.DoTBItems.tea_leaves;

public class DoTBItemsRegistry {

	public static List<Item> items_list = new ArrayList<>();
	private static void addToList(Item... items){
		Collections.addAll(items_list, items);
	}

	public static void init() {
		addToList(
				new ItemIronPlateArmor(EntityEquipmentSlot.FEET),
				new ItemIronPlateArmor(EntityEquipmentSlot.CHEST),
				new ItemIronPlateArmor(EntityEquipmentSlot.HEAD),
				new ItemIronPlateArmor(EntityEquipmentSlot.LEGS),
				new DoTBItemHat("bamboo_hat"),
				new DoTBItem("grey_tile"),
				new DoTBItem("grey_clay_tile"),
				new DoTBItemCanBeDried("camellia_leaves", 1,400, tea_leaves, 1),
				new DoTBItem("tea_leaves"),
				new DoTBItemCanBeDried("silk_cocoons", 1,600, silk, 1),
				new DoTBItem("silk"),
				new ItemJapaneseLightArmor(EntityEquipmentSlot.FEET),
				new ItemJapaneseLightArmor(EntityEquipmentSlot.CHEST),
				new ItemJapaneseLightArmor(EntityEquipmentSlot.HEAD),
				new ItemJapaneseLightArmor(EntityEquipmentSlot.LEGS),
				new ItemOYoroiArmor(EntityEquipmentSlot.FEET),
				new ItemOYoroiArmor(EntityEquipmentSlot.CHEST),
				new ItemOYoroiArmor(EntityEquipmentSlot.HEAD),
				new ItemOYoroiArmor(EntityEquipmentSlot.LEGS),
				new ItemRaijinArmor(EntityEquipmentSlot.FEET),
				new ItemRaijinArmor(EntityEquipmentSlot.CHEST),
				new ItemRaijinArmor(EntityEquipmentSlot.HEAD),
				new ItemRaijinArmor(EntityEquipmentSlot.LEGS),
				new ItemTachiSword()
		);
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();
		for(Item item : items_list){

			if(!DoTBConfigs.enabledMap.get(item.getRegistryName().getPath())){
				//Disabled in the config file → skip registering the item
				continue;
			}

			registry.register(item);
			setResourceLocation(item, 0);
		}
	}

	@SideOnly(Side.CLIENT)
	private static void setResourceLocation(Item item, int meta){
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName().toString(), "inventory"));
	}
}
