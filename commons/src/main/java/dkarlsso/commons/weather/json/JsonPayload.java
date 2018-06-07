package dkarlsso.commons.weather.json;

import java.util.List;

public class JsonPayload {

    private String cod;

    private double message;

    private int cnt;

    private List<JsonWeather> list;

    private JsonCity city;

    public JsonPayload(String cod, double message, int cnt, List<JsonWeather> list, JsonCity city) {
        this.cod = cod;
        this.message = message;
        this.cnt = cnt;
        this.list = list;
        this.city = city;
    }

    public JsonPayload() {
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public List<JsonWeather> getList() {
        return list;
    }

    public void setList(List<JsonWeather> list) {
        this.list = list;
    }

    public JsonCity getCity() {
        return city;
    }

    public void setCity(JsonCity city) {
        this.city = city;
    }


    @Override
    public String toString() {
        return "JsonPayload{" +
                "cod='" + cod + '\'' +
                ", message=" + message +
                ", cnt=" + cnt +
                ", list=" + list +
                ", city=" + city +
                '}';
    }
}
