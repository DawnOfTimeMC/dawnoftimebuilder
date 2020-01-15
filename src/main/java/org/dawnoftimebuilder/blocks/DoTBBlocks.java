package org.dawnoftimebuilder.blocks;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import org.dawnoftimebuilder.blocks.japanese.BlockCamellia;
import org.dawnoftimebuilder.blocks.japanese.BlockMulberry;
import org.dawnoftimebuilder.blocks.japanese.BlockRice;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

@ObjectHolder(MOD_ID)
public class DoTBBlocks {

	//general
	@ObjectHolder("path_dirt") public static Block path_dirt;
	@ObjectHolder("path_gravel") public static Block path_gravel;
	@ObjectHolder("path_stepping_stones") public static Block path_stepping_stones;
	@ObjectHolder("path_cobbled") public static Block path_cobbled;
	@ObjectHolder("path_ochre_tiles") public static Block path_ochre_tiles;
	@ObjectHolder("path_dirt_slab") public static Block path_dirt_slab;
	@ObjectHolder("path_gravel_slab") public static Block path_gravel_slab;
	@ObjectHolder("path_stepping_stones_slab") public static Block path_stepping_stones_slab;
	@ObjectHolder("path_cobbled_slab") public static Block path_cobbled_slab;
	@ObjectHolder("path_ochre_tiles_slab") public static Block path_ochre_tiles_slab;
	@ObjectHolder("rammed_dirt") public static Block rammed_dirt;
	@ObjectHolder("thatch_wheat") public static Block thatch_wheat;
	@ObjectHolder("thatch_wheat_stairs") public static Block thatch_wheat_stairs;
	@ObjectHolder("thatch_wheat_slab") public static Block thatch_wheat_slab;
	@ObjectHolder("thatch_wheat_edge") public static Block thatch_wheat_edge;
	@ObjectHolder("thatch_bamboo") public static Block thatch_bamboo;
	@ObjectHolder("thatch_bamboo_stairs") public static Block thatch_bamboo_stairs;
	@ObjectHolder("thatch_bamboo_slab") public static Block thatch_bamboo_slab;
	@ObjectHolder("thatch_bamboo_edge") public static Block thatch_bamboo_edge;
	@ObjectHolder("fireplace") public static Block fireplace;
	@ObjectHolder("iron_chain") public static Block iron_chain;
	@ObjectHolder("stone_frieze") public static Block stone_frieze;

	//french
	@ObjectHolder("cobbled_limestone") public static Block cobbled_limestone;
	@ObjectHolder("limestone_brick") public static Block limestone_brick;
	@ObjectHolder("limestone_brick_stairs") public static Block limestone_brick_stairs;
	@ObjectHolder("limestone_brick_slab") public static Block limestone_brick_slab;
	@ObjectHolder("limestone_brick_edge") public static Block limestone_brick_edge;
	@ObjectHolder("limestone_brick_wall") public static Block limestone_brick_wall;
	@ObjectHolder("limestone_fireplace") public static Block limestone_fireplace;
	@ObjectHolder("limestone_chimney") public static Block limestone_chimney;
	@ObjectHolder("framed_rammed_dirt") public static Block framed_rammed_dirt;
	@ObjectHolder("iron_portcullis") public static Block iron_portcullis;
	@ObjectHolder("lattice_glass") public static Block lattice_glass;
	@ObjectHolder("lattice_glass_pane") public static Block lattice_glass_pane;
	@ObjectHolder("lattice_oak_window") public static Block lattice_oak_window;
	@ObjectHolder("oak_planks_edge") public static Block oak_planks_edge;
	@ObjectHolder("small_oak_shutters") public static Block small_oak_shutters;
	@ObjectHolder("oak_shutters") public static Block oak_shutters;
	@ObjectHolder("oak_beam") public static Block oak_beam;
	@ObjectHolder("oak_support_beam") public static Block oak_support_beam;
	@ObjectHolder("oak_support_slab") public static Block oak_support_slab;
	@ObjectHolder("oak_timber_frame") public static Block oak_timber_frame;
	@ObjectHolder("oak_timber_frame_corner") public static Block oak_timber_frame_corner;
	@ObjectHolder("oak_timber_frame_pillar") public static Block oak_timber_frame_pillar;
	@ObjectHolder("flat_roof_tiles") public static Block flat_roof_tiles;
	@ObjectHolder("oak_waxed_planks") public static Block oak_waxed_planks;
	@ObjectHolder("oak_waxed_planks_stairs") public static Block oak_waxed_planks_stairs;
	@ObjectHolder("oak_waxed_planks_slab") public static Block oak_waxed_planks_slab;
	@ObjectHolder("oak_waxed_planks_edge") public static Block oak_waxed_planks_edge;
	@ObjectHolder("oak_waxed_fence") public static Block oak_waxed_fence;
	@ObjectHolder("flat_roof_tiles_stairs") public static Block flat_roof_tiles_stairs;
	@ObjectHolder("flat_roof_tiles_slab") public static Block flat_roof_tiles_slab;
	@ObjectHolder("flat_roof_tiles_edge") public static Block flat_roof_tiles_edge;

