package org.dawnoftimebuilder.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class DoubleChairBlock extends ChairBlock {

	public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

	public DoubleChairBlock(final Properties properties, final float offsetY) {
		super(properties, offsetY);
		this.registerDefaultState(this.defaultBlockState().setValue(DoubleChairBlock.HALF, Half.BOTTOM));
	}

	@Override
	protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(DoubleChairBlock.HALF);
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(final BlockPlaceContext context) {
		if (!context.getLevel().getBlockState(context.getClickedPos().above()).canBeReplaced(context)) {
			return null;
		}
		return super.getStateForPlacement(context).setValue(ChairBlock.FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	public void setPlacedBy(final Level worldIn, final BlockPos pos, final BlockState state, final LivingEntity placer, final ItemStack stack) {
		worldIn.setBlock(pos.above(), state.setValue(DoubleChairBlock.HALF, Half.TOP), 10);
	}

	@Override
	public InteractionResult use(final BlockState state, final Level worldIn, final BlockPos pos, final Player player, final InteractionHand handIn, final BlockHitResult hit) {
		if (state.getValue(DoubleChairBlock.HALF) == Half.TOP) {
			return InteractionResult.PASS;
		}

		return super.use(state, worldIn, pos, player, handIn, hit);
	}

	@Override
	public BlockState updateShape(final BlockState stateIn, final Direction facing, final BlockState facingState, final LevelAccessor worldIn, final BlockPos currentPos, final BlockPos facingPos) {
		final Direction halfDirection = stateIn.getValue(DoubleChairBlock.HALF) == Half.TOP ? Direction.DOWN : Direction.UP;
		if (facing == halfDirection && (facingState.getBlock() != this || facingState.getValue(DoubleChairBlock.HALF) == stateIn.getValue(DoubleChairBlock.HALF) || facingState.getValue(ChairBlock.FACING) != stateIn.getValue(ChairBlock.FACING))) {
			return Blocks.AIR.defaultBlockState();
		}
		return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public boolean canSurvive(final BlockState state, final LevelReader worldIn, final BlockPos pos) {
		if (state.getValue(DoubleChairBlock.HALF) != Half.TOP) {
			return true;
		}
		final BlockState bottomState = worldIn.getBlockState(pos.below());
		if (bottomState.getBlock() == this) {
			return bottomState.getValue(DoubleChairBlock.HALF) == Half.BOTTOM && bottomState.getValue(ChairBlock.FACING) == state.getValue(ChairBlock.FACING);
		}
		return false;
	}
}
