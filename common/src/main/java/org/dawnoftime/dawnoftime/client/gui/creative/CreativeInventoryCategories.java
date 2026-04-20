package org.dawnoftime.dawnoftime.client.gui.creative;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.dawnoftime.dawnoftime.registry.DoTBBlocksRegistry;
import org.dawnoftime.dawnoftime.registry.DoTBItemsRegistry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.dawnoftime.dawnoftime.DoTBCommon.MOD_ID;

public enum CreativeInventoryCategories {
    JAPANESE("japanese", "https://www.youtube.com/watch?v=AxJGk-deTmo&list=PLRp3sDcdVhnSw-C9jHe_ykJZc5AurcvyG", List.of(
            new SubTab("building",
                    DoTBBlocksRegistry.INSTANCE.STRAIGHT_RAKED_GRAVEL.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CURVED_RAKED_GRAVEL.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.STEPPING_STONES.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.STEPPING_STONES_SLAB.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.THATCH_BAMBOO.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.THATCH_BAMBOO_STAIRS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.THATCH_BAMBOO_PLATE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.THATCH_BAMBOO_SLAB.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.THATCH_BAMBOO_EDGE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.SPRUCE_FOUNDATION.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.SPRUCE_BOARDS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.SPRUCE_BOARDS_STAIRS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.SPRUCE_BOARDS_PLATE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.SPRUCE_BOARDS_SLAB.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.SPRUCE_BOARDS_EDGE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.SPRUCE_ROOF_SUPPORT.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.SPRUCE_PAPER_DOOR.get().asItem(),

                    DoTBBlocksRegistry.INSTANCE.CHARRED_SPRUCE_PLANKS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CHARRED_SPRUCE_PLANKS_STAIRS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CHARRED_SPRUCE_PLANKS_PLATE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CHARRED_SPRUCE_PLANKS_SLAB.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CHARRED_SPRUCE_PLANKS_EDGE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CHARRED_SPRUCE_SUPPORT_SLAB.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CHARRED_SPRUCE_SUPPORT_BEAM.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CHARRED_SPRUCE_BOARDS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CHARRED_SPRUCE_FOUNDATION.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CHARRED_SPRUCE_FOUNDATION_SLAB.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CHARRED_SPRUCE_LOG_STRIPPED.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CHARRED_SPRUCE_BEAM.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CHARRED_SPRUCE_WALL.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CHARRED_SPRUCE_PERGOLA.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CHARRED_SPRUCE_FENCE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CHARRED_SPRUCE_FENCE_GATE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CHARRED_SPRUCE_RAILING.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CHARRED_SPRUCE_FANCY_RAILING.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CHARRED_SPRUCE_LATTICE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CHARRED_SPRUCE_DOOR.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CHARRED_SPRUCE_TRAPDOOR.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CHARRED_SPRUCE_SHUTTERS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CHARRED_SPRUCE_TALL_SHUTTERS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CHARRED_SPRUCE_ROOF_SUPPORT.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CHARRED_SPRUCE_GLASS_PANE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CHARRED_SPRUCE_WINDOW.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WHITE_WATTLE_AND_DAUB.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WHITE_WATTLE_AND_DAUB_STAIRS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WHITE_WATTLE_AND_DAUB_PLATE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WHITE_WATTLE_AND_DAUB_SLAB.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WHITE_WATTLE_AND_DAUB_EDGE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CHARRED_SPRUCE_TIMBER_FRAME.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CHARRED_SPRUCE_TIMBER_FRAME_PILLAR.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PAINTED_TIMBER_FRAME.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PAINTED_TIMBER_FRAME_PILLAR.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PAINTED_LOG.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PAINTED_BEAM.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PAINTED_PERGOLA.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PAINTED_FENCE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PAINTED_RAILING.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PAINTED_FANCY_RAILING.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PAINTED_SUPPORT_SLAB.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PAINTED_SUPPORT_BEAM.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PAINTED_ROOF_SUPPORT.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.REINFORCED_RED_PAINTED_RELIEF.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PAINTED_DOOR.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PAINTED_WINDOWED_DOOR.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PAINTED_SHUTTERS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PAINTED_SMALL_SHUTTER.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.PAPER_WALL_FLAT.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.PAPER_WALL.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.PAPER_WALL_SQUARED.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.PAPER_WALL_WINDOWS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.PAPER_WALL_FLOWERY.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.PAPER_DOOR.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.GRAY_ROOF_TILES.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.GRAY_ROOF_TILES_STAIRS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.GRAY_ROOF_TILES_PLATE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.GRAY_ROOF_TILES_SLAB.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.GRAY_ROOF_TILES_EDGE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.GRAY_ROOF_TILES_WALL.get().asItem(),

                    DoTBItemsRegistry.INSTANCE.UNFIRED_CLAY_ROOF_TILE.get(),
                    DoTBItemsRegistry.INSTANCE.GRAY_CLAY_ROOF_TILE.get()
            ),
            new SubTab("furniture",
                    DoTBBlocksRegistry.INSTANCE.RED_PAPER_LANTERN.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.STONE_LANTERN.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.PAPER_LAMP.get().asItem(),

                    DoTBBlocksRegistry.INSTANCE.PAPER_FOLDING_SCREEN.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WHITE_LITTLE_FLAG.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.SMALL_TATAMI_MAT.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.TATAMI_MAT.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.LIGHT_GRAY_FUTON.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WHITE_CUSHION.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.SPRUCE_LEGLESS_CHAIR.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.SPRUCE_LOW_TABLE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.IKEBANA_FLOWER_POT.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.SAKE_BOTTLE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.SAKE_CUP.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CAST_IRON_TEAPOT_GRAY.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CAST_IRON_TEACUP_GRAY.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CAST_IRON_TEAPOT_GREEN.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CAST_IRON_TEACUP_GREEN.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CAST_IRON_TEAPOT_DECORATED.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CAST_IRON_TEACUP_DECORATED.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.IRORI_FIREPLACE.get().asItem(),
                    Items.FLINT_AND_STEEL,
                    DoTBItemsRegistry.INSTANCE.JAPANESE_EMBLEM.get()
            )
    )),

