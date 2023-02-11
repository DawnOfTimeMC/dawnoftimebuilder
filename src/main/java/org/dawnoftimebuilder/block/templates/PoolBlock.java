package org.dawnoftimebuilder.block.templates;

import java.util.HashMap;
import java.util.Map;

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

/**
 * @author seyro
 */
public class PoolBlock extends BlockDoTB {
	private static final VoxelShape[] SHAPES = PoolBlock.makeShapes();

	public PoolBlock(final Properties propertiesIn) {
		super(propertiesIn);
		this.registerDefaultState(this.defaultBlockState()
				.setValue(BlockStateProperties.NORTH, false)
				.setValue(BlockStateProperties.EAST, false)
				.setValue(BlockStateProperties.SOUTH, false)
				.setValue(BlockStateProperties.WEST, false)
				.setValue(DoTBBlockStateProperties.HAS_PILLAR, false)
				.setValue(BlockStateProperties.LEVEL, 0));
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
		builder.add(BlockStateProperties.NORTH).add(BlockStateProperties.EAST).add(BlockStateProperties.SOUTH).add(BlockStateProperties.WEST).add(DoTBBlockStateProperties.HAS_PILLAR).add(BlockStateProperties.LEVEL);
	}

	private final static Map<BlockPos, BlockState> REMOVE_WATER_MAP = new HashMap<>();

	/**
	 * Seynax : binary method to remove water on all associated pool
	 */
	public static boolean removeWater(final Map<BlockPos, BlockState> testedPositionsIn, BlockState blockStateIn, final BlockPos blockPosIn, final World worldIn, final float prohibitedXIn, final float prohibitedZIn) {
		boolean success = blockStateIn.getValue(BlockStateProperties.LEVEL) > 0;
		blockStateIn = blockStateIn.setValue(BlockStateProperties.LEVEL, 0);
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

	private static boolean removeWaterOffset(final Map<BlockPos, BlockState> testedPositionsIn, final BlockPos blockPosIn, final World worldIn, final int x, final int z) {
		final BlockPos pos = blockPosIn.offset(x, 0, z);
		if (!testedPositionsIn.containsKey(pos)) {
			final BlockState state = worldIn.getBlockState(pos);
			if (state.getBlock() instanceof PoolBlock) {
				testedPositionsIn.put(pos, state);
				return PoolBlock.removeWater(testedPositionsIn, state, pos, worldIn, x, z);
			}
		}
		return false;
	}

	@Override
	public ActionResultType use(BlockState blockStateIn, final World worldIn, final BlockPos blockPosIn, final PlayerEntity playerEntityIn, final Hand handIn, final BlockRayTraceResult blockRayTraceResultIn) {
		if (playerEntityIn.isCrouching()) {
			/*
			 * Seynax : allows the player to remove the contents of all stuck basins, with a single click with an empty bucket while sneaking
			 */
			final ItemStack itemStack = playerEntityIn.getMainHandItem();
			if (itemStack.getItem() instanceof BucketItem && ((BucketItem) itemStack.getItem()).getFluid() instanceof WaterFluid) {
				blockStateIn = blockStateIn.setValue(BlockStateProperties.LEVEL, 16);

				worldIn.setBlock(blockPosIn, blockStateIn, 10);
			}
			else if (itemStack.getItem() instanceof PotionItem) {
				final Potion potion = PotionUtils.getPotion(itemStack);

				if (potion.getEffects().size() <= 0) {
					final int newLevel = blockStateIn.getValue(BlockStateProperties.LEVEL) + 1;
					blockStateIn = blockStateIn.setValue(BlockStateProperties.LEVEL, newLevel < 16 ? newLevel : 15);

					worldIn.setBlock(blockPosIn, blockStateIn, 10);
				}
			}
			else if (itemStack.getItem() instanceof GlassBottleItem) {
				final Potion potion = PotionUtils.getPotion(itemStack);

				if (potion.getEffects().size() <= 0) {
					final int newLevel = blockStateIn.getValue(BlockStateProperties.LEVEL) - 1;
					blockStateIn = blockStateIn.setValue(BlockStateProperties.LEVEL, Math.max(newLevel, 0));

					worldIn.setBlock(blockPosIn, blockStateIn, 10);

					if (!playerEntityIn.isCreative()) {
						playerEntityIn.getMainHandItem().shrink(1);
						playerEntityIn.inventory.add(new ItemStack(Items.GLASS_BOTTLE));
					}
				}
			}
			else if (itemStack.getItem() instanceof BucketItem && ((BucketItem) itemStack.getItem()).getFluid() instanceof EmptyFluid) {
				PoolBlock.REMOVE_WATER_MAP.clear();
				if (PoolBlock.removeWater(PoolBlock.REMOVE_WATER_MAP, blockStateIn, blockPosIn, worldIn, 0, 0)) {
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

	@Override
	public BlockState updateShape(BlockState stateIn, final Direction directionIn, final BlockState facingStateIn, final IWorld worldIn, final BlockPos currentPosIn, final BlockPos facingPosIn) {
		if (directionIn.getAxis().isHorizontal()) {
			final boolean hasPoolInSide = facingStateIn.getBlock() == this;
			if (hasPoolInSide && facingStateIn.getValue(BlockStateProperties.LEVEL) > 0) {
				stateIn = stateIn.setValue(BlockStateProperties.LEVEL, facingStateIn.getValue(BlockStateProperties.LEVEL));
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

		return super.updateShape(stateIn, directionIn, facingStateIn, worldIn, currentPosIn, facingPosIn);
	}
}