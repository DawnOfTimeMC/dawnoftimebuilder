package org.dawnoftime.dawnoftime.item.templates;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.dawnoftime.dawnoftime.DoTBCommon;
import org.dawnoftime.dawnoftime.util.Utils;

import java.util.function.Consumer;

public class ItemDoTB extends Item {
    private final boolean hasTooltip;

    public ItemDoTB(boolean hasTooltip, String id) {
        this(new Properties().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(DoTBCommon.MOD_ID, id))), hasTooltip);
    }

    public ItemDoTB(Properties properties, boolean hasTooltip) {
        super(properties);
        this.hasTooltip = hasTooltip;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        if (this.hasTooltip) {
            Utils.addTooltip(tooltipAdder, this);
        }
    }
}
