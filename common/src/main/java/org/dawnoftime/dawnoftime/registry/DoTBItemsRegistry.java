package org.dawnoftime.dawnoftime.registry;

import net.minecraft.world.item.Item;
import org.dawnoftime.dawnoftime.item.templates.ItemDoTB;

import java.util.function.Supplier;

@SuppressWarnings({"unused"})
public abstract class DoTBItemsRegistry {
    public static DoTBItemsRegistry INSTANCE;

    // General
    public final Supplier<Item> DOT_ITEM = register("dawn_of_time", () -> new ItemDoTB(false, "dawn_of_time"));
    public final Supplier<Item> GERMAN_EMBLEM = register("german_emblem", () -> new ItemDoTB(false, "german_emblem"));
    public final Supplier<Item> JAPANESE_EMBLEM = register("japanese_emblem", () -> new ItemDoTB(false, "japanese_emblem"));
    public final Supplier<Item> ROMAN_EMBLEM = register("roman_emblem", () -> new ItemDoTB(false, "roman_emblem"));
    public final Supplier<Item> PERSIAN_EMBLEM = register("persian_emblem", () -> new ItemDoTB(false, "persian_emblem"));
    public final Supplier<Item> FRENCH_EMBLEM = register("french_emblem", () -> new ItemDoTB(false, "french_emblem"));
    public final Supplier<Item> PRECOLUMBIAN_EMBLEM = register("precolumbian_emblem", () -> new ItemDoTB(false, "precolumbian_emblem"));
    public final Supplier<Item> UNFIRED_CLAY_TILE = register("unfired_clay_tile", () -> new ItemDoTB(false, "unfired_clay_tile"));
    public final Supplier<Item> CLAY_TILE = register("clay_tile", () -> new ItemDoTB(false, "clay_tile"));
    public final Supplier<Item> CLAY_TILE_WHITE = register("clay_tile_white", () -> new ItemDoTB(false, "clay_tile_white"));
    public final Supplier<Item> CLAY_TILE_ORANGE = register("clay_tile_orange", () -> new ItemDoTB(false, "clay_tile_orange"));
    public final Supplier<Item> CLAY_TILE_BLACK = register("clay_tile_black", () -> new ItemDoTB(false, "clay_tile_black"));
    public final Supplier<Item> CLAY_TILE_BLUE = register("clay_tile_blue", () -> new ItemDoTB(false, "clay_tile_blue"));
    public final Supplier<Item> CLAY_TILE_CYAN = register("clay_tile_cyan", () -> new ItemDoTB(false, "clay_tile_cyan"));
    public final Supplier<Item> UNFIRED_CLAY_ROOF_TILE = register("unfired_clay_roof_tile", () -> new ItemDoTB(false, "unfired_clay_roof_tile"));
    public final Supplier<Item> GRAY_CLAY_ROOF_TILE = register("gray_clay_roof_tile", () -> new ItemDoTB(false, "gray_clay_roof_tile"));

    public abstract <T extends Item> Supplier<Item> register(final String name, final Supplier<T> itemSupplier);

}
