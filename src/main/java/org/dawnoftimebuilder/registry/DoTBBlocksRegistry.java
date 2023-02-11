package org.dawnoftimebuilder.registry;

import java.util.HashMap;
import java.util.function.ToIntFunction;

import org.dawnoftimebuilder.DawnOfTimeBuilder;
import org.dawnoftimebuilder.block.ICustomBlockItem;
import org.dawnoftimebuilder.block.french.LimestoneChimneyBlock;
import org.dawnoftimebuilder.block.french.LimestoneGargoyleBlock;
import org.dawnoftimebuilder.block.french.ReinforcedIronFenceBlock;
import org.dawnoftimebuilder.block.general.FireplaceBlock;
import org.dawnoftimebuilder.block.general.IronFenceBlock;
import org.dawnoftimebuilder.block.german.GeraniumBlock;
import org.dawnoftimebuilder.block.german.IvyBlock;
import org.dawnoftimebuilder.block.german.LatticeStoneBricksWindowBlock;
import org.dawnoftimebuilder.block.german.StoneBricksArrowslitBlock;
import org.dawnoftimebuilder.block.german.StoneBricksMachicolationBlock;
import org.dawnoftimebuilder.block.german.WaxedOakChairBlock;
import org.dawnoftimebuilder.block.german.WaxedOakChandelierBlock;
import org.dawnoftimebuilder.block.german.WaxedOakTableBlock;
import org.dawnoftimebuilder.block.german.WaxedOakTimberFrameCornerBlock;
import org.dawnoftimebuilder.block.japanese.CastIronTeacupBlock;
import org.dawnoftimebuilder.block.japanese.CastIronTeapotBlock;
import org.dawnoftimebuilder.block.japanese.CharredSpruceFancyRailingBlock;
import org.dawnoftimebuilder.block.japanese.CharredSpruceRailingBlock;
import org.dawnoftimebuilder.block.japanese.CharredSpruceShuttersBlock;
import org.dawnoftimebuilder.block.japanese.CharredSpruceTallShuttersBlock;
import org.dawnoftimebuilder.block.japanese.FutonBlock;
import org.dawnoftimebuilder.block.japanese.IroriFireplaceBlock;
import org.dawnoftimebuilder.block.japanese.LittleFlagBlock;
import org.dawnoftimebuilder.block.japanese.MapleLeavesBlock;
import org.dawnoftimebuilder.block.japanese.MapleSaplingBlock;
import org.dawnoftimebuilder.block.japanese.MapleTrunkBlock;
import org.dawnoftimebuilder.block.japanese.MulberryBlock;
import org.dawnoftimebuilder.block.japanese.PaperDoorBlock;
import org.dawnoftimebuilder.block.japanese.PaperLampBlock;
import org.dawnoftimebuilder.block.japanese.PaperLanternBlock;
import org.dawnoftimebuilder.block.japanese.PausedMapleSaplingBlock;
import org.dawnoftimebuilder.block.japanese.SakeBottleBlock;
import org.dawnoftimebuilder.block.japanese.SakeCupBlock;
import org.dawnoftimebuilder.block.japanese.SmallTatamiFloorBlock;
import org.dawnoftimebuilder.block.japanese.SmallTatamiMatBlock;
import org.dawnoftimebuilder.block.japanese.SpruceLeglessChairBlock;
import org.dawnoftimebuilder.block.japanese.SpruceLowTableBlock;
import org.dawnoftimebuilder.block.japanese.StickBundleBlock;
import org.dawnoftimebuilder.block.japanese.StoneLanternBlock;
import org.dawnoftimebuilder.block.japanese.TatamiFloorBlock;
import org.dawnoftimebuilder.block.japanese.TatamiMatBlock;
import org.dawnoftimebuilder.block.precolumbian.FeatheredSerpentSculptureBlock;
import org.dawnoftimebuilder.block.precolumbian.GreenSculptedPlasteredStoneFriezeBlock;
import org.dawnoftimebuilder.block.precolumbian.PlasteredStoneColumnBlock;
import org.dawnoftimebuilder.block.precolumbian.PlasteredStoneCressetBlock;
import org.dawnoftimebuilder.block.precolumbian.PlasteredStoneWindowBlock;
import org.dawnoftimebuilder.block.precolumbian.RedSculptedPlasteredStoneFriezeBlock;
import org.dawnoftimebuilder.block.precolumbian.SerpentSculptedColumnBlock;
import org.dawnoftimebuilder.block.precolumbian.WildMaizeBlock;
import org.dawnoftimebuilder.block.roman.BigFlowerPotBlock;
import org.dawnoftimebuilder.block.roman.BirchCouchBlock;
import org.dawnoftimebuilder.block.roman.BirchFootstoolBlock;
import org.dawnoftimebuilder.block.roman.CypressBlock;
import org.dawnoftimebuilder.block.roman.MarbleBigFlowerPotBlock;
import org.dawnoftimebuilder.block.roman.MarbleStatueBlock;
import org.dawnoftimebuilder.block.roman.SandstoneColumnBlock;
import org.dawnoftimebuilder.block.templates.BalusterBlock;
import org.dawnoftimebuilder.block.templates.BaseChimneyDoTB;
import org.dawnoftimebuilder.block.templates.BeamBlock;
import org.dawnoftimebuilder.block.templates.BlockDoTB;
import org.dawnoftimebuilder.block.templates.BottomPaneBlockDoTB;
import org.dawnoftimebuilder.block.templates.BushBlockDoT;
import org.dawnoftimebuilder.block.templates.CandlestickBlock;
import org.dawnoftimebuilder.block.templates.CappedWallBlock;
import org.dawnoftimebuilder.block.templates.CarpetBlockDoTB;
import org.dawnoftimebuilder.block.templates.DoorBlockDoTB;
import org.dawnoftimebuilder.block.templates.DoubleCropsBlock;
import org.dawnoftimebuilder.block.templates.DryerBlock;
import org.dawnoftimebuilder.block.templates.EdgeBlock;
import org.dawnoftimebuilder.block.templates.FaucetBlock;
import org.dawnoftimebuilder.block.templates.FenceGateBlockDoTB;
import org.dawnoftimebuilder.block.templates.FlowerPotBlockDoTB;
import org.dawnoftimebuilder.block.templates.FoldingScreenBlock;
import org.dawnoftimebuilder.block.templates.GrowingBushBlock;
import org.dawnoftimebuilder.block.templates.HorizontalAxisBlockDoTB;
import org.dawnoftimebuilder.block.templates.HorizontalBlockDoTB;
import org.dawnoftimebuilder.block.templates.LatticeBlock;
import org.dawnoftimebuilder.block.templates.MixedRoofSupportBlock;
import org.dawnoftimebuilder.block.templates.MixedSlabBlock;
import org.dawnoftimebuilder.block.templates.MultiblockFireplaceBlock;
import org.dawnoftimebuilder.block.templates.NoItemBlock;
import org.dawnoftimebuilder.block.templates.PaneBlockDoTB;
import org.dawnoftimebuilder.block.templates.PergolaBlock;
import org.dawnoftimebuilder.block.templates.PlanterBlock;
import org.dawnoftimebuilder.block.templates.PlateBlock;
import org.dawnoftimebuilder.block.templates.PoolBlock;
import org.dawnoftimebuilder.block.templates.PortcullisBlock;
import org.dawnoftimebuilder.block.templates.RotatedPillarBlockDoTB;
import org.dawnoftimebuilder.block.templates.ShutterBlock;
import org.dawnoftimebuilder.block.templates.SidedFlowerPotBlock;
import org.dawnoftimebuilder.block.templates.SidedWindowBlock;
import org.dawnoftimebuilder.block.templates.SlabBlockDoTB;
import org.dawnoftimebuilder.block.templates.SmallPoolBlock;
import org.dawnoftimebuilder.block.templates.SmallShutterBlock;
import org.dawnoftimebuilder.block.templates.SoilCropsBlock;
import org.dawnoftimebuilder.block.templates.StairsBlockDoTB;
import org.dawnoftimebuilder.block.templates.SupportBeamBlock;
import org.dawnoftimebuilder.block.templates.SupportSlabBlock;
import org.dawnoftimebuilder.block.templates.WaterDoubleCropsBlock;
import org.dawnoftimebuilder.block.templates.WaterJetBlock;
import org.dawnoftimebuilder.block.templates.WaterMovingTrickleBlock;
import org.dawnoftimebuilder.block.templates.WaterSourceTrickleBlock;
import org.dawnoftimebuilder.block.templates.WaterTrickleBlock;
import org.dawnoftimebuilder.block.templates.WildPlantBlock;
import org.dawnoftimebuilder.item.IHasFlowerPot;
import org.dawnoftimebuilder.util.DoTBFoods;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DoTBBlocksRegistry {

	public static final DeferredRegister<Block>	BLOCKS										= DeferredRegister.create(ForgeRegistries.BLOCKS, DawnOfTimeBuilder.MOD_ID);
	public static final HashMap<String, Block>	POT_BLOCKS									= new HashMap<>();

	//General
	public static final RegistryObject<Block>	ACACIA_PLANKS_EDGE							= DoTBBlocksRegistry.reg("acacia_planks_edge", new EdgeBlock(AbstractBlock.Properties.copy(Blocks.ACACIA_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	ACACIA_PLANKS_PLATE							= DoTBBlocksRegistry.reg("acacia_planks_plate", new PlateBlock(AbstractBlock.Properties.copy(Blocks.ACACIA_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	ACACIA_PERGOLA								= DoTBBlocksRegistry.reg("acacia_pergola", new PergolaBlock(AbstractBlock.Properties.copy(Blocks.ACACIA_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	ACACIA_LATTICE								= DoTBBlocksRegistry.reg("acacia_lattice", new LatticeBlock(AbstractBlock.Properties.copy(Blocks.ACACIA_PLANKS).noOcclusion()).setBurnable());
	public static final RegistryObject<Block>	ACACIA_BEAM									= DoTBBlocksRegistry.reg("acacia_beam", new BeamBlock(AbstractBlock.Properties.copy(Blocks.ACACIA_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	ACACIA_WALL									= DoTBBlocksRegistry.reg("acacia_wall", new WallBlock(AbstractBlock.Properties.copy(Blocks.ACACIA_PLANKS)));
	public static final RegistryObject<Block>	ACACIA_SUPPORT_BEAM							= DoTBBlocksRegistry.reg("acacia_support_beam", new SupportBeamBlock(AbstractBlock.Properties.copy(Blocks.ACACIA_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	ACACIA_SUPPORT_SLAB							= DoTBBlocksRegistry.reg("acacia_support_slab", new SupportSlabBlock(AbstractBlock.Properties.copy(Blocks.ACACIA_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	BIRCH_PLANKS_EDGE							= DoTBBlocksRegistry.reg("birch_planks_edge", new EdgeBlock(AbstractBlock.Properties.copy(Blocks.BIRCH_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	BIRCH_PLANKS_PLATE							= DoTBBlocksRegistry.reg("birch_planks_plate", new PlateBlock(AbstractBlock.Properties.copy(Blocks.BIRCH_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	BIRCH_PERGOLA								= DoTBBlocksRegistry.reg("birch_pergola", new PergolaBlock(AbstractBlock.Properties.copy(Blocks.BIRCH_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	BIRCH_LATTICE								= DoTBBlocksRegistry.reg("birch_lattice", new LatticeBlock(AbstractBlock.Properties.copy(Blocks.BIRCH_PLANKS).noOcclusion()).setBurnable());
	public static final RegistryObject<Block>	BIRCH_BEAM									= DoTBBlocksRegistry.reg("birch_beam", new BeamBlock(AbstractBlock.Properties.copy(Blocks.BIRCH_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	BIRCH_WALL									= DoTBBlocksRegistry.reg("birch_wall", new WallBlock(AbstractBlock.Properties.copy(Blocks.BIRCH_PLANKS)));
	public static final RegistryObject<Block>	BIRCH_SUPPORT_BEAM							= DoTBBlocksRegistry.reg("birch_support_beam", new SupportBeamBlock(AbstractBlock.Properties.copy(Blocks.BIRCH_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	BIRCH_SUPPORT_SLAB							= DoTBBlocksRegistry.reg("birch_support_slab", new SupportSlabBlock(AbstractBlock.Properties.copy(Blocks.BIRCH_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	CANDLESTICK									= DoTBBlocksRegistry.reg("candlestick", new CandlestickBlock(AbstractBlock.Properties.copy(Blocks.CAULDRON).lightLevel(DoTBBlocksRegistry.litBlockEmission(10))));
	public static final RegistryObject<Block>	CRIMSON_PLANKS_EDGE							= DoTBBlocksRegistry.reg("crimson_planks_edge", new EdgeBlock(AbstractBlock.Properties.copy(Blocks.CRIMSON_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	CRIMSON_PLANKS_PLATE						= DoTBBlocksRegistry.reg("crimson_planks_plate", new PlateBlock(AbstractBlock.Properties.copy(Blocks.CRIMSON_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	CRIMSON_PERGOLA								= DoTBBlocksRegistry.reg("crimson_pergola", new PergolaBlock(AbstractBlock.Properties.copy(Blocks.CRIMSON_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	CRIMSON_SUPPORT_SLAB						= DoTBBlocksRegistry.reg("crimson_support_slab", new SupportSlabBlock(AbstractBlock.Properties.copy(Blocks.CRIMSON_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	CRIMSON_LATTICE								= DoTBBlocksRegistry.reg("crimson_lattice", new LatticeBlock(AbstractBlock.Properties.copy(Blocks.CRIMSON_PLANKS).noOcclusion()).setBurnable());
	public static final RegistryObject<Block>	CRIMSON_BEAM								= DoTBBlocksRegistry.reg("crimson_beam", new BeamBlock(AbstractBlock.Properties.copy(Blocks.CRIMSON_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	CRIMSON_SUPPORT_BEAM						= DoTBBlocksRegistry.reg("crimson_support_beam", new SupportBeamBlock(AbstractBlock.Properties.copy(Blocks.CRIMSON_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	CRIMSON_WALL								= DoTBBlocksRegistry.reg("crimson_wall", new WallBlock(AbstractBlock.Properties.copy(Blocks.CRIMSON_PLANKS)));
	public static final RegistryObject<Block>	DARK_OAK_PLANKS_EDGE						= DoTBBlocksRegistry.reg("dark_oak_planks_edge", new EdgeBlock(AbstractBlock.Properties.copy(Blocks.DARK_OAK_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	DARK_OAK_PLANKS_PLATE						= DoTBBlocksRegistry.reg("dark_oak_planks_plate", new PlateBlock(AbstractBlock.Properties.copy(Blocks.DARK_OAK_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	DARK_OAK_PERGOLA							= DoTBBlocksRegistry.reg("dark_oak_pergola", new PergolaBlock(AbstractBlock.Properties.copy(Blocks.DARK_OAK_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	DARK_OAK_LATTICE							= DoTBBlocksRegistry.reg("dark_oak_lattice", new LatticeBlock(AbstractBlock.Properties.copy(Blocks.DARK_OAK_PLANKS).noOcclusion()).setBurnable());
	public static final RegistryObject<Block>	DARK_OAK_BEAM								= DoTBBlocksRegistry.reg("dark_oak_beam", new BeamBlock(AbstractBlock.Properties.copy(Blocks.DARK_OAK_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	DARK_OAK_WALL								= DoTBBlocksRegistry.reg("dark_oak_wall", new WallBlock(AbstractBlock.Properties.copy(Blocks.DARK_OAK_PLANKS)));
	public static final RegistryObject<Block>	DARK_OAK_SUPPORT_BEAM						= DoTBBlocksRegistry.reg("dark_oak_support_beam", new SupportBeamBlock(AbstractBlock.Properties.copy(Blocks.DARK_OAK_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	DARK_OAK_SUPPORT_SLAB						= DoTBBlocksRegistry.reg("dark_oak_support_slab", new SupportSlabBlock(AbstractBlock.Properties.copy(Blocks.DARK_OAK_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	JUNGLE_PLANKS_EDGE							= DoTBBlocksRegistry.reg("jungle_planks_edge", new EdgeBlock(AbstractBlock.Properties.copy(Blocks.JUNGLE_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	JUNGLE_PLANKS_PLATE							= DoTBBlocksRegistry.reg("jungle_planks_plate", new PlateBlock(AbstractBlock.Properties.copy(Blocks.JUNGLE_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	JUNGLE_PERGOLA								= DoTBBlocksRegistry.reg("jungle_pergola", new PergolaBlock(AbstractBlock.Properties.copy(Blocks.JUNGLE_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	JUNGLE_LATTICE								= DoTBBlocksRegistry.reg("jungle_lattice", new LatticeBlock(AbstractBlock.Properties.copy(Blocks.JUNGLE_PLANKS).noOcclusion()).setBurnable());
	public static final RegistryObject<Block>	JUNGLE_BEAM									= DoTBBlocksRegistry.reg("jungle_beam", new BeamBlock(AbstractBlock.Properties.copy(Blocks.JUNGLE_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	JUNGLE_WALL									= DoTBBlocksRegistry.reg("jungle_wall", new WallBlock(AbstractBlock.Properties.copy(Blocks.JUNGLE_PLANKS)));
	public static final RegistryObject<Block>	JUNGLE_SUPPORT_BEAM							= DoTBBlocksRegistry.reg("jungle_support_beam", new SupportBeamBlock(AbstractBlock.Properties.copy(Blocks.JUNGLE_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	JUNGLE_SUPPORT_SLAB							= DoTBBlocksRegistry.reg("jungle_support_slab", new SupportSlabBlock(AbstractBlock.Properties.copy(Blocks.JUNGLE_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	OAK_PLANKS_PLATE							= DoTBBlocksRegistry.reg("oak_planks_plate", new PlateBlock(AbstractBlock.Properties.copy(Blocks.OAK_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	OAK_PLANKS_EDGE								= DoTBBlocksRegistry.reg("oak_planks_edge", new EdgeBlock(AbstractBlock.Properties.copy(Blocks.OAK_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	OAK_PERGOLA									= DoTBBlocksRegistry.reg("oak_pergola", new PergolaBlock(AbstractBlock.Properties.copy(Blocks.OAK_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	OAK_LATTICE									= DoTBBlocksRegistry.reg("oak_lattice", new LatticeBlock(AbstractBlock.Properties.copy(Blocks.OAK_PLANKS).noOcclusion()).setBurnable());
	public static final RegistryObject<Block>	OAK_BEAM									= DoTBBlocksRegistry.reg("oak_beam", new BeamBlock(AbstractBlock.Properties.copy(Blocks.OAK_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	OAK_WALL									= DoTBBlocksRegistry.reg("oak_wall", new WallBlock(AbstractBlock.Properties.copy(Blocks.OAK_PLANKS)));
	public static final RegistryObject<Block>	OAK_SUPPORT_BEAM							= DoTBBlocksRegistry.reg("oak_support_beam", new SupportBeamBlock(AbstractBlock.Properties.copy(Blocks.OAK_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	OAK_SUPPORT_SLAB							= DoTBBlocksRegistry.reg("oak_support_slab", new SupportSlabBlock(AbstractBlock.Properties.copy(Blocks.OAK_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	RAMMED_DIRT									= DoTBBlocksRegistry.reg("rammed_dirt", new BlockDoTB(AbstractBlock.Properties.copy(Blocks.TERRACOTTA)));
	public static final RegistryObject<Block>	SPRUCE_PLANKS_EDGE							= DoTBBlocksRegistry.reg("spruce_planks_edge", new EdgeBlock(AbstractBlock.Properties.copy(Blocks.SPRUCE_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	SPRUCE_PLANKS_PLATE							= DoTBBlocksRegistry.reg("spruce_planks_plate", new PlateBlock(AbstractBlock.Properties.copy(Blocks.SPRUCE_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	SPRUCE_PERGOLA								= DoTBBlocksRegistry.reg("spruce_pergola", new PergolaBlock(AbstractBlock.Properties.copy(Blocks.SPRUCE_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	SPRUCE_LATTICE								= DoTBBlocksRegistry.reg("spruce_lattice", new LatticeBlock(AbstractBlock.Properties.copy(Blocks.SPRUCE_PLANKS).noOcclusion()).setBurnable());
	public static final RegistryObject<Block>	SPRUCE_BEAM									= DoTBBlocksRegistry.reg("spruce_beam", new BeamBlock(AbstractBlock.Properties.copy(Blocks.SPRUCE_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	SPRUCE_WALL									= DoTBBlocksRegistry.reg("spruce_wall", new WallBlock(AbstractBlock.Properties.copy(Blocks.SPRUCE_PLANKS)));
	public static final RegistryObject<Block>	SPRUCE_SUPPORT_BEAM							= DoTBBlocksRegistry.reg("spruce_support_beam", new SupportBeamBlock(AbstractBlock.Properties.copy(Blocks.SPRUCE_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	SPRUCE_SUPPORT_SLAB							= DoTBBlocksRegistry.reg("spruce_support_slab", new SupportSlabBlock(AbstractBlock.Properties.copy(Blocks.SPRUCE_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	THATCH_WHEAT								= DoTBBlocksRegistry.reg("thatch_wheat", new BlockDoTB(AbstractBlock.Properties.of(Material.GRASS, MaterialColor.COLOR_YELLOW).strength(1.0F).sound(SoundType.GRASS)).setBurnable(80, 30));
	public static final RegistryObject<Block>	THATCH_WHEAT_EDGE							= DoTBBlocksRegistry.reg("thatch_wheat_edge", new EdgeBlock(AbstractBlock.Properties.of(Material.GRASS, MaterialColor.COLOR_YELLOW).strength(1.0F).sound(SoundType.GRASS)).setBurnable(80, 30));
	public static final RegistryObject<Block>	THATCH_WHEAT_PLATE							= DoTBBlocksRegistry.reg("thatch_wheat_plate", new PlateBlock(AbstractBlock.Properties.of(Material.GRASS, MaterialColor.COLOR_YELLOW).strength(1.0F).sound(SoundType.GRASS)).setBurnable(80, 30));
	public static final RegistryObject<Block>	THATCH_WHEAT_SLAB							= DoTBBlocksRegistry.reg("thatch_wheat_slab", new SlabBlockDoTB(AbstractBlock.Properties.of(Material.GRASS, MaterialColor.COLOR_YELLOW).strength(1.0F).sound(SoundType.GRASS)).setBurnable(80, 30));
	public static final RegistryObject<Block>	THATCH_WHEAT_STAIRS							= DoTBBlocksRegistry.reg("thatch_wheat_stairs", new StairsBlockDoTB(DoTBBlocksRegistry.THATCH_WHEAT, AbstractBlock.Properties.of(Material.GRASS, MaterialColor.COLOR_YELLOW).strength(1.0F).sound(SoundType.GRASS)).setBurnable(80, 30));
	public static final RegistryObject<Block>	THATCH_BAMBOO								= DoTBBlocksRegistry.reg("thatch_bamboo", new BlockDoTB(AbstractBlock.Properties.of(Material.GRASS, MaterialColor.COLOR_YELLOW).strength(1.0F).sound(SoundType.GRASS)).setBurnable(40, 30));
	public static final RegistryObject<Block>	THATCH_BAMBOO_EDGE							= DoTBBlocksRegistry.reg("thatch_bamboo_edge", new EdgeBlock(AbstractBlock.Properties.of(Material.GRASS, MaterialColor.COLOR_YELLOW).strength(1.0F).sound(SoundType.GRASS)).setBurnable(40, 30));
	public static final RegistryObject<Block>	THATCH_BAMBOO_PLATE							= DoTBBlocksRegistry.reg("thatch_bamboo_plate", new PlateBlock(AbstractBlock.Properties.of(Material.GRASS, MaterialColor.COLOR_YELLOW).strength(1.0F).sound(SoundType.GRASS)).setBurnable(40, 30));
	public static final RegistryObject<Block>	THATCH_BAMBOO_SLAB							= DoTBBlocksRegistry.reg("thatch_bamboo_slab", new SlabBlockDoTB(AbstractBlock.Properties.of(Material.GRASS, MaterialColor.COLOR_YELLOW).strength(1.0F).sound(SoundType.GRASS)).setBurnable(40, 30));
	public static final RegistryObject<Block>	THATCH_BAMBOO_STAIRS						= DoTBBlocksRegistry.reg("thatch_bamboo_stairs", new StairsBlockDoTB(DoTBBlocksRegistry.THATCH_BAMBOO, AbstractBlock.Properties.of(Material.GRASS, MaterialColor.COLOR_YELLOW).strength(1.0F).sound(SoundType.GRASS)).setBurnable(40, 30));
	public static final RegistryObject<Block>	WARPED_PLANKS_EDGE							= DoTBBlocksRegistry.reg("warped_planks_edge", new EdgeBlock(AbstractBlock.Properties.copy(Blocks.WARPED_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	WARPED_PLANKS_PLATE							= DoTBBlocksRegistry.reg("warped_planks_plate", new PlateBlock(AbstractBlock.Properties.copy(Blocks.WARPED_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	WARPED_SUPPORT_SLAB							= DoTBBlocksRegistry.reg("warped_support_slab", new SupportSlabBlock(AbstractBlock.Properties.copy(Blocks.WARPED_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	WARPED_PERGOLA								= DoTBBlocksRegistry.reg("warped_pergola", new PergolaBlock(AbstractBlock.Properties.copy(Blocks.WARPED_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	WARPED_LATTICE								= DoTBBlocksRegistry.reg("warped_lattice", new LatticeBlock(AbstractBlock.Properties.copy(Blocks.WARPED_PLANKS).noOcclusion()).setBurnable());
	public static final RegistryObject<Block>	WARPED_BEAM									= DoTBBlocksRegistry.reg("warped_beam", new BeamBlock(AbstractBlock.Properties.copy(Blocks.WARPED_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	WARPED_SUPPORT_BEAM							= DoTBBlocksRegistry.reg("warped_support_beam", new SupportBeamBlock(AbstractBlock.Properties.copy(Blocks.WARPED_PLANKS)).setBurnable());
	public static final RegistryObject<Block>	WARPED_WALL									= DoTBBlocksRegistry.reg("warped_wall", new WallBlock(AbstractBlock.Properties.copy(Blocks.WARPED_PLANKS)));
	public static final RegistryObject<Block>	FIREPLACE									= DoTBBlocksRegistry.reg("fireplace", new FireplaceBlock(AbstractBlock.Properties.of(Material.STONE).strength(1.5F, 6.0F).lightLevel(DoTBBlocksRegistry.litBlockEmission(15))));
	public static final RegistryObject<Block>	IRON_PORTCULLIS								= DoTBBlocksRegistry.reg("iron_portcullis", new PortcullisBlock(AbstractBlock.Properties.copy(Blocks.IRON_DOOR)));
	public static final RegistryObject<Block>	WROUGHT_IRON_FENCE							= DoTBBlocksRegistry.reg("wrought_iron_fence", new IronFenceBlock(AbstractBlock.Properties.copy(Blocks.IRON_BARS)));

	//French
	public static final RegistryObject<Block>	COBBLED_LIMESTONE							= DoTBBlocksRegistry.reg("cobbled_limestone", new BlockDoTB(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	LIMESTONE_BRICKS							= DoTBBlocksRegistry.reg("limestone_bricks", new BlockDoTB(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	LIMESTONE_BRICKS_EDGE						= DoTBBlocksRegistry.reg("limestone_bricks_edge", new EdgeBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	LIMESTONE_BRICKS_PLATE						= DoTBBlocksRegistry.reg("limestone_bricks_plate", new PlateBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	LIMESTONE_BRICKS_SLAB						= DoTBBlocksRegistry.reg("limestone_bricks_slab", new SlabBlockDoTB(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	LIMESTONE_BRICKS_STAIRS						= DoTBBlocksRegistry.reg("limestone_bricks_stairs", new StairsBlockDoTB(DoTBBlocksRegistry.LIMESTONE_BRICKS, AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	LIMESTONE_BRICKS_WALL						= DoTBBlocksRegistry.reg("limestone_bricks_wall", new WallBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	LIMESTONE_BALUSTER							= DoTBBlocksRegistry.reg("limestone_baluster", new PlateBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS).noOcclusion()));
	public static final RegistryObject<Block>	LIMESTONE_GARGOYLE							= DoTBBlocksRegistry.reg("limestone_gargoyle", new LimestoneGargoyleBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS).noOcclusion().randomTicks()));
	public static final RegistryObject<Block>	LIMESTONE_CHIMNEY							= DoTBBlocksRegistry.reg("limestone_chimney", new LimestoneChimneyBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	LIMESTONE_FIREPLACE							= DoTBBlocksRegistry.reg("limestone_fireplace", new MultiblockFireplaceBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS).noOcclusion().lightLevel(DoTBBlocksRegistry.litBlockEmission(15))));
	public static final RegistryObject<Block>	ROOFING_SLATES								= DoTBBlocksRegistry.reg("roofing_slates", new BlockDoTB(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	ROOFING_SLATES_STAIRS						= DoTBBlocksRegistry.reg("roofing_slates_stairs", new StairsBlockDoTB(DoTBBlocksRegistry.ROOFING_SLATES, AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	ROOFING_SLATES_PLATE						= DoTBBlocksRegistry.reg("roofing_slates_plate", new PlateBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	ROOFING_SLATES_SLAB							= DoTBBlocksRegistry.reg("roofing_slates_slab", new SlabBlockDoTB(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	ROOFING_SLATES_EDGE							= DoTBBlocksRegistry.reg("roofing_slates_edge", new EdgeBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	ROOFING_SLATES_WALL							= DoTBBlocksRegistry.reg("roofing_slates_wall", new WallBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	BLACK_WROUGHT_IRON_BALUSTER					= DoTBBlocksRegistry.reg("black_wrought_iron_baluster", new BalusterBlock(AbstractBlock.Properties.copy(Blocks.IRON_BARS)));
	public static final RegistryObject<Block>	BLACK_WROUGHT_IRON_FENCE					= DoTBBlocksRegistry.reg("black_wrought_iron_fence", new IronFenceBlock(AbstractBlock.Properties.copy(Blocks.IRON_BARS)));
	public static final RegistryObject<Block>	REINFORCED_BLACK_WROUGHT_IRON_FENCE			= DoTBBlocksRegistry.reg("reinforced_black_wrought_iron_fence", new ReinforcedIronFenceBlock(AbstractBlock.Properties.copy(Blocks.IRON_BARS)));
	public static final RegistryObject<Block>	REINFORCED_GOLDEN_WROUGHT_IRON_FENCE		= DoTBBlocksRegistry.reg("reinforced_golden_wrought_iron_fence", new ReinforcedIronFenceBlock(AbstractBlock.Properties.copy(Blocks.IRON_BARS)));
	public static final RegistryObject<Block>	BOXWOOD_BUSH								= DoTBBlocksRegistry.reg("boxwood_bush", new BushBlockDoT(AbstractBlock.Properties.copy(Blocks.SPRUCE_LEAVES)));
	public static final RegistryObject<Block>	BOXWOOD_TALL_HEDGE							= DoTBBlocksRegistry.reg("boxwood_tall_hedge", new PlateBlock(AbstractBlock.Properties.copy(Blocks.SPRUCE_LEAVES)));
	public static final RegistryObject<Block>	BOXWOOD_SMALL_HEDGE							= DoTBBlocksRegistry.reg("boxwood_small_hedge", new EdgeBlock(AbstractBlock.Properties.copy(Blocks.SPRUCE_LEAVES)));

	//German
	public static final RegistryObject<Block>	FLAT_ROOF_TILES								= DoTBBlocksRegistry.reg("flat_roof_tiles", new BlockDoTB(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	FLAT_ROOF_TILES_STAIRS						= DoTBBlocksRegistry.reg("flat_roof_tiles_stairs", new StairsBlockDoTB(DoTBBlocksRegistry.FLAT_ROOF_TILES, AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	FLAT_ROOF_TILES_PLATE						= DoTBBlocksRegistry.reg("flat_roof_tiles_plate", new PlateBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	FLAT_ROOF_TILES_SLAB						= DoTBBlocksRegistry.reg("flat_roof_tiles_slab", new SlabBlockDoTB(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	FLAT_ROOF_TILES_EDGE						= DoTBBlocksRegistry.reg("flat_roof_tiles_edge", new EdgeBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	FLAT_ROOF_TILES_WALL						= DoTBBlocksRegistry.reg("flat_roof_tiles_wall", new WallBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	LATTICE_GLASS								= DoTBBlocksRegistry.reg("lattice_glass", new GlassBlock(AbstractBlock.Properties.copy(Blocks.GLASS)));
	public static final RegistryObject<Block>	LATTICE_GLASS_PANE							= DoTBBlocksRegistry.reg("lattice_glass_pane", new PaneBlockDoTB(AbstractBlock.Properties.copy(Blocks.GLASS)));
	public static final RegistryObject<Block>	LATTICE_WAXED_OAK_WINDOW					= DoTBBlocksRegistry.reg("lattice_waxed_oak_window", new SidedWindowBlock(AbstractBlock.Properties.copy(Blocks.GLASS)));
	public static final RegistryObject<Block>	LATTICE_STONE_BRICKS_WINDOW					= DoTBBlocksRegistry.reg("lattice_stone_bricks_window", new LatticeStoneBricksWindowBlock(AbstractBlock.Properties.copy(Blocks.GLASS)));
	public static final RegistryObject<Block>	STONE_BRICKS_ARROWSLIT						= DoTBBlocksRegistry.reg("stone_bricks_arrowslit", new StoneBricksArrowslitBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS).noOcclusion()));
	public static final RegistryObject<Block>	STONE_BRICKS_CHIMNEY						= DoTBBlocksRegistry.reg("stone_bricks_chimney", new BaseChimneyDoTB(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	STONE_BRICKS_EDGE							= DoTBBlocksRegistry.reg("stone_bricks_edge", new EdgeBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	STONE_BRICKS_FIREPLACE						= DoTBBlocksRegistry.reg("stone_bricks_fireplace", new MultiblockFireplaceBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS).noOcclusion().lightLevel(DoTBBlocksRegistry.litBlockEmission(15))));
	public static final RegistryObject<Block>	STONE_BRICKS_MACHICOLATION					= DoTBBlocksRegistry.reg("stone_bricks_machicolation", new StoneBricksMachicolationBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS).noOcclusion()));
	public static final RegistryObject<Block>	STONE_BRICKS_PLATE							= DoTBBlocksRegistry.reg("stone_bricks_plate", new PlateBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	STONE_BRICKS_MASONRY						= DoTBBlocksRegistry.reg("stone_bricks_masonry", new BlockDoTB(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	STONE_BRICKS_MASONRY_STAIRS					= DoTBBlocksRegistry.reg("stone_bricks_masonry_stairs", new StairsBlockDoTB(DoTBBlocksRegistry.STONE_BRICKS_MASONRY, AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	STONE_BRICKS_MASONRY_PLATE					= DoTBBlocksRegistry.reg("stone_bricks_masonry_plate", new PlateBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	STONE_BRICKS_MASONRY_SLAB					= DoTBBlocksRegistry.reg("stone_bricks_masonry_slab", new SlabBlockDoTB(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	STONE_BRICKS_MASONRY_EDGE					= DoTBBlocksRegistry.reg("stone_bricks_masonry_edge", new EdgeBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	WAXED_OAK_FRAMED_RAMMED_DIRT				= DoTBBlocksRegistry.reg("waxed_oak_framed_rammed_dirt", new BlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block>	WAXED_OAK_FRAMED_RAMMED_DIRT_PILLAR			= DoTBBlocksRegistry.reg("waxed_oak_framed_rammed_dirt_pillar", new RotatedPillarBlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block>	WAXED_OAK_PLANKS							= DoTBBlocksRegistry.reg("waxed_oak_planks", new BlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block>	WAXED_OAK_DOOR								= DoTBBlocksRegistry.reg("waxed_oak_door", new DoorBlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD).noOcclusion()));
	public static final RegistryObject<Block>	WAXED_OAK_TRAPDOOR							= DoTBBlocksRegistry.reg("waxed_oak_trapdoor", new TrapDoorBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD).noOcclusion()));
	public static final RegistryObject<Block>	WAXED_OAK_SHUTTER							= DoTBBlocksRegistry.reg("waxed_oak_shutters", new ShutterBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block>	WAXED_OAK_LOG_STRIPPED						= DoTBBlocksRegistry.reg("waxed_oak_log_stripped", new RotatedPillarBlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block>	WAXED_OAK_BALUSTER							= DoTBBlocksRegistry.reg("waxed_oak_baluster", new BalusterBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD).noOcclusion()));
	public static final RegistryObject<Block>	WAXED_OAK_BEAM								= DoTBBlocksRegistry.reg("waxed_oak_beam", new BeamBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block>	WAXED_OAK_SUPPORT_BEAM						= DoTBBlocksRegistry.reg("waxed_oak_support_beam", new SupportBeamBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block>	WAXED_OAK_SUPPORT_SLAB						= DoTBBlocksRegistry.reg("waxed_oak_support_slab", new SupportSlabBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block>	WAXED_OAK_SMALL_SHUTTER						= DoTBBlocksRegistry.reg("waxed_oak_small_shutters", new SmallShutterBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block>	WAXED_OAK_TIMBER_FRAME						= DoTBBlocksRegistry.reg("waxed_oak_timber_frame", new BlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block>	WAXED_OAK_TIMBER_FRAME_CORNER				= DoTBBlocksRegistry.reg("waxed_oak_timber_frame_corner", new WaxedOakTimberFrameCornerBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block>	WAXED_OAK_TIMBER_FRAME_CROSSED				= DoTBBlocksRegistry.reg("waxed_oak_timber_frame_crossed", new BlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block>	WAXED_OAK_TIMBER_FRAME_PILLAR				= DoTBBlocksRegistry.reg("waxed_oak_timber_frame_pillar", new RotatedPillarBlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block>	WAXED_OAK_TIMBER_FRAME_SQUARED				= DoTBBlocksRegistry.reg("waxed_oak_timber_frame_squared", new BlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block>	WAXED_OAK_FENCE								= DoTBBlocksRegistry.reg("waxed_oak_fence", new FenceBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block>	WAXED_OAK_FENCE_GATE						= DoTBBlocksRegistry.reg("waxed_oak_fence_gate", new FenceGateBlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block>	WAXED_OAK_WALL								= DoTBBlocksRegistry.reg("waxed_oak_wall", new WallBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block>	WAXED_OAK_PERGOLA							= DoTBBlocksRegistry.reg("waxed_oak_pergola", new PergolaBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block>	WAXED_OAK_LATTICE							= DoTBBlocksRegistry.reg("waxed_oak_lattice", new LatticeBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD).noOcclusion()).setBurnable());
	public static final RegistryObject<Block>	WAXED_OAK_PLANKS_EDGE						= DoTBBlocksRegistry.reg("waxed_oak_planks_edge", new EdgeBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block>	WAXED_OAK_PLANKS_PLATE						= DoTBBlocksRegistry.reg("waxed_oak_planks_plate", new PlateBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block>	WAXED_OAK_PLANKS_SLAB						= DoTBBlocksRegistry.reg("waxed_oak_planks_slab", new SlabBlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block>	WAXED_OAK_PLANKS_STAIRS						= DoTBBlocksRegistry.reg("waxed_oak_planks_stairs", new StairsBlockDoTB(DoTBBlocksRegistry.WAXED_OAK_PLANKS, AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block>	WAXED_OAK_CHANDELIER						= DoTBBlocksRegistry.reg("waxed_oak_chandelier", new WaxedOakChandelierBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD).noOcclusion().lightLevel(DoTBBlocksRegistry.litBlockEmission(15))));
	public static final RegistryObject<Block>	WAXED_OAK_CHAIR								= DoTBBlocksRegistry.reg("waxed_oak_chair", new WaxedOakChairBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD).noOcclusion(), 11.0F));
	public static final RegistryObject<Block>	WAXED_OAK_TABLE								= DoTBBlocksRegistry.reg("waxed_oak_table", new WaxedOakTableBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD).noOcclusion()));
	public static final RegistryObject<Block>	IVY											= DoTBBlocksRegistry.reg("ivy", new IvyBlock(AbstractBlock.Properties.of(Material.REPLACEABLE_PLANT).noCollission().randomTicks().strength(0.2F).sound(SoundType.VINE)));
	// TODO flowerpot for ivy
	public static final RegistryObject<Block>	GERANIUM_PINK								= DoTBBlocksRegistry.reg("geranium_pink", new GeraniumBlock(AbstractBlock.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
	public static final RegistryObject<Block>	PLANTER_GERANIUM_PINK						= DoTBBlocksRegistry.reg("planter_geranium_pink", new PlanterBlock(AbstractBlock.Properties.of(Material.CLAY).strength(0.6F).noOcclusion()));
	public static final RegistryObject<Block>	STONE_BRICKS_POOL							= DoTBBlocksRegistry.reg("stone_bricks_pool", new PoolBlock(AbstractBlock.Properties.copy(Blocks.STONE)));
	public static final RegistryObject<Block>	STONE_BRICKS_SMALL_POOL						= DoTBBlocksRegistry.reg("stone_bricks_small_pool", new SmallPoolBlock(AbstractBlock.Properties.copy(Blocks.STONE)));
	public static final RegistryObject<Block>	STONE_BRICKS_FAUCET							= DoTBBlocksRegistry.reg("stone_bricks_faucet", new FaucetBlock(AbstractBlock.Properties.of(Material.GRASS).noOcclusion().noCollission()));
	public static final RegistryObject<Block>	STONE_BRICKS_WATER_JET						= DoTBBlocksRegistry.reg("stone_bricks_water_jet", new WaterJetBlock(AbstractBlock.Properties.copy(Blocks.STONE)));
	public static final RegistryObject<Block>	WATER_TRICKLE								= DoTBBlocksRegistry.reg("water_trickle", new WaterTrickleBlock(AbstractBlock.Properties.copy(Blocks.STONE)));
	public static final RegistryObject<Block>	WATER_MOVING_TRICKLE						= DoTBBlocksRegistry.reg("water_moving_trickle", new WaterMovingTrickleBlock(AbstractBlock.Properties.copy(Blocks.STONE).randomTicks()));
	public static final RegistryObject<Block>	WATER_SOURCE_TRICKLE						= DoTBBlocksRegistry.reg("water_source_trickle", new WaterSourceTrickleBlock(AbstractBlock.Properties.copy(Blocks.STONE).randomTicks()));

	//Japanese
	public static final RegistryObject<Block>	CHARRED_SPRUCE_PLANKS						= DoTBBlocksRegistry.reg("charred_spruce_planks", new BlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)).setBurnable(2, 3));
	public static final RegistryObject<Block>	CHARRED_SPRUCE_LOG_STRIPPED					= DoTBBlocksRegistry.reg("charred_spruce_log_stripped", new RotatedPillarBlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)).setBurnable(2, 3));
	public static final RegistryObject<Block>	CHARRED_SPRUCE_PLANKS_EDGE					= DoTBBlocksRegistry.reg("charred_spruce_planks_edge", new EdgeBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)).setBurnable(2, 3));
	public static final RegistryObject<Block>	CHARRED_SPRUCE_PLANKS_PLATE					= DoTBBlocksRegistry.reg("charred_spruce_planks_plate", new PlateBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)).setBurnable(2, 3));
	public static final RegistryObject<Block>	CHARRED_SPRUCE_PLANKS_SLAB					= DoTBBlocksRegistry.reg("charred_spruce_planks_slab", new SlabBlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)).setBurnable(2, 3));
	public static final RegistryObject<Block>	CHARRED_SPRUCE_PLANKS_STAIRS				= DoTBBlocksRegistry.reg("charred_spruce_planks_stairs", new StairsBlockDoTB(DoTBBlocksRegistry.CHARRED_SPRUCE_PLANKS, AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)).setBurnable(2, 3));
	public static final RegistryObject<Block>	CHARRED_SPRUCE_BOARDS						= DoTBBlocksRegistry.reg("charred_spruce_boards", new BlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)).setBurnable(2, 3));
	public static final RegistryObject<Block>	CHARRED_SPRUCE_DOOR							= DoTBBlocksRegistry.reg("charred_spruce_door", new DoorBlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD).noOcclusion()));
	public static final RegistryObject<Block>	CHARRED_SPRUCE_TRAPDOOR						= DoTBBlocksRegistry.reg("charred_spruce_trapdoor", new TrapDoorBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD).noOcclusion()));
	public static final RegistryObject<Block>	CHARRED_SPRUCE_SHUTTERS						= DoTBBlocksRegistry.reg("charred_spruce_shutters", new CharredSpruceShuttersBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD).noOcclusion()));
	public static final RegistryObject<Block>	CHARRED_SPRUCE_TALL_SHUTTERS				= DoTBBlocksRegistry.reg("charred_spruce_tall_shutters", new CharredSpruceTallShuttersBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD).noOcclusion()));
	public static final RegistryObject<Block>	CHARRED_SPRUCE_FOUNDATION					= DoTBBlocksRegistry.reg("charred_spruce_foundation", new BlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)).setBurnable(2, 3));
	public static final RegistryObject<Block>	CHARRED_SPRUCE_FOUNDATION_SLAB				= DoTBBlocksRegistry.reg("charred_spruce_foundation_slab", new SlabBlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)).setBurnable(2, 3));
	public static final RegistryObject<Block>	CHARRED_SPRUCE_WALL							= DoTBBlocksRegistry.reg("charred_spruce_wall", new WallBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block>	CHARRED_SPRUCE_FENCE						= DoTBBlocksRegistry.reg("charred_spruce_fence", new FenceBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block>	CHARRED_SPRUCE_FENCE_GATE					= DoTBBlocksRegistry.reg("charred_spruce_fence_gate", new FenceGateBlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block>	CHARRED_SPRUCE_RAILING						= DoTBBlocksRegistry.reg("charred_spruce_railing", new CharredSpruceRailingBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD).noOcclusion()));
	public static final RegistryObject<Block>	CHARRED_SPRUCE_FANCY_RAILING				= DoTBBlocksRegistry.reg("charred_spruce_fancy_railing", new CharredSpruceFancyRailingBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD).noOcclusion()));
	public static final RegistryObject<Block>	CHARRED_SPRUCE_PERGOLA						= DoTBBlocksRegistry.reg("charred_spruce_pergola", new PergolaBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)).setBurnable(2, 3));
	public static final RegistryObject<Block>	CHARRED_SPRUCE_LATTICE						= DoTBBlocksRegistry.reg("charred_spruce_lattice", new LatticeBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD).noOcclusion()).setBurnable(2, 3));
	public static final RegistryObject<Block>	CHARRED_SPRUCE_SUPPORT_SLAB					= DoTBBlocksRegistry.reg("charred_spruce_support_slab", new SupportSlabBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)).setBurnable(2, 3));
	public static final RegistryObject<Block>	CHARRED_SPRUCE_SUPPORT_BEAM					= DoTBBlocksRegistry.reg("charred_spruce_support_beam", new SupportBeamBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)).setBurnable(2, 3));
	public static final RegistryObject<Block>	CHARRED_SPRUCE_BEAM							= DoTBBlocksRegistry.reg("charred_spruce_beam", new BeamBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)).setBurnable(2, 3));
	public static final RegistryObject<Block>	CHARRED_SPRUCE_TIMBER_FRAME					= DoTBBlocksRegistry.reg("charred_spruce_timber_frame", new BlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block>	CHARRED_SPRUCE_TIMBER_FRAME_PILLAR			= DoTBBlocksRegistry.reg("charred_spruce_timber_frame_pillar", new RotatedPillarBlockDoTB(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)).setBurnable());
	public static final RegistryObject<Block>	RED_PAINTED_BEAM							= DoTBBlocksRegistry.reg("red_painted_beam", new BeamBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD)).setBurnable(2, 3));
	public static final RegistryObject<Block>	GRAY_ROOF_TILES								= DoTBBlocksRegistry.reg("gray_roof_tiles", new BlockDoTB(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	GRAY_ROOF_TILES_STAIRS						= DoTBBlocksRegistry.reg("gray_roof_tiles_stairs", new StairsBlockDoTB(DoTBBlocksRegistry.GRAY_ROOF_TILES, AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	GRAY_ROOF_TILES_PLATE						= DoTBBlocksRegistry.reg("gray_roof_tiles_plate", new PlateBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	GRAY_ROOF_TILES_SLAB						= DoTBBlocksRegistry.reg("gray_roof_tiles_slab", new SlabBlockDoTB(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	GRAY_ROOF_TILES_EDGE						= DoTBBlocksRegistry.reg("gray_roof_tiles_edge", new EdgeBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	GRAY_ROOF_TILES_WALL						= DoTBBlocksRegistry.reg("gray_roof_tiles_wall", new WallBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	CHARRED_SPRUCE_ROOF_SUPPORT					= DoTBBlocksRegistry.reg("charred_spruce_roof_support", new MixedRoofSupportBlock(DoTBBlocksRegistry.GRAY_ROOF_TILES_SLAB, AbstractBlock.Properties.copy(Blocks.STONE_BRICKS).noOcclusion()));
	//TODO Redo stepping stone's textures
	public static final RegistryObject<Block>	STEPPING_STONES								= DoTBBlocksRegistry.reg("stepping_stones", new BlockDoTB(Block.Properties.of(Material.SAND, MaterialColor.STONE).strength(1.2F).sound(SoundType.GRAVEL)));
	public static final RegistryObject<Block>	STEPPING_STONES_SLAB						= DoTBBlocksRegistry.reg("stepping_stones_slab", new SlabBlockDoTB(AbstractBlock.Properties.of(Material.SAND, MaterialColor.STONE).strength(1.2F).sound(SoundType.GRAVEL)));
	public static final RegistryObject<Block>	CURVED_RAKED_GRAVEL							= DoTBBlocksRegistry.reg("curved_raked_gravel", new HorizontalBlockDoTB(AbstractBlock.Properties.of(Material.SAND, MaterialColor.STONE).strength(1.0F).sound(SoundType.GRAVEL)));
	public static final RegistryObject<Block>	STRAIGHT_RAKED_GRAVEL						= DoTBBlocksRegistry.reg("straight_raked_gravel", new HorizontalAxisBlockDoTB(AbstractBlock.Properties.of(Material.SAND, MaterialColor.STONE).strength(1.0F).sound(SoundType.GRAVEL)));
	public static final RegistryObject<Block>	CAST_IRON_TEAPOT_GRAY						= DoTBBlocksRegistry.reg("cast_iron_teapot_gray", new CastIronTeapotBlock(AbstractBlock.Properties.of(Material.METAL).strength(1.0F).sound(SoundType.METAL).noOcclusion()));
	public static final RegistryObject<Block>	CAST_IRON_TEAPOT_GREEN						= DoTBBlocksRegistry.reg("cast_iron_teapot_green", new CastIronTeapotBlock(AbstractBlock.Properties.of(Material.METAL).strength(1.0F).sound(SoundType.METAL).noOcclusion()));
	public static final RegistryObject<Block>	CAST_IRON_TEAPOT_DECORATED					= DoTBBlocksRegistry.reg("cast_iron_teapot_decorated", new CastIronTeapotBlock(AbstractBlock.Properties.of(Material.METAL).strength(1.0F).sound(SoundType.METAL).noOcclusion()));
	public static final RegistryObject<Block>	CAST_IRON_TEACUP_GRAY						= DoTBBlocksRegistry.reg("cast_iron_teacup_gray", new CastIronTeacupBlock(AbstractBlock.Properties.of(Material.METAL).strength(1.0F).sound(SoundType.METAL).noOcclusion()));
	public static final RegistryObject<Block>	CAST_IRON_TEACUP_GREEN						= DoTBBlocksRegistry.reg("cast_iron_teacup_green", new CastIronTeacupBlock(AbstractBlock.Properties.of(Material.METAL).strength(1.0F).sound(SoundType.METAL).noOcclusion()));
	public static final RegistryObject<Block>	CAST_IRON_TEACUP_DECORATED					= DoTBBlocksRegistry.reg("cast_iron_teacup_decorated", new CastIronTeacupBlock(AbstractBlock.Properties.of(Material.METAL).strength(1.0F).sound(SoundType.METAL).noOcclusion()));
	public static final RegistryObject<Block>	BAMBOO_DRYING_TRAY							= DoTBBlocksRegistry.reg("bamboo_drying_tray", new DryerBlock(AbstractBlock.Properties.copy(Blocks.OAK_PLANKS).noOcclusion()));
	public static final RegistryObject<Block>	CAMELLIA									= DoTBBlocksRegistry.reg("camellia", new GrowingBushBlock("camellia_seeds", PlantType.PLAINS, 3));
	public static final RegistryObject<Block>	MULBERRY									= DoTBBlocksRegistry.reg("mulberry", new MulberryBlock("mulberry", PlantType.PLAINS, 3, 2, DoTBFoods.MULBERRY));
	public static final RegistryObject<Block>	IKEBANA_FLOWER_POT							= DoTBBlocksRegistry.reg("ikebana_flower_pot", new SidedFlowerPotBlock(null));
	public static final RegistryObject<Block>	SPRUCE_LOW_TABLE							= DoTBBlocksRegistry.reg("spruce_low_table", new SpruceLowTableBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD).noOcclusion().lightLevel(DoTBBlocksRegistry.litBlockEmission(14))));
	public static final RegistryObject<Block>	SPRUCE_LEGLESS_CHAIR						= DoTBBlocksRegistry.reg("spruce_legless_chair", new SpruceLeglessChairBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(2.0F, 6.0F).sound(SoundType.WOOD).noOcclusion(), 3.0F));
	public static final RegistryObject<Block>	WHITE_LITTLE_FLAG							= DoTBBlocksRegistry.reg("white_little_flag", new LittleFlagBlock(AbstractBlock.Properties.copy(Blocks.WHITE_WOOL)));
	public static final RegistryObject<Block>	PAPER_WALL									= DoTBBlocksRegistry.reg("paper_wall", new BottomPaneBlockDoTB(AbstractBlock.Properties.copy(Blocks.WHITE_WOOL).strength(1.5F, 1.5F)));
	public static final RegistryObject<Block>	PAPER_DOOR									= DoTBBlocksRegistry.reg("paper_door", new PaperDoorBlock(AbstractBlock.Properties.copy(Blocks.WHITE_WOOL).strength(1.5F, 1.5F)));
	public static final RegistryObject<Block>	PAPER_WALL_FLAT								= DoTBBlocksRegistry.reg("paper_wall_flat", new PaneBlockDoTB(AbstractBlock.Properties.copy(Blocks.WHITE_WOOL).strength(1.5F, 1.5F)));
	public static final RegistryObject<Block>	PAPER_WALL_WINDOWS							= DoTBBlocksRegistry.reg("paper_wall_window", new PaneBlockDoTB(AbstractBlock.Properties.copy(Blocks.WHITE_WOOL).strength(1.5F, 1.5F)));
	public static final RegistryObject<Block>	PAPER_WALL_FLOWERY							= DoTBBlocksRegistry.reg("paper_wall_flowery", new PaneBlockDoTB(AbstractBlock.Properties.copy(Blocks.WHITE_WOOL).strength(1.5F, 1.5F)));
	public static final RegistryObject<Block>	PAPER_FOLDING_SCREEN						= DoTBBlocksRegistry.reg("paper_folding_screen", new FoldingScreenBlock(AbstractBlock.Properties.copy(Blocks.WHITE_WOOL).strength(1.5F, 1.5F).noOcclusion()));
	public static final RegistryObject<Block>	RED_PAPER_LANTERN							= DoTBBlocksRegistry.reg("red_paper_lantern", new PaperLanternBlock(AbstractBlock.Properties.copy(Blocks.RED_WOOL).noOcclusion().noCollission().lightLevel(state -> 12)));
	public static final RegistryObject<Block>	PAPER_LAMP									= DoTBBlocksRegistry.reg("paper_lamp", new PaperLampBlock(AbstractBlock.Properties.copy(Blocks.WHITE_WOOL).noOcclusion().lightLevel(state -> 14)));
	public static final RegistryObject<Block>	STONE_LANTERN								= DoTBBlocksRegistry.reg("stone_lantern", new StoneLanternBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS).noOcclusion().lightLevel(state -> 15)));
	public static final RegistryObject<Block>	RICE										= DoTBBlocksRegistry.reg("rice", new WaterDoubleCropsBlock("rice", 2));
	public static final RegistryObject<Block>	SMALL_TATAMI_MAT							= DoTBBlocksRegistry.reg("small_tatami_mat", new SmallTatamiMatBlock(AbstractBlock.Properties.copy(Blocks.WHITE_CARPET)));
	public static final RegistryObject<Block>	SMALL_TATAMI_FLOOR							= DoTBBlocksRegistry.reg("small_tatami_floor", new SmallTatamiFloorBlock(AbstractBlock.Properties.copy(Blocks.WHITE_CARPET)));
	public static final RegistryObject<Block>	TATAMI_MAT									= DoTBBlocksRegistry.reg("tatami_mat", new TatamiMatBlock(AbstractBlock.Properties.copy(Blocks.WHITE_CARPET)));
	public static final RegistryObject<Block>	TATAMI_FLOOR								= DoTBBlocksRegistry.reg("tatami_floor", new TatamiFloorBlock(AbstractBlock.Properties.copy(Blocks.WHITE_CARPET)));
	public static final RegistryObject<Block>	LIGHT_GRAY_FUTON							= DoTBBlocksRegistry.reg("light_gray_futon", new FutonBlock(DyeColor.LIGHT_GRAY, AbstractBlock.Properties.copy(Blocks.LIGHT_GRAY_BED)));
	public static final RegistryObject<Block>	IRORI_FIREPLACE								= DoTBBlocksRegistry.reg("irori_fireplace", new IroriFireplaceBlock(AbstractBlock.Properties.copy(Blocks.SPRUCE_PLANKS).noOcclusion().lightLevel(DoTBBlocksRegistry.litBlockEmission(15))));
	public static final RegistryObject<Block>	SAKE_BOTTLE									= DoTBBlocksRegistry.reg("sake_bottle", new SakeBottleBlock(AbstractBlock.Properties.copy(Blocks.FLOWER_POT)));
	public static final RegistryObject<Block>	SAKE_CUP									= DoTBBlocksRegistry.reg("sake_cup", new SakeCupBlock(AbstractBlock.Properties.copy(Blocks.FLOWER_POT)));
	public static final RegistryObject<Block>	STICK_BUNDLE								= DoTBBlocksRegistry.reg("stick_bundle", new StickBundleBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.GRASS).noOcclusion()).setBurnable());
	public static final RegistryObject<Block>	MAPLE_RED_TRUNK								= DoTBBlocksRegistry.reg("maple_red_trunk", new MapleTrunkBlock(AbstractBlock.Properties.copy(Blocks.SPRUCE_LEAVES)));
	public static final RegistryObject<Block>	MAPLE_RED_LEAVES							= DoTBBlocksRegistry.reg("maple_red_leaves", new MapleLeavesBlock(AbstractBlock.Properties.copy(Blocks.SPRUCE_LEAVES)));
	public static final RegistryObject<Block>	MAPLE_RED_SAPLING							= DoTBBlocksRegistry.reg("maple_red_sapling", new MapleSaplingBlock(AbstractBlock.Properties.copy(Blocks.SPRUCE_LEAVES)));
	public static final RegistryObject<Block>	PAUSED_MAPLE_RED_SAPLING					= DoTBBlocksRegistry.reg("paused_maple_red_sapling", new PausedMapleSaplingBlock(AbstractBlock.Properties.copy(Blocks.SPRUCE_LEAVES)));

	//Persian
	public static final RegistryObject<Block>	PERSIAN_CARPET_RED							= DoTBBlocksRegistry.reg("persian_carpet_red", new CarpetBlockDoTB(AbstractBlock.Properties.copy(Blocks.RED_WOOL)));
	public static final RegistryObject<Block>	PERSIAN_CARPET_DELICATE_RED					= DoTBBlocksRegistry.reg("persian_carpet_delicate_red", new CarpetBlockDoTB(AbstractBlock.Properties.copy(Blocks.RED_WOOL)));
	public static final RegistryObject<Block>	MORAQ_MOSAIC_TILES_DELICATE					= DoTBBlocksRegistry.reg("moraq_mosaic_tiles_delicate", new BlockDoTB(AbstractBlock.Properties.copy(Blocks.BRICKS)));
	public static final RegistryObject<Block>	MORAQ_MOSAIC_TILES_TRADITIONAL				= DoTBBlocksRegistry.reg("moraq_mosaic_tiles_traditional", new BlockDoTB(AbstractBlock.Properties.copy(Blocks.BRICKS)));
	public static final RegistryObject<Block>	MORAQ_MOSAIC_TILES_BORDER					= DoTBBlocksRegistry.reg("moraq_mosaic_tiles_border", new BlockDoTB(AbstractBlock.Properties.copy(Blocks.BRICKS)));
	public static final RegistryObject<Block>	MORAQ_MOSAIC_RECESS							= DoTBBlocksRegistry.reg("moraq_mosaic_recess", new StairsBlockDoTB(DoTBBlocksRegistry.MORAQ_MOSAIC_TILES_DELICATE, AbstractBlock.Properties.copy(Blocks.BRICKS)));

	//Pre_columbian
	public static final RegistryObject<Block>	COMMELINA									= DoTBBlocksRegistry.reg("commelina", new SoilCropsBlock("commelina", PlantType.PLAINS));
	public static final RegistryObject<Block>	PLASTERED_STONE								= DoTBBlocksRegistry.reg("plastered_stone", new BlockDoTB(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	PLASTERED_STONE_EDGE						= DoTBBlocksRegistry.reg("plastered_stone_edge", new EdgeBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	PLASTERED_STONE_PLATE						= DoTBBlocksRegistry.reg("plastered_stone_plate", new PlateBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	PLASTERED_STONE_SLAB						= DoTBBlocksRegistry.reg("plastered_stone_slab", new SlabBlockDoTB(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	PLASTERED_STONE_STAIRS						= DoTBBlocksRegistry.reg("plastered_stone_stairs", new StairsBlockDoTB(DoTBBlocksRegistry.PLASTERED_STONE, AbstractBlock.Properties.copy(Blocks.BRICKS)));
	public static final RegistryObject<Block>	PLASTERED_STONE_WINDOW						= DoTBBlocksRegistry.reg("plastered_stone_window", new PlasteredStoneWindowBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS).noOcclusion()));
	public static final RegistryObject<Block>	CHISELED_PLASTERED_STONE					= DoTBBlocksRegistry.reg("chiseled_plastered_stone", new BlockDoTB(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	CHISELED_PLASTERED_STONE_FRIEZE				= DoTBBlocksRegistry.reg("chiseled_plastered_stone_frieze", new PlateBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	ORNAMENTED_CHISELED_PLASTERED_STONE			= DoTBBlocksRegistry.reg("ornamented_chiseled_plastered_stone", new BlockDoTB(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	RED_PLASTERED_STONE							= DoTBBlocksRegistry.reg("red_plastered_stone", new BlockDoTB(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	RED_CHISELED_PLASTERED_STONE				= DoTBBlocksRegistry.reg("red_chiseled_plastered_stone", new BlockDoTB(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	RED_ORNAMENTED_CHISELED_PLASTERED_STONE		= DoTBBlocksRegistry.reg("red_ornamented_chiseled_plastered_stone", new BlockDoTB(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	RED_PLASTERED_STONE_EDGE					= DoTBBlocksRegistry.reg("red_plastered_stone_edge", new EdgeBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	RED_PLASTERED_STONE_FRIEZE					= DoTBBlocksRegistry.reg("red_plastered_stone_frieze", new PlateBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	RED_PLASTERED_STONE_PLATE					= DoTBBlocksRegistry.reg("red_plastered_stone_plate", new PlateBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	RED_PLASTERED_STONE_SLAB					= DoTBBlocksRegistry.reg("red_plastered_stone_slab", new SlabBlockDoTB(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	RED_PLASTERED_STONE_STAIRS					= DoTBBlocksRegistry.reg("red_plastered_stone_stairs", new StairsBlockDoTB(DoTBBlocksRegistry.RED_PLASTERED_STONE, AbstractBlock.Properties.copy(Blocks.BRICKS)));
	public static final RegistryObject<Block>	RED_SMALL_PLASTERED_STONE_FRIEZE			= DoTBBlocksRegistry.reg("red_small_plastered_stone_frieze", new EdgeBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	RED_ORNAMENTED_PLASTERED_STONE_FRIEZE		= DoTBBlocksRegistry.reg("red_ornamented_plastered_stone_frieze", new PlateBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS).noOcclusion()));
	public static final RegistryObject<Block>	RED_SCULPTED_PLASTERED_STONE_FRIEZE			= DoTBBlocksRegistry.reg("red_sculpted_plastered_stone_frieze", new RedSculptedPlasteredStoneFriezeBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	GREEN_CHISELED_PLASTERED_STONE				= DoTBBlocksRegistry.reg("green_chiseled_plastered_stone", new BlockDoTB(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	GREEN_ORNAMENTED_CHISELED_PLASTERED_STONE	= DoTBBlocksRegistry.reg("green_ornamented_chiseled_plastered_stone", new BlockDoTB(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	GREEN_ORNAMENTED_PLASTERED_STONE_FRIEZE		= DoTBBlocksRegistry.reg("green_ornamented_plastered_stone_frieze", new PlateBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS).noOcclusion()));
	public static final RegistryObject<Block>	GREEN_PLASTERED_STONE_FRIEZE				= DoTBBlocksRegistry.reg("green_plastered_stone_frieze", new PlateBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS).noOcclusion()));
	public static final RegistryObject<Block>	GREEN_SCULPTED_PLASTERED_STONE_FRIEZE		= DoTBBlocksRegistry.reg("green_sculpted_plastered_stone_frieze", new GreenSculptedPlasteredStoneFriezeBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	GREEN_SMALL_PLASTERED_STONE_FRIEZE			= DoTBBlocksRegistry.reg("green_small_plastered_stone_frieze", new EdgeBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	WILD_MAIZE									= DoTBBlocksRegistry.reg("wild_maize", new WildMaizeBlock(AbstractBlock.Properties.copy(Blocks.DANDELION)));
	public static final RegistryObject<Block>	MAIZE										= DoTBBlocksRegistry.reg("maize", new DoubleCropsBlock("maize", PlantType.CROP, 4, DoTBFoods.MAIZE));
	public static final RegistryObject<Block>	RED_ORNAMENTED_PLASTERED_STONE				= DoTBBlocksRegistry.reg("red_ornamented_plastered_stone", new BlockDoTB(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	PLASTERED_STONE_COLUMN						= DoTBBlocksRegistry.reg("plastered_stone_column", new PlasteredStoneColumnBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));
	public static final RegistryObject<Block>	PLASTERED_STONE_CRESSET						= DoTBBlocksRegistry.reg("plastered_stone_cresset", new PlasteredStoneCressetBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS).noOcclusion().lightLevel(DoTBBlocksRegistry.litBlockEmission(15))));
	public static final RegistryObject<Block>	FEATHERED_SERPENT_SCULPTURE					= DoTBBlocksRegistry.reg("feathered_serpent_sculpture", new FeatheredSerpentSculptureBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS).noOcclusion()));
	public static final RegistryObject<Block>	SERPENT_SCULPTED_COLUMN						= DoTBBlocksRegistry.reg("serpent_sculpted_column", new SerpentSculptedColumnBlock(AbstractBlock.Properties.copy(Blocks.STONE_BRICKS)));

	//Roman
	public static final RegistryObject<Block>	SANDSTONE_PLATE								= DoTBBlocksRegistry.reg("sandstone_plate", new PlateBlock(AbstractBlock.Properties.copy(Blocks.SANDSTONE)));
	public static final RegistryObject<Block>	SANDSTONE_EDGE								= DoTBBlocksRegistry.reg("sandstone_edge", new EdgeBlock(AbstractBlock.Properties.copy(Blocks.SANDSTONE)));
	public static final RegistryObject<Block>	CUT_SANDSTONE_STAIRS						= DoTBBlocksRegistry.reg("cut_sandstone_stairs", new StairsBlock(Blocks.CUT_SANDSTONE::defaultBlockState, AbstractBlock.Properties.copy(Blocks.CUT_SANDSTONE)));
	public static final RegistryObject<Block>	CUT_SANDSTONE_PLATE							= DoTBBlocksRegistry.reg("cut_sandstone_plate", new PlateBlock(AbstractBlock.Properties.copy(Blocks.CUT_SANDSTONE)));
	public static final RegistryObject<Block>	CUT_SANDSTONE_EDGE							= DoTBBlocksRegistry.reg("cut_sandstone_edge", new EdgeBlock(AbstractBlock.Properties.copy(Blocks.CUT_SANDSTONE)));
	public static final RegistryObject<Block>	SMOOTH_SANDSTONE_PLATE						= DoTBBlocksRegistry.reg("smooth_sandstone_plate", new PlateBlock(AbstractBlock.Properties.copy(Blocks.SMOOTH_SANDSTONE)));
	public static final RegistryObject<Block>	SMOOTH_SANDSTONE_EDGE						= DoTBBlocksRegistry.reg("smooth_sandstone_edge", new EdgeBlock(AbstractBlock.Properties.copy(Blocks.SMOOTH_SANDSTONE)));
	public static final RegistryObject<Block>	OCHRE_ROOF_TILES							= DoTBBlocksRegistry.reg("ochre_roof_tiles", new BlockDoTB(AbstractBlock.Properties.copy(Blocks.BRICKS)));
	public static final RegistryObject<Block>	OCHRE_ROOF_TILES_EDGE						= DoTBBlocksRegistry.reg("ochre_roof_tiles_edge", new EdgeBlock(AbstractBlock.Properties.copy(Blocks.BRICKS)));
	public static final RegistryObject<Block>	OCHRE_ROOF_TILES_PLATE						= DoTBBlocksRegistry.reg("ochre_roof_tiles_plate", new PlateBlock(AbstractBlock.Properties.copy(Blocks.BRICKS)));
	public static final RegistryObject<Block>	OCHRE_ROOF_TILES_STAIRS						= DoTBBlocksRegistry.reg("ochre_roof_tiles_stairs", new StairsBlockDoTB(DoTBBlocksRegistry.OCHRE_ROOF_TILES, AbstractBlock.Properties.copy(Blocks.BRICKS)));
	public static final RegistryObject<Block>	OCHRE_ROOF_TILES_WALL						= DoTBBlocksRegistry.reg("ochre_roof_tiles_wall", new WallBlock(AbstractBlock.Properties.copy(Blocks.BRICKS)));
	public static final RegistryObject<Block>	SANDSTONE_BOT_OCHRE_ROOF_TILES_TOP			= DoTBBlocksRegistry.reg("sandstone_bot_ochre_roof_tiles_top", new NoItemBlock(AbstractBlock.Properties.copy(Blocks.BRICKS)));
	public static final RegistryObject<Block>	CUT_SANDSTONE_BOT_OCHRE_ROOF_TILES_TOP		= DoTBBlocksRegistry.reg("cut_sandstone_bot_ochre_roof_tiles_top", new NoItemBlock(AbstractBlock.Properties.copy(Blocks.BRICKS)));
	public static final RegistryObject<Block>	SMOOTH_SANDSTONE_BOT_OCHRE_ROOF_TILES_TOP	= DoTBBlocksRegistry.reg("smooth_sandstone_bot_ochre_roof_tiles_top", new NoItemBlock(AbstractBlock.Properties.copy(Blocks.BRICKS)));
	public static final RegistryObject<Block>	OCHRE_ROOF_TILES_SLAB						= DoTBBlocksRegistry.reg("ochre_roof_tiles_slab",
			new MixedSlabBlock(AbstractBlock.Properties.copy(Blocks.BRICKS)).addMixedBlockRecipe(Blocks.SANDSTONE_SLAB, DoTBBlocksRegistry.SANDSTONE_BOT_OCHRE_ROOF_TILES_TOP, false).addMixedBlockRecipe(Blocks.CUT_SANDSTONE_SLAB, DoTBBlocksRegistry.CUT_SANDSTONE_BOT_OCHRE_ROOF_TILES_TOP, false).addMixedBlockRecipe(Blocks.SMOOTH_SANDSTONE_SLAB, DoTBBlocksRegistry.SMOOTH_SANDSTONE_BOT_OCHRE_ROOF_TILES_TOP, false));
	public static final RegistryObject<Block>	SANDSTONE_COLUMN							= DoTBBlocksRegistry.reg("sandstone_column", new SandstoneColumnBlock(AbstractBlock.Properties.copy(Blocks.SANDSTONE)));
	public static final RegistryObject<Block>	COVERED_SANDSTONE_WALL						= DoTBBlocksRegistry.reg("covered_sandstone_wall", new CappedWallBlock(AbstractBlock.Properties.copy(Blocks.SANDSTONE)));
	public static final RegistryObject<Block>	MOSAIC_FLOOR								= DoTBBlocksRegistry.reg("mosaic_floor", new BlockDoTB(AbstractBlock.Properties.copy(Blocks.SANDSTONE)));
	public static final RegistryObject<Block>	MOSAIC_FLOOR_DELICATE						= DoTBBlocksRegistry.reg("mosaic_floor_delicate", new BlockDoTB(AbstractBlock.Properties.copy(Blocks.SANDSTONE)));
	public static final RegistryObject<Block>	MOSAIC_FLOOR_ROSETTE						= DoTBBlocksRegistry.reg("mosaic_floor_rosette", new BlockDoTB(AbstractBlock.Properties.copy(Blocks.SANDSTONE)));
	public static final RegistryObject<Block>	BIRCH_FOOTSTOOL								= DoTBBlocksRegistry.reg("birch_footstool", new BirchFootstoolBlock(AbstractBlock.Properties.copy(Blocks.BIRCH_PLANKS), 9.0F));
	public static final RegistryObject<Block>	BIRCH_COUCH									= DoTBBlocksRegistry.reg("birch_couch", new BirchCouchBlock(AbstractBlock.Properties.copy(Blocks.BIRCH_PLANKS), 13.0F));
	public static final RegistryObject<Block>	MARBLE_STATUE_MARS							= DoTBBlocksRegistry.reg("marble_statue_mars", new MarbleStatueBlock(AbstractBlock.Properties.copy(Blocks.SANDSTONE).noOcclusion()));
	public static final RegistryObject<Block>	WILD_GRAPE									= DoTBBlocksRegistry.reg("wild_grape", new WildPlantBlock(AbstractBlock.Properties.copy(Blocks.DANDELION)));
	public static final RegistryObject<Block>	CYPRESS										= DoTBBlocksRegistry.reg("cypress", new CypressBlock(AbstractBlock.Properties.copy(Blocks.SPRUCE_LEAVES)).setBurnable());
	public static final RegistryObject<Block>	BIG_FLOWER_POT								= DoTBBlocksRegistry.reg("big_flower_pot", new BigFlowerPotBlock(AbstractBlock.Properties.copy(Blocks.CLAY)));
	public static final RegistryObject<Block>	MARBLE_BIG_FLOWER_POT						= DoTBBlocksRegistry.reg("marble_big_flower_pot", new MarbleBigFlowerPotBlock(AbstractBlock.Properties.copy(Blocks.SANDSTONE)));

	private static RegistryObject<Block> reg(final String name, final Block block) {
		Item	item;
		String	itemName	= null;
		if (block instanceof ICustomBlockItem) {
			item		= ((ICustomBlockItem) block).getCustomBlockItem();
			itemName	= ((ICustomBlockItem) block).getCustomItemName();
		}
		else {
			item = new BlockItem(block, new Item.Properties().tab(DawnOfTimeBuilder.DOTB_TAB));
		}
		if (item != null) {
			if (item instanceof IHasFlowerPot) {
				final IHasFlowerPot itemForPot = (IHasFlowerPot) item;
				if (itemForPot.hasFlowerPot()) {
					final String				potName		= name + "_flower_pot";
					final FlowerPotBlockDoTB	potBlock	= itemForPot.makeFlowerPotInstance(item);
					itemForPot.setPotBlock(potBlock);
					DoTBBlocksRegistry.finalReg(potName, potBlock);
					DoTBItemsRegistry.finalReg(potName, new BlockItem(potBlock, new Item.Properties().tab(DawnOfTimeBuilder.DOTB_TAB)));
					DoTBBlocksRegistry.POT_BLOCKS.put(potName, potBlock);
				}
			}
			DoTBItemsRegistry.finalReg(itemName == null ? name : itemName, item);
		}
		return DoTBBlocksRegistry.finalReg(name, block);
	}

	public static RegistryObject<Block> finalReg(final String name, final Block block) {
		return DoTBBlocksRegistry.BLOCKS.register(name, () -> block);
	}

	private static ToIntFunction<BlockState> litBlockEmission(final int lightValue) {
		return state -> state.getValue(BlockStateProperties.LIT) ? lightValue : 0;
	}

	@SuppressWarnings("unused")
	private static Boolean ocelotOrParrot(final BlockState p_235441_0_, final IBlockReader p_235441_1_, final BlockPos p_235441_2_, final EntityType<?> p_235441_3_) {
		return p_235441_3_ == EntityType.OCELOT || p_235441_3_ == EntityType.PARROT;
	}

	@SuppressWarnings("unused")
	private static boolean never(final BlockState p_235436_0_, final IBlockReader p_235436_1_, final BlockPos p_235436_2_) {
		return false;
	}
}