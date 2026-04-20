package org.dawnoftime.dawnoftime.config;

import com.google.gson.Gson;
import org.dawnoftime.dawnoftime.DoTBCommon;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads the patron list bundled inside the mod JAR.
 * File location in the JAR: data/dawnoftimebuilder/patrons.json
 *
 * This file is internal — players cannot edit it without recompiling the mod.
 * To add or remove a patron, edit the file and release a new version.
 *
 * Structure: each patron is listed only in their highest tier.
 * The cascade (tier 3 → access to tiers 1, 2, 3) is handled in PatronRewardCommand.
 */
public class PatronConfig {
    public List<String> tier1 = new ArrayList<>();
    public List<String> tier2 = new ArrayList<>();
    public List<String> tier3 = new ArrayList<>();

    private static final Gson GSON = new Gson();
    private static final String RESOURCE_PATH = "/data/dawnoftimebuilder/patrons.json";

    /**
     * Loads the patron list from inside the JAR.
     * Returns an empty config if the resource is missing (should never happen in production).
     * Returns null if the JSON is malformed — callers must handle this case.
     */
    public static PatronConfig load() {
        InputStream stream = PatronConfig.class.getResourceAsStream(RESOURCE_PATH);

        if (stream == null) {
            DoTBCommon.LOG.error("[DawnOfTime] patrons.json not found in JAR at {}", RESOURCE_PATH);
            return new PatronConfig();
        }

        try (InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            PatronConfig config = GSON.fromJson(reader, PatronConfig.class);
            if (config == null) return new PatronConfig();
            // Guard against missing keys in the JSON
            if (config.tier1 == null) config.tier1 = new ArrayList<>();
            if (config.tier2 == null) config.tier2 = new ArrayList<>();
            if (config.tier3 == null) config.tier3 = new ArrayList<>();
            return config;
        } catch (Exception e) {
            DoTBCommon.LOG.error("[DawnOfTime] Failed to parse patrons.json: {}", e.getMessage());
            return null;
        }
    }
}
