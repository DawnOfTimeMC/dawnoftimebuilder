package org.dawnoftimebuilder.block.japanese;

import org.dawnoftimebuilder.block.ICustomBlockItem;
import org.dawnoftimebuilder.block.templates.BlockDoTB;
import org.dawnoftimebuilder.registry.DoTBBlocksRegistry;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MapleLeavesBlock extends BlockDoTB implements ICustomBlockItem {
	public static final IntegerProperty	MULTIBLOCK_X	= DoTBBlockStateProperties.MULTIBLOCK_3X;
	public static final IntegerProperty	MULTIBLOCK_Y	= DoTBBlockStateProperties.MULTIBLOCK_2Y;
	public static final IntegerProperty	MULTIBLOCK_Z	= DoTBBlockStateProperties.MULTIBLOCK_3Z;

	public MapleLeavesBlock(final Properties properties) {
		super(properties);

		this.registerDefaultState(this.defaultBlockState().setValue(MapleTrunkBlock.FACING, Direction.NORTH).setValue(MapleLeavesBlock.MULTIBLOCK_X, 0).setValue(MapleLeavesBlock.MULTIBLOCK_Y, 0).setValue(MapleLeavesBlock.MULTIBLOCK_Z, 0));
	}

	@Override
	public void playerWillDestroy(final World worldIn, final BlockPos blockPosIn, final BlockState blockStateIn, final PlayerEntity playerEntityIn) {
		if (!worldIn.isClientSide) {
			final float	currentX	= -blockStateIn.getValue(MapleLeavesBlock.MULTIBLOCK_X);
			final float	currentY	= -blockStateIn.getValue(MapleLeavesBlock.MULTIBLOCK_Y);
			final float	currentZ	= -blockStateIn.getValue(MapleLeavesBlock.MULTIBLOCK_Z);
			for (int x = 0; x <= 2; x++) {
				for (int y = 0; y <= 1; y++) {
					for (int z = 0; z <= 2; z++) {
						final BlockPos		baseBlockPos	= new BlockPos(blockPosIn.getX() + x + currentX, blockPosIn.getY() + y + currentY, blockPosIn.getZ() + z + currentZ);
						final BlockState	state			= worldIn.getBlockState(baseBlockPos);
						worldIn.setBlock(baseBlockPos, Blocks.AIR.defaultBlockState(), 35);
						worldIn.levelEvent(playerEntityIn, 2001, blockPosIn, Block.getId(state));
					}
				}
			}

			if (!playerEntityIn.isCreative()) {
				final BlockPos trunkBlockPos = new BlockPos(blockPosIn.getX() + currentX + 1, blockPosIn.getY() + currentY - 1, blockPosIn.getZ() + currentZ + 1);
				worldIn.destroyBlock(trunkBlockPos, true);
			}
			else {
				final BlockPos		trunkBlockPos	= new BlockPos(blockPosIn.getX() + currentX + 1, blockPosIn.getY() + currentY - 1, blockPosIn.getZ() + currentZ + 1);
				final BlockState	state			= worldIn.getBlockState(trunkBlockPos);
				worldIn.setBlock(trunkBlockPos, Blocks.AIR.defaultBlockState(), 35);
				worldIn.levelEvent(playerEntityIn, 2001, blockPosIn, Block.getId(state));
			}
		}

		super.playerWillDestroy(worldIn, blockPosIn, blockStateIn, playerEntityIn);
	}

	@Override
	protected void createBlockStateDefinition(final StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(MapleTrunkBlock.FACING, MapleLeavesBlock.MULTIBLOCK_X, MapleLeavesBlock.MULTIBLOCK_Y, MapleLeavesBlock.MULTIBLOCK_Z);
	}

	@Override
	public BlockState updateShape(final BlockState stateIn, final Direction facing, final BlockState facingState, final IWorld worldIn, final BlockPos currentPos, final BlockPos facingPos) {
		final Direction	currentFacing	= stateIn.getValue(MapleTrunkBlock.FACING);
		final float		multiblockX		= stateIn.getValue(MapleLeavesBlock.MULTIBLOCK_X);
		final float		multiblockY		= stateIn.getValue(MapleLeavesBlock.MULTIBLOCK_Y);
		final float		multiblockZ		= stateIn.getValue(MapleLeavesBlock.MULTIBLOCK_Z);

		if (Direction.DOWN.equals(facing)) {
			if (multiblockX == 1 && multiblockY == 0 && multiblockZ == 1) {
				final BlockState state = worldIn.getBlockState(currentPos.offset(0, -1, 0));

				if (!(state.getBlock() instanceof MapleTrunkBlock) || currentFacing == null || !currentFacing.equals(state.getValue(MapleTrunkBlock.FACING))) {
					return Blocks.AIR.defaultBlockState();
				}
			}
			else if (multiblockY == 1) {
				final BlockState state = worldIn.getBlockState(currentPos.offset(0, -1, 0));

				if (!(state.getBlock() instanceof MapleLeavesBlock) || currentFacing == null || !currentFacing.equals(state.getValue(MapleTrunkBlock.FACING))) {
					return Blocks.AIR.defaultBlockState();
				}
			}
		}
		else if (Direction.UP.equals(facing) && multiblockY == 0) {
			final BlockState state = worldIn.getBlockState(currentPos.offset(0, 1, 0));

			if (!(state.getBlock() instanceof MapleLeavesBlock) || currentFacing == null || !currentFacing.equals(state.getValue(MapleTrunkBlock.FACING))) {
				return Blocks.AIR.defaultBlockState();
			}
		}
		else if (Direction.WEST.equals(facing) && multiblockX > 0) {
			final BlockState state = worldIn.getBlockState(currentPos.offset(-1, 0, 0));

			if (!(state.getBlock() instanceof MapleLeavesBlock) || currentFacing == null || !currentFacing.equals(state.getValue(MapleTrunkBlock.FACING))) {
				return Blocks.AIR.defaultBlockState();
			}
		}
		else if (Direction.EAST.equals(facing)) {
			if (multiblockX < 2) {
				final BlockState state = worldIn.getBlockState(currentPos.offset(1, 0, 0));

				if (!(state.getBlock() instanceof MapleLeavesBlock) || currentFacing == null || !currentFacing.equals(state.getValue(MapleTrunkBlock.FACING))) {
					return Blocks.AIR.defaultBlockState();
				}
			}
		}
		else if (Direction.NORTH.equals(facing)) {
			if (multiblockZ > 0) {
				final BlockState state = worldIn.getBlockState(currentPos.offset(0, 0, -1));

				if (!(state.getBlock() instanceof MapleLeavesBlock) || currentFacing == null || !currentFacing.equals(state.getValue(MapleTrunkBlock.FACING))) {
					return Blocks.AIR.defaultBlockState();
				}
			}
		}
		else if (Direction.SOUTH.equals(facing) && multiblockZ < 2) {
			final BlockState state = worldIn.getBlockState(currentPos.offset(0, 0, 1));

			if (!(state.getBlock() instanceof MapleLeavesBlock) || currentFacing == null || !currentFacing.equals(state.getValue(MapleTrunkBlock.FACING))) {
				return Blocks.AIR.defaultBlockState();
			}
		}

		return stateIn;
	}

	@Override
	public PushReaction getPistonPushReaction(final BlockState state) {
		return PushReaction.DESTROY;
	}

	@Override
	public Item getCustomBlockItem() {
		return null;
	}

	@Override
	public ItemStack getPickBlock(final BlockState stateIn, final RayTraceResult targetIn, final IBlockReader worldIn, final BlockPos posIn, final PlayerEntity playerIn) {
		return new ItemStack(DoTBBlocksRegistry.MAPLE_RED_SAPLING.get().asItem());
	}

	@Override
	public VoxelShape getBlockSupportShape(final BlockState p_230335_1_, final IBlockReader p_230335_2_, final BlockPos p_230335_3_) {
		return VoxelShapes.empty();
	}

	/**
	 * Light corrections methods
	 */

	private static final VoxelShape VS = Block.box(0.1D, 0.1D, 0.1D, 15.9D, 15.9D, 15.9D);

	@Override
	public VoxelShape getShape(final BlockState state, final IBlockReader worldIn, final BlockPos pos, final ISelectionContext context) {
		return MapleLeavesBlock.VS;
	}

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
