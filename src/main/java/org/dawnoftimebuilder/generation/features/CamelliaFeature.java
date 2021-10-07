package org.dawnoftimebuilder.generation.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.DecoratedFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.dawnoftimebuilder.DoTBConfig;
import org.dawnoftimebuilder.block.templates.GrowingBushBlock;
import org.dawnoftimebuilder.block.templates.SoilCropsBlock;
import org.dawnoftimebuilder.registry.DoTBBlocksRegistry;

import java.util.Random;

public class CamelliaFeature extends Feature<DecoratedFeatureConfig> {

    public CamelliaFeature(Codec<DecoratedFeatureConfig> codec) {
        super(codec);
    }


    @Override
    public boolean place(ISeedReader level, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DecoratedFeatureConfig config) {
        /*
        boolean success = false;
        for (int i = 0; i < DoTBConfig.CAMELLIA_ROLLS.get(); ++i) {
            BlockPos nextPos = getRandomPos(pos, random, DoTBConfig.CAMELLIA_SPAWN_WIDTH.get(), DoTBConfig.CAMELLIA_SPAWN_HIGH.get());
            GrowingBushBlock camellia = (GrowingBushBlock) DoTBBlocksRegistry.CAMELLIA.get();
            BlockState bushState = camellia.defaultBlockState().setValue(camellia.getAgeProperty(), random.nextInt(camellia.getMaxAge() + 1));
            if (isValidPosition(level, nextPos)) {
                success = true;
                level.setBlock(nextPos, bushState, 2);
            }
        }
        return success;
        */

        MutableBoolean mutableboolean = new MutableBoolean();
        config.decorator.getPositions(new WorldDecoratingHelper(level, chunkGenerator), random, pos).forEach((testPos) -> {
            if (config.feature.get().place(level, chunkGenerator, random, testPos)) {
                mutableboolean.setTrue();
            }

        });
        return mutableboolean.isTrue();
    }

    private BlockPos getRandomPos(BlockPos pos, Random rand, int width, int high){
        return pos.add(
                rand.nextInt(width) - rand.nextInt(width),
                rand.nextInt(high) - rand.nextInt(high),
                rand.nextInt(width) - rand.nextInt(width));
    }

    private boolean isValidPosition(IWorld worldIn, BlockPos pos) {
        return worldIn.isAirBlock(pos) && ((SoilCropsBlock) DoTBBlocksRegistry.CAMELLIA).isValidGround(DoTBBlocksRegistry.CAMELLIA.defaultBlockState(), worldIn, pos.down());
    }
}