    GERMAN("german", "https://www.youtube.com/watch?v=g_GulBiXvXs&list=PLRp3sDcdVhnSzsKrXbCEMr-833Em-ntDF", List.of(

            new SubTab("building",
                    DoTBBlocksRegistry.INSTANCE.RAMMED_DIRT.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RAMMED_DIRT_STAIRS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RAMMED_DIRT_PLATE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RAMMED_DIRT_SLAB.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RAMMED_DIRT_EDGE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WAXED_OAK_FRAMED_RAMMED_DIRT.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WAXED_OAK_FRAMED_RAMMED_DIRT_PILLAR.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WATTLE_AND_DAUB.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WATTLE_AND_DAUB_STAIRS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WATTLE_AND_DAUB_PLATE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WATTLE_AND_DAUB_SLAB.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WATTLE_AND_DAUB_EDGE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WAXED_OAK_TIMBER_FRAME.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WAXED_OAK_TIMBER_FRAME_PILLAR.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WAXED_OAK_TIMBER_FRAME_CORNER.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WAXED_OAK_TIMBER_FRAME_CROSSED.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WAXED_OAK_TIMBER_FRAME_SQUARED.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.REINFORCED_WAXED_OAK.get().asItem(),

                    DoTBBlocksRegistry.INSTANCE.SCULPTED_WAXED_OAK.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.SCULPTED_WAXED_OAK_STAIRS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.SCULPTED_WAXED_OAK_PLATE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.SCULPTED_WAXED_OAK_SLAB.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.SCULPTED_WAXED_OAK_EDGE.get().asItem(),

                    DoTBBlocksRegistry.INSTANCE.WAXED_OAK_PLANKS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WAXED_OAK_PLANKS_STAIRS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WAXED_OAK_PLANKS_PLATE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WAXED_OAK_PLANKS_SLAB.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WAXED_OAK_PLANKS_EDGE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WAXED_OAK_SUPPORT_SLAB.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WAXED_OAK_SUPPORT_BEAM.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WAXED_OAK_LOG_STRIPPED.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WAXED_OAK_BEAM.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WAXED_OAK_WALL.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WAXED_OAK_PERGOLA.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WAXED_OAK_FENCE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WAXED_OAK_FENCE_GATE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WAXED_OAK_BALUSTER.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WAXED_OAK_LATTICE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WAXED_OAK_DOOR.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WAXED_OAK_TRAPDOOR.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WAXED_OAK_SMALL_SHUTTER.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WAXED_OAK_SHUTTER.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.LATTICE_GLASS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.LATTICE_GLASS_PANE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.LATTICE_WAXED_OAK_WINDOW.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.LATTICE_STONE_BRICKS_WINDOW.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.STONE_BRICKS_MASONRY.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.STONE_BRICKS_MASONRY_STAIRS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.STONE_BRICKS_MASONRY_PLATE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.STONE_BRICKS_MASONRY_SLAB.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.STONE_BRICKS_MASONRY_EDGE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.STONE_BRICKS_MASONRY_WALL.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.STONE_BRICKS_ARROWSLIT.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.STONE_BRICKS_MACHICOLATION.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.STONE_BRICKS_CHIMNEY.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.STONE_BRICKS_POOL.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.STONE_BRICKS_SMALL_POOL.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.STONE_BRICKS_FAUCET.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.STONE_BRICKS_WATER_JET.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WATER_SOURCE_TRICKLE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.FLAT_ROOF_TILES.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.FLAT_ROOF_TILES_STAIRS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.FLAT_ROOF_TILES_PLATE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.FLAT_ROOF_TILES_SLAB.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.FLAT_ROOF_TILES_EDGE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.FLAT_ROOF_TILES_WALL.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.FLAT_ROOF_TILES_CHIMNEY.get().asItem(),

                    DoTBBlocksRegistry.INSTANCE.IRON_PORTCULLIS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WROUGHT_IRON_FENCE.get().asItem()
            ),
            new SubTab("furniture",
                    DoTBBlocksRegistry.INSTANCE.WAXED_OAK_TABLE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WAXED_OAK_CHAIR.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.STONE_BRICKS_FIREPLACE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CANDLESTICK.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WAXED_OAK_CHANDELIER.get().asItem(),
                    Items.FLINT_AND_STEEL,
                    DoTBItemsRegistry.INSTANCE.GERMAN_EMBLEM.get()
            )
    )),
    
