package org.dawnoftimebuilder.block.japanese;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import org.dawnoftimebuilder.block.templates.ColumnConnectibleBlock;

import javax.annotation.Nonnull;

import static org.dawnoftimebuilder.utils.DoTBBlockStateProperties.VerticalConnection;

public class PaperLampBlock extends ColumnConnectibleBlock {

    private static final VoxelShape VS_BOTTOM = makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    private static final VoxelShape VS_TOP = makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 13.0D, 12.0D);

    public PaperLampBlock() {
        super(Properties.create(Material.WOOL).hardnessAndResistance(2.0F, 1.5F).lightValue(15));
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VerticalConnection connection = state.get(VERTICAL_CONNECTION);
        if(connection == VerticalConnection.ABOVE || connection == VerticalConnection.BOTH){
            return VS_BOTTOM;
        }else return VS_TOP;
    }

    @Nonnull
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
}
