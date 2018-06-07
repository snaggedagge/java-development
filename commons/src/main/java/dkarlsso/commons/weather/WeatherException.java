package dkarlsso.commons.weather;

public class WeatherException extends Exception {

    public WeatherException() {
        super();
    }

    public WeatherException(String msg) {
        super(msg);
    }

    public WeatherException(String msg, Exception e) {
        super(msg,e);
    }
}
