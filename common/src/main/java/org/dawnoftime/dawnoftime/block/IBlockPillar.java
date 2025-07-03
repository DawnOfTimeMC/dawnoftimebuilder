package org.dawnoftime.dawnoftime.block;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.dawnoftime.dawnoftime.util.BlockStatePropertiesAA;

import javax.annotation.Nonnull;

public interface IBlockPillar {
    /**
     * Get the block pillar connection on the top side. Useful to connect blocks with elements of good size.
     *
     * @param worldIn World of the block.
     * @param pos     Position of the block
     *
     * @return PillarConnection of the Block toward the block above it.
     */
    static BlockStatePropertiesAA.PillarConnection getPillarConnectionAbove(LevelAccessor worldIn, BlockPos pos) {
        BlockState state = worldIn.getBlockState(pos);
        Block block = state.getBlock();
        if(block instanceof IBlockPillar)
            return ((IBlockPillar) block).getBlockPillarConnectionAbove(state);
        if(state.is(BlockTags.FENCES))
            return BlockStatePropertiesAA.PillarConnection.FOUR_PX;
        if(state.is(BlockTags.WALLS))
            return BlockStatePropertiesAA.PillarConnection.EIGHT_PX;
        return BlockStatePropertiesAA.PillarConnection.NOTHING;
    }

    /**
     * Get the block pillar connection on the downside. Useful to connect blocks with elements of good size.
     *
     * @param worldIn World of the block.
     * @param pos     Position of the block
     *
     * @return PillarConnection of the Block toward the block under it.
     */
    static BlockStatePropertiesAA.PillarConnection getPillarConnectionUnder(LevelAccessor worldIn, BlockPos pos) {
        BlockState state = worldIn.getBlockState(pos);
        Block block = state.getBlock();
        if(block instanceof IBlockPillar)
            return ((IBlockPillar) block).getBlockPillarConnectionAbove(state);
        if(state.is(BlockTags.FENCES))
            return BlockStatePropertiesAA.PillarConnection.FOUR_PX;
        if(state.is(BlockTags.WALLS))
            return BlockStatePropertiesAA.PillarConnection.EIGHT_PX;
        return BlockStatePropertiesAA.PillarConnection.NOTHING;
    }

    /**
     * @param state BlockState of the block.
     *
     * @return The pillar connection on the Top side for a given blockstate
     */
    @Nonnull
    BlockStatePropertiesAA.PillarConnection getBlockPillarConnectionAbove(BlockState state);

    /**
     * @param state BlockState of the block.
     *
     * @return The pillar connection on the Downside for a given blockstate.
     * Default : return getBlockPillarConnectionAbove value.
     */
    @Nonnull
    default BlockStatePropertiesAA.PillarConnection getBlockPillarConnectionUnder(BlockState state) {
        return this.getBlockPillarConnectionAbove(state);
    }
}
