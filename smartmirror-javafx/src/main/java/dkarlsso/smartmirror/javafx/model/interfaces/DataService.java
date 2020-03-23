package dkarlsso.smartmirror.javafx.model.interfaces;

import dkarlsso.commons.quotes.FamousQuoteDTO;
import dkarlsso.commons.userinfo.UserWeekInfo;
import dkarlsso.commons.weather.LightWeatherPrognosisDTO;
import dkarlsso.commons.weather.WeatherPrognosis;

import java.util.Date;
import java.util.List;

public interface DataService {

    List<Date> getDateList() throws DataServiceException;

    List<UserWeekInfo> getUserWeekInfoList() throws DataServiceException;

    WeatherPrognosis getWeatherPrognosis() throws DataServiceException;

    LightWeatherPrognosisDTO getLightWeatherPrognosis() throws DataServiceException;

    FamousQuoteDTO getDailyQuote() throws DataServiceException;
}
