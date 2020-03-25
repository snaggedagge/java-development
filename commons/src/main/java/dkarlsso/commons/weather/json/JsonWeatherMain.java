package dkarlsso.commons.weather.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonWeatherMain {

    private double temp;

    private double temp_min;

    private double temp_max;

    private double pressure;

    private double sea_level;

    private double grnd_level;

    private double humidity;

    private double temp_kf;

    private double feels_like;
}
