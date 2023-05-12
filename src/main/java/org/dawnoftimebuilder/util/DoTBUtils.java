package org.dawnoftimebuilder.util;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.dawnoftimebuilder.DawnOfTimeBuilder;
import org.dawnoftimebuilder.block.templates.WaterloggedBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FireChargeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.item.SnowballItem;
import net.minecraft.item.SplashPotionItem;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags;

public class DoTBUtils {

	//General
	public static final int						HIGHEST_Y				= 255;

	//Tooltip translation text
	public static final ITextComponent			TOOLTIP_HOLD_SHIFT		= new TranslationTextComponent("tooltip." + DawnOfTimeBuilder.MOD_ID + ".hold_key").withStyle(TextFormatting.GRAY).append(new TranslationTextComponent("tooltip." + DawnOfTimeBuilder.MOD_ID + ".shift").withStyle(TextFormatting.AQUA));
	public static final String					TOOLTIP_COLUMN			= "column";
	public static final String					TOOLTIP_CLIMBING_PLANT	= "climbing_plant";
	public static final String					TOOLTIP_BEAM			= "beam";
	public static final String					TOOLTIP_CROP			= "crop";
	public static final String					TOOLTIP_SIDED_WINDOW	= "sided_window";
	public static final String 					TOOLTIP_FIREPLACE 		= "fireplace";

	//Item tags
	public static final Tags.IOptionalNamedTag<Item>	LIGHTERS				= ItemTags.createOptional(new ResourceLocation(DawnOfTimeBuilder.MOD_ID, "lighters"));

	//Block tags
	public static final Tags.IOptionalNamedTag<Block>	COVERED_BLOCKS			= BlockTags.createOptional(new ResourceLocation(DawnOfTimeBuilder.MOD_ID, "covered_blocks"));

