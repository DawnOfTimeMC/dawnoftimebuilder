package org.dawnoftimebuilder.block.french;

import org.dawnoftimebuilder.block.templates.BaseChimneyDoTB;
import org.dawnoftimebuilder.block.templates.ColumnConnectibleBlock;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class LimestoneChimneyBlock extends BaseChimneyDoTB {
	public static final EnumProperty<Direction.Axis>	HORIZONTAL_AXIS	= BlockStateProperties.HORIZONTAL_AXIS;
	private static final VoxelShape[]					SHAPES			= LimestoneChimneyBlock.makeShapes();

	public LimestoneChimneyBlock(final Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(LimestoneChimneyBlock.HORIZONTAL_AXIS, Direction.Axis.X).setValue(BaseChimneyDoTB.LIT, true));
	}

	@Override
	protected void createBlockStateDefinition(final StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(LimestoneChimneyBlock.HORIZONTAL_AXIS);
	}

	@Override
	public VoxelShape getShape(final BlockState state, final IBlockReader worldIn, final BlockPos pos, final ISelectionContext context) {
		if (state.getValue(ColumnConnectibleBlock.VERTICAL_CONNECTION) == DoTBBlockStateProperties.VerticalConnection.NONE) {
			return LimestoneChimneyBlock.SHAPES[0];
		}
		return LimestoneChimneyBlock.SHAPES[state.getValue(ColumnConnectibleBlock.VERTICAL_CONNECTION).getIndex() - 1];
	}

	@Override
	public BlockState getStateForPlacement(final BlockItemUseContext context) {
		final BlockState state = super.getStateForPlacement(context);
		return state.setValue(LimestoneChimneyBlock.HORIZONTAL_AXIS, context.getHorizontalDirection().getAxis() == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X).setValue(BaseChimneyDoTB.LIT, true);
	}

	/**
	 * @return Stores VoxelShape with index : <p/>
	 * 0 : None or Under <p/>
	 * 1 : Above
	 * 2 : Both
	 */
	private static VoxelShape[] makeShapes() {
		return new VoxelShape[] {
				VoxelShapes.or(Block.box(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D), Block.box(4.0D, 8.0D, 4.0D, 12.0D, 16.0D, 12.0D)), VoxelShapes.or(Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.box(2.0D, 8.0D, 2.0D, 14.0D, 16.0D, 14.0D)), Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D)
		};
	}
}
