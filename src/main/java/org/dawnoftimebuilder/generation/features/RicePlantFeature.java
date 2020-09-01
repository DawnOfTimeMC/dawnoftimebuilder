package org.dawnoftimebuilder.generation.features;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.Half;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import org.dawnoftimebuilder.block.templates.DoubleCropsBlock;
import org.dawnoftimebuilder.block.templates.WaterDoubleCropsBlock;
import org.dawnoftimebuilder.block.templates.WaterloggedBlock;
import org.dawnoftimebuilder.generation.features.templates.FeatureDoTB;
import org.dawnoftimebuilder.registries.DoTBBlocksRegistry;

import java.util.Random;
import java.util.function.Function;

public class RicePlantFeature extends FeatureDoTB<NoFeatureConfig> {

    public RicePlantFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configIn, String name) {
        super(configIn, name);
    }

    /**
     * Places a rice plant in the world in a similar fashion to melons.
     */
    @Override
    public boolean place(IWorld worldIn, ChunkGenerator generator, Random rand, BlockPos pos, IFeatureConfig config) {
        for (int i = 0; i < 64; ++i) {
            BlockPos nextPos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
            WaterDoubleCropsBlock rice = (WaterDoubleCropsBlock) DoTBBlocksRegistry.RICE;
            BlockState baseState = rice.getDefaultState().with(rice.getAgeProperty(), rice.getMaxAge()).with(WaterloggedBlock.WATERLOGGED, true); // place holder
            BlockState topState = rice.getDefaultState().with(rice.getAgeProperty(), rice.getMaxAge()).with(DoubleCropsBlock.HALF, Half.TOP);
            if (isValidPosition(worldIn, nextPos)) {
                worldIn.setBlockState(nextPos, baseState, 2);
                worldIn.setBlockState(nextPos.up(), topState, 2);
            }
        }
        return true;
    }

    /**
     * Determins if the given position is valid for a rice plant.
     */
    private boolean isValidPosition(IWorld worldIn, BlockPos pos) {
        Block blockIn = worldIn.getBlockState(pos).getBlock();
        Block blockOn = worldIn.getBlockState(pos.down()).getBlock();
        Block blockUnder = worldIn.getBlockState(pos.up()).getBlock();
        return worldIn.getBlockState(pos).getMaterial().isReplaceable() &&
                (blockOn == Blocks.DIRT) &&
                blockUnder == Blocks.AIR &&
                blockIn == Blocks.WATER;
    }
}
