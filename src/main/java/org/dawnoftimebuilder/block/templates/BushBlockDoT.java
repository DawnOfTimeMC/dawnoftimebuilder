package org.dawnoftimebuilder.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.dawnoftimebuilder.block.IBlockGeneration;

public class BushBlockDoT extends BushBlock implements IBlockGeneration {
    private int fireSpreadSpeed = 0;
    private int fireDestructionSpeed = 0;

    public BushBlockDoT(final Properties properties) {
        super(properties);
    }

    /**
     * Set Encouragement to 5 and Flammability to 20
     *
     * @return this
     */
    public Block setBurnable() {
        return this.setBurnable(5, 20);
    }

    /**
     * Set burning parameters (default 5 / 20)
     *
     * @param fireSpreadSpeed      Increases the probability to catch fire
     * @param fireDestructionSpeed Decreases burning duration
     *
     * @return this
     */
    public Block setBurnable(final int fireSpreadSpeed, final int fireDestructionSpeed) {
        this.fireSpreadSpeed = fireSpreadSpeed;
        this.fireDestructionSpeed = fireDestructionSpeed;
        return this;
    }

    @Override
    public int getFireSpreadSpeed(final BlockState state, final BlockGetter world, final BlockPos pos, final Direction face) {
        return state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED) ? 0 : this.fireSpreadSpeed;
    }

    @Override
    public int getFlammability(final BlockState state, final BlockGetter world, final BlockPos pos, final Direction face) {
        return state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED) ? 0 : this.fireDestructionSpeed;
    }

    @Override
    public boolean generateOnPos(WorldGenLevel world, BlockPos pos, BlockState state, RandomSource random) {
        final BlockState groundState = world.getBlockState(pos.below());

        if (!groundState.is(BlockTags.DIRT)) {
            return false;
        }
        world.setBlock(pos, state, 2);

        return true;
    }
}
