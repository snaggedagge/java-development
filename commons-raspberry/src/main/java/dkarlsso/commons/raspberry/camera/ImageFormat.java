package dkarlsso.commons.raspberry.camera;

public enum ImageFormat {
    FORMAT_JPG("jpg"),
    FORMAT_PNG("png");

    private final String format;

    private ImageFormat(final String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

}
