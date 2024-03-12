package ovh.debris.tutorialmod.version;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.vdurmont.semver4j.Semver;
import ovh.debris.tutorialmod.TutorialMod;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class ModVersion {

    private static String getUrlContent(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder responseContent = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
                return responseContent.toString();
            } else {
                TutorialMod.LOGGER.error("Invalid return code (200): {}", connection.getResponseCode());
            }
        } catch (Exception e) {
            TutorialMod.LOGGER.error("Error connecting to API : {}", e.getMessage());
        }
        return null;
    }

    private static List<ReleaseInfo> parseReleaseInfo(String jsonString) {
        return new Gson().fromJson(jsonString, new TypeToken<List<ReleaseInfo>>() {}.getType());
    }

    private static ReleaseInfo getLatestVersion() {
        String json = getUrlContent(TutorialMod.MOD_VERSION_URL);
        List<ReleaseInfo> releaseInfo = parseReleaseInfo(json);

        if (releaseInfo != null) {
            return releaseInfo.stream()
                    .filter(release -> release != null && (release.prerelease == null || !release.prerelease))
                    .max(Comparator.comparing(release -> ZonedDateTime.parse(release.publishedAt, DateTimeFormatter.ISO_DATE_TIME)))
                    .orElse(null);
        }
        return null;
    }

    public static void setVersionInfo() {
        ReleaseInfo release = getLatestVersion();

        if (release == null) {
            TutorialMod.MOD_IS_UPDATE_AVAILABLE = false;
            return;
        }

        TutorialMod.MOD_LAST_VERSION = release.tagName;

        Semver currentVersion = new Semver(TutorialMod.MOD_VERSION);
        Semver latestVersion = new Semver(release.tagName);

        if (latestVersion.isGreaterThan(currentVersion)) {
            TutorialMod.MOD_IS_UPDATE_AVAILABLE = true;
            TutorialMod.MOD_UPDATE_VALUE = getUpdateValue(currentVersion, latestVersion);
        } else {
            TutorialMod.MOD_IS_UPDATE_AVAILABLE = false;
        }
    }

    private static String getUpdateValue(Semver currentVersion, Semver latestVersion) {
        if (latestVersion.getMajor() > currentVersion.getMajor()) {
            return "major";
        } else if (latestVersion.getMinor() > currentVersion.getMinor()) {
            return "minor";
        } else if (latestVersion.getPatch() > currentVersion.getPatch()) {
            return "patch";
        } else if (currentVersion.getSuffixTokens().length != 0 && latestVersion.getSuffixTokens().length == 0) {
            return "release";
        } else if (latestVersion.getSuffixTokens().length != 0) {
            return latestVersion.getSuffixTokens()[0];
        } else {
            return "unknown";
        }
    }
}