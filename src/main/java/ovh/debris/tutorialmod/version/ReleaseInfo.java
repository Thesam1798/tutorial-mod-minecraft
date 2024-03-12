package ovh.debris.tutorialmod.version;

import com.google.gson.annotations.SerializedName;

public class ReleaseInfo {
    @SerializedName("tag_name")
    public String tagName;

    @SerializedName("name")
    public String name;
    
    @SerializedName("prerelease")
    public Boolean prerelease;

    @SerializedName("published_at")
    public String publishedAt;
}