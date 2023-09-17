package org.dawnoftimebuilder.util;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import org.dawnoftimebuilder.DawnOfTimeBuilder;
import org.dawnoftimebuilder.block.templates.WaterloggedBlock;
import javax.annotation.Nonnull;
import java.util.List;

public class DoTBUtils {

    //General
    public static final int HIGHEST_Y = 255;

    //Tooltip translation text
    public static final Component TOOLTIP_HOLD_SHIFT = Component.translatable("tooltip." +
            DawnOfTimeBuilder.MOD_ID + ".hold_key").withStyle(ChatFormatting.GRAY).append(Component.translatable("tooltip." +
            DawnOfTimeBuilder.MOD_ID + ".shift").withStyle(ChatFormatting.AQUA));
    public static final String TOOLTIP_COLUMN = "column";
    public static final String TOOLTIP_CLIMBING_PLANT = "climbing_plant";
    public static final String TOOLTIP_BEAM = "beam";
    public static final String TOOLTIP_CROP = "crop";
    public static final String TOOLTIP_SIDED_WINDOW = "sided_window";
    public static final String TOOLTIP_FIREPLACE = "fireplace";
    public static final String TOOLTIP_ADD_COLUMN = "add_column";

    //Item tags
    public static final TagKey<Item> LIGHTERS = ItemTags.create(new ResourceLocation(DawnOfTimeBuilder.MOD_ID, "lighters"));

    //Block tags
    public static final TagKey<Block> COVERED_BLOCKS = BlockTags.create(new ResourceLocation(DawnOfTimeBuilder.MOD_ID, "covered_blocks"));

