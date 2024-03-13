package ovh.debris.tutorialmod.version.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vdurmont.semver4j.Semver;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import ovh.debris.tutorialmod.TutorialMod;
import ovh.debris.tutorialmod.version.models.ReleaseModel;
import ovh.debris.tutorialmod.version.models.VersionModel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class VersionService {

    private static List<ReleaseModel> getLatestVersion() {
        try {
            URL url = new URL(TutorialMod.MOD_VERSION_URL);
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
                return new Gson().fromJson(responseContent.toString(), new TypeToken<List<ReleaseModel>>() {
                }.getType());
            } else {
                TutorialMod.LOGGER.error("Invalid return code (200): {}", connection.getResponseCode());
            }
        } catch (Exception e) {
            TutorialMod.LOGGER.error("Error connecting to API : {}", e.getMessage());
        }
        return null;
    }

    public static VersionModel getVersionInfo() {
        List<ReleaseModel> releaseInfo = getLatestVersion();
        String versionType = "unknown";
        String versionNumber = "unknown";

        if (releaseInfo != null) {
            ReleaseModel lastRelease = releaseInfo.stream().max(Comparator.comparing(release -> ZonedDateTime.parse(release.publishedAt, DateTimeFormatter.ISO_DATE_TIME))).orElse(null);

            if (lastRelease != null) {
                Semver currentVersion = new Semver(TutorialMod.MOD_VERSION);
                Semver latestVersion = new Semver(lastRelease.tagName);

                if (latestVersion.isGreaterThan(currentVersion)) {
                    if (latestVersion.getMajor() > currentVersion.getMajor()) {
                        versionType = "major";
                    } else if (latestVersion.getMinor() > currentVersion.getMinor()) {
                        versionType = "minor";
                    } else if (latestVersion.getPatch() > currentVersion.getPatch()) {
                        versionType = "patch";
                    } else if (currentVersion.getSuffixTokens().length != 0 && latestVersion.getSuffixTokens().length == 0) {
                        versionType = "release";
                    } else if (latestVersion.getSuffixTokens().length != 0) {
                        versionType = latestVersion.getSuffixTokens()[0];
                    }
                }
                versionNumber = latestVersion.getValue();
            }
        }

        return new VersionModel(versionType, versionNumber, versionNumber.compareTo(TutorialMod.MOD_VERSION) > 0);
    }

    public static void UpdateGlobalModInfo() {
        ModContainer modContainer = FabricLoader.getInstance().getModContainer(TutorialMod.MOD_ID).orElseThrow(() -> new RuntimeException("No mod found for " + TutorialMod.MOD_ID));

        TutorialMod.MOD_NAME = modContainer.getMetadata().getName();
        TutorialMod.MOD_VERSION = modContainer.getMetadata().getVersion().getFriendlyString();
        TutorialMod.MOD_HOMEPAGE_URL = modContainer.getMetadata().getContact().get("homepage").orElseThrow(() -> new RuntimeException("No homepage found for " + TutorialMod.MOD_ID));
        TutorialMod.MOD_SOURCE_URL = modContainer.getMetadata().getContact().get("sources").orElseThrow(() -> new RuntimeException("No sources found for " + TutorialMod.MOD_ID));
        TutorialMod.MOD_VERSION_URL = modContainer.getMetadata().getContact().get("version").orElseThrow(() -> new RuntimeException("No version found for " + TutorialMod.MOD_ID));
    }
}