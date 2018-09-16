package dkarlsso.smartmirror.javafx.model.impl;


import dkarlsso.commons.application.ApplicationUtils;
import dkarlsso.commons.cache.TimeCache;
import dkarlsso.commons.quotes.FamousQuoteDTO;
import dkarlsso.commons.quotes.FamousQuoteException;
import dkarlsso.commons.quotes.FamousQuotesService;
import dkarlsso.smartmirror.javafx.UserinfoController;
import dkarlsso.commons.date.DayUtils;
import dkarlsso.commons.userinfo.UserWeekInfo;
import dkarlsso.commons.weather.LightWeatherPrognosisDTO;
import dkarlsso.commons.weather.WeatherException;
import dkarlsso.commons.weather.WeatherPrognosis;
import dkarlsso.commons.weather.WeatherService;
import dkarlsso.smartmirror.javafx.model.DataService;
import dkarlsso.smartmirror.javafx.model.DataServiceException;
import javafx.scene.image.Image;

import java.io.File;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DefaultDataService implements DataService {

    private static final String CACHE_USER_WEEK_INFO = "USER_WEEK_INFO";

    private static final String CACHE_WEATHER_PROGNOSIS = "WEATHER_PROGNOSIS";

    private static final String CACHE_LIGHT_WEATHER_PROGNOSIS = "LIGHT_WEATHER_PROGNOSIS";

    private static final String CACHE_DAILY_QUOTE = "DAILY_QUOTE";

    private final WeatherService weatherReader = new WeatherService(ApplicationUtils.getSubfolder("weatherdata"), 3);

    private final FamousQuotesService famousQuotesService = new FamousQuotesService();

    private TimeCache cache = new TimeCache();

    @Override
    public List<Date> getDateList() throws DataServiceException {
        return DayUtils.getDateForAWeek();
    }

    @Override
    public List<UserWeekInfo> getUserWeekInfoList() throws DataServiceException {
        try {
            if(!cache.isValid(CACHE_USER_WEEK_INFO)) {
                cache.put(CACHE_USER_WEEK_INFO, UserinfoController.getAllUsersInfo(), 10);
            }
            return cache.get(CACHE_USER_WEEK_INFO);
        } catch (ParseException e) {
            throw new DataServiceException();
        }
    }

    @Override
    public WeatherPrognosis getWeatherPrognosis() throws DataServiceException {
        try {
            if(!cache.isValid(CACHE_WEATHER_PROGNOSIS)) {
                cache.put(CACHE_WEATHER_PROGNOSIS, weatherReader.getWeather(), 15);
            }
            return cache.get(CACHE_WEATHER_PROGNOSIS);
        } catch (WeatherException e) {
            throw new DataServiceException(e.getMessage(), e);
        }
    }


    @Override
    public LightWeatherPrognosisDTO getLightWeatherPrognosis() throws DataServiceException {
        try {
            if(!cache.isValid(CACHE_LIGHT_WEATHER_PROGNOSIS)) {
                cache.put(CACHE_LIGHT_WEATHER_PROGNOSIS, weatherReader.getLightWeather(), 15);
            }
            return cache.get(CACHE_LIGHT_WEATHER_PROGNOSIS);
        } catch (WeatherException e) {
            throw new DataServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Image getIcon(String iconName) throws DataServiceException {
        File file;
        try {
            file = weatherReader.getWeatherIcon(iconName);
        } catch (WeatherException e) {
            throw new DataServiceException(e.getMessage(), e);
        }

        return new Image(file.toURI().toString());
    }

    @Override
    public FamousQuoteDTO getDailyQuote() throws DataServiceException {
        try {
            if(!cache.isValid(CACHE_DAILY_QUOTE)) {
                cache.put(CACHE_DAILY_QUOTE, famousQuotesService.getRandomQuote(), 60 * 24);
            }
            return cache.get(CACHE_DAILY_QUOTE);
        } catch (FamousQuoteException e) {
            throw new DataServiceException(e.getMessage(), e);
        }
    }
}
