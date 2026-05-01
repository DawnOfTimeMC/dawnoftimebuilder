package org.dawnoftime.dawnoftime.registry;

import net.minecraft.world.item.Item;
import org.dawnoftime.dawnoftime.item.templates.ItemDoTB;

import java.util.function.Supplier;

@SuppressWarnings({"unused"})
public abstract class DoTBItemsRegistry {
    public static DoTBItemsRegistry INSTANCE;

    // General
    public final Supplier<Item> DOT_ITEM = register("dawn_of_time", () -> new ItemDoTB());
    public final Supplier<Item> GERMAN_EMBLEM = register("german_emblem", () -> new ItemDoTB());
    public final Supplier<Item> JAPANESE_EMBLEM = register("japanese_emblem", () -> new ItemDoTB());
    public final Supplier<Item> CHINESE_EMBLEM = register("chinese_emblem", () -> new ItemDoTB());
    public final Supplier<Item> ROMAN_EMBLEM = register("roman_emblem", () -> new ItemDoTB());
    public final Supplier<Item> PERSIAN_EMBLEM = register("persian_emblem", () -> new ItemDoTB());
    public final Supplier<Item> FRENCH_EMBLEM = register("french_emblem", () -> new ItemDoTB());
    public final Supplier<Item> PRECOLUMBIAN_EMBLEM = register("precolumbian_emblem", () -> new ItemDoTB());
    public final Supplier<Item> PATREON_EMBLEM = register("patreon_emblem", () -> new ItemDoTB());


    // Patreon tokens — given by /dotreward, used as crafting ingredients for patron rewards
    public final Supplier<Item> PATREON_TIER_1 = register("patreon_tier_1", ItemDoTB::new);
    public final Supplier<Item> PATREON_TIER_2 = register("patreon_tier_2", ItemDoTB::new);
    public final Supplier<Item> PATREON_TIER_3 = register("patreon_tier_3", ItemDoTB::new);
    public final Supplier<Item> PATREON_TIER_4 = register("patreon_tier_4", ItemDoTB::new);
    public final Supplier<Item> PATREON_TIER_5 = register("patreon_tier_5", ItemDoTB::new);
    public final Supplier<Item> PATREON_TIER_6 = register("patreon_tier_6", ItemDoTB::new);

    public final Supplier<Item> UNFIRED_CLAY_TILE = register("unfired_clay_tile", ItemDoTB::new);
    public final Supplier<Item> CLAY_TILE = register("clay_tile", ItemDoTB::new);
    public final Supplier<Item> CLAY_TILE_WHITE = register("clay_tile_white", ItemDoTB::new);
    public final Supplier<Item> CLAY_TILE_ORANGE = register("clay_tile_orange", ItemDoTB::new);
    public final Supplier<Item> CLAY_TILE_BLACK = register("clay_tile_black", ItemDoTB::new);
    public final Supplier<Item> CLAY_TILE_BLUE = register("clay_tile_blue", ItemDoTB::new);
    public final Supplier<Item> CLAY_TILE_CYAN = register("clay_tile_cyan", ItemDoTB::new);
    public final Supplier<Item> UNFIRED_CLAY_ROOF_TILE = register("unfired_clay_roof_tile", ItemDoTB::new);
    public final Supplier<Item> GRAY_CLAY_ROOF_TILE = register("gray_clay_roof_tile", ItemDoTB::new);

    public abstract <T extends Item> Supplier<Item> register(final String name, final Supplier<T> itemSupplier);

}
