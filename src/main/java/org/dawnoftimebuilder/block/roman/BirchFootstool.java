package org.dawnoftimebuilder.block.roman;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import org.dawnoftimebuilder.block.templates.ChairBlock;

import javax.annotation.Nonnull;

public class BirchFootstool extends ChairBlock {

    private static final VoxelShape X_AXIS_VS = VoxelShapes.or(
            makeCuboidShape(4.0F, 0.0F, 2.0F, 12.0F, 3.0F, 14.0F),
            makeCuboidShape(2.0F, 3.0F, 0.0F, 14.0F, 9.0F, 16.0F));
    private static final VoxelShape Z_AXIS_VS = VoxelShapes.or(
            makeCuboidShape(2.0F, 0.0F, 4.0F, 14.0F, 3.0F, 12.0F),
            makeCuboidShape(0.0F, 3.0F, 2.0F, 16.0F, 9.0F, 14.0F));

    public BirchFootstool() {
        super(Material.WOOD, 2.0F, 2.0F, 9.0F);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return (state.get(FACING).getAxis() == Direction.Axis.X) ? X_AXIS_VS : Z_AXIS_VS;
    }
}
