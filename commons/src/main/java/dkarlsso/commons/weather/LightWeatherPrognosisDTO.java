package dkarlsso.commons.weather;

import java.util.ArrayList;
import java.util.List;

public class LightWeatherPrognosisDTO {

    private String locationName;

    private String countryCode;

    private List<LightWeatherDTO> weatherList = new ArrayList<>();


    public LightWeatherPrognosisDTO(String locationName, String countryCode, List<LightWeatherDTO> weatherList) {
        this.locationName = locationName;
        this.countryCode = countryCode;
        this.weatherList = weatherList;
    }

    public LightWeatherPrognosisDTO() {
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

    public List<LightWeatherDTO> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<LightWeatherDTO> weatherList) {
        this.weatherList = weatherList;
    }
}
