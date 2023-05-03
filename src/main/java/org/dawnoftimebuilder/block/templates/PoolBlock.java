package org.dawnoftimebuilder.block.templates;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.EmptyFluid;
import net.minecraft.fluid.WaterFluid;
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
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

/**
 * @author seyro
 */
public class PoolBlock extends BlockDoTB {
	private static final VoxelShape[] SHAPES = PoolBlock.makeShapes();

	public PoolBlock(final Properties propertiesIn) {
		super(propertiesIn);
		this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.NORTH, false).setValue(BlockStateProperties.EAST, false).setValue(BlockStateProperties.SOUTH, false).setValue(BlockStateProperties.WEST, false).setValue(DoTBBlockStateProperties.HAS_PILLAR, false).setValue(DoTBBlockStateProperties.LEVEL, 0));
	}

	@Override
	public VoxelShape getShape(final BlockState state, final IBlockReader worldIn, final BlockPos pos, final ISelectionContext context) {
		int index = 0;
		if (state.getValue(BlockStateProperties.NORTH)) {
			index += 1;
		}
		if (state.getValue(BlockStateProperties.EAST)) {
			index += 2;
		}
		if (state.getValue(BlockStateProperties.SOUTH)) {
			index += 4;
		}
		if (state.getValue(BlockStateProperties.WEST)) {
			index += 8;
		}
		if (state.getValue(DoTBBlockStateProperties.HAS_PILLAR)) {
			index += 16;
		}
		return PoolBlock.SHAPES[index];
	}

	private static VoxelShape[] makeShapes() {
		final VoxelShape	vs_floor	= Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
		final VoxelShape	vs_north	= Block.box(0.0D, 2.0D, 0.0D, 16.0D, 16.0D, 2.0D);
		final VoxelShape	vs_east		= Block.box(14.0D, 2.0D, 0.0D, 16.0D, 16.0D, 16.0D);
		final VoxelShape	vs_south	= Block.box(0.0D, 2.0D, 14.0D, 16.0D, 16.0D, 16.0D);
		final VoxelShape	vs_west		= Block.box(0.0D, 2.0D, 0.0D, 2.0D, 16.0D, 16.0D);
		final VoxelShape	vs_pillar	= Block.box(4.0D, 2.0D, 4.0D, 12.0D, 16.0D, 12.0D);
		final VoxelShape[]	shapes		= new VoxelShape[32];
		for (int i = 0; i < 32; i++) {
			VoxelShape temp = vs_floor;
			if ((i & 1) == 0) { // Check first bit : 0 -> North true
				temp = VoxelShapes.or(temp, vs_north);
			}
			if ((i >> 1 & 1) == 0) { // Check second bit : 0 -> East true
				temp = VoxelShapes.or(temp, vs_east);
			}
			if ((i >> 2 & 1) == 0) { // Check third bit : 0 -> South true
				temp = VoxelShapes.or(temp, vs_south);
			}
			if ((i >> 3 & 1) == 0) { // Check fourth bit : 0 -> West true
				temp = VoxelShapes.or(temp, vs_west);
			}
			if ((i >> 4 & 1) == 1) { // Check fifth bit : 1 -> Pillar true
				temp = VoxelShapes.or(temp, vs_pillar);
			}
			shapes[i] = temp;
		}
		return shapes;
	}

	@Override
	protected void createBlockStateDefinition(final StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(BlockStateProperties.NORTH).add(BlockStateProperties.EAST).add(BlockStateProperties.SOUTH).add(BlockStateProperties.WEST).add(DoTBBlockStateProperties.HAS_PILLAR).add(DoTBBlockStateProperties.LEVEL);
	}

	protected final static Map<BlockPos, BlockState> REMOVE_WATER_MAP = new HashMap<>();

	/**
	 * Seynax : binary method to remove water on all associated pool
	 */
	public static boolean removeWater(final Map<BlockPos, BlockState> testedPositionsIn, BlockState blockStateIn, final BlockPos blockPosIn, final World worldIn, final float prohibitedXIn, final float prohibitedZIn) {
		boolean success = blockStateIn.getValue(DoTBBlockStateProperties.LEVEL) > 0;
		blockStateIn = blockStateIn.setValue(DoTBBlockStateProperties.LEVEL, 0);
		worldIn.setBlock(blockPosIn, blockStateIn, 10);
		if (prohibitedXIn != 1 && PoolBlock.removeWaterOffset(testedPositionsIn, blockPosIn, worldIn, 1, 0)) {
			success = true;
		}
		if (prohibitedXIn != -1 && PoolBlock.removeWaterOffset(testedPositionsIn, blockPosIn, worldIn, -1, 0)) {
			success = true;
		}
		if (prohibitedZIn != 1 && PoolBlock.removeWaterOffset(testedPositionsIn, blockPosIn, worldIn, 0, 1)) {
			success = true;
		}
		if (prohibitedZIn != -1 && PoolBlock.removeWaterOffset(testedPositionsIn, blockPosIn, worldIn, 0, -1)) {
			success = true;
		}
		return success;
	}

	private static boolean removeWaterOffset(final Map<BlockPos, BlockState> testedPositionsIn, final BlockPos blockPosIn,
			final World worldIn, final int x, final int z) {
		final BlockPos pos = blockPosIn.offset(x, 0, z);
		if (!testedPositionsIn.containsKey(pos)) {
			BlockState state = worldIn.getBlockState(pos);
			if (state.getBlock() instanceof PoolBlock) {
				testedPositionsIn.put(pos, state);
				return PoolBlock.removeWater(testedPositionsIn, state, pos, worldIn, x, z);
			}
			if (state.getBlock() instanceof FaucetBlock) {
				state = state.setValue(DoTBBlockStateProperties.ACTIVATED, false);
				worldIn.setBlock(pos, state, 10);
			}
		}
		return false;
	}

	/**
	 * Seynax : binary method to remove water on all associated pool
	 */
	public static boolean hasOneActivatedFaucetOrJet(final Map<BlockPos, BlockState> testedPositionsIn, final BlockPos blockPosIn, final IWorld worldIn, final float prohibitedXIn, final float prohibitedZIn) {

		BlockState state = worldIn.getBlockState(blockPosIn.above());
		if (state.getBlock() instanceof FaucetBlock) {
			if(state.getValue(DoTBBlockStateProperties.ACTIVATED))
			{
				return true;
			}
		}
		if (prohibitedXIn != 1 && PoolBlock.hasOneActivatedFaucetOrJetOffset(testedPositionsIn, blockPosIn, worldIn, 1, 0)) {
			return true;
		}
		if (prohibitedXIn != -1 && PoolBlock.hasOneActivatedFaucetOrJetOffset(testedPositionsIn, blockPosIn, worldIn, -1, 0)) {
			return true;
		}
		if (prohibitedZIn != 1 && PoolBlock.hasOneActivatedFaucetOrJetOffset(testedPositionsIn, blockPosIn, worldIn, 0, 1)) {
			return true;
		}
		if (prohibitedZIn != -1 && PoolBlock.hasOneActivatedFaucetOrJetOffset(testedPositionsIn, blockPosIn, worldIn, 0, -1)) {
			return true;
		}
		return false;
	}

	private static boolean hasOneActivatedFaucetOrJetOffset(final Map<BlockPos, BlockState> testedPositionsIn, final BlockPos blockPosIn,
			final IWorld worldIn, final int x, final int z) {
		final BlockPos pos = blockPosIn.offset(x, 0, z);
		if (!testedPositionsIn.containsKey(pos)) {
			BlockState state = worldIn.getBlockState(pos.above());
			if (state.getBlock() instanceof FaucetBlock) {
				if(state.getValue(DoTBBlockStateProperties.ACTIVATED))
				{
					return true;
				}
			}
			state = worldIn.getBlockState(pos);
			if (state.getBlock() instanceof PoolBlock) {
				testedPositionsIn.put(pos, state);
				return PoolBlock.hasOneActivatedFaucetOrJet(testedPositionsIn, pos, worldIn, x, z);
			}
		}
		return false;
	}

	@Override
	public ActionResultType use(BlockState blockStateIn, final World worldIn, final BlockPos blockPosIn, final PlayerEntity playerEntityIn, final Hand handIn, final BlockRayTraceResult blockRayTraceResultIn) {

		final ItemStack itemStack = playerEntityIn.getMainHandItem();
		if (!playerEntityIn.isCrouching()) {
			/*
			 * Seynax : allows the player to remove the contents of all stuck basins, with a single click with an empty bucket while sneaking
			 */
			if (itemStack.getItem() instanceof BucketItem && ((BucketItem) itemStack.getItem()).getFluid() instanceof WaterFluid) {
				blockStateIn = blockStateIn.setValue(DoTBBlockStateProperties.LEVEL, 16);
				worldIn.setBlock(blockPosIn, blockStateIn, 10);

				return ActionResultType.SUCCESS;
			}
			if (itemStack.getItem() instanceof BucketItem && ((BucketItem) itemStack.getItem()).getFluid() instanceof EmptyFluid) {
				PoolBlock.REMOVE_WATER_MAP.clear();
				if (PoolBlock.removeWater(PoolBlock.REMOVE_WATER_MAP, blockStateIn, blockPosIn, worldIn, 0, 0)) {
					return ActionResultType.SUCCESS;
				}
			}
			else if (itemStack.getItem() instanceof PotionItem) {
				final Potion potion = PotionUtils.getPotion(itemStack);

				if (potion.getEffects().size() <= 0) {
					final int newLevel = blockStateIn.getValue(DoTBBlockStateProperties.LEVEL) + 1;
					blockStateIn = blockStateIn.setValue(DoTBBlockStateProperties.LEVEL, newLevel < 17 ? newLevel : 16);

					worldIn.setBlock(blockPosIn, blockStateIn, 10);

					return ActionResultType.SUCCESS;
				}
			}
			else if (itemStack.getItem() instanceof GlassBottleItem) {
				final Potion potion = PotionUtils.getPotion(itemStack);

				if (potion.getEffects().size() <= 0) {
					final int newLevel = blockStateIn.getValue(DoTBBlockStateProperties.LEVEL) - 1;
					blockStateIn = blockStateIn.setValue(DoTBBlockStateProperties.LEVEL, Math.max(newLevel, 0));

					worldIn.setBlock(blockPosIn, blockStateIn, 10);

					if (!playerEntityIn.isCreative()) {
						playerEntityIn.getMainHandItem().shrink(1);
						playerEntityIn.inventory.add(new ItemStack(Items.GLASS_BOTTLE));
					}
					return ActionResultType.SUCCESS;
				}
			}
			else {
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
	public void tick(BlockState p_225534_1_, ServerWorld p_225534_2_, BlockPos p_225534_3_, Random p_225534_4_)
	{
		super.tick(p_225534_1_, p_225534_2_, p_225534_3_, p_225534_4_);

		REMOVE_WATER_MAP.clear();
		boolean increase = hasOneActivatedFaucetOrJet(REMOVE_WATER_MAP, p_225534_3_, p_225534_2_, 0, 0);

		int maxLevel = p_225534_1_.getBlock() instanceof SmallPoolBlock ? 6 : 16;
		System.out.println("Max : " + maxLevel);

		if(increase)
		{
			int level = p_225534_1_.getValue(DoTBBlockStateProperties.LEVEL);

			if(level < maxLevel)
			{
				p_225534_1_ = p_225534_1_.setValue(DoTBBlockStateProperties.LEVEL
					, level + 1);
				p_225534_2_.setBlock(p_225534_3_, p_225534_1_, 10);

				if(level+1 < maxLevel)
				{
					((ServerWorld) p_225534_2_).getBlockTicks().scheduleTick(p_225534_3_, this, 5);
				}
			}
		}
		else
		{
			int level = p_225534_1_.getValue(DoTBBlockStateProperties.LEVEL);

			if(level > 0)
			{
				p_225534_1_ = p_225534_1_.setValue(DoTBBlockStateProperties.LEVEL
					, level - 1);
				p_225534_2_.setBlock(p_225534_3_, p_225534_1_, 10);

				if(level-1 > 0)
				{
					((ServerWorld) p_225534_2_).getBlockTicks().scheduleTick(p_225534_3_, this, 5);
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
		if(facingStateIn.getBlock() instanceof FaucetBlock)
		{
			if(facingPosIn.getY() == currentPosIn.getY() + 1)
			{
				int lastLevel = level;
				PoolBlock.REMOVE_WATER_MAP.clear();
				if(hasOneActivatedFaucetOrJet(REMOVE_WATER_MAP, currentPosIn, worldIn, 0, 0))
				{
					if(stateIn.getBlock() instanceof SmallPoolBlock)
					{
						if(level < 6)
						{
							level ++;
						}
					}
					else
					{
						if(level < 16)
						{
							level ++;
						}
					}
				}
				else
				{
						if(level > 0)
						{
							level --;
						}
				}

				System.out.println("level " + lastLevel + " / " + level);

				stateIn = stateIn.setValue(DoTBBlockStateProperties.LEVEL, level);

				if(!worldIn.isClientSide() && lastLevel != level)
				{
					((ServerWorld) worldIn).getBlockTicks().scheduleTick(currentPosIn, this, 5);
				}
			}
		}

		return stateIn;
	}
}