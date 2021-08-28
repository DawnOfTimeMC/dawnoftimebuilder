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
import org.dawnoftimebuilder.block.templates.SoilCropsBlock;
import org.dawnoftimebuilder.registries.DoTBBlocksRegistry;
import org.dawnoftimebuilder.utils.DoTBConfig;

import java.util.Random;
import java.util.function.Function;

public class CamelliaFeature extends Feature<NoFeatureConfig> {

    public CamelliaFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configIn) {
        super(configIn);
    }

    @Override
    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        boolean success = false;
        for (int i = 0; i < DoTBConfig.CAMELLIA_ROLLS.get(); ++i) {
            BlockPos nextPos = getRandomPos(pos, rand, DoTBConfig.CAMELLIA_SPAWN_WIDTH.get(), DoTBConfig.CAMELLIA_SPAWN_HIGH.get());
            GrowingBushBlock camellia = (GrowingBushBlock) DoTBBlocksRegistry.CAMELLIA;
            BlockState bushState = camellia.getDefaultState().with(camellia.getAgeProperty(), rand.nextInt(camellia.getMaxAge() + 1));
            if (isValidPosition(worldIn, nextPos)) {
                success = true;
                worldIn.setBlockState(nextPos, bushState, 2);
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
        return worldIn.isAirBlock(pos) && ((SoilCropsBlock) DoTBBlocksRegistry.CAMELLIA).isValidGround(DoTBBlocksRegistry.CAMELLIA.getDefaultState(), worldIn, pos.down());
    }
}
