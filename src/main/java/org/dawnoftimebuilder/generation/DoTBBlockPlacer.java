package org.dawnoftimebuilder.generation;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;
import org.dawnoftimebuilder.block.IBlockGeneration;
import org.dawnoftimebuilder.block.templates.SoilCropsBlock;

import java.util.Random;

public class DoTBBlockPlacer extends BlockPlacer {

    public DoTBBlockPlacer() {}

    protected BlockPlacerType<?> type() {
        return BlockPlacerType.COLUMN_PLACER;
    }

    public void place(IWorld world, BlockPos pos, BlockState state, Random random) {
        if(state.getBlock() instanceof IBlockGeneration){
            IBlockGeneration block = ((IBlockGeneration) state.getBlock());
            block.generateOnPos(world, pos, state, random);
        }
    }
}