    /**
     * Fills a table with VS rotated in each horizontal directions following the horizontal index order :<p/>
     * south - west - north - east
     *
     * @param shapes Contains the VoxelShapes oriented toward south.
     * @return A table filled with the previous VS and new ones rotated in each 3 horizontal directions.
     */
    public static VoxelShape[] GenerateHorizontalShapes(final VoxelShape[] shapes) {
        final VoxelShape[] newShape = {
                Shapes.empty()
        };
        final VoxelShape[] newShapes = new VoxelShape[shapes.length * 4];
        int i = 0;
        for (final VoxelShape shape : shapes) {
            newShapes[i] = shape;
            i++;
        }
        for (int rotation = 1; rotation < 4; rotation++) {
            int j = 0;
            for (final VoxelShape shape : shapes) {
                shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> newShape[0] = Shapes.or(newShape[0], Shapes.box(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)));
                shapes[j] = newShape[0];
                newShapes[i] = newShape[0];
                newShape[0] = Shapes.empty();
                i++;
                j++;
            }
        }
        return newShapes;
    }

    /**
     * @param serverWorld   World can be cast to ServerWorld.
     * @param stateIn       Current state of the Block.
     * @param itemStackHand ItemStack in player's hand, allow tools conditions.
     * @param name          Used to define the name of the LootTable.
     * @return the List of ItemStack found in the corresponding LootTable.
     */
    public static List<ItemStack> getLootList(final ServerLevel serverWorld, final BlockState stateIn, final ItemStack itemStackHand, final String name) {
        final LootTable table = serverWorld.getServer().getLootTables().get(new ResourceLocation(DawnOfTimeBuilder.MOD_ID + ":blocks/" + name));
        final LootContext.Builder builder = new LootContext.Builder(serverWorld).withRandom(serverWorld.random).withParameter(LootContextParams.BLOCK_STATE, stateIn).withParameter(LootContextParams.TOOL, itemStackHand).withParameter(LootContextParams.ORIGIN, new Vec3(0, 0, 0));
        final LootContext lootcontext = builder.create(LootContextParamSets.BLOCK);
        return table.getRandomItems(lootcontext);
    }

    /**
     * Drops each item in the List of ItemStack one by one.
     *
     * @param worldIn    World of the Block.
     * @param pos        Position of the Block.
     * @param drops      ItemStack list that will be dropped.
     * @param multiplier Multiply the quantity of item (round down) per ItemStack (use 1.0F to keep the same number).
     * @return True if some items are dropped, False otherwise.
     */
    public static boolean dropLootFromList(final LevelAccessor worldIn, final BlockPos pos, final List<ItemStack> drops, final float multiplier) {
        if (drops.isEmpty() || !(worldIn instanceof Level)) {
            return false;
        }
        for (final ItemStack drop : drops) {
            final int quantity = (int) Math.floor(drop.getCount() * multiplier);
            for (int i = 0; i < quantity; i++) {
                Block.popResource((Level) worldIn, pos, new ItemStack(drop.getItem(), 1));
            }
        }
        return true;
    }

    /**
     * Checks if the player can light the block. If yes, damages the item used and display the sound.
     *
     * @param worldIn World of the Block.
     * @param pos     Position of the Block.
     * @param player  Player that clicks on the Block.
     * @param handIn  Player's hand.
     * @return True if the block is now in fire. False otherwise.
     */
    public static boolean useLighter(final Level worldIn, final BlockPos pos, final Player player, final InteractionHand handIn) {
        final ItemStack itemInHand = player.getItemInHand(handIn);
        if (!itemInHand.isEmpty() && itemInHand.is(DoTBUtils.LIGHTERS)) {
            worldIn.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
            itemInHand.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(handIn));
            return true;
        }
        return false;
    }

    /**
     * Checks if the player can light the block. If yes, damages the item is player is not in creative mod used and display the sound.
     *
     * @param worldIn World of the Block.
     * @param pos     Position of the Block.
     * @param player  Player that clicks on the Block.
     * @param handIn  Player's hand.
     * @return True if the block is now in fire. False otherwise.
     */
    public static boolean useLighterAndDamageItemIfPlayerIsNotCreative(final Level worldIn, final BlockPos pos, final Player player, final InteractionHand handIn) {
        final ItemStack itemInHand = player.getItemInHand(handIn);
        if (!itemInHand.isEmpty() && itemInHand.is(DoTBUtils.LIGHTERS)) {
            worldIn.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!player.isCreative())
                itemInHand.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(handIn));
            return true;
        }
        return false;
    }

    public static boolean useFireActivatorOnBlockIfPossible(final BlockState blockstateIn, final Level worldIn, final BlockPos pos, final Player player, final InteractionHand handIn) {
        if (blockstateIn.getValue(WaterloggedBlock.WATERLOGGED)) {
            return false;
        }
        final ItemStack itemStackInHand = player.getItemInHand(handIn);
        if (!itemStackInHand.isEmpty()) {
            final Item itemInHand = itemStackInHand.getItem();
            if (itemInHand instanceof FireChargeItem) {
                if (!player.isCreative()) {
                    player.getMainHandItem().shrink(1);
                }

                return true;
            } else if (itemStackInHand.is(DoTBUtils.LIGHTERS)) {
                worldIn.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!player.isCreative()) {
                    itemStackInHand.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(handIn));
                }
                return true;
            }
        }

        return false;
    }

    public static boolean useFireStopperIfPossible(final BlockState blockstateIn, final Level worldIn, final BlockPos pos, final Player player, final InteractionHand handIn) {
        final ItemStack mainItemStack = player.getMainHandItem();
        if (player.isCreative()) {
            return true;
        }
        if (mainItemStack.isEmpty())
            return false;
        if (mainItemStack.is(DoTBUtils.LIGHTERS)) {
            worldIn.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!player.isCreative()) {
                mainItemStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(handIn));
            }
            return true;
        }
        if (mainItemStack.getItem() instanceof PotionItem && !(mainItemStack.getItem() instanceof SplashPotionItem)) {
            final Potion potion = PotionUtils.getPotion(mainItemStack);

            if (potion != null && potion.getEffects().size() <= 0) {
                player.getMainHandItem().shrink(1);
                player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));

                return true;
            }
        } else if (mainItemStack.getItem() instanceof SnowballItem) {
            player.getMainHandItem().shrink(1);

            return true;
        }

        return false;
    }

    public static int changeBlockLitStateWithItemOrCreativePlayer(final BlockState stateIn, final Level worldIn, final BlockPos pos, final Player player, final InteractionHand handIn) {
        int activation = -1;

        if (stateIn.getValue(BlockStateProperties.LIT) && DoTBUtils.useFireStopperIfPossible(stateIn, worldIn, pos, player, handIn)) {
            activation = 0;
        } else if (!stateIn.getValue(BlockStateProperties.LIT) && DoTBUtils.useFireActivatorOnBlockIfPossible(stateIn, worldIn, pos, player, handIn)) {
            activation = 1;
        }

        if (activation >= 0) {
            final boolean isActivated = activation == 1;
            worldIn.setBlock(pos, stateIn.setValue(BlockStateProperties.LIT, isActivated), 10);
            worldIn.playSound(null, pos, isActivated ? SoundEvents.FIRE_AMBIENT : SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (worldIn.isClientSide) {
                if (!isActivated) {
                    for (int i = 0; i < worldIn.random.nextInt(4) + 2; ++i) {
                        worldIn.addParticle(ParticleTypes.CLOUD, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, worldIn.random.nextFloat() / 4.0F, 2.5E-5D, worldIn.random.nextFloat() / 4.0F);
                    }
                } else {
                    for (int i = 0; i < worldIn.random.nextInt(4) + 2; ++i) {
                        worldIn.addParticle(ParticleTypes.LARGE_SMOKE, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, worldIn.random.nextFloat() / 4.0F, 2.5E-5D, worldIn.random.nextFloat() / 4.0F);
                    }
                }
            }
        }

        return activation;
    }

    public static void addTooltip(final List<Component> tooltip, @Nonnull final Item item, final String... tooltipNames) {
        final ResourceLocation itemName = ForgeRegistries.ITEMS.getKey(item);
        if (itemName != null) {
            String[] tts = new String[tooltipNames.length + 1];
            System.arraycopy(tooltipNames, 0, tts, 0, tooltipNames.length);
            tts[tooltipNames.length] = itemName.getPath();
            DoTBUtils.addTooltip(tooltip, tts);
        }
    }

    public static void addTooltip(final List<Component> tooltip, @Nonnull final Block block, final String... tooltipNames) {
        final ResourceLocation itemName = ForgeRegistries.BLOCKS.getKey(block);
        if (itemName != null) {
            String[] tts = new String[tooltipNames.length + 1];
            System.arraycopy(tooltipNames, 0, tts, 0, tooltipNames.length);
            tts[tooltipNames.length] = itemName.getPath();
            DoTBUtils.addTooltip(tooltip, tts);
        }
    }

    public static void addTooltip(final List<Component> tooltip, final String... tooltipNames) {
        if (Screen.hasShiftDown()) {
            for (final String tooltipName : tooltipNames) {
                tooltip.add(Component.translatable("tooltip." + DawnOfTimeBuilder.MOD_ID + "." + tooltipName).withStyle(ChatFormatting.GRAY));
            }
        } else {
            tooltip.add(DoTBUtils.TOOLTIP_HOLD_SHIFT);
        }
    }
}