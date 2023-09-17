package org.dawnoftimebuilder.block.precolumbian;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.dawnoftimebuilder.block.templates.WaterloggedBlock;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;
import org.dawnoftimebuilder.util.DoTBUtils;

public class PlasteredStoneCressetBlock extends WaterloggedBlock {

    private static final IntegerProperty HEAT = DoTBBlockStateProperties.HEAT_0_4;
    private static final BooleanProperty LIT = BlockStateProperties.LIT;
    private static final VoxelShape VS = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 14.0D, 13.0D);

    public PlasteredStoneCressetBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(LIT, false).setValue(HEAT, 0).setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return VS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LIT, HEAT);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit){
        if (state.getValue(LIT)) {
            worldIn.setBlock(pos, state.setValue(HEAT, 3), 10);
            worldIn.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        } else {
            if(state.getValue(WATERLOGGED)) return InteractionResult.PASS;

            if(DoTBUtils.useLighter(worldIn, pos, player, handIn)){
                worldIn.setBlock(pos, state.setValue(LIT, true).setValue(HEAT, 4), 10);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void onProjectileHit(Level worldIn, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (!worldIn.isClientSide && projectile instanceof AbstractArrow) {
            AbstractArrow abstractarrowentity = (AbstractArrow)projectile;
            if (abstractarrowentity.isOnFire() && !state.getValue(LIT) && !state.getValue(WATERLOGGED)) {
                BlockPos pos = hit.getBlockPos();
                worldIn.setBlock(pos, state.setValue(LIT, true).setValue(HEAT, 4), 10);
                worldIn.playSound(null, pos, SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        }
    }

    @Override
    public boolean placeLiquid(LevelAccessor world, BlockPos pos, BlockState state, FluidState fluid) {
        if (!state.getValue(WATERLOGGED) && fluid.getType() == Fluids.WATER) {
            if (state.getValue(LIT) || state.getValue(HEAT) > 0) {
                world.playSound(null, pos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            world.setBlock(pos, state.setValue(WATERLOGGED, true).setValue(LIT, false).setValue(HEAT, 0), 10);
            world.scheduleTick(pos, fluid.getType(), fluid.getType().getTickDelay(world));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(LIT) && state.getValue(HEAT) < 4;
    }

    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
        super.tick(state, worldIn, pos, rand);
        int heat = state.getValue(HEAT);
        if(state.getValue(LIT) && heat < 4){
            if(rand.nextInt(10) == 0) {
                heat = Math.max(heat - 1, 0);
                worldIn.setBlock(pos, state.setValue(HEAT, heat).setValue(LIT, heat > 0), 2);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
        int currentHeat = stateIn.getValue(HEAT);
        if (currentHeat == 4) {
            if (rand.nextInt(10) == 0) {
                worldIn.playLocalSound((float)pos.getX() + 0.5F, (float)pos.getY() + 0.5F, (float)pos.getZ() + 0.5F, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.5F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.6F, false);
            }
            if (rand.nextInt(10) == 0) {
                for(int i = 0; i < rand.nextInt(1) + 1; ++i) {
                    worldIn.addParticle(ParticleTypes.LAVA, (float)pos.getX() + 0.5F, (float)pos.getY() + 0.5F, (float)pos.getZ() + 0.5F, rand.nextFloat() / 4.0F, 2.5E-5D, rand.nextFloat() / 4.0F);
                }
            }
            if(rand.nextInt(2) == 0) {
                worldIn.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, (double)pos.getX() + 0.5D + rand.nextDouble() / 4.0D * (double)(rand.nextBoolean() ? 1 : -1), (double)pos.getY() + 0.8D, (double)pos.getZ() + 0.5D + rand.nextDouble() / 4.0D * (double)(rand.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
            }
        }else if(currentHeat > 0){
            if(rand.nextInt((4 - currentHeat) * 2) == 0) {
                worldIn.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,  (double)pos.getX() + 0.5D + rand.nextDouble() / 4.0D * (double)(rand.nextBoolean() ? 1 : -1), (double)pos.getY() + 0.8D, (double)pos.getZ() + 0.5D + rand.nextDouble() / 4.0D * (double)(rand.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
            }
        }
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
        if(state.getValue(WATERLOGGED) || !state.getValue(LIT)) return 0;
        return (state.getValue(HEAT) == 4) ? 15 : state.getValue(HEAT) * 2;
    }
}