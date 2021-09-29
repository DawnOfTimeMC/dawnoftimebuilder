package org.dawnoftimebuilder.block.japanese;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import org.dawnoftimebuilder.block.templates.ChairBlock;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

public class SpruceLeglessChairBlock extends ChairBlock {

	private static final VoxelShape[] VS = DoTBBlockUtils.GenerateHorizontalShapes(new VoxelShape[]{
			VoxelShapes.or(
					Block.box(2.0D, 0.0D, 0.0D, 14.0D, 3.0D, 16.0D),
					Block.box(2.0D, 3.0D, 0.0D, 14.0D, 11.0D, 4.0D))});

	public SpruceLeglessChairBlock(Properties properties, float pixelsYOffset) {
		super(properties, pixelsYOffset);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return VS[state.getValue(FACING).get2DDataValue()];
	}
}
