package org.dawnoftimebuilder.registries;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import org.dawnoftimebuilder.blocks.IBlockCustomItem;
import org.dawnoftimebuilder.blocks.general.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;

public class DoTBBlocksRegistry {

	public static List<Block> blocks_list = new ArrayList<>();
	private static void addToList(Block... blocks){
		Collections.addAll(blocks_list, blocks);
	}

	public static void init(){
		DoTBBlock thatch_bamboo = new DoTBBlock("thatch_bamboo", Material.WOOL, 1.0F, 1.0F);
		DoTBBlock thatch_wheat = new DoTBBlock("thatch_wheat", Material.WOOL, 1.0F, 1.0F);
		DoTBBlock flat_roof_tiles = new DoTBBlock("flat_roof_tiles", Material.ROCK,1.5F, 5.0F);
		DoTBBlock limestone_brick = new DoTBBlock("limestone_brick", Material.ROCK,1.5F, 6.0F);
		DoTBBlock oak_waxed_planks = new DoTBBlock("oak_waxed_planks", Material.WOOD, 2.0F, 3.0F);
		DoTBBlock grey_roof_tiles = new DoTBBlock("grey_roof_tiles", Material.ROCK,1.5F, 5.0F);
		DoTBBlock plastered_stone = new DoTBBlock("plastered_stone", Material.ROCK,1.5F, 6.0F);
		DoTBBlock red_plastered_stone = new DoTBBlock("red_plastered_stone", Material.ROCK,1.5F, 6.0F);

		addToList(
				//General
				new IronChainBlock(),
	/*			new DoTBBlockPath("path_gravel"),
				new DoTBBlockPath("path_stepping_stones"),
				new DoTBBlockPath("path_cobbled"),
				new DoTBBlockPath("path_ochre_tiles"),
				new DoTBBlockPath("path_dirt"),
				new DoTBBlockSlabPath("path_gravel_slab"),
				new DoTBBlockSlabPath("path_stepping_stones_slab"),
				new DoTBBlockSlabPath("path_cobbled_slab"),
				new DoTBBlockSlabPath("path_ochre_tiles_slab"),
				new DoTBBlockSlabPath("path_dirt_slab"),
	*/			new DoTBBlock("rammed_dirt", Material.EARTH, 0.7F, 0.7F),
				thatch_wheat.setBurnable(),
				new DoTBBlockStairs("thatch_wheat_stairs", thatch_wheat).setBurnable(),
				new DoTBBlockSlab("thatch_wheat_slab", thatch_wheat).setBurnable(),
				new DoTBBlockEdge("thatch_wheat_edge", thatch_wheat).setBurnable(),
				thatch_bamboo.setBurnable(),
				new DoTBBlockStairs("thatch_bamboo_stairs", thatch_bamboo).setBurnable(),
				new DoTBBlockSlab("thatch_bamboo_slab", thatch_bamboo).setBurnable(),
				new DoTBBlockEdge("thatch_bamboo_edge", thatch_bamboo).setBurnable(),
				new FireplaceBlock(),

				//French
				new DoTBBlock("cobbled_limestone", Material.ROCK, 2.0F, 6.0F),
				flat_roof_tiles,
				new DoTBBlockStairs("flat_roof_tiles_stairs", flat_roof_tiles),
				new DoTBBlockSlab("flat_roof_tiles_slab", flat_roof_tiles),
				new DoTBBlockEdge("flat_roof_tiles_edge", flat_roof_tiles),
				new DoTBBlock("framed_rammed_dirt", Material.WOOD, 2.0F, 3.0F).setBurnable(),
	/*			new DoTBBlockPortcullis("iron_portcullis", Material.IRON),
				new DoTBBlockGlassBlock("lattice_glass"),
				new BlockLatticeGlassPane(),
				new BlockLatticeOakWindow(),*/
				limestone_brick,
				new DoTBBlockStairs("limestone_brick_stairs", limestone_brick),
				new DoTBBlockSlab("limestone_brick_slab", limestone_brick),
				new DoTBBlockEdge("limestone_brick_edge", limestone_brick),
				new DoTBBlockWall("limestone_brick_wall", Material.ROCK, 1.5F, 6.0F),
	/*			new BlockLimestoneChimney(),
				new BlockLimestoneFireplace(),
	*/			new DoTBBlockEdge("oak_planks_edge", Material.WOOD, 2.0F, 3.0F),
	//			new BlockOakShutters(),
				new DoTBBlockBeam("oak_beam", Material.WOOD, 2.0F, 3.0F).setBurnable(),
				new DoTBBlockSupportBeam("oak_support_beam", Material.WOOD, 2.0F, 3.0F).setBurnable(),
				new DoTBBlockSupportSlab("oak_support_slab", Material.WOOD, 1.5F, 3.0F).setBurnable(),
		/*		new BlockSmallOakShutters(),
		*/		new DoTBBlock("oak_timber_frame", Material.WOOD, 3.0F, 5.0F).setBurnable(),
		/*		new DoTBBlockRotatedPillar("oak_timber_frame_corner", Material.WOOD,2.0F,SoundType.WOOD).setBurnable(),
				new DoTBBlockRotatedPillar("oak_timber_frame_pillar", Material.WOOD,2.0F,SoundType.WOOD).setBurnable(),
		*/		new DoTBBlockFence("oak_waxed_fence", Material.WOOD, 2.0F, 3.0F).setBurnable(),
				oak_waxed_planks.setBurnable(),
				new DoTBBlockStairs("oak_waxed_planks_stairs", oak_waxed_planks),
				new DoTBBlockSlab("oak_waxed_planks_slab", oak_waxed_planks),
				new DoTBBlockEdge("oak_waxed_planks_edge", oak_waxed_planks),

				//Japanese
		/*		new DoTBBlockDryer("bamboo_drying_tray", Material.WOOD, 1.0F, SoundType.WOOD),
				new BlockCamellia(),
				new BlockMulberry(),
				new BlockCastIronTeapot(),
				new BlockCastIronTeacup(),
				new BlockFloweryPaperWall(),
				new BlockPaperLamp(),*/
				grey_roof_tiles,
				new DoTBBlockStairs("grey_roof_tiles_stairs", grey_roof_tiles),
				new DoTBBlockSlab("grey_roof_tiles_slab", grey_roof_tiles),
				new DoTBBlockEdge("grey_roof_tiles_edge", grey_roof_tiles),
				new DoTBBlockWall("grey_roof_tiles_wall", Material.ROCK,1.5F, 5.0F),
		/*		new BlockIkebanaFlowerPot(),
				new BlockSpruceLeglessChair(),
				new BlockLittleFlag(),
				new BlockPaperDoor(),
				new BlockPaperLantern(),
				new BlockPaperWall("paper_wall"),
				new BlockPaperWall("paper_wall_flat"),
				new DoTBBlockPane("paper_wall_window", Material.CLOTH).setBurnable(),
				new BlockPaperFoldingScreen(),
				new BlockRedPaintedLog(),
				new BlockRice(),
				new BlockSmallTatamiMat(),
				new DoTBBlockRotatedPillar("spruce_log_covered", Material.WOOD,2.0F,SoundType.WOOD).setBurnable(),
			*/	new DoTBBlockBeam("burnt_spruce_beam", Material.WOOD, 2.5F, 5.0F).setBurnable(),
				new DoTBBlock("burnt_spruce_foundation", Material.WOOD, 2.5F, 5.0F).setBurnable(),
				new DoTBBlockFence("burnt_spruce_fence", Material.WOOD, 2.5F, 5.0F).setBurnable(),
				new DoTBBlockWall("burnt_spruce_wall", Material.WOOD, 2.5F, 5.0F).setBurnable(),
				new DoTBBlockEdge("spruce_planks_edge", Material.WOOD, 2.0F, 3.0F),
			/*	new BlockSpruceRailing(),
				new BlockSpruceRoofSupport("spruce_roof_support"),
				new BlockSpruceRoofSupportMerged(),
			*/	new DoTBBlockSupportBeam("burnt_spruce_support_beam", Material.WOOD, 2.5F, 5.0F).setBurnable(),
				new DoTBBlockSupportSlab("spruce_support_slab", Material.WOOD, 1.5F, 3.0F).setBurnable(),
			/*	new BlockSpruceLowTable(),
			*/	new DoTBBlock("burnt_spruce_timber_frame", Material.WOOD, 3.0F, 6.0F).setBurnable(),
			/*	new DoTBBlockRotatedPillar("spruce_timber_frame_pillar", Material.WOOD, 2.0F, SoundType.WOOD).setBurnable(),
				new BlockStoneLantern(),
				new BlockTatamiMat("tatami_mat"),
				new BlockFuton(),
				new BlockSmallTatamiFloor(),
				new BlockTatamiFloor(),
				new BlockIrori(),
				new BlockSakeBottle(),
				new BlockSakeCup(),
		*/		new DoTBBlockSlab("spruce_foundation_slab", Material.WOOD, 2.5F, 5.0F),
		/*		new BlockStickBundle(),

				//pre_colombian
				new BlockChiseledPlasteredStone(),
				new BlockCommelina(),
				new BlockFeatheredSerpentSculpture(),
		*/		plastered_stone,
				red_plastered_stone,
				new DoTBBlockPlate("green_ornamented_plastered_stone_frieze", plastered_stone),
				new DoTBBlockPlate("green_plastered_stone_frieze", plastered_stone),
		//		new BlockGreenSculptedPlasteredStoneFrieze(),
				new DoTBBlockEdge("green_small_plastered_stone_frieze", plastered_stone),
		//		new BlockMaize(),
				new DoTBBlock("red_ornamented_plastered_stone", Material.ROCK,1.5F, 6.0F),
		//		new BlockPlasteredStoneColumn(),
		//		new BlockPlasteredStoneCresset(),
				new DoTBBlockPlate("plastered_stone_frieze", plastered_stone),
				new DoTBBlockPlate("plastered_stone_plate", plastered_stone),
				new DoTBBlockStairs("plastered_stone_stairs", plastered_stone),
				new DoTBBlockSlab("plastered_stone_slab", plastered_stone),
				new DoTBBlockEdge("plastered_stone_edge", plastered_stone),
		//		new BlockPlasteredStoneWindow(),
				new DoTBBlockPlate("red_ornamented_plastered_stone_frieze", red_plastered_stone),
				new DoTBBlockPlate("red_plastered_stone_frieze", red_plastered_stone),
				new DoTBBlockPlate("red_plastered_stone_plate", red_plastered_stone),
				new DoTBBlockStairs("red_plastered_stone_stairs", red_plastered_stone),
				new DoTBBlockSlab("red_plastered_stone_slab", red_plastered_stone),
				new DoTBBlockEdge("red_plastered_stone_edge", red_plastered_stone),
		//		new BlockRedSculptedPlasteredStoneFrieze(),
				new DoTBBlockEdge("red_small_plastered_stone_frieze", red_plastered_stone),
		//		new BlockSerpentSculptedColumn(),
		//		new BlockStoneFrieze(),

				//roman
				new DoTBBlock("ochre_roof_tiles", Material.ROCK, 1.5F, 5.0F)/*,
				new BlockOchreRoofTilesMerged(),
				new BlockOchreRoofTilesSlab(),
				new BlockSandstoneColumn()*/
		);
	}

	public static void registerBlocks(RegistryEvent.Register<Block> event) {

		init();

		IForgeRegistry<Block> registry = event.getRegistry();
		for(Block block : blocks_list){
			registry.register(block);

			Item item = null;
			if(block instanceof IBlockCustomItem){
				item = ((IBlockCustomItem)block).getCustomItemBlock();
			}else{
				ResourceLocation name = block.getRegistryName();
				if(name != null) item = new BlockItem(block, new Item.Properties().group(DOTB_TAB)).setRegistryName(block.getRegistryName());
			}

			if(item != null) DoTBItemsRegistry.items_list.add(item);
		}
	}
}