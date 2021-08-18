package org.dawnoftimebuilder.block.japanese;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.state.properties.DoorHingeSide;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import org.dawnoftimebuilder.block.templates.DoorBlockDoTB;
import org.dawnoftimebuilder.utils.DoTBBlockUtils;

public class PaperDoorBlock extends DoorBlockDoTB {

    //VoxelShapes are slightly larger than paper_walls so that it can easily be right-clicked when opened.
    private static final VoxelShape[] VS_NORTH = DoTBBlockUtils.GenerateHorizontalShapes(new VoxelShape[]{makeCuboidShape(0.0D, 0.0D, 6.99D, 16.0D, 16.0D, 8.01D)});
    private static final VoxelShape[] VS_NORTH_OPEN = DoTBBlockUtils.GenerateHorizontalShapes(new VoxelShape[]{makeCuboidShape(-12.0D, 0.0D, 6.99D, 4.0D, 16.0D, 8.01)});

    public PaperDoorBlock() {
        super(Material.WOOL, 2.0F, 2.0F);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return state.get(OPEN) ? VS_NORTH_OPEN[state.get(HINGE) == DoorHingeSide.LEFT ? state.get(FACING).getOpposite().getHorizontalIndex() : state.get(FACING).getHorizontalIndex()] : VS_NORTH[state.get(FACING).getHorizontalIndex()];
    }
}
