package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import static org.dawnoftime.dawnoftime.util.VoxelShapes.WILD_PLANT_SHAPES;

public class WildPlantBlock extends BlockDoT {

    public WildPlantBlock(Properties properties, VoxelShape[] shapes) {
        super(properties.pushReaction(PushReaction.DESTROY), shapes);
    }

    public WildPlantBlock(Properties properties){
        this(properties, WILD_PLANT_SHAPES);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        Vec3 vector = state.getOffset(worldIn, pos);
        return super.getShape(state, worldIn, pos, context).move(vector.x, vector.y, vector.z);
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
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
