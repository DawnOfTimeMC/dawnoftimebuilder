package org.dawnoftimebuilder.registry;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.dawnoftimebuilder.block.templates.FlowerPotBlockDoTB;
import org.dawnoftimebuilder.client.model.armor.*;
import org.dawnoftimebuilder.item.IHasFlowerPot;
import org.dawnoftimebuilder.item.IconItem;
import org.dawnoftimebuilder.item.templates.CustomArmorItem;
import org.dawnoftimebuilder.item.templates.HatItem;
import org.dawnoftimebuilder.item.templates.ItemDoTB;
import org.dawnoftimebuilder.item.templates.PotItem;
import org.dawnoftimebuilder.util.DoTBFoods;

import static net.minecraft.inventory.EquipmentSlotType.*;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;
import static org.dawnoftimebuilder.registry.DoTBBlocksRegistry.POT_BLOCKS;
import static org.dawnoftimebuilder.util.DoTBMaterials.ArmorMaterial.*;

public class DoTBItemsRegistry {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

	public static final RegistryObject<Item> GENERAL= reg("general", new IconItem());
	public static final RegistryObject<Item> EGYPTIAN= reg("egyptian", new IconItem());
	public static final RegistryObject<Item> FRENCH= reg("french", new IconItem());
	public static final RegistryObject<Item> GERMAN= reg("german", new IconItem());
	public static final RegistryObject<Item> PERSIAN= reg("persian", new IconItem());
	public static final RegistryObject<Item> JAPANESE= reg("japanese", new IconItem());
	public static final RegistryObject<Item> PRE_COLUMBIAN= reg("pre_columbian", new IconItem());
	public static final RegistryObject<Item> ROMAN= reg("roman", new IconItem());

	//General
	public static final RegistryObject<Item> SILK_WORMS = reg("silk_worms", new ItemDoTB(true));
	public static final RegistryObject<Item> SILK_WORMS_HATCHERY = reg("silk_worm_hatchery", new ItemDoTB(true));
	public static final RegistryObject<Item> SILK_WORM_EGGS = reg("silk_worm_eggs", new ItemDoTB(true));
	public static final RegistryObject<Item> SILK_COCOONS = reg("silk_cocoons", new ItemDoTB(true));
	public static final RegistryObject<Item> SILK = reg("silk", new ItemDoTB());
	public static final RegistryObject<Item> TEA_LEAVES = reg("tea_leaves", new ItemDoTB());
	public static final RegistryObject<Item> CAMELLIA_LEAVES = reg("camellia_leaves", new ItemDoTB());
	public static final RegistryObject<Item> BAMBOO_HAT = reg("bamboo_hat", new HatItem());
	public static final RegistryObject<Item> GRAY_TILE = reg("gray_tile", new ItemDoTB());
	public static final RegistryObject<Item> GRAY_CLAY_TILE = reg("gray_clay_tile", new ItemDoTB());
	public static final RegistryObject<Item> MULBERRY_LEAVES = reg("mulberry_leaves", new ItemDoTB());
	public static final RegistryObject<Item> GRAPE = reg("grape", new ItemDoTB(new Item.Properties().food(DoTBFoods.GRAPE)));
	public static final RegistryObject<Item> GRAPE_SEEDS = reg("grape_seeds", new PotItem());
	//public static final RegistryObject<Item> CLEMATIS_SEEDS = reg("clematis_seeds", new ItemDoTB());

