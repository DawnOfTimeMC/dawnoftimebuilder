package org.dawnoftimebuilder.block.templates;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import org.dawnoftimebuilder.block.general.WaterTrickleBlock;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.EmptyFluid;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.BucketItem;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.dawnoftimebuilder.util.DoTBUtils;

import javax.annotation.Nullable;

public abstract class BasePoolBlock extends BlockDoTB
{
	/**
	 * Method used to execute removeWaterAround(final Map<BlockPos, BlockState> testedPositionsIn, BlockState blockStateIn, final BlockPos blockPosIn, final World worldIn, final float prohibitedXIn, final float prohibitedZIn)
	 */
	public static boolean removeWaterAround(BlockState blockStateIn, final BlockPos blockPosIn, final World worldIn)
	{
		return removeWaterAround(new LinkedHashMap<BlockPos, BlockState>(), blockStateIn, blockPosIn, worldIn, 0.0f, 0.0f);
	}

	/**
	 * Seynax : binary method to remove water on all associated pool
	 */
	private static boolean removeWaterAround(final Map<BlockPos, BlockState> testedPositionsIn, BlockState blockStateIn, final BlockPos blockPosIn, final World worldIn, final float prohibitedXIn, final float prohibitedZIn) {
		boolean success = blockStateIn.getValue(DoTBBlockStateProperties.LEVEL) > 0;
		blockStateIn = blockStateIn.setValue(DoTBBlockStateProperties.LEVEL, 0);
		worldIn.setBlock(blockPosIn, blockStateIn, 10);
		if (prohibitedXIn != 1 && removeWaterAroundOffset(testedPositionsIn, blockPosIn, worldIn, 1, 0)) {
			success = true;
		}
		if (prohibitedXIn != -1 && removeWaterAroundOffset(testedPositionsIn, blockPosIn, worldIn, -1, 0)) {
			success = true;
		}
		if (prohibitedZIn != 1 && removeWaterAroundOffset(testedPositionsIn, blockPosIn, worldIn, 0, 1)) {
			success = true;
		}
		if (prohibitedZIn != -1 && removeWaterAroundOffset(testedPositionsIn, blockPosIn, worldIn, 0, -1)) {
			success = true;
		}
		return success;
	}

	/**
	 * Used by removeWaterAround(final Map<BlockPos, BlockState> testedPositionsIn, BlockState blockStateIn, final BlockPos blockPosIn, final World worldIn, final float prohibitedXIn, final float prohibitedZIn)
	 * @param testedPositionsIn
	 * @param blockPosIn
	 * @param worldIn
	 * @param x
	 * @param z
	 * @return
	 */
	private static boolean removeWaterAroundOffset(final Map<BlockPos, BlockState> testedPositionsIn, final BlockPos blockPosIn,
			final World worldIn, final int x, final int z) {
		final BlockPos pos = blockPosIn.offset(x, 0, z);
		if (!testedPositionsIn.containsKey(pos)) {
			BlockState state = worldIn.getBlockState(pos);
			if (state.getBlock() instanceof PoolBlock) {
				testedPositionsIn.put(pos, state);
				return removeWaterAround(testedPositionsIn, state, pos, worldIn, x, z);
			}
			if (state.getBlock() instanceof FaucetBlock) {
				state = state.setValue(DoTBBlockStateProperties.ACTIVATED, false);
				worldIn.setBlock(pos, state, 10);
			}
		}
		return false;
	}

	/**
	 * Method used to execute hasOneActivatedFaucetOrJet(final Map<BlockPos, BlockState> testedPositionsIn, final BlockPos blockPosIn, final IWorld worldIn, final float prohibitedXIn, final float prohibitedZIn)
	 * @param blockPosIn
	 * @param worldIn
	 * @return
	 */
	public final static EnumActivatorState hasOnePoolActivatorAround(final BlockPos blockPosIn, final IWorld worldIn)
	{
		return hasOnePoolActivatorAround(new LinkedHashMap<>(), blockPosIn, worldIn, 0.0f, 0.0f);
	}

	/**
	 * You can use hasOnePoolActivatorAround(final BlockPos blockPosIn, final IWorld worldIn)
	 * Seynax : binary method to remove water on all associated pool
	 */
	private static EnumActivatorState hasOnePoolActivatorAround(final Map<BlockPos, BlockState> testedPositionsIn, final BlockPos blockPosIn, final IWorld worldIn, final float prohibitedXIn, final float prohibitedZIn) {
		BlockState state = worldIn.getBlockState(blockPosIn.above());
		if (state.getBlock() instanceof FaucetBlock)
		{
			if(state.getValue(DoTBBlockStateProperties.ACTIVATED))
			{
				return EnumActivatorState.ENABLED;
			}

			return EnumActivatorState.DISABLED;
		}
		else if(state.getBlock() instanceof WaterTrickleBlock)
		{
			return EnumActivatorState.ENABLED;
		}

		if (prohibitedXIn != 1 && hasOnePoolActivatorAroundOffset(testedPositionsIn, blockPosIn, worldIn, 1, 0).equals(EnumActivatorState.ENABLED))
		{
			return EnumActivatorState.ENABLED;
		}

		if (prohibitedXIn != -1 && hasOnePoolActivatorAroundOffset(testedPositionsIn, blockPosIn, worldIn, -1, 0).equals(EnumActivatorState.ENABLED))
		{
			return EnumActivatorState.ENABLED;
		}

		if (prohibitedZIn != 1 && hasOnePoolActivatorAroundOffset(testedPositionsIn, blockPosIn, worldIn, 0, 1).equals(EnumActivatorState.ENABLED))
		{
			return EnumActivatorState.ENABLED;
		}

		if (prohibitedZIn != -1 && hasOnePoolActivatorAroundOffset(testedPositionsIn, blockPosIn, worldIn, 0, -1).equals(EnumActivatorState.ENABLED))
		{
			return EnumActivatorState.ENABLED;
		}

		return EnumActivatorState.NO;
	}

	/**
	 * Used by hasOnePoolActivatorAround(final BlockPos blockPosIn, final IWorld worldIn)
	 * @param testedPositionsIn
	 * @param blockPosIn
	 * @param worldIn
	 * @param xIn
	 * @param zIn
	 * @return
	 */
	private static EnumActivatorState hasOnePoolActivatorAroundOffset(final Map<BlockPos, BlockState> testedPositionsIn, final BlockPos blockPosIn,
			final IWorld worldIn, final int xIn, final int zIn) {
		final BlockPos pos = blockPosIn.offset(xIn, 0, zIn);
		if (testedPositionsIn.containsKey(pos))
		{
			return EnumActivatorState.NO;
		}

		BlockState state = worldIn.getBlockState(pos);

		if (state.getBlock() instanceof BasePoolBlock)
		{
			testedPositionsIn.put(pos, state);

			return hasOnePoolActivatorAround(testedPositionsIn, pos, worldIn, xIn, zIn);
		}

		return EnumActivatorState.NO;
	}

	public final int maxLevel;
	public final int faucetLevel;

