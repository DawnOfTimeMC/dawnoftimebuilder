package org.dawnoftimebuilder.block.precolumbian;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import org.dawnoftimebuilder.block.templates.SidedColumnConnectibleBlock;
import org.dawnoftimebuilder.util.DoTBUtils;

public class SerpentSculptedColumnBlock extends SidedColumnConnectibleBlock {

	private static final VoxelShape[] SHAPES = DoTBUtils.GenerateHorizontalShapes(makeShapes());

	public SerpentSculptedColumnBlock(Properties properties) {
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		int index = state.getValue(VERTICAL_CONNECTION).getIndex();
		return SHAPES[index + state.getValue(FACING).get2DDataValue() * 4];
	}

	/**
	 * @return Stores VoxelShape for "South" with index : <p/>
	 * 0 : S Lone <p/>
	 * 1 : S Under <p/>
	 * 2 : S Above <p/>
	 * 3 : S Both <p/>
	 */
	private static VoxelShape[] makeShapes() {
		VoxelShape vs_head = Block.box(4.0D, 0.0D, 6.0D, 12.0D, 9.0D, 16.0D);
		VoxelShape vs_tail = Block.box(5.0D, 0.0D, 0.0D, 11.0D, 16.0D, 6.0D);
		return new VoxelShape[]{
				VoxelShapes.or(vs_head, Block.box(5.0D, 0.0D, 0.0D, 11.0D, 6.0D, 6.0D)),
				VoxelShapes.or(vs_tail, Block.box(5.0D, 10.0D, 6.0D, 11.0D, 16.0D, 15.0D)),
				VoxelShapes.or(vs_head, vs_tail),
				vs_tail
		};
	}
}