	//Armors
	public static final RegistryObject<Item> CENTURION_ARMOR_HEAD = reg(new CustomArmorItem("centurion_armor", IRON_PLATE, HEAD, CenturionArmorModel::new));
	public static final RegistryObject<Item> CENTURION_ARMOR_CHEST = reg(new CustomArmorItem("centurion_armor", IRON_PLATE, CHEST, CenturionArmorModel::new));
	public static final RegistryObject<Item> CENTURION_ARMOR_LEGS = reg(new CustomArmorItem("centurion_armor", IRON_PLATE, LEGS, CenturionArmorModel::new));
	public static final RegistryObject<Item> CENTURION_ARMOR_FEET = reg(new CustomArmorItem("centurion_armor", IRON_PLATE, FEET, CenturionArmorModel::new));
	public static final RegistryObject<Item> IRON_PLATE_ARMOR_HEAD = reg(new CustomArmorItem("iron_plate_armor", IRON_PLATE, HEAD, IronPlateArmorModel::new));
	public static final RegistryObject<Item> IRON_PLATE_ARMOR_CHEST = reg(new CustomArmorItem("iron_plate_armor", IRON_PLATE, CHEST, IronPlateArmorModel::new));
	public static final RegistryObject<Item> IRON_PLATE_ARMOR_LEGS = reg(new CustomArmorItem("iron_plate_armor", IRON_PLATE, LEGS, IronPlateArmorModel::new));
	public static final RegistryObject<Item> IRON_PLATE_ARMOR_FEET = reg(new CustomArmorItem("iron_plate_armor", IRON_PLATE, FEET, IronPlateArmorModel::new));
	public static final RegistryObject<Item> HOLY_ARMOR_HEAD = reg(new CustomArmorItem("holy_armor", HOLY, HEAD, HolyArmorModel::new));
	public static final RegistryObject<Item> HOLY_ARMOR_CHEST = reg(new CustomArmorItem("holy_armor", HOLY, CHEST, HolyArmorModel::new));
	public static final RegistryObject<Item> HOLY_ARMOR_LEGS = reg(new CustomArmorItem("holy_armor", HOLY, LEGS, HolyArmorModel::new));
	public static final RegistryObject<Item> HOLY_ARMOR_FEET = reg(new CustomArmorItem("holy_armor", HOLY, FEET, HolyArmorModel::new));
	public static final RegistryObject<Item> PHARAOH_ARMOR_HEAD = reg(new CustomArmorItem("pharaoh_armor", PHARAOH, HEAD, PharaohArmorModel::new));
	public static final RegistryObject<Item> PHARAOH_ARMOR_CHEST = reg(new CustomArmorItem("pharaoh_armor", PHARAOH, CHEST, PharaohArmorModel::new));
	public static final RegistryObject<Item> PHARAOH_ARMOR_LEGS = reg(new CustomArmorItem("pharaoh_armor", PHARAOH, LEGS, PharaohArmorModel::new));
	public static final RegistryObject<Item> PHARAOH_ARMOR_FEET = reg(new CustomArmorItem("pharaoh_armor", PHARAOH, FEET, PharaohArmorModel::new));
	public static final RegistryObject<Item> JAPANESE_LIGHT_ARMOR_HEAD = reg(new CustomArmorItem("japanese_light_armor", JAPANESE_LIGHT, HEAD, JapaneseLightArmorModel::new));
	public static final RegistryObject<Item> JAPANESE_LIGHT_ARMOR_CHEST = reg(new CustomArmorItem("japanese_light_armor", JAPANESE_LIGHT, CHEST, JapaneseLightArmorModel::new));
	public static final RegistryObject<Item> JAPANESE_LIGHT_ARMOR_LEGS = reg(new CustomArmorItem("japanese_light_armor", JAPANESE_LIGHT, LEGS, JapaneseLightArmorModel::new));
	public static final RegistryObject<Item> JAPANESE_LIGHT_ARMOR_FEET = reg(new CustomArmorItem("japanese_light_armor", JAPANESE_LIGHT, FEET, JapaneseLightArmorModel::new));
	public static final RegistryObject<Item> O_YOROI_ARMOR_HEAD = reg(new CustomArmorItem("o_yoroi_armor", O_YOROI, HEAD, OYoroiArmorModel::new));
	public static final RegistryObject<Item> O_YOROI_ARMOR_CHEST = reg(new CustomArmorItem("o_yoroi_armor", O_YOROI, CHEST, OYoroiArmorModel::new));
	public static final RegistryObject<Item> O_YOROI_ARMOR_LEGS = reg(new CustomArmorItem("o_yoroi_armor", O_YOROI, LEGS, OYoroiArmorModel::new));
	public static final RegistryObject<Item> O_YOROI_ARMOR_FEET = reg(new CustomArmorItem("o_yoroi_armor", O_YOROI, FEET, OYoroiArmorModel::new));
	public static final RegistryObject<Item> QUETZALCOATL_ARMOR_HEAD = reg(new CustomArmorItem("quetzalcoatl_armor", RAIJIN, HEAD, QuetzalcoatlModel::new));
	public static final RegistryObject<Item> QUETZALCOATL_ARMOR_CHEST = reg(new CustomArmorItem("quetzalcoatl_armor", RAIJIN, CHEST, QuetzalcoatlModel::new));
	public static final RegistryObject<Item> QUETZALCOATL_ARMOR_LEGS = reg(new CustomArmorItem("quetzalcoatl_armor", RAIJIN, LEGS, QuetzalcoatlModel::new));
	public static final RegistryObject<Item> QUETZALCOATL_ARMOR_FEET = reg(new CustomArmorItem("quetzalcoatl_armor", RAIJIN, FEET, QuetzalcoatlModel::new));
	public static final RegistryObject<Item> RAIJIN_ARMOR_HEAD = reg(new CustomArmorItem("raijin_armor", RAIJIN, HEAD, RaijinArmorModel::new));
	public static final RegistryObject<Item> RAIJIN_ARMOR_CHEST = reg(new CustomArmorItem("raijin_armor", RAIJIN, CHEST, RaijinArmorModel::new));
	public static final RegistryObject<Item> RAIJIN_ARMOR_LEGS = reg(new CustomArmorItem("raijin_armor", RAIJIN, LEGS, RaijinArmorModel::new));
	public static final RegistryObject<Item> RAIJIN_ARMOR_FEET = reg(new CustomArmorItem("raijin_armor", RAIJIN, FEET, RaijinArmorModel::new));

	private static RegistryObject<Item> reg(CustomArmorItem item){
		return reg(item.getItemPartName(), item);
	}

	private static RegistryObject<Item> reg(String name, Item item) {
		if(item instanceof IHasFlowerPot){
			IHasFlowerPot itemForPot = (IHasFlowerPot) item;
			if(itemForPot.hasFlowerPot()){
				String potName = name + "_flower_pot";
				FlowerPotBlockDoTB potBlock = itemForPot.makeFlowerPotInstance(item);
				itemForPot.setPotBlock(potBlock);
				DoTBBlocksRegistry.finalReg(potName, potBlock);
				finalReg(potName, new BlockItem(potBlock, new Item.Properties().tab(DOTB_TAB)));
				POT_BLOCKS.put(potName, potBlock);
			}
		}
		return finalReg(name, item);
	}

	public static RegistryObject<Item> finalReg(String name, Item item){
		return ITEMS.register(name, () -> item);
	}
}
