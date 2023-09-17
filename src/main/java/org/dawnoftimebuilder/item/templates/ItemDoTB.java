package org.dawnoftimebuilder.item.templates;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.dawnoftimebuilder.util.DoTBUtils;

import javax.annotation.Nullable;
import java.util.List;

public class ItemDoTB extends Item {

    private final boolean hasTooltip;

    public ItemDoTB() {
        this(false);
    }

    public ItemDoTB(boolean hasTooltip){
        this(new Properties(), hasTooltip);
    }

    public ItemDoTB(Properties properties){
        this(properties, false);
    }

    public ItemDoTB(Properties properties, boolean hasTooltip){
        super(properties);
        this.hasTooltip = hasTooltip;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltips, TooltipFlag flagIn) {
        super.appendHoverText(stack, world, tooltips, flagIn);
        if(this.hasTooltip){
            DoTBUtils.addTooltip(tooltips, this);
        }
    }
}
