package org.dawnoftime.dawnoftime.util;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.DoTBCommon;
import org.dawnoftime.dawnoftime.block.templates.WaterloggedBlock;
import org.dawnoftime.dawnoftime.registry.DoTBTags;

import javax.annotation.Nonnull;
import java.util.List;

public class Utils {
    //General
    public static final int HIGHEST_Y = 255;
    //Tooltip translation text
    public static final Component TOOLTIP_HOLD_SHIFT = Component.translatable("tooltip." +
            DoTBCommon.MOD_ID + ".hold_key").withStyle(ChatFormatting.GRAY).append(Component.translatable("tooltip." +
            DoTBCommon.MOD_ID + ".shift").withStyle(ChatFormatting.AQUA));
    public static final String TOOLTIP_COLUMN = "column";
    public static final String TOOLTIP_CLIMBING_PLANT = "climbing_plant";
    public static final String TOOLTIP_BEAM = "beam";
    public static final String TOOLTIP_CROP = "crop";
    public static final String TOOLTIP_FIREPLACE = "fireplace";
    public static final String TOOLTIP_ADD_COLUMN = "add_column";

    /**
     * Fills a table with VS rotated in each horizontal directions following the horizontal index order :<p/>
     * south - west - north - east
     *
     * @param shapes           Contains the VoxelShapes oriented toward south.
     * @param nonRotatedShapes Facultative list of shapes that must no be rotated (will be at the end of the array, starting at index
     *                         shapes#length * 4).
     * @return A table filled with the previous VS and new ones rotated in each 3 horizontal directions.
     */
    public static VoxelShape[] generateHorizontalShapes(final VoxelShape[] shapes, VoxelShape... nonRotatedShapes) {
        final VoxelShape[] newShape = {Shapes.empty()};
        final VoxelShape[] newShapes = new VoxelShape[shapes.length * 4 + nonRotatedShapes.length];
        int i = 0;
        // First we copy the provided array at the start of the new one.
        for (final VoxelShape shape : shapes) {
            newShapes[i] = shape;
            i++;
        }
        // Then rotate the provided array in each direction, and add it the new array.
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
        // Lastly, we add the non-rotated shapes at the end of the array.
        for (final VoxelShape shape : nonRotatedShapes) {
            newShapes[i] = shape;
            i++;
        }
        return newShapes;
    }

    /**
     * First, keeps only the part of the testedShape that is on the tested face of the block (within faceShape).
     * Then, compares this shapeOnFace to inShape : if the shapeOnFace has a part outside the inShape, return false.
     *
     * @param testedShape Shape that will be tested.
     * @param faceShape   Shape that correspond the full face of the block tested for the direction.
     * @param inShape     Shape to which the testedShape will be compared.
     * @return False is the testedShape has a part that is not within the inShape, true otherwise.
     */
    public static boolean isShapeIncludedInShape(VoxelShape testedShape, VoxelShape faceShape, VoxelShape inShape) {
        VoxelShape shapeOnFace = Shapes.join(testedShape, faceShape, BooleanOp.AND);
        return !Shapes.joinIsNotEmpty(shapeOnFace, inShape, BooleanOp.ONLY_FIRST);
    }

    public static Rotation invertCounterClockWise(Rotation rot) {
        return switch (rot) {
            case NONE -> Rotation.NONE;
            case CLOCKWISE_90 -> Rotation.COUNTERCLOCKWISE_90;
            case CLOCKWISE_180 -> Rotation.CLOCKWISE_180;
            case COUNTERCLOCKWISE_90 -> Rotation.CLOCKWISE_90;
        };
    }

    /**
     * @param serverWorld   World can be cast to ServerWorld.
     * @param stateIn       Current state of the Block.
     * @param itemStackHand ItemStack in player's hand, allow tools conditions.
     * @param name          Used to define the name of the LootTable.
     * @return the List of ItemStack found in the corresponding LootTable.
     */
    public static List<ItemStack> getLootList(final ServerLevel serverWorld, final BlockState stateIn, final ItemStack itemStackHand, final String name) {
        final LootTable table = serverWorld.getServer().getLootData().getLootTable(new ResourceLocation(DoTBCommon.MOD_ID + ":blocks/" + name));
        final LootParams.Builder builder = new LootParams.Builder(serverWorld).withParameter(LootContextParams.BLOCK_STATE, stateIn).withParameter(LootContextParams.TOOL, itemStackHand).withParameter(LootContextParams.ORIGIN, new Vec3(0, 0, 0));
        final LootParams lootParams = builder.create(LootContextParamSets.BLOCK);
        return table.getRandomItems(lootParams);
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
     * Function that checks if the clickLocation is located on the lef half of a block.
     *
     * @param clickedPos    of the target block.
     * @param dir           Direction of the player.
     * @param clickLocation Vec3 of the clickLocation.
     * @return True if the clickLocation is on the left, false otherwise.
     */
    public static boolean clickedOnLeftHalf(BlockPos clickedPos, Direction dir, Vec3 clickLocation) {
        int dirStepX = dir.getStepX();
        int dirStepZ = dir.getStepZ();
        double diffX = clickLocation.x - (double) clickedPos.getX();
        double diffZ = clickLocation.z - (double) clickedPos.getZ();
        return (dirStepX >= 0 || !(diffZ < 0.5D)) && (dirStepX <= 0 || !(diffZ > 0.5D)) && (dirStepZ >= 0 || !(diffX > 0.5D)) && (dirStepZ <= 0 || !(diffX < 0.5D));
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
        if (!itemInHand.isEmpty() && itemInHand.is(DoTBTags.INSTANCE.LIGHTERS)) {
            worldIn.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
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
            } else if (itemStackInHand.is(DoTBTags.INSTANCE.LIGHTERS)) {
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
        if (mainItemStack.is(DoTBTags.INSTANCE.LIGHTERS)) {
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

        if (stateIn.getValue(BlockStateProperties.LIT) && Utils.useFireStopperIfPossible(stateIn, worldIn, pos, player, handIn)) {
            activation = 0;
        } else if (!stateIn.getValue(BlockStateProperties.LIT) && Utils.useFireActivatorOnBlockIfPossible(stateIn, worldIn, pos, player, handIn)) {
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
        final ResourceLocation itemName = item.builtInRegistryHolder().key().location();
        if (itemName != null) {
            String[] tts = new String[tooltipNames.length + 1];
            System.arraycopy(tooltipNames, 0, tts, 0, tooltipNames.length);
            tts[tooltipNames.length] = itemName.getPath();
            Utils.addTooltip(tooltip, tts);
        }
    }

    public static void addTooltip(final List<Component> tooltip, @Nonnull final Block block, final String... tooltipNames) {
        final ResourceLocation itemName = block.builtInRegistryHolder().key().location();
        if (itemName != null) {
            String[] tts = new String[tooltipNames.length + 1];
            System.arraycopy(tooltipNames, 0, tts, 0, tooltipNames.length);
            tts[tooltipNames.length] = itemName.getPath();
            Utils.addTooltip(tooltip, tts);
        }
    }

    public static void addTooltip(final List<Component> tooltip, final String... tooltipNames) {
        if (Screen.hasShiftDown()) {
            for (final String tooltipName : tooltipNames) {
                tooltip.add(Component.translatable("tooltip." + DoTBCommon.MOD_ID + "." + tooltipName).withStyle(ChatFormatting.GRAY));
            }
        } else {
            tooltip.add(Utils.TOOLTIP_HOLD_SHIFT);
        }
    }
}