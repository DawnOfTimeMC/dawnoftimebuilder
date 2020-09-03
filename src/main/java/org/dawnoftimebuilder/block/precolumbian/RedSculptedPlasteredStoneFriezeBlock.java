package org.dawnoftimebuilder.block.precolumbian;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import org.dawnoftimebuilder.block.templates.PlateBlock;
import net.minecraft.block.material.Material;
import org.dawnoftimebuilder.utils.DoTBBlockUtils;

public class RedSculptedPlasteredStoneFriezeBlock extends PlateBlock {

	private static final VoxelShape[] SHAPES = DoTBBlockUtils.GenerateHorizontalShapes(makeShapes());
    
    public RedSculptedPlasteredStoneFriezeBlock() {
    	super("red_sculpted_plastered_stone_frieze", Material.ROCK,1.5F, 6.0F);
    }

	/**
	 * @return Stores VoxelShape with index : <p/>
	 * 0 : NW Outer <p/>
	 * 1 : N Default <p/>
	 * 2 : NW Inner <p/>
	 */
	private static VoxelShape[] makeShapes() {
		VoxelShape vs_qtr_n = makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 8.0D);
		VoxelShape vs_spike_n = makeCuboidShape(4.0D, 4.0D, 4.0D, 12.0D, 13.0D, 7.0D);
		return new VoxelShape[]{
				VoxelShapes.or(
						makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 4.0D, 8.0D),
						makeCuboidShape(4.0D, 2.0D, 4.0D, 12.0D, 13.0D, 12.0D)),
				VoxelShapes.or(vs_qtr_n, vs_spike_n),
				VoxelShapes.or(
						vs_qtr_n,
						makeCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 4.0D, 16.0D),
						vs_spike_n,
						makeCuboidShape(4.0D, 4.0D, 4.0D, 7.0D, 13.0D, 12.0D)
						)
		};
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		int index = (state.get(FACING).getHorizontalIndex() + 2) % 4;
		index *= 3;
		switch (state.get(SHAPE)) {
			default:
			case OUTER_LEFT:
				break;
			case OUTER_RIGHT:
				index += 3;
				break;
			case STRAIGHT:
				index += 1;
				break;
			case INNER_LEFT:
				index += 2;
				break;
			case INNER_RIGHT:
				index += 5;
				break;
		}
		index %= 12;
		return SHAPES[index];
	}
}