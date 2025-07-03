package org.dawnoftime.dawnoftime.block.german;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import org.dawnoftime.dawnoftime.block.templates.BlockDoT;
import javax.annotation.Nullable;
import static org.dawnoftime.dawnoftime.util.VoxelShapes.GERANIUM_SHAPE;

public class GeraniumBlock extends BlockDoT {

    public GeraniumBlock(Properties properties) {
        super(properties.pushReaction(PushReaction.DESTROY), GERANIUM_SHAPE);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return canSurvive(this.defaultBlockState(), context.getLevel(), context.getClickedPos()) ? super.getStateForPlacement(context) : null;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockState blockDown = worldIn.getBlockState(pos.below());
        return blockDown.getBlock() == Blocks.GRASS_BLOCK || blockDown.is(BlockTags.DIRT) || blockDown.getBlock() == Blocks.FARMLAND;
    }
}
