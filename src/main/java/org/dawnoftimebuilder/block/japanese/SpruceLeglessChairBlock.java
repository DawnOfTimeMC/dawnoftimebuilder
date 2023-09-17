package org.dawnoftimebuilder.block.japanese;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftimebuilder.block.templates.ChairBlock;
import org.dawnoftimebuilder.util.DoTBUtils;

public class SpruceLeglessChairBlock extends ChairBlock {

	private static final VoxelShape[] VS = DoTBUtils.GenerateHorizontalShapes(new VoxelShape[]{
			Shapes.or(
					Block.box(2.0D, 0.0D, 0.0D, 14.0D, 3.0D, 16.0D),
					Block.box(2.0D, 3.0D, 0.0D, 14.0D, 11.0D, 4.0D))});

	public SpruceLeglessChairBlock(Properties properties, float pixelsYOffset) {
		super(properties, pixelsYOffset);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return VS[state.getValue(FACING).get2DDataValue()];
	}
}
