package org.dawnoftimebuilder.registry;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.dawnoftimebuilder.DawnOfTimeBuilder;
import org.dawnoftimebuilder.block.templates.FlowerPotBlockDoTB;
import org.dawnoftimebuilder.client.model.armor.*;
import org.dawnoftimebuilder.item.IHasFlowerPot;
import org.dawnoftimebuilder.item.IconItem;
import org.dawnoftimebuilder.item.templates.CustomArmorItem;
import org.dawnoftimebuilder.item.templates.HatItem;
import org.dawnoftimebuilder.item.templates.ItemDoTB;
import org.dawnoftimebuilder.item.templates.PotItem;
import org.dawnoftimebuilder.util.DoTBFoods;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.inventory.EquipmentSlotType.*;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;
import static org.dawnoftimebuilder.registry.DoTBBlocksRegistry.POT_BLOCKS;
import static org.dawnoftimebuilder.util.DoTBMaterials.ArmorMaterial.*;

public class DoTBItemsRegistry {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

	public static final RegistryObject<Item> GENERAL = reg("general", new IconItem());
	public static final RegistryObject<Item> EGYPTIAN = reg("egyptian", new IconItem());
	public static final RegistryObject<Item> FRENCH = reg("french", new IconItem());
	public static final RegistryObject<Item> GERMAN = reg("german", new IconItem());
	public static final RegistryObject<Item> PERSIAN = reg("persian", new IconItem());
	public static final RegistryObject<Item> JAPANESE = reg("japanese", new IconItem());
	public static final RegistryObject<Item> PRE_COLUMBIAN = reg("pre_columbian", new IconItem());
	public static final RegistryObject<Item> ROMAN = reg("roman", new IconItem());

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
	public static final ArmorSetRegistryObject CENTURION_ARMOR_SET = 		new ArmorSetRegistryObject("centurion_armor", IRON_PLATE) 			{@OnlyIn(Dist.CLIENT) @Override protected CustomArmorItem.ArmorModelFactory getFactory() { return CenturionArmorModel::new;}};
	public static final ArmorSetRegistryObject IRON_PLATE_ARMOR_SET = 		new ArmorSetRegistryObject("iron_plate_armor", IRON_PLATE) 			{@OnlyIn(Dist.CLIENT) @Override protected CustomArmorItem.ArmorModelFactory getFactory() { return IronPlateArmorModel::new;}};
	public static final ArmorSetRegistryObject HOLY_ARMOR_SET = 			new ArmorSetRegistryObject("holy_armor", HOLY) 						{@OnlyIn(Dist.CLIENT) @Override protected CustomArmorItem.ArmorModelFactory getFactory() { return HolyArmorModel::new;}};
	public static final ArmorSetRegistryObject PHARAOH_ARMOR_SET = 			new ArmorSetRegistryObject("pharaoh_armor", PHARAOH) 				{@OnlyIn(Dist.CLIENT) @Override protected CustomArmorItem.ArmorModelFactory getFactory() { return PharaohArmorModel::new;}};
	public static final ArmorSetRegistryObject JAPANESE_LIGHT_ARMOR_SET = 	new ArmorSetRegistryObject("japanese_light_armor", JAPANESE_LIGHT) 	{@OnlyIn(Dist.CLIENT) @Override protected CustomArmorItem.ArmorModelFactory getFactory() { return JapaneseLightArmorModel::new;}};
	public static final ArmorSetRegistryObject O_YOROI_ARMOR_SET = 			new ArmorSetRegistryObject("o_yoroi_armor", O_YOROI) 				{@OnlyIn(Dist.CLIENT) @Override protected CustomArmorItem.ArmorModelFactory getFactory() { return OYoroiArmorModel::new;}};
	public static final ArmorSetRegistryObject QUETZALCOATL_ARMOR_SET = 	new ArmorSetRegistryObject("quetzalcoatl_armor", RAIJIN) 			{@OnlyIn(Dist.CLIENT) @Override protected CustomArmorItem.ArmorModelFactory getFactory() { return QuetzalcoatlModel::new;}};
	public static final ArmorSetRegistryObject RAIJIN_ARMOR_SET = 			new ArmorSetRegistryObject("raijin_armor", RAIJIN) 					{@OnlyIn(Dist.CLIENT) @Override protected CustomArmorItem.ArmorModelFactory getFactory() { return RaijinArmorModel::new;}};

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

	public abstract static class ArmorSetRegistryObject{
		private static final EquipmentSlotType[] SLOT_LIST = new EquipmentSlotType[]{HEAD, CHEST, LEGS, FEET};
		private final String set;
		private final IArmorMaterial material;
		public final RegistryObject<Item> head;
		public final RegistryObject<Item> chest;
		public final RegistryObject<Item> legs;
		public final RegistryObject<Item> feet;

		protected ArmorSetRegistryObject(String set, IArmorMaterial materialIn){
			this.set = set;
			this.material = materialIn;

			List<RegistryObject<Item>> objectList = new ArrayList<>();
			for(EquipmentSlotType slot : SLOT_LIST){
				objectList.add(
						reg(new CustomArmorItem(this.set, this.material, slot) {
							@Override
							@OnlyIn(Dist.CLIENT)
							public ArmorModelFactory getModelFactory() {
								return getFactory();
							}
						})
				);
			}

			this.head = objectList.get(0);
			this.chest = objectList.get(1);
			this.legs = objectList.get(2);
			this.feet = objectList.get(3);
		}

		/**
		 * Returns the armor model's factory. It needs to be associated with @OnlyIn(Dist.CLIENT) since BipedModel is Client Side only !
		 */
		@OnlyIn(Dist.CLIENT)
		protected abstract CustomArmorItem.ArmorModelFactory getFactory();
	}
}
