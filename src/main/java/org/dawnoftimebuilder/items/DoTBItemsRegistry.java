package org.dawnoftimebuilder.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
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

import java.util.*;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;
import static org.dawnoftimebuilder.items.DoTBItems.silk;
import static org.dawnoftimebuilder.items.DoTBItems.tea_leaves;

public class DoTBItemsRegistry {

	public static List<Item> items_list = new ArrayList<>();
	private static void addToList(Item... items){
		Collections.addAll(items_list, items);
	}

	private static final HashMap<String, IBakedModel> CUSTOM_MODELS = new HashMap<>();
	public static IBakedModel getModel(String name){
		return CUSTOM_MODELS.get(name);
	}

	public static void init(){
		DoTBItem tea_leaves = new DoTBItem("tea_leaves");
		DoTBItem silk = new DoTBItem("silk");

		addToList(
				new ItemIronPlateArmor(EntityEquipmentSlot.FEET),
				new ItemIronPlateArmor(EntityEquipmentSlot.CHEST),
				new ItemIronPlateArmor(EntityEquipmentSlot.HEAD),
				new ItemIronPlateArmor(EntityEquipmentSlot.LEGS),
				new DoTBItemHat("bamboo_hat"),
				new DoTBItem("grey_tile"),
				new DoTBItem("grey_clay_tile"),
				new DoTBItemCanBeDried("camellia_leaves", 1,40, tea_leaves, 1),
				tea_leaves,
				new DoTBItemCanBeDried("silk_cocoons", 1,60, silk, 1),
				silk,
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

			if(!DoTBConfigs.enabledMap.get(Objects.requireNonNull(item.getRegistryName()).getPath())){
				//Disabled in the config file → skip registering the item
				continue;
			}

			registry.register(item);
			setResourceLocation(item);
		}
	}

	@SideOnly(Side.CLIENT)
	public static void setResourceLocation(Item item){
		setResourceLocation(item, 0, new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()).toString(), "inventory"));
	}

	@SideOnly(Side.CLIENT)
	public static void setResourceLocation(Item item, int meta, String variant){
		setResourceLocation(item, meta, new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()).toString() + "_" + variant, "inventory"));
	}

	private static void setResourceLocation(Item item, int meta, ModelResourceLocation location){
		ModelLoader.setCustomModelResourceLocation(item, meta, location);
		if(item instanceof IItemCanBeDried) {
			preInitCustomModels("dryer", location.getPath());
			preInitCustomModels("dryer", Objects.requireNonNull(((IItemCanBeDried) item).getDriedItem().getRegistryName()).getPath());
		}
	}

	private static void preInitCustomModels(String path, String name){
		CUSTOM_MODELS.put(path + "/" + name, null);
	}

	public static void initCustomModels(){
		HashMap<String, IBakedModel> models = new HashMap<>();

		for(String path : CUSTOM_MODELS.keySet()){
			IModel imodel = ModelLoaderRegistry.getModelOrMissing(new ResourceLocation(MOD_ID, path));
			IBakedModel ibakedmodel = imodel.bake(imodel.getDefaultState(), DefaultVertexFormats.BLOCK, location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString()));
			models.put(path.split("/")[1], ibakedmodel);
		}

		CUSTOM_MODELS.clear();
		CUSTOM_MODELS.putAll(models);
	}
}
