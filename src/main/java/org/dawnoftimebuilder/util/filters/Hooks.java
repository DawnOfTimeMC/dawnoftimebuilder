package org.dawnoftimebuilder.util.filters;

import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import org.dawnoftimebuilder.DawnOfTimeBuilder;
import org.dawnoftimebuilder.HandlerClient;

public class Hooks {

    private static int animation = 48;

    public static int getPotionEffectOffset(@SuppressWarnings("rawtypes") DisplayEffectsScreen screen)
    {
        if(screen instanceof CreativeScreen && !DawnOfTimeBuilder.get().events.noFilters)
        {
            animation = 48;
            return 172;
        }
        if (animation == 0) return 150;
        else {
            animation -= 3;
            return 150 + animation;
        }
    }

    public static int getEffectsGuiOffset(@SuppressWarnings("rawtypes") DisplayEffectsScreen screen)
    {
        if(screen instanceof CreativeScreen)
        {
            return 185;
        }
        return 160;
    }

}
