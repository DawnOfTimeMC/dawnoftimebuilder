package org.dawnoftime.dawnoftime.block.japanese;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import org.dawnoftime.dawnoftime.block.templates.PillarPaneBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FlatPaperWallBlock extends PillarPaneBlock {
    public FlatPaperWallBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable BlockGetter worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.translatable("tooltip.dawnoftimebuilder.connected_texture_label"));
        tooltip.add(Component.translatable("tooltip.dawnoftimebuilder.connected_texture"));
    }
}
