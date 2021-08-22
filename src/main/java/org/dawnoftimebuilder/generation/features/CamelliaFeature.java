package org.dawnoftimebuilder.generation.features;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import org.dawnoftimebuilder.block.templates.GrowingBushBlock;
import org.dawnoftimebuilder.registries.DoTBBlocksRegistry;

import java.util.Random;
import java.util.function.Function;

public class CamelliaFeature extends Feature<NoFeatureConfig> {

    public CamelliaFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configIn) {
        super(configIn);
    }

    @Override
    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        for (int i = 0; i < 64; ++i) {
            BlockPos nextPos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
            GrowingBushBlock camellia = (GrowingBushBlock) DoTBBlocksRegistry.CAMELLIA;
            BlockState bushState = camellia.getDefaultState().with(camellia.getAgeProperty(), camellia.getMaxAge());
            if (isValidPosition(worldIn, nextPos)) {
                worldIn.setBlockState(nextPos, bushState, 2);
            }
        }
        return true;
    }

    private boolean isValidPosition(IWorld worldIn, BlockPos pos) {
        Block blockOn = worldIn.getBlockState(pos.down()).getBlock();
        return worldIn.getBlockState(pos).getMaterial().isReplaceable() &&
                (blockOn == Blocks.GRASS_BLOCK || blockOn == Blocks.DIRT);
    }
}
