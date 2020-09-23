package org.dawnoftimebuilder.block.precolumbian;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import org.dawnoftimebuilder.block.templates.SidedColumnConnectibleBlock;
import org.dawnoftimebuilder.utils.DoTBBlockUtils;

public class BlockSerpentSculptedColumn extends SidedColumnConnectibleBlock {

	private static final VoxelShape[] SHAPES = DoTBBlockUtils.GenerateHorizontalShapes(makeShapes());

	public BlockSerpentSculptedColumn() {
		super(Material.ROCK, 1.5F, 6.0F);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		int index = state.get(VERTICAL_CONNECTION).getIndex();
		return SHAPES[index + state.get(FACING).getHorizontalIndex() * 4];
	}

	/**
	 * @return Stores VoxelShape for "South" with index : <p/>
	 * 0 : S None <p/>
	 * 1 : S Under <p/>
	 * 2 : S Above <p/>
	 * 3 : S Both <p/>
	 */
	private static VoxelShape[] makeShapes() {
		VoxelShape vs_head = makeCuboidShape(4.0D, 0.0D, 7.0D, 12.0D, 11.0D, 16.0D);
		VoxelShape vs_tail = makeCuboidShape(5.0D, 0.0D, 0.0D, 11.0D, 16.0D, 6.0D);
		return new VoxelShape[]{
				VoxelShapes.or(vs_head, makeCuboidShape(5.0D, 0.0D, 10.0D, 11.0D, 6.0D, 16.0D)),
				VoxelShapes.or(vs_tail, makeCuboidShape(5.0D, 10.0D, 6.0D, 11.0D, 16.0D, 15.0D)),
				VoxelShapes.or(vs_head, vs_tail),
				vs_tail
		};
	}
}
