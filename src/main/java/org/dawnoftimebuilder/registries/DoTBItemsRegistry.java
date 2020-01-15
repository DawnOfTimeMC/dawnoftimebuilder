package org.dawnoftimebuilder.registries;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
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
import org.dawnoftimebuilder.blocks.IBlockMeta;
import org.dawnoftimebuilder.enums.IEnumMetaVariants;
import org.dawnoftimebuilder.items.IItemCanBeDried;
import org.dawnoftimebuilder.items.egyptian.ItemPharaohArmor;
import org.dawnoftimebuilder.items.french.ItemIronPlateArmor;
import org.dawnoftimebuilder.items.general.DoTBItem;
import org.dawnoftimebuilder.items.general.DoTBItemCanBeDried;
import org.dawnoftimebuilder.items.general.DoTBItemHat;
import org.dawnoftimebuilder.items.japanese.ItemJapaneseLightArmor;
import org.dawnoftimebuilder.items.japanese.ItemOYoroiArmor;
import org.dawnoftimebuilder.items.japanese.ItemRaijinArmor;
import org.dawnoftimebuilder.items.japanese.ItemTachiSword;

import java.util.*;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

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
		DoTBItem silk_worms = new DoTBItem("silk_worms");
		DoTBItem tea_leaves = new DoTBItem("tea_leaves");

		addToList(
				new ItemIronPlateArmor(EntityEquipmentSlot.FEET),
				new ItemIronPlateArmor(EntityEquipmentSlot.CHEST),
				new ItemIronPlateArmor(EntityEquipmentSlot.HEAD),
				new ItemIronPlateArmor(EntityEquipmentSlot.LEGS),
				new DoTBItem("wax"),
				new DoTBItemHat("bamboo_hat"),
				new DoTBItem("grey_tile"),
				new DoTBItem("grey_clay_tile"),
				new DoTBItem("mulberry_leaves"),
				new DoTBItemCanBeDried("silk_worm_hatchery", 1,60, silk_worms, 1),
				silk_worms,
				new DoTBItemCanBeDried("camellia_leaves", 1,40, tea_leaves, 1),
				tea_leaves,
				new DoTBItem("silk_worm_eggs"),
				new DoTBItem("silk_cocoons"),
				new DoTBItem("silk"),
				new ItemJapaneseLightArmor(EntityEquipmentSlot.FEET),
				new ItemJapaneseLightArmor(EntityEquipmentSlot.CHEST),
				new ItemJapaneseLightArmor(EntityEquipmentSlot.HEAD),
				new ItemJapaneseLightArmor(EntityEquipmentSlot.LEGS),
				new ItemOYoroiArmor(EntityEquipmentSlot.FEET),
				new ItemOYoroiArmor(EntityEquipmentSlot.CHEST),
				new ItemOYoroiArmor(EntityEquipmentSlot.HEAD),
				new ItemOYoroiArmor(EntityEquipmentSlot.LEGS),
				new ItemPharaohArmor(EntityEquipmentSlot.FEET),
				new ItemPharaohArmor(EntityEquipmentSlot.CHEST),
				new ItemPharaohArmor(EntityEquipmentSlot.HEAD),
				new ItemPharaohArmor(EntityEquipmentSlot.LEGS),
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
		boolean enabled;
		for(Item item : items_list){

			if(DoTBConfigs.enabledMap.containsKey(Objects.requireNonNull(item.getRegistryName()).getPath())){
				if(!DoTBConfigs.enabledMap.get(Objects.requireNonNull(item.getRegistryName()).getPath())) continue;
				//Disabled in the config file → skip registering the item
			}

			registry.register(item);
		}
	}

	@SideOnly(Side.CLIENT)
	public static void registerItemsModels(){
		for(Item item : items_list){
			if(item instanceof ItemBlock){
				Block block = ((ItemBlock) item).getBlock();
				if(block instanceof IBlockMeta){
					IBlockMeta blockMeta = (IBlockMeta) block;
					for(IEnumMetaVariants variant : blockMeta.getVariants()){
						setResourceLocation(item, variant.getMetadata(), variant.getName());
					}
					continue;
				}
			}
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

	@SideOnly(Side.CLIENT)
	private static void setResourceLocation(Item item, int meta, ModelResourceLocation location){
		ModelLoader.setCustomModelResourceLocation(item, meta, location);
		if(item instanceof IItemCanBeDried) {
			preInitCustomModels("dryer", location.getPath());
			preInitCustomModels("dryer", Objects.requireNonNull(((IItemCanBeDried) item).getDriedItem().getRegistryName()).getPath());
		}
	}

	@SideOnly(Side.CLIENT)
	private static void preInitCustomModels(String path, String name){
		CUSTOM_MODELS.put(path + "/" + name, null);
	}

	@SideOnly(Side.CLIENT)
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
