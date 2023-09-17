package org.dawnoftimebuilder.block.japanese;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
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
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
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
