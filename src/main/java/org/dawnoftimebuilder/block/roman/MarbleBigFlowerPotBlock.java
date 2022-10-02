package org.dawnoftimebuilder.block.roman;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import org.dawnoftimebuilder.block.templates.WaterloggedBlock;

public class MarbleBigFlowerPotBlock extends WaterloggedBlock {

    private static final VoxelShape VS = VoxelShapes.or(
            Block.box(-0.5F, 15.0F, -0.5F, 16.5F, 17.0F, 16.5F),
            Block.box(1.5F, 5.0F, 1.5F, 14.5F, 15.0F, 14.5F),
            Block.box(5.0F, 2.0F, 5.0F, 11.0F, 5.0F, 11.0F),
            Block.box(2.0F, 0.0F, 2.0F, 14.0F, 2.0F, 14.0F));

    public MarbleBigFlowerPotBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return VS;
    }
}
