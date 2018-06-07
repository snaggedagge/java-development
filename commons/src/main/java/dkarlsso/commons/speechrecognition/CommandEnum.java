package dkarlsso.commons.speechrecognition;

import org.apache.commons.lang.StringUtils;

public enum CommandEnum {
    WEATHER,
    SELFIE,
    RADIO,
//    SPOTIFY,
    YOUTUBE,
    ACTIVATE,
    SLEEP,
    SHUTDOWN;
//    FACEBOOK,
//    SELECT;


    public String prettyName() {
        return StringUtils.capitalize(this.name().toLowerCase());
    }
}
