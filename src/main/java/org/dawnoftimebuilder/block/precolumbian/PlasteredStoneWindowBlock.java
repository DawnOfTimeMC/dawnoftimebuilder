package org.dawnoftimebuilder.block.precolumbian;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import org.dawnoftimebuilder.block.templates.WaterloggedBlock;

public class PlasteredStoneWindowBlock extends WaterloggedBlock {

	public static final EnumProperty<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;
	private static final VoxelShape X_AXIS_VS = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
    private static final VoxelShape Z_AXIS_VS = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);
	
	public PlasteredStoneWindowBlock(Material materialIn, float hardness, float resistance, SoundType soundType) {
		super(Properties.of(materialIn).strength(hardness, resistance).sound(soundType));
	    this.registerDefaultState(this.stateContainer.getBaseState().setValue(HORIZONTAL_AXIS, Direction.Axis.X));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<net.minecraft.block.Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(HORIZONTAL_AXIS);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		if(state.get(HORIZONTAL_AXIS) == Direction.Axis.X) return X_AXIS_VS;
		else return Z_AXIS_VS;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return super.getStateForPlacement(context).setValue(HORIZONTAL_AXIS, context.getHorizontalDirection().getAxis() == Direction.Axis.Z ? Direction.Axis.X : Direction.Axis.Z);
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		if(rot == Rotation.CLOCKWISE_90 || rot == Rotation.COUNTERCLOCKWISE_90) return state.setValue(HORIZONTAL_AXIS, (state.get(HORIZONTAL_AXIS) == Direction.Axis.X) ? Direction.Axis.Z : Direction.Axis.X);
		else return super.rotate(state, rot);
	}
}
