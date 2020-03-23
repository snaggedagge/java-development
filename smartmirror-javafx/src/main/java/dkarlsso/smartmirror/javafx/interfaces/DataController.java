package dkarlsso.smartmirror.javafx.interfaces;

import dkarlsso.commons.quotes.FamousQuoteDTO;
import dkarlsso.commons.weather.LightWeatherPrognosisDTO;
import dkarlsso.commons.weather.WeatherException;
import dkarlsso.commons.weather.WeatherService;
import dkarlsso.smartmirror.javafx.model.interfaces.DataService;
import dkarlsso.smartmirror.javafx.model.interfaces.DataServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    private final DataService dataService;

    @Autowired
    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/api/weather")
    public LightWeatherPrognosisDTO getWeather() throws DataServiceException {
        return dataService.getLightWeatherPrognosis();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/api/quote")
    public FamousQuoteDTO getQuote() throws DataServiceException {
        return dataService.getDailyQuote();
    }
}
