package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.PushReaction;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nullable;

import static net.minecraftforge.common.Tags.Blocks.DIRT;

public class WildPlantBlock extends BlockDoTB{

    private final VoxelShape VS = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 14.0D, 14.0D);

    public WildPlantBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Vector3d vector = state.getOffset(worldIn, pos);
        return VS.move(vector.x, vector.y, vector.z);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public Block.OffsetType getOffsetType() {
        return Block.OffsetType.XZ;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return canSurvive(this.defaultBlockState(), context.getLevel(), context.getClickedPos()) ? super.getStateForPlacement(context) : null;
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        Block blockDown = worldIn.getBlockState(pos.below()).getBlock();
        return blockDown == Blocks.GRASS_BLOCK || blockDown.is(DIRT) || blockDown == Blocks.FARMLAND;
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }
}
