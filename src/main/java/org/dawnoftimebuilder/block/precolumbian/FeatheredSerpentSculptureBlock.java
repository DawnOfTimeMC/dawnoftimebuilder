package org.dawnoftimebuilder.block.precolumbian;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import org.dawnoftimebuilder.block.templates.WaterloggedBlock;
import org.dawnoftimebuilder.util.DoTBUtils;

import javax.annotation.Nonnull;

public class FeatheredSerpentSculptureBlock extends WaterloggedBlock {

	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	private static final VoxelShape[] SHAPES = DoTBUtils.GenerateHorizontalShapes(new VoxelShape[]{
			Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 14.0D)
	});

	public FeatheredSerpentSculptureBlock(Properties properties) {
		super(properties);
	    this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPES[state.getValue(FACING).get2DDataValue()];
	}

	@Nonnull
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot){
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return this.rotate(state, Rotation.CLOCKWISE_180);
	}
}
