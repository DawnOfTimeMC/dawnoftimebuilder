package org.dawnoftimebuilder.block.templates;

import javax.annotation.Nullable;

import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoorHingeSide;
import net.minecraft.state.properties.Half;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class ShutterBlock extends SmallShutterBlock
{

	public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

	public ShutterBlock(final Properties properties)
	{
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(ShutterBlock.HALF, Half.BOTTOM));
	}

	@Override
	protected void createBlockStateDefinition(final StateContainer.Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder);
		builder.add(ShutterBlock.HALF);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(final BlockItemUseContext context)
	{
		final World		world		= context.getLevel();
		final Direction	direction	= context.getHorizontalDirection();
		final BlockPos	pos			= context.getClickedPos();
		if (!world.getBlockState(pos.above()).canBeReplaced(context))
		{
			return null;
		}
		final int		x			= direction.getStepX();
		final int		z			= direction.getStepZ();
		final double	onX			= context.getClickLocation().x - pos.getX();
		final double	onZ			= context.getClickLocation().z - pos.getZ();
		final boolean	hingeLeft	= (x >= 0 || onZ >= 0.5D) && (x <= 0 || onZ <= 0.5D) && (z >= 0 || onX <= 0.5D) && (z <= 0 || onX >= 0.5D);
		return super.getStateForPlacement(context).setValue(SmallShutterBlock.HINGE, hingeLeft ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT).setValue(SmallShutterBlock.FACING, direction)
				.setValue(SmallShutterBlock.POWERED, world.hasNeighborSignal(pos)).setValue(ShutterBlock.HALF, Half.BOTTOM);
	}

	@Override
	public boolean canSurvive(final BlockState state, final IWorldReader worldIn, final BlockPos pos)
	{
		if (state.getValue(ShutterBlock.HALF) != Half.TOP)
		{
			return true;
		}
		final BlockState bottomState = worldIn.getBlockState(pos.below());
		if (bottomState.getBlock() == this)
		{
			return bottomState.getValue(ShutterBlock.HALF) == Half.BOTTOM && bottomState.getValue(SmallShutterBlock.FACING) == state.getValue(SmallShutterBlock.FACING)
					&& bottomState.getValue(SmallShutterBlock.HINGE) == state.getValue(SmallShutterBlock.HINGE);
		}
		return false;
	}

	@Override
	public void setPlacedBy(final World worldIn, final BlockPos pos, final BlockState state, @Nullable final LivingEntity entity, final ItemStack itemStack)
	{
		worldIn.setBlock(pos.above(), state.setValue(ShutterBlock.HALF, Half.TOP), 10);
	}

	@Override
	public BlockState updateShape(BlockState stateIn, final Direction facing, final BlockState facingState, final IWorld worldIn, final BlockPos currentPos, final BlockPos facingPos)
	{
		final Direction halfDirection = stateIn.getValue(ShutterBlock.HALF) == Half.TOP ? Direction.DOWN : Direction.UP;
		if (facing == halfDirection)
		{
			if (facingState.getBlock() != this || facingState.getValue(ShutterBlock.HALF) == stateIn.getValue(ShutterBlock.HALF) || facingState.getValue(SmallShutterBlock.FACING) != stateIn.getValue(SmallShutterBlock.FACING)
					|| facingState.getValue(SmallShutterBlock.HINGE) != stateIn.getValue(SmallShutterBlock.HINGE))
			{
				return Blocks.AIR.defaultBlockState();
			}
			stateIn = stateIn.setValue(SmallShutterBlock.OPEN_POSITION, facingState.getValue(SmallShutterBlock.OPEN_POSITION));
		}
		return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	protected DoTBBlockStateProperties.OpenPosition getOpenState(final BlockState stateIn, final IWorld worldIn, final BlockPos pos)
	{
		final BlockPos secondPos = pos.relative(stateIn.getValue(ShutterBlock.HALF) == Half.TOP ? Direction.DOWN : Direction.UP);
		if (!worldIn.getBlockState(secondPos).getCollisionShape(worldIn, pos).isEmpty() || !worldIn.getBlockState(pos).getCollisionShape(worldIn, pos).isEmpty())
		{
			return DoTBBlockStateProperties.OpenPosition.HALF;
		}
		return DoTBBlockStateProperties.OpenPosition.FULL;
	}
}