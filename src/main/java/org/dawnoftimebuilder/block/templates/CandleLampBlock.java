package org.dawnoftimebuilder.block.templates;

import org.dawnoftimebuilder.block.IBlockSpecialDisplay;
import org.dawnoftimebuilder.util.DoTBUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.PotionUtils;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class CandleLampBlock extends WaterloggedBlock implements IBlockSpecialDisplay {

	public static final BooleanProperty LIT = BlockStateProperties.LIT;

	public CandleLampBlock(final Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(CandleLampBlock.LIT, false));
	}

	@Override
	protected void createBlockStateDefinition(final StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(CandleLampBlock.LIT);
	}

	@OnlyIn(Dist.CLIENT)
	public void animateLitCandle(final BlockState stateIn, final World worldIn, final BlockPos pos, final double x, final double y, final double z) {
		if (stateIn.getValue(CandleLampBlock.LIT)) {
			final double	d0	= pos.getX() + x;
			final double	d1	= pos.getY() + y;
			final double	d2	= pos.getZ() + z;
			worldIn.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
			worldIn.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.01D, 0.0D);
		}
	}

	@Override
	public ActionResultType use(final BlockState state, final World worldIn, final BlockPos pos, final PlayerEntity player, final Hand handIn, final BlockRayTraceResult hit) {
		return DoTBUtils.changeBlockLitStateWithItemOrCreativePlayer(state, worldIn, pos, player, handIn) >= 0 ? ActionResultType.SUCCESS : ActionResultType.PASS;
	}

	@Override
	public void onProjectileHit(final World worldIn, final BlockState state, final BlockRayTraceResult hit, final ProjectileEntity projectile) {

		int activation = -1;

		if (!state.getValue(WaterloggedBlock.WATERLOGGED) && !state.getValue(CandleLampBlock.LIT) && (projectile instanceof AbstractArrowEntity && ((AbstractArrowEntity) projectile).isOnFire() || projectile instanceof FireballEntity)) {
			activation = 1;
		}
		else if (state.getValue(CandleLampBlock.LIT) && (projectile instanceof SnowballEntity || projectile instanceof PotionEntity && PotionUtils.getPotion(((PotionEntity) projectile).getItem()).getEffects().size() <= 0)) {
			activation = 0;
		}

		if (activation >= 0) {

			final BlockPos	pos			= hit.getBlockPos();
			final boolean	isActivated	= activation == 1;

			if (!worldIn.isClientSide()) {
				worldIn.setBlock(pos, state.setValue(CandleLampBlock.LIT, isActivated), 10);
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
	public boolean placeLiquid(final IWorld world, final BlockPos pos, final BlockState state, final FluidState fluid) {
		if (state.getValue(WaterloggedBlock.WATERLOGGED) || fluid.getType() != Fluids.WATER) {
			return false;
		}
		if (state.getValue(CandleLampBlock.LIT)) {
			world.playSound(null, pos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1.0F, 1.0F);
		}
		world.setBlock(pos, state.setValue(WaterloggedBlock.WATERLOGGED, true).setValue(CandleLampBlock.LIT, false), 10);
		world.getLiquidTicks().scheduleTick(pos, fluid.getType(), fluid.getType().getTickDelay(world));
		return true;
	}

	@Override
	public boolean emitsLight() {
		return true;
	}
}
