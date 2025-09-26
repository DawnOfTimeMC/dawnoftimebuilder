package org.dawnoftime.dawnoftime.platform;

import net.neoforged.fml.ModList;
import org.dawnoftime.dawnoftime.platform.services.IPlatformHelper;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }
}