package org.dawnoftime.dawnoftime.registry;

import net.minecraft.world.item.Item;
import org.dawnoftime.dawnoftime.item.IHasFlowerPot;
import org.dawnoftime.dawnoftime.item.templates.ItemDoTB;
import org.dawnoftime.dawnoftime.item.templates.PotItem;
import org.dawnoftime.dawnoftime.util.Foods;

import java.util.function.Supplier;

@SuppressWarnings({"unused"})
public abstract class DoTBItemsRegistry {
    public static DoTBItemsRegistry INSTANCE;

    // General
    public final Supplier<Item> ANCIENTARCHI = register("ancientarchi", () -> new ItemDoTB(false));
    public final Supplier<Item> SILK_WORMS = register("silk_worms", () -> new ItemDoTB(true));
    public final Supplier<Item> SILK_WORMS_HATCHERY = register("silk_worm_hatchery", () -> new ItemDoTB(true));
    public final Supplier<Item> SILK_WORM_EGGS = register("silk_worm_eggs", () -> new ItemDoTB(true));
    public final Supplier<Item> SILK_COCOONS = register("silk_cocoons", () -> new ItemDoTB(true));
    public final Supplier<Item> SILK = register("silk", ItemDoTB::new);
    public final Supplier<Item> TEA_LEAVES = register("tea_leaves", ItemDoTB::new);
    public final Supplier<Item> CAMELLIA_LEAVES = register("camellia_leaves", ItemDoTB::new);
    public final Supplier<Item> UNFIRED_CLAY_TILE = register("unfired_clay_tile", ItemDoTB::new);
    public final Supplier<Item> CLAY_TILE = register("clay_tile", ItemDoTB::new);
    public final Supplier<Item> CLAY_TILE_WHITE = register("clay_tile_white", ItemDoTB::new);
    public final Supplier<Item> CLAY_TILE_ORANGE = register("clay_tile_orange", ItemDoTB::new);
    public final Supplier<Item> CLAY_TILE_BLACK = register("clay_tile_black", ItemDoTB::new);
    public final Supplier<Item> CLAY_TILE_BLUE = register("clay_tile_blue", ItemDoTB::new);
    public final Supplier<Item> CLAY_TILE_CYAN = register("clay_tile_cyan", ItemDoTB::new);
    public final Supplier<Item> UNFIRED_CLAY_ROOF_TILE = register("unfired_clay_roof_tile", ItemDoTB::new);
    public final Supplier<Item> GRAY_CLAY_ROOF_TILE = register("gray_clay_roof_tile", ItemDoTB::new);
    public final Supplier<Item> MULBERRY_LEAVES = register("mulberry_leaves", ItemDoTB::new);
    public final Supplier<Item> GRAPE = register("grape", () -> new ItemDoTB(new Item.Properties().food(Foods.GRAPE)));
    public Supplier<Item> GRAPE_SEEDS;

    public void postRegister() {
        GRAPE_SEEDS = registerWithFlowerPot("grape_seeds", PotItem::new);
    }

    public abstract <T extends Item> Supplier<Item> register(final String name, final Supplier<T> itemSupplier);
    public abstract <T extends Item & IHasFlowerPot> Supplier<Item> registerWithFlowerPot(final String name, final Supplier<T> itemSupplier);
    public abstract <T extends Item & IHasFlowerPot> Supplier<Item> registerWithFlowerPot(final String plantName, final String seedName, final Supplier<T> itemSupplier);

//    public static <T extends Item & IHasFlowerPot> Supplier<Item> regWithFlowerPot(final String name, final Supplier<T> itemSupplier) {
//        return regWithFlowerPot(name, name, itemSupplier);
//    }
//
//        @SuppressWarnings("unchecked")
//    public static <T extends Item & IHasFlowerPot> Supplier<Item> regWithFlowerPot(final String plantName, final String seedName, final Supplier<T> itemSupplier) {
//        final String potName = plantName + "_flower_pot";
//        Supplier<FlowerPotBlockDoT> potBlockObject = (Supplier<FlowerPotBlockDoT>) (Object) DoTBBlocksRegistry.reg(potName, () -> {
//            final FlowerPotBlockDoT potBlock = new FlowerPotBlockDoT(null);
//            DoTBBlocksRegistry.POT_BLOCKS.put(potName, potBlock);
//            return potBlock;
//        }, BlockTags.MINEABLE_WITH_PICKAXE);
//
//        Supplier<T> toReturn = DoTBItemsRegistry.ITEMS.register(seedName, () -> {
//            T item = itemSupplier.get();
//            FlowerPotBlockDoT potBlock = potBlockObject.get();
//
//            item.setPotBlock(potBlock);
//            potBlock.setItemInPot(item);
//
//            return item;
//        });
//
//        return (Supplier<Item>) (Object) toReturn;
//    }

//    public static void register(IEventBus eventBus) {
//        ITEMS.register(eventBus);
//    }
}
