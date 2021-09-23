package org.dawnoftimebuilder.generation.features;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import org.dawnoftimebuilder.block.roman.CypressBlock;
import org.dawnoftimebuilder.DoTBConfig;

import java.util.Random;
import java.util.function.Function;

import static net.minecraftforge.common.Tags.Blocks.DIRT;
import static org.dawnoftimebuilder.registry.DoTBBlocksRegistry.CYPRESS;

public class CypressFeature extends Feature<NoFeatureConfig> {

    public CypressFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configIn) {
        super(configIn);
    }

    /**
     * Places fully grown mulberry trees in a random pattern around a point.
     */
    @Override
    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        boolean success = false;
        for (int i = 0; i < DoTBConfig.CYPRESS_ROLLS.get(); ++i) {
            // get next random position.
            BlockPos nextPos = getRandomPos(pos, rand, DoTBConfig.CYPRESS_SPAWN_WIDTH.get(), DoTBConfig.CYPRESS_SPAWN_HIGH.get());
            if (isValidPosition(worldIn, nextPos)) {
                int cypressSize;
                //Get max cypress size
                for(cypressSize = 1; cypressSize < 9; cypressSize++){
                    if(!worldIn.getBlockState(nextPos.up(cypressSize)).getMaterial().isReplaceable()) break;
                }
                if(cypressSize > 2){
                    success = true;
                    cypressSize = 3 + rand.nextInt(cypressSize - 2);
                    int stateSize;
                    for(int j = 0; j < cypressSize; j++){
                        stateSize = (j == 0) ? 0 : Math.min(cypressSize - j, 5);
                        worldIn.setBlockState(nextPos.up(j), CypressBlock.setSize(CYPRESS.getDefaultState(), stateSize), 2);
                    }
                }
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
        Block blockUnder = worldIn.getBlockState(pos.down()).getBlock();
        return worldIn.isAirBlock(pos) && (blockUnder == Blocks.GRASS_BLOCK || blockUnder.isIn(DIRT));
    }
}
