package ovh.debris.tutorialmod;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ovh.debris.tutorialmod.version.ModInfo;

public class TutorialMod implements ModInitializer {
    public static final String MOD_ID = "tutorialmod";
    public static final Logger LOGGER = LoggerFactory.getLogger("Tutorial Mod");

    public static String MOD_NAME;
    public static String MOD_VERSION;
    public static String MOD_HOMEPAGE_URL;
    public static String MOD_SOURCE_URL;
    public static String MOD_VERSION_URL;
    public static String MOD_LAST_VERSION;
    public static boolean MOD_IS_UPDATE_AVAILABLE = false;
    public static String MOD_UPDATE_VALUE = "pre-release";

    @Override
    public void onInitialize() {
        ModInfo.GetModInfo();

        LOGGER.info("------------------------------------------------");
        LOGGER.info("Initializing {}...", MOD_NAME);
        LOGGER.info("Homepage Url: {}", MOD_HOMEPAGE_URL);
        LOGGER.info("Source Url: {}", MOD_SOURCE_URL);
        LOGGER.info("Current version: {}", MOD_VERSION);

        if (MOD_IS_UPDATE_AVAILABLE) {
            LOGGER.warn("An update is available {} (last version: {})", MOD_VERSION, MOD_LAST_VERSION);
            LOGGER.warn("The superior version is a : {}", MOD_UPDATE_VALUE);
        } else {
            LOGGER.info("No update available for {}", MOD_NAME);
        }

        LOGGER.info("Loaded successfully !");

        LOGGER.info("------------------------------------------------");
    }
}