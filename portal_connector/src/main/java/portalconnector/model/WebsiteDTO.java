package portalconnector.model;

public class WebsiteDTO {

    private String websiteId;

    private String websiteName;

    private String websiteDescription;

    private String imageBase64;

    private String websiteLink;

    private Permission permission;

// TODO: Lombok


    public String getWebsiteId() {
        return websiteId;
    }

    public void setWebsiteId(String websiteId) {
        this.websiteId = websiteId;
    }

    public String getWebsiteName() {
        return websiteName;
    }

    public void setWebsiteName(String websiteName) {
        this.websiteName = websiteName;
    }

    public String getWebsiteDescription() {
        return websiteDescription;
    }

    public void setWebsiteDescription(String websiteDescription) {
        this.websiteDescription = websiteDescription;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public String getWebsiteLink() {
        return websiteLink;
    }

    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }

    @Override
    public String toString() {
        return "WebsiteDTO{" +
                "websiteId='" + websiteId + '\'' +
                ", websiteName='" + websiteName + '\'' +
                ", websiteDescription='" + websiteDescription + '\'' +
                ", imageBase64='" + imageBase64 + '\'' +
                ", websiteLink='" + websiteLink + '\'' +
                ", permission=" + permission +
                '}';
    }
}
