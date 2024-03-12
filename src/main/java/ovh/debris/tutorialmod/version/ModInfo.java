package ovh.debris.tutorialmod.version;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import ovh.debris.tutorialmod.TutorialMod;

public class ModInfo {

    public static void GetModInfo() {
        ModContainer FABRIC_MOD = FabricLoader.getInstance().getModContainer(TutorialMod.MOD_ID)
                .orElseThrow(() -> new RuntimeException("No mod found for " + TutorialMod.MOD_ID));

        TutorialMod.MOD_NAME = FABRIC_MOD.getMetadata().getName();
        TutorialMod.MOD_VERSION = FABRIC_MOD.getMetadata().getVersion().getFriendlyString();
        TutorialMod.MOD_HOMEPAGE_URL = FABRIC_MOD.getMetadata().getContact().get("homepage")
                .orElseThrow(() -> new RuntimeException("No homepage found for " + TutorialMod.MOD_ID));
        TutorialMod.MOD_SOURCE_URL = FABRIC_MOD.getMetadata().getContact().get("sources")
                .orElseThrow(() -> new RuntimeException("No sources found for " + TutorialMod.MOD_ID));
        TutorialMod.MOD_VERSION_URL = FABRIC_MOD.getMetadata().getContact().get("version")
                .orElseThrow(() -> new RuntimeException("No version URL found for " + TutorialMod.MOD_ID));

        ModVersion.setVersionInfo();
    }
}