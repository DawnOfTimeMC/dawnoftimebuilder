package org.dawnoftimebuilder.registry;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.dawnoftimebuilder.DawnOfTimeBuilder;
import org.dawnoftimebuilder.block.templates.FlowerPotBlockDoTB;
import org.dawnoftimebuilder.item.IHasFlowerPot;
import org.dawnoftimebuilder.item.IconItem;
import org.dawnoftimebuilder.item.templates.ItemDoTB;
import org.dawnoftimebuilder.item.templates.PotItem;
import org.dawnoftimebuilder.util.DoTBFoods;

import java.util.function.Supplier;

public class DoTBItemsRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DawnOfTimeBuilder.MOD_ID);

    public static final RegistryObject<Item> GENERAL = DoTBItemsRegistry.reg("general", IconItem::new);
    public static final RegistryObject<Item> EGYPTIAN = DoTBItemsRegistry.reg("egyptian", IconItem::new);
    public static final RegistryObject<Item> FRENCH = DoTBItemsRegistry.reg("french", IconItem::new);
    public static final RegistryObject<Item> GERMAN = DoTBItemsRegistry.reg("german", IconItem::new);
    public static final RegistryObject<Item> PERSIAN = DoTBItemsRegistry.reg("persian", IconItem::new);
    public static final RegistryObject<Item> JAPANESE = DoTBItemsRegistry.reg("japanese", IconItem::new);
    public static final RegistryObject<Item> PRE_COLUMBIAN = DoTBItemsRegistry.reg("pre_columbian", IconItem::new);
    public static final RegistryObject<Item> ROMAN = DoTBItemsRegistry.reg("roman", IconItem::new);

    // General
    public static final RegistryObject<Item> SILK_WORMS = DoTBItemsRegistry.reg("silk_worms", () -> new ItemDoTB(true));
    public static final RegistryObject<Item> SILK_WORMS_HATCHERY = DoTBItemsRegistry.reg("silk_worm_hatchery", () -> new ItemDoTB(true));
    public static final RegistryObject<Item> SILK_WORM_EGGS = DoTBItemsRegistry.reg("silk_worm_eggs", () -> new ItemDoTB(true));
    public static final RegistryObject<Item> SILK_COCOONS = DoTBItemsRegistry.reg("silk_cocoons", () -> new ItemDoTB(true));
    public static final RegistryObject<Item> SILK = DoTBItemsRegistry.reg("silk", ItemDoTB::new);
    public static final RegistryObject<Item> TEA_LEAVES = DoTBItemsRegistry.reg("tea_leaves", ItemDoTB::new);
    public static final RegistryObject<Item> CAMELLIA_LEAVES = DoTBItemsRegistry.reg("camellia_leaves", ItemDoTB::new);
    public static final RegistryObject<Item> GRAY_TILE = DoTBItemsRegistry.reg("gray_tile", ItemDoTB::new);
    public static final RegistryObject<Item> GRAY_CLAY_TILE = DoTBItemsRegistry.reg("gray_clay_tile", ItemDoTB::new);
    public static final RegistryObject<Item> MULBERRY_LEAVES = DoTBItemsRegistry.reg("mulberry_leaves", ItemDoTB::new);
    public static final RegistryObject<Item> GRAPE = DoTBItemsRegistry.reg("grape", () -> new ItemDoTB(new Item.Properties().food(DoTBFoods.GRAPE)));
    public static final RegistryObject<Item> GRAPE_SEEDS = DoTBItemsRegistry.regWithFlowerPot("grape_seeds", PotItem::new);

    public static <T extends Item> RegistryObject<Item> reg(final String name, final Supplier<T> itemSupplier) {
        return DoTBItemsRegistry.ITEMS.register(name, itemSupplier);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Item & IHasFlowerPot> RegistryObject<Item> regWithFlowerPot(final String name, final Supplier<T> itemSupplier) {
        final String potName = name + "_flower_pot";
        RegistryObject<FlowerPotBlockDoTB> potBlockObject = (RegistryObject<FlowerPotBlockDoTB>) (Object) DoTBBlocksRegistry.reg(potName, () -> {
            final FlowerPotBlockDoTB potBlock = new FlowerPotBlockDoTB(null);
            DoTBBlocksRegistry.POT_BLOCKS.put(potName, potBlock);
            return potBlock;
        });

        RegistryObject<T> toReturn = DoTBItemsRegistry.ITEMS.register(name, () -> {
            T item = itemSupplier.get();
            FlowerPotBlockDoTB potBlock = potBlockObject.get();

            item.setPotBlock(potBlock);
            potBlock.setItemInPot(item);

            return item;
        });

        return (RegistryObject<Item>) (Object) toReturn;
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
