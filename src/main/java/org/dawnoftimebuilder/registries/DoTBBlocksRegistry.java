package org.dawnoftimebuilder.registries;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import org.dawnoftimebuilder.block.IBlockCustomItem;
import org.dawnoftimebuilder.block.precolumbian.BlockPlasteredStoneWindow;
import org.dawnoftimebuilder.block.templates.*;
import org.dawnoftimebuilder.block.german.*;
import org.dawnoftimebuilder.block.general.*;
import org.dawnoftimebuilder.block.japanese.*;
import org.dawnoftimebuilder.block.precolumbian.BlockSerpentSculptedColumn;

import java.util.ArrayList;
import java.util.List;

import static net.minecraftforge.common.PlantType.Crop;
import static net.minecraftforge.common.PlantType.Plains;
import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;

public class DoTBBlocksRegistry {

	public static final List<Block> BLOCKS = new ArrayList<>();

	//General
    public static final Block ACACIA_PLANKS_EDGE = reg(new EdgeBlock("acacia_planks_edge", Material.WOOD, 2.0F, 3.0F));
	public static final Block ACACIA_PLANKS_PLATE = reg(new PlateBlock("acacia_planks_plate", Material.WOOD, 2.0F, 3.0F));
	public static final Block BIRCH_PLANKS_EDGE = reg(new EdgeBlock("birch_planks_edge", Material.WOOD, 2.0F, 3.0F));
	public static final Block BIRCH_PLANKS_PLATE = reg(new PlateBlock("birch_planks_plate", Material.WOOD, 2.0F, 3.0F));
	public static final Block DARK_OAK_PLANKS_EDGE = reg(new EdgeBlock("dark_oak_planks_edge", Material.WOOD, 2.0F, 3.0F));
	public static final Block DARK_OAK_PLANKS_PLATE = reg(new PlateBlock("dark_oak_planks_plate", Material.WOOD, 2.0F, 3.0F));
	public static final Block IRON_CHAIN = reg(new ChainBlock("iron_chain", Material.IRON, 5.0F, 6.0F));
	public static final Block JUNGLE_PLANKS_EDGE = reg(new EdgeBlock("jungle_planks_edge", Material.WOOD, 2.0F, 3.0F));
	public static final Block JUNGLE_PLANKS_PLATE = reg(new PlateBlock("jungle_planks_plate", Material.WOOD, 2.0F, 3.0F));
	public static final Block OAK_PLANKS_PLATE = reg(new PlateBlock("oak_planks_plate", Material.WOOD, 2.0F, 3.0F));
    public static final Block OAK_PLANKS_EDGE = reg(new EdgeBlock("oak_planks_edge", Material.WOOD, 2.0F, 3.0F));
	public static final Block PATH_GRAVEL = reg(new PathBlock("path_gravel"));
	public static final Block PATH_STEPPING_STONES = reg(new PathBlock("path_stepping_stones"));
	public static final Block PATH_COBBLED = reg(new PathBlock("path_cobbled"));
	public static final Block PATH_OCHRE_TILES = reg(new PathBlock("path_ochre_tiles"));
	public static final Block PATH_DIRT = reg(new PathBlock("path_dirt"));
	public static final Block PATH_GRAVEL_SLAB = reg(new SlabPathBlock("path_gravel_slab"));
	public static final Block PATH_STEPPING_STONES_SLAB = reg(new SlabPathBlock("path_stepping_stones_slab"));
	public static final Block PATH_COBBLED_SLAB = reg(new SlabPathBlock("path_cobbled_slab"));
	public static final Block PATH_OCHRE_TILES_SLAB = reg(new SlabPathBlock("path_ochre_tiles_slab"));
	public static final Block PATH_DIRT_SLAB = reg(new SlabPathBlock("path_dirt_slab"));
	public static final Block RAMMED_DIRT = reg(new BlockDoTB("rammed_dirt", Material.EARTH, 0.7F, 0.7F));
    public static final Block SPRUCE_PLANKS_EDGE = reg(new EdgeBlock("spruce_planks_edge", Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block SPRUCE_PLANKS_SUPPORT_SLAB = reg(new SupportSlabBlock("spruce_planks_support_slab", Material.WOOD, 1.5F, 3.0F).setBurnable());
	public static final Block SPRUCE_PLANKS_PLATE = reg(new PlateBlock("spruce_planks_plate", Material.WOOD, 2.0F, 3.0F));
	public static final Block THATCH_WHEAT = reg(new BlockDoTB("thatch_wheat", Material.WOOL, 1.0F, 1.0F).setBurnable());
	public static final Block THATCH_WHEAT_STAIRS = reg(new StairsBlockDoTB("thatch_wheat_stairs", THATCH_WHEAT).setBurnable());
	public static final Block THATCH_WHEAT_SLAB = reg(new SlabBlock("thatch_wheat_slab", THATCH_WHEAT).setBurnable());
	public static final Block THATCH_WHEAT_EDGE = reg(new EdgeBlock("thatch_wheat_edge", THATCH_WHEAT).setBurnable());
	public static final Block THATCH_BAMBOO = reg(new BlockDoTB("thatch_bamboo", Material.WOOL, 1.0F, 1.0F).setBurnable());
	public static final Block THATCH_BAMBOO_STAIRS = reg(new StairsBlockDoTB("thatch_bamboo_stairs", THATCH_BAMBOO).setBurnable());
	public static final Block THATCH_BAMBOO_SLAB = reg(new SlabBlock("thatch_bamboo_slab", THATCH_BAMBOO).setBurnable());
	public static final Block THATCH_BAMBOO_EDGE = reg(new EdgeBlock("thatch_bamboo_edge", THATCH_BAMBOO).setBurnable());
	public static final Block FIREPLACE = reg(new FireplaceBlock());

	//German
	public static final Block COBBLED_LIMESTONE = reg(new BlockDoTB("cobbled_limestone", Material.ROCK, 2.0F, 6.0F));
	public static final Block FLAT_ROOF_TILES = reg(new BlockDoTB("flat_roof_tiles", Material.ROCK,1.5F, 5.0F));
	public static final Block FLAT_ROOF_TILES_STAIRS = reg(new StairsBlockDoTB("flat_roof_tiles_stairs", FLAT_ROOF_TILES));
	public static final Block FLAT_ROOF_TILES_SLAB = reg(new SlabBlock("flat_roof_tiles_slab", FLAT_ROOF_TILES));
	public static final Block FLAT_ROOF_TILES_EDGE = reg(new EdgeBlock("flat_roof_tiles_edge", FLAT_ROOF_TILES));
	public static final Block FRAMED_RAMMED_DIRT = reg(new BlockDoTB("framed_rammed_dirt", Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block IRON_PORTCULLIS = reg(new PortcullisBlock("iron_portcullis"));
	public static final Block LATTICE_GLASS = reg(new GlassBlockDoTB("lattice_glass", 1.0F, 1.0F));
	public static final Block LATTICE_GLASS_PANE = reg(new PaneBlockDoTB("lattice_glass_pane", Material.GLASS, 1.0F, 1.0F, BlockRenderLayer.TRANSLUCENT));
	public static final Block LATTICE_WAXED_OAK_WINDOW = reg(new SidedWindowBlock("lattice_waxed_oak_window", Material.GLASS, 2.0F, 3.0F));
	public static final Block LIMESTONE_BRICK = reg(new BlockDoTB("limestone_brick", Material.ROCK,1.5F, 6.0F));
	public static final Block LIMESTONE_BRICK_STAIRS = reg(new StairsBlockDoTB("limestone_brick_stairs", LIMESTONE_BRICK));
	public static final Block LIMESTONE_BRICK_SLAB = reg(new SlabBlock("limestone_brick_slab", LIMESTONE_BRICK));
	public static final Block LIMESTONE_BRICK_EDGE = reg(new EdgeBlock("limestone_brick_edge", LIMESTONE_BRICK));
	public static final Block LIMESTONE_BRICK_WALL = reg(new WallBlockDoTB("limestone_brick_wall", Material.ROCK, 1.5F, 6.0F));
	public static final Block LIMESTONE_CHIMNEY = reg(new LimestoneChimneyBlock());
	public static final Block LIMESTONE_FIREPLACE = reg(new LimestoneFireplaceBlock());
	public static final Block WAXED_OAK_DOOR = reg(new DoorBlockDoTB("waxed_oak_door", Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block WAXED_OAK_TRAPDOOR = reg(new TrapDoorBlockDoTB("waxed_oak_trapdoor", Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block WAXED_OAK_SHUTTERS = reg(new ShuttersBlock("waxed_oak_shutters", Material.WOOD, 2.0F, 2.0F).setBurnable());
	public static final Block WAXED_OAK_BEAM = reg(new BeamBlock("waxed_oak_beam", Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block WAXED_OAK_SUPPORT_BEAM = reg(new SupportBeamBlock("waxed_oak_support_beam", Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block WAXED_OAK_SUPPORT_SLAB = reg(new SupportSlabBlock("waxed_oak_support_slab", Material.WOOD, 1.5F, 3.0F).setBurnable());
	public static final Block WAXED_OAK_SMALL_SHUTTERS = reg(new SmallShuttersBlock("waxed_oak_small_shutters", Material.WOOD, 2.0F, 2.0F).setBurnable());
	public static final Block WAXED_OAK_TIMBER_FRAME = reg(new BlockDoTB("waxed_oak_timber_frame", Material.WOOD, 3.0F, 5.0F).setBurnable());
	public static final Block WAXED_OAK_TIMBER_FRAME_CORNER = reg(new RotatedPillarBlockDoTB("waxed_oak_timber_frame_corner", Material.WOOD, 3.0F, 5.0F).setBurnable());
	public static final Block WAXED_OAK_TIMBER_FRAME_PILLAR = reg(new RotatedPillarBlockDoTB("waxed_oak_timber_frame_pillar", Material.WOOD, 3.0F, 5.0F).setBurnable());
	public static final Block WAXED_OAK_FENCE = reg(new FenceBlockDoTB("waxed_oak_fence", Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block WAXED_OAK_PERGOLA = reg(new PergolaBlock("waxed_oak_pergola", Material.WOOD, 2.0F, 3.0F));
	public static final Block WAXED_OAK_LATTICE = reg(new LatticeBlock("waxed_oak_lattice", Material.WOOD, 2.0F, 3.0F));
	public static final Block WAXED_OAK_PLANKS = reg(new BlockDoTB("waxed_oak_planks", Material.WOOD, 2.0F, 3.0F).setBurnable());
	public static final Block WAXED_OAK_PLANKS_STAIRS = reg(new StairsBlockDoTB("waxed_oak_planks_stairs", WAXED_OAK_PLANKS).setBurnable());
	public static final Block WAXED_OAK_PLANKS_SLAB = reg(new SlabBlock("waxed_oak_planks_slab", WAXED_OAK_PLANKS).setBurnable());
	public static final Block WAXED_OAK_PLANKS_PLATE = reg(new PlateBlock("waxed_oak_planks_plate", WAXED_OAK_PLANKS).setBurnable());
	public static final Block WAXED_OAK_PLANKS_EDGE = reg(new EdgeBlock("waxed_oak_planks_edge", WAXED_OAK_PLANKS).setBurnable());

	//Japanese
	//public static final Block X = reg(new DoTBBlockDryer("bamboo_drying_tray", Material.WOOD, 1.0F, SoundType.WOOD));
	public static final Block CAMELLIA = reg(new GrowingBushBlock("camellia", "camellia_seeds", Plains));
	//public static final Block X = reg(new BlockMulberry());
	public static final Block CAST_IRON_TEAPOT_GREY = reg(new CastIronTeapotBlock("cast_iron_teapot_grey", 0.15F));
	public static final Block CAST_IRON_TEAPOT_GREEN = reg(new CastIronTeapotBlock("cast_iron_teapot_green", 0.05F));
	public static final Block CAST_IRON_TEAPOT_DECORATED = reg(new CastIronTeapotBlock("cast_iron_teapot_decorated", 0.07F));
	public static final Block CAST_IRON_TEACUP_GREY = reg(new CastIronTeacupBlock("cast_iron_teacup_grey"));
	public static final Block CAST_IRON_TEACUP_GREEN = reg(new CastIronTeacupBlock("cast_iron_teacup_green"));
	public static final Block CAST_IRON_TEACUP_DECORATED = reg(new CastIronTeacupBlock("cast_iron_teacup_decorated"));
	//public static final Block X = reg(new BlockFloweryPaperWall());
	//public static final Block X = reg(new BlockPaperLamp());
	public static final Block GREY_ROOF_TILES = reg(new BlockDoTB("grey_roof_tiles", Material.ROCK,1.5F, 5.0F));
	public static final Block GREY_ROOF_TILES_STAIRS = reg(new StairsBlockDoTB("grey_roof_tiles_stairs", GREY_ROOF_TILES));
	public static final Block GREY_ROOF_TILES_SLAB = reg(new SlabBlock("grey_roof_tiles_slab", GREY_ROOF_TILES));
	public static final Block GREY_ROOF_TILES_EDGE = reg(new EdgeBlock("grey_roof_tiles_edge", GREY_ROOF_TILES));
	public static final Block GREY_ROOF_TILES_WALL = reg(new WallBlockDoTB("grey_roof_tiles_wall", Material.ROCK,1.5F, 5.0F));
	public static final Block IKEBANA_FLOWER_POT = reg(new IkebanaFlowerPotBlock());
	//public static final Block X = reg(new BlockSpruceLeglessChair());
	//public static final Block X = reg(new BlockLittleFlag());
	//public static final Block X = reg(new BlockPaperDoor());
	//public static final Block X = reg(new BlockPaperLantern());
	//public static final Block X = reg(new BlockPaperWall("paper_wall"));
	//public static final Block X = reg(new BlockPaperWall("paper_wall_flat"));
	public static final Block PAPER_WALL_WINDOWS = reg(new PaneBlockDoTB("paper_wall_window", Material.WOOL, 1.0F, 1.0F).setBurnable());
	//public static final Block X = reg(new BlockPaperFoldingScreen());
	//public static final Block X = reg(new BlockRedPaintedLog());
	public static final Block RICE = reg(new WaterDoubleCropsBlock("rice", 2));
	public static final Block BURNT_STRIPPED_SPRUCE_LOG = reg(new RotatedPillarBlockDoTB("burnt_stripped_spruce_log", Material.WOOD, 2.5F, 5.0F));
	public static final Block SMALL_TATAMI_MAT = reg(new SmallTatamiMatBlock());
	public static final Block SMALL_TATAMI_FLOOR = reg(new SmallTatamiFloorBlock());
	public static final Block CHARRED_SPRUCE_FOUNDATION = reg(new BlockDoTB("charred_spruce_foundation",Material.WOOD,2.5F, 5.0F));
	public static final Block CHARRED_SPRUCE_BOARDS =reg(new BlockDoTB("charred_spruce_boards", Material.WOOD, 2.5F, 5.0F));
	public static final Block BURNT_SPRUCE_LOG_COVERED = reg(new RotatedPillarBlockDoTB("burnt_spruce_log_covered", Material.WOOD, 3.0F, 5.0F).setBurnable());
	public static final Block BURNT_SPRUCE_BEAM = reg(new BeamBlock("burnt_spruce_beam", Material.WOOD, 2.5F, 5.0F).setBurnable());
	public static final Block BURNT_SPRUCE_FOUNDATION = reg(new BlockDoTB("burnt_spruce_foundation", Material.WOOD, 2.5F, 5.0F).setBurnable());
	public static final Block BURNT_SPRUCE_FENCE = reg(new FenceBlockDoTB("burnt_spruce_fence", Material.WOOD, 2.5F, 5.0F).setBurnable());
	public static final Block BURNT_SPRUCE_WALL = reg(new WallBlockDoTB("burnt_spruce_wall", Material.WOOD, 2.5F, 5.0F).setBurnable());
	public static final Block BURNT_SPRUCE_RAILING = reg(new BurntSpruceRailingBlock());
	//public static final Block X = reg(new BlockSpruceRoofSupport("spruce_roof_support"));
	//public static final Block X = reg(new BlockSpruceRoofSupportMerged());
	public static final Block BURNT_SPRUCE_PLANKS = reg(new BlockDoTB("burnt_spruce_planks", Material.WOOD, 2.5F, 5.0F));
	public static final Block BURNT_SPRUCE_SUPPORT_BEAM = reg(new SupportBeamBlock("burnt_spruce_support_beam", Material.WOOD, 2.5F, 5.0F).setBurnable());
	public static final Block SPRUCE_LOW_TABLE = reg(new SpruceLowTableBlock());
	public static final Block BURNT_SPRUCE_TIMBER_FRAME = reg(new BlockDoTB("burnt_spruce_timber_frame", Material.WOOD, 3.0F, 6.0F).setBurnable());
	public static final Block BURNT_SPRUCE_TIMBER_FRAME_PILLAR = reg(new RotatedPillarBlockDoTB("burnt_spruce_timber_frame_pillar", Material.WOOD, 3.0F, 6.0F).setBurnable());
	//public static final Block X = reg(new BlockStoneLantern());
	public static final Block TATAMI_MAT = reg(new TatamiMatBlock());
	public static final Block TATAMI_FLOOR = reg(new TatamiFloorBlock());
	//public static final Block X = reg(new BlockFuton());
	//public static final Block X = reg(new BlockIrori());
	public static final Block SAKE_BOTTLE = reg(new SakeBottleBlock());
	public static final Block SAKE_CUP = reg(new SakeCupBlock());
	public static final Block BURNT_SPRUCE_FOUNDATION_SLAB = reg(new SlabBlock("burnt_spruce_foundation_slab", Material.WOOD, 2.5F, 5.0F));
	//public static final Block X = reg(new BlockStickBundle());

	//Pre_columbian
	//public static final Block X = reg(new BlockChiseledPlasteredStone());
	public static final Block COMMELINA = reg(new SoilCropsBlock("commelina", Crop));
	//public static final Block X = reg(new BlockFeatheredSerpentSculpture());
	public static final Block PLASTERED_STONE = reg(new BlockDoTB("plastered_stone", Material.ROCK,1.5F, 6.0F));
	public static final Block RED_PLASTERED_STONE = reg(new BlockDoTB("red_plastered_stone", Material.ROCK,1.5F, 6.0F));
	public static final Block GREEN_ORNAMENTED_PLASTERED_STONE_FRIEZE = reg(new PlateBlock("green_ornamented_plastered_stone_frieze", PLASTERED_STONE));
	public static final Block GREEN_PLASTERED_STONE_FRIEZE = reg(new PlateBlock("green_plastered_stone_frieze", PLASTERED_STONE));
	//public static final Block X = reg(new BlockGreenSculptedPlasteredStoneFrieze());
	public static final Block GREEN_SMALL_PLASTERED_STONE_FRIEZE = reg(new EdgeBlock("green_small_plastered_stone_frieze", PLASTERED_STONE));
	public static final Block MAIZE = reg(new DoubleCropsBlock("maize", Crop, 4));
	public static final Block RED_ORNAMENTED_PLASTERED_STONE = reg(new BlockDoTB("red_ornamented_plastered_stone", Material.ROCK,1.5F, 6.0F));
	//public static final Block X = reg(new BlockPlasteredStoneColumn());
	//public static final Block X = reg(new BlockPlasteredStoneCresset());
	public static final Block PLASTERED_STONE_FRIEZE = reg(new PlateBlock("plastered_stone_frieze", PLASTERED_STONE));
	public static final Block PLASTERED_STONE_PLATE = reg(new PlateBlock("plastered_stone_plate", PLASTERED_STONE));
	public static final Block PLASTERED_STONE_STAIRS = reg(new StairsBlockDoTB("plastered_stone_stairs", PLASTERED_STONE));
	public static final Block PLASTERED_STONE_SLAB = reg(new SlabBlock("plastered_stone_slab", PLASTERED_STONE));
	public static final Block PLASTERED_STONE_EDGE = reg(new EdgeBlock("plastered_stone_edge", PLASTERED_STONE));
	public static final Block PLASTERED_STONE_WINDOW = reg(new BlockPlasteredStoneWindow());
	public static final Block RED_ORNAMENTED_PLASTERED_STONE_FRIEZE = reg(new PlateBlock("red_ornamented_plastered_stone_frieze", RED_PLASTERED_STONE));
	public static final Block RED_PLASTERED_STONE_FRIEZE = reg(new PlateBlock("red_plastered_stone_frieze", RED_PLASTERED_STONE));
	public static final Block RED_PLASTERED_STONE_PLATE = reg(new PlateBlock("red_plastered_stone_plate", RED_PLASTERED_STONE));
	public static final Block RED_PLASTERED_STONE_STAIRS = reg(new StairsBlockDoTB("red_plastered_stone_stairs", RED_PLASTERED_STONE));
	public static final Block RED_PLASTERED_STONE_SLAB = reg(new SlabBlock("red_plastered_stone_slab", RED_PLASTERED_STONE));
	public static final Block RED_PLASTERED_STONE_EDGE = reg(new EdgeBlock("red_plastered_stone_edge", RED_PLASTERED_STONE));
	//public static final Block X = reg(new BlockRedSculptedPlasteredStoneFrieze());
	public static final Block RED_SMALL_PLASTERED_STONE_FRIEZE = reg(new EdgeBlock("red_small_plastered_stone_frieze", RED_PLASTERED_STONE));
	public static final Block SERPENT_SCULPTED_COLUMN = reg(new BlockSerpentSculptedColumn());
	//public static final Block X = reg(new BlockStoneFrieze());

	//Roman
	public static final Block OCHRE_ROOF_TILES = reg(new BlockDoTB("ochre_roof_tiles", Material.ROCK, 1.5F, 5.0F));
	//public static final Block X = reg(new BlockOchreRoofTilesMerged());
	//public static final Block X = reg(new BlockOchreRoofTilesSlab());
	//public static final Block X = reg(new BlockSandstoneColumn());

	private static Block reg(Block block){
		BLOCKS.add(block);

		Item item = null;
		if(block instanceof IBlockCustomItem) item = ((IBlockCustomItem)block).getCustomItemBlock();
		else if(block.getRegistryName() != null) item = new BlockItem(block, new Item.Properties().group(DOTB_TAB)).setRegistryName(block.getRegistryName());

		if(item != null) DoTBItemsRegistry.ITEMS.add(item);

		return block;
	}
}