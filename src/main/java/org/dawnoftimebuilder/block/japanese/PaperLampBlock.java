package org.dawnoftimebuilder.block.japanese;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import org.dawnoftimebuilder.block.IBlockSpecialDisplay;
import org.dawnoftimebuilder.block.templates.ColumnConnectibleBlock;

import javax.annotation.Nonnull;

import static org.dawnoftimebuilder.util.DoTBBlockStateProperties.VerticalConnection;

public class PaperLampBlock extends ColumnConnectibleBlock implements IBlockSpecialDisplay {

    private static final VoxelShape VS_BOTTOM = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    private static final VoxelShape VS_TOP = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 13.0D, 12.0D);

    public PaperLampBlock(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VerticalConnection connection = state.getValue(VERTICAL_CONNECTION);
        if(connection == VerticalConnection.ABOVE || connection == VerticalConnection.BOTH){
            return VS_BOTTOM;
        }else return VS_TOP;
    }

    @Override
    public boolean emitsLight() {
        return true;
    }

    @Override
    public float getDisplayScale() {
        return 0.6F;
    }
}
