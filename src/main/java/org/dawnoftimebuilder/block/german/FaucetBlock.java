/**
 *
 */
package org.dawnoftimebuilder.block.german;

import org.dawnoftimebuilder.block.templates.BlockDoTB;
import org.dawnoftimebuilder.registry.DoTBBlocksRegistry;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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

/**
 * @author seyro
 *
 */
public class FaucetBlock extends BlockDoTB {
	/**
	 * @param propertiesIn
	 */
	public FaucetBlock(final Properties propertiesIn) {
		super(propertiesIn);
		this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.UP, false).setValue(BlockStateProperties.DOWN, false).setValue(DoTBBlockStateProperties.NORTHSTATE, DoTBBlockStateProperties.VerticalLimitedConnection.NONE).setValue(DoTBBlockStateProperties.EASTSTATE, DoTBBlockStateProperties.VerticalLimitedConnection.NONE).setValue(DoTBBlockStateProperties.SOUTHSTATE, DoTBBlockStateProperties.VerticalLimitedConnection.NONE)
				.setValue(DoTBBlockStateProperties.WESTSTATE, DoTBBlockStateProperties.VerticalLimitedConnection.NONE).setValue(BlockStateProperties.POWERED, false).setValue(DoTBBlockStateProperties.ACTIVATED, false));
	}

	@Override
	protected void createBlockStateDefinition(final StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(BlockStateProperties.UP).add(BlockStateProperties.DOWN).add(DoTBBlockStateProperties.NORTHSTATE).add(DoTBBlockStateProperties.EASTSTATE).add(DoTBBlockStateProperties.SOUTHSTATE).add(DoTBBlockStateProperties.WESTSTATE).add(BlockStateProperties.POWERED).add(DoTBBlockStateProperties.ACTIVATED);
	}

	@Override
	public BlockState getStateForPlacement(final BlockItemUseContext context) {

		BlockState state = context.getLevel().getBlockState(context.getClickedPos());
		if (state.getBlock() != this) {
			state = super.getStateForPlacement(context);
		}
		switch (context.getClickedFace()) {
			default:
			case UP:
				return state.setValue(BlockStateProperties.UP, true);
			case DOWN:
				return state.setValue(BlockStateProperties.DOWN, true);
			case SOUTH:
				return state.setValue(DoTBBlockStateProperties.NORTHSTATE, DoTBBlockStateProperties.VerticalLimitedConnection.NONE);
			case WEST:
				return state.setValue(DoTBBlockStateProperties.EASTSTATE, DoTBBlockStateProperties.VerticalLimitedConnection.NONE);
			case NORTH:
				return state.setValue(DoTBBlockStateProperties.SOUTHSTATE, DoTBBlockStateProperties.VerticalLimitedConnection.NONE);
			case EAST:
				return state.setValue(DoTBBlockStateProperties.WESTSTATE, DoTBBlockStateProperties.VerticalLimitedConnection.NONE);
		}
	}

	@Override
	public boolean canBeReplaced(final BlockState state, final BlockItemUseContext useContext) {
		final ItemStack itemstack = useContext.getItemInHand();
		if (useContext.getPlayer() != null && useContext.getPlayer().isCrouching()) {
			return false;
		}
		if (itemstack.getItem() == this.asItem()) {
			final Direction newDirection = useContext.getClickedFace();
			switch (newDirection) {
				default:
				case UP:
					return !state.getValue(BlockStateProperties.UP);
				case DOWN:
					return !state.getValue(BlockStateProperties.DOWN);
				case SOUTH:
					return DoTBBlockStateProperties.VerticalLimitedConnection.NONE.equals(state.getValue(DoTBBlockStateProperties.NORTHSTATE));
				case WEST:
					return DoTBBlockStateProperties.VerticalLimitedConnection.NONE.equals(state.getValue(DoTBBlockStateProperties.EASTSTATE));
				case NORTH:
					return DoTBBlockStateProperties.VerticalLimitedConnection.NONE.equals(state.getValue(DoTBBlockStateProperties.SOUTHSTATE));
				case EAST:
					return DoTBBlockStateProperties.VerticalLimitedConnection.NONE.equals(state.getValue(DoTBBlockStateProperties.WESTSTATE));
			}
		}
		return false;
	}

	@Override
	public ActionResultType use(BlockState blockStateIn, final World worldIn, final BlockPos blockPosIn, final PlayerEntity playerEntityIn, final Hand handIn, final BlockRayTraceResult blockRaytraceResultIn) {

		final ItemStack mainHandItemStack = playerEntityIn.getMainHandItem();
		if (mainHandItemStack != null && mainHandItemStack.getItem() == this.asItem()) {
			return ActionResultType.PASS;
		}

		final boolean activated = !blockStateIn.getValue(DoTBBlockStateProperties.ACTIVATED);
		blockStateIn = blockStateIn.setValue(DoTBBlockStateProperties.ACTIVATED, activated);
		worldIn.setBlock(blockPosIn, blockStateIn, 10);

		final BlockPos below = blockPosIn.below();
		if (activated) {
			if (worldIn.getBlockState(below).getBlock() instanceof AirBlock) {
				final BlockPos		belowBlockPosOfBelow	= below.below();
				final BlockState	bottomBlockStateOfBelow	= worldIn.getBlockState(belowBlockPosOfBelow);

				if (bottomBlockStateOfBelow.getBlock() instanceof AirBlock) {
					final BlockState state = DoTBBlocksRegistry.WATER_MOVING_TRICKLE.get().defaultBlockState().setValue(BlockStateProperties.NORTH, !DoTBBlockStateProperties.VerticalLimitedConnection.NONE.equals(blockStateIn.getValue(DoTBBlockStateProperties.NORTHSTATE))).setValue(BlockStateProperties.EAST, !DoTBBlockStateProperties.VerticalLimitedConnection.NONE.equals(blockStateIn.getValue(DoTBBlockStateProperties.EASTSTATE)))
							.setValue(BlockStateProperties.SOUTH, DoTBBlockStateProperties.VerticalLimitedConnection.NONE.equals(blockStateIn.getValue(DoTBBlockStateProperties.SOUTHSTATE))).setValue(BlockStateProperties.WEST, !DoTBBlockStateProperties.VerticalLimitedConnection.NONE.equals(blockStateIn.getValue(DoTBBlockStateProperties.WESTSTATE)));

					worldIn.setBlock(below, state, 10);
				}
				else {
					final BlockState state = DoTBBlocksRegistry.WATER_TRICKLE.get().defaultBlockState().setValue(BlockStateProperties.NORTH, !DoTBBlockStateProperties.VerticalLimitedConnection.NONE.equals(blockStateIn.getValue(DoTBBlockStateProperties.NORTHSTATE))).setValue(BlockStateProperties.EAST, !DoTBBlockStateProperties.VerticalLimitedConnection.NONE.equals(blockStateIn.getValue(DoTBBlockStateProperties.EASTSTATE)))
							.setValue(BlockStateProperties.SOUTH, DoTBBlockStateProperties.VerticalLimitedConnection.NONE.equals(blockStateIn.getValue(DoTBBlockStateProperties.SOUTHSTATE))).setValue(BlockStateProperties.WEST, !DoTBBlockStateProperties.VerticalLimitedConnection.NONE.equals(blockStateIn.getValue(DoTBBlockStateProperties.WESTSTATE)))

							.setValue(DoTBBlockStateProperties.FLOOR, /**!(bottomBlockStateOfBelow.getBlock() instanceof AirBlock) &&**/
									Block.canSupportCenter(worldIn, belowBlockPosOfBelow, Direction.UP));

					worldIn.setBlock(below, state, 10);
				}
			}
		}
		else {
			final BlockState bottomState = worldIn.getBlockState(below);

			if (bottomState.getBlock() instanceof WaterTrickleBlock || bottomState.getBlock() instanceof WaterMovingTrickleBlock) {
				worldIn.setBlock(below, Blocks.AIR.defaultBlockState(), 10);
			}
		}

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
