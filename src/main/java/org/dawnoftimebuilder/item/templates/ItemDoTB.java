package org.dawnoftimebuilder.item.templates;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import org.dawnoftimebuilder.util.DoTBUtils;

import javax.annotation.Nullable;
import java.util.List;

import static org.dawnoftimebuilder.DawnOfTimeBuilder.DOTB_TAB;

public class ItemDoTB extends Item {

    private final boolean hasTooltip;

    public ItemDoTB() {
        this(false);
    }

    public ItemDoTB(boolean hasTooltip){
        this(new Properties(), hasTooltip);
    }

    public ItemDoTB(Properties properties){
        this(properties.tab(DOTB_TAB), false);
    }

    public ItemDoTB(Properties properties, boolean hasTooltip){
        super(properties.tab(DOTB_TAB));
        this.hasTooltip = hasTooltip;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltips, ITooltipFlag flagIn) {
        super.appendHoverText(stack, world, tooltips, flagIn);
        if(this.hasTooltip){
            DoTBUtils.addTooltip(tooltips, this);
        }
    }
}
