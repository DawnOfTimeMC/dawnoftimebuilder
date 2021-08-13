package org.dawnoftimebuilder.registries;

import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import org.dawnoftimebuilder.block.IBlockCustomItem;
import org.dawnoftimebuilder.block.french.LimestoneChimneyBlock;
import org.dawnoftimebuilder.block.precolumbian.*;
import org.dawnoftimebuilder.block.roman.CypressLeavesBlock;
import org.dawnoftimebuilder.block.roman.SandstoneColumnBlock;
import org.dawnoftimebuilder.block.templates.*;
import org.dawnoftimebuilder.block.german.*;
import org.dawnoftimebuilder.block.general.*;
import org.dawnoftimebuilder.block.japanese.*;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.block.Blocks.*;
import static net.minecraftforge.common.PlantType.Crop;
import static net.minecraftforge.common.PlantType.Plains;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class DoTBBlocksRegistry {

	public static final List<Block> BLOCKS = new ArrayList<>();

	//General
    public static final Block ACACIA_PLANKS_EDGE = reg("acacia_planks_edge", new EdgeBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block ACACIA_PLANKS_PLATE = reg("acacia_planks_plate", new PlateBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block ACACIA_PERGOLA = reg("acacia_pergola", new PergolaBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block ACACIA_LATTICE = reg("acacia_lattice", new LatticeBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block ACACIA_BEAM = reg("acacia_beam", new BeamBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block ACACIA_WALL = reg("acacia_wall", new WallBlockDoTB(Material.ROCK, 1.5F, 6.0F).setBurnable());
	public static final Block ACACIA_SUPPORT_BEAM = reg("acacia_support_beam", new SupportBeamBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block ACACIA_SUPPORT_SLAB = reg("acacia_support_slab", new SupportSlabBlock(Material.WOOD, 1.5F, 3.0F).setBurnable());
	public static final Block BIRCH_PLANKS_EDGE = reg("birch_planks_edge", new EdgeBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block BIRCH_PLANKS_PLATE = reg("birch_planks_plate", new PlateBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block BIRCH_PERGOLA = reg("birch_pergola", new PergolaBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block BIRCH_LATTICE = reg("birch_lattice", new LatticeBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block BIRCH_BEAM = reg("birch_beam", new BeamBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block BIRCH_WALL = reg("birch_wall", new WallBlockDoTB(Material.ROCK, 1.5F, 6.0F).setBurnable());
	public static final Block BIRCH_SUPPORT_BEAM = reg("birch_support_beam", new SupportBeamBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block BIRCH_SUPPORT_SLAB = reg("birch_support_slab", new SupportSlabBlock(Material.WOOD, 1.5F, 3.0F).setBurnable());
	public static final Block CHAIN = reg("chain", new ChainBlock(Material.IRON, 5.0F, 6.0F));
	public static final Block CANDLESTICK = reg("candlestick", new CandlestickBlock(Material.IRON, 2.0F, 6.0F));
	public static final Block DARK_OAK_PLANKS_EDGE = reg("dark_oak_planks_edge", new EdgeBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block DARK_OAK_PLANKS_PLATE = reg("dark_oak_planks_plate", new PlateBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block DARK_OAK_PERGOLA = reg("dark_oak_pergola", new PergolaBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block DARK_OAK_LATTICE = reg("dark_oak_lattice", new LatticeBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block DARK_OAK_BEAM = reg("dark_oak_beam", new BeamBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block DARK_OAK_WALL = reg("dark_oak_wall", new WallBlockDoTB(Material.ROCK, 1.5F, 6.0F).setBurnable());
	public static final Block DARK_OAK_SUPPORT_BEAM = reg("dark_oak_support_beam", new SupportBeamBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block DARK_OAK_SUPPORT_SLAB = reg("dark_oak_support_slab", new SupportSlabBlock(Material.WOOD, 1.5F, 3.0F).setBurnable());
	public static final Block JUNGLE_PLANKS_EDGE = reg("jungle_planks_edge", new EdgeBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block JUNGLE_PLANKS_PLATE = reg("jungle_planks_plate", new PlateBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block JUNGLE_PERGOLA = reg("jungle_pergola", new PergolaBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block JUNGLE_LATTICE = reg("jungle_lattice", new LatticeBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block JUNGLE_BEAM = reg("jungle_beam", new BeamBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block JUNGLE_WALL = reg("jungle_wall", new WallBlockDoTB(Material.ROCK, 1.5F, 6.0F).setBurnable());
	public static final Block JUNGLE_SUPPORT_BEAM = reg("jungle_support_beam", new SupportBeamBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block JUNGLE_SUPPORT_SLAB = reg("jungle_support_slab", new SupportSlabBlock(Material.WOOD, 1.5F, 3.0F).setBurnable());
	public static final Block OAK_PLANKS_PLATE = reg("oak_planks_plate", new PlateBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
    public static final Block OAK_PLANKS_EDGE = reg("oak_planks_edge", new EdgeBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block OAK_PERGOLA = reg("oak_pergola", new PergolaBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block OAK_LATTICE = reg("oak_lattice", new LatticeBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block OAK_BEAM = reg("oak_beam", new BeamBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block OAK_WALL = reg("oak_wall", new WallBlockDoTB(Material.ROCK, 1.5F, 6.0F).setBurnable());
	public static final Block OAK_SUPPORT_BEAM = reg("oak_support_beam", new SupportBeamBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block OAK_SUPPORT_SLAB = reg("oak_support_slab", new SupportSlabBlock(Material.WOOD, 1.5F, 3.0F).setBurnable());
	public static final Block RAMMED_DIRT = reg("rammed_dirt", new BlockDoTB(Material.EARTH, 0.7F, 0.7F));
	public static final Block SPRUCE_PLANKS_EDGE = reg("spruce_planks_edge", new EdgeBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block SPRUCE_PLANKS_PLATE = reg("spruce_planks_plate", new PlateBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block SPRUCE_PERGOLA = reg("spruce_pergola", new PergolaBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block SPRUCE_LATTICE = reg("spruce_lattice", new LatticeBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block SPRUCE_BEAM = reg("spruce_beam", new BeamBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block SPRUCE_WALL = reg("spruce_wall", new WallBlockDoTB(Material.ROCK, 1.5F, 6.0F).setBurnable());
	public static final Block SPRUCE_SUPPORT_BEAM = reg("spruce_support_beam", new SupportBeamBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block SPRUCE_SUPPORT_SLAB = reg("spruce_support_slab", new SupportSlabBlock(Material.WOOD, 1.5F, 3.0F).setBurnable());
	public static final Block THATCH_WHEAT = reg("thatch_wheat", new BlockDoTB(Material.WOOL, 1.0F, 1.0F).setBurnable(80, 30));
	public static final Block THATCH_WHEAT_EDGE = reg("thatch_wheat_edge", new EdgeBlock(THATCH_WHEAT).setBurnable(80, 30));
	public static final Block THATCH_WHEAT_PLATE = reg("thatch_wheat_plate", new PlateBlock(THATCH_WHEAT).setBurnable(80, 30));
	public static final Block THATCH_WHEAT_SLAB = reg("thatch_wheat_slab", new SlabBlockDoTB(THATCH_WHEAT).setBurnable(80, 30));
	public static final Block THATCH_WHEAT_STAIRS = reg("thatch_wheat_stairs", new StairsBlockDoTB(THATCH_WHEAT).setBurnable(80, 30));
	public static final Block THATCH_BAMBOO = reg("thatch_bamboo", new BlockDoTB(Material.WOOL, 1.0F, 1.0F).setBurnable(40, 30));
	public static final Block THATCH_BAMBOO_EDGE = reg("thatch_bamboo_edge", new EdgeBlock(THATCH_BAMBOO).setBurnable(40, 30));
	public static final Block THATCH_BAMBOO_PLATE = reg("thatch_bamboo_plate", new PlateBlock(THATCH_BAMBOO).setBurnable(40, 30));
	public static final Block THATCH_BAMBOO_SLAB = reg("thatch_bamboo_slab", new SlabBlockDoTB(THATCH_BAMBOO).setBurnable(40, 30));
	public static final Block THATCH_BAMBOO_STAIRS = reg("thatch_bamboo_stairs", new StairsBlockDoTB(THATCH_BAMBOO).setBurnable(40, 30));
	public static final Block FIREPLACE = reg("fireplace", new FireplaceBlock());
	public static final Block IRON_PORTCULLIS = reg("iron_portcullis", new PortcullisBlock());

	//French
	public static final Block COBBLED_LIMESTONE = reg("cobbled_limestone", new BlockDoTB(Material.ROCK, 2.0F, 6.0F));
	public static final Block LIMESTONE_BRICKS = reg("limestone_bricks", new BlockDoTB(Material.ROCK,1.5F, 6.0F));
	public static final Block LIMESTONE_BRICKS_EDGE = reg("limestone_bricks_edge", new EdgeBlock(LIMESTONE_BRICKS));
	public static final Block LIMESTONE_BRICKS_PLATE = reg("limestone_bricks_plate", new PlateBlock(LIMESTONE_BRICKS));
	public static final Block LIMESTONE_BRICKS_SLAB = reg("limestone_bricks_slab", new SlabBlockDoTB(LIMESTONE_BRICKS));
	public static final Block LIMESTONE_BRICKS_STAIRS = reg("limestone_bricks_stairs", new StairsBlockDoTB(LIMESTONE_BRICKS));
	public static final Block LIMESTONE_BRICKS_WALL = reg("limestone_bricks_wall", new WallBlockDoTB(Material.ROCK, 1.5F, 6.0F));
	public static final Block LIMESTONE_CHIMNEY = reg("limestone_chimney", new LimestoneChimneyBlock());
	public static final Block LIMESTONE_FIREPLACE = reg("limestone_fireplace", new MultiblockFireplaceBlock(Material.ROCK, 2.0F, 6.0F));
	//public static final Block X = reg(new BlockStoneFrieze());

	//German
	public static final Block FLAT_ROOF_TILES = reg("flat_roof_tiles", new BlockDoTB(Material.ROCK,1.5F, 5.0F));
	public static final Block FLAT_ROOF_TILES_STAIRS = reg("flat_roof_tiles_stairs", new StairsBlockDoTB(FLAT_ROOF_TILES));
	public static final Block FLAT_ROOF_TILES_SLAB = reg("flat_roof_tiles_slab", new SlabBlockDoTB(FLAT_ROOF_TILES));
	public static final Block FLAT_ROOF_TILES_EDGE = reg("flat_roof_tiles_edge", new EdgeBlock(FLAT_ROOF_TILES));
	public static final Block LATTICE_GLASS = reg("lattice_glass", new GlassBlockDoTB(1.0F, 1.0F));
	public static final Block LATTICE_GLASS_PANE = reg("lattice_glass_pane", new PaneBlockDoTB(Material.GLASS, 1.0F, 1.0F, BlockRenderLayer.TRANSLUCENT));
	public static final Block LATTICE_WAXED_OAK_WINDOW = reg("lattice_waxed_oak_window", new SidedWindowBlock(Material.GLASS, 2.0F, 3.0F));
	public static final Block LATTICE_STONE_BRICKS_WINDOW = reg("lattice_stone_bricks_window", new LatticeStoneBricksWindowBlock());
	public static final Block STONE_BRICKS_ARROWSLIT = reg("stone_bricks_arrowslit", new StoneBricksArrowslitBlock());
	public static final Block STONE_BRICKS_CHIMNEY = reg("stone_bricks_chimney", new StoneBricksChimneyBlock());
	public static final Block STONE_BRICKS_EDGE = reg("stone_bricks_edge", new EdgeBlock(STONE_BRICKS));
	public static final Block STONE_BRICKS_FIREPLACE = reg("stone_bricks_fireplace", new MultiblockFireplaceBlock(Material.ROCK, 2.0F, 6.0F));
	public static final Block STONE_BRICKS_MACHICOLATION = reg("stone_bricks_machicolation", new StoneBricksMachicolationBlock());
	public static final Block STONE_BRICKS_PLATE = reg("stone_bricks_plate", new PlateBlock(STONE_BRICKS));
	public static final Block WAXED_OAK_FRAMED_RAMMED_DIRT = reg("waxed_oak_framed_rammed_dirt", new BlockDoTB(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block WAXED_OAK_DOOR = reg("waxed_oak_door", new DoorBlockDoTB(Material.WOOD, 1.5F, 6.0F).setBurnable());
	public static final Block WAXED_OAK_TRAPDOOR = reg("waxed_oak_trapdoor", new TrapDoorBlockDoTB(Material.WOOD, 2.0F, 3.0F));
	public static final Block WAXED_OAK_SHUTTERS = reg("waxed_oak_shutters", new ShuttersBlock(Material.WOOD, 2.0F, 2.0F));
	public static final Block WAXED_OAK_LOG_STRIPPED = reg("waxed_oak_log_stripped", new RotatedPillarBlockDoTB(Material.WOOD, 2.5F, 5.0F).setBurnable());
	public static final Block WAXED_OAK_BEAM = reg("waxed_oak_beam", new BeamBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block WAXED_OAK_SUPPORT_BEAM = reg("waxed_oak_support_beam", new SupportBeamBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block WAXED_OAK_SUPPORT_SLAB = reg("waxed_oak_support_slab", new SupportSlabBlock(Material.WOOD, 1.5F, 3.0F).setBurnable());
	public static final Block WAXED_OAK_SMALL_SHUTTERS = reg("waxed_oak_small_shutters", new SmallShuttersBlock(Material.WOOD, 2.0F, 2.0F).setBurnable());
	public static final Block WAXED_OAK_TIMBER_FRAME = reg("waxed_oak_timber_frame", new BlockDoTB(Material.WOOD, 3.0F, 5.0F).setBurnable());
	public static final Block WAXED_OAK_TIMBER_FRAME_CORNER = reg("waxed_oak_timber_frame_corner", new WaxedOakTimberFrameCornerBlock().setBurnable());
	public static final Block WAXED_OAK_TIMBER_FRAME_CROSSED = reg("waxed_oak_timber_frame_crossed", new BlockDoTB(Material.WOOD, 3.0F, 5.0F).setBurnable());
	public static final Block WAXED_OAK_TIMBER_FRAME_PILLAR = reg("waxed_oak_timber_frame_pillar", new RotatedPillarBlockDoTB(Material.WOOD, 3.0F, 5.0F).setBurnable());
	public static final Block WAXED_OAK_TIMBER_FRAME_SQUARED = reg("waxed_oak_timber_frame_squared", new BlockDoTB(Material.WOOD, 3.0F, 5.0F).setBurnable());
	public static final Block WAXED_OAK_FENCE = reg("waxed_oak_fence", new FenceBlockDoTB(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block WAXED_OAK_WALL = reg("waxed_oak_wall", new WallBlockDoTB(Material.ROCK, 1.5F, 6.0F).setBurnable());
	public static final Block WAXED_OAK_PERGOLA = reg("waxed_oak_pergola", new PergolaBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block WAXED_OAK_LATTICE = reg("waxed_oak_lattice", new LatticeBlock(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block WAXED_OAK_PLANKS = reg("waxed_oak_planks", new BlockDoTB(Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block WAXED_OAK_PLANKS_EDGE = reg("waxed_oak_planks_edge", new EdgeBlock(WAXED_OAK_PLANKS).setBurnable());
	public static final Block WAXED_OAK_PLANKS_PLATE = reg("waxed_oak_planks_plate", new PlateBlock(WAXED_OAK_PLANKS).setBurnable());
	public static final Block WAXED_OAK_PLANKS_SLAB = reg("waxed_oak_planks_slab", new SlabBlockDoTB(WAXED_OAK_PLANKS).setBurnable());
	public static final Block WAXED_OAK_PLANKS_STAIRS = reg("waxed_oak_planks_stairs", new StairsBlockDoTB(WAXED_OAK_PLANKS).setBurnable());
	public static final Block WAXED_OAK_CHANDELIER = reg("waxed_oak_chandelier", new WaxedOakChandelier());


	//Japanese
	public static final Block CHARRED_SPRUCE_LOG_STRIPPED = reg("charred_spruce_log_stripped", new RotatedPillarBlockDoTB(Material.WOOD, 2.5F, 5.0F).setBurnable(2, 3));
	public static final Block CHARRED_SPRUCE_PLANKS = reg("charred_spruce_planks", new BlockDoTB(Material.WOOD, 2.5F, 6.0F).setBurnable(2, 3));
	public static final Block CHARRED_SPRUCE_PLANKS_EDGE = reg("charred_spruce_planks_edge", new EdgeBlock(CHARRED_SPRUCE_PLANKS).setBurnable(2, 3));
	public static final Block CHARRED_SPRUCE_PLANKS_PLATE = reg("charred_spruce_planks_plate", new PlateBlock(CHARRED_SPRUCE_PLANKS).setBurnable(2, 3));
	public static final Block CHARRED_SPRUCE_PLANKS_SLAB = reg("charred_spruce_planks_slab", new SlabBlockDoTB(CHARRED_SPRUCE_PLANKS).setBurnable(2, 3));
	public static final Block CHARRED_SPRUCE_PLANKS_STAIRS = reg("charred_spruce_planks_stairs", new StairsBlockDoTB(CHARRED_SPRUCE_PLANKS).setBurnable(2, 3));
	public static final Block CHARRED_SPRUCE_BOARDS =reg("charred_spruce_boards", new BlockDoTB(Material.WOOD, 2.5F, 5.0F));
	public static final Block CHARRED_SPRUCE_DOOR = reg("charred_spruce_door", new DoorBlockDoTB(Material.WOOD, 1.5F, 6.0F));
	public static final Block CHARRED_SPRUCE_TRAPDOOR = reg("charred_spruce_trapdoor", new TrapDoorBlockDoTB(Material.WOOD, 2.0F, 3.0F));
	public static final Block CHARRED_SPRUCE_SHUTTERS = reg("charred_spruce_shutters", new CharredSpruceShuttersBlock());
	public static final Block CHARRED_SPRUCE_FOUNDATION = reg("charred_spruce_foundation", new BlockDoTB(Material.WOOD, 2.5F, 5.0F).setBurnable(2, 3));
	public static final Block CHARRED_SPRUCE_FOUNDATION_SLAB = reg("charred_spruce_foundation_slab", new SlabBlockDoTB(Material.WOOD, 2.5F, 5.0F).setBurnable(2, 3));
	public static final Block CHARRED_SPRUCE_WALL = reg("charred_spruce_wall", new WallBlockDoTB(Material.WOOD, 2.5F, 5.0F).setBurnable(2, 10));
	public static final Block CHARRED_SPRUCE_FENCE = reg("charred_spruce_fence", new FenceBlockDoTB(Material.WOOD, 2.5F, 5.0F).setBurnable(2, 10));
	public static final Block CHARRED_SPRUCE_RAILING = reg("charred_spruce_railing", new CharredSpruceRailingBlock());
	public static final Block CHARRED_SPRUCE_PERGOLA = reg("charred_spruce_pergola", new PergolaBlock(Material.WOOD, 2.0F, 3.0F).setBurnable(2, 3));
	public static final Block CHARRED_SPRUCE_LATTICE = reg("charred_spruce_lattice", new LatticeBlock(Material.WOOD, 2.0F, 3.0F).setBurnable(2, 3));
	public static final Block CHARRED_SPRUCE_SUPPORT_SLAB = reg("charred_spruce_support_slab", new SupportSlabBlock(Material.WOOD, 1.5F, 3.0F).setBurnable(2, 3));
	public static final Block CHARRED_SPRUCE_SUPPORT_BEAM = reg("charred_spruce_support_beam", new SupportBeamBlock(Material.WOOD, 2.0F, 3.0F).setBurnable(2, 3));
	public static final Block CHARRED_SPRUCE_BEAM = reg("charred_spruce_beam", new BeamBlock(Material.WOOD, 2.0F, 3.0F).setBurnable(2, 3));
	public static final Block CHARRED_SPRUCE_TIMBER_FRAME = reg("charred_spruce_timber_frame", new BlockDoTB(Material.WOOD, 3.0F, 6.0F).setBurnable());
	public static final Block CHARRED_SPRUCE_TIMBER_FRAME_PILLAR = reg("charred_spruce_timber_frame_pillar", new RotatedPillarBlockDoTB(Material.WOOD, 3.0F, 6.0F).setBurnable());
	public static final Block SPRUCE_LOW_TABLE = reg("spruce_low_table" ,new SpruceLowTableBlock());
	public static final Block GRAY_ROOF_TILES = reg("gray_roof_tiles", new BlockDoTB(Material.ROCK,1.5F, 5.0F));
	public static final Block GRAY_ROOF_TILES_STAIRS = reg("gray_roof_tiles_stairs", new StairsBlockDoTB(GRAY_ROOF_TILES));
	public static final Block GRAY_ROOF_TILES_PLATE = reg("gray_roof_tiles_plate", new PlateBlock(GRAY_ROOF_TILES));
	public static final Block GRAY_ROOF_TILES_SLAB = reg("gray_roof_tiles_slab", new SlabBlockDoTB(GRAY_ROOF_TILES));
	public static final Block GRAY_ROOF_TILES_EDGE = reg("gray_roof_tiles_edge", new EdgeBlock(GRAY_ROOF_TILES));
	public static final Block GRAY_ROOF_TILES_WALL = reg("gray_roof_tiles_wall", new WallBlockDoTB(Material.ROCK,1.5F, 5.0F));
	public static final Block CHARRED_SPRUCE_ROOF_SUPPORT = reg("charred_spruce_roof_support", new MixedRoofSupportBlock((SlabBlock) GRAY_ROOF_TILES_SLAB, CHARRED_SPRUCE_PLANKS));
	public static final Block STEPPING_STONES = reg("stepping_stones", new BlockDoTB(Block.Properties.create(Material.SAND, MaterialColor.STONE).hardnessAndResistance(1.2F).sound(SoundType.GROUND)));
	public static final Block STEPPING_STONES_SLAB = reg("stepping_stones_slab", new SlabBlockDoTB(STEPPING_STONES));
	public static final Block CAST_IRON_TEAPOT_GRAY = reg("cast_iron_teapot_gray", new CastIronTeapotBlock(0.15F));
	public static final Block CAST_IRON_TEAPOT_GREEN = reg("cast_iron_teapot_green", new CastIronTeapotBlock(0.05F));
	public static final Block CAST_IRON_TEAPOT_DECORATED = reg("cast_iron_teapot_decorated", new CastIronTeapotBlock(0.07F));
	public static final Block CAST_IRON_TEACUP_GRAY = reg("cast_iron_teacup_gray", new CastIronTeacupBlock());
	public static final Block CAST_IRON_TEACUP_GREEN = reg("cast_iron_teacup_green", new CastIronTeacupBlock());
	public static final Block CAST_IRON_TEACUP_DECORATED = reg("cast_iron_teacup_decorated", new CastIronTeacupBlock());
	//public static final Block X = reg(new DoTBBlockDryer("bamboo_drying_tray", Material.WOOD, 1.0F, SoundType.WOOD));
	public static final Block CAMELLIA = reg("camellia", new GrowingBushBlock("camellia_seeds", Plains, 3));
	public static final Block MULBERRY = reg("mulberry", new DoubleGrowingBushBlock("mulberry", Plains, 3, 2));
	public static final Block IKEBANA_FLOWER_POT = reg("ikebana_flower_pot", new IkebanaFlowerPotBlock());
	//public static final Block X = reg(new BlockSpruceLeglessChair());
	//public static final Block X = reg(new BlockLittleFlag());
	//public static final Block X = reg(new BlockPaperDoor());
	public static final Block PAPER_WALL = reg("paper_wall", new PaneBlockDoTB(Material.WOOL, 1.0F, 1.0F).setBurnable());
	public static final Block PAPER_WALL_FLAT = reg("paper_wall_flat", new PaneBlockDoTB(Material.WOOL, 1.0F, 1.0F).setBurnable());
	public static final Block PAPER_WALL_WINDOWS = reg("paper_wall_window", new PaneBlockDoTB(Material.WOOL, 1.0F, 1.0F).setBurnable());
	public static final Block PAPER_WALL_FLOWERY = reg("paper_wall_flowery", new PaneBlockDoTB(Material.WOOL, 1.0F, 1.0F).setBurnable());
	public static final Block PAPER_FOLDING_SCREEN = reg("paper_folding_screen", new FoldingScreenBlock(Material.WOOL, 1.0F, 1.0F).setBurnable());
	public static final Block RED_PAPER_LANTERN = reg("red_paper_lantern", new PaperLanternBlock());
	public static final Block PAPER_LAMP = reg("paper_lamp", new PaperLampBlock());
	public static final Block STONE_LANTERN = reg("stone_lantern", new StoneLanternBlock());
	//public static final Block X = reg(new BlockRedPaintedLog());
	public static final Block RICE = reg("rice", new WaterDoubleCropsBlock("rice", 2));
	public static final Block SMALL_TATAMI_MAT = reg("small_tatami_mat", new SmallTatamiMatBlock());
	public static final Block SMALL_TATAMI_FLOOR = reg("small_tatami_floor", new SmallTatamiFloorBlock());
	public static final Block TATAMI_MAT = reg("tatami_mat", new TatamiMatBlock());
	public static final Block TATAMI_FLOOR = reg("tatami_floor", new TatamiFloorBlock());
	//public static final Block X = reg(new BlockFuton());
	//public static final Block X = reg(new BlockIrori());
	public static final Block SAKE_BOTTLE = reg("sake_bottle", new SakeBottleBlock());
	public static final Block SAKE_CUP = reg("sake_cup", new SakeCupBlock());
	//public static final Block X = reg(new BlockStickBundle());

	//Persian
	public static final Block PERSIAN_CARPET_RED = reg("persian_carpet_red", new CarpetBlockDoTB(Material.CARPET, 0.1F, 0.1F));

	//Pre_columbian
	public static final Block COMMELINA = reg("commelina", new SoilCropsBlock("commelina", Crop));
	public static final Block PLASTERED_STONE = reg("plastered_stone", new BlockDoTB(Material.ROCK,1.5F, 6.0F));
	public static final Block PLASTERED_STONE_EDGE = reg("plastered_stone_edge", new EdgeBlock(PLASTERED_STONE));
	public static final Block PLASTERED_STONE_FRIEZE = reg("plastered_stone_frieze", new PlateBlock(PLASTERED_STONE));
	public static final Block PLASTERED_STONE_PLATE = reg("plastered_stone_plate", new PlateBlock(PLASTERED_STONE));
	public static final Block PLASTERED_STONE_SLAB = reg("plastered_stone_slab", new SlabBlockDoTB(PLASTERED_STONE));
	public static final Block PLASTERED_STONE_STAIRS = reg("plastered_stone_stairs", new StairsBlockDoTB(PLASTERED_STONE));
	public static final Block PLASTERED_STONE_WINDOW = reg("plastered_stone_window", new PlasteredStoneWindowBlock());
	public static final Block CHISELED_PLASTERED_STONE = reg("chiseled_plastered_stone", new BlockDoTB(Material.ROCK,1.5F, 6.0F));
	public static final Block ORNAMENTED_CHISELED_PLASTERED_STONE = reg("ornamented_chiseled_plastered_stone", new BlockDoTB(Material.ROCK,1.5F, 6.0F));
	public static final Block RED_PLASTERED_STONE = reg("red_plastered_stone", new BlockDoTB(Material.ROCK,1.5F, 6.0F));
	public static final Block RED_CHISELED_PLASTERED_STONE = reg("red_chiseled_plastered_stone", new BlockDoTB(Material.ROCK,1.5F, 6.0F));
	public static final Block RED_ORNAMENTED_CHISELED_PLASTERED_STONE = reg("red_ornamented_chiseled_plastered_stone", new BlockDoTB(Material.ROCK,1.5F, 6.0F));
	public static final Block RED_PLASTERED_STONE_EDGE = reg("red_plastered_stone_edge", new EdgeBlock(RED_PLASTERED_STONE));
	public static final Block RED_PLASTERED_STONE_FRIEZE = reg("red_plastered_stone_frieze", new PlateBlock(RED_PLASTERED_STONE));
	public static final Block RED_PLASTERED_STONE_PLATE = reg("red_plastered_stone_plate", new PlateBlock(RED_PLASTERED_STONE));
	public static final Block RED_PLASTERED_STONE_SLAB = reg("red_plastered_stone_slab", new SlabBlockDoTB(RED_PLASTERED_STONE));
	public static final Block RED_PLASTERED_STONE_STAIRS = reg("red_plastered_stone_stairs", new StairsBlockDoTB(RED_PLASTERED_STONE));
	public static final Block RED_SMALL_PLASTERED_STONE_FRIEZE = reg("red_small_plastered_stone_frieze", new EdgeBlock(RED_PLASTERED_STONE));
	public static final Block RED_ORNAMENTED_PLASTERED_STONE_FRIEZE = reg("red_ornamented_plastered_stone_frieze", new PlateBlock(RED_PLASTERED_STONE));
	public static final Block RED_SCULPTED_PLASTERED_STONE_FRIEZE = reg("red_sculpted_plastered_stone_frieze", new RedSculptedPlasteredStoneFriezeBlock());
	public static final Block GREEN_CHISELED_PLASTERED_STONE = reg("green_chiseled_plastered_stone", new BlockDoTB(Material.ROCK,1.5F, 6.0F));
	public static final Block GREEN_ORNAMENTED_CHISELED_PLASTERED_STONE = reg("green_ornamented_chiseled_plastered_stone", new BlockDoTB(Material.ROCK,1.5F, 6.0F));
	public static final Block GREEN_ORNAMENTED_PLASTERED_STONE_FRIEZE = reg("green_ornamented_plastered_stone_frieze", new PlateBlock(PLASTERED_STONE));
	public static final Block GREEN_PLASTERED_STONE_FRIEZE = reg("green_plastered_stone_frieze", new PlateBlock(PLASTERED_STONE));
	public static final Block GREEN_SCULPTED_PLASTERED_STONE_FRIEZE = reg("green_sculpted_plastered_stone_frieze", new GreenSculptedPlasteredStoneFriezeBlock());
	public static final Block GREEN_SMALL_PLASTERED_STONE_FRIEZE = reg("green_small_plastered_stone_frieze", new EdgeBlock(PLASTERED_STONE));
	public static final Block MAIZE = reg("maize", new DoubleCropsBlock("maize", Crop, 4));
	public static final Block RED_ORNAMENTED_PLASTERED_STONE = reg("red_ornamented_plastered_stone", new BlockDoTB(Material.ROCK,1.5F, 6.0F));
	public static final Block PLASTERED_STONE_COLUMN = reg("plastered_stone_column", new PlasteredStoneColumnBlock());
	public static final Block PLASTERED_STONE_CRESSET = reg("plastered_stone_cresset", new PlasteredStoneCressetBlock());
	public static final Block FEATHERED_SERPENT_SCULPTURE = reg("feathered_serpent_sculpture", new FeatheredSerpentSculptureBlock());
	public static final Block SERPENT_SCULPTED_COLUMN = reg("serpent_sculpted_column", new SerpentSculptedColumnBlock());

	//Roman
	public static final Block OCHRE_ROOF_TILES = reg("ochre_roof_tiles", new BlockDoTB(Material.ROCK, 1.5F, 5.0F));
	public static final Block OCHRE_ROOF_TILES_EDGE = reg("ochre_roof_tiles_edge", new EdgeBlock(Material.ROCK, 2.0F, 3.0F));
	public static final Block OCHRE_ROOF_TILES_PLATE = reg("ochre_roof_tiles_plate", new PlateBlock(Material.ROCK, 2.0F, 3.0F));
	public static final Block OCHRE_ROOF_TILES_STAIRS = reg("ochre_roof_tiles_stairs", new StairsBlockDoTB(OCHRE_ROOF_TILES));
	public static final Block OCHRE_ROOF_TILES_WALL = reg("ochre_roof_tiles_wall", new WallBlockDoTB(Material.ROCK, 1.5F, 6.0F));
	public static final Block SANDSTONE_BOT_OCHRE_ROOF_TILES_TOP = reg("sandstone_bot_ochre_roof_tiles_top", new NoItemBlock(Material.ROCK, 0.8F, 0.8F));
	public static final Block OCHRE_ROOF_TILES_SLAB = reg("ochre_roof_tiles_slab", new MixedSlabBlock(OCHRE_ROOF_TILES).addMixedBlockRecipe((SlabBlock) SANDSTONE_SLAB, SANDSTONE_BOT_OCHRE_ROOF_TILES_TOP, false));
	public static final Block SANDSTONE_COLUMN = reg("sandstone_column", new SandstoneColumnBlock());
	public static final Block CYPRESS_LEAVES = reg("cypress_leaves", new CypressLeavesBlock().setBurnable());
	//TODO Add cypress log or sticks

	private static Block reg(String name, Block block){
		block = block.setRegistryName(MOD_ID, name);
		BLOCKS.add(block);

		Item item = null;
		if(block instanceof IBlockCustomItem) item = ((IBlockCustomItem)block).getCustomItemBlock();
		else if(block.getRegistryName() != null) item = new BlockItem(block, new Item.Properties().group(DOTB_TAB)).setRegistryName(block.getRegistryName());

		if(item != null) DoTBItemsRegistry.ITEMS.add(item);

		return block;
	}
}