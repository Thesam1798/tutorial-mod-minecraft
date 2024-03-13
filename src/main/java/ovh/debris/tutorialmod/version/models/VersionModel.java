package ovh.debris.tutorialmod.version.models;

public class VersionModel {
    private final String versionType;
    private final String versionNumber;
    private final boolean updateAvailable;

    public VersionModel(String versionType, String versionNumber, boolean updateAvailable) {
        this.versionType = versionType;
        this.versionNumber = versionNumber;
        this.updateAvailable = updateAvailable;
    }

    public String getVersionType() {
        return versionType;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public boolean isUpdateAvailable() {
        return updateAvailable;
    }
}