    ROMAN("roman", "https://www.youtube.com/watch?v=7TgxqQHGVlo&list=PLRp3sDcdVhnQNEcVV6Zi0NvG0p80IhaZo",

            DoTBBlocksRegistry.INSTANCE.SANDSTONE_COLUMN.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.SANDSTONE_SIDED_COLUMN.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.COVERED_SANDSTONE_WALL.get().asItem(),

            DoTBBlocksRegistry.INSTANCE.BIRCH_FANCY_FENCE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.BIRCH_FOOTSTOOL.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.BIRCH_COUCH.get().asItem(),

            DoTBBlocksRegistry.INSTANCE.ROMAN_FRESCO_BLACK.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.ROMAN_FRESCO_RED.get().asItem(),

            DoTBBlocksRegistry.INSTANCE.MOSAIC_FLOOR.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.MOSAIC_FLOOR_DELICATE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.MOSAIC_FLOOR_ROSETTE.get().asItem(),

            DoTBBlocksRegistry.INSTANCE.SMOOTH_WHITE_TERRACOTTA.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.SMOOTH_WHITE_TERRACOTTA_STAIRS.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.SMOOTH_WHITE_TERRACOTTA_PLATE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.SMOOTH_WHITE_TERRACOTTA_SLAB.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.SMOOTH_WHITE_TERRACOTTA_EDGE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.SMOOTH_WHITE_TERRACOTTA_WALL.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.SMOOTH_BLACK_TERRACOTTA.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.SMOOTH_BLACK_TERRACOTTA_STAIRS.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.SMOOTH_BLACK_TERRACOTTA_PLATE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.SMOOTH_BLACK_TERRACOTTA_SLAB.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.SMOOTH_BLACK_TERRACOTTA_EDGE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.SMOOTH_BLACK_TERRACOTTA_WALL.get().asItem(),

            DoTBBlocksRegistry.INSTANCE.MARBLE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.MARBLE_STAIRS.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.MARBLE_PLATE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.MARBLE_SLAB.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.MARBLE_EDGE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.MARBLE_WALL.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.MARBLE_PILLAR.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.MARBLE_COFFER.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.MARBLE_COFFER_SLAB.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.MARBLE_COLUMN.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.MARBLE_SIDED_COLUMN.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.MARBLE_FANCY_FENCE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.MARBLE_STATUE_MARS.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.MARBLE_BIG_FLOWER_POT.get().asItem(),

            DoTBBlocksRegistry.INSTANCE.BIG_FLOWER_POT.get().asItem(),

            DoTBBlocksRegistry.INSTANCE.OCHRE_ROOF_TILES.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.OCHRE_ROOF_TILES_STAIRS.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.OCHRE_ROOF_TILES_PLATE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.OCHRE_ROOF_TILES_SLAB.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.OCHRE_ROOF_TILES_EDGE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.OCHRE_ROOF_TILES_WALL.get().asItem(),

            DoTBItemsRegistry.INSTANCE.UNFIRED_CLAY_TILE.get(),
            DoTBItemsRegistry.INSTANCE.CLAY_TILE.get(),
            DoTBItemsRegistry.INSTANCE.CLAY_TILE_ORANGE.get(),
            DoTBItemsRegistry.INSTANCE.CLAY_TILE_BLACK.get(),
            DoTBItemsRegistry.INSTANCE.ROMAN_EMBLEM.get()
    ),
    PRE_COLOMBIAN("pre_columbian", "https://www.youtube.com/watch?v=jR-dWUqHgQ8&list=PLRp3sDcdVhnTP3E2QNE-2E-inx1K51Btu", List.of(

            new SubTab("plastered",
                    DoTBBlocksRegistry.INSTANCE.THATCH_WHEAT.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.THATCH_WHEAT_STAIRS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.THATCH_WHEAT_PLATE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.THATCH_WHEAT_SLAB.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.THATCH_WHEAT_EDGE.get().asItem(),

                    DoTBBlocksRegistry.INSTANCE.PLASTERED_STONE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.PLASTERED_STONE_STAIRS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.PLASTERED_STONE_PLATE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.PLASTERED_STONE_SLAB.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.PLASTERED_STONE_EDGE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.PLASTERED_STONE_COLUMN.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.PLASTERED_STONE_WINDOW.get().asItem(),

                    DoTBBlocksRegistry.INSTANCE.RED_PLASTERED_STONE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PLASTERED_STONE_STAIRS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PLASTERED_STONE_PLATE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PLASTERED_STONE_SLAB.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PLASTERED_STONE_EDGE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PLASTERED_STONE_COLUMN.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PLASTERED_STONE_WINDOW.get().asItem(),

                    DoTBBlocksRegistry.INSTANCE.BLUE_PLASTERED_STONE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.BLUE_PLASTERED_STONE_STAIRS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.BLUE_PLASTERED_STONE_PLATE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.BLUE_PLASTERED_STONE_SLAB.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.BLUE_PLASTERED_STONE_EDGE.get().asItem(),

                    DoTBBlocksRegistry.INSTANCE.GREEN_PLASTERED_STONE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.GREEN_PLASTERED_STONE_STAIRS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.GREEN_PLASTERED_STONE_PLATE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.GREEN_PLASTERED_STONE_SLAB.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.GREEN_PLASTERED_STONE_EDGE.get().asItem(),

                    DoTBBlocksRegistry.INSTANCE.YELLOW_PLASTERED_STONE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.YELLOW_PLASTERED_STONE_STAIRS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.YELLOW_PLASTERED_STONE_PLATE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.YELLOW_PLASTERED_STONE_SLAB.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.YELLOW_PLASTERED_STONE_EDGE.get().asItem(),

                    DoTBBlocksRegistry.INSTANCE.RED_ORNAMENTED_PLASTERED_STONE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CHISELED_PLASTERED_STONE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.CHISELED_PLASTERED_STONE_FRIEZE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.ORNAMENTED_CHISELED_PLASTERED_STONE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_CHISELED_PLASTERED_STONE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_ORNAMENTED_CHISELED_PLASTERED_STONE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PLASTERED_STONE_FRIEZE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_ORNAMENTED_PLASTERED_STONE_FRIEZE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_SCULPTED_PLASTERED_STONE_FRIEZE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_SMALL_PLASTERED_STONE_FRIEZE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.GREEN_CHISELED_PLASTERED_STONE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.GREEN_ORNAMENTED_CHISELED_PLASTERED_STONE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.GREEN_PLASTERED_STONE_FRIEZE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.GREEN_ORNAMENTED_PLASTERED_STONE_FRIEZE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.GREEN_SCULPTED_PLASTERED_STONE_FRIEZE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.GREEN_SMALL_PLASTERED_STONE_FRIEZE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.FEATHERED_SERPENT_SCULPTURE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.SERPENT_SCULPTED_COLUMN.get().asItem(),
                    Items.FLINT_AND_STEEL,
                    DoTBBlocksRegistry.INSTANCE.PLASTERED_STONE_CRESSET.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.FIREPLACE.get().asItem(),

                    DoTBItemsRegistry.INSTANCE.PRECOLUMBIAN_EMBLEM.get()
            ),

            new SubTab("painted",
                    DoTBBlocksRegistry.INSTANCE.WHITE_PAINTED_STONE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WHITE_PAINTED_STONE_STAIRS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WHITE_PAINTED_STONE_PLATE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WHITE_PAINTED_STONE_SLAB.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WHITE_PAINTED_STONE_EDGE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WHITE_SPIRAL_TEMPLATE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WHITE_PAINTED_LATTICE.get().asItem(),

                    DoTBBlocksRegistry.INSTANCE.RED_PAINTED_STONE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PAINTED_BLUE_WAVE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PAINTED_BLUE_ROUND.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PAINTED_WHITE_SPIRAL.get().asItem(),

                    DoTBBlocksRegistry.INSTANCE.RED_PAINTED_STONE_STAIRS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PAINTED_STONE_PLATE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PAINTED_STONE_SLAB.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PAINTED_STONE_EDGE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PAINTED_STONE_FRIEZE_EDGE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PAINTED_CRENELATION.get().asItem(),

                    DoTBBlocksRegistry.INSTANCE.RED_PAINTED_LATTICE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.BLUE_PAINTED_STONE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.BLUE_PAINTED_STONE_STAIRS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.BLUE_PAINTED_STONE_PLATE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.BLUE_PAINTED_STONE_SLAB.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.BLUE_PAINTED_STONE_EDGE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.BLUE_WAVE_TEMPLATE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.BLUE_ROUND_TEMPLATE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.GREEN_PAINTED_STONE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.GREEN_PAINTED_STONE_STAIRS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.GREEN_PAINTED_STONE_PLATE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.GREEN_PAINTED_STONE_SLAB.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.GREEN_PAINTED_STONE_EDGE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.YELLOW_PAINTED_STONE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.YELLOW_PAINTED_STONE_STAIRS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.YELLOW_PAINTED_STONE_PLATE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.YELLOW_PAINTED_STONE_SLAB.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.YELLOW_PAINTED_STONE_EDGE.get().asItem()
            ),

            new SubTab("puuc",
                    DoTBBlocksRegistry.INSTANCE.PUUC_COBBLED_LIMESTONE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.PUUC_COBBLED_LIMESTONE_STAIRS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.PUUC_COBBLED_LIMESTONE_PLATE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.PUUC_COBBLED_LIMESTONE_SLAB.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.PUUC_COBBLED_LIMESTONE_EDGE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.PUUC_LIMESTONE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.PUUC_LIMESTONE_STAIRS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.PUUC_LIMESTONE_PLATE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.PUUC_LIMESTONE_SLAB.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.PUUC_LIMESTONE_EDGE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.PUUC_LIMESTONE_WAVE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.PUUC_LIMESTONE_CROSSED.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.PUUC_LIMESTONE_TIGHT_LATTICE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.PUUC_LIMESTONE_DECORATED.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WHITE_PAINTED_PUUC_LIMESTONE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WHITE_PAINTED_PUUC_LIMESTONE_STAIRS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WHITE_PAINTED_PUUC_LIMESTONE_PLATE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WHITE_PAINTED_PUUC_LIMESTONE_SLAB.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.WHITE_PAINTED_PUUC_LIMESTONE_EDGE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PAINTED_PUUC_LIMESTONE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PAINTED_PUUC_LIMESTONE_STAIRS.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PAINTED_PUUC_LIMESTONE_PLATE.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PAINTED_PUUC_LIMESTONE_SLAB.get().asItem(),
                    DoTBBlocksRegistry.INSTANCE.RED_PAINTED_PUUC_LIMESTONE_EDGE.get().asItem()
            )
    )),

