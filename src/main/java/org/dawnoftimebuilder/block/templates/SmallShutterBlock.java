package org.dawnoftimebuilder.block.templates;

import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoorHingeSide;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class SmallShutterBlock extends WaterloggedBlock {

	public static final DirectionProperty									FACING			= BlockStateProperties.HORIZONTAL_FACING;
	public static final BooleanProperty										POWERED			= BlockStateProperties.POWERED;
	public static final EnumProperty<DoTBBlockStateProperties.OpenPosition>	OPEN_POSITION	= DoTBBlockStateProperties.OPEN_POSITION;
	public static final EnumProperty<DoorHingeSide>							HINGE			= BlockStateProperties.DOOR_HINGE;
	private static final VoxelShape[]										SHAPES			= DoTBUtils.GenerateHorizontalShapes(SmallShutterBlock.makeShapes());

	public SmallShutterBlock(final Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(SmallShutterBlock.OPEN_POSITION, DoTBBlockStateProperties.OpenPosition.CLOSED).setValue(SmallShutterBlock.HINGE, DoorHingeSide.LEFT).setValue(SmallShutterBlock.POWERED, false));
	}

	@Override
	protected void createBlockStateDefinition(final StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(SmallShutterBlock.FACING, SmallShutterBlock.HINGE, SmallShutterBlock.OPEN_POSITION, SmallShutterBlock.POWERED);
	}

	@Override
	public VoxelShape getShape(final BlockState state, final IBlockReader worldIn, final BlockPos pos, final ISelectionContext context) {
		int				index			= 0;
		int				horizontalIndex	= 0;
		final boolean	hinge			= state.getValue(SmallShutterBlock.HINGE) == DoorHingeSide.RIGHT;
		switch (state.getValue(SmallShutterBlock.OPEN_POSITION)) {
			case FULL:
				index = hinge ? 1 : 2;
			case CLOSED:
				horizontalIndex = state.getValue(SmallShutterBlock.FACING).get2DDataValue();
				break;
			case HALF:
				if (hinge) {
					horizontalIndex = state.getValue(SmallShutterBlock.FACING).getClockWise().get2DDataValue();
				}
				else {
					horizontalIndex = state.getValue(SmallShutterBlock.FACING).getCounterClockWise().get2DDataValue();
				}
				break;

		}
		return SmallShutterBlock.SHAPES[index + horizontalIndex * 3];
	}

	/**
	 * @return Stores VoxelShape for "South" with index : <p/>
	 * 0 : S Closed <p/>
	 * 1 : S Fully opened to the right <p/>
	 * 2 : S Fully opened to the left
	 */
	private static VoxelShape[] makeShapes() {
		return new VoxelShape[] {
				Block.box(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D), Block.box(-13.0D, 0.0D, 13.0D, 3.0D, 16.0D, 16.0D), Block.box(13.0D, 0.0D, 13.0D, 29.0D, 16.0D, 16.0D)
		};
	}

	@Override
	public PushReaction getPistonPushReaction(final BlockState state) {
		return PushReaction.DESTROY;
	}

	@Override
	public BlockState getStateForPlacement(final BlockItemUseContext context) {
		final World			world		= context.getLevel();
		final Direction		direction	= context.getHorizontalDirection();
		final BlockPos		pos			= context.getClickedPos();
		final int			x			= direction.getStepX();
		final int			z			= direction.getStepZ();
		final double		onX			= context.getClickLocation().x - pos.getX();
		final double		onZ			= context.getClickLocation().z - pos.getZ();
		final boolean		hingeLeft	= (x >= 0 || onZ >= 0.5D) && (x <= 0 || onZ <= 0.5D) && (z >= 0 || onX <= 0.5D) && (z <= 0 || onX >= 0.5D);
		final boolean		powered		= world.hasNeighborSignal(pos) || world.hasNeighborSignal(pos.above());
		final BlockState	madeState	= super.getStateForPlacement(context).setValue(SmallShutterBlock.HINGE, hingeLeft ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT).setValue(SmallShutterBlock.FACING, direction).setValue(SmallShutterBlock.POWERED, powered);
		return madeState.setValue(SmallShutterBlock.OPEN_POSITION, powered ? this.getOpenState(madeState, world, pos) : DoTBBlockStateProperties.OpenPosition.CLOSED);
	}

	@Override
	public BlockState updateShape(BlockState stateIn, final Direction facing, final BlockState facingState, final IWorld worldIn, final BlockPos currentPos, final BlockPos facingPos) {
		final Direction	direction		= stateIn.getValue(SmallShutterBlock.FACING);
		final Direction	hingeDirection	= stateIn.getValue(SmallShutterBlock.HINGE) == DoorHingeSide.LEFT ? direction.getCounterClockWise() : direction.getClockWise();
		if (facing == hingeDirection) {
			stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
			return stateIn.getValue(SmallShutterBlock.OPEN_POSITION) == DoTBBlockStateProperties.OpenPosition.CLOSED ? stateIn : stateIn.setValue(SmallShutterBlock.OPEN_POSITION, this.getOpenState(stateIn, worldIn, facingPos));
		}
		return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public ActionResultType use(BlockState state, final World worldIn, final BlockPos pos, final PlayerEntity player, final Hand handIn, final BlockRayTraceResult hit) {
		if (state.getValue(SmallShutterBlock.OPEN_POSITION).isOpen()) {
			state = state.setValue(SmallShutterBlock.OPEN_POSITION, DoTBBlockStateProperties.OpenPosition.CLOSED);
		}
		else {
			final Direction hingeDirection = state.getValue(SmallShutterBlock.HINGE) == DoorHingeSide.LEFT ? state.getValue(SmallShutterBlock.FACING).getCounterClockWise() : state.getValue(SmallShutterBlock.FACING).getClockWise();
			state = state.setValue(SmallShutterBlock.OPEN_POSITION, this.getOpenState(state, worldIn, pos.relative(hingeDirection)));
		}
		worldIn.setBlock(pos, state, 10);
		worldIn.levelEvent(player, state.getValue(SmallShutterBlock.OPEN_POSITION).isOpen() ? this.getOpenSound() : this.getCloseSound(), pos, 0);
		if (state.getValue(WaterloggedBlock.WATERLOGGED)) {
			worldIn.getLiquidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
		}
		return ActionResultType.SUCCESS;
	}

	@Override
	public void neighborChanged(BlockState state, final World worldIn, final BlockPos pos, final Block blockIn, final BlockPos fromPos, final boolean isMoving) {
		final boolean isPowered = worldIn.hasNeighborSignal(pos);
		if (blockIn != this && isPowered != state.getValue(SmallShutterBlock.POWERED)) {
			if (isPowered != state.getValue(SmallShutterBlock.OPEN_POSITION).isOpen()) {
				this.playSound(worldIn, pos, isPowered);
			}
			if (isPowered) {
				final Direction hingeDirection = state.getValue(SmallShutterBlock.HINGE) == DoorHingeSide.LEFT ? state.getValue(SmallShutterBlock.FACING).getCounterClockWise() : state.getValue(SmallShutterBlock.FACING).getClockWise();
				state = state.setValue(SmallShutterBlock.OPEN_POSITION, this.getOpenState(state, worldIn, pos.relative(hingeDirection)));
			}
			else {
				state = state.setValue(SmallShutterBlock.OPEN_POSITION, DoTBBlockStateProperties.OpenPosition.CLOSED);
			}
			worldIn.setBlock(pos, state.setValue(SmallShutterBlock.POWERED, isPowered), 2);
		}
	}

	protected DoTBBlockStateProperties.OpenPosition getOpenState(final BlockState stateIn, final IWorld worldIn, final BlockPos pos) {
		return worldIn.getBlockState(pos).getCollisionShape(worldIn, pos).isEmpty() ? DoTBBlockStateProperties.OpenPosition.FULL : DoTBBlockStateProperties.OpenPosition.HALF;
	}

	private void playSound(final World worldIn, final BlockPos pos, final boolean isOpening) {
		worldIn.levelEvent(null, isOpening ? this.getOpenSound() : this.getCloseSound(), pos, 0);
	}

	private int getCloseSound() {
		return 1012;
	}

	private int getOpenSound() {
		return 1006;
	}

	@Override
	public BlockState rotate(final BlockState state, final Rotation rot) {
		return state.setValue(SmallShutterBlock.FACING, rot.rotate(state.getValue(SmallShutterBlock.FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, final Mirror mirrorIn) {
		switch (mirrorIn) {
			default:
			case NONE:
				return state;
			case FRONT_BACK:
				state = this.rotate(state, Rotation.CLOCKWISE_180);
			case LEFT_RIGHT:
				return state.setValue(SmallShutterBlock.HINGE, state.getValue(SmallShutterBlock.HINGE) == DoorHingeSide.RIGHT ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT);
		}
	}

	/**
	 * Light corrections methods
	 */

	@Override
	public boolean propagatesSkylightDown(final BlockState p_200123_1_, final IBlockReader p_200123_2_, final BlockPos p_200123_3_) {
		return true;
	}
}