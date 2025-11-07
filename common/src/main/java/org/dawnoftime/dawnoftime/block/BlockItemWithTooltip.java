package org.dawnoftime.dawnoftime.block;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;
import org.dawnoftime.dawnoftime.util.Utils;

import java.util.function.Consumer;

public class BlockItemWithTooltip extends BlockItem {

    private final boolean tooltipFromBlock;
    private final String[] tooltipNames;

    public BlockItemWithTooltip(Block block, Properties properties, boolean tooltipFromBlock, final String... tooltipNames) {
        super(block, properties);
        this.tooltipFromBlock = tooltipFromBlock;
        this.tooltipNames = tooltipNames;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        if (!this.tooltipFromBlock && this.tooltipNames == null) return;

        if (this.tooltipFromBlock && this.tooltipNames != null)
            Utils.addTooltip(tooltipAdder, this.getBlock(), this.tooltipNames);
        else if (this.tooltipFromBlock)
            Utils.addTooltip(tooltipAdder, this.getBlock());
        else
            Utils.addTooltip(tooltipAdder, this.tooltipNames);
    }
}
