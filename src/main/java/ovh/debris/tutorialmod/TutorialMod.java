package ovh.debris.tutorialmod;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ovh.debris.tutorialmod.version.models.VersionModel;
import ovh.debris.tutorialmod.version.services.VersionService;

public class TutorialMod implements ModInitializer {
    public static final String MOD_ID = "tutorialmod";
    public static final Logger LOGGER = LoggerFactory.getLogger("Tutorial Mod");

    public static String MOD_NAME = "Tutorial Mod";
    public static String MOD_VERSION = "0.0.0-UNKNOWN";
    public static String MOD_HOMEPAGE_URL;
    public static String MOD_SOURCE_URL;
    public static String MOD_VERSION_URL;
    public static String MOD_LAST_VERSION;
    public static boolean MOD_IS_UPDATE_AVAILABLE = false;
    public static String MOD_UPDATE_VALUE = "pre-release";

    @Override
    public void onInitialize() {
        long startTime = System.currentTimeMillis();

        VersionService.UpdateGlobalModInfo();

        LOGGER.info("Checking for updates...");

        VersionModel versionInfo = VersionService.getVersionInfo();

        TutorialMod.MOD_LAST_VERSION = versionInfo.getVersionNumber();
        TutorialMod.MOD_IS_UPDATE_AVAILABLE = versionInfo.isUpdateAvailable();
        TutorialMod.MOD_UPDATE_VALUE = versionInfo.getVersionType();

        if (MOD_IS_UPDATE_AVAILABLE && !MOD_VERSION.equals("0.0.0-DEV")) {
            LOGGER.warn("An update is available {} (last version: {})", MOD_VERSION, MOD_LAST_VERSION);
            LOGGER.warn("The superior version is a : {}", MOD_UPDATE_VALUE);
        } else if (MOD_VERSION.equals("0.0.0-DEV")) {
            LOGGER.warn("You are using a development version, please be careful");
        } else {
            LOGGER.info("You are using the latest version of the mod");
        }

        LOGGER.info("Loaded successfully on {}ms", System.currentTimeMillis() - startTime);
    }
}