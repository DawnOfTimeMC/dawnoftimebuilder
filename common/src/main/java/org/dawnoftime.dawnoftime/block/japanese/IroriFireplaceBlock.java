package org.dawnoftime.dawnoftime.block.japanese;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;


import org.dawnoftime.dawnoftime.block.templates.CandleLampBlock;

import static org.dawnoftime.dawnoftime.util.VoxelShapes.FULL_SHAPE;

public class IroriFireplaceBlock extends CandleLampBlock {
    public IroriFireplaceBlock(Properties properties) {
        super(properties, FULL_SHAPE);
    }

    
    @Override
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
        if(stateIn.getValue(LIT)) {
            if(rand.nextInt(10) == 0) {
                worldIn.playLocalSound((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.5F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.6F, false);
            }
            if(rand.nextInt(10) == 0) {
                for(int i = 0; i < rand.nextInt(1) + 1; ++i) {
                    worldIn.addParticle(ParticleTypes.LAVA, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, rand.nextFloat() / 4.0F, 2.5E-5D, rand.nextFloat() / 4.0F);
                }
            }
            worldIn.addParticle(ParticleTypes.SMOKE, (double) pos.getX() + 0.5D + rand.nextDouble() / 3.0D * (double) (rand.nextBoolean() ? 1 : -1), (double) pos.getY() + 0.8D, (double) pos.getZ() + 0.5D + rand.nextDouble() / 3.0D * (double) (rand.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
        }
    }

    @Override
    public float getDisplayScale() {
        return 0.2F;
    }

    @Override
    public boolean emitsLight() {
        return false;
    }
}