	//japanese
	@ObjectHolder("bamboo_drying_tray") public static Block bamboo_drying_tray;
	@ObjectHolder("camellia") public static BlockCamellia camellia;
	@ObjectHolder("mulberry") public static BlockMulberry mulberry;
	@ObjectHolder("cast_iron_teapot") public static Block cast_iron_teapot;
	@ObjectHolder("cast_iron_teacup") public static Block cast_iron_teacup;
	@ObjectHolder("flowery_paper_wall") public static Block flowery_paper_wall;
	@ObjectHolder("paper_lamp") public static Block paper_lamp;
	@ObjectHolder("grey_roof_tiles") public static Block grey_roof_tiles;
	@ObjectHolder("grey_roof_tiles_stairs") public static Block grey_roof_tiles_stairs;
	@ObjectHolder("grey_roof_tiles_slab") public static Block grey_roof_tiles_slab;
	@ObjectHolder("grey_roof_tiles_edge") public static Block grey_roof_tiles_edge;
	@ObjectHolder("grey_roof_tiles_wall") public static Block grey_roof_tiles_wall;
	@ObjectHolder("ikebana_flower_pot") public static Block ikebana_flower_pot;
	@ObjectHolder("spruce_legless_chair") public static Block spruce_legless_chair;
	@ObjectHolder("paper_folding_screen") public static Block paper_folding_screen;
	@ObjectHolder("paper_lantern") public static Block paper_lantern;
	@ObjectHolder("paper_wall") public static Block paper_wall;
	@ObjectHolder("paper_wall_flat") public static Block paper_wall_flat;
	@ObjectHolder("paper_wall_window") public static Block paper_wall_window;
	@ObjectHolder("red_painted_log") public static Block red_painted_log;
	@ObjectHolder("small_tatami_mat") public static Block small_tatami_mat;
	@ObjectHolder("small_tatami_floor") public static Block small_tatami_floor;
	@ObjectHolder("spruce_beam") public static Block spruce_beam;
	@ObjectHolder("spruce_foundation") public static Block spruce_foundation;
	@ObjectHolder("spruce_log_fence") public static Block spruce_log_fence;
	@ObjectHolder("spruce_log_wall") public static Block spruce_log_wall;
	@ObjectHolder("spruce_log_covered") public static Block spruce_log_covered;
	@ObjectHolder("spruce_planks_edge") public static Block spruce_planks_edge;
	@ObjectHolder("spruce_railing") public static Block spruce_railing;
	@ObjectHolder("spruce_roof_support") public static Block spruce_roof_support;
	@ObjectHolder("spruce_roof_support_merged") public static Block spruce_roof_support_merged;
	@ObjectHolder("spruce_support_beam") public static Block spruce_support_beam;
	@ObjectHolder("spruce_support_slab") public static Block spruce_support_slab;
	@ObjectHolder("spruce_low_table") public static Block spruce_low_table;
	@ObjectHolder("spruce_timber_frame") public static Block spruce_timber_frame;
	@ObjectHolder("spruce_timber_frame_pillar") public static Block spruce_timber_frame_pillar;
	@ObjectHolder("stick_bundle") public static Block stick_bundle;
	@ObjectHolder("stone_lantern") public static Block stone_lantern;
	@ObjectHolder("tatami_floor") public static Block tatami_floor;
	@ObjectHolder("irori") public static Block irori;
	@ObjectHolder("sake_bottle") public static Block sake_bottle;
	@ObjectHolder("sake_cup") public static Block sake_cup;
	@ObjectHolder("spruce_foundation_slab") public static Block spruce_foundation_slab;
	@ObjectHolder("futon") public static Block futon;
	@ObjectHolder("little_flag") public static Block little_flag;
	@ObjectHolder("paper_door") public static Block paper_door;
	@ObjectHolder("rice") public static BlockRice rice;
	@ObjectHolder("tatami_mat") public static Block tatami_mat;

