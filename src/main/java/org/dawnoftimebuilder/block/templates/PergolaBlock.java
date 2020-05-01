package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.dawnoftimebuilder.utils.DoTBBlockStateProperties;

import javax.annotation.Nonnull;

public class PergolaBlock extends BeamBlock {

	private static final VoxelShape[] SHAPES = makeShapes();

	public PergolaBlock(String name, Material materialIn, float hardness, float resistance) {
		super(name, materialIn, hardness, resistance);
	}

	/**
	 * @return Stores VoxelShape with index : <p/>
	 * 0 : Axis X <p/>
	 * 1 : Axis Z <p/>
	 * 2 : Axis X + Z <p/>
	 * 3 : Axis Y <p/>
	 * 4 : Axis Y + Bottom <p/>
	 * 5 : Axis Y + X <p/>
	 * 6 : Axis Y + X + Bottom <p/>
	 * 7 : Axis Y + Z <p/>
	 * 8 : Axis Y + Z + Bottom <p/>
	 * 9 : Axis Y + X + Z <p/>
	 * 10 : Axis Y + X + Z + Bottom
	 */
	private static VoxelShape[] makeShapes() {
		VoxelShape vs_axis_x = Block.makeCuboidShape(0.0D, 5.0D, 6.0D, 16.0D, 11.0D, 10.0D);
		VoxelShape vs_axis_z = Block.makeCuboidShape(6.0D, 5.0D, 0.0D, 10.0D, 11.0D, 16.0D);
		VoxelShape vs_axis_x_z = VoxelShapes.or(vs_axis_x, vs_axis_z);
		VoxelShape vs_axis_y = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
		VoxelShape vs_axis_y_bottom = VoxelShapes.or(vs_axis_y, Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 2.0D, 14.0D));
		return new VoxelShape[]{
				vs_axis_x,
				vs_axis_z,
				vs_axis_x_z,
				vs_axis_y,
				vs_axis_y_bottom,
				VoxelShapes.or(vs_axis_y, vs_axis_x),
				VoxelShapes.or(vs_axis_y_bottom, vs_axis_x),
				VoxelShapes.or(vs_axis_y, vs_axis_z),
				VoxelShapes.or(vs_axis_y_bottom, vs_axis_z),
				VoxelShapes.or(vs_axis_y, vs_axis_x_z),
				VoxelShapes.or(vs_axis_y_bottom, vs_axis_x_z)
		};
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPES[getShapeIndex(state)];
	}

	@Override
	public BlockState getCurrentState(BlockState stateIn, IWorld worldIn, BlockPos pos) {
		stateIn = super.getCurrentState(stateIn, worldIn, pos);
		boolean isBottom = stateIn.get(BOTTOM);
		return stateIn.with(BOTTOM, isBottom && !worldIn.getBlockState(pos.down()).isIn(BlockTags.DIRT_LIKE) && !stateIn.get(CLIMBING_PLANT).hasNoPlant());
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (state.get(BOTTOM) || worldIn.getBlockState(pos.down()).isIn(BlockTags.DIRT_LIKE)){
			if (this.tryPlacingPlant(state, worldIn, pos, player, handIn)) return true;
		}
		return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
	}

	@Override
	public boolean canHavePlant(BlockState state) {
		return !state.get(WATERLOGGED);
	}

	@Nonnull
	@Override
	public DoTBBlockStateProperties.PillarConnection getBlockPillarConnection(BlockState state) {
		return DoTBBlockStateProperties.PillarConnection.SIX_PX;
	}
}
