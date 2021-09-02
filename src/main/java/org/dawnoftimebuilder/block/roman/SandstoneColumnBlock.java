package org.dawnoftimebuilder.block.roman;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import org.dawnoftimebuilder.block.templates.ColumnConnectibleBlock;

public class SandstoneColumnBlock extends ColumnConnectibleBlock {

    private static final VoxelShape VS_BOT = VoxelShapes.or(
    		makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D),
			makeCuboidShape(4.0D, 8.0D, 4.0D, 12.0D, 16.0D, 12.0D)
	);
	private static final VoxelShape VS_MID = makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
	private static final VoxelShape VS_TOP = VoxelShapes.or(
			makeCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D),
			makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D)
	);

	public SandstoneColumnBlock(Material materialIn, float hardness, float resistance, SoundType soundType) {
		super(Properties.create(materialIn).hardnessAndResistance(hardness, resistance).sound(soundType));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch (state.get(VERTICAL_CONNECTION)) {
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
