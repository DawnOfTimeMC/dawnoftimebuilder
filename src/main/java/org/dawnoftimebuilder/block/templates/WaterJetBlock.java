package org.dawnoftimebuilder.block.templates;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

public class WaterJetBlock extends BlockDoTB {

	public WaterJetBlock(final Properties propertiesIn) {
		super(propertiesIn);
		this.registerDefaultState(this.defaultBlockState()
				.setValue(BlockStateProperties.UP, false)
				.setValue(BlockStateProperties.DOWN, false)
				.setValue(DoTBBlockStateProperties.NORTH_STATE, DoTBBlockStateProperties.VerticalLimitedConnection.NONE)
				.setValue(DoTBBlockStateProperties.EAST_STATE, DoTBBlockStateProperties.VerticalLimitedConnection.NONE)
				.setValue(DoTBBlockStateProperties.SOUTH_STATE, DoTBBlockStateProperties.VerticalLimitedConnection.NONE)
				.setValue(DoTBBlockStateProperties.WEST_STATE, DoTBBlockStateProperties.VerticalLimitedConnection.NONE)
				.setValue(BlockStateProperties.POWERED, false)
				.setValue(DoTBBlockStateProperties.ACTIVATED, false));
	}

	@Override
	protected void createBlockStateDefinition(final StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(
				BlockStateProperties.UP,
				BlockStateProperties.DOWN,
				DoTBBlockStateProperties.NORTH_STATE,
				DoTBBlockStateProperties.EAST_STATE,
				DoTBBlockStateProperties.SOUTH_STATE,
				DoTBBlockStateProperties.WEST_STATE,
				BlockStateProperties.POWERED,
				DoTBBlockStateProperties.ACTIVATED);
	}

	@Override
	public BlockState getStateForPlacement(final BlockItemUseContext context) {
		BlockState state = context.getLevel().getBlockState(context.getClickedPos());
		if (state.getBlock() != this) {
			state = super.getStateForPlacement(context);
		}
		if(state == null){
			return this.defaultBlockState().setValue(BlockStateProperties.DOWN, true);
		}
		DoTBBlockStateProperties.VerticalLimitedConnection clickedFace = (context.getClickLocation().y - context.getClickedPos().getY() <= 0.5D) ? DoTBBlockStateProperties.VerticalLimitedConnection.BOTTOM : DoTBBlockStateProperties.VerticalLimitedConnection.TOP;
		switch (context.getClickedFace()) {
			default:
			case UP:
				return state.setValue(BlockStateProperties.DOWN, true);
			case DOWN:
				return state.setValue(BlockStateProperties.UP, true);
			case SOUTH:
				return state.setValue(DoTBBlockStateProperties.NORTH_STATE, clickedFace);
			case WEST:
				return state.setValue(DoTBBlockStateProperties.EAST_STATE, clickedFace);
			case NORTH:
				return state.setValue(DoTBBlockStateProperties.SOUTH_STATE, clickedFace);
			case EAST:
				return state.setValue(DoTBBlockStateProperties.WEST_STATE, clickedFace);
		}
	}

	@Override
	public boolean canBeReplaced(final BlockState state, final BlockItemUseContext useContext) {
		final ItemStack itemstack = useContext.getItemInHand();
		if (useContext.getPlayer() != null && useContext.getPlayer().isCrouching()) {
			return false;
		}
		if (itemstack.getItem() == this.asItem()) {
			final Direction newDirection = useContext.getNearestLookingDirection();
			switch (newDirection) {
				default:
				case UP:
					return !state.getValue(BlockStateProperties.UP);
				case DOWN:
					return !state.getValue(BlockStateProperties.DOWN);
				case SOUTH:
					return DoTBBlockStateProperties.VerticalLimitedConnection.NONE.equals(state.getValue(DoTBBlockStateProperties.NORTH_STATE));
				case WEST:
					return DoTBBlockStateProperties.VerticalLimitedConnection.NONE.equals(state.getValue(DoTBBlockStateProperties.EAST_STATE));
				case NORTH:
					return DoTBBlockStateProperties.VerticalLimitedConnection.NONE.equals(state.getValue(DoTBBlockStateProperties.SOUTH_STATE));
				case EAST:
					return DoTBBlockStateProperties.VerticalLimitedConnection.NONE.equals(state.getValue(DoTBBlockStateProperties.WEST_STATE));
			}
		}
		return false;
	}

	@Override
	public ActionResultType use(BlockState blockStateIn, final World worldIn, final BlockPos blockPosIn, final PlayerEntity playerEntityIn, final Hand handIn, final BlockRayTraceResult blockRaytraceResultIn) {

		final ItemStack mainHandItemStack = playerEntityIn.getMainHandItem();
		if (!mainHandItemStack.isEmpty() && mainHandItemStack.getItem() == this.asItem()) {
			return ActionResultType.PASS;
		}
		blockStateIn = blockStateIn.setValue(DoTBBlockStateProperties.ACTIVATED, !blockStateIn.getValue(DoTBBlockStateProperties.ACTIVATED));
		worldIn.setBlock(blockPosIn, blockStateIn, 10);

		return ActionResultType.SUCCESS;
	}

	@Override
	public BlockState updateShape(BlockState stateIn, final Direction directionIn, final BlockState facingStateIn, final IWorld worldIn, final BlockPos currentPosIn, final BlockPos facingPosIn) {
		if (Direction.DOWN.equals(directionIn)) {
			stateIn = stateIn.setValue(DoTBBlockStateProperties.ACTIVATED, facingStateIn.getBlock() instanceof WaterMovingTrickleBlock || facingStateIn.getBlock() instanceof WaterTrickleBlock);
		}

		return stateIn;
	}
}