package org.dawnoftimebuilder.block.builders;

import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import org.dawnoftimebuilder.utils.DoTBBlockStateProperties;

public class SlabBlock extends BlockDoTB implements IWaterLoggable {

	public static final EnumProperty<DoTBBlockStateProperties.Slab> SLAB = DoTBBlockStateProperties.SLAB;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	private static final VoxelShape VS_BOTTOM = net.minecraft.block.Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
	private static final VoxelShape VS_TOP = net.minecraft.block.Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);

	public SlabBlock(String name, net.minecraft.block.Block.Properties properties) {
		super(name, properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(SLAB, DoTBBlockStateProperties.Slab.BOTTOM).with(WATERLOGGED, false));
	}

	public SlabBlock(String name, Material materialIn, float hardness, float resistance) {
		this(name, net.minecraft.block.Block.Properties.create(materialIn).hardnessAndResistance(hardness, resistance));
	}

	public SlabBlock(String name, net.minecraft.block.Block block) {
		this(name, net.minecraft.block.Block.Properties.from(block));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<net.minecraft.block.Block, BlockState> builder) {
		builder.add(SLAB, WATERLOGGED);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch (state.get(SLAB)) {
			default:
			case BOTTOM:
				return VS_BOTTOM;
			case TOP:
				return VS_TOP;
			case DOUBLE:
				return VoxelShapes.fullCube();
		}
	}

	@Override
	public IFluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	@Override
	public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, IFluidState fluidStateIn) {
		if(state.get(SLAB) == DoTBBlockStateProperties.Slab.DOUBLE) return false;
		return IWaterLoggable.super.receiveFluid(worldIn, pos, state, fluidStateIn);
	}

	@Override
	public boolean canContainFluid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
		if(state.get(SLAB) == DoTBBlockStateProperties.Slab.DOUBLE) return false;
		return IWaterLoggable.super.canContainFluid(worldIn, pos, state, fluidIn);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockPos pos = context.getPos();
		BlockState blockstate = context.getWorld().getBlockState(pos);
		if (blockstate.getBlock() == this) {
			return blockstate.with(SLAB, DoTBBlockStateProperties.Slab.DOUBLE).with(WATERLOGGED, false);
		} else {
			IFluidState ifluidstate = context.getWorld().getFluidState(pos);
			Direction direction = context.getFace();
			boolean bottom = (direction != Direction.DOWN && (direction == Direction.UP || !(context.getHitVec().y - (double)pos.getY() > 0.5D)));
			return this.getDefaultState().with(SLAB, bottom ? DoTBBlockStateProperties.Slab.BOTTOM : DoTBBlockStateProperties.Slab.TOP).with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
		}
	}

	public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
		ItemStack itemstack = useContext.getItem();
		DoTBBlockStateProperties.Slab slab = state.get(SLAB);
		if (slab != DoTBBlockStateProperties.Slab.DOUBLE && itemstack.getItem() == this.asItem()) {
			if (useContext.replacingClickedOnBlock()) {
				boolean flag = useContext.getHitVec().y - (double)useContext.getPos().getY() > 0.5D;
				Direction direction = useContext.getFace();
				if (slab == DoTBBlockStateProperties.Slab.BOTTOM) {
					return direction == Direction.UP || flag && direction.getAxis().isHorizontal();
				} else {
					return direction == Direction.DOWN || !flag && direction.getAxis().isHorizontal();
				}
			}
		}
		return false;
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.get(WATERLOGGED)) worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}
}
