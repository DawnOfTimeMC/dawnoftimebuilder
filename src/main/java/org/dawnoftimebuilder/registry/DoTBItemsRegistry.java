package org.dawnoftimebuilder.registry;

import static net.minecraft.inventory.EquipmentSlotType.CHEST;
import static net.minecraft.inventory.EquipmentSlotType.FEET;
import static net.minecraft.inventory.EquipmentSlotType.HEAD;
import static net.minecraft.inventory.EquipmentSlotType.LEGS;
import static org.dawnoftimebuilder.util.DoTBMaterials.ArmorMaterial.HOLY;
import static org.dawnoftimebuilder.util.DoTBMaterials.ArmorMaterial.IRON_PLATE;
import static org.dawnoftimebuilder.util.DoTBMaterials.ArmorMaterial.JAPANESE_LIGHT;
import static org.dawnoftimebuilder.util.DoTBMaterials.ArmorMaterial.O_YOROI;
import static org.dawnoftimebuilder.util.DoTBMaterials.ArmorMaterial.PHARAOH;
import static org.dawnoftimebuilder.util.DoTBMaterials.ArmorMaterial.RAIJIN;

import java.util.ArrayList;
import java.util.List;

import org.dawnoftimebuilder.DawnOfTimeBuilder;
import org.dawnoftimebuilder.block.templates.FlowerPotBlockDoTB;
import org.dawnoftimebuilder.client.model.armor.CenturionArmorModel;
import org.dawnoftimebuilder.client.model.armor.HolyArmorModel;
import org.dawnoftimebuilder.client.model.armor.IronPlateArmorModel;
import org.dawnoftimebuilder.client.model.armor.JapaneseLightArmorModel;
import org.dawnoftimebuilder.client.model.armor.OYoroiArmorModel;
import org.dawnoftimebuilder.client.model.armor.PharaohArmorModel;
import org.dawnoftimebuilder.client.model.armor.QuetzalcoatlModel;
import org.dawnoftimebuilder.client.model.armor.RaijinArmorModel;
import org.dawnoftimebuilder.item.IHasFlowerPot;
import org.dawnoftimebuilder.item.IconItem;
import org.dawnoftimebuilder.item.templates.CustomArmorItem;
import org.dawnoftimebuilder.item.templates.HatItem;
import org.dawnoftimebuilder.item.templates.ItemDoTB;
import org.dawnoftimebuilder.item.templates.PotItem;
import org.dawnoftimebuilder.util.DoTBFoods;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DoTBItemsRegistry
{

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DawnOfTimeBuilder.MOD_ID);

	public static final RegistryObject<Item>	GENERAL			= DoTBItemsRegistry.reg("general", new IconItem());
	public static final RegistryObject<Item>	EGYPTIAN		= DoTBItemsRegistry.reg("egyptian", new IconItem());
	public static final RegistryObject<Item>	FRENCH			= DoTBItemsRegistry.reg("french", new IconItem());
	public static final RegistryObject<Item>	GERMAN			= DoTBItemsRegistry.reg("german", new IconItem());
	public static final RegistryObject<Item>	PERSIAN			= DoTBItemsRegistry.reg("persian", new IconItem());
	public static final RegistryObject<Item>	JAPANESE		= DoTBItemsRegistry.reg("japanese", new IconItem());
	public static final RegistryObject<Item>	PRE_COLUMBIAN	= DoTBItemsRegistry.reg("pre_columbian", new IconItem());
	public static final RegistryObject<Item>	ROMAN			= DoTBItemsRegistry.reg("roman", new IconItem());

	// General
	public static final RegistryObject<Item>	SILK_WORMS			= DoTBItemsRegistry.reg("silk_worms", new ItemDoTB(true));
	public static final RegistryObject<Item>	SILK_WORMS_HATCHERY	= DoTBItemsRegistry.reg("silk_worm_hatchery", new ItemDoTB(true));
	public static final RegistryObject<Item>	SILK_WORM_EGGS		= DoTBItemsRegistry.reg("silk_worm_eggs", new ItemDoTB(true));
	public static final RegistryObject<Item>	SILK_COCOONS		= DoTBItemsRegistry.reg("silk_cocoons", new ItemDoTB(true));
	public static final RegistryObject<Item>	SILK				= DoTBItemsRegistry.reg("silk", new ItemDoTB());
	public static final RegistryObject<Item>	TEA_LEAVES			= DoTBItemsRegistry.reg("tea_leaves", new ItemDoTB());
	public static final RegistryObject<Item>	CAMELLIA_LEAVES		= DoTBItemsRegistry.reg("camellia_leaves", new ItemDoTB());
	public static final RegistryObject<Item>	BAMBOO_HAT			= DoTBItemsRegistry.reg("bamboo_hat", new HatItem());
	public static final RegistryObject<Item>	GRAY_TILE			= DoTBItemsRegistry.reg("gray_tile", new ItemDoTB());
	public static final RegistryObject<Item>	GRAY_CLAY_TILE		= DoTBItemsRegistry.reg("gray_clay_tile", new ItemDoTB());
	public static final RegistryObject<Item>	MULBERRY_LEAVES		= DoTBItemsRegistry.reg("mulberry_leaves", new ItemDoTB());
	public static final RegistryObject<Item>	GRAPE				= DoTBItemsRegistry.reg("grape", new ItemDoTB(new Item.Properties().food(DoTBFoods.GRAPE)));
	public static final RegistryObject<Item>	GRAPE_SEEDS			= DoTBItemsRegistry.reg("grape_seeds", new PotItem());
	// public static final RegistryObject<Item> CLEMATIS_SEEDS = reg("clematis_seeds", new ItemDoTB());

	// Armors
	public static final ArmorSetRegistryObject	CENTURION_ARMOR_SET			= new ArmorSetRegistryObject("centurion_armor", IRON_PLATE)
																			{
																				@OnlyIn(Dist.CLIENT)
																				@Override
																				protected CustomArmorItem.ArmorModelFactory getFactory()
																				{
																					return CenturionArmorModel::new;
																				}
																			};
	public static final ArmorSetRegistryObject	IRON_PLATE_ARMOR_SET		= new ArmorSetRegistryObject("iron_plate_armor", IRON_PLATE)
																			{
																				@OnlyIn(Dist.CLIENT)
																				@Override
																				protected CustomArmorItem.ArmorModelFactory getFactory()
																				{
																					return IronPlateArmorModel::new;
																				}
																			};
	public static final ArmorSetRegistryObject	HOLY_ARMOR_SET				= new ArmorSetRegistryObject("holy_armor", HOLY)
																			{
																				@OnlyIn(Dist.CLIENT)
																				@Override
																				protected CustomArmorItem.ArmorModelFactory getFactory()
																				{
																					return HolyArmorModel::new;
																				}
																			};
	public static final ArmorSetRegistryObject	PHARAOH_ARMOR_SET			= new ArmorSetRegistryObject("pharaoh_armor", PHARAOH)
																			{
																				@OnlyIn(Dist.CLIENT)
																				@Override
																				protected CustomArmorItem.ArmorModelFactory getFactory()
																				{
																					return PharaohArmorModel::new;
																				}
																			};
	public static final ArmorSetRegistryObject	JAPANESE_LIGHT_ARMOR_SET	= new ArmorSetRegistryObject("japanese_light_armor", JAPANESE_LIGHT)
																			{
																				@OnlyIn(Dist.CLIENT)
																				@Override
																				protected CustomArmorItem.ArmorModelFactory getFactory()
																				{
																					return JapaneseLightArmorModel::new;
																				}
																			};
	public static final ArmorSetRegistryObject	O_YOROI_ARMOR_SET			= new ArmorSetRegistryObject("o_yoroi_armor", O_YOROI)
																			{
																				@OnlyIn(Dist.CLIENT)
																				@Override
																				protected CustomArmorItem.ArmorModelFactory getFactory()
																				{
																					return OYoroiArmorModel::new;
																				}
																			};
	public static final ArmorSetRegistryObject	QUETZALCOATL_ARMOR_SET		= new ArmorSetRegistryObject("quetzalcoatl_armor", RAIJIN)
																			{
																				@OnlyIn(Dist.CLIENT)
																				@Override
																				protected CustomArmorItem.ArmorModelFactory getFactory()
																				{
																					return QuetzalcoatlModel::new;
																				}
																			};
	public static final ArmorSetRegistryObject	RAIJIN_ARMOR_SET			= new ArmorSetRegistryObject("raijin_armor", RAIJIN)
																			{
																				@OnlyIn(Dist.CLIENT)
																				@Override
																				protected CustomArmorItem.ArmorModelFactory getFactory()
																				{
																					return RaijinArmorModel::new;
																				}
																			};

	private static RegistryObject<Item> reg(final CustomArmorItem item)
	{
		return DoTBItemsRegistry.reg(item.getItemPartName(), item);
	}

	private static RegistryObject<Item> reg(final String name, final Item item)
	{
		if (item instanceof IHasFlowerPot)
		{
			final IHasFlowerPot itemForPot = (IHasFlowerPot) item;
			if (itemForPot.hasFlowerPot())
			{
				final String				potName		= name + "_flower_pot";
				final FlowerPotBlockDoTB	potBlock	= itemForPot.makeFlowerPotInstance(item);
				itemForPot.setPotBlock(potBlock);
				DoTBBlocksRegistry.finalReg(potName, potBlock);
				DoTBItemsRegistry.finalReg(potName, new BlockItem(potBlock, new Item.Properties().tab(DawnOfTimeBuilder.DOTB_TAB)));
				DoTBBlocksRegistry.POT_BLOCKS.put(potName, potBlock);
			}
		}
		return DoTBItemsRegistry.finalReg(name, item);
	}

	public static RegistryObject<Item> finalReg(final String name, final Item item)
	{
		return DoTBItemsRegistry.ITEMS.register(name, () -> item);
	}

	public abstract static class ArmorSetRegistryObject
	{
		private static final EquipmentSlotType[]	SLOT_LIST	= { HEAD, CHEST, LEGS, FEET };
		private final String						set;
		private final IArmorMaterial				material;
		public final RegistryObject<Item>			head;
		public final RegistryObject<Item>			chest;
		public final RegistryObject<Item>			legs;
		public final RegistryObject<Item>			feet;

		protected ArmorSetRegistryObject(final String set, final IArmorMaterial materialIn)
		{
			this.set		= set;
			this.material	= materialIn;

			final List<RegistryObject<Item>> objectList = new ArrayList<>();
			for (final EquipmentSlotType slot : ArmorSetRegistryObject.SLOT_LIST)
			{
				objectList.add(DoTBItemsRegistry.reg(new CustomArmorItem(this.set, this.material, slot)
				{
					@Override
					@OnlyIn(Dist.CLIENT)
					public ArmorModelFactory getModelFactory()
					{
						return ArmorSetRegistryObject.this.getFactory();
					}
				}));
			}

			this.head	= objectList.get(0);
			this.chest	= objectList.get(1);
			this.legs	= objectList.get(2);
			this.feet	= objectList.get(3);
		}

		/**
		 * Returns the armor model's factory. It needs to be associated with @OnlyIn(Dist.CLIENT) since BipedModel is Client Side only !
		 */
		@OnlyIn(Dist.CLIENT)
		protected abstract CustomArmorItem.ArmorModelFactory getFactory();
	}
}
