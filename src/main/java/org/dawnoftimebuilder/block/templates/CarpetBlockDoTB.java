package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class CarpetBlockDoTB extends WaterloggedBlock {

	private static final VoxelShape VS = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);

	public CarpetBlockDoTB(Material materialIn, float hardness, float resistance, SoundType soundType) {
		super(Properties.of(materialIn).strength(hardness, resistance).sound(soundType));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return VS;
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		return !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return !worldIn.isAirBlock(pos.below());
	}
}
