package org.dawnoftimebuilder.block.japanese;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.item.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class FutonBlock extends BedBlock {

	private static final VoxelShape VS = makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);

	public FutonBlock(DyeColor colorIn, Material materialIn, float hardness, float resistance, SoundType soundType) {
		super(colorIn, Block.Properties.create(materialIn).hardnessAndResistance(hardness, resistance).sound(soundType));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return VS;
	}

	@Override
	public boolean isBed(BlockState state, IBlockReader world, BlockPos pos, @Nullable Entity player) {
		return true;
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}
}