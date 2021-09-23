package org.dawnoftimebuilder.generation.features;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import org.dawnoftimebuilder.block.templates.SoilCropsBlock;
import org.dawnoftimebuilder.block.templates.WaterDoubleCropsBlock;
import org.dawnoftimebuilder.block.templates.WaterloggedBlock;
import org.dawnoftimebuilder.registry.DoTBBlocksRegistry;
import org.dawnoftimebuilder.DoTBConfig;

import java.util.Random;
import java.util.function.Function;

public class RiceFeature extends Feature<NoFeatureConfig> {

    public RiceFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configIn) {
        super(configIn);
    }

    /**
     * Places a rice plant in the world in a similar fashion to melons.
     */
    @Override
    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        boolean success = false;
        for (int i = 0; i < DoTBConfig.RICE_ROLLS.get(); ++i) {
            BlockPos nextPos = getRandomPos(pos, rand, DoTBConfig.RICE_SPAWN_WIDTH.get(), DoTBConfig.RICE_SPAWN_HIGH.get());
            if (isValidPosition(worldIn, nextPos)) {
                success = true;
                WaterDoubleCropsBlock rice = (WaterDoubleCropsBlock) DoTBBlocksRegistry.RICE;
                BlockState baseState = rice.getDefaultState()
                        .with(rice.getAgeProperty(), rand.nextInt(rice.getMaxAge() - rice.getAgeReachingTopBlock() + 1) + rice.getAgeReachingTopBlock())
                        .with(WaterloggedBlock.WATERLOGGED, true);
                worldIn.setBlockState(nextPos, baseState, 2);
                worldIn.setBlockState(nextPos.up(), rice.getTopState(baseState), 2);
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
     * Determines if the given position is valid for a rice plant.
     */
    private boolean isValidPosition(IWorld worldIn, BlockPos pos) {
        return ((SoilCropsBlock) DoTBBlocksRegistry.RICE).isValidGround(DoTBBlocksRegistry.RICE.getDefaultState(), worldIn, pos.down());
    }
}
