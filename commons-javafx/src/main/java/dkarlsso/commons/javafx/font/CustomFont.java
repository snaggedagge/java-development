package dkarlsso.commons.javafx.font;

public enum CustomFont {
    CASSANDRA("ttf"),
    VEGAN_STYLE("ttf"),
    LEMON_JELLY("ttf"),
    BULGATTI("ttf"),
    MISDEMEANOR("ttf"),
    URBAN_JUNGLE("otf"),
    OPENSANS_REGULAR("ttf");

    private final String fontType;

    CustomFont(final String fontType) {
        this.fontType = fontType;
    }

    public String getFontType() {
        return fontType;
    }
}