	public BasePoolBlock(final Properties propertiesIn, final int maxLevelIn, final int faucetLevelIn)
	{
		super(propertiesIn);
		this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.NORTH, false).setValue(BlockStateProperties.EAST, false).setValue(BlockStateProperties.SOUTH, false).setValue(BlockStateProperties.WEST, false).setValue(DoTBBlockStateProperties.HAS_PILLAR, false).setValue(DoTBBlockStateProperties.LEVEL, 0));
		maxLevel = maxLevelIn;
		faucetLevel = faucetLevelIn;
	}

	@Override
	protected void createBlockStateDefinition(final StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(BlockStateProperties.NORTH).add(BlockStateProperties.EAST).add(BlockStateProperties.SOUTH).add(BlockStateProperties.WEST).add(DoTBBlockStateProperties.HAS_PILLAR).add(DoTBBlockStateProperties.LEVEL);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext contextIn) {

		BlockState state = super.getStateForPlacement(contextIn);

		if(hasOnePoolActivatorAround(contextIn.getClickedPos(), contextIn.getLevel()).equals(EnumActivatorState.ENABLED))
		{
			state = state.setValue(DoTBBlockStateProperties.LEVEL, 1);
			contextIn.getLevel().setBlock(contextIn.getClickedPos(), state, 10);

			if(!contextIn.getLevel().isClientSide())
			{
				((ServerWorld) contextIn.getLevel()).getBlockTicks().scheduleTick(contextIn.getClickedPos(), this, 5);
			}
		}

		return state;
	}

	@Override
	public ActionResultType use(BlockState blockStateIn, final World worldIn, final BlockPos blockPosIn, final PlayerEntity playerEntityIn, final Hand handIn, final BlockRayTraceResult blockRayTraceResultIn) {
		final ItemStack itemStack = playerEntityIn.getMainHandItem();
		if (!playerEntityIn.isCrouching())
		{
			int lastLevel = blockStateIn.getValue(DoTBBlockStateProperties.LEVEL);
			int nextLevel = lastLevel;
			boolean tryToChangeLevel = false;
			ItemStack newItemStack = null;

			if (itemStack.getItem() instanceof BucketItem)
			{
				if(((BucketItem) itemStack.getItem()).getFluid() instanceof WaterFluid)
				{
					nextLevel = maxLevel;

					if(!playerEntityIn.isCreative())
					{
						newItemStack = new ItemStack(Items.BUCKET);
					}
				}
				else if(((BucketItem) itemStack.getItem()).getFluid() instanceof EmptyFluid)
				{
					nextLevel = 0;

					if(!playerEntityIn.isCreative())
					{
						newItemStack = new ItemStack(Items.WATER_BUCKET);
					}
				}
				tryToChangeLevel = true;
			}
			else if (itemStack.getItem() instanceof PotionItem)
			{
				final Potion potion = PotionUtils.getPotion(itemStack);

				if (potion.getEffects() != null && potion.getEffects().size() <= 0 && nextLevel + 1 < maxLevel)
				{
					nextLevel ++;

					if(!playerEntityIn.isCreative())
					{
						newItemStack = new ItemStack(Items.GLASS_BOTTLE);
					}
				}
				tryToChangeLevel = true;
			}
			else if(itemStack.getItem() instanceof GlassBottleItem)
			{
				final Potion potion = PotionUtils.getPotion(itemStack);

				if (potion.getEffects() != null && potion.getEffects().size() <= 0 && nextLevel - 1 >= 0)
				{
					nextLevel --;

					if(!playerEntityIn.isCreative())
					{
						newItemStack = Items.POTION.getDefaultInstance();
					}
				}
				tryToChangeLevel = true;
			}

			if(tryToChangeLevel)
			{
				if(nextLevel != lastLevel)
				{
					if(newItemStack != null)
					{
						itemStack.shrink(1);
						playerEntityIn.inventory.add(newItemStack);
					}

					blockStateIn = blockStateIn.setValue(DoTBBlockStateProperties.LEVEL, nextLevel);
					worldIn.setBlock(blockPosIn, blockStateIn, 10);

					if(nextLevel == 0)
					{
						removeWaterAround(blockStateIn, blockPosIn, worldIn);
					}

					return ActionResultType.SUCCESS;
				}
				else
				{
					return ActionResultType.CONSUME;
				}
			}
			else if(itemStack.isEmpty())
			{
				blockStateIn = blockStateIn.setValue(DoTBBlockStateProperties.HAS_PILLAR, !blockStateIn.getValue(DoTBBlockStateProperties.HAS_PILLAR));
				worldIn.setBlock(blockPosIn, blockStateIn, 10);
				return ActionResultType.SUCCESS;
			}
		}
		return ActionResultType.PASS;
	}

	/*
	 * Seynax : allows the player to prevent the appearance of a pole in the basin below, under sneak conditions
	 */
	@Override
	public void setPlacedBy(final World worldIn, final BlockPos blockPosIn, final BlockState blockStateIn, final LivingEntity entityIn, final ItemStack itemStackIn) {
		if (entityIn instanceof PlayerEntity && !entityIn.isShiftKeyDown()) {
			super.setPlacedBy(worldIn, blockPosIn, blockStateIn, entityIn, itemStackIn);

			final BlockPos	blockPos	= blockPosIn.below();
			BlockState		blockState	= worldIn.getBlockState(blockPos);
			if (blockState.getBlock() == this) {
				blockState = blockState.setValue(DoTBBlockStateProperties.HAS_PILLAR, true);
				worldIn.setBlock(blockPos, blockState, 10);
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void tick(BlockState blockStateIn, ServerWorld serverWorldIn, BlockPos blockPosIn, Random randomIn)
	{
		super.tick(blockStateIn, serverWorldIn, blockPosIn, randomIn);

		boolean increase = hasOnePoolActivatorAround(blockPosIn, serverWorldIn).equals(EnumActivatorState.ENABLED);

		if(increase)
		{
			int level = blockStateIn.getValue(DoTBBlockStateProperties.LEVEL);

			if(level < maxLevel)
			{
				blockStateIn = blockStateIn.setValue(DoTBBlockStateProperties.LEVEL
					, level + 1);
				serverWorldIn.setBlock(blockPosIn, blockStateIn, 10);

				if(level+1 < maxLevel)
				{
					((ServerWorld) serverWorldIn).getBlockTicks().scheduleTick(blockPosIn, this, 5);
				}
			}
		}
		else
		{
			int level = blockStateIn.getValue(DoTBBlockStateProperties.LEVEL);

			if(level - 1 >= 0)
			{
				level --;
				blockStateIn = blockStateIn.setValue(DoTBBlockStateProperties.LEVEL, level);
				serverWorldIn.setBlock(blockPosIn, blockStateIn, 10);

				if(level-1 >= 0)
				{
					((ServerWorld) serverWorldIn).getBlockTicks().scheduleTick(blockPosIn, this, 5);
				}
			}
		}
	}


	@Override
	public BlockState updateShape(BlockState stateIn, final Direction directionIn, final BlockState facingStateIn, final IWorld worldIn, final BlockPos currentPosIn, final BlockPos facingPosIn) {
		if (directionIn.getAxis().isHorizontal()) {
			final boolean hasPoolInSide = facingStateIn.getBlock() == this;
			if (hasPoolInSide && facingStateIn.getValue(DoTBBlockStateProperties.LEVEL) >= 0) {
				stateIn = stateIn.setValue(DoTBBlockStateProperties.LEVEL, facingStateIn.getValue(DoTBBlockStateProperties.LEVEL));
			}
			switch (directionIn) {
				case NORTH:
					stateIn = stateIn.setValue(BlockStateProperties.NORTH, hasPoolInSide);
					break;
				case EAST:
					stateIn = stateIn.setValue(BlockStateProperties.EAST, hasPoolInSide);
					break;
				case SOUTH:
					stateIn = stateIn.setValue(BlockStateProperties.SOUTH, hasPoolInSide);
					break;
				case WEST:
					stateIn = stateIn.setValue(BlockStateProperties.WEST, hasPoolInSide);
					break;
				default:
					break;
			}
		}

		int level = stateIn.getValue(DoTBBlockStateProperties.LEVEL);

		if(facingPosIn.getY() == currentPosIn.getY() + 1)
		{
			int lastLevel = level;
			EnumActivatorState state = hasOnePoolActivatorAround(currentPosIn, worldIn);

			if(state.equals(EnumActivatorState.ENABLED))
			{
				if(level < maxLevel)
				{
					level ++;
				}
			}
			else if(state.equals(EnumActivatorState.DISABLED) && level - 1 >= 0)
			{
				level --;
			}

			stateIn = stateIn.setValue(DoTBBlockStateProperties.LEVEL, level);

			if(!worldIn.isClientSide() && lastLevel != level)
			{
				((ServerWorld) worldIn).getBlockTicks().scheduleTick(currentPosIn, this, 5);
			}
		}

		return stateIn;
	}

	public static enum EnumActivatorState {
		NO, DISABLED, ENABLED,
	}

	@Override
	public void appendHoverText(final ItemStack stack, @Nullable final IBlockReader worldIn, final List<ITextComponent> tooltip, final ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		DoTBUtils.addTooltip(tooltip, DoTBUtils.TOOLTIP_ADD_COLUMN);
	}
}