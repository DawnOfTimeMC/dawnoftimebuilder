package org.dawnoftimebuilder.registry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.SwordItem;
import org.dawnoftimebuilder.item.egyptian.PharaohArmorItem;
import org.dawnoftimebuilder.item.german.HolyArmorItem;
import org.dawnoftimebuilder.item.german.IronPlateArmorItem;
import org.dawnoftimebuilder.item.japanese.JapaneseLightArmorItem;
import org.dawnoftimebuilder.item.japanese.OYoroiArmorItem;
import org.dawnoftimebuilder.item.japanese.RaijinArmorItem;
import org.dawnoftimebuilder.item.templates.CustomArmorItem;
import org.dawnoftimebuilder.item.templates.HatItem;
import org.dawnoftimebuilder.item.templates.ItemDoTB;
import org.dawnoftimebuilder.util.DoTBConfig;
import org.dawnoftimebuilder.util.DoTBFoods;

import java.util.*;

import static net.minecraft.inventory.EquipmentSlotType.*;
import static net.minecraft.item.ItemTier.DIAMOND;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;
import static org.dawnoftimebuilder.registry.DoTBEntitiesRegistry.JAPANESE_DRAGON_ENTITY;
import static org.dawnoftimebuilder.registry.DoTBEntitiesRegistry.SILKMOTH_ENTITY;

public class DoTBItemsRegistry {

	public static final List<Item> ITEMS = new ArrayList<>();

	//General
	public static final Item SILKMOTH_SPAWN_EGG = reg("silkmoth_spawn_egg", new SpawnEggItem(SILKMOTH_ENTITY, 0xDBD8BD, 0xFEFEFC, (new Item.Properties()).group(ItemGroup.MISC)));
	public static final Item JAPANESE_DRAGON_SPAWN_EGG = reg("japanese_dragon_spawn_egg", new SpawnEggItem(JAPANESE_DRAGON_ENTITY, 0xFFFFFF, 0xFFFFFF, (new Item.Properties()).group(ItemGroup.MISC)));
	public static final Item SILK_WORMS = reg("silk_worms", new ItemDoTB());
	public static final Item SILK_WORMS_HATCHERY = reg("silk_worm_hatchery", new ItemDoTB());
	public static final Item SILK_WORM_EGGS = reg("silk_worm_eggs", new ItemDoTB());
	public static final Item SILK_COCOONS = reg("silk_cocoons", new ItemDoTB());
	public static final Item SILK = reg("silk", new ItemDoTB());
	public static final Item TEA_LEAVES = reg("tea_leaves", new ItemDoTB());
	public static final Item CAMELLIA_LEAVES = reg("camellia_leaves", new ItemDoTB());
	public static final Item WAX = reg("wax", new ItemDoTB());
	public static final Item BAMBOO_HAT = reg("bamboo_hat", new HatItem());
	public static final Item GRAY_TILE = reg("gray_tile", new ItemDoTB());
	public static final Item GRAY_CLAY_TILE = reg("gray_clay_tile", new ItemDoTB());
	public static final Item MULBERRY_LEAVES = reg("mulberry_leaves", new ItemDoTB());
	public static final Item GRAPE = reg("grape", new ItemDoTB(new Item.Properties().food(DoTBFoods.GRAPE)));
	public static final Item GRAPE_SEEDS = reg("grape_seeds", new ItemDoTB());
	//public static final Item CLEMATIS_SEEDS = reg("clematis_seeds", new ItemDoTB());
	public static final Item TACHI_SWORD = reg("tachi_sword", new SwordItem(DIAMOND, DoTBConfig.TACHI_ATT_DMG.get(), DoTBConfig.TACHI_ATT_SPD.get().floatValue(), new Item.Properties().group(DOTB_TAB)));//TODO import the model!!

	//Armors
	public static final Item IRON_PLATE_ARMOR_HEAD = reg(new IronPlateArmorItem(HEAD));
	public static final Item IRON_PLATE_ARMOR_CHEST = reg(new IronPlateArmorItem(CHEST));
	public static final Item IRON_PLATE_ARMOR_LEGS = reg(new IronPlateArmorItem(LEGS));
	public static final Item IRON_PLATE_ARMOR_FEET = reg(new IronPlateArmorItem(FEET));
	public static final Item HOLY_ARMOR_HEAD = reg(new HolyArmorItem(HEAD));
	public static final Item HOLY_ARMOR_CHEST = reg(new HolyArmorItem(CHEST));
	public static final Item HOLY_ARMOR_LEGS = reg(new HolyArmorItem(LEGS));
	public static final Item HOLY_ARMOR_FEET = reg(new HolyArmorItem(FEET));
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

	private static Item reg(CustomArmorItem item){
		return reg(item.getItemPartName(), item);
	}

	public static Item reg(String name, Item item) {
		item = item.setRegistryName(MOD_ID, name);
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
