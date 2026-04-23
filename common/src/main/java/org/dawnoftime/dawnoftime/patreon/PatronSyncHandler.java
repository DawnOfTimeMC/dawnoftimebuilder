package org.dawnoftime.dawnoftime.patreon;

import org.dawnoftime.dawnoftime.config.PatronConfig;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class PatronSyncHandler {

    /**
     * Triggered on player login (server-side only).
     * Fetches the remote patron list and refreshes the in-memory cache asynchronously.
     * Never blocks the main thread. Never propagates exceptions.
     */
    public static void onPlayerLogin(Path cacheFile) {
        CompletableFuture.runAsync(() -> {
            try {
                PatronFetcher.fetchAndCache(cacheFile);
                PatronConfig.load(cacheFile);
            } catch (Exception e) {
                PatronConfig.load(cacheFile);
            }
        });
    }
}
