package org.dawnoftimebuilder.block.general;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import org.dawnoftimebuilder.block.templates.WaterloggedBlock;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties.HorizontalConnection;
import org.dawnoftimebuilder.util.DoTBUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.PotionUtils;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FireplaceBlock extends WaterloggedBlock {

	public static final EnumProperty<Direction.Axis>								HORIZONTAL_AXIS			= BlockStateProperties.HORIZONTAL_AXIS;
	public static final BooleanProperty												LIT						= BlockStateProperties.LIT;
	public static final EnumProperty<DoTBBlockStateProperties.HorizontalConnection>	HORIZONTAL_CONNECTION	= DoTBBlockStateProperties.HORIZONTAL_CONNECTION;

	private static final VoxelShape													ON_X_SHAPE				= net.minecraft.block.Block.box(0.0D, 0.0D, 2.0D, 16.0D, 14.0D, 14.0D);
	private static final VoxelShape													OFF_X_SHAPE				= net.minecraft.block.Block.box(0.0D, 0.0D, 2.0D, 16.0D, 5.0D, 14.0D);
	private static final VoxelShape													ON_Z_SHAPE				= net.minecraft.block.Block.box(2.0D, 0.0D, 0.0D, 14.0D, 14.0D, 16.0D);
	private static final VoxelShape													OFF_Z_SHAPE				= net.minecraft.block.Block.box(2.0D, 0.0D, 0.0D, 14.0D, 5.0D, 16.0D);

	public FireplaceBlock(final Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(FireplaceBlock.LIT, false).setValue(FireplaceBlock.HORIZONTAL_AXIS, Direction.Axis.X).setValue(FireplaceBlock.HORIZONTAL_CONNECTION, HorizontalConnection.NONE));
	}

	@Override
	protected void createBlockStateDefinition(final StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FireplaceBlock.HORIZONTAL_AXIS, FireplaceBlock.LIT, FireplaceBlock.HORIZONTAL_CONNECTION);
	}

	@Override
	public VoxelShape getCollisionShape(final BlockState state, final IBlockReader worldIn, final BlockPos pos, final ISelectionContext context) {
		return state.getValue(FireplaceBlock.HORIZONTAL_AXIS) == Direction.Axis.X ? FireplaceBlock.OFF_X_SHAPE : FireplaceBlock.OFF_Z_SHAPE;
	}

	@Override
	public VoxelShape getShape(final BlockState state, final IBlockReader worldIn, final BlockPos pos, final ISelectionContext context) {
		if (state.getValue(FireplaceBlock.HORIZONTAL_AXIS) == Direction.Axis.X) {
			return state.getValue(FireplaceBlock.LIT) ? FireplaceBlock.ON_X_SHAPE : FireplaceBlock.OFF_X_SHAPE;
		}
		return state.getValue(FireplaceBlock.LIT) ? FireplaceBlock.ON_Z_SHAPE : FireplaceBlock.OFF_Z_SHAPE;
	}

	@Override
	public BlockState updateShape(BlockState stateIn, final Direction facing, final BlockState facingState, final IWorld worldIn, final BlockPos currentPos, final BlockPos facingPos) {
		stateIn = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		return !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public boolean canSurvive(final BlockState state, final IWorldReader worldIn, final BlockPos pos) {
		return Block.canSupportCenter(worldIn, pos.below(), Direction.UP);
	}

	@Override
	public ActionResultType use(final BlockState state, final World worldIn, final BlockPos pos, final PlayerEntity player, final Hand handIn, final BlockRayTraceResult hit) {
		return DoTBUtils.changeBlockLitStateWithItemOrCreativePlayer(state, worldIn, pos, player, handIn) >= 0 ? ActionResultType.SUCCESS : ActionResultType.PASS;
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
				worldIn.setBlock(pos, state.setValue(FireplaceBlock.LIT, isActivated), 10);
				worldIn.playSound(null, pos, isActivated ? SoundEvents.FIRE_AMBIENT : SoundEvents.FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}
			else if (!isActivated && worldIn.isClientSide()) {
				for (int i = 0; i < worldIn.random.nextInt(1) + 1; ++i) {
					worldIn.addParticle(ParticleTypes.CLOUD, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, worldIn.random.nextFloat() / 4.0F, 2.5E-5D, worldIn.random.nextFloat() / 4.0F);
				}
			}
		}
	}

	@Override
	public void entityInside(final BlockState state, final World world, final BlockPos pos, final Entity entityIn) {
		if (!entityIn.fireImmune() && state.getValue(FireplaceBlock.LIT) && entityIn instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entityIn)) {
			entityIn.hurt(DamageSource.IN_FIRE, 1.0F);
		}
		super.entityInside(state, world, pos, entityIn);
	}

	@Override
	public BlockState getStateForPlacement(final BlockItemUseContext context) {
		final BlockState		state	= super.getStateForPlacement(context);
		final Direction.Axis	axis	= context.getHorizontalDirection().getAxis() == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
		return state.setValue(FireplaceBlock.HORIZONTAL_AXIS, axis).setValue(FireplaceBlock.HORIZONTAL_CONNECTION, this.getHorizontalShape(context.getLevel(), context.getClickedPos(), axis));
	}

	@Override
	public void neighborChanged(BlockState state, final World worldIn, final BlockPos pos, final net.minecraft.block.Block blockIn, final BlockPos fromPos, final boolean isMoving) {
		super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
		if (pos.getY() == fromPos.getY()) {
			final Direction.Axis axis = state.getValue(FireplaceBlock.HORIZONTAL_AXIS);
			if (axis == Direction.Axis.X) {
				if (pos.getX() == fromPos.getX()) {
					return;
				}
			}
			else if (pos.getZ() == fromPos.getZ()) {
				return;
			}

			state = state.setValue(FireplaceBlock.HORIZONTAL_CONNECTION, this.getHorizontalShape(worldIn, pos, axis));

			final BlockState newState = worldIn.getBlockState(fromPos);
			if (newState.getBlock() instanceof FireplaceBlock && newState.getValue(FireplaceBlock.HORIZONTAL_AXIS) == axis && newState.getValue(FireplaceBlock.LIT) != state.getValue(FireplaceBlock.LIT)) {
				if (newState.getValue(FireplaceBlock.LIT) && state.getValue(WaterloggedBlock.WATERLOGGED)) {
					return;
				}
				worldIn.setBlock(pos, state.setValue(FireplaceBlock.LIT, newState.getValue(FireplaceBlock.LIT)), 10);
				final BlockPos newPos = axis == Direction.Axis.X ? pos.relative(Direction.EAST, pos.getX() - fromPos.getX()) : pos.relative(Direction.SOUTH, pos.getZ() - fromPos.getZ());
				worldIn.getBlockState(newPos).neighborChanged(worldIn, newPos, this, pos, false);
				return;
			}
			worldIn.setBlock(pos, state, 10);
		}
	}

	private DoTBBlockStateProperties.HorizontalConnection getHorizontalShape(final World worldIn, final BlockPos pos, final Direction.Axis axis) {

		final BlockState	left		= worldIn.getBlockState(pos.relative(axis == Direction.Axis.X ? Direction.EAST : Direction.SOUTH, 1));
		final BlockState	right		= worldIn.getBlockState(pos.relative(axis == Direction.Axis.X ? Direction.EAST : Direction.SOUTH, -1));

		boolean				blockLeft	= left.getBlock() instanceof FireplaceBlock;
		if (blockLeft) {
			blockLeft = left.getValue(FireplaceBlock.HORIZONTAL_AXIS) == axis;
		}

		boolean blockRight = right.getBlock() instanceof FireplaceBlock;
		if (blockRight) {
			blockRight = right.getValue(FireplaceBlock.HORIZONTAL_AXIS) == axis;
		}

		if (blockLeft) {
			return blockRight ? DoTBBlockStateProperties.HorizontalConnection.BOTH : DoTBBlockStateProperties.HorizontalConnection.LEFT;
		}
		return blockRight ? DoTBBlockStateProperties.HorizontalConnection.RIGHT : DoTBBlockStateProperties.HorizontalConnection.NONE;
	}

	@Override
	public boolean placeLiquid(final IWorld world, final BlockPos pos, final BlockState state, final FluidState fluid) {
		if (state.getValue(BlockStateProperties.WATERLOGGED) || fluid.getType() != Fluids.WATER) {
			return false;
		}
		if (state.getValue(FireplaceBlock.LIT)) {
			world.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
		}
		world.setBlock(pos, state.setValue(WaterloggedBlock.WATERLOGGED, true).setValue(FireplaceBlock.LIT, false), 10);
		world.getLiquidTicks().scheduleTick(pos, fluid.getType(), fluid.getType().getTickDelay(world));
		return true;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(final BlockState stateIn, final World worldIn, final BlockPos pos, final Random rand) {
		if (stateIn.getValue(FireplaceBlock.LIT)) {
			if (rand.nextInt(10) == 0) {
				worldIn.playLocalSound(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, SoundEvents.CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, 0.5F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.6F, false);
			}

			if (rand.nextInt(10) == 0) {
				for (int i = 0; i < rand.nextInt(1) + 1; ++i) {
					worldIn.addParticle(ParticleTypes.LAVA, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, rand.nextFloat() / 4.0F, 2.5E-5D, rand.nextFloat() / 4.0F);
				}
			}
			worldIn.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, pos.getX() + 0.5D + rand.nextDouble() / 3.0D * (rand.nextBoolean() ? 1 : -1), pos.getY() + 0.4D, pos.getZ() + 0.5D + rand.nextDouble() / 3.0D * (rand.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
		}
	}

	@Override
	public BlockState rotate(final BlockState state, final Rotation rot) {
		final Direction.Axis axis = state.getValue(FireplaceBlock.HORIZONTAL_AXIS);
		return rot == Rotation.CLOCKWISE_90 || rot == Rotation.COUNTERCLOCKWISE_90 ? state.setValue(FireplaceBlock.HORIZONTAL_AXIS, axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X) : state;
	}

	@Override
	public void appendHoverText(final ItemStack stack, @Nullable final IBlockReader worldIn, final List<ITextComponent> tooltip, final ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		DoTBUtils.addTooltip(tooltip, DoTBUtils.TOOLTIP_FIREPLACE);
	}
}