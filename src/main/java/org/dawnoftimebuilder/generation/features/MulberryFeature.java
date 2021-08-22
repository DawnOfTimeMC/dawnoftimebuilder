package org.dawnoftimebuilder.generation.features;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.Half;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import org.dawnoftimebuilder.block.templates.DoubleCropsBlock;
import org.dawnoftimebuilder.registries.DoTBBlocksRegistry;

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
        for (int i = 0; i < 64; ++i) {
            // get next random position.
            BlockPos nextPos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if (isValidPosition(worldIn, nextPos)) {

                // the block states to be placed
                DoubleCropsBlock mulberry = (DoubleCropsBlock) DoTBBlocksRegistry.MULBERRY;
                BlockState treeBaseState = mulberry.getDefaultState().with(mulberry.getAgeProperty(), mulberry.getMaxAge());
                BlockState treeTopState = mulberry.getDefaultState().with(mulberry.getAgeProperty(), mulberry.getMaxAge()).with(DoubleCropsBlock.HALF, Half.TOP);

                // set the block states
                worldIn.setBlockState(nextPos, treeBaseState, 2);
                worldIn.setBlockState(nextPos.up(), treeTopState, 2);
            }
        }

        return true;
    }

    /**
     * Determines if the given position is valid for a mulberry bush.
     */
    private boolean isValidPosition(IWorld worldIn, BlockPos pos) {
        Block blockOn = worldIn.getBlockState(pos.down()).getBlock();
        return worldIn.getBlockState(pos).getMaterial().isReplaceable() &&
                (blockOn == Blocks.GRASS_BLOCK || blockOn == Blocks.DIRT);
    }
}
