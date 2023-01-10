/**
 *
 */
package org.dawnoftimebuilder.block.german;

import java.util.HashMap;
import java.util.Map;

import org.dawnoftimebuilder.block.templates.WaterloggedBlock;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.EmptyFluid;
import net.minecraft.item.BucketItem;
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
public class PoolBlock extends WaterloggedBlock {
	/**
	 * @param propertiesIn
	 */
	public PoolBlock(final Properties propertiesIn) {
		super(propertiesIn);
		this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.NORTH, false).setValue(BlockStateProperties.EAST, false).setValue(BlockStateProperties.SOUTH, false).setValue(BlockStateProperties.WEST, false).setValue(DoTBBlockStateProperties.HAS_WALL, false).setValue(BlockStateProperties.WATERLOGGED, false));
	}

	@Override
	protected void createBlockStateDefinition(final StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(BlockStateProperties.NORTH).add(BlockStateProperties.EAST).add(BlockStateProperties.SOUTH).add(BlockStateProperties.WEST).add(DoTBBlockStateProperties.HAS_WALL);
	}

	private final static Map<BlockPos, BlockState> REMOVE_WATER_MAP = new HashMap<>();

	/**
	 * Seynax : binary method to remove water on all associated pool
	 */
	public final static boolean removeWater(final Map<BlockPos, BlockState> testedsPositionsIn, BlockState blockStateIn, final BlockPos blockPosIn, final World worldIn, final float prohibitedXIn, final float prohibitedZIn) {

		boolean success = false;
		if (blockStateIn.getValue(BlockStateProperties.WATERLOGGED)) {
			success = true;
		}

		blockStateIn = blockStateIn.setValue(BlockStateProperties.WATERLOGGED, false);
		worldIn.setBlock(blockPosIn, blockStateIn, 10);

		if (prohibitedXIn != 1) {
			final BlockPos pos = blockPosIn.offset(1, 0, 0);
			if (!testedsPositionsIn.containsKey(pos)) {
				final BlockState state = worldIn.getBlockState(pos);
				if (state.getBlock() instanceof PoolBlock) {
					testedsPositionsIn.put(pos, state);
					if (PoolBlock.removeWater(testedsPositionsIn, state, pos, worldIn, 1, 0)) {
						success = true;
					}
				}
			}
		}
		if (prohibitedXIn != -1) {
			final BlockPos pos = blockPosIn.offset(-1, 0, 0);
			if (!testedsPositionsIn.containsKey(pos)) {
				final BlockState state = worldIn.getBlockState(pos);
				if (state.getBlock() instanceof PoolBlock) {
					testedsPositionsIn.put(pos, state);
					if (PoolBlock.removeWater(testedsPositionsIn, state, pos, worldIn, -1, 0)) {
						success = true;
					}
				}
			}
		}

		if (prohibitedZIn != 1) {
			final BlockPos pos = blockPosIn.offset(0, 0, 1);
			if (!testedsPositionsIn.containsKey(pos)) {
				final BlockState state = worldIn.getBlockState(pos);
				if (state.getBlock() instanceof PoolBlock) {
					testedsPositionsIn.put(pos, state);
					if (PoolBlock.removeWater(testedsPositionsIn, state, pos, worldIn, 0, 1)) {
						success = true;
					}
				}
			}
		}
		if (prohibitedZIn != -1) {
			final BlockPos pos = blockPosIn.offset(0, 0, -1);
			if (!testedsPositionsIn.containsKey(pos)) {
				final BlockState state = worldIn.getBlockState(pos);
				if (state.getBlock() instanceof PoolBlock) {
					testedsPositionsIn.put(pos, state);
					if (PoolBlock.removeWater(testedsPositionsIn, state, pos, worldIn, 0, -1)) {
						success = true;
					}
				}
			}
		}

		return success;
	}

	@Override
	public ActionResultType use(BlockState blockStateIn, final World worldIn, final BlockPos blockPosIn, final PlayerEntity playerEntityIn, final Hand handIn, final BlockRayTraceResult blockRayTraceResultIn) {
		if (playerEntityIn.isShiftKeyDown()) {
			/*
			 * Seynax : allows the player to remove the contents of all stuck basins, with a single click with an empty bucket while sneaking
			 */
			final ItemStack itemStack = playerEntityIn.getMainHandItem();
			if ((itemStack == null) || !(itemStack.getItem() instanceof BucketItem)) {
				blockStateIn = blockStateIn.setValue(DoTBBlockStateProperties.HAS_WALL, !blockStateIn.getValue(DoTBBlockStateProperties.HAS_WALL));

				worldIn.setBlock(blockPosIn, blockStateIn, 10);

				return ActionResultType.SUCCESS;
			}
			if (((BucketItem) itemStack.getItem()).getFluid() instanceof EmptyFluid) {
				PoolBlock.REMOVE_WATER_MAP.clear();
				if (PoolBlock.removeWater(PoolBlock.REMOVE_WATER_MAP, blockStateIn, blockPosIn, worldIn, 0, 0)) {
					return ActionResultType.SUCCESS;
				}
			}
		}

		return ActionResultType.PASS;
	}

	/*
	 * Seynax : allows the player to prevent the appearance of a pole in the basin below, under sneak conditions
	 */
	@Override
	public void setPlacedBy(final World worldIn, final BlockPos blockPosIn, final BlockState blockStateIn, final LivingEntity entityIn, final ItemStack itemStackIn) {
		if (entityIn instanceof PlayerEntity && !((PlayerEntity) entityIn).isShiftKeyDown()) {
			super.setPlacedBy(worldIn, blockPosIn, blockStateIn, entityIn, itemStackIn);

			final BlockPos	blockPos	= blockPosIn.below();
			BlockState		blockState	= worldIn.getBlockState(blockPos);
			if (blockState.getBlock() instanceof PoolBlock) {
				blockState = blockState.setValue(DoTBBlockStateProperties.HAS_WALL, true);
				worldIn.setBlock(blockPos, blockState, 10);
			}
		}

	}

	@Override
	public BlockState updateShape(BlockState stateIn, final Direction facingIn, final BlockState facingStateIn, final IWorld worldIn, final BlockPos currentPosIn, final BlockPos facingPosIn) {
		final BlockState	state			= facingStateIn;

		final boolean		hasPoolInSide	= state.getBlock() instanceof PoolBlock;

		switch (facingIn) {
			case NORTH:
				stateIn = stateIn.setValue(BlockStateProperties.NORTH, hasPoolInSide);

				if (hasPoolInSide && facingStateIn.getValue(BlockStateProperties.WATERLOGGED)) {
					stateIn = stateIn.setValue(BlockStateProperties.WATERLOGGED, true);
				}

				break;
			case EAST:
				stateIn = stateIn.setValue(BlockStateProperties.EAST, hasPoolInSide);

				if (hasPoolInSide && facingStateIn.getValue(BlockStateProperties.WATERLOGGED)) {
					stateIn = stateIn.setValue(BlockStateProperties.WATERLOGGED, true);
				}

				break;
			case SOUTH:
				stateIn = stateIn.setValue(BlockStateProperties.SOUTH, hasPoolInSide);

				if (hasPoolInSide && facingStateIn.getValue(BlockStateProperties.WATERLOGGED)) {
					stateIn = stateIn.setValue(BlockStateProperties.WATERLOGGED, true);
				}

				break;
			case WEST:
				stateIn = stateIn.setValue(BlockStateProperties.WEST, hasPoolInSide);

				if (hasPoolInSide && facingStateIn.getValue(BlockStateProperties.WATERLOGGED)) {
					stateIn = stateIn.setValue(BlockStateProperties.WATERLOGGED, true);
				}
				break;
			default:
				break;
		}
		worldIn.setBlock(currentPosIn, stateIn, 10);

		return super.updateShape(stateIn, facingIn, facingStateIn, worldIn, currentPosIn, facingPosIn);
	}
}