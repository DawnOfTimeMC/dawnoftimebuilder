package org.dawnoftimebuilder.block.german;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import org.dawnoftimebuilder.block.templates.WaterloggedBlock;
import org.dawnoftimebuilder.utils.DoTBBlockStateProperties;
import org.dawnoftimebuilder.utils.DoTBBlockUtils;

import javax.annotation.Nonnull;

import static net.minecraft.util.Direction.NORTH;

public class StoneBricksArrowslitBlock extends WaterloggedBlock {

	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	private static final VoxelShape[] SHAPES = DoTBBlockUtils.GenerateHorizontalShapes(new VoxelShape[]{
			VoxelShapes.or(
					makeCuboidShape(0.0D, 0.0D, 14.0D, 7.0D, 16.0D, 16.0D),
					makeCuboidShape(9.0D, 0.0D, 14.0D, 16.0D, 16.0D, 16.0D),
					makeCuboidShape(0.0D, 0.0D, 8.0D, 2.0D, 16.0D, 14.0D),
					makeCuboidShape(14.0D, 0.0D, 8.0D, 16.0D, 16.0D, 14.0D),
					makeCuboidShape(2.0D, 0.0D, 13.0D, 4.5D, 16.0D, 14.0D),
					makeCuboidShape(11.5D, 0.0D, 13.0D, 14.0D, 16.0D, 14.0D)
			)
	});

	public StoneBricksArrowslitBlock() {
		super("stone_bricks_arrowslit", Material.ROCK, 1.5F, 6.0F);
		this.setDefaultState(this.getDefaultState().with(FACING, NORTH).with(WATERLOGGED, false));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(FACING);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPES[state.get(FACING).getHorizontalIndex()];
	}

	@Nonnull
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return super.getStateForPlacement(context).with(FACING, context.getPlacementHorizontalFacing());
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean isSolid(BlockState state) {
		return true;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot){
		return state.with(FACING, rot.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return this.rotate(state, Rotation.CLOCKWISE_180);
	}
}
