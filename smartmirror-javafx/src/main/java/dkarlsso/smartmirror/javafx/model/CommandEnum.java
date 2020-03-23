package dkarlsso.smartmirror.javafx.model;

public enum CommandEnum {
    WEATHER("Weather"),
    SELFIE("Selfie"),
    RADIO("Radio"),
    LIGHTS("Lights"),
    ALEXA_LISTENING("Mirror is listening"),
    ALEXA_STOPPED_LISTENING("Mirror stopped listening"),
    VOLUME("Volume change");

//    VOLUME,
//    COMMAND,
//    SLEEP,
//
//
//    // Not used
//    SPOTIFY,
//    YOUTUBE,
//    SHUTDOWN,
//    FACEBOOK,
//    SELECT;

    private String prettyName;

    private CommandEnum(final String prettyName) {
        this.prettyName = prettyName;
    }

    public String prettyName() {
        return prettyName;
    }
}
