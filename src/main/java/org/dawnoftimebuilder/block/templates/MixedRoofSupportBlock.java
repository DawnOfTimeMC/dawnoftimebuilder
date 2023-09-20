package org.dawnoftimebuilder.block.templates;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.dawnoftimebuilder.DawnOfTimeBuilder;
import org.dawnoftimebuilder.block.ICustomBlockItem;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.SlabType;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

public class MixedRoofSupportBlock extends SlabBlockDoTB implements ICustomBlockItem
{

	public static final DirectionProperty			FACING	= BlockStateProperties.HORIZONTAL_FACING;
	public static final EnumProperty<StairsShape>	SHAPE	= BlockStateProperties.STAIRS_SHAPE;
	private final Supplier<Block>					roofSlabBlockSupplier;

	public MixedRoofSupportBlock(final RegistryObject<Block> roofSlabBlockSupplier, final Properties properties)
	{
		super(properties);
		this.roofSlabBlockSupplier = roofSlabBlockSupplier;
		this.registerDefaultState(this.defaultBlockState().setValue(MixedRoofSupportBlock.FACING, Direction.NORTH)
				.setValue(SlabBlock.TYPE, SlabType.BOTTOM).setValue(MixedRoofSupportBlock.SHAPE, StairsShape.STRAIGHT)
				.setValue(SlabBlock.WATERLOGGED, false));
	}

