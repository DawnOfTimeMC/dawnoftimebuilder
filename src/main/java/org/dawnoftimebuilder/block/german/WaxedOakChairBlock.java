package org.dawnoftimebuilder.block.german;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.properties.Half;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import org.dawnoftimebuilder.block.templates.DoubleChairBlock;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

public class WaxedOakChairBlock extends DoubleChairBlock {

	private static final VoxelShape[] VS = DoTBBlockUtils.GenerateHorizontalShapes(new VoxelShape[]{
			VoxelShapes.or(
					Block.box(2.0D, 0.0D, 2.0D, 14.0D, 11.0D, 14.0D),
					Block.box(2.5D, 11.0D, 3.0D, 13.5D, 16.0D, 5.0D)),
			Block.box(2.5D, 0.0D, 3.0D, 13.5D, 10.0D, 5.0D)});

	public WaxedOakChairBlock(Properties properties, float pixelsYOffset) {
		super(properties, pixelsYOffset);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		int index = (state.getValue(HALF) == Half.TOP) ? 1 : 0;
		return VS[state.getValue(FACING).get2DDataValue() * 2 + index];
	}
}
