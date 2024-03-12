package ovh.debris.tutorialmod;

import net.fabricmc.api.ClientModInitializer;

public class TutorialModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        TutorialMod.LOGGER.info("Hello Fabric world! (Client)");
    }
}