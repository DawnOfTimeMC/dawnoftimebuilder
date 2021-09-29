package org.dawnoftimebuilder.block.japanese;

import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class FutonBlock extends BedBlock {

	private static final VoxelShape VS = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);

	public FutonBlock(DyeColor colorIn, Properties properties) {
		super(colorIn, properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return VS;
	}

	@Override
	public boolean isBed(BlockState state, IBlockReader world, BlockPos pos, @Nullable Entity player) {
		return true;
	}
}