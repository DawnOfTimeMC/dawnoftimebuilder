package org.dawnoftime.dawnoftime.block.precolumbian;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
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
import org.dawnoftime.dawnoftime.block.templates.WaterloggedBlock;
import org.dawnoftime.dawnoftime.util.Utils;

import static org.dawnoftime.dawnoftime.util.VoxelShapes.PLASTERED_STONE_CRESSET_SHAPES;

public class PlasteredStoneCressetBlock extends WaterloggedBlock {
    private static final BooleanProperty LIT = BlockStateProperties.LIT;

    public PlasteredStoneCressetBlock(Properties properties) {
        super(properties.lightLevel((state) -> {
            if(state.getValue(WATERLOGGED) || !state.getValue(LIT)) {
                return 0;
            }
            return 15;
        }), PLASTERED_STONE_CRESSET_SHAPES);
        this.registerDefaultState(this.defaultBlockState().setValue(LIT, false).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LIT);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level worldIn, BlockPos pos, Player player, BlockHitResult hit) {
        return Utils.changeBlockLitStateWithItemOrCreativePlayer(state, worldIn, pos, player, player.getUsedItemHand()) >= 0 ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Override
    public void onProjectileHit(Level worldIn, BlockState state, BlockHitResult hit, Projectile projectile) {
        if(!worldIn.isClientSide() && projectile instanceof AbstractArrow) {
            AbstractArrow abstractarrowentity = (AbstractArrow) projectile;
            if(abstractarrowentity.isOnFire() && !state.getValue(LIT) && !state.getValue(WATERLOGGED)) {
                BlockPos pos = hit.getBlockPos();
                worldIn.setBlock(pos, state.setValue(LIT, true), 10);
                worldIn.playSound(null, pos, SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        }
    }

    @Override
    public boolean placeLiquid(LevelAccessor world, BlockPos pos, BlockState state, FluidState fluid) {
        if(!state.getValue(WATERLOGGED) && fluid.getType() == Fluids.WATER) {
            if(state.getValue(LIT)) {
                world.playSound(null, pos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            world.setBlock(pos, state.setValue(WATERLOGGED, true).setValue(LIT, false), 10);
            world.scheduleTick(pos, fluid.getType(), fluid.getType().getTickDelay(world));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
        if(stateIn.getValue(LIT)) {
            if(rand.nextInt(10) == 0) {
                worldIn.playLocalSound((float) pos.getX() + 0.5F, (float) pos.getY() + 0.5F, (float) pos.getZ() + 0.5F, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.5F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.6F, false);
            }
            if(rand.nextInt(10) == 0) {
                for(int i = 0; i < rand.nextInt(1) + 1; ++i) {
                    worldIn.addParticle(ParticleTypes.LAVA, (float) pos.getX() + 0.5F, (float) pos.getY() + 0.5F, (float) pos.getZ() + 0.5F, rand.nextFloat() / 4.0F, 2.5E-5D, rand.nextFloat() / 4.0F);
                }
            }
            if(rand.nextInt(2) == 0) {
                worldIn.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, (double) pos.getX() + 0.5D + rand.nextDouble() / 4.0D * (double) (rand.nextBoolean() ? 1 : -1), (double) pos.getY() + 0.8D, (double) pos.getZ() + 0.5D + rand.nextDouble() / 4.0D * (double) (rand.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
            }
        }
    }
}