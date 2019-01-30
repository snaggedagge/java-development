package dkarlsso.smartmirror.javafx.model.interfaces.impl;


import dkarlsso.smartmirror.javafx.model.application.ApplicationUtils;
import dkarlsso.commons.cache.TimeCache;
import dkarlsso.commons.quotes.FamousQuoteDTO;
import dkarlsso.commons.quotes.FamousQuoteException;
import dkarlsso.commons.quotes.FamousQuotesService;
import dkarlsso.commons.date.DayUtils;
import dkarlsso.commons.userinfo.UserWeekInfo;
import dkarlsso.commons.weather.LightWeatherPrognosisDTO;
import dkarlsso.commons.weather.WeatherException;
import dkarlsso.commons.weather.WeatherPrognosis;
import dkarlsso.commons.weather.WeatherService;
import dkarlsso.smartmirror.javafx.model.interfaces.DataService;
import dkarlsso.smartmirror.javafx.model.interfaces.DataServiceException;
import javafx.scene.image.Image;

import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class DefaultDataService implements DataService {

    private static final String CACHE_USER_WEEK_INFO = "USER_WEEK_INFO";

    private static final String CACHE_WEATHER_PROGNOSIS = "WEATHER_PROGNOSIS";

    private static final String CACHE_LIGHT_WEATHER_PROGNOSIS = "LIGHT_WEATHER_PROGNOSIS";

    private static final String CACHE_DAILY_QUOTE = "DAILY_QUOTE";

    private final WeatherService weatherReader = new WeatherService(ApplicationUtils.getSubfolder("weatherdata"), 3);

    private final FamousQuotesService famousQuotesService = new FamousQuotesService();

    private TimeCache cache = new TimeCache();

    private int quoteCounter = 0;

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
        if(!cache.isValid(CACHE_DAILY_QUOTE)) {
            cache.put(CACHE_DAILY_QUOTE, getQuoteInOrder(), 60 * 24);

        }
        return cache.get(CACHE_DAILY_QUOTE);
    }


    private FamousQuoteDTO getQuoteInOrder() throws DataServiceException {

        final List<FamousQuoteDTO> famousQuotes = new ArrayList<>();
        try {
            famousQuotes.addAll(famousQuotesService.getRandomQuotes());
        } catch (FamousQuoteException e) { }
        try {
            final File quotesFile = new File(ApplicationUtils.getSubfolder("quotes"), "quotes.txt");

            if(quotesFile.exists() && quotesFile.isFile()) {
                final ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(quotesFile));
                famousQuotes.addAll((List<FamousQuoteDTO>) inputStream.readObject());
                inputStream.close();
            }

            // Sorting is kinda retarded, but needed to get them in some sort of order, seems to be scrambled around each time otherwise.
            // Should be implemented in DB with index in DB in the future
            final List<FamousQuoteDTO> uniqueQuotes = famousQuotes.stream()
                    .distinct()
                    .sorted(Comparator.comparing(FamousQuoteDTO::getQuote))
                    .collect(Collectors.toList());

            if (quotesFile.exists() && quotesFile.isFile() || quotesFile.createNewFile()) {
                final ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(quotesFile));
                outputStream.writeObject(uniqueQuotes);
                outputStream.close();
            }
            quoteCounter = quoteCounter % uniqueQuotes.size();
            return uniqueQuotes.get(quoteCounter++);
        } catch (final Exception e) {
            throw new DataServiceException(e.getMessage(), e);
        }
    }

}