	//mayan
	@ObjectHolder("plastered_stone") public static Block plastered_stone;
	@ObjectHolder("plastered_stone_slab") public static Block plastered_stone_slab;
	@ObjectHolder("plastered_stone_edge") public static Block plastered_stone_edge;
	@ObjectHolder("plastered_stone_plate") public static Block plastered_stone_plate;
	@ObjectHolder("red_plastered_stone_slab") public static Block red_plastered_stone_slab;
	@ObjectHolder("red_plastered_stone_edge") public static Block red_plastered_stone_edge;
	@ObjectHolder("feathered_serpent_sculpture") public static Block feathered_serpent_sculpture;
	@ObjectHolder("green_ornamented_plastered_stone_frieze") public static Block green_ornamented_plastered_stone_frieze;
	@ObjectHolder("chiseled_plastered_stone") public static Block chiseled_plastered_stone;
	@ObjectHolder("green_plastered_stone_frieze") public static Block green_plastered_stone_frieze;
	@ObjectHolder("green_sculpted_plastered_stone_frieze") public static Block green_sculpted_plastered_stone_frieze;
	@ObjectHolder("green_small_plastered_stone_frieze") public static Block green_small_plastered_stone_frieze;
	@ObjectHolder("plastered_stone_column") public static Block plastered_stone_column;
	@ObjectHolder("plastered_stone_cresset") public static Block plastered_stone_cresset;
	@ObjectHolder("plastered_stone_frieze") public static Block plastered_stone_frieze;
	@ObjectHolder("plastered_stone_stairs") public static Block plastered_stone_stairs;
	@ObjectHolder("plastered_stone_window") public static Block plastered_stone_window;
	@ObjectHolder("red_plastered_stone_plate") public static Block red_plastered_stone_plate;
	@ObjectHolder("red_plastered_stone_frieze") public static Block red_plastered_stone_frieze;
	@ObjectHolder("red_ornamented_plastered_stone_frieze") public static Block red_ornamented_plastered_stone_frieze;
	@ObjectHolder("red_plastered_stone_stairs") public static Block red_plastered_stone_stairs;
	@ObjectHolder("red_sculpted_plastered_stone_frieze") public static Block red_sculpted_plastered_stone_frieze;
	@ObjectHolder("red_small_plastered_stone_frieze") public static Block red_small_plastered_stone_frieze;
	@ObjectHolder("serpent_sculpted_column") public static Block serpent_sculpted_column;

	//roman
	@ObjectHolder("ochre_roof_tiles") public static Block ochre_roof_tiles;
	@ObjectHolder("ochre_roof_tiles_merged") public static Block ochre_roof_tiles_merged;
	@ObjectHolder("ochre_roof_tiles_slab") public static Block ochre_roof_tiles_slab;
	@ObjectHolder("sandstone_column") public static Block sandstone_column;
}