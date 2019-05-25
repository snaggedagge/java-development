package dkarlsso.smartmirror.javafx.model;

public enum CommandEnum {
    WEATHER("Weather"),
    SELFIE("Selfie"),
    RADIO("Radio"),
    LIGHTS("Lights"),
    ALEXA_LISTENING("Alexa is listening"),
    ALEXA_STOPPED_LISTENING("Alexa stopped listening");


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
