package org.dawnoftimebuilder.registry;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.dawnoftimebuilder.block.templates.FlowerPotBlockDoTB;
import org.dawnoftimebuilder.item.IHasFlowerPot;
import org.dawnoftimebuilder.item.IconItem;
import org.dawnoftimebuilder.item.egyptian.PharaohArmorItem;
import org.dawnoftimebuilder.item.german.HolyArmorItem;
import org.dawnoftimebuilder.item.german.IronPlateArmorItem;
import org.dawnoftimebuilder.item.japanese.JapaneseLightArmorItem;
import org.dawnoftimebuilder.item.japanese.OYoroiArmorItem;
import org.dawnoftimebuilder.item.japanese.RaijinArmorItem;
import org.dawnoftimebuilder.item.templates.CustomArmorItem;
import org.dawnoftimebuilder.item.templates.HatItem;
import org.dawnoftimebuilder.item.templates.ItemDoTB;
import org.dawnoftimebuilder.item.templates.PotItem;
import org.dawnoftimebuilder.util.DoTBFoods;

import static net.minecraft.inventory.EquipmentSlotType.*;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;
import static org.dawnoftimebuilder.registry.DoTBBlocksRegistry.POT_BLOCKS;

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
	public static final RegistryObject<Item> IRON_PLATE_ARMOR_HEAD = reg(new IronPlateArmorItem(HEAD));
	public static final RegistryObject<Item> IRON_PLATE_ARMOR_CHEST = reg(new IronPlateArmorItem(CHEST));
	public static final RegistryObject<Item> IRON_PLATE_ARMOR_LEGS = reg(new IronPlateArmorItem(LEGS));
	public static final RegistryObject<Item> IRON_PLATE_ARMOR_FEET = reg(new IronPlateArmorItem(FEET));
	public static final RegistryObject<Item> HOLY_ARMOR_HEAD = reg(new HolyArmorItem(HEAD));
	public static final RegistryObject<Item> HOLY_ARMOR_CHEST = reg(new HolyArmorItem(CHEST));
	public static final RegistryObject<Item> HOLY_ARMOR_LEGS = reg(new HolyArmorItem(LEGS));
	public static final RegistryObject<Item> HOLY_ARMOR_FEET = reg(new HolyArmorItem(FEET));
	public static final RegistryObject<Item> PHARAOH_ARMOR_HEAD = reg(new PharaohArmorItem(HEAD));
	public static final RegistryObject<Item> PHARAOH_ARMOR_CHEST = reg(new PharaohArmorItem(CHEST));
	public static final RegistryObject<Item> PHARAOH_ARMOR_LEGS = reg(new PharaohArmorItem(LEGS));
	public static final RegistryObject<Item> PHARAOH_ARMOR_FEET = reg(new PharaohArmorItem(FEET));
	public static final RegistryObject<Item> JAPANESE_LIGHT_ARMOR_HEAD = reg(new JapaneseLightArmorItem(HEAD));
	public static final RegistryObject<Item> JAPANESE_LIGHT_ARMOR_CHEST = reg(new JapaneseLightArmorItem(CHEST));
	public static final RegistryObject<Item> JAPANESE_LIGHT_ARMOR_LEGS = reg(new JapaneseLightArmorItem(LEGS));
	public static final RegistryObject<Item> JAPANESE_LIGHT_ARMOR_FEET = reg(new JapaneseLightArmorItem(FEET));
	public static final RegistryObject<Item> O_YOROI_ARMOR_HEAD = reg(new OYoroiArmorItem(HEAD));
	public static final RegistryObject<Item> O_YOROI_ARMOR_CHEST = reg(new OYoroiArmorItem(CHEST));
	public static final RegistryObject<Item> O_YOROI_ARMOR_LEGS = reg(new OYoroiArmorItem(LEGS));
	public static final RegistryObject<Item> O_YOROI_ARMOR_FEET = reg(new OYoroiArmorItem(FEET));
	public static final RegistryObject<Item> RAIJIN_ARMOR_HEAD = reg(new RaijinArmorItem(HEAD));
	public static final RegistryObject<Item> RAIJIN_ARMOR_CHEST = reg(new RaijinArmorItem(CHEST));
	public static final RegistryObject<Item> RAIJIN_ARMOR_LEGS = reg(new RaijinArmorItem(LEGS));
	public static final RegistryObject<Item> RAIJIN_ARMOR_FEET = reg(new RaijinArmorItem(FEET));

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
