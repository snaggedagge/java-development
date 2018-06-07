package dkarlsso.commons.weather.json;

public class JsonCoordinates {

    private double lat;

    private double lon;

    public JsonCoordinates() {
    }

    public JsonCoordinates(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
