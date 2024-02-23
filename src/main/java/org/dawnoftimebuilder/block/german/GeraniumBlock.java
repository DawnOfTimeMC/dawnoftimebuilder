package org.dawnoftimebuilder.block.german;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.block.templates.BlockDoTB;

import javax.annotation.Nullable;

public class GeraniumBlock extends BlockDoTB {
    private static final VoxelShape VS = Block.box(-2.0D, -7.0D, -2.0D, 17.0D, 15.0D, 17.0D);

    public GeraniumBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return canSurvive(this.defaultBlockState(), context.getLevel(), context.getClickedPos()) ? super.getStateForPlacement(context) : null;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        Block blockDown = worldIn.getBlockState(pos.below()).getBlock();
        return blockDown == Blocks.GRASS_BLOCK || blockDown == Blocks.FARMLAND;
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return VS;
    }
}
