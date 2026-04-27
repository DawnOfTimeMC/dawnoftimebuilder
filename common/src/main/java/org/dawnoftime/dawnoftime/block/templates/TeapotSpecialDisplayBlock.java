package org.dawnoftime.dawnoftime.block.templates;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.dawnoftime.dawnoftime.block.general.FireplaceBlock;
import org.dawnoftime.dawnoftime.platform.Services;
import org.dawnoftime.dawnoftime.registry.DoTBBlocksRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TeapotSpecialDisplayBlock extends SpecialDisplayBlock {

    public TeapotSpecialDisplayBlock(Properties properties, VoxelShape[] shapes) {
        super(properties, shapes);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource rand) {
        if (!level.isClientSide) return;

        BlockState state1 = level.getBlockState(pos.below());
        if (state1.getBlock() != DoTBBlocksRegistry.INSTANCE.IRORI_FIREPLACE.get()) return;

        if (state1.getValue(FireplaceBlock.LIT) && rand.nextInt(5) == 0) {
            SimpleParticleType particle;
            if (Services.PLATFORM.isModLoaded("farmersdelight"))
                particle = (SimpleParticleType) BuiltInRegistries.PARTICLE_TYPE.get(ResourceLocation.tryParse("farmersdelight:steam"));
            else
                particle = ParticleTypes.CAMPFIRE_COSY_SMOKE;

            if (particle == null) return;
            level.addParticle(particle, pos.getX() + 0.5, pos.getY() + 0.3D, pos.getZ() + 0.5, 0.0D, 0.01D, 0.0D);
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Item.TooltipContext context,
            @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.dawnoftimebuilder.teapot"));
    }
}
