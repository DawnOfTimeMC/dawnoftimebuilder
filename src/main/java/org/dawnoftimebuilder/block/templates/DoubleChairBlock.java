package org.dawnoftimebuilder.block.templates;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class DoubleChairBlock extends ChairBlock {

	public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

	public DoubleChairBlock(final Properties properties, final float offsetY) {
		super(properties, offsetY);
		this.registerDefaultState(this.defaultBlockState().setValue(DoubleChairBlock.HALF, Half.BOTTOM));
	}

	@Override
	protected void createBlockStateDefinition(final StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(DoubleChairBlock.HALF);
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(final BlockItemUseContext context) {
		if (!context.getLevel().getBlockState(context.getClickedPos().above()).canBeReplaced(context)) {
			return null;
		}
		return super.getStateForPlacement(context).setValue(ChairBlock.FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	public void setPlacedBy(final World worldIn, final BlockPos pos, final BlockState state, final LivingEntity placer, final ItemStack stack) {
		worldIn.setBlock(pos.above(), state.setValue(DoubleChairBlock.HALF, Half.TOP), 10);
	}

	@Override
	public ActionResultType use(final BlockState state, final World worldIn, final BlockPos pos, final PlayerEntity player, final Hand handIn, final BlockRayTraceResult hit) {
		if (state.getValue(DoubleChairBlock.HALF) == Half.TOP) {
			return ActionResultType.PASS;
		}

		return super.use(state, worldIn, pos, player, handIn, hit);
	}

	@Override
	public BlockState updateShape(final BlockState stateIn, final Direction facing, final BlockState facingState, final IWorld worldIn, final BlockPos currentPos, final BlockPos facingPos) {
		final Direction halfDirection = stateIn.getValue(DoubleChairBlock.HALF) == Half.TOP ? Direction.DOWN : Direction.UP;
		if (facing == halfDirection && (facingState.getBlock() != this || facingState.getValue(DoubleChairBlock.HALF) == stateIn.getValue(DoubleChairBlock.HALF) || facingState.getValue(ChairBlock.FACING) != stateIn.getValue(ChairBlock.FACING))) {
			return Blocks.AIR.defaultBlockState();
		}
		return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public boolean canSurvive(final BlockState state, final IWorldReader worldIn, final BlockPos pos) {
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