	@Override
	protected void createBlockStateDefinition(final StateContainer.Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder);
		builder.add(MixedRoofSupportBlock.FACING, MixedRoofSupportBlock.SHAPE);
	}

	@Override
	public boolean canBeReplaced(final BlockState state, final BlockItemUseContext context)
	{
		return false;
	}

	@Override
	public BlockState getStateForPlacement(final BlockItemUseContext context)
	{
		final BlockPos		blockpos	= context.getClickedPos();
		BlockState			newState	= this.defaultBlockState().setValue(MixedRoofSupportBlock.FACING,
				context.getHorizontalDirection());
		final BlockPos		pos			= context.getClickedPos();
		final FluidState	fluidState	= context.getLevel().getFluidState(blockpos);
		final Direction		direction	= context.getClickedFace();
		newState = newState.setValue(SlabBlock.TYPE,
				direction != Direction.DOWN
						&& (direction == Direction.UP || (context.getClickLocation().y - blockpos.getY() <= 0.5D))
								? SlabType.BOTTOM
								: SlabType.TOP);
		return newState.setValue(SlabBlock.WATERLOGGED, fluidState.getType() == Fluids.WATER)
				.setValue(MixedRoofSupportBlock.SHAPE, this.getShapeProperty(newState, context.getLevel(), pos));
	}

	@Override
	public ActionResultType use(final BlockState state, final World worldIn, final BlockPos pos,
			final PlayerEntity player, final Hand handIn, final BlockRayTraceResult hit)
	{
		final Direction	facing		= hit.getDirection();
		final ItemStack	itemStack	= player.getItemInHand(handIn);
		if (!player.isCrouching() && player.mayUseItemAt(pos, facing, itemStack) && facing.getAxis().isVertical()
				&& !itemStack.isEmpty())
		{
			if ((facing == Direction.UP) && (state.getValue(SlabBlock.TYPE) == SlabType.BOTTOM
					&& itemStack.getItem() == this.roofSlabBlockSupplier.get().asItem()))
			{
				if (worldIn.setBlock(pos, state.setValue(SlabBlock.TYPE, SlabType.DOUBLE), 11))
				{
					this.setPlacedBy(worldIn, pos, state, player, itemStack);
					if (player instanceof ServerPlayerEntity)
					{
						CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) player, pos, itemStack);
					}
					final SoundType soundtype = this.getSoundType(state, worldIn, pos, player);
					worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS,
							(soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
					if (!player.isCreative())
					{
						itemStack.shrink(1);
					}
					return ActionResultType.SUCCESS;
				}
			}
		}
		return super.use(state, worldIn, pos, player, handIn, hit);
	}

	@Nullable
	@Override
	public Item getCustomBlockItem()
	{
		return new BlockItem(this, new Item.Properties().tab(DawnOfTimeBuilder.DOTB_TAB))
		{
			@Override
			public ActionResultType place(final BlockItemUseContext context)
			{
				final Direction facing = context.getClickedFace();
				if (context.getPlayer() != null && context.getPlayer().isCrouching() || !facing.getAxis().isVertical())
				{
					return super.place(context);
				}

				final PlayerEntity	player		= context.getPlayer();
				final ItemStack		itemStack	= context.getItemInHand();
				final World			worldIn		= context.getLevel();
				BlockPos			pos			= context.getClickedPos();
				if (!context.replacingClickedOnBlock())
				{
					pos = pos.relative(facing.getOpposite());
				}
				if ((player != null) && !player.mayUseItemAt(pos, facing, itemStack))
				{
					return super.place(context);
				}

				if (!itemStack.isEmpty())
				{
					final BlockState			state	= worldIn.getBlockState(pos);
					final MixedRoofSupportBlock	block	= (MixedRoofSupportBlock) this.getBlock();
					if ((state.getBlock() == block.roofSlabBlockSupplier.get())
							&& (state.getValue(SlabBlock.TYPE) == SlabType.TOP))
					{
						BlockState madeState = block.getStateForPlacement(context);
						if (madeState == null)
						{
							return super.place(context);
						}
						madeState = madeState.setValue(SlabBlock.TYPE, SlabType.DOUBLE);
						if (worldIn.setBlock(pos, madeState.setValue(SlabBlock.TYPE, SlabType.DOUBLE), 11))
						{
							this.getBlock().setPlacedBy(worldIn, pos, state, player, itemStack);
							if (player instanceof ServerPlayerEntity)
							{
								CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) player, pos, itemStack);
							}
							final SoundType soundtype = block.getSoundType(state, worldIn, pos, player);
							worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS,
									(soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
							itemStack.shrink(1);
							return ActionResultType.SUCCESS;
						}
					}
				}
				return super.place(context);
			}
		};
	}

	@Override
	public BlockState updateShape(BlockState stateIn, final Direction facing, final BlockState facingState,
			final IWorld worldIn, final BlockPos currentPos, final BlockPos facingPos)
	{
		stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		return facing.getAxis().isHorizontal()
				? stateIn.setValue(MixedRoofSupportBlock.SHAPE, this.getShapeProperty(stateIn, worldIn, currentPos))
				: stateIn;
	}

	/**
	 * Returns a shape property based on the surrounding stairs from the given blockstate and position
	 */
	private StairsShape getShapeProperty(final BlockState state, final IBlockReader worldIn, final BlockPos pos)
	{
		final Direction	direction		= state.getValue(MixedRoofSupportBlock.FACING);
		BlockState		adjacentState	= worldIn.getBlockState(pos.relative(direction));
		if (this.isSameBlock(adjacentState) && (state
				.getValue(SlabBlock.TYPE) == SlabType.TOP == (adjacentState.getValue(SlabBlock.TYPE) == SlabType.TOP)))
		{
			final Direction adjacentDirection = adjacentState.getValue(MixedRoofSupportBlock.FACING);
			if (adjacentDirection.getAxis() != state.getValue(MixedRoofSupportBlock.FACING).getAxis()
					&& this.isConnectableRoofSupport(state, worldIn, pos, adjacentDirection.getOpposite()))
			{
				return adjacentDirection == direction.getCounterClockWise() ? StairsShape.OUTER_LEFT
						: StairsShape.OUTER_RIGHT;
			}
		}
		adjacentState = worldIn.getBlockState(pos.relative(direction.getOpposite()));
		if (this.isSameBlock(adjacentState) && (state
				.getValue(SlabBlock.TYPE) == SlabType.TOP == (adjacentState.getValue(SlabBlock.TYPE) == SlabType.TOP)))
		{
			final Direction adjacentDirection = adjacentState.getValue(MixedRoofSupportBlock.FACING);
			if (adjacentDirection.getAxis() != state.getValue(MixedRoofSupportBlock.FACING).getAxis()
					&& this.isConnectableRoofSupport(state, worldIn, pos, adjacentDirection))
			{
				return adjacentDirection == direction.getCounterClockWise() ? StairsShape.INNER_LEFT
						: StairsShape.INNER_RIGHT;
			}
		}
		return StairsShape.STRAIGHT;
	}

	private boolean isConnectableRoofSupport(final BlockState state, final IBlockReader worldIn, final BlockPos pos,
			final Direction face)
	{
		final BlockState adjacentState = worldIn.getBlockState(pos.relative(face));
		return !this.isSameBlock(adjacentState)
				|| adjacentState.getValue(MixedRoofSupportBlock.FACING) != state.getValue(MixedRoofSupportBlock.FACING)
				|| adjacentState
						.getValue(SlabBlock.TYPE) == SlabType.TOP != (state.getValue(SlabBlock.TYPE) == SlabType.TOP);
	}

	public boolean isSameBlock(final BlockState state)
	{
		return state.getBlock() == this;
	}

	@Override
	public boolean placeLiquid(final IWorld world, final BlockPos pos, final BlockState state, final FluidState fluid)
	{
		if (state.getValue(BlockStateProperties.WATERLOGGED) || fluid.getType() != Fluids.WATER)
		{
			return false;
		}
		if (!world.isClientSide())
		{
			world.setBlock(pos, state.setValue(BlockStateProperties.WATERLOGGED, true), 3);
			world.getLiquidTicks().scheduleTick(pos, fluid.getType(), fluid.getType().getTickDelay(world));
		}
		return true;
	}

	@Override
	public boolean canPlaceLiquid(final IBlockReader world, final BlockPos pos, final BlockState state,
			final Fluid fluid)
	{
		return !state.getValue(BlockStateProperties.WATERLOGGED) && fluid == Fluids.WATER;
	}

	@Override
	public BlockState rotate(final BlockState state, final Rotation rot)
	{
		return state.setValue(MixedRoofSupportBlock.FACING, rot.rotate(state.getValue(MixedRoofSupportBlock.FACING)));
	}

	@Override
	public BlockState mirror(final BlockState state, final Mirror mirrorIn)
	{
		final Direction		direction	= state.getValue(MixedRoofSupportBlock.FACING);
		final StairsShape	stairsshape	= state.getValue(MixedRoofSupportBlock.SHAPE);
		switch (mirrorIn)
		{
			case LEFT_RIGHT:
				if (direction.getAxis() == Direction.Axis.Z)
				{
					switch (stairsshape)
					{
						case INNER_LEFT:
							return state.rotate(Rotation.CLOCKWISE_180).setValue(MixedRoofSupportBlock.SHAPE,
									StairsShape.INNER_RIGHT);
						case INNER_RIGHT:
							return state.rotate(Rotation.CLOCKWISE_180).setValue(MixedRoofSupportBlock.SHAPE,
									StairsShape.INNER_LEFT);
						case OUTER_LEFT:
							return state.rotate(Rotation.CLOCKWISE_180).setValue(MixedRoofSupportBlock.SHAPE,
									StairsShape.OUTER_RIGHT);
						case OUTER_RIGHT:
							return state.rotate(Rotation.CLOCKWISE_180).setValue(MixedRoofSupportBlock.SHAPE,
									StairsShape.OUTER_LEFT);
						default:
							return state.rotate(Rotation.CLOCKWISE_180);
					}
				}
				break;
			case FRONT_BACK:
				if (direction.getAxis() == Direction.Axis.X)
				{
					switch (stairsshape)
					{
						case INNER_LEFT:
							return state.rotate(Rotation.CLOCKWISE_180).setValue(MixedRoofSupportBlock.SHAPE,
									StairsShape.INNER_LEFT);
						case INNER_RIGHT:
							return state.rotate(Rotation.CLOCKWISE_180).setValue(MixedRoofSupportBlock.SHAPE,
									StairsShape.INNER_RIGHT);
						case OUTER_LEFT:
							return state.rotate(Rotation.CLOCKWISE_180).setValue(MixedRoofSupportBlock.SHAPE,
									StairsShape.OUTER_RIGHT);
						case OUTER_RIGHT:
							return state.rotate(Rotation.CLOCKWISE_180).setValue(MixedRoofSupportBlock.SHAPE,
									StairsShape.OUTER_LEFT);
						case STRAIGHT:
							return state.rotate(Rotation.CLOCKWISE_180);
					}
				}
			default:
				break;
		}

		return super.mirror(state, mirrorIn);
	}
}
