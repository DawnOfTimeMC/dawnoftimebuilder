package org.dawnoftimebuilder.generation.features;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import org.dawnoftimebuilder.block.templates.WildPlantBlock;
import org.dawnoftimebuilder.registry.DoTBBlocksRegistry;
import org.dawnoftimebuilder.DoTBConfig;

import java.util.Random;
import java.util.function.Function;

public class WildGrapeFeature extends Feature<NoFeatureConfig> {

    public WildGrapeFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configIn) {
        super(configIn);
    }

    @Override
    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        boolean success = false;
        for (int i = 0; i < DoTBConfig.WILD_GRAPE_ROLLS.get(); ++i) {
            BlockPos nextPos = getRandomPos(pos, rand, DoTBConfig.WILD_GRAPE_SPAWN_WIDTH.get(), DoTBConfig.WILD_GRAPE_SPAWN_HIGH.get());
            WildPlantBlock plant = (WildPlantBlock) DoTBBlocksRegistry.WILD_GRAPE;
            if (isValidPosition(worldIn, nextPos)) {
                success = true;
                worldIn.setBlockState(nextPos, plant.getDefaultState(), 2);
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

    private boolean isValidPosition(IWorld worldIn, BlockPos pos) {
        WildPlantBlock plant = (WildPlantBlock) DoTBBlocksRegistry.WILD_GRAPE;
        return worldIn.isAirBlock(pos) && plant.isValidPosition(plant.getDefaultState(), worldIn, pos);
    }
}