    FRENCH("french", null,

            DoTBBlocksRegistry.INSTANCE.COBBLED_LIMESTONE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.COBBLED_LIMESTONE_STAIRS.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.COBBLED_LIMESTONE_PLATE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.COBBLED_LIMESTONE_SLAB.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.COBBLED_LIMESTONE_EDGE.get().asItem(),
            // DoTBBlocksRegistry.INSTANCE.COBBLED_LIMESTONE_WALL.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.LIMESTONE_BRICKS.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.LIMESTONE_BRICKS_STAIRS.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.LIMESTONE_BRICKS_PLATE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.LIMESTONE_BRICKS_SLAB.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.LIMESTONE_BRICKS_EDGE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.LIMESTONE_SIDED_COLUMN.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.LIMESTONE_BRICKS_WALL.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.LIMESTONE_BALUSTER.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.LIMESTONE_GARGOYLE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.LIMESTONE_FIREPLACE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.REINFORCED_GOLDEN_WROUGHT_IRON_FENCE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.REINFORCED_BLACK_WROUGHT_IRON_FENCE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.BLACK_WROUGHT_IRON_BALUSTER.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.BLACK_WROUGHT_IRON_FENCE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.ROOFING_SLATES.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.ROOFING_SLATES_STAIRS.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.ROOFING_SLATES_PLATE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.ROOFING_SLATES_SLAB.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.ROOFING_SLATES_EDGE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.ROOFING_SLATES_WALL.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.LIMESTONE_CHIMNEY.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.IRON_COLUMN.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.IRON_FANCY_LANTERN.get().asItem(),
            DoTBItemsRegistry.INSTANCE.FRENCH_EMBLEM.get()
    ),
    PERSIAN("persian", null,

            DoTBBlocksRegistry.INSTANCE.SANDSTONE_BRICKS.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.SANDSTONE_BRICKS_STAIRS.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.SANDSTONE_BRICKS_PLATE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.SANDSTONE_BRICKS_SLAB.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.SANDSTONE_BRICKS_EDGE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.SANDSTONE_BRICKS_WALL.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.SANDSTONE_BRICKS_TURQUOISE_PATTERN.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.SANDSTONE_BRICKS_TURQUOISE_PATTERN_STAIRS.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.SANDSTONE_BRICKS_TURQUOISE_PATTERN_PLATE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.SANDSTONE_BRICKS_TURQUOISE_PATTERN_SLAB.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.SANDSTONE_BRICKS_TURQUOISE_PATTERN_EDGE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.SANDSTONE_BRICKS_TURQUOISE_PATTERN_WALL.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.SANDSTONE_SCULPTED_RELIEF.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.SANDSTONE_CRENELATION.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.MORAQ_MOSAIC_TRADITIONAL.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.MORAQ_MOSAIC_DELICATE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.MORAQ_MOSAIC_BORDER.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.MORAQ_MOSAIC_PATTERN.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.MORAQ_MOSAIC_GEOMETRIC.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.MORAQ_MOSAIC_RECESS.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.MORAQ_MOSAIC_RELIEF.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.MORAQ_MOSAIC_COLUMN.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.PERSIAN_CARPET_RED.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.PERSIAN_CARPET_DELICATE_RED.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.GOLD_PLATED_SMOOTH_BLOCK.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.GOLD_PLATED_SMOOTH_STAIRS.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.GOLD_PLATED_SMOOTH_PLATE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.GOLD_PLATED_SMOOTH_SLAB.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.GOLD_PLATED_SMOOTH_EDGE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.GOLD_PLATED_SMOOTH_WALL.get().asItem(),
            DoTBItemsRegistry.INSTANCE.UNFIRED_CLAY_TILE.get(),
            DoTBItemsRegistry.INSTANCE.CLAY_TILE.get(),
            DoTBItemsRegistry.INSTANCE.CLAY_TILE_WHITE.get(),
            DoTBItemsRegistry.INSTANCE.CLAY_TILE_CYAN.get(),
            DoTBItemsRegistry.INSTANCE.CLAY_TILE_BLUE.get(),
            DoTBItemsRegistry.INSTANCE.PERSIAN_EMBLEM.get()
    ),
    CHINESE("chinese", null,
            DoTBBlocksRegistry.INSTANCE.SLATE_BRICKS.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.SLATE_BRICKS_STAIRS.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.SLATE_BRICKS_PLATE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.SLATE_BRICKS_SLAB.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.SLATE_BRICKS_EDGE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.SLATE_BRICKS_WALL.get().asItem(),

            DoTBBlocksRegistry.INSTANCE.WAXED_ACACIA_LOG_STRIPPED.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.WAXED_ACACIA_PLANKS.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.WAXED_ACACIA_STAIRS.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.WAXED_ACACIA_PLATE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.WAXED_ACACIA_SLAB.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.WAXED_ACACIA_EDGE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.WAXED_ACACIA_DOOR.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.WAXED_ACACIA_SHUTTERS.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.WAXED_ACACIA_SMALL_SHUTTERS.get().asItem(),


            DoTBBlocksRegistry.INSTANCE.WAXED_ACACIA_FANCY_RAILING.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.WAXED_ACACIA_ROOF_SUPPORT.get().asItem(),

            DoTBBlocksRegistry.INSTANCE.PAINTED_ACACIA_PLANKS.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.PAINTED_ACACIA_PLANKS_EDGE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.PAINTED_ACACIA_PLANKS_PLATE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.PAINTED_ACACIA_PLANKS_SLAB.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.PAINTED_ACACIA_PLANKS_STAIRS.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.PAINTED_ACACIA_FENCE.get().asItem(),

            DoTBBlocksRegistry.INSTANCE.GREEN_PAINTED_WINDOW.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.GREEN_PAINTED_STRAIGHT_WINDOW.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.GREEN_PAINTED_DIAMOND_WINDOW.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.GREEN_PAINTED_DECORATED_WINDOW.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.GREEN_ROOF_TILES.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.GREEN_ROOF_TILES_EDGE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.GREEN_ROOF_TILES_PLATE.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.GREEN_ROOF_TILES_SLAB.get().asItem(),
            DoTBBlocksRegistry.INSTANCE.GREEN_ROOF_TILES_STAIRS.get().asItem(),

            DoTBBlocksRegistry.INSTANCE.RED_ROUND_PAPER_LANTERN.get().asItem()

            );

