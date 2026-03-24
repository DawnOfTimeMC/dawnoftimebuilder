package org.dawnoftime.dawnoftime.item.templates;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class SakeBottleItem extends BlockItem {

    public SakeBottleItem(Block block, Properties properties) {
        super(block, properties);
    }

    // Duration of the drinking animation: 32 ticks = ~1.6 seconds (standard for drinks)
    @Override
    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    // Show the drink animation when holding right-click in air
    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    // Right-click on a block face → useOn() handles placement (BlockItem default, untouched)
    // Right-click in air → this method is called, we start the drinking animation
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    // Called when the player finishes holding right-click for the full drink duration
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof Player player && !level.isClientSide()) {
            applyDrinkingEffects(player);
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }
        return stack.isEmpty() ? ItemStack.EMPTY : stack;
    }

    /**
     * Cumulative effects based on how drunk the player already is.
     * We detect the "drinking level" by checking which effects are currently active.
     *
     * Level 1 (no effects yet)  → Speed I – 5s
     * Level 2 (Speed active)    → Speed I – 45s + Nausea I – 5s
     * Level 3 (Speed + Nausea)  → Poison I – 5s + Nausea I – 8s + Speed I – 60s
     * Level 4 (Speed + Poison)  → Poison I – 25s + Nausea I – 8s + Speed I – 60s + Strength I – 30s
     */
    private void applyDrinkingEffects(Player player) {
        boolean hasSpeed  = player.hasEffect(MobEffects.MOVEMENT_SPEED);
        boolean hasNausea = player.hasEffect(MobEffects.CONFUSION);
        boolean hasPoison = player.hasEffect(MobEffects.POISON);

        if (!hasSpeed) {
            // First bottle
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 0));

        } else if (!hasNausea) {
            // Second bottle: Speed still active, no nausea yet
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 900, 0));
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION,      100, 0));

        } else if (!hasPoison) {
            // Third bottle: Speed + Nausea active, no poison yet
            player.addEffect(new MobEffectInstance(MobEffects.POISON,          100, 0));
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION,       160, 0));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 0));

        } else {
            // Fourth bottle and beyond: fully drunk
            player.addEffect(new MobEffectInstance(MobEffects.POISON,          500, 0));
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION,       160, 0));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 0));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,    600, 0));
        }
    }
}
