package org.dawnoftimebuilder.block.japanese;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import org.dawnoftimebuilder.block.IBlockSpecialDisplay;
import org.dawnoftimebuilder.block.templates.WaterloggedBlock;

import javax.annotation.Nonnull;

public class PaperLanternBlock extends WaterloggedBlock implements IBlockSpecialDisplay{

    private static final VoxelShape VS = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 15.0D, 12.0D);

    public PaperLanternBlock(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VS;
    }

    @Override
    public boolean emitsLight() {
        return true;
    }
}
