package org.dawnoftime.dawnoftime.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class DefaultCropsFeature extends Feature<RandomPatchConfiguration> {
    public DefaultCropsFeature(Codec<RandomPatchConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<RandomPatchConfiguration> pContext) {
        WorldGenLevel level = pContext.level();
        ChunkGenerator generator = pContext.chunkGenerator();
        BlockPos pos = pContext.origin();
        RandomSource random = pContext.random();

        RandomPatchConfiguration config = pContext.config();

        FeatureConfiguration featureConfig = config.feature().value().feature().value().config();
        if(!(featureConfig instanceof SimpleBlockConfiguration))
            return false;

        BlockState state = ((SimpleBlockConfiguration) featureConfig).toPlace().getState(random, pos);
        int i = 0;

        for(int j = 0; j < config.tries(); ++j) {
            BlockPos blockpos = this.getPos(random, pos, config);
            if(state.getBlock() instanceof SimpleWaterloggedBlock) {
                if(level.getBlockState(blockpos).is(Blocks.WATER)){
                    if(blockpos.getY() < 255 && state.canSurvive(level, blockpos)) {
                        config.feature().value().place(level, generator, random, blockpos);
                        ++i;
                    }
                }
            } else {
                if(level.isEmptyBlock(blockpos)) {
                    if(blockpos.getY() < 255 && state.canSurvive(level, blockpos)) {
                        config.feature().value().place(level, generator, random, blockpos);
                        ++i;
                    }
                }
            }
        }

        return i > 0;
    }

    public BlockPos getPos(RandomSource random, BlockPos pos, RandomPatchConfiguration config) {
        return pos.offset(random.nextInt(config.xzSpread()) - random.nextInt(config.xzSpread()), random.nextInt(config.ySpread()) - random.nextInt(config.ySpread()), random.nextInt(config.xzSpread()) - random.nextInt(config.xzSpread()));
    }
}
