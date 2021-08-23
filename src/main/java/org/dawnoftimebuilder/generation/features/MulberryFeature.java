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
import org.dawnoftimebuilder.block.templates.DoubleGrowingBushBlock;
import org.dawnoftimebuilder.registries.DoTBBlocksRegistry;
import org.dawnoftimebuilder.utils.DoTBConfig;

import java.util.Random;
import java.util.function.Function;

public class MulberryFeature extends Feature<NoFeatureConfig> {

    public MulberryFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configIn) {
        super(configIn);
    }

    /**
     * Places fully grown mulberry trees in a random pattern around a point.
     */
    @Override
    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        boolean success = false;
        for (int i = 0; i < 64; ++i) {
            // get next random position.
            BlockPos nextPos = getRandomPos(pos, rand, DoTBConfig.MULBERRY_SPAWN_WIDTH.get(), DoTBConfig.MULBERRY_SPAWN_HIGH.get());
            if (isValidPosition(worldIn, nextPos)) {
                success = true;
                // the block states to be placed
                DoubleGrowingBushBlock mulberry = (DoubleGrowingBushBlock) DoTBBlocksRegistry.MULBERRY;
                BlockState treeBaseState = mulberry.getDefaultState().with(mulberry.getAgeProperty(),rand.nextInt(mulberry.getMaxAge() - mulberry.getAgeReachingTopBlock() + 1) + mulberry.getAgeReachingTopBlock());
                // set the block states
                worldIn.setBlockState(nextPos, treeBaseState, 2);
                worldIn.setBlockState(nextPos.up(), mulberry.getTopState(treeBaseState), 2);
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
        Block blockOn = worldIn.getBlockState(pos.down()).getBlock();
        return worldIn.getBlockState(pos).getMaterial().isReplaceable() &&
                (blockOn == Blocks.GRASS_BLOCK || blockOn == Blocks.DIRT);//TODO replace with tags
    }
}
