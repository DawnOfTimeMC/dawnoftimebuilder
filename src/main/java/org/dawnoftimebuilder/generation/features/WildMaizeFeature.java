package org.dawnoftimebuilder.generation.features;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import org.dawnoftimebuilder.block.precolumbian.WildMaizeBlock;
import org.dawnoftimebuilder.registry.DoTBBlocksRegistry;
import org.dawnoftimebuilder.DoTBConfig;

import java.util.Random;
import java.util.function.Function;

public class WildMaizeFeature extends Feature<NoFeatureConfig> {

    public WildMaizeFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configIn) {
        super(configIn);
    }

    /**
     * Places wild maize in a random pattern around a point.
     */
    @Override
    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        boolean success = false;
        for (int i = 0; i < DoTBConfig.WILD_MAIZE_ROLLS.get(); ++i) {
            // get next random position.
            BlockPos nextPos = getRandomPos(pos, rand, DoTBConfig.WILD_MAIZE_SPAWN_WIDTH.get(), DoTBConfig.WILD_MAIZE_SPAWN_HIGH.get());
            if (isValidPosition(worldIn, nextPos)) {
                success = true;
                // the block states to be placed
                WildMaizeBlock maize = (WildMaizeBlock) DoTBBlocksRegistry.WILD_MAIZE;
                // set the block states
                worldIn.setBlockState(nextPos, maize.defaultBlockState(), 2);
                worldIn.setBlockState(nextPos.up(), maize.getDefaultTopState(), 2);
            }
        }
        return success;
    }

    private BlockPos getRandomPos(BlockPos pos, Random rand, int width, int high){
        return pos.add(
                rand.nextInt(width) - rand.nextInt(width),
                rand.nextInt(high) - rand.nextInt(high),
                rand.nextInt(width) - rand.nextInt(width));
    }

    /**
     * Determines if the given position is valid for a mulberry bush.
     */
    private boolean isValidPosition(IWorld worldIn, BlockPos pos) {
        WildMaizeBlock plant = (WildMaizeBlock) DoTBBlocksRegistry.WILD_MAIZE;
        return worldIn.isAirBlock(pos) && worldIn.isAirBlock(pos.up()) && plant.isValidPosition(plant.defaultBlockState(), worldIn, pos);
    }
}
