package org.dawnoftimebuilder.block.japanese;

import java.util.Random;

import javax.annotation.Nullable;

import org.dawnoftimebuilder.DawnOfTimeBuilder;
import org.dawnoftimebuilder.block.IBlockGeneration;
import org.dawnoftimebuilder.block.ICustomBlockItem;
import org.dawnoftimebuilder.block.templates.BushBlockDoT;
import org.dawnoftimebuilder.item.templates.PotAndBlockItem;
import org.dawnoftimebuilder.registry.DoTBBlocksRegistry;

import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.Item;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;

public class MapleSaplingBlock extends BushBlockDoT implements IBlockGeneration, ICustomBlockItem, IGrowable {
	public final static boolean isValidForPlacement(final IWorld worldIn, final BlockPos bottomCenterIn, final boolean isSaplingCallIn) {
		final BlockPos	floorCenter	= bottomCenterIn.below();
		BlockState		state		= worldIn.getBlockState(floorCenter);

		if (!Tags.Blocks.DIRT.contains(state.getBlock())) {
			return false;
		}

		state = worldIn.getBlockState(bottomCenterIn);

		if (!state.getMaterial().isReplaceable() || !isSaplingCallIn) {
			return true;
		}

		for (int x = -1; x <= 1; x++) {
			for (int y = 0; y <= 1; y++) {
				for (int z = -1; z <= 1; z++) {
					state = worldIn.getBlockState(bottomCenterIn.offset(x, y + 1, z));

					if (state != null && !(state.getBlock() instanceof AirBlock)) {
						return false;
					}
				}
			}
		}

		return true;
	}

	public static void placeFinalTreeIfPossible(final IWorld worldIn, final BlockPos centerPosIn) {
		if (MapleSaplingBlock.isValidForPlacement(worldIn, centerPosIn, true)) {
			final Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(worldIn.getRandom());
			worldIn.setBlock(centerPosIn, DoTBBlocksRegistry.MAPLE_RED_TRUNK.get().defaultBlockState().setValue(MapleTrunkBlock.FACING, direction), 10);

			for (int x = -1; x <= 1; x++) {
				for (int y = 0; y <= 1; y++) {
					for (int z = -1; z <= 1; z++) {
						final BlockPos newBlockPosition = new BlockPos(centerPosIn.getX() + x, centerPosIn.getY() + y + 1, centerPosIn.getZ() + z);

						worldIn.setBlock(newBlockPosition, DoTBBlocksRegistry.MAPLE_RED_LEAVES.get().defaultBlockState().setValue(MapleTrunkBlock.FACING, direction).setValue(MapleLeavesBlock.MULTIBLOCK_X, x + 1).setValue(MapleLeavesBlock.MULTIBLOCK_Y, y).setValue(MapleLeavesBlock.MULTIBLOCK_Z, z + 1), 10);
					}
				}
			}
		}
	}

	public static final IntegerProperty	STAGE	= BlockStateProperties.STAGE;
	protected static final VoxelShape	SHAPE	= Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);

	public MapleSaplingBlock(final Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(MapleSaplingBlock.STAGE, 0));
	}

	@Override
	public ActionResultType use(final BlockState p_225533_1_In, final World p_225533_2_In, final BlockPos p_225533_3_In, final PlayerEntity p_225533_4_In, final Hand p_225533_5_In, final BlockRayTraceResult p_225533_6_In) {
		final Item mainItem = p_225533_4_In.getMainHandItem().getItem();
		if (mainItem instanceof FlintAndSteelItem) {
			if (!p_225533_2_In.isClientSide) {
				p_225533_2_In.setBlock(p_225533_3_In, DoTBBlocksRegistry.PAUSED_MAPLE_RED_SAPLING.get().defaultBlockState(), 35);
				p_225533_2_In.levelEvent(p_225533_4_In, 2001, p_225533_3_In, Block.getId(p_225533_1_In));
			}

			return ActionResultType.SUCCESS;
		}

		return ActionResultType.FAIL;
	}

	@Override
	public VoxelShape getShape(final BlockState p_220053_1_In, final IBlockReader p_220053_2_In, final BlockPos p_220053_3_In, final ISelectionContext p_220053_4_In) {
		return MapleSaplingBlock.SHAPE;
	}

	@Override
	public void generateOnPos(final IWorld world, final BlockPos centerPosIn, final BlockState state, final Random random) {
		MapleSaplingBlock.placeFinalTreeIfPossible(world, centerPosIn);
	}

	@Nullable
	@Override
	public Item getCustomBlockItem() {
		return new PotAndBlockItem(this, new Item.Properties().tab(DawnOfTimeBuilder.DOTB_TAB));
	}


	@Override
	protected void createBlockStateDefinition(final StateContainer.Builder<Block, BlockState> p_206840_1_) {
		p_206840_1_.add(MapleSaplingBlock.STAGE);
	}

	/**
	 * Growable code
	 */

	@Override
	public boolean isRandomlyTicking(final BlockState blockstateIn) {
		return true;
	}

	@Override
	public void tick(final BlockState p_225542_1_, final ServerWorld p_225542_2_, final BlockPos p_225542_3_, final Random p_225542_4_) {
		if (p_225542_2_.getMaxLocalRawBrightness(p_225542_3_.above()) >= 9 && p_225542_4_.nextInt(7) == 0) {
			if (!p_225542_2_.isAreaLoaded(p_225542_3_, 1)) {
				return; // Forge: prevent loading unloaded chunks when checking neighbor's light
			}
			this.advanceTree(p_225542_2_, p_225542_3_, p_225542_1_, p_225542_4_);
		}
	}

	public void advanceTree(final ServerWorld p_226942_1_, final BlockPos p_226942_2_, final BlockState p_226942_3_, final Random p_226942_4_) {
		if (p_226942_3_.getValue(MapleSaplingBlock.STAGE) == 0) {
			p_226942_1_.setBlock(p_226942_2_, p_226942_3_.cycle(MapleSaplingBlock.STAGE), 4);
		}
		else if (MapleSaplingBlock.isValidForPlacement(p_226942_1_, p_226942_2_, true)) {
			MapleSaplingBlock.placeFinalTreeIfPossible(p_226942_1_, p_226942_2_);
		}
	}

	@Override
	public boolean isValidBonemealTarget(final IBlockReader p_176473_1_, final BlockPos p_176473_2_, final BlockState p_176473_3_, final boolean p_176473_4_) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(final World p_180670_1_, final Random p_180670_2_, final BlockPos p_180670_3_, final BlockState p_180670_4_) {
		return p_180670_1_.random.nextFloat() < 0.45D;
	}

	@Override
	public void performBonemeal(final ServerWorld p_225535_1_, final Random p_225535_2_, final BlockPos p_225535_3_, final BlockState p_225535_4_) {
		this.advanceTree(p_225535_1_, p_225535_3_, p_225535_4_, p_225535_2_);
	}

	/**
	 * Lights methods
	 */

	@Override
	public boolean useShapeForLightOcclusion(final BlockState p_220074_1_In) {
		return false;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public float getShadeBrightness(final BlockState p_220080_1_, final IBlockReader p_220080_2_, final BlockPos p_220080_3_) {
		return 1.0F;
	}

	@Override
	public boolean propagatesSkylightDown(final BlockState p_200123_1_, final IBlockReader p_200123_2_, final BlockPos p_200123_3_) {
		return true;
	}
}
