package dkarlsso.smartmirror.javafx;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.google.inject.util.Providers;
import com.pi4j.io.gpio.GpioFactory;
import dkarlsso.commons.application.ApplicationUtils;
import dkarlsso.commons.multimedia.MediaPlayer;
import dkarlsso.commons.multimedia.radio.RadioPlayer;
import dkarlsso.commons.raspberry.OSHelper;
import dkarlsso.commons.raspberry.enums.GPIOPins;
import dkarlsso.commons.raspberry.relay.MockRelay;
import dkarlsso.commons.raspberry.relay.OptoRelay;
import dkarlsso.commons.raspberry.relay.interfaces.RelayInterface;
import dkarlsso.commons.raspberry.settings.SoundController;
import dkarlsso.smartmirror.javafx.model.DataService;
import dkarlsso.smartmirror.javafx.model.impl.DefaultDataService;

public class BasicModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DataService.class).toInstance(new DefaultDataService());
        bind(MediaPlayer.class).toInstance(new RadioPlayer(ApplicationUtils.getSubfolder("radiochannels"), ApplicationUtils.getSubfolder("vlc")));
        bind(SoundController.class).toInstance(new SoundController());

        if (OSHelper.isRaspberryPi()) {
            final RelayInterface lightsRelay = new OptoRelay(GPIOPins.GPIO14_TXDO);
            lightsRelay.setHigh();
            bind(RelayInterface.class).annotatedWith(Names.named("LightsRelay")).toInstance(lightsRelay);
        }
        else {
            bind(RelayInterface.class).annotatedWith(Names.named("LightsRelay")).toInstance(new MockRelay());
        }


    }
}