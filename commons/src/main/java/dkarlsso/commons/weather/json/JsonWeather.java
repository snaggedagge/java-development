package dkarlsso.commons.weather.json;

import java.util.List;

public class JsonWeather {

    private long dt;

    private JsonWeatherMain main;

    private List<JsonWeatherWeather> weather;

    private JsonClouds clouds;

    private JsonWind wind;

    private JsonRain rain;

    private JsonSnow snow;

    private JsonSys sys;

    private String dt_txt;




    public JsonWeather() {
    }

    public JsonWeather(long dt, JsonWeatherMain main, List<JsonWeatherWeather> weather, JsonClouds clouds, JsonWind wind, JsonRain rain, JsonSnow snow, JsonSys sys, String dt_txt) {
        this.dt = dt;
        this.main = main;
        this.weather = weather;
        this.clouds = clouds;
        this.wind = wind;
        this.rain = rain;
        this.snow = snow;
        this.sys = sys;
        this.dt_txt = dt_txt;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public JsonWeatherMain getMain() {
        return main;
    }

    public void setMain(JsonWeatherMain main) {
        this.main = main;
    }

    public List<JsonWeatherWeather> getWeather() {
        return weather;
    }

    public void setWeather(List<JsonWeatherWeather> weather) {
        this.weather = weather;
    }

    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }

    public JsonClouds getClouds() {
        return clouds;
    }

    public void setClouds(JsonClouds clouds) {
        this.clouds = clouds;
    }

    public JsonRain getRain() {
        return rain;
    }

    public void setRain(JsonRain rain) {
        this.rain = rain;
    }

    public JsonSnow getSnow() {
        return snow;
    }

    public void setSnow(JsonSnow snow) {
        this.snow = snow;
    }

    public JsonSys getSys() {
        return sys;
    }

    public void setSys(JsonSys sys) {
        this.sys = sys;
    }

    public JsonWind getWind() {
        return wind;
    }

    public void setWind(JsonWind wind) {
        this.wind = wind;
    }
}
