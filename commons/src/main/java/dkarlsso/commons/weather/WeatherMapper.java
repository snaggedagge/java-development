package dkarlsso.commons.weather;

import dkarlsso.commons.date.DayUtils;
import dkarlsso.commons.weather.json.JsonPayload;
import dkarlsso.commons.weather.json.JsonWeather;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class WeatherMapper {

    public static WeatherPrognosis map(final JsonPayload weatherPayload) throws WeatherException {
        final WeatherPrognosis weatherPrognosis = new WeatherPrognosis();
        final List<List<Weather>> weatherList = new ArrayList<>();

        if(weatherPayload.getCity() != null) {
            weatherPrognosis.setCountryCode(weatherPayload.getCity().getCountry());
            weatherPrognosis.setLocationName(weatherPayload.getCity().getName());
        }

        int firstDayIndex = -1;
        for(JsonWeather jsonWeather : weatherPayload.getList()) {
            final Weather weather = map(jsonWeather);

            if(firstDayIndex == -1) {
                firstDayIndex = new DateTime(weather.getTime()).getDayOfYear();
            }

            int dayIndex = new DateTime(weather.getTime()).getDayOfYear();
            int realIndex = dayIndex - firstDayIndex;

            if(weatherList.size() <= realIndex) {
                weatherList.add(new ArrayList<>());
            }

            weatherList.get(realIndex).add(weather);
        }

        for(List<Weather> weatherListUnsorted : weatherList) {
            Collections.sort(weatherListUnsorted, Comparator.comparing(Weather::getTime));
        }

        weatherPrognosis.setWeatherList(weatherList);

        return weatherPrognosis;
    }


    public static Weather map(final JsonWeather jsonWeather) throws WeatherException {
        final Weather weather = new Weather();

        if(jsonWeather.getWeather() != null && !jsonWeather.getWeather().isEmpty()) {
            weather.setIconName(jsonWeather.getWeather().get(0).getIcon());
            weather.setWeather(jsonWeather.getWeather().get(0).getMain());
            weather.setWeatherDescription(jsonWeather.getWeather().get(0).getDescription());
        }

        if(jsonWeather.getMain() != null) {
            weather.setMaxTemp(jsonWeather.getMain().getTemp_max());
            weather.setMinTemp(jsonWeather.getMain().getTemp_min());
            weather.setPressure(jsonWeather.getMain().getPressure());
            weather.setTemp(jsonWeather.getMain().getTemp());
            weather.setHumidity((int)jsonWeather.getMain().getHumidity());
        }

        if(jsonWeather.getRain() != null) {
            weather.setRainVolumeThreeHours(jsonWeather.getRain().get3h());
        }
        if(jsonWeather.getSnow() != null) {
            weather.setSnowVolumeThreeHours(jsonWeather.getSnow().get3h());
        }
        if(jsonWeather.getClouds() != null) {
            weather.setCloudinessInPercent(jsonWeather.getClouds().getAll());
        }
        if(jsonWeather.getWind() != null) {
            weather.setWindDegrees(jsonWeather.getWind().getDeg());
            weather.setWindSpeed(jsonWeather.getWind().getSpeed());
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

        try {
            weather.setTime(dateFormat.parse(jsonWeather.getDt_txt()));
        } catch (ParseException e) {
            throw new WeatherException(e.getMessage(),e);
        }

        return weather;
    }


    public static LightWeatherPrognosisDTO lightMap(final JsonPayload weatherPayload) throws WeatherException {
        final LightWeatherPrognosisDTO lightWeatherPrognosisDTO = new LightWeatherPrognosisDTO();

        if(weatherPayload.getCity() != null) {
            lightWeatherPrognosisDTO.setCountryCode(weatherPayload.getCity().getCountry());
            lightWeatherPrognosisDTO.setLocationName(weatherPayload.getCity().getName());
        }

        final List<List<Weather>> weatherList = new ArrayList<>();

        for(JsonWeather jsonWeather : weatherPayload.getList()) {
            final Weather weather = map(jsonWeather);

            int dayIndex = Days.daysBetween(new DateTime(),new DateTime(weather.getTime())).getDays();

            if(weatherList.size() <= dayIndex) {
                weatherList.add(new ArrayList<>());
            }

            weatherList.get(dayIndex).add(weather);
        }
        final List<LightWeatherDTO> lightWeatherList = new ArrayList<>();
        lightWeatherPrognosisDTO.setWeatherList(lightWeatherList);

        for(List<Weather> weathers : weatherList) {
            lightWeatherList.add(lightMap(weathers));
        }

        return lightWeatherPrognosisDTO;
    }


    public static LightWeatherDTO lightMap(final List<Weather> jsonWeathers) {
        final LightWeatherDTO lightWeatherDTO = new LightWeatherDTO();


        double minTemp = jsonWeathers.get(0).getTemp();
        double maxTemp = jsonWeathers.get(0).getTemp();


        for(Weather jsonWeather: jsonWeathers) {
            if(jsonWeather.getTemp() > maxTemp) {
                maxTemp = jsonWeather.getTemp();
            }
            if(jsonWeather.getTemp() < minTemp) {
                minTemp = jsonWeather.getTemp();
            }
        }
        lightWeatherDTO.setMaxTemp(maxTemp);
        lightWeatherDTO.setMinTemp(minTemp);
        final Weather closestWeather = jsonWeathers.stream()
                .sorted(Comparator.comparing(Weather::getTime))
                .filter(weather -> {
                    final Calendar calendarOfWeather = GregorianCalendar.from(ZonedDateTime.ofInstant(weather.getTime().toInstant(), ZoneId.systemDefault()));
                    if(GregorianCalendar.getInstance().get(Calendar.DAY_OF_YEAR) != calendarOfWeather.get(Calendar.DAY_OF_YEAR)) {
                        // Return midday results of all other days for now
                        calendarOfWeather.set(Calendar.HOUR, 11);
                        return weather.getTime().toInstant().isAfter(calendarOfWeather.toInstant());
                    }
                    else {
                        calendarOfWeather.set(Calendar.HOUR, GregorianCalendar.getInstance().get(Calendar.HOUR));
                        return weather.getTime().toInstant().isAfter(calendarOfWeather.toInstant());
                    }
                })
                .findFirst()
                    .orElseGet(() -> jsonWeathers.get(jsonWeathers.size() - 1));
        lightWeatherDTO.setTemp(closestWeather.getTemp());

        lightWeatherDTO.setIconName(closestWeather.getIconName());
        lightWeatherDTO.setWeather(closestWeather.getWeather());
        lightWeatherDTO.setWeatherDescription(closestWeather.getWeatherDescription());

        lightWeatherDTO.setHumidity(closestWeather.getHumidity());


        lightWeatherDTO.setRainVolumeThreeHours(closestWeather.getRainVolumeThreeHours());

        lightWeatherDTO.setSnowVolumeThreeHours(closestWeather.getSnowVolumeThreeHours());

        lightWeatherDTO.setCloudinessInPercent(closestWeather.getCloudinessInPercent());

        lightWeatherDTO.setWindDegrees(closestWeather.getWindDegrees());
        lightWeatherDTO.setWindSpeed(closestWeather.getWindSpeed());


        lightWeatherDTO.setTime(closestWeather.getTime().toInstant());

        lightWeatherDTO.setDay(DayUtils.getDay(closestWeather.getTime()));
        return lightWeatherDTO;
    }

}
