package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import org.dawnoftimebuilder.block.IBlockSpecialDisplay;

public class FlowerPotBlockDoTB extends BlockDoTB implements IBlockSpecialDisplay {

    private static final VoxelShape VS = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 4.0D, 10.0D);

	public FlowerPotBlockDoTB(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader blockReader, BlockPos pos, ISelectionContext context) {
        return VS;
    }
}
