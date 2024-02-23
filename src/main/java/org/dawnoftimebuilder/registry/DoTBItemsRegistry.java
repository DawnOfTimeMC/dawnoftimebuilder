package org.dawnoftimebuilder.registry;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.dawnoftimebuilder.DawnOfTimeBuilder;
import org.dawnoftimebuilder.item.templates.ItemDoTB;
import org.dawnoftimebuilder.item.templates.PotItem;

import java.util.function.Supplier;

public class DoTBItemsRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DawnOfTimeBuilder.MOD_ID);
    public static final RegistryObject<Item> GENERAL = DoTBItemsRegistry.reg("general", () -> new ItemDoTB(true));
    public static final RegistryObject<Item> EGYPTIAN = DoTBItemsRegistry.reg("egyptian", () -> new ItemDoTB(true));
    public static final RegistryObject<Item> FRENCH = DoTBItemsRegistry.reg("french", () -> new ItemDoTB(true));
    public static final RegistryObject<Item> GERMAN = DoTBItemsRegistry.reg("german", () -> new ItemDoTB(true));
    public static final RegistryObject<Item> PERSIAN = DoTBItemsRegistry.reg("persian", () -> new ItemDoTB(true));
    public static final RegistryObject<Item> JAPANESE = DoTBItemsRegistry.reg("japanese", () -> new ItemDoTB(true));
    public static final RegistryObject<Item> PRE_COLUMBIAN = DoTBItemsRegistry.reg("pre_columbian", () -> new ItemDoTB(true));
    public static final RegistryObject<Item> ROMAN = DoTBItemsRegistry.reg("roman", () -> new ItemDoTB(true));
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
    //    public static final RegistryObject<Item> GRAPE = DoTBItemsRegistry.reg("grape", () -> new ItemDoTB(new Item.Properties().food(DoTBFoods.GRAPE)));
    public static final RegistryObject<Item> GRAPE_SEEDS = DoTBItemsRegistry.reg("grape_seeds", PotItem::new);

    private static <T extends Item> RegistryObject<T> reg(final String name, final Supplier<T> itemSupplier) {
        return DoTBItemsRegistry.ITEMS.register(name, itemSupplier);
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
