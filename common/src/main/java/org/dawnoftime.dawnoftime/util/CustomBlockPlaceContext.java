package org.dawnoftime.dawnoftime.util;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class CustomBlockPlaceContext extends BlockPlaceContext {
    public CustomBlockPlaceContext(Player $$0, InteractionHand $$1, ItemStack $$2, BlockHitResult $$3) {
        super($$0, $$1, $$2, $$3);
    }

    public CustomBlockPlaceContext(UseOnContext $$0) {
        super($$0);
    }

    public CustomBlockPlaceContext(Level $$0, @Nullable Player $$1, InteractionHand $$2, ItemStack $$3, BlockHitResult $$4) {
        super($$0, $$1, $$2, $$3, $$4);
    }
}
