package dkarlsso.commons.weather;

import java.util.List;

public class WeatherPrognosis {

    private String locationName;

    private String countryCode;

    private List<List<Weather>> weatherList;

    public WeatherPrognosis() {
    }

    public WeatherPrognosis(String locationName, String countryCode, List<List<Weather>> weatherList) {
        this.locationName = locationName;
        this.countryCode = countryCode;
        this.weatherList = weatherList;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public List<List<Weather>> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<List<Weather>> weatherList) {
        this.weatherList = weatherList;
    }

    @Override
    public String toString() {
        return "WeatherPrognosis{" +
                "locationName='" + locationName + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", weatherList=" + weatherList +
                '}';
    }
}
