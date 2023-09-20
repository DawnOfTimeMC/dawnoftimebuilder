package org.dawnoftimebuilder.block.roman;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import org.dawnoftimebuilder.DawnOfTimeBuilder;
import org.dawnoftimebuilder.block.IBlockGeneration;
import org.dawnoftimebuilder.block.ICustomBlockItem;
import org.dawnoftimebuilder.block.templates.BlockDoTB;
import org.dawnoftimebuilder.item.templates.PotAndBlockItem;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;

public class CypressBlock extends BlockDoTB implements IBlockGeneration, ICustomBlockItem
{

	private static final IntegerProperty	SIZE	= DoTBBlockStateProperties.SIZE_0_5;
	private static final VoxelShape			VS_0	= Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);
	private static final VoxelShape			VS_1	= Block.box(6.0D, 0.0D, 6.0D, 10.0D, 8.0D, 10.0D);
	private static final VoxelShape			VS_2	= Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
	private static final VoxelShape			VS_3_4	= Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

	public CypressBlock(final Properties properties)
	{
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(CypressBlock.SIZE, 1));
	}

	@Override
	public boolean canSurvive(final BlockState state, final IWorldReader worldIn, final BlockPos pos)
	{
		return Block.canSupportCenter(worldIn, pos.below(), Direction.UP)
				|| worldIn.getBlockState(pos.below()).getBlock() == this;
	}

	@Override
	public ActionResultType use(final BlockState state, final World worldIn, final BlockPos pos,
			final PlayerEntity player, final Hand handIn, final BlockRayTraceResult hit)
	{
		final ItemStack heldItemStack = player.getItemInHand(handIn);
		if (player.isCrouching())
		{
			//We remove the highest CypressBlock
			final BlockPos topPos = this.getHighestCypressPos(worldIn, pos);
			if (topPos != pos)
			{
				if (!worldIn.isClientSide())
				{
					worldIn.setBlock(topPos, Blocks.AIR.defaultBlockState(), 35);
					if (!player.isCreative())
					{
						Block.dropResources(state, worldIn, pos, null, player, heldItemStack);
					}
				}
				return ActionResultType.SUCCESS;
			}
		}
		else if (!heldItemStack.isEmpty() && heldItemStack.getItem() == this.asItem())
		{
			//We put a CypressBlock on top of the cypress
			final BlockPos topPos = this.getHighestCypressPos(worldIn, pos).above();
			if (topPos.getY() <= DoTBUtils.HIGHEST_Y)
			{
				if (!worldIn.isClientSide() && worldIn.getBlockState(topPos).isAir(worldIn, topPos))
				{
					worldIn.setBlock(topPos, this.defaultBlockState(), 11);
					if (!player.isCreative())
					{
						heldItemStack.shrink(1);
					}
				}
				return ActionResultType.SUCCESS;
			}
		}
		return super.use(state, worldIn, pos, player, handIn, hit);
	}

	private BlockPos getHighestCypressPos(final World worldIn, final BlockPos pos)
	{
		int yOffset;
		for (yOffset = 0; yOffset + pos.getY() <= DoTBUtils.HIGHEST_Y; yOffset++)
		{
			if (worldIn.getBlockState(pos.above(yOffset)).getBlock() != this)
			{
				break;
			}
		}
		return pos.above(yOffset - 1);
	}

	@Override
	protected void createBlockStateDefinition(final StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(CypressBlock.SIZE);
	}

	@Override
	public VoxelShape getShape(final BlockState state, final IBlockReader worldIn, final BlockPos pos,
			final ISelectionContext context)
	{
		switch (state.getValue(CypressBlock.SIZE))
		{
			case 0:
				return CypressBlock.VS_0;
			default:
			case 1:
				return CypressBlock.VS_1;
			case 2:
				return CypressBlock.VS_2;
			case 3:
			case 4:
				return CypressBlock.VS_3_4;
			case 5:
				return VoxelShapes.block();
		}
	}

	@Override
	public VoxelShape getBlockSupportShape(final BlockState p_230335_1_, final IBlockReader p_230335_2_,
			final BlockPos p_230335_3_)
	{
		return VoxelShapes.empty();
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(final BlockItemUseContext context)
	{
		BlockState	adjacentState	= context.getLevel().getBlockState(context.getClickedPos().above());
		final int	size			= adjacentState.getBlock() == this
				? Math.min(adjacentState.getValue(CypressBlock.SIZE) + 1, 5)
				: 1;
		if (size < 3)
		{
			return this.defaultBlockState().setValue(CypressBlock.SIZE, size);
		}
		adjacentState = context.getLevel().getBlockState(context.getClickedPos().below());
		return this.defaultBlockState().setValue(CypressBlock.SIZE, adjacentState.getBlock() == this ? size : 0);
	}

	@Override
	public BlockState updateShape(final BlockState stateIn, final Direction facing, final BlockState facingState,
			final IWorld worldIn, final BlockPos currentPos, final BlockPos facingPos)
	{
		if (!facing.getAxis().isVertical())
		{
			return stateIn;
		}
		if (!this.canSurvive(stateIn, worldIn, currentPos))
		{
			return Blocks.AIR.defaultBlockState();
		}
		BlockState	adjacentState	= worldIn.getBlockState(currentPos.above());
		final int	size			= adjacentState.getBlock() == this
				? Math.min(adjacentState.getValue(CypressBlock.SIZE) + 1, 5)
				: 1;
		if (size < 3)
		{
			return this.defaultBlockState().setValue(CypressBlock.SIZE, size);
		}
		adjacentState = worldIn.getBlockState(currentPos.below());
		return this.defaultBlockState().setValue(CypressBlock.SIZE, adjacentState.getBlock() == this ? size : 0);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(final BlockState stateIn, final World worldIn, final BlockPos pos, final Random rand)
	{
		if (worldIn.isRainingAt(pos.above()) && rand.nextInt(15) == 1)
		{
			final BlockPos		posDown		= pos.below();
			final BlockState	stateDown	= worldIn.getBlockState(posDown);
			if (!stateDown.canOcclude() || !stateDown.isFaceSturdy(worldIn, posDown, Direction.UP))
			{
				final double	x	= pos.getX() + rand.nextFloat();
				final double	y	= pos.getY() - 0.05D;
				final double	z	= pos.getZ() + rand.nextFloat();
				worldIn.addParticle(ParticleTypes.DRIPPING_WATER, x, y, z, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	public void appendHoverText(final ItemStack stack, @Nullable final IBlockReader worldIn,
			final List<ITextComponent> tooltip, final ITooltipFlag flagIn)
	{
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		DoTBUtils.addTooltip(tooltip, DoTBUtils.TOOLTIP_COLUMN);
	}

	@Override
	public void generateOnPos(final IWorld world, final BlockPos pos, final BlockState state, final Random random)
	{
		final BlockState groundState = world.getBlockState(pos.below());

		if (!Tags.Blocks.DIRT.contains(groundState.getBlock()))
		{
			return;
		}

		final int maxSize = 2 + random.nextInt(5);
		for (int i = 0; i < maxSize; i++)
		{
			if (world.getBlockState(pos.above(i)).getMaterial() != Material.AIR)
			{
				return;
			}
		}
		world.setBlock(pos, state.setValue(CypressBlock.SIZE, 0), 2);
		int size = 1;
		for (int i = maxSize; i > 0; i--)
		{
			world.setBlock(pos.above(i), state.setValue(CypressBlock.SIZE, size), 2);
			if (size < 5)
			{
				size++;
			}
		}
	}

	@Nullable
	@Override
	public Item getCustomBlockItem()
	{
		return new PotAndBlockItem(this, new Item.Properties().tab(DawnOfTimeBuilder.DOTB_TAB));
	}
}