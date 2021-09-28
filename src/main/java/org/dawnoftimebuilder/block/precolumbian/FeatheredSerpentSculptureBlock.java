package org.dawnoftimebuilder.block.precolumbian;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import org.dawnoftimebuilder.block.templates.WaterloggedBlock;
import org.dawnoftimebuilder.util.DoTBBlockUtils;

import javax.annotation.Nonnull;

public class FeatheredSerpentSculptureBlock extends WaterloggedBlock {

	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	private static final VoxelShape[] SHAPES = DoTBBlockUtils.GenerateHorizontalShapes(new VoxelShape[]{
			Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 14.0D)
	});

	public FeatheredSerpentSculptureBlock(Material materialIn, float hardness, float resistance, SoundType soundType) {
		super(Properties.of(materialIn).strength(hardness, resistance).sound(soundType));
	    this.registerDefaultState(this.stateContainer.getBaseState().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPES[state.get(FACING).get2DDataValue()];
	}

	@Nonnull
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	public boolean isSolid(BlockState state) {
		return true;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot){
		return state.setValue(FACING, rot.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return this.rotate(state, Rotation.CLOCKWISE_180);
	}
}
