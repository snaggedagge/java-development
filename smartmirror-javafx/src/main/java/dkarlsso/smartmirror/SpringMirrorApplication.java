package dkarlsso.smartmirror;

import com.pi4j.io.gpio.GpioFactory;
import dkarlsso.commons.multimedia.MediaPlayer;
import dkarlsso.commons.multimedia.radio.RadioPlayer;
import dkarlsso.commons.multimedia.settings.SoundController;
import dkarlsso.commons.raspberry.OSHelper;
import dkarlsso.commons.raspberry.enums.GPIOPins;
import dkarlsso.commons.raspberry.relay.MockRelay;
import dkarlsso.commons.raspberry.relay.OptoRelay;
import dkarlsso.commons.raspberry.relay.interfaces.RelayInterface;
import dkarlsso.commons.raspberry.screen.DefaultScreenHandler;
import dkarlsso.commons.raspberry.screen.EmptyScreenHandler;
import dkarlsso.commons.raspberry.screen.ScreenHandler;
import dkarlsso.commons.weather.WeatherService;
import dkarlsso.smartmirror.javafx.model.application.ApplicationUtils;
import dkarlsso.smartmirror.javafx.model.interfaces.DataService;
import dkarlsso.smartmirror.javafx.model.interfaces.StateService;
import dkarlsso.smartmirror.javafx.model.interfaces.impl.DefaultDataService;
import dkarlsso.smartmirror.javafx.model.interfaces.impl.DefaultStateService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@ComponentScan
@EnableScheduling
public class SpringMirrorApplication{

    public static void main(String[] args) {
        if (OSHelper.isRaspberryPi()) {
            GpioFactory.getInstance();
        }
        SpringApplication.run(SpringMirrorApplication.class);
    }

    @Bean
    public WeatherService weatherService() {
        return new WeatherService(ApplicationUtils.getSubfolder("weatherdata"), 3);
    }

    @Bean
    public SoundController soundController() {
        return new SoundController();
    }

    @Bean
    public MediaPlayer mediaPlayer() {
        return new RadioPlayer(ApplicationUtils.getSubfolder("radiochannels"), ApplicationUtils.getSubfolder("vlc"));
    }

    @Bean
    public DataService dataService() {
        return new DefaultDataService();
    }

    @Bean
    public StateService stateService() {
        return new DefaultStateService(mediaPlayer(), soundController());
    }

    @Bean
    public RelayInterface lightsRelay() {
        final RelayInterface lightsRelay;
        if (OSHelper.isRaspberryPi()) {
            lightsRelay = new OptoRelay(GPIOPins.GPIO14_TXDO);
            lightsRelay.setHigh();
        }
        else {
            lightsRelay = new MockRelay();
        }
        return lightsRelay;
    }

    @Bean
    public ScreenHandler screenHandler() {
        if (OSHelper.isRaspberryPi()) {
            return new DefaultScreenHandler();
        }
        else {
            return new EmptyScreenHandler();
        }
    }
}
