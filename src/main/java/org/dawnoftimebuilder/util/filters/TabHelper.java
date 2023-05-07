package org.dawnoftimebuilder.util.filters;

import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.client.event.GuiScreenEvent;
import org.dawnoftimebuilder.DawnOfTimeBuilder;
import org.dawnoftimebuilder.HandlerClient;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class TabHelper {

    public static boolean getHasFilters(GuiScreenEvent event) {
        return DawnOfTimeBuilder.get().hasFilters(getGroup(event));
    }

    @Nonnull
    public static ItemGroup getGroup(GuiScreenEvent event) {
        return requireItemGroup(((CreativeScreen) event.getGui()).getSelectedTab());
    }

    @Nullable
    public static ItemGroup getGroup(int index)
    {
        if (index < 0 || index >= ItemGroup.TABS.length)
            return null;
        return ItemGroup.TABS[index];
    }

    @Nonnull
    public static ItemGroup requireItemGroup(int index) {
        return Objects.requireNonNull(getGroup(index));
    }

    public static boolean getHasFilters(ItemGroup tab) {
        return DawnOfTimeBuilder.get().hasFilters(tab);
    }

}
