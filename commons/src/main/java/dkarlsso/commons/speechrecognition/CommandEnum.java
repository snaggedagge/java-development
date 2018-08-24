package dkarlsso.commons.speechrecognition;

import org.apache.commons.lang.StringUtils;

public enum CommandEnum {
    WEATHER,
    SELFIE,
    RADIO,
    START,
    LIGHTS,
    VOLUME,
    SLEEP;


//    SPOTIFY,
//    YOUTUBE,
//    SHUTDOWN;
//    FACEBOOK,
//    SELECT;


    public String prettyName() {
        return StringUtils.capitalize(this.name().toLowerCase());
    }
}
