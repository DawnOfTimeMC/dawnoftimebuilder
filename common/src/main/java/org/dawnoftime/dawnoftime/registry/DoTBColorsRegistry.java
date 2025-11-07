package org.dawnoftime.dawnoftime.registry;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Supplier;

public class DoTBColorsRegistry {
    private static final Map<BlockColor, List<Supplier<Block>>> BLOCKS_COLOR_REGISTRY = new HashMap<>();

    public static final BlockColor WATER_BLOCK_COLOR = DoTBColorsRegistry.register((blockStateIn, blockDisplayReaderIn, blockPosIn, tintIndexIn) -> BiomeColors.getAverageWaterColor(blockDisplayReaderIn, blockPosIn),
            DoTBBlocksRegistry.INSTANCE.STONE_BRICKS_FAUCET,
            DoTBBlocksRegistry.INSTANCE.STONE_BRICKS_POOL,
            DoTBBlocksRegistry.INSTANCE.STONE_BRICKS_SMALL_POOL,
            DoTBBlocksRegistry.INSTANCE.WATER_FLOWING_TRICKLE,
            DoTBBlocksRegistry.INSTANCE.WATER_SOURCE_TRICKLE,
            DoTBBlocksRegistry.INSTANCE.STONE_BRICKS_WATER_JET
    );

    public static Map<BlockColor, List<Supplier<Block>>> getBlocksColorRegistry() {
        return BLOCKS_COLOR_REGISTRY;
    }

    @SafeVarargs
    private static BlockColor register(final BlockColor blockColorIn, final Supplier<Block>... blocksIn) {
        List<Supplier<Block>> blocks = DoTBColorsRegistry.getBlocks(blockColorIn);
        if (blocks == null) {
            blocks = new ArrayList<>();
            DoTBColorsRegistry.BLOCKS_COLOR_REGISTRY.put(blockColorIn, blocks);
        }
        Collections.addAll(blocks, blocksIn);
        return blockColorIn;
    }

    private static List<Supplier<Block>> getBlocks(final BlockColor blockColorIn) {
        for (final Entry<BlockColor, List<Supplier<Block>>> entry : DoTBColorsRegistry.BLOCKS_COLOR_REGISTRY.entrySet()) {
            if (entry.getKey().getClass() == blockColorIn.getClass()) {
                return entry.getValue();
            }
        }
        return null;
    }

    public static void initialize() {}

    public static class BiomeItemTintSource implements ItemTintSource {
        public static final MapCodec<BiomeItemTintSource> MAP_CODEC = MapCodec.unit(new BiomeItemTintSource());
        private int lastColor = -12618012;

        @Override
        public int calculate(@NotNull ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity) {
            if (clientLevel == null || livingEntity == null) return lastColor;
            lastColor = BiomeColors.getAverageWaterColor(clientLevel, livingEntity.blockPosition()) | 0xFF000000;
            return lastColor;
        }

        @Override
        public @NotNull MapCodec<? extends ItemTintSource> type() {
            return MAP_CODEC;
        }
    }
}