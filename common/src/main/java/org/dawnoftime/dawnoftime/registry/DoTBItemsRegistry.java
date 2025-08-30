package org.dawnoftime.dawnoftime.registry;

import net.minecraft.world.item.Item;
import org.dawnoftime.dawnoftime.item.templates.ItemDoTB;

import java.util.function.Supplier;

@SuppressWarnings({"unused"})
public abstract class DoTBItemsRegistry {
    public static DoTBItemsRegistry INSTANCE;

    // General
    public final Supplier<Item> DOT_ITEM = register("dawn_of_time", () -> new ItemDoTB(false));
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
