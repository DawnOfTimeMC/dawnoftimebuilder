package org.dawnoftimebuilder.block.roman;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import org.dawnoftimebuilder.block.templates.ColumnConnectibleBlock;

public class SandstoneColumnBlock extends ColumnConnectibleBlock {

    private static final VoxelShape VS_BOT = VoxelShapes.or(
    		Block.box(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D),
			Block.box(4.0D, 8.0D, 4.0D, 12.0D, 16.0D, 12.0D)
	);
	private static final VoxelShape VS_MID = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
	private static final VoxelShape VS_TOP = VoxelShapes.or(
			Block.box(0.0D, 12.0D, 0.0D, 16.0D, 16.0D, 16.0D),
			Block.box(2.0D, 8.0D, 2.0D, 14.0D, 12.0D, 14.0D),
			Block.box(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D)
	);

	public SandstoneColumnBlock(Properties properties) {
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch (state.getValue(VERTICAL_CONNECTION)) {
			case UNDER:
				return VS_TOP;
			default:
			case NONE:
			case BOTH:
				return VS_MID;
			case ABOVE:
				return VS_BOT;
		}
	}
}
