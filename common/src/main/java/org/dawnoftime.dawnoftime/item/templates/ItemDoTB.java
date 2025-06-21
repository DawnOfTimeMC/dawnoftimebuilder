package org.dawnoftime.dawnoftime.item.templates;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.dawnoftime.dawnoftime.util.Utils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class ItemDoTB extends Item {
    private final boolean hasTooltip;

    public ItemDoTB() {
        this(false);
    }

    public ItemDoTB(boolean hasTooltip) {
        this(new Properties(), hasTooltip);
    }

    public ItemDoTB(Properties properties) {
        this(properties, false);
    }

    public ItemDoTB(Properties properties, boolean hasTooltip) {
        super(properties);
        this.hasTooltip = hasTooltip;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltips, @NotNull TooltipFlag flagIn) {
        super.appendHoverText(stack, world, tooltips, flagIn);
        if (this.hasTooltip) {
            Utils.addTooltip(tooltips, this);
        }
    }
}
