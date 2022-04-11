package org.dawnoftimebuilder.block;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;

import java.util.Random;

public interface IBlockGeneration {

    /**
     * This function is called by the placer during the world generation.
     * It must check whenever this position is suitable for the block.
     * @param world where the block must spawn.
     * @param pos position where to spawn the block.
     * @param state of the block (default state).
     * @param random instance.
     */
    void generateOnPos(IWorld world, BlockPos pos, BlockState state, Random random);
}
