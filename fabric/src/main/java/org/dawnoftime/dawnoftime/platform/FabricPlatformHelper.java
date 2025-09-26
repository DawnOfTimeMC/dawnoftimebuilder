package org.dawnoftime.dawnoftime.platform;

import net.fabricmc.loader.api.FabricLoader;
import org.dawnoftime.dawnoftime.platform.services.IPlatformHelper;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }
}
