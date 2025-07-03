package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.block.IBlockSpecialDisplay;
import org.dawnoftime.dawnoftime.util.Utils;

public abstract class CandleLampBlock extends WaterloggedBlock implements IBlockSpecialDisplay {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public CandleLampBlock(final Properties properties, VoxelShape[] shapes) {
        super(properties, shapes);
        this.registerDefaultState(this.defaultBlockState().setValue(CandleLampBlock.LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(CandleLampBlock.LIT);
    }

    /**
     * Call this function in animateTick to render the list candle effect at the given coordinate xyz.
     * @param stateIn Current state of the block.
     * @param worldIn World where the block is placed.
     * @param pos BlockPos of the block.
     * @param x coordinate offset (between 0 and 1).
     * @param y coordinate offset (between 0 and 1).
     * @param z coordinate offset (between 0 and 1).
     */
    public void animateLitCandle(final BlockState stateIn, final Level worldIn, final BlockPos pos, final double x, final double y, final double z) {
        if(stateIn.getValue(CandleLampBlock.LIT)) {
            final double d0 = pos.getX() + x;
            final double d1 = pos.getY() + y;
            final double d2 = pos.getZ() + z;
            worldIn.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            worldIn.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.01D, 0.0D);
        }
    }

    @Override
    public InteractionResult use(final BlockState state, final Level worldIn, final BlockPos pos, final Player player, final InteractionHand handIn, final BlockHitResult hit) {
        return Utils.changeBlockLitStateWithItemOrCreativePlayer(state, worldIn, pos, player, handIn) >= 0 ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Override
    public void onProjectileHit(final Level worldIn, final BlockState state, final BlockHitResult hit, final Projectile projectile) {

        int activation = -1;

        if(!state.getValue(WaterloggedBlock.WATERLOGGED) && !state.getValue(CandleLampBlock.LIT) && (projectile instanceof AbstractArrow && ((AbstractArrow) projectile).isOnFire() || projectile instanceof Fireball)) {
            activation = 1;
        } else if(state.getValue(CandleLampBlock.LIT) && (projectile instanceof Snowball || projectile instanceof ThrowableProjectile && PotionUtils.getPotion(((ThrowableItemProjectile) projectile).getItem()).getEffects().size() <= 0)) {
            activation = 0;
        }

        if(activation >= 0) {

            final BlockPos pos = hit.getBlockPos();
            final boolean isActivated = activation == 1;

            if(!worldIn.isClientSide()) {
                worldIn.setBlock(pos, state.setValue(CandleLampBlock.LIT, isActivated), 10);
                worldIn.playSound(null, pos, isActivated ? SoundEvents.FIRE_AMBIENT : SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
            } else if(!isActivated && worldIn.isClientSide()) {
                for(int i = 0; i < worldIn.random.nextInt(1) + 1; ++i) {
                    worldIn.addParticle(ParticleTypes.CLOUD, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, worldIn.random.nextFloat() / 4.0F, 2.5E-5D, worldIn.random.nextFloat() / 4.0F);
                }
            }
        }
    }

    @Override
    public boolean placeLiquid(final LevelAccessor world, final BlockPos pos, final BlockState state, final FluidState fluid) {
        if(state.getValue(WaterloggedBlock.WATERLOGGED) || fluid.getType() != Fluids.WATER) {
            return false;
        }
        if(state.getValue(CandleLampBlock.LIT)) {
            world.playSound(null, pos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
        world.setBlock(pos, state.setValue(WaterloggedBlock.WATERLOGGED, true).setValue(CandleLampBlock.LIT, false), 10);
        world.scheduleTick(pos, fluid.getType(), fluid.getType().getTickDelay(world));
        return true;
    }

    @Override
    public boolean emitsLight() {
        return true;
    }
}
