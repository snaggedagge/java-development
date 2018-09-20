package dkarlsso.smartmirror.javafx.model;

import org.apache.commons.lang.StringUtils;

public enum CommandEnum {
    WEATHER,
    SELFIE,
    RADIO,
    START,
    LIGHTS,
    VOLUME,
    SLEEP,


    // Not used
    SPOTIFY,
    YOUTUBE,
    SHUTDOWN,
    FACEBOOK,
    SELECT;

    public String prettyName() {
        return StringUtils.capitalize(this.name().toLowerCase());
    }
}
