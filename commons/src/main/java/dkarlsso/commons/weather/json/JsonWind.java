package dkarlsso.commons.weather.json;

public class JsonWind {

    private double speed;

    private int deg;

    public JsonWind(double speed, int deg) {
        this.speed = speed;
        this.deg = deg;
    }

    public JsonWind() {
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }
}
