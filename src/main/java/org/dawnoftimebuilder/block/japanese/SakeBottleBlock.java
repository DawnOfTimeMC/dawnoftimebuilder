package org.dawnoftimebuilder.block.japanese;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import org.dawnoftimebuilder.block.IBlockSpecialDisplay;
import org.dawnoftimebuilder.block.builders.WaterloggedBlock;

public class SakeBottleBlock extends WaterloggedBlock implements IBlockSpecialDisplay {

    private static final VoxelShape VS = makeCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 6.0D, 10.0D);

    public SakeBottleBlock() {
        super("sake_bottle", Material.CLAY, 0.8F, 0.8F);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VS;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
}