	/** Fills a table with VS rotated in each horizontal directions following the horizontal index order :<p/>
	 * south - west - north - east
	 *
	 * @param shapes Contains the VoxelShapes oriented toward south.
	 * @return A table filled with the previous VS and new ones rotated in each 3 horizontal directions.
	 */
	public static VoxelShape[] GenerateHorizontalShapes(final VoxelShape[] shapes) {
		final VoxelShape[]	newShape	= {
				VoxelShapes.empty()
		};
		final VoxelShape[]	newShapes	= new VoxelShape[shapes.length * 4];
		int					i			= 0;
		for (final VoxelShape shape : shapes) {
			newShapes[i] = shape;
			i++;
		}
		for (int rotation = 1; rotation < 4; rotation++) {
			int j = 0;
			for (final VoxelShape shape : shapes) {
				shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> newShape[0] = VoxelShapes.or(newShape[0], VoxelShapes.box(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)));
				shapes[j]		= newShape[0];
				newShapes[i]	= newShape[0];
				newShape[0]		= VoxelShapes.empty();
				i++;
				j++;
			}
		}
		return newShapes;
	}

	/**
	 * @param serverWorld World can be cast to ServerWorld.
	 * @param stateIn Current state of the Block.
	 * @param itemStackHand ItemStack in player's hand, allow tools conditions.
	 * @param name Used to define the name of the LootTable.
	 * @return the List of ItemStack found in the corresponding LootTable.
	 */
	public static List<ItemStack> getLootList(final ServerWorld serverWorld, final BlockState stateIn, final ItemStack itemStackHand, final String name) {
		final LootTable				table		= serverWorld.getServer().getLootTables().get(new ResourceLocation(DawnOfTimeBuilder.MOD_ID + ":blocks/" + name));
		final LootContext.Builder	builder		= new LootContext.Builder(serverWorld).withRandom(serverWorld.random).withParameter(LootParameters.BLOCK_STATE, stateIn).withParameter(LootParameters.TOOL, itemStackHand).withParameter(LootParameters.ORIGIN, new Vector3d(0, 0, 0));
		final LootContext			lootcontext	= builder.create(LootParameterSets.BLOCK);
		return table.getRandomItems(lootcontext);
	}

	/**
	 * Drops each item in the List of ItemStack one by one.
	 * @param worldIn World of the Block.
	 * @param pos Position of the Block.
	 * @param drops ItemStack list that will be dropped.
	 * @param multiplier Multiply the quantity of item (round down) per ItemStack (use 1.0F to keep the same number).
	 * @return True if some items are dropped, False otherwise.
	 */
	public static boolean dropLootFromList(final IWorld worldIn, final BlockPos pos, final List<ItemStack> drops, final float multiplier) {
		if (drops.isEmpty() || !(worldIn instanceof World)) {
			return false;
		}
		for (final ItemStack drop : drops) {
			final int quantity = (int) Math.floor(drop.getCount() * multiplier);
			for (int i = 0; i < quantity; i++) {
				Block.popResource((World) worldIn, pos, new ItemStack(drop.getItem(), 1));
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
	public static boolean useLighter(final World worldIn, final BlockPos pos, final PlayerEntity player, final Hand handIn) {
		final ItemStack itemInHand = player.getItemInHand(handIn);
		if (!itemInHand.isEmpty() && itemInHand.getItem().is(DoTBUtils.LIGHTERS)) {
			worldIn.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
			itemInHand.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(handIn));
			return true;
		}
		return false;
	}

	/**
	 * Checks if the player can light the block. If yes, damages the item is player is not in creative mod used and display the sound.
	 * @param worldIn World of the Block.
	 * @param pos Position of the Block.
	 * @param player Player that clicks on the Block.
	 * @param handIn Player's hand.
	 * @return True if the block is now in fire. False otherwise.
	 */
	public static boolean useLighterAndDamageItemIfPlayerIsNotCreative(final World worldIn, final BlockPos pos, final PlayerEntity player, final Hand handIn) {
		final ItemStack itemInHand = player.getItemInHand(handIn);
		if (!itemInHand.isEmpty() && itemInHand.getItem().is(DoTBUtils.LIGHTERS)) {
			worldIn.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
			if(!player.isCreative())
				itemInHand.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(handIn));
			return true;
		}
		return false;
	}

	public static boolean useFireActivatorOnBlockIfPossible(final BlockState blockstateIn, final World worldIn, final BlockPos pos, final PlayerEntity player, final Hand handIn) {
		if (blockstateIn.getValue(WaterloggedBlock.WATERLOGGED)) {
			return false;
		}
		final ItemStack itemStackInHand = player.getItemInHand(handIn);
		if(!itemStackInHand.isEmpty())
		{
			final Item itemInHand = itemStackInHand.getItem();
			if (itemInHand instanceof FireChargeItem)
			{
				if (!player.isCreative()) {
					player.getMainHandItem().shrink(1);
				}

				return true;
			}
			else if (itemInHand.getItem().is(DoTBUtils.LIGHTERS))
			{
				worldIn.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
				if(!player.isCreative())
				{
					itemStackInHand.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(handIn));
				}
				return true;
			}
		}

		return false;
	}

	public static boolean useFireStopperIfPossible(final BlockState blockstateIn, final World worldIn, final BlockPos pos, final PlayerEntity player, final Hand handIn) {
		final ItemStack mainItemStack = player.getMainHandItem();
		if (player.isCreative()) {
			return true;
		}
		if(mainItemStack.isEmpty())
			return false;
		if (mainItemStack.getItem().is(DoTBUtils.LIGHTERS))
		{
			worldIn.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
			if(!player.isCreative())
			{
				mainItemStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(handIn));
			}
			return true;
		}
		if (mainItemStack.getItem() instanceof PotionItem && !(mainItemStack.getItem() instanceof SplashPotionItem)) {
			final Potion potion = PotionUtils.getPotion(mainItemStack);

			if (potion != null && potion.getEffects().size() <= 0) {
				player.getMainHandItem().shrink(1);
				player.inventory.add(new ItemStack(Items.GLASS_BOTTLE));

				return true;
			}
		}
		else if (mainItemStack.getItem() instanceof SnowballItem) {
			player.getMainHandItem().shrink(1);

			return true;
		}

		return false;
	}

	public static int changeBlockLitStateWithItemOrCreativePlayer(final BlockState stateIn, final World worldIn, final BlockPos pos, final PlayerEntity player, final Hand handIn) {
		int activation = -1;

		if (stateIn.getValue(BlockStateProperties.LIT) && DoTBUtils.useFireStopperIfPossible(stateIn, worldIn, pos, player, handIn)) {
			activation = 0;
		}
		else if (!stateIn.getValue(BlockStateProperties.LIT) && DoTBUtils.useFireActivatorOnBlockIfPossible(stateIn, worldIn, pos, player, handIn)) {
			activation = 1;
		}

		if (activation >= 0) {
			final boolean isActivated = activation == 1;
			worldIn.setBlock(pos, stateIn.setValue(BlockStateProperties.LIT, isActivated), 10);
			worldIn.playSound(null, pos, isActivated ? SoundEvents.FIRE_AMBIENT : SoundEvents.FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
			if (worldIn.isClientSide) {
				if (!isActivated) {
					for (int i = 0; i < worldIn.random.nextInt(4) + 2; ++i) {
						worldIn.addParticle(ParticleTypes.CLOUD, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, worldIn.random.nextFloat() / 4.0F, 2.5E-5D, worldIn.random.nextFloat() / 4.0F);
					}
				}
				else {
					for (int i = 0; i < worldIn.random.nextInt(4) + 2; ++i) {
						worldIn.addParticle(ParticleTypes.LARGE_SMOKE, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, worldIn.random.nextFloat() / 4.0F, 2.5E-5D, worldIn.random.nextFloat() / 4.0F);
					}
				}
			}
		}

		return activation;
	}

	public static void addTooltip(final List<ITextComponent> tooltip, @Nonnull final Item item, final String... tooltipNames) {
		final ResourceLocation itemName = item.getRegistryName();
		if (itemName != null){
			String[] tts = new String[tooltipNames.length + 1];
			System.arraycopy(tooltipNames, 0, tts, 0, tooltipNames.length);
			tts[tooltipNames.length] = itemName.getPath();
			DoTBUtils.addTooltip(tooltip, tts);
		}
	}

	public static void addTooltip(final List<ITextComponent> tooltip, @Nonnull final Block block, final String... tooltipNames) {
		final ResourceLocation itemName = block.getRegistryName();
		if (itemName != null){
			String[] tts = new String[tooltipNames.length + 1];
			System.arraycopy(tooltipNames, 0, tts, 0, tooltipNames.length);
			tts[tooltipNames.length] = itemName.getPath();
			DoTBUtils.addTooltip(tooltip, tts);
		}
	}

	public static void addTooltip(final List<ITextComponent> tooltip, final String... tooltipNames) {
		if (Screen.hasShiftDown()) {
			for (final String tooltipName : tooltipNames) {
				tooltip.add(new TranslationTextComponent("tooltip." + DawnOfTimeBuilder.MOD_ID + "." + tooltipName).withStyle(TextFormatting.GRAY));
			}
		} else {
			tooltip.add(DoTBUtils.TOOLTIP_HOLD_SHIFT);
		}
	}
}