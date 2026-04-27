package org.dawnoftime.dawnoftime.block.japanese;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.dawnoftime.dawnoftime.block.templates.PillarPaneBlock;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FlatPaperWallBlock extends PillarPaneBlock {
    public FlatPaperWallBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Item.TooltipContext context,
            @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.dawnoftimebuilder.connected_texture_label"));
        tooltip.add(Component.translatable("tooltip.dawnoftimebuilder.connected_texture"));
    }
}
