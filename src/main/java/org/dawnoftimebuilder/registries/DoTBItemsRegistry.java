package org.dawnoftimebuilder.registries;

import net.minecraft.item.Item;
import org.dawnoftimebuilder.items.egyptian.PharaohArmorItem;
import org.dawnoftimebuilder.items.german.IronPlateArmorItem;
import org.dawnoftimebuilder.items.japanese.JapaneseLightArmorItem;
import org.dawnoftimebuilder.items.japanese.OYoroiArmorItem;
import org.dawnoftimebuilder.items.japanese.RaijinArmorItem;
import org.dawnoftimebuilder.items.templates.CanBeDriedItem;
import org.dawnoftimebuilder.items.templates.ItemDoTB;
import org.dawnoftimebuilder.items.templates.SwordItemDoTB;

import java.util.*;

import static net.minecraft.inventory.EquipmentSlotType.*;
import static net.minecraft.item.ItemTier.DIAMOND;
import static org.dawnoftimebuilder.utils.DoTBFoods.GRAPPE;

public class DoTBItemsRegistry {

	public static final List<Item> ITEMS = new ArrayList<>();

	//General
	public static final Item SILK_WORMS = reg(new ItemDoTB("silk_worms"));
	public static final Item SILK_WORMS_HATCHERY = reg(new CanBeDriedItem("silk_worm_hatchery", 1,60, SILK_WORMS, 1));
	public static final Item SILK_WORM_EGGS = reg(new ItemDoTB("silk_worm_eggs"));
	public static final Item SILK_COCOONS = reg(new ItemDoTB("silk_cocoons"));
	public static final Item SILK = reg(new ItemDoTB("silk"));
	public static final Item TEA_LEAVES = reg(new ItemDoTB("tea_leaves"));
	public static final Item CAMELLIA_LEAVES = reg(new CanBeDriedItem("camellia_leaves", 1,40, TEA_LEAVES, 1));
	public static final Item WAX = reg(new ItemDoTB("wax"));
	public static final Item BAMBOO_HAT = reg(new ItemDoTB("bamboo_hat"));//TODO Make the model in .json !
	public static final Item GRAY_TILE = reg(new ItemDoTB("gray_tile"));
	public static final Item GRAY_CLAY_TILE = reg(new ItemDoTB("gray_clay_tile"));
	public static final Item MULBERRY_LEAVES = reg(new ItemDoTB("mulberry_leaves"));
	public static final Item GRAPE = reg(new ItemDoTB("grape", new Item.Properties().food(GRAPPE)));
	public static final Item GRAPE_SEEDS = reg(new ItemDoTB("grape_seeds"));
	//public static final Item CLEMATIS_SEEDS = reg(new ItemDoTB("clematis_seeds"));
	public static final Item TACHI_SWORD = reg(new SwordItemDoTB("tachi_sword", DIAMOND, 3, -2.4F));//TODO import the model!!

	//Armors
	public static final Item IRON_PLATE_ARMOR_HEAD = reg(new IronPlateArmorItem(HEAD));
	public static final Item IRON_PLATE_ARMOR_CHEST = reg(new IronPlateArmorItem(CHEST));
	public static final Item IRON_PLATE_ARMOR_LEGS = reg(new IronPlateArmorItem(LEGS));
	public static final Item IRON_PLATE_ARMOR_FEET = reg(new IronPlateArmorItem(FEET));
	public static final Item PHARAOH_ARMOR_HEAD = reg(new PharaohArmorItem(HEAD));
	public static final Item PHARAOH_ARMOR_CHEST = reg(new PharaohArmorItem(CHEST));
	public static final Item PHARAOH_ARMOR_LEGS = reg(new PharaohArmorItem(LEGS));
	public static final Item PHARAOH_ARMOR_FEET = reg(new PharaohArmorItem(FEET));
	public static final Item JAPANESE_LIGHT_ARMOR_HEAD = reg(new JapaneseLightArmorItem(HEAD));
	public static final Item JAPANESE_LIGHT_ARMOR_CHEST = reg(new JapaneseLightArmorItem(CHEST));
	public static final Item JAPANESE_LIGHT_ARMOR_LEGS = reg(new JapaneseLightArmorItem(LEGS));
	public static final Item JAPANESE_LIGHT_ARMOR_FEET = reg(new JapaneseLightArmorItem(FEET));
	public static final Item O_YOROI_ARMOR_HEAD = reg(new OYoroiArmorItem(HEAD));
	public static final Item O_YOROI_ARMOR_CHEST = reg(new OYoroiArmorItem(CHEST));
	public static final Item O_YOROI_ARMOR_LEGS = reg(new OYoroiArmorItem(LEGS));
	public static final Item O_YOROI_ARMOR_FEET = reg(new OYoroiArmorItem(FEET));
	public static final Item RAIJIN_ARMOR_HEAD = reg(new RaijinArmorItem(HEAD));
	public static final Item RAIJIN_ARMOR_CHEST = reg(new RaijinArmorItem(CHEST));
	public static final Item RAIJIN_ARMOR_LEGS = reg(new RaijinArmorItem(LEGS));
	public static final Item RAIJIN_ARMOR_FEET = reg(new RaijinArmorItem(FEET));

	private static Item reg(Item item){
		ITEMS.add(item);
		return item;
	}

/*
	public static void registerItems(RegistryEvent.Register<Item> event) {

		init();

		IForgeRegistry<Item> registry = event.getRegistry();
		boolean enabled;
		for(Item item : ITEMS){

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
	}*/
}
