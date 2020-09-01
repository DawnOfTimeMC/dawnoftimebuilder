package org.dawnoftimebuilder.generation.features;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import org.dawnoftimebuilder.generation.features.templates.FeatureDoTB;
import org.dawnoftimebuilder.registries.DoTBBlocksRegistry;

import java.util.Random;
import java.util.function.Function;

public class CamelliaFeature extends FeatureDoTB {

    public CamelliaFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configIn, String registryName) {
        super(configIn, registryName);
    }

    @Override
    public boolean place(IWorld worldIn, ChunkGenerator generator, Random rand, BlockPos pos, IFeatureConfig config) {
        for (int i = 0; i < 64; ++i) {
            BlockPos nextPos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
            Block camellia = DoTBBlocksRegistry.CAMELLIA;
            BlockState bushState = camellia.getDefaultState().with(BlockStateProperties.AGE_0_5, Integer.valueOf(5));
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