    private final String name;
    private final String youtubePlaylist;
    private final Component translation;
    private final ArrayList<Item> items = new ArrayList<>();
    private final List<SubTab> subTabs;

    CreativeInventoryCategories(String name, @Nullable String youtubePlaylist, Item... items) {
        this.name = name;
        this.youtubePlaylist = youtubePlaylist;
        this.translation = Component.translatable("gui." + MOD_ID + "." + name);
        this.items.addAll(Arrays.asList(items));
        this.subTabs = List.of();
    }

    CreativeInventoryCategories(String name, @Nullable String youtubePlaylist, List<SubTab> subTabs) {
        this.name = name;
        this.youtubePlaylist = youtubePlaylist;
        this.translation = Component.translatable("gui." + MOD_ID + "." + name);
        this.subTabs = subTabs;
    }

    public String getName() {
        return this.name;
    }

    @Nullable
    public String getYoutubePlaylist() {
        return this.youtubePlaylist;
    }

    public Component getTranslation() {
        return this.translation;
    }

    public List<Item> getItems() {
        if (!subTabs.isEmpty()) {
            return subTabs.stream()
                    .flatMap(sub -> sub.items().stream())
                    .collect(Collectors.toList());
        }
        return this.items;
    }

    public List<SubTab> getSubTabs() {
        return this.subTabs;
    }

    public boolean hasSubTabs() {
        return !this.subTabs.isEmpty();
    }

    public List<Item> getSubTabItems(int subTabIndex) {
        if (!hasSubTabs()) return this.items;
        int idx = Math.max(0, Math.min(subTabIndex, subTabs.size() - 1));
        return subTabs.get(idx).items();
    }

    public record SubTab(String nameKey, ResourceLocation textureOn, ResourceLocation textureOff, List<Item> items) {
        public SubTab(String nameKey, Item... items) {
            this(nameKey,
                new ResourceLocation(MOD_ID, "textures/gui/subtab_" + nameKey + "_on.png"),
                new ResourceLocation(MOD_ID, "textures/gui/subtab_" + nameKey + "_off.png"),
                Arrays.asList(items));
        }

        public Component getTooltip() {
            return Component.translatable("tooltip." + MOD_ID + ".subtab." + nameKey);
        }
    }
}