package org.dawnoftimebuilder.generation;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;
import org.dawnoftimebuilder.block.IBlockGeneration;
import org.dawnoftimebuilder.registry.DoTBBlockPlacerRegistry;

import java.util.Random;

public class DoTBBlockPlacer extends BlockPlacer {

    public static final Codec<DoTBBlockPlacer> CODEC;
    public static final DoTBBlockPlacer INSTANCE = new DoTBBlockPlacer();

    static {
        CODEC = Codec.unit(() -> INSTANCE);
    }

    public DoTBBlockPlacer() {}

    protected BlockPlacerType<?> type() {
        return DoTBBlockPlacerRegistry.DOTB_BLOCK_PLACER.get();
    }

    public void place(IWorld world, BlockPos pos, BlockState state, Random random) {
        if(state.getBlock() instanceof IBlockGeneration){
            IBlockGeneration block = ((IBlockGeneration) state.getBlock());
            block.generateOnPos(world, pos, state, random);
        }
    }
}
