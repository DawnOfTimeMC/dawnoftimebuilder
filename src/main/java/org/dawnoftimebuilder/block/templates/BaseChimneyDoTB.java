/**
 *
 */
package org.dawnoftimebuilder.block.templates;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import org.dawnoftimebuilder.block.general.FireplaceBlock;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties.VerticalConnection;
import org.dawnoftimebuilder.util.DoTBUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.PotionUtils;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author Seynax
 *
 */
public class BaseChimneyDoTB extends ColumnConnectibleBlock {

	private static final VoxelShape[]	SHAPES	= BaseChimneyDoTB.makeShapes();
	public static final BooleanProperty	LIT		= BlockStateProperties.LIT;

	public BaseChimneyDoTB(final Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.LIT, true));
	}

	@Override
	protected void createBlockStateDefinition(final StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(BlockStateProperties.LIT);
	}

	@Override
	public BlockState getStateForPlacement(final BlockItemUseContext context) {
		final BlockState state = super.getStateForPlacement(context);
		final World world = context.getLevel();
		BlockPos belowBlockPos = context.getClickedPos().below();
		BlockState	blockState = world.getBlockState(belowBlockPos);

		if(blockState == null) {
			return state;
		}

		if (blockState.getBlock() instanceof BaseChimneyDoTB) {
			return state.setValue(BlockStateProperties.LIT, blockState.getValue(BlockStateProperties.LIT));
		}

		if(blockState.getBlock() instanceof MultiblockFireplaceBlock) {
			BlockState foundBlockState = blockState;
			belowBlockPos = belowBlockPos.below();
			while((foundBlockState = world.getBlockState(belowBlockPos)) != null && foundBlockState.getBlock() instanceof MultiblockFireplaceBlock) {
				blockState = foundBlockState;
				belowBlockPos = belowBlockPos.below();
			}
			return state.setValue(BlockStateProperties.LIT, blockState.getValue(BlockStateProperties.LIT));
		}
		return state;
	}

	@Override
	public ActionResultType use(final BlockState blockStateIn, final World worldIn, final BlockPos blockPosIn, final PlayerEntity player, final Hand handIn, final BlockRayTraceResult hit) {
		if(super.use(blockStateIn, worldIn, blockPosIn, player, handIn, hit).equals(ActionResultType.SUCCESS))
			return ActionResultType.SUCCESS;

		final int activation = DoTBUtils.changeBlockLitStateWithItemOrCreativePlayer(blockStateIn, worldIn, blockPosIn, player, handIn);
		if (activation >= 0) {
			final boolean isActivated = activation == 1;

			BaseChimneyDoTB.updateAllChimneyConductParts(isActivated, blockStateIn, blockPosIn, worldIn);
			BaseChimneyDoTB.updateFireplace(isActivated, blockPosIn, worldIn);

			return ActionResultType.SUCCESS;
		}

		return ActionResultType.PASS;
	}

	@Override
	public void onProjectileHit(final World worldIn, final BlockState state, final BlockRayTraceResult hit, final ProjectileEntity projectile) {

		int activation = -1;

		if (!state.getValue(WaterloggedBlock.WATERLOGGED) && !state.getValue(FireplaceBlock.LIT) && (projectile instanceof AbstractArrowEntity && ((AbstractArrowEntity) projectile).isOnFire() || projectile instanceof FireballEntity)) {
			activation = 1;
		}
		else if (state.getValue(FireplaceBlock.LIT) && (projectile instanceof SnowballEntity || projectile instanceof PotionEntity && PotionUtils.getPotion(((PotionEntity) projectile).getItem()).getEffects().size() <= 0)) {
			activation = 0;
		}

		if (activation >= 0) {
			final BlockPos	pos			= hit.getBlockPos();
			final boolean	isActivated	= activation == 1;

			if (!worldIn.isClientSide()) {
				BaseChimneyDoTB.updateAllChimneyConductParts(isActivated, state, pos, worldIn);
				worldIn.playSound(null, pos, isActivated ? SoundEvents.FIRE_AMBIENT : SoundEvents.FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}
			else if (!isActivated && worldIn.isClientSide()) {
				for (int i = 0; i < worldIn.random.nextInt(1) + 1; ++i) {
					worldIn.addParticle(ParticleTypes.CLOUD, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, worldIn.random.nextFloat() / 4.0F, 2.5E-5D, worldIn.random.nextFloat() / 4.0F);
				}
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(final BlockState stateIn, final World worldIn, final BlockPos pos, final Random rand) {
		if (stateIn.getValue(WaterloggedBlock.WATERLOGGED) || !stateIn.getValue(BlockStateProperties.LIT)) {
			return;
		}
		if (stateIn.getValue(ColumnConnectibleBlock.VERTICAL_CONNECTION) == DoTBBlockStateProperties.VerticalConnection.UNDER || stateIn.getValue(ColumnConnectibleBlock.VERTICAL_CONNECTION) == DoTBBlockStateProperties.VerticalConnection.NONE) {
			worldIn.addParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, true, pos.getX() + rand.nextDouble() * 0.5D + 0.25D, pos.getY() + rand.nextDouble() * 0.5D + 0.3D, pos.getZ() + rand.nextDouble() * 0.5D + 0.25D, 0.0D, 0.07D, 0.0D);
			worldIn.addParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, pos.getX() + rand.nextDouble() * 0.5D + 0.25D, pos.getY() + rand.nextDouble() * 0.5D + 0.3D, pos.getZ() + rand.nextDouble() * 0.5D + 0.25D, 0.0D, 0.04D, 0.0D);
		}
	}

	@Override
	public VoxelShape getShape(final BlockState state, final IBlockReader worldIn, final BlockPos pos, final ISelectionContext context) {
		if (state.getValue(ColumnConnectibleBlock.VERTICAL_CONNECTION) == DoTBBlockStateProperties.VerticalConnection.NONE) {
			return BaseChimneyDoTB.SHAPES[0];
		}
		return BaseChimneyDoTB.SHAPES[state.getValue(ColumnConnectibleBlock.VERTICAL_CONNECTION).getIndex() - 1];
	}

	/**
	 * @return Stores VoxelShape with index : <p/>
	 * 0 : None or Under <p/>
	 * 1 : Above
	 * 2 : Both
	 */
	private static VoxelShape[] makeShapes() {
		return new VoxelShape[]{
				VoxelShapes.or(
						Block.box(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D),
						Block.box(1.0D, 8.0D, 1.0D, 15.0D, 11.0D, 15.0D)
				),
				VoxelShapes.or(
						Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
						Block.box(2.0D, 8.0D, 2.0D, 14.0D, 16.0D, 14.0D)
				),
				Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D)
		};
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos)
	{
		if(facingState.getBlock() instanceof BaseChimneyDoTB || facingState.getBlock() instanceof MultiblockFireplaceBlock)
		{
			stateIn.getBlockState().setValue(LIT, facingState.getBlockState().getValue(LIT));
		}

		return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public void appendHoverText(final ItemStack stack, @Nullable final IBlockReader worldIn, final List<ITextComponent> tooltip, final ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		DoTBUtils.addTooltip(tooltip, DoTBUtils.TOOLTIP_FIREPLACE);
	}

	public static void updateAllChimneyConductParts(final boolean isActivatedIn, BlockState stateIn, final BlockPos blockPosIn, final World worldIn) {
		stateIn = stateIn.setValue(BlockStateProperties.LIT, isActivatedIn);
		worldIn.setBlock(blockPosIn, stateIn, 10);
		BaseChimneyDoTB.updateIsActivatedInAllPartsOfBottom(isActivatedIn, stateIn, blockPosIn, worldIn);
		BaseChimneyDoTB.updateIsActivatedInAllPartsOfTop(isActivatedIn, stateIn, blockPosIn, worldIn);
	}

	public static void updateIsActivatedInAllPartsOfBottom(final boolean isActivatedIn, final BlockState stateIn, final BlockPos blockPosIn, final World worldIn) {
		BlockState	blockState	= null;
		BlockPos	blockPos	= blockPosIn;
		while ((blockState = worldIn.getBlockState(blockPos = blockPos.below())) != null && blockState.getBlock() instanceof BaseChimneyDoTB) {
			blockState = blockState.setValue(BlockStateProperties.LIT, isActivatedIn);
			worldIn.setBlock(blockPos, blockState, 10);
		}
	}

	public static void updateIsActivatedInAllPartsOfTop(final boolean isActivatedIn, final BlockState stateIn, final BlockPos blockPosIn, final World worldIn) {
		BlockState	blockState	= null;
		BlockPos	blockPos	= blockPosIn;
		while ((blockState = worldIn.getBlockState(blockPos = blockPos.above())) != null && blockState.getBlock() instanceof BaseChimneyDoTB) {
			blockState = blockState.setValue(BlockStateProperties.LIT, isActivatedIn);
			worldIn.setBlock(blockPos, blockState, 10);
		}
	}

	public static void updateFireplace(final boolean isActivatedIn, final BlockPos blockPosIn, final World worldIn) {
		BlockState	blockState	= null;
		BlockPos	blockPos	= blockPosIn;
		while ((blockState = worldIn.getBlockState(blockPos = blockPos.below())) != null && blockState.getBlock() instanceof BaseChimneyDoTB) {
		}

		while ((blockState = worldIn.getBlockState(blockPos = blockPos.below())) != null && blockState.getBlock() instanceof MultiblockFireplaceBlock) {
			final VerticalConnection verticalConnection = blockState.getValue(ColumnConnectibleBlock.VERTICAL_CONNECTION);
			if (verticalConnection != null && VerticalConnection.ABOVE.equals(verticalConnection)) {
				blockState = blockState.setValue(BlockStateProperties.LIT, isActivatedIn);
				worldIn.setBlock(blockPos, blockState, 10);
				final Direction direction = blockState.getValue(SidedColumnConnectibleBlock.FACING);
				worldIn.getBlockState(blockPos.relative(direction.getCounterClockWise())).neighborChanged(worldIn, blockPos.relative(direction.getCounterClockWise()), blockState.getBlock(), blockPos, false);
				worldIn.getBlockState(blockPos.relative(direction.getClockWise())).neighborChanged(worldIn, blockPos.relative(direction.getClockWise()), blockState.getBlock(), blockPos, false);

			}
		}
	}
}