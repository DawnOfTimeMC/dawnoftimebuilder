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
	/*			new DoTBBlockStairs("thatch_wheat_stairs", thatch_wheat, 0.3F, SoundType.CLOTH).setBurnable(),
				new DoTBBlockSlab("thatch_wheat_slab", Material.CLOTH, 0.3F, SoundType.CLOTH).setBurnable(),
				new DoTBBlockEdge("thatch_wheat_edge", Material.CLOTH, 0.3F, SoundType.CLOTH).setBurnable(),
	*/			thatch_bamboo.setBurnable(),
	/*			new DoTBBlockStairs("thatch_bamboo_stairs", thatch_bamboo, 0.3F, SoundType.CLOTH).setBurnable(),
				new DoTBBlockSlab("thatch_bamboo_slab", Material.CLOTH, 0.3F, SoundType.CLOTH).setBurnable(),
				new DoTBBlockEdge("thatch_bamboo_edge", Material.CLOTH, 0.3F, SoundType.CLOTH).setBurnable(),*/
				new FireplaceBlock(),

				//French
				new DoTBBlock("cobbled_limestone", Material.ROCK, 2.0F, 6.0F),
				flat_roof_tiles,
	/*			new DoTBBlockStairs("flat_roof_tiles_stairs", flat_roof_tiles, 2.0F, SoundType.STONE),
				new DoTBBlockSlab("flat_roof_tiles_slab", Material.ROCK, 2.0F, SoundType.STONE),
				new DoTBBlockEdge("flat_roof_tiles_edge", Material.ROCK, 1.5F, SoundType.STONE),*/
				new DoTBBlock("framed_rammed_dirt", Material.WOOD, 2.0F, 3.0F).setBurnable(),
	/*			new DoTBBlockPortcullis("iron_portcullis", Material.IRON),
				new DoTBBlockGlassBlock("lattice_glass"),
				new BlockLatticeGlassPane(),
				new BlockLatticeOakWindow(),*/
				limestone_brick,
	/*			new DoTBBlockStairs("limestone_brick_stairs", limestone_brick, 2.0F, SoundType.STONE),
				new DoTBBlockSlab("limestone_brick_slab", Material.ROCK, 2.0F, SoundType.STONE),
				new DoTBBlockEdge("limestone_brick_edge", Material.ROCK, 2.0F, SoundType.STONE),
				new DoTBBlockWall("limestone_brick_wall", Material.ROCK, 2.0F, SoundType.STONE),
				new BlockLimestoneChimney(),
				new BlockLimestoneFireplace(),
				new DoTBBlockEdge("oak_planks_edge", Material.WOOD, 1.5F, SoundType.WOOD),
				new BlockOakShutters(),
		*/		new DoTBBlockBeam("oak_beam", Material.WOOD, 2.0F, 3.0F).setBurnable(),
				new DoTBBlockSupportBeam("oak_support_beam", Material.WOOD, 2.0F, 3.0F).setBurnable(),
		/*		new DoTBBlockSupportSlab("oak_support_slab", Material.WOOD, 1.0F, SoundType.WOOD).setBurnable(),
				new BlockSmallOakShutters(),
		*/		new DoTBBlock("oak_timber_frame", Material.WOOD, 3.0F, 5.0F).setBurnable(),
		/*		new DoTBBlockRotatedPillar("oak_timber_frame_corner", Material.WOOD,2.0F,SoundType.WOOD).setBurnable(),
				new DoTBBlockRotatedPillar("oak_timber_frame_pillar", Material.WOOD,2.0F,SoundType.WOOD).setBurnable(),
				new DoTBBlockFence("oak_waxed_fence", Material.WOOD).setBurnable(),
		*/		oak_waxed_planks.setBurnable(),
		/*		new DoTBBlockStairs("oak_waxed_planks_stairs", oak_waxed_planks, 1.5F, SoundType.WOOD),
				new DoTBBlockSlab("oak_waxed_planks_slab", Material.WOOD, 1.5F, SoundType.WOOD),
				new DoTBBlockEdge("oak_waxed_planks_edge", Material.WOOD, 1.5F, SoundType.WOOD),

				//Japanese
				new DoTBBlockDryer("bamboo_drying_tray", Material.WOOD, 1.0F, SoundType.WOOD),
				new BlockCamellia(),
				new BlockMulberry(),
				new BlockCastIronTeapot(),
				new BlockCastIronTeacup(),
				new BlockFloweryPaperWall(),
				new BlockPaperLamp(),*/
				grey_roof_tiles,
		/*		new DoTBBlockStairs("grey_roof_tiles_stairs", grey_roof_tiles, 2.0F, SoundType.STONE),
				new DoTBBlockSlab("grey_roof_tiles_slab", Material.ROCK, 2.0F, SoundType.STONE),
				new DoTBBlockEdge("grey_roof_tiles_edge", Material.ROCK, 1.5F, SoundType.STONE),
				new DoTBBlockWall("grey_roof_tiles_wall", Material.ROCK, 2.0F, SoundType.STONE),
				new BlockIkebanaFlowerPot(),
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
			*/	new DoTBBlockBeam("spruce_beam", Material.WOOD, 2.0F, 3.0F).setBurnable(),
				new DoTBBlock("spruce_foundation", Material.WOOD, 2.5F, 5.0F).setBurnable(),
			/*	new DoTBBlockFence("spruce_log_fence", Material.WOOD, 2.0F, SoundType.WOOD).setBurnable(),
				new DoTBBlockWall("spruce_log_wall", Material.WOOD, 2.0F, SoundType.WOOD).setBurnable(),
				new DoTBBlockEdge("spruce_planks_edge", Material.WOOD, 1.5F, SoundType.WOOD),
				new BlockSpruceRailing(),
				new BlockSpruceRoofSupport("spruce_roof_support"),
				new BlockSpruceRoofSupportMerged(),
			*/	new DoTBBlockSupportBeam("spruce_support_beam", Material.WOOD, 2.0F, 3.0F).setBurnable(),
			/*	new DoTBBlockSupportSlab("spruce_support_slab", Material.WOOD, 1.0F, SoundType.WOOD).setBurnable(),
				new BlockSpruceLowTable(),
			*/	new DoTBBlock("spruce_timber_frame", Material.WOOD, 2.0F, 3.0F).setBurnable(),
			/*	new DoTBBlockRotatedPillar("spruce_timber_frame_pillar", Material.WOOD, 2.0F, SoundType.WOOD).setBurnable(),
				new BlockStoneLantern(),
				new BlockTatamiMat("tatami_mat"),
				new BlockFuton(),
				new BlockSmallTatamiFloor(),
				new BlockTatamiFloor(),
				new BlockIrori(),
				new BlockSakeBottle(),
				new BlockSakeCup(),
				new DoTBBlockSlab("spruce_foundation_slab", Material.WOOD, 1.0F, SoundType.WOOD),
				new BlockStickBundle(),

				//pre_colombian
				new BlockChiseledPlasteredStone(),
				new BlockCommelina(),
				new BlockFeatheredSerpentSculpture(),
				new DoTBBlockPlate("green_ornamented_plastered_stone_frieze"),
				new DoTBBlockPlate("green_plastered_stone_frieze"),
				new BlockGreenSculptedPlasteredStoneFrieze(),
				new DoTBBlockEdge("green_small_plastered_stone_frieze", Material.ROCK, 1.5F, SoundType.STONE),
				new BlockMaize(),
			*/	plastered_stone,
				red_plastered_stone,
				new DoTBBlock("red_ornamented_plastered_stone", Material.ROCK,1.5F, 6.0F),
			/*	new BlockPlasteredStoneColumn(),
				new BlockPlasteredStoneCresset(),
				new DoTBBlockPlate("plastered_stone_frieze"),
				new DoTBBlockPlate("plastered_stone_plate"),
				new DoTBBlockStairs("plastered_stone_stairs", plastered_stone, 2.0F, SoundType.STONE),
				new DoTBBlockSlab("plastered_stone_slab", Material.ROCK, 2.0F, SoundType.STONE),
				new DoTBBlockEdge("plastered_stone_edge", Material.ROCK, 1.5F, SoundType.STONE),
				new BlockPlasteredStoneWindow(),
				new DoTBBlockPlate("red_ornamented_plastered_stone_frieze"),
				new DoTBBlockPlate("red_plastered_stone_frieze"),
				new DoTBBlockPlate("red_plastered_stone_plate"),
				new DoTBBlockStairs("red_plastered_stone_stairs", red_plastered_stone, 2.0F, SoundType.STONE),
				new DoTBBlockSlab("red_plastered_stone_slab", Material.ROCK, 2.0F, SoundType.STONE),
				new DoTBBlockEdge("red_plastered_stone_edge", Material.ROCK, 1.5F, SoundType.STONE),
				new BlockRedSculptedPlasteredStoneFrieze(),
				new DoTBBlockEdge("red_small_plastered_stone_frieze", Material.ROCK, 1.5F, SoundType.STONE),
				new BlockSerpentSculptedColumn(),
				new BlockStoneFrieze(),

				//roman
			*/	new DoTBBlock("ochre_roof_tiles", Material.ROCK, 1.5F, 5.0F)/*,
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