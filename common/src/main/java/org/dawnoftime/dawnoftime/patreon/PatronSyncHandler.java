package org.dawnoftime.dawnoftime.patreon;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.dawnoftime.dawnoftime.DoTBCommon;
import org.dawnoftime.dawnoftime.config.PatronConfig;
import org.dawnoftime.dawnoftime.platform.Services;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

public class PatronSyncHandler {

    public static final ResourceLocation PATRON_TIER_CHANNEL = new ResourceLocation(DoTBCommon.MOD_ID, "patron_tier");

    // 30-minute cooldown between remote fetches
    private static final long COOLDOWN_MS = 30 * 60 * 1000L;
    private static final AtomicLong lastFetchTime = new AtomicLong(0L);

    public static void onPlayerLogin(Path cacheFile, ServerPlayer player) {
        CompletableFuture.runAsync(() -> {
            try {
                long now = System.currentTimeMillis();
                long last = lastFetchTime.get();
                if (now - last >= COOLDOWN_MS && lastFetchTime.compareAndSet(last, now)) {
                    PatronFetcher.fetchAndCache(cacheFile);
                }
                PatronConfig.load(cacheFile);
            } catch (Exception e) {
                PatronConfig.load(cacheFile);
            }

            PatronConfig config = PatronConfig.load();
            int tier = resolvePlayerTier(config, player.getStringUUID());
            player.getServer().execute(() -> Services.PLATFORM.sendPatronTierToPlayer(player, tier));
        });
    }

    private static int resolvePlayerTier(PatronConfig config, String uuid) {
        if (config.tier6.contains(uuid)) return 6;
        if (config.tier5.contains(uuid)) return 5;
        if (config.tier4.contains(uuid)) return 4;
        if (config.tier3.contains(uuid)) return 3;
        if (config.tier2.contains(uuid)) return 2;
        if (config.tier1.contains(uuid)) return 1;
        return 0;
    }
}
