package org.dawnoftime.dawnoftime.patreon;

import org.dawnoftime.dawnoftime.DoTBCommon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class PatronFetcher {

    private static final String REMOTE_URL =
            "https://raw.githubusercontent.com/DawnOfTimeMC/patreon_list/main/patrons.json";
    private static final int TIMEOUT_MS = 3000;

    /**
     * Fetches the patron list from GitHub and writes the raw JSON to cacheFile.
     * Returns null silently on any network/HTTP failure — the caller falls back to the existing cache.
     */
    public static String fetchAndCache(Path cacheFile) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(REMOTE_URL).openConnection();
            conn.setConnectTimeout(TIMEOUT_MS);
            conn.setReadTimeout(TIMEOUT_MS);
            conn.setRequestMethod("GET");

            int status = conn.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK) {
                DoTBCommon.LOG.warn("[DawnOfTime] Patron fetch failed: HTTP {}", status);
                return null;
            }

            String json;
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                json = reader.lines().collect(Collectors.joining("\n"));
            }

            Files.createDirectories(cacheFile.getParent());
            Files.writeString(cacheFile, json, StandardCharsets.UTF_8);
            return json;

        } catch (IOException e) {
            DoTBCommon.LOG.warn("[DawnOfTime] Patron fetch error: {}", e.getMessage());
            return null;
        }
    }
}
