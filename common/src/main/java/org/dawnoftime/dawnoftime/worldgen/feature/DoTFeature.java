package org.dawnoftime.dawnoftime.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import org.dawnoftime.dawnoftime.block.IBlockGeneration;

public class DoTFeature extends Feature<SimpleBlockConfiguration> {
    public DoTFeature(Codec<SimpleBlockConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> pContext) {
        WorldGenLevel level = pContext.level();
        BlockPos pos = pContext.origin();
        RandomSource random = pContext.random();

        SimpleBlockConfiguration config = pContext.config();
        BlockState state = config.toPlace().getState(random, pos);

        if(state.getBlock() instanceof IBlockGeneration block) {
            return block.generateOnPos(level, pos, state, random);
        }
        return true;
    }
}
