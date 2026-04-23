package org.dawnoftime.dawnoftime.config;

import com.google.gson.Gson;
import org.dawnoftime.dawnoftime.DoTBCommon;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Holds the patron list, sourced from GitHub via PatronFetcher and cached locally.
 * If no cache exists yet, returns an empty config (no rewards granted by default).
 *
 * JSON format (remote + local cache):
 *   {
 *     "tier1": [{"uuid": "...", "name": "PlayerName"}, ...],
 *     "tier2": [...],
 *     "tier3": [...]
 *   }
 *
 * Tier lists (UUID strings) are consumed directly by PatronRewardCommand.
 */
public class PatronConfig {

    public List<String> tier1 = new ArrayList<>();
    public List<String> tier2 = new ArrayList<>();
    public List<String> tier3 = new ArrayList<>();

    private static final Gson GSON = new Gson();

    // Written by the async fetch thread, read by the command thread
    private static volatile PatronConfig CACHED = null;

    // Internal DTO for JSON deserialization — never exposed outside this class
    private static class PatronData {
        List<PatronEntry> tier1 = new ArrayList<>();
        List<PatronEntry> tier2 = new ArrayList<>();
        List<PatronEntry> tier3 = new ArrayList<>();

        static class PatronEntry {
            String uuid;
            String name;
        }
    }

    /**
     * Called from PatronSyncHandler (async thread) after a remote fetch attempt.
     * Reads cacheFile from disk, parses it, and updates the in-memory cache.
     */
    public static void load(Path cacheFile) {
        if (!Files.exists(cacheFile)) {
            CACHED = new PatronConfig();
            return;
        }

        try {
            String json = Files.readString(cacheFile, StandardCharsets.UTF_8);
            PatronData data = GSON.fromJson(json, PatronData.class);
            if (data == null) {
                CACHED = new PatronConfig();
                return;
            }
            CACHED = fromData(data);
        } catch (Exception e) {
            DoTBCommon.LOG.warn("[DawnOfTime] Failed to read patron cache: {}", e.getMessage());
            CACHED = new PatronConfig();
        }
    }

    /**
     * Called by PatronRewardCommand on every command execution.
     * Returns the latest cached config. If no async fetch has run yet, returns empty (no rewards granted).
     */
    public static PatronConfig load() {
        if (CACHED != null) return CACHED;
        DoTBCommon.LOG.warn("[DawnOfTime] Patron cache not ready — no fetch completed yet");
        return new PatronConfig();
    }

    private static PatronConfig fromData(PatronData data) {
        PatronConfig config = new PatronConfig();
        if (data.tier1 != null)
            for (PatronData.PatronEntry e : data.tier1)
                if (e != null && e.uuid != null) config.tier1.add(e.uuid);
        if (data.tier2 != null)
            for (PatronData.PatronEntry e : data.tier2)
                if (e != null && e.uuid != null) config.tier2.add(e.uuid);
        if (data.tier3 != null)
            for (PatronData.PatronEntry e : data.tier3)
                if (e != null && e.uuid != null) config.tier3.add(e.uuid);
        return config;
    }
}
