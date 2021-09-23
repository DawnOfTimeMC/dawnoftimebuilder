package org.dawnoftimebuilder.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameterSets;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.LootTable;

import java.util.List;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.MOD_ID;

public class DoTBBlockUtils {

	//BlockPos
	public static final int HIGHEST_Y = 255;

	//Tooltip translation text
	public static final ITextComponent TOOLTIP_HOLD_SHIFT = new TranslationTextComponent("tooltip." + MOD_ID + ".hold_key").applyTextStyle(TextFormatting.GRAY).appendSibling(new TranslationTextComponent("tooltip." + MOD_ID + ".shift").applyTextStyle(TextFormatting.AQUA));
	public static final String TOOLTIP_COLUMN = "column";
	public static final String TOOLTIP_CLIMBING_PLANT = "climbing_plant";
	public static final String TOOLTIP_CROP = "crop";
	public static final String TOOLTIP_SIDED_WINDOW = "sided_window";

	//Item tags
	public static final Tag<Item> SHEARS = new ItemTags.Wrapper(new ResourceLocation(MOD_ID, "shears"));
	public static final Tag<Item> LIGHTERS = new ItemTags.Wrapper(new ResourceLocation(MOD_ID, "lighters"));

	//Block tags
	public static final Tag<Block> COVERED_BLOCKS = new BlockTags.Wrapper(new ResourceLocation(MOD_ID, "covered_blocks"));

	/** Fills a table with VS rotated in each horizontal directions following the horizontal index order :<p/>
	 * south - west - north - east
	 *
	 * @param shapes Contains the VoxelShapes oriented toward south.
	 * @return A table filled with the previous VS and new ones rotated in each 3 horizontal directions.
	 */
	public static VoxelShape[] GenerateHorizontalShapes(VoxelShape[] shapes){
		VoxelShape[] newShape = {VoxelShapes.empty()};
		VoxelShape[] newShapes = new VoxelShape[shapes.length * 4];
		int i = 0;
		for(VoxelShape shape : shapes){
			newShapes[i] = shape;
			i++;
		}
		for(int rotation = 1; rotation < 4; rotation++){
			int j = 0;
			for(VoxelShape shape : shapes){
				shape.forEachBox((minX, minY, minZ, maxX, maxY, maxZ) -> newShape[0] = VoxelShapes.or(newShape[0], VoxelShapes.create(1-maxZ, minY, minX, 1-minZ, maxY, maxX)));
				shapes[j] = newShape[0];
				newShapes[i] = newShape[0];
				newShape[0] = VoxelShapes.empty();
				i++;
				j++;
			}
		}
		return newShapes;
	}

	/**
	 * @param serverWorld World can be cast to ServerWorld.
	 * @param stateIn Current state of the Block.
	 * @param pos Position of the Block.
	 * @param itemStackHand ItemStack in player's hand, allow tools conditions.
	 * @param name Used to define the name of the LootTable.
	 * @return the List of ItemStack found in the corresponding LootTable.
	 */
	public static List<ItemStack> getLootList(ServerWorld serverWorld, BlockState stateIn, BlockPos pos, ItemStack itemStackHand, String name){
		LootTable table = serverWorld.getServer().getLootTableManager().getLootTableFromLocation(new ResourceLocation(MOD_ID + ":blocks/" + name));
		LootContext.Builder builder = (new LootContext.Builder(serverWorld))
				.withRandom(serverWorld.rand)
				.withParameter(LootParameters.BLOCK_STATE, stateIn)
				.withParameter(LootParameters.POSITION, pos)
				.withParameter(LootParameters.TOOL, itemStackHand);
		LootContext lootcontext = builder.build(LootParameterSets.BLOCK);
		return table.generate(lootcontext);
	}

	/**
	 * Drops each item in the List of ItemStack one by one.
	 * @param worldIn World of the Block.
	 * @param pos Position of the Block.
	 * @param drops ItemStack list that will be dropped.
	 * @param multiplier Multiply the quantity of item (round down) per ItemStack (use 1.0F to keep the same number).
	 * @return True if some items are dropped, False otherwise.
	 */
	public static boolean dropLootFromList(World worldIn, BlockPos pos, List<ItemStack> drops, float multiplier){
		if(drops.isEmpty()) return false;
		for(ItemStack drop : drops){
			int quantity = (int) Math.floor(drop.getCount() * multiplier);
			for (int i = 0; i < quantity; i++){
				Block.spawnAsEntity(worldIn, pos, new ItemStack(drop.getItem(), 1));
			}
		}
		return true;
	}

	/**
	 * Checks if the player can light the block. If yes, damages the item used and display the sound.
	 * @param worldIn World of the Block.
	 * @param pos Position of the Block.
	 * @param player Player that clicks on the Block.
	 * @param handIn Player's hand.
	 * @return True if the block is now in fire. False otherwise.
	 */
	public static boolean useLighter(World worldIn, BlockPos pos, PlayerEntity player, Hand handIn){
		ItemStack itemInHand = player.getHeldItem(handIn);
		if (!itemInHand.isEmpty() && itemInHand.getItem().isIn(LIGHTERS)) {
			worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F);
			itemInHand.damageItem(1, player, (p) -> p.sendBreakAnimation(handIn));
			return true;
		}
		return false;
	}

	public static void addTooltip(List<ITextComponent> tooltip, String name){
		if(Screen.hasShiftDown()) tooltip.add(new TranslationTextComponent("tooltip." + MOD_ID + "." + name).applyTextStyle(TextFormatting.GRAY));
		else tooltip.add(TOOLTIP_HOLD_SHIFT);
	}
	
	public static void addTooltip(List<ITextComponent> tooltip, Block block){
		ResourceLocation name = block.getRegistryName();
		if(name != null){
			if(Screen.hasShiftDown()) tooltip.add(new TranslationTextComponent("tooltip." + MOD_ID + "." + name.getPath()).applyTextStyle(TextFormatting.GRAY));
			else tooltip.add(TOOLTIP_HOLD_SHIFT);
		}
	}
}
