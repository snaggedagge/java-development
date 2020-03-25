package dkarlsso.commons.weather;

import lombok.Data;

import java.time.Instant;

@Data
public class LightWeatherDTO {

    private double temp;

    private double minTemp;

    private double maxTemp;

    private int humidity;

    private String weather;

    private String weatherDescription;

    private String iconName;

    private int cloudinessInPercent;

    private double windSpeed;

    private int windDegrees;

    private double rainVolumeThreeHours;

    private double snowVolumeThreeHours;

    private Instant time;

    private String day;
}
