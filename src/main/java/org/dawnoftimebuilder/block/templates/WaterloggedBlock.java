package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import javax.annotation.Nonnull;

public class WaterloggedBlock extends BlockDoTB implements IWaterLoggable {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public WaterloggedBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED,false));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.getValue(WATERLOGGED)) worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
		return stateIn;
	}

	@Override
	@Nonnull
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
	}
}
