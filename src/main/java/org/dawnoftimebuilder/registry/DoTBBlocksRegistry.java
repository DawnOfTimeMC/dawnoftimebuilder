package org.dawnoftimebuilder.registry;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.dawnoftimebuilder.DoTBConfig;
import org.dawnoftimebuilder.block.IBlockCustomItem;
import org.dawnoftimebuilder.block.french.LimestoneChimneyBlock;
import org.dawnoftimebuilder.block.french.LimestoneGargoyleBlock;
import org.dawnoftimebuilder.block.precolumbian.*;
import org.dawnoftimebuilder.block.roman.*;
import org.dawnoftimebuilder.block.templates.*;
import org.dawnoftimebuilder.block.german.*;
import org.dawnoftimebuilder.block.general.*;
import org.dawnoftimebuilder.block.japanese.*;
import org.dawnoftimebuilder.util.DoTBFoods;

import java.util.function.ToIntFunction;

import static net.minecraft.block.Blocks.*;
import static net.minecraftforge.common.PlantType.CROP;
import static net.minecraftforge.common.PlantType.PLAINS;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class DoTBBlocksRegistry {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);

	//General
    public static final RegistryObject<Block> ACACIA_PLANKS_EDGE = reg("acacia_planks_edge", new EdgeBlock(AbstractBlock.Properties.copy(ACACIA_PLANKS)).setBurnable());
	public static final RegistryObject<Block> ACACIA_PLANKS_PLATE = reg("acacia_planks_plate", new PlateBlock(AbstractBlock.Properties.copy(ACACIA_PLANKS)).setBurnable());
	public static final RegistryObject<Block> ACACIA_PERGOLA = reg("acacia_pergola", new PergolaBlock(AbstractBlock.Properties.copy(ACACIA_PLANKS)).setBurnable());
	public static final RegistryObject<Block> ACACIA_LATTICE = reg("acacia_lattice", new LatticeBlock(AbstractBlock.Properties.copy(ACACIA_PLANKS).noOcclusion()).setBurnable());
	public static final RegistryObject<Block> ACACIA_BEAM = reg("acacia_beam", new BeamBlock(AbstractBlock.Properties.copy(ACACIA_PLANKS)).setBurnable());
	public static final RegistryObject<Block> ACACIA_WALL = reg("acacia_wall", new WallBlock(AbstractBlock.Properties.copy(ACACIA_PLANKS)));
	public static final RegistryObject<Block> ACACIA_SUPPORT_BEAM = reg("acacia_support_beam", new SupportBeamBlock(AbstractBlock.Properties.copy(ACACIA_PLANKS)).setBurnable());
	public static final RegistryObject<Block> ACACIA_SUPPORT_SLAB = reg("acacia_support_slab", new SupportSlabBlock(AbstractBlock.Properties.copy(ACACIA_PLANKS)).setBurnable());
	public static final RegistryObject<Block> BIRCH_PLANKS_EDGE = reg("birch_planks_edge", new EdgeBlock(AbstractBlock.Properties.copy(BIRCH_PLANKS)).setBurnable());
	public static final RegistryObject<Block> BIRCH_PLANKS_PLATE = reg("birch_planks_plate", new PlateBlock(AbstractBlock.Properties.copy(BIRCH_PLANKS)).setBurnable());
	public static final RegistryObject<Block> BIRCH_PERGOLA = reg("birch_pergola", new PergolaBlock(AbstractBlock.Properties.copy(BIRCH_PLANKS)).setBurnable());
	public static final RegistryObject<Block> BIRCH_LATTICE = reg("birch_lattice", new LatticeBlock(AbstractBlock.Properties.copy(BIRCH_PLANKS).noOcclusion()).setBurnable());
	public static final RegistryObject<Block> BIRCH_BEAM = reg("birch_beam", new BeamBlock(AbstractBlock.Properties.copy(BIRCH_PLANKS)).setBurnable());
	public static final RegistryObject<Block> BIRCH_WALL = reg("birch_wall", new WallBlock(AbstractBlock.Properties.copy(BIRCH_PLANKS)));
	public static final RegistryObject<Block> BIRCH_SUPPORT_BEAM = reg("birch_support_beam", new SupportBeamBlock(AbstractBlock.Properties.copy(BIRCH_PLANKS)).setBurnable());
	public static final RegistryObject<Block> BIRCH_SUPPORT_SLAB = reg("birch_support_slab", new SupportSlabBlock(AbstractBlock.Properties.copy(BIRCH_PLANKS)).setBurnable());
	public static final RegistryObject<Block> CANDLESTICK = reg("candlestick", new CandlestickBlock(AbstractBlock.Properties.copy(CAULDRON).lightLevel(litBlockEmission(10))));
	public static final RegistryObject<Block> DARK_OAK_PLANKS_EDGE = reg("dark_oak_planks_edge", new EdgeBlock(AbstractBlock.Properties.copy(DARK_OAK_PLANKS)).setBurnable());
	public static final RegistryObject<Block> DARK_OAK_PLANKS_PLATE = reg("dark_oak_planks_plate", new PlateBlock(AbstractBlock.Properties.copy(DARK_OAK_PLANKS)).setBurnable());
	public static final RegistryObject<Block> DARK_OAK_PERGOLA = reg("dark_oak_pergola", new PergolaBlock(AbstractBlock.Properties.copy(DARK_OAK_PLANKS)).setBurnable());
	public static final RegistryObject<Block> DARK_OAK_LATTICE = reg("dark_oak_lattice", new LatticeBlock(AbstractBlock.Properties.copy(DARK_OAK_PLANKS).noOcclusion()).setBurnable());
	public static final RegistryObject<Block> DARK_OAK_BEAM = reg("dark_oak_beam", new BeamBlock(AbstractBlock.Properties.copy(DARK_OAK_PLANKS)).setBurnable());
	public static final RegistryObject<Block> DARK_OAK_WALL = reg("dark_oak_wall", new WallBlock(AbstractBlock.Properties.copy(DARK_OAK_PLANKS)));
	public static final RegistryObject<Block> DARK_OAK_SUPPORT_BEAM = reg("dark_oak_support_beam", new SupportBeamBlock(AbstractBlock.Properties.copy(DARK_OAK_PLANKS)).setBurnable());
	public static final RegistryObject<Block> DARK_OAK_SUPPORT_SLAB = reg("dark_oak_support_slab", new SupportSlabBlock(AbstractBlock.Properties.copy(DARK_OAK_PLANKS)).setBurnable());
	public static final RegistryObject<Block> JUNGLE_PLANKS_EDGE = reg("jungle_planks_edge", new EdgeBlock(AbstractBlock.Properties.copy(JUNGLE_PLANKS)).setBurnable());
	public static final RegistryObject<Block> JUNGLE_PLANKS_PLATE = reg("jungle_planks_plate", new PlateBlock(AbstractBlock.Properties.copy(JUNGLE_PLANKS)).setBurnable());
	public static final RegistryObject<Block> JUNGLE_PERGOLA = reg("jungle_pergola", new PergolaBlock(AbstractBlock.Properties.copy(JUNGLE_PLANKS)).setBurnable());
	public static final RegistryObject<Block> JUNGLE_LATTICE = reg("jungle_lattice", new LatticeBlock(AbstractBlock.Properties.copy(JUNGLE_PLANKS).noOcclusion()).setBurnable());
	public static final RegistryObject<Block> JUNGLE_BEAM = reg("jungle_beam", new BeamBlock(AbstractBlock.Properties.copy(JUNGLE_PLANKS)).setBurnable());
	public static final RegistryObject<Block> JUNGLE_WALL = reg("jungle_wall", new WallBlock(AbstractBlock.Properties.copy(JUNGLE_PLANKS)));
	public static final RegistryObject<Block> JUNGLE_SUPPORT_BEAM = reg("jungle_support_beam", new SupportBeamBlock(AbstractBlock.Properties.copy(JUNGLE_PLANKS)).setBurnable());
	public static final RegistryObject<Block> JUNGLE_SUPPORT_SLAB = reg("jungle_support_slab", new SupportSlabBlock(AbstractBlock.Properties.copy(JUNGLE_PLANKS)).setBurnable());
	public static final RegistryObject<Block> OAK_PLANKS_PLATE = reg("oak_planks_plate", new PlateBlock(AbstractBlock.Properties.copy(OAK_PLANKS)).setBurnable());
    public static final RegistryObject<Block> OAK_PLANKS_EDGE = reg("oak_planks_edge", new EdgeBlock(AbstractBlock.Properties.copy(OAK_PLANKS)).setBurnable());
	public static final RegistryObject<Block> OAK_PERGOLA = reg("oak_pergola", new PergolaBlock(AbstractBlock.Properties.copy(OAK_PLANKS)).setBurnable());
	public static final RegistryObject<Block> OAK_LATTICE = reg("oak_lattice", new LatticeBlock(AbstractBlock.Properties.copy(OAK_PLANKS).noOcclusion()).setBurnable());
	public static final RegistryObject<Block> OAK_BEAM = reg("oak_beam", new BeamBlock(AbstractBlock.Properties.copy(OAK_PLANKS)).setBurnable());
	public static final RegistryObject<Block> OAK_WALL = reg("oak_wall", new WallBlock(AbstractBlock.Properties.copy(OAK_PLANKS)));
	public static final RegistryObject<Block> OAK_SUPPORT_BEAM = reg("oak_support_beam", new SupportBeamBlock(AbstractBlock.Properties.copy(OAK_PLANKS)).setBurnable());
	public static final RegistryObject<Block> OAK_SUPPORT_SLAB = reg("oak_support_slab", new SupportSlabBlock(AbstractBlock.Properties.copy(OAK_PLANKS)).setBurnable());
	public static final RegistryObject<Block> RAMMED_DIRT = reg("rammed_dirt", new BlockDoTB(AbstractBlock.Properties.copy(TERRACOTTA)));
	public static final RegistryObject<Block> SPRUCE_PLANKS_EDGE = reg("spruce_planks_edge", new EdgeBlock(AbstractBlock.Properties.copy(SPRUCE_PLANKS)).setBurnable());
	public static final RegistryObject<Block> SPRUCE_PLANKS_PLATE = reg("spruce_planks_plate", new PlateBlock(AbstractBlock.Properties.copy(SPRUCE_PLANKS)).setBurnable());
	public static final RegistryObject<Block> SPRUCE_PERGOLA = reg("spruce_pergola", new PergolaBlock(AbstractBlock.Properties.copy(SPRUCE_PLANKS)).setBurnable());
	public static final RegistryObject<Block> SPRUCE_LATTICE = reg("spruce_lattice", new LatticeBlock(AbstractBlock.Properties.copy(SPRUCE_PLANKS).noOcclusion()).setBurnable());
	public static final RegistryObject<Block> SPRUCE_BEAM = reg("spruce_beam", new BeamBlock(AbstractBlock.Properties.copy(SPRUCE_PLANKS)).setBurnable());
	public static final RegistryObject<Block> SPRUCE_WALL = reg("spruce_wall", new WallBlock(AbstractBlock.Properties.copy(SPRUCE_PLANKS)));
	public static final RegistryObject<Block> SPRUCE_SUPPORT_BEAM = reg("spruce_support_beam", new SupportBeamBlock(AbstractBlock.Properties.copy(SPRUCE_PLANKS)).setBurnable());
	public static final RegistryObject<Block> SPRUCE_SUPPORT_SLAB = reg("spruce_support_slab", new SupportSlabBlock(AbstractBlock.Properties.copy(SPRUCE_PLANKS)).setBurnable());
	public static final RegistryObject<Block> THATCH_WHEAT = reg("thatch_wheat", new BlockDoTB(AbstractBlock.Properties.of(Material.GRASS, MaterialColor.COLOR_YELLOW).strength(1.0F).sound(SoundType.GRASS)).setBurnable(80, 30));
	public static final RegistryObject<Block> THATCH_WHEAT_EDGE = reg("thatch_wheat_edge", new EdgeBlock(AbstractBlock.Properties.of(Material.GRASS, MaterialColor.COLOR_YELLOW).strength(1.0F).sound(SoundType.GRASS)).setBurnable(80, 30));
	public static final RegistryObject<Block> THATCH_WHEAT_PLATE = reg("thatch_wheat_plate", new PlateBlock(AbstractBlock.Properties.of(Material.GRASS, MaterialColor.COLOR_YELLOW).strength(1.0F).sound(SoundType.GRASS)).setBurnable(80, 30));
	public static final RegistryObject<Block> THATCH_WHEAT_SLAB = reg("thatch_wheat_slab", new SlabBlockDoTB(AbstractBlock.Properties.of(Material.GRASS, MaterialColor.COLOR_YELLOW).strength(1.0F).sound(SoundType.GRASS)).setBurnable(80, 30));
	public static final RegistryObject<Block> THATCH_WHEAT_STAIRS = reg("thatch_wheat_stairs", new StairsBlockDoTB(THATCH_WHEAT, AbstractBlock.Properties.of(Material.GRASS, MaterialColor.COLOR_YELLOW).strength(1.0F).sound(SoundType.GRASS)).setBurnable(80, 30));
	public static final RegistryObject<Block> THATCH_BAMBOO = reg("thatch_bamboo", new BlockDoTB(AbstractBlock.Properties.of(Material.GRASS, MaterialColor.COLOR_YELLOW).strength(1.0F).sound(SoundType.GRASS)).setBurnable(40, 30));
	public static final RegistryObject<Block> THATCH_BAMBOO_EDGE = reg("thatch_bamboo_edge", new EdgeBlock(AbstractBlock.Properties.of(Material.GRASS, MaterialColor.COLOR_YELLOW).strength(1.0F).sound(SoundType.GRASS)).setBurnable(40, 30));
	public static final RegistryObject<Block> THATCH_BAMBOO_PLATE = reg("thatch_bamboo_plate", new PlateBlock(AbstractBlock.Properties.of(Material.GRASS, MaterialColor.COLOR_YELLOW).strength(1.0F).sound(SoundType.GRASS)).setBurnable(40, 30));
	public static final RegistryObject<Block> THATCH_BAMBOO_SLAB = reg("thatch_bamboo_slab", new SlabBlockDoTB(AbstractBlock.Properties.of(Material.GRASS, MaterialColor.COLOR_YELLOW).strength(1.0F).sound(SoundType.GRASS)).setBurnable(40, 30));
	public static final RegistryObject<Block> THATCH_BAMBOO_STAIRS = reg("thatch_bamboo_stairs", new StairsBlockDoTB(THATCH_BAMBOO, AbstractBlock.Properties.of(Material.GRASS, MaterialColor.COLOR_YELLOW).strength(1.0F).sound(SoundType.GRASS)).setBurnable(40, 30));
	public static final RegistryObject<Block> FIREPLACE = reg("fireplace", new FireplaceBlock(AbstractBlock.Properties.of(Material.STONE).strength(1.5F, 6.0F).lightLevel(litBlockEmission(15))));
	public static final RegistryObject<Block> IRON_PORTCULLIS = reg("iron_portcullis", new PortcullisBlock(AbstractBlock.Properties.copy(IRON_DOOR)));

	//French
	public static final RegistryObject<Block> COBBLED_LIMESTONE = reg("cobbled_limestone", new BlockDoTB(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> LIMESTONE_BRICKS = reg("limestone_bricks", new BlockDoTB(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> LIMESTONE_BRICKS_EDGE = reg("limestone_bricks_edge", new EdgeBlock(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> LIMESTONE_BRICKS_PLATE = reg("limestone_bricks_plate", new PlateBlock(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> LIMESTONE_BRICKS_SLAB = reg("limestone_bricks_slab", new SlabBlockDoTB(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> LIMESTONE_BRICKS_STAIRS = reg("limestone_bricks_stairs", new StairsBlockDoTB(LIMESTONE_BRICKS, AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> LIMESTONE_BRICKS_WALL = reg("limestone_bricks_wall", new WallBlock(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> LIMESTONE_BALUSTER = reg("limestone_baluster", new PlateBlock(AbstractBlock.Properties.copy(STONE_BRICKS).noOcclusion()));
	public static final RegistryObject<Block> LIMESTONE_GARGOYLE = reg("limestone_gargoyle", new LimestoneGargoyleBlock(AbstractBlock.Properties.copy(STONE_BRICKS).noOcclusion().randomTicks()));
	public static final RegistryObject<Block> LIMESTONE_CHIMNEY = reg("limestone_chimney", new LimestoneChimneyBlock(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> LIMESTONE_FIREPLACE = reg("limestone_fireplace", new MultiblockFireplaceBlock(AbstractBlock.Properties.copy(STONE_BRICKS).noOcclusion().lightLevel(litBlockEmission(15))));
	public static final RegistryObject<Block> ROOFING_SLATES = reg("roofing_slates", new BlockDoTB(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> ROOFING_SLATES_STAIRS = reg("roofing_slates_stairs", new StairsBlockDoTB(ROOFING_SLATES, AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> ROOFING_SLATES_PLATE = reg("roofing_slates_plate", new PlateBlock(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> ROOFING_SLATES_SLAB = reg("roofing_slates_slab", new SlabBlockDoTB(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> ROOFING_SLATES_EDGE = reg("roofing_slates_edge", new EdgeBlock(AbstractBlock.Properties.copy(STONE_BRICKS)));

	//German
	public static final RegistryObject<Block> FLAT_ROOF_TILES = reg("flat_roof_tiles", new BlockDoTB(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> FLAT_ROOF_TILES_STAIRS = reg("flat_roof_tiles_stairs", new StairsBlockDoTB(FLAT_ROOF_TILES, AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> FLAT_ROOF_TILES_PLATE = reg("flat_roof_tiles_plate", new PlateBlock(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> FLAT_ROOF_TILES_SLAB = reg("flat_roof_tiles_slab", new SlabBlockDoTB(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> FLAT_ROOF_TILES_EDGE = reg("flat_roof_tiles_edge", new EdgeBlock(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> LATTICE_GLASS = reg("lattice_glass", new GlassBlock(AbstractBlock.Properties.copy(GLASS)));
	public static final RegistryObject<Block> LATTICE_GLASS_PANE = reg("lattice_glass_pane", new PaneBlockDoTB(AbstractBlock.Properties.copy(GLASS)));
	public static final RegistryObject<Block> LATTICE_WAXED_OAK_WINDOW = reg("lattice_waxed_oak_window", new SidedWindowBlock(AbstractBlock.Properties.copy(GLASS)));
	public static final RegistryObject<Block> LATTICE_STONE_BRICKS_WINDOW = reg("lattice_stone_bricks_window", new LatticeStoneBricksWindowBlock(AbstractBlock.Properties.copy(GLASS)));
	public static final RegistryObject<Block> STONE_BRICKS_ARROWSLIT = reg("stone_bricks_arrowslit", new StoneBricksArrowslitBlock(AbstractBlock.Properties.copy(STONE_BRICKS).noOcclusion()));
	public static final RegistryObject<Block> STONE_BRICKS_CHIMNEY = reg("stone_bricks_chimney", new StoneBricksChimneyBlock(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> STONE_BRICKS_EDGE = reg("stone_bricks_edge", new EdgeBlock(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> STONE_BRICKS_FIREPLACE = reg("stone_bricks_fireplace", new MultiblockFireplaceBlock(AbstractBlock.Properties.copy(STONE_BRICKS).noOcclusion().lightLevel(litBlockEmission(15))));
	public static final RegistryObject<Block> STONE_BRICKS_MACHICOLATION = reg("stone_bricks_machicolation", new StoneBricksMachicolationBlock(AbstractBlock.Properties.copy(STONE_BRICKS).noOcclusion()));
	public static final RegistryObject<Block> STONE_BRICKS_PLATE = reg("stone_bricks_plate", new PlateBlock(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> STONE_BRICKS_MASONRY = reg("stone_bricks_masonry", new BlockDoTB(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> WAXED_OAK_FRAMED_RAMMED_DIRT = reg("waxed_oak_framed_rammed_dirt", new BlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> WAXED_OAK_PLANKS = reg("waxed_oak_planks", new BlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block> WAXED_OAK_DOOR = reg("waxed_oak_door", new DoorBlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD).noOcclusion()));
	public static final RegistryObject<Block> WAXED_OAK_TRAPDOOR = reg("waxed_oak_trapdoor", new TrapDoorBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD).noOcclusion()));
	public static final RegistryObject<Block> WAXED_OAK_SHUTTERS = reg("waxed_oak_shutters", new ShuttersBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> WAXED_OAK_LOG_STRIPPED = reg("waxed_oak_log_stripped", new RotatedPillarBlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block> WAXED_OAK_BALUSTER = reg("waxed_oak_baluster", new WaxedOakBalusterBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD).noOcclusion()));
	public static final RegistryObject<Block> WAXED_OAK_BEAM = reg("waxed_oak_beam", new BeamBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block> WAXED_OAK_SUPPORT_BEAM = reg("waxed_oak_support_beam", new SupportBeamBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block> WAXED_OAK_SUPPORT_SLAB = reg("waxed_oak_support_slab", new SupportSlabBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block> WAXED_OAK_SMALL_SHUTTERS = reg("waxed_oak_small_shutters", new SmallShuttersBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block> WAXED_OAK_TIMBER_FRAME = reg("waxed_oak_timber_frame", new BlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block> WAXED_OAK_TIMBER_FRAME_CORNER = reg("waxed_oak_timber_frame_corner", new WaxedOakTimberFrameCornerBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block> WAXED_OAK_TIMBER_FRAME_CROSSED = reg("waxed_oak_timber_frame_crossed", new BlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block> WAXED_OAK_TIMBER_FRAME_PILLAR = reg("waxed_oak_timber_frame_pillar", new RotatedPillarBlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block> WAXED_OAK_TIMBER_FRAME_SQUARED = reg("waxed_oak_timber_frame_squared", new BlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block> WAXED_OAK_FENCE = reg("waxed_oak_fence", new FenceBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> WAXED_OAK_WALL = reg("waxed_oak_wall", new WallBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> WAXED_OAK_PERGOLA = reg("waxed_oak_pergola", new PergolaBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block> WAXED_OAK_LATTICE = reg("waxed_oak_lattice", new LatticeBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD).noOcclusion()).setBurnable());
	public static final RegistryObject<Block> WAXED_OAK_PLANKS_EDGE = reg("waxed_oak_planks_edge", new EdgeBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block> WAXED_OAK_PLANKS_PLATE = reg("waxed_oak_planks_plate", new PlateBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block> WAXED_OAK_PLANKS_SLAB = reg("waxed_oak_planks_slab", new SlabBlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block> WAXED_OAK_PLANKS_STAIRS = reg("waxed_oak_planks_stairs", new StairsBlockDoTB(WAXED_OAK_PLANKS, AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block> WAXED_OAK_CHANDELIER = reg("waxed_oak_chandelier", new WaxedOakChandelierBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD).noOcclusion().lightLevel(litBlockEmission(15))));
	public static final RegistryObject<Block> WAXED_OAK_CHAIR = reg("waxed_oak_chair", new WaxedOakChairBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD).noOcclusion(), 11.0F));
	public static final RegistryObject<Block> PLANTER_GERANIUM_PINK = reg("planter_geranium_pink", new PlanterBlock(AbstractBlock.Properties.of(Material.CLAY).strength(0.6F).noOcclusion()));

	//Japanese
	public static final RegistryObject<Block> CHARRED_SPRUCE_PLANKS = reg("charred_spruce_planks", new BlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)).setBurnable(2, 3));
	public static final RegistryObject<Block> CHARRED_SPRUCE_LOG_STRIPPED = reg("charred_spruce_log_stripped", new RotatedPillarBlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)).setBurnable(2, 3));
	public static final RegistryObject<Block> CHARRED_SPRUCE_PLANKS_EDGE = reg("charred_spruce_planks_edge", new EdgeBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)).setBurnable(2, 3));
	public static final RegistryObject<Block> CHARRED_SPRUCE_PLANKS_PLATE = reg("charred_spruce_planks_plate", new PlateBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)).setBurnable(2, 3));
	public static final RegistryObject<Block> CHARRED_SPRUCE_PLANKS_SLAB = reg("charred_spruce_planks_slab", new SlabBlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)).setBurnable(2, 3));
	public static final RegistryObject<Block> CHARRED_SPRUCE_PLANKS_STAIRS = reg("charred_spruce_planks_stairs", new StairsBlockDoTB(CHARRED_SPRUCE_PLANKS, AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)).setBurnable(2, 3));
	public static final RegistryObject<Block> CHARRED_SPRUCE_BOARDS =reg("charred_spruce_boards", new BlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)).setBurnable(2, 3));
	public static final RegistryObject<Block> CHARRED_SPRUCE_DOOR = reg("charred_spruce_door", new DoorBlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD).noOcclusion()));
	public static final RegistryObject<Block> CHARRED_SPRUCE_TRAPDOOR = reg("charred_spruce_trapdoor", new TrapDoorBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD).noOcclusion()));
	public static final RegistryObject<Block> CHARRED_SPRUCE_SHUTTERS = reg("charred_spruce_shutters", new CharredSpruceShuttersBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD).noOcclusion()));
	public static final RegistryObject<Block> CHARRED_SPRUCE_TALL_SHUTTERS = reg("charred_spruce_tall_shutters", new CharredSpruceTallShuttersBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD).noOcclusion()));
	public static final RegistryObject<Block> CHARRED_SPRUCE_FOUNDATION = reg("charred_spruce_foundation", new BlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)).setBurnable(2, 3));
	public static final RegistryObject<Block> CHARRED_SPRUCE_FOUNDATION_SLAB = reg("charred_spruce_foundation_slab", new SlabBlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)).setBurnable(2, 3));
	public static final RegistryObject<Block> CHARRED_SPRUCE_WALL = reg("charred_spruce_wall", new WallBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> CHARRED_SPRUCE_FENCE = reg("charred_spruce_fence", new FenceBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> CHARRED_SPRUCE_RAILING = reg("charred_spruce_railing", new CharredSpruceRailingBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD).noOcclusion()));
	public static final RegistryObject<Block> CHARRED_SPRUCE_PERGOLA = reg("charred_spruce_pergola", new PergolaBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)).setBurnable(2, 3));
	public static final RegistryObject<Block> CHARRED_SPRUCE_LATTICE = reg("charred_spruce_lattice", new LatticeBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD).noOcclusion()).setBurnable(2, 3));
	public static final RegistryObject<Block> CHARRED_SPRUCE_SUPPORT_SLAB = reg("charred_spruce_support_slab", new SupportSlabBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)).setBurnable(2, 3));
	public static final RegistryObject<Block> CHARRED_SPRUCE_SUPPORT_BEAM = reg("charred_spruce_support_beam", new SupportBeamBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)).setBurnable(2, 3));
	public static final RegistryObject<Block> CHARRED_SPRUCE_BEAM = reg("charred_spruce_beam", new BeamBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)).setBurnable(2, 3));
	public static final RegistryObject<Block> CHARRED_SPRUCE_TIMBER_FRAME = reg("charred_spruce_timber_frame", new BlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block> CHARRED_SPRUCE_TIMBER_FRAME_PILLAR = reg("charred_spruce_timber_frame_pillar", new RotatedPillarBlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block> RED_PAINTED_BEAM = reg("red_painted_beam", new BeamBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)).setBurnable(2, 3));
	public static final RegistryObject<Block> GRAY_ROOF_TILES = reg("gray_roof_tiles", new BlockDoTB(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> GRAY_ROOF_TILES_STAIRS = reg("gray_roof_tiles_stairs", new StairsBlockDoTB(GRAY_ROOF_TILES, AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> GRAY_ROOF_TILES_PLATE = reg("gray_roof_tiles_plate", new PlateBlock(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> GRAY_ROOF_TILES_SLAB = reg("gray_roof_tiles_slab", new SlabBlockDoTB(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> GRAY_ROOF_TILES_EDGE = reg("gray_roof_tiles_edge", new EdgeBlock(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> GRAY_ROOF_TILES_WALL = reg("gray_roof_tiles_wall", new WallBlock(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> CHARRED_SPRUCE_ROOF_SUPPORT = reg("charred_spruce_roof_support", new MixedRoofSupportBlock(GRAY_ROOF_TILES_SLAB, AbstractBlock.Properties.copy(STONE_BRICKS).noOcclusion()));
	//TODO Redo stepping stone's textures
	public static final RegistryObject<Block> STEPPING_STONES = reg("stepping_stones", new BlockDoTB(Block.Properties.of(Material.SAND, MaterialColor.STONE).strength(1.2F).sound(SoundType.GRAVEL)));
	public static final RegistryObject<Block> STEPPING_STONES_SLAB = reg("stepping_stones_slab", new SlabBlockDoTB(AbstractBlock.Properties.of(Material.SAND, MaterialColor.STONE).strength(1.2F).sound(SoundType.GRAVEL)));
	public static final RegistryObject<Block> CAST_IRON_TEAPOT_GRAY = reg("cast_iron_teapot_gray", new CastIronTeapotBlock(AbstractBlock.Properties.of(Material.METAL).strength(1.0F).sound(SoundType.METAL).noOcclusion()));
	public static final RegistryObject<Block> CAST_IRON_TEAPOT_GREEN = reg("cast_iron_teapot_green", new CastIronTeapotBlock(AbstractBlock.Properties.of(Material.METAL).strength(1.0F).sound(SoundType.METAL).noOcclusion()));
	public static final RegistryObject<Block> CAST_IRON_TEAPOT_DECORATED = reg("cast_iron_teapot_decorated", new CastIronTeapotBlock(AbstractBlock.Properties.of(Material.METAL).strength(1.0F).sound(SoundType.METAL).noOcclusion()));
	public static final RegistryObject<Block> CAST_IRON_TEACUP_GRAY = reg("cast_iron_teacup_gray", new CastIronTeacupBlock(AbstractBlock.Properties.of(Material.METAL).strength(1.0F).sound(SoundType.METAL).noOcclusion()));
	public static final RegistryObject<Block> CAST_IRON_TEACUP_GREEN = reg("cast_iron_teacup_green", new CastIronTeacupBlock(AbstractBlock.Properties.of(Material.METAL).strength(1.0F).sound(SoundType.METAL).noOcclusion()));
	public static final RegistryObject<Block> CAST_IRON_TEACUP_DECORATED = reg("cast_iron_teacup_decorated", new CastIronTeacupBlock(AbstractBlock.Properties.of(Material.METAL).strength(1.0F).sound(SoundType.METAL).noOcclusion()));
	public static final RegistryObject<Block> BAMBOO_DRYING_TRAY = reg("bamboo_drying_tray", new DryerBlock(AbstractBlock.Properties.copy(OAK_PLANKS).noOcclusion()));
	public static final RegistryObject<Block> CAMELLIA = reg("camellia", new GrowingBushBlock("camellia_seeds", PLAINS, 3));
	public static final RegistryObject<Block> MULBERRY = reg("mulberry", new MulberryBlock("mulberry", PLAINS, 3, 2, DoTBFoods.MULBERRY));
	public static final RegistryObject<Block> IKEBANA_FLOWER_POT = reg("ikebana_flower_pot", new IkebanaFlowerPotBlock(AbstractBlock.Properties.copy(FLOWER_POT)));
	public static final RegistryObject<Block> SPRUCE_LOW_TABLE = reg("spruce_low_table" ,new SpruceLowTableBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD).noOcclusion().lightLevel(litBlockEmission(14))));
	public static final RegistryObject<Block> SPRUCE_LEGLESS_CHAIR = reg("spruce_legless_chair", new SpruceLeglessChairBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD).noOcclusion(), 3.0F));
	public static final RegistryObject<Block> WHITE_LITTLE_FLAG = reg("white_little_flag", new LittleFlagBlock(AbstractBlock.Properties.copy(WHITE_WOOL)));
	public static final RegistryObject<Block> PAPER_WALL = reg("paper_wall", new PaneBlockDoTB(AbstractBlock.Properties.copy(WHITE_WOOL).strength(1.5F, 1.5F)));
	public static final RegistryObject<Block> PAPER_DOOR = reg("paper_door", new PaperDoorBlock(AbstractBlock.Properties.copy(WHITE_WOOL).strength(1.5F, 1.5F)));
	public static final RegistryObject<Block> PAPER_WALL_FLAT = reg("paper_wall_flat", new PaneBlockDoTB(AbstractBlock.Properties.copy(WHITE_WOOL).strength(1.5F, 1.5F)));
	public static final RegistryObject<Block> PAPER_WALL_WINDOWS = reg("paper_wall_window", new PaneBlockDoTB(AbstractBlock.Properties.copy(WHITE_WOOL).strength(1.5F, 1.5F)));
	public static final RegistryObject<Block> PAPER_WALL_FLOWERY = reg("paper_wall_flowery", new PaneBlockDoTB(AbstractBlock.Properties.copy(WHITE_WOOL).strength(1.5F, 1.5F)));
	public static final RegistryObject<Block> PAPER_FOLDING_SCREEN = reg("paper_folding_screen", new FoldingScreenBlock(AbstractBlock.Properties.copy(WHITE_WOOL).strength(1.5F, 1.5F).noOcclusion()));
	public static final RegistryObject<Block> RED_PAPER_LANTERN = reg("red_paper_lantern", new PaperLanternBlock(AbstractBlock.Properties.copy(RED_WOOL).noOcclusion().noCollission().lightLevel((state) -> 12)));
	public static final RegistryObject<Block> PAPER_LAMP = reg("paper_lamp", new PaperLampBlock(AbstractBlock.Properties.copy(WHITE_WOOL).noOcclusion().lightLevel((state) -> 14)));
	public static final RegistryObject<Block> STONE_LANTERN = reg("stone_lantern", new StoneLanternBlock(AbstractBlock.Properties.copy(STONE_BRICKS).noOcclusion().lightLevel((state) -> 15)));
	public static final RegistryObject<Block> RICE = reg("rice", new WaterDoubleCropsBlock("rice", 2));
	public static final RegistryObject<Block> SMALL_TATAMI_MAT = reg("small_tatami_mat", new SmallTatamiMatBlock(AbstractBlock.Properties.copy(WHITE_CARPET)));
	public static final RegistryObject<Block> SMALL_TATAMI_FLOOR = reg("small_tatami_floor", new SmallTatamiFloorBlock(AbstractBlock.Properties.copy(WHITE_CARPET)));
	public static final RegistryObject<Block> TATAMI_MAT = reg("tatami_mat", new TatamiMatBlock(AbstractBlock.Properties.copy(WHITE_CARPET)));
	public static final RegistryObject<Block> TATAMI_FLOOR = reg("tatami_floor", new TatamiFloorBlock(AbstractBlock.Properties.copy(WHITE_CARPET)));
	public static final RegistryObject<Block> LIGHT_GRAY_FUTON = reg("light_gray_futon", new FutonBlock(DyeColor.LIGHT_GRAY, AbstractBlock.Properties.copy(LIGHT_GRAY_BED)));
	public static final RegistryObject<Block> IRORI_FIREPLACE = reg("irori_fireplace", new IroriFireplaceBlock(AbstractBlock.Properties.copy(SPRUCE_PLANKS).noOcclusion().lightLevel(litBlockEmission(15))));
	public static final RegistryObject<Block> SAKE_BOTTLE = reg("sake_bottle", new SakeBottleBlock(AbstractBlock.Properties.copy(FLOWER_POT)));
	public static final RegistryObject<Block> SAKE_CUP = reg("sake_cup", new SakeCupBlock(AbstractBlock.Properties.copy(FLOWER_POT)));
	public static final RegistryObject<Block> STICK_BUNDLE = reg("stick_bundle", new StickBundleBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.GRASS).noOcclusion()).setBurnable());

	//Persian
	public static final RegistryObject<Block> PERSIAN_CARPET_RED = reg("persian_carpet_red", new CarpetBlockDoTB(AbstractBlock.Properties.copy(RED_WOOL)));
	public static final RegistryObject<Block> PERSIAN_CARPET_DELICATE_RED = reg("persian_carpet_delicate_red", new CarpetBlockDoTB(AbstractBlock.Properties.copy(RED_WOOL)));
	public static final RegistryObject<Block> MORAQ_MOSAIC_TILES_DELICATE = reg("moraq_mosaic_tiles_delicate", new BlockDoTB(AbstractBlock.Properties.copy(BRICKS)));
	public static final RegistryObject<Block> MORAQ_MOSAIC_TILES_TRADITIONAL = reg("moraq_mosaic_tiles_traditional", new BlockDoTB(AbstractBlock.Properties.copy(BRICKS)));
	public static final RegistryObject<Block> MORAQ_MOSAIC_TILES_BORDER = reg("moraq_mosaic_tiles_border", new BlockDoTB(AbstractBlock.Properties.copy(BRICKS)));

	//Pre_columbian
	public static final RegistryObject<Block> COMMELINA = reg("commelina", new SoilCropsBlock("commelina", PLAINS));
	public static final RegistryObject<Block> PLASTERED_STONE = reg("plastered_stone", new BlockDoTB(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> PLASTERED_STONE_EDGE = reg("plastered_stone_edge", new EdgeBlock(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> PLASTERED_STONE_PLATE = reg("plastered_stone_plate", new PlateBlock(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> PLASTERED_STONE_SLAB = reg("plastered_stone_slab", new SlabBlockDoTB(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> PLASTERED_STONE_STAIRS = reg("plastered_stone_stairs", new StairsBlockDoTB(PLASTERED_STONE, AbstractBlock.Properties.copy(BRICKS)));
	public static final RegistryObject<Block> PLASTERED_STONE_WINDOW = reg("plastered_stone_window", new PlasteredStoneWindowBlock(AbstractBlock.Properties.copy(STONE_BRICKS).noOcclusion()));
	public static final RegistryObject<Block> CHISELED_PLASTERED_STONE = reg("chiseled_plastered_stone", new BlockDoTB(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> CHISELED_PLASTERED_STONE_FRIEZE = reg("chiseled_plastered_stone_frieze", new PlateBlock(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> ORNAMENTED_CHISELED_PLASTERED_STONE = reg("ornamented_chiseled_plastered_stone", new BlockDoTB(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> RED_PLASTERED_STONE = reg("red_plastered_stone", new BlockDoTB(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> RED_CHISELED_PLASTERED_STONE = reg("red_chiseled_plastered_stone", new BlockDoTB(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> RED_ORNAMENTED_CHISELED_PLASTERED_STONE = reg("red_ornamented_chiseled_plastered_stone", new BlockDoTB(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> RED_PLASTERED_STONE_EDGE = reg("red_plastered_stone_edge", new EdgeBlock(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> RED_PLASTERED_STONE_FRIEZE = reg("red_plastered_stone_frieze", new PlateBlock(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> RED_PLASTERED_STONE_PLATE = reg("red_plastered_stone_plate", new PlateBlock(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> RED_PLASTERED_STONE_SLAB = reg("red_plastered_stone_slab", new SlabBlockDoTB(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> RED_PLASTERED_STONE_STAIRS = reg("red_plastered_stone_stairs", new StairsBlockDoTB(RED_PLASTERED_STONE, AbstractBlock.Properties.copy(BRICKS)));
	public static final RegistryObject<Block> RED_SMALL_PLASTERED_STONE_FRIEZE = reg("red_small_plastered_stone_frieze", new EdgeBlock(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> RED_ORNAMENTED_PLASTERED_STONE_FRIEZE = reg("red_ornamented_plastered_stone_frieze", new PlateBlock(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> RED_SCULPTED_PLASTERED_STONE_FRIEZE = reg("red_sculpted_plastered_stone_frieze", new RedSculptedPlasteredStoneFriezeBlock(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> GREEN_CHISELED_PLASTERED_STONE = reg("green_chiseled_plastered_stone", new BlockDoTB(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> GREEN_ORNAMENTED_CHISELED_PLASTERED_STONE = reg("green_ornamented_chiseled_plastered_stone", new BlockDoTB(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> GREEN_ORNAMENTED_PLASTERED_STONE_FRIEZE = reg("green_ornamented_plastered_stone_frieze", new PlateBlock(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> GREEN_PLASTERED_STONE_FRIEZE = reg("green_plastered_stone_frieze", new PlateBlock(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> GREEN_SCULPTED_PLASTERED_STONE_FRIEZE = reg("green_sculpted_plastered_stone_frieze", new GreenSculptedPlasteredStoneFriezeBlock(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> GREEN_SMALL_PLASTERED_STONE_FRIEZE = reg("green_small_plastered_stone_frieze", new EdgeBlock(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> WILD_MAIZE = reg("wild_maize", new WildMaizeBlock(AbstractBlock.Properties.copy(DANDELION)));
	public static final RegistryObject<Block> MAIZE = reg("maize", new DoubleCropsBlock("maize", CROP, 4, DoTBFoods.MAIZE));
	public static final RegistryObject<Block> RED_ORNAMENTED_PLASTERED_STONE = reg("red_ornamented_plastered_stone", new BlockDoTB(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> PLASTERED_STONE_COLUMN = reg("plastered_stone_column", new PlasteredStoneColumnBlock(AbstractBlock.Properties.copy(STONE_BRICKS)));
	public static final RegistryObject<Block> PLASTERED_STONE_CRESSET = reg("plastered_stone_cresset", new PlasteredStoneCressetBlock(AbstractBlock.Properties.copy(STONE_BRICKS).noOcclusion().lightLevel(litBlockEmission(15))));
	public static final RegistryObject<Block> FEATHERED_SERPENT_SCULPTURE = reg("feathered_serpent_sculpture", new FeatheredSerpentSculptureBlock(AbstractBlock.Properties.copy(STONE_BRICKS).noOcclusion()));
	public static final RegistryObject<Block> SERPENT_SCULPTED_COLUMN = reg("serpent_sculpted_column", new SerpentSculptedColumnBlock(AbstractBlock.Properties.copy(STONE_BRICKS)));

	//Roman
	public static final RegistryObject<Block> SANDSTONE_PLATE = reg("sandstone_plate", new PlateBlock(AbstractBlock.Properties.copy(SANDSTONE)));
	public static final RegistryObject<Block> SANDSTONE_EDGE = reg("sandstone_edge", new EdgeBlock(AbstractBlock.Properties.copy(SANDSTONE)));
	public static final RegistryObject<Block> CUT_SANDSTONE_STAIRS = reg("cut_sandstone_stairs", new StairsBlock(CUT_SANDSTONE::defaultBlockState, AbstractBlock.Properties.copy(CUT_SANDSTONE)));
	public static final RegistryObject<Block> CUT_SANDSTONE_PLATE = reg("cut_sandstone_plate", new PlateBlock(AbstractBlock.Properties.copy(CUT_SANDSTONE)));
	public static final RegistryObject<Block> CUT_SANDSTONE_EDGE = reg("cut_sandstone_edge", new EdgeBlock(AbstractBlock.Properties.copy(CUT_SANDSTONE)));
	public static final RegistryObject<Block> SMOOTH_SANDSTONE_PLATE = reg("smooth_sandstone_plate", new PlateBlock(AbstractBlock.Properties.copy(SMOOTH_SANDSTONE)));
	public static final RegistryObject<Block> SMOOTH_SANDSTONE_EDGE = reg("smooth_sandstone_edge", new EdgeBlock(AbstractBlock.Properties.copy(SMOOTH_SANDSTONE)));
	public static final RegistryObject<Block> OCHRE_ROOF_TILES = reg("ochre_roof_tiles", new BlockDoTB(AbstractBlock.Properties.copy(BRICKS)));
	public static final RegistryObject<Block> OCHRE_ROOF_TILES_EDGE = reg("ochre_roof_tiles_edge", new EdgeBlock(AbstractBlock.Properties.copy(BRICKS)));
	public static final RegistryObject<Block> OCHRE_ROOF_TILES_PLATE = reg("ochre_roof_tiles_plate", new PlateBlock(AbstractBlock.Properties.copy(BRICKS)));
	public static final RegistryObject<Block> OCHRE_ROOF_TILES_STAIRS = reg("ochre_roof_tiles_stairs", new StairsBlockDoTB(OCHRE_ROOF_TILES, AbstractBlock.Properties.copy(BRICKS)));
	public static final RegistryObject<Block> OCHRE_ROOF_TILES_WALL = reg("ochre_roof_tiles_wall", new WallBlock(AbstractBlock.Properties.copy(BRICKS)));
	public static final RegistryObject<Block> SANDSTONE_BOT_OCHRE_ROOF_TILES_TOP = reg("sandstone_bot_ochre_roof_tiles_top", new NoItemBlock(AbstractBlock.Properties.copy(BRICKS)));
	public static final RegistryObject<Block> CUT_SANDSTONE_BOT_OCHRE_ROOF_TILES_TOP = reg("cut_sandstone_bot_ochre_roof_tiles_top", new NoItemBlock(AbstractBlock.Properties.copy(BRICKS)));
	public static final RegistryObject<Block> SMOOTH_SANDSTONE_BOT_OCHRE_ROOF_TILES_TOP = reg("smooth_sandstone_bot_ochre_roof_tiles_top", new NoItemBlock(AbstractBlock.Properties.copy(BRICKS)));
	public static final RegistryObject<Block> OCHRE_ROOF_TILES_SLAB = reg("ochre_roof_tiles_slab", new MixedSlabBlock(AbstractBlock.Properties.copy(BRICKS)).addMixedBlockRecipe(SANDSTONE_SLAB, SANDSTONE_BOT_OCHRE_ROOF_TILES_TOP, false).addMixedBlockRecipe(CUT_SANDSTONE_SLAB, CUT_SANDSTONE_BOT_OCHRE_ROOF_TILES_TOP, false).addMixedBlockRecipe(SMOOTH_SANDSTONE_SLAB, SMOOTH_SANDSTONE_BOT_OCHRE_ROOF_TILES_TOP, false));
	public static final RegistryObject<Block> SANDSTONE_COLUMN = reg("sandstone_column", new SandstoneColumnBlock(AbstractBlock.Properties.copy(SANDSTONE)));
	public static final RegistryObject<Block> COVERED_SANDSTONE_WALL = reg("covered_sandstone_wall", new CappedWallBlock(AbstractBlock.Properties.copy(SANDSTONE)));
	public static final RegistryObject<Block> MOSAIC_FLOOR = reg("mosaic_floor", new BlockDoTB(AbstractBlock.Properties.copy(SANDSTONE)));
	public static final RegistryObject<Block> MOSAIC_FLOOR_DELICATE = reg("mosaic_floor_delicate", new BlockDoTB(AbstractBlock.Properties.copy(SANDSTONE)));
	public static final RegistryObject<Block> MOSAIC_FLOOR_ROSETTE = reg("mosaic_floor_rosette", new BlockDoTB(AbstractBlock.Properties.copy(SANDSTONE)));
	public static final RegistryObject<Block> BIRCH_FOOTSTOOL = reg("birch_footstool", new BirchFootstoolBlock(AbstractBlock.Properties.copy(BIRCH_PLANKS), 9.0F));
	public static final RegistryObject<Block> BIRCH_COUCH = reg("birch_couch", new BirchCouchBlock(AbstractBlock.Properties.copy(BIRCH_PLANKS), 13.0F));
	public static final RegistryObject<Block> MARBLE_STATUE_MARS = reg("marble_statue_mars", new MarbleStatueBlock(AbstractBlock.Properties.copy(SANDSTONE).noOcclusion()));
	public static final RegistryObject<Block> WILD_GRAPE = reg("wild_grape", new WildPlantBlock(AbstractBlock.Properties.copy(DANDELION)));
	public static final RegistryObject<Block> CYPRESS = reg("cypress", new CypressBlock(AbstractBlock.Properties.copy(SPRUCE_LEAVES)).setBurnable());

	private static RegistryObject<Block> reg(String name, Block block){
		Item item;
		String itemName = null;
		if(block instanceof IBlockCustomItem){
			item = ((IBlockCustomItem)block).getCustomItemBlock();
			itemName = ((IBlockCustomItem)block).getCustomItemName();
		}else{
			item = new BlockItem(block, new Item.Properties().tab(DOTB_TAB));
		}
		if(item != null) DoTBItemsRegistry.reg(itemName == null ? name : itemName, item);
		return BLOCKS.register(name, () -> block);
	}

	private static ToIntFunction<BlockState> litBlockEmission(int lightValue) {
		return state -> state.getValue(BlockStateProperties.LIT) ? lightValue : 0;
	}
}