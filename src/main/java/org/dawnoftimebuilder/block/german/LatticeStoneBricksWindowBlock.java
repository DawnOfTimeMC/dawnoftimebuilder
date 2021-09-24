package org.dawnoftimebuilder.block.german;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import org.dawnoftimebuilder.block.templates.SidedWindowBlock;

public class LatticeStoneBricksWindowBlock extends SidedWindowBlock {

	private static final VoxelShape NORTH_VS = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 6.0D);
	private static final VoxelShape EAST_VS = Block.box(10.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	private static final VoxelShape SOUTH_VS = Block.box(0.0D, 0.0D, 10.0D, 16.0D, 16.0D, 16.0D);
	private static final VoxelShape WEST_VS = Block.box(0.0D, 0.0D, 0.0D, 6.0D, 16.0D, 16.0D);
	private static final VoxelShape X_VS = Block.box(5.0D, 0.0D, 0.0D, 11.0D, 16.0D, 16.0D);
	private static final VoxelShape Z_VS = Block.box(0.0D, 0.0D, 5.0D, 16.0D, 16.0D, 11.0D);

	public LatticeStoneBricksWindowBlock(Material materialIn, float hardness, float resistance, SoundType soundType) {
		super(Properties.of(materialIn).strength(hardness, resistance).sound(soundType));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		switch(state.get(SIDED_WINDOW)){
			default:
			case NORTH:
				return NORTH_VS;
			case EAST:
				return EAST_VS;
			case SOUTH:
				return SOUTH_VS;
			case WEST:
				return WEST_VS;
			case AXIS_X:
				return X_VS;
			case AXIS_Z:
				return Z_VS;
		}
	}
}
