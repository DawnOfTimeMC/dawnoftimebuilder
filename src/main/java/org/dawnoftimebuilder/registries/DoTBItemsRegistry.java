package org.dawnoftimebuilder.registries;

import net.minecraft.item.Item;
import org.dawnoftimebuilder.items.templates.ClimbingPlantSeeds;
import org.dawnoftimebuilder.items.templates.DoTBItem;
import org.dawnoftimebuilder.items.japanese.TachiSwordItem;
import org.dawnoftimebuilder.utils.DoTBBlockStateProperties;

import java.util.*;

public class DoTBItemsRegistry {
	/*
		private static final HashMap<String, IBakedModel> CUSTOM_MODELS = new HashMap<>();
		public static IBakedModel getModel(String name){
			return CUSTOM_MODELS.get(name);
		}
	*/
	public static final List<Item> ITEMS = new ArrayList<>();

	//General
	public static final Item SILK_WORMS = reg(new DoTBItem("silk_worms"));
	//public static final Item SILK_WORMS_HATCHERY = reg(new DoTBItemCanBeDried("silk_worm_hatchery", 1,60, SILK_WORMS, 1));
	public static final Item SILK_WORM_EGGS = reg(new DoTBItem("silk_worm_eggs"));
	public static final Item SILK_COCOONS = reg(new DoTBItem("silk_cocoons"));
	public static final Item SILK = reg(new DoTBItem("silk"));
	public static final Item TEA_LEAVES = reg(new DoTBItem("tea_leaves"));
	//public static final Item CAMELLIA_LEAVES = reg(new DoTBItemCanBeDried("camellia_leaves", 1,40, TEA_LEAVES, 1));
	public static final Item WAX = reg(new DoTBItem("wax"));
	public static final Item BAMBOO_HAT = reg(new DoTBItem("bamboo_hat"));
	public static final Item GREY_TILE = reg(new DoTBItem("grey_tile"));
	public static final Item GREY_CLAY_TILE = reg(new DoTBItem("grey_clay_tile"));
	public static final Item MULBERRY_LEAVES = reg(new DoTBItem("mulberry_leaves"));
	public static final Item GRAPE = reg(new DoTBItem("grape"));
	public static final Item GRAPE_SEEDS = reg(new ClimbingPlantSeeds("grape_seeds", DoTBBlockStateProperties.ClimbingPlant.GRAPE));
	public static final Item TACHI_SWORD = reg(new TachiSwordItem());
	/*
	public static final Item IRON_PLATE_ARMOR_HEAD = reg(new ItemIronPlateArmor(EntityEquipmentSlot.HEAD));
	public static final Item IRON_PLATE_ARMOR_CHEST = reg(new ItemIronPlateArmor(EntityEquipmentSlot.CHEST));
	public static final Item IRON_PLATE_ARMOR_LEGS = reg(new ItemIronPlateArmor(EntityEquipmentSlot.HEAD));
	public static final Item IRON_PLATE_ARMOR_FEET = reg(new ItemIronPlateArmor(EntityEquipmentSlot.LEGS));
	public static final Item PHARAOH_ARMOR_HEAD = reg(new ItemPharaohArmor(EntityEquipmentSlot.HEAD));
	public static final Item PHARAOH_ARMOR_CHEST = reg(new ItemPharaohArmor(EntityEquipmentSlot.CHEST));
	public static final Item PHARAOH_ARMOR_LEGS = reg(new ItemPharaohArmor(EntityEquipmentSlot.LEGS));
	public static final Item PHARAOH_ARMOR_FEET = reg(new ItemPharaohArmor(EntityEquipmentSlot.FEET));
	public static final Item JAPANESE_LIGHT_ARMOR_HEAD = reg(new ItemJapaneseLightArmor(EntityEquipmentSlot.HEAD));
	public static final Item JAPANESE_LIGHT_ARMOR_CHEST = reg(new ItemJapaneseLightArmor(EntityEquipmentSlot.CHEST));
	public static final Item JAPANESE_LIGHT_ARMOR_LEGS = reg(new ItemJapaneseLightArmor(EntityEquipmentSlot.LEGS));
	public static final Item JAPANESE_LIGHT_ARMOR_FEET = reg(new ItemJapaneseLightArmor(EntityEquipmentSlot.FEET));
	public static final Item O_YOROI_ARMOR_HEAD = reg(new ItemOYoroiArmor(EntityEquipmentSlot.HEAD));
	public static final Item O_YOROI_ARMOR_CHEST = reg(new ItemOYoroiArmor(EntityEquipmentSlot.CHEST));
	public static final Item O_YOROI_ARMOR_LEGS = reg(new ItemOYoroiArmor(EntityEquipmentSlot.LEGS));
	public static final Item O_YOROI_ARMOR_FEET = reg(new ItemOYoroiArmor(EntityEquipmentSlot.FEET));
	public static final Item RAIJIN_ARMOR_HEAD = reg(new ItemRaijinArmor(EntityEquipmentSlot.HEAD));
	public static final Item RAIJIN_ARMOR_CHEST = reg(new ItemRaijinArmor(EntityEquipmentSlot.CHEST));
	public static final Item RAIJIN_ARMOR_LEGS = reg(new ItemRaijinArmor(EntityEquipmentSlot.LEGS));
	public static final Item RAIJIN_ARMOR_FEET = reg(new ItemRaijinArmor(EntityEquipmentSlot.FEET));
	*/
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
