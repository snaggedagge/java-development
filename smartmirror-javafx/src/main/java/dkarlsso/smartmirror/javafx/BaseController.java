package dkarlsso.smartmirror.javafx;

import com.google.inject.Inject;
import dkarlsso.commons.application.ApplicationUtils;
import dkarlsso.commons.model.CommonsException;
import dkarlsso.commons.multimedia.MediaPlayer;
import dkarlsso.commons.multimedia.radio.RadioPlayer;
import dkarlsso.commons.quotes.FamousQuoteDTO;
import dkarlsso.commons.raspberry.OSHelper;
import dkarlsso.commons.raspberry.enums.GPIOPins;
import dkarlsso.commons.raspberry.relay.OptoRelay;
import dkarlsso.commons.raspberry.relay.interfaces.RelayInterface;
import dkarlsso.commons.raspberry.settings.SoundController;
import dkarlsso.commons.speechrecognition.CommandEnum;
import dkarlsso.commons.speechrecognition.CommandInterface;
import dkarlsso.commons.speechrecognition.ListenerInterface;
import dkarlsso.commons.speechrecognition.PlayEnum;
import dkarlsso.smartmirror.javafx.model.DataService;
import dkarlsso.smartmirror.javafx.model.DataServiceException;
import dkarlsso.smartmirror.javafx.view.ViewControllerInterface;
import dkarlsso.smartmirror.javafx.view.ViewInterface;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

public abstract class BaseController implements ListenerInterface, CommandInterface {

    private final Logger LOG = LogManager.getLogger(BaseController.class);

    @Inject
    private MediaPlayer radioPlayer;

    @Inject
    protected DataService dataService;

    private SoundController soundController = new SoundController();

    protected final ViewControllerInterface viewInterface;

    protected ViewInterface viewBuilder;

    protected boolean voiceCommandsActive = true;

    protected boolean activatedTwice = false;

    protected DateTime lastActivated = new DateTime();

    protected RelayInterface lightsRelay;

    protected BaseController(final ViewControllerInterface viewControllerInterface) {
        viewInterface = viewControllerInterface;

        if(OSHelper.isRaspberryPi()) {
            lightsRelay = new OptoRelay(GPIOPins.GPIO14_TXDO);
            lightsRelay.setHigh();
            try {
                soundController.setVolume(soundController.getVolumeInPercent());
            } catch (CommonsException e) {
                LOG.error("Could not set initial volume: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public void playCommand(PlayEnum playEnum) {

    }

    @Override
    public void menuCommand(CommandEnum commandEnum) {

    }



    @Override
    public void radio() {
        if (radioPlayer.isPlaying()) {
            radioPlayer.stop();
        } else {
            radioPlayer.play();
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void lights() {
        lightsRelay.switchState();
    }

    @Override
    public void volume() {
        try {
            soundController.increaseVolume(25);
            LOG.info("Current volume is " + soundController.getVolumeInPercent() + "%");
        } catch (CommonsException e) {
            LOG.error(e.getMessage(), e);
        }
    }


    @Override
    public boolean shouldCallFunction(final CommandEnum commandEnum) {
        boolean shouldCallFunction = false;
        if(voiceCommandsActive) {
            shouldCallFunction = true;
        }
        else {
            if(CommandEnum.START.equals(commandEnum)) {
                shouldCallFunction = true;
            }
        }

        if(shouldCallFunction) {
            lastActivated = new DateTime();
            Platform.runLater(() -> viewInterface.displayView(buildStandardView(commandEnum.prettyName())));
        }
        return shouldCallFunction;
    }

    protected Node buildStandardView(final String command) {

        ViewInterface viewInterface = viewBuilder.clear()
                .addClock(Pos.TOP_LEFT)
                .addWeatherData()
                .addDayData()
                .addEventData();

        try {
            viewInterface = viewInterface.addDailyQuote(dataService.getDailyQuote());
        } catch (DataServiceException e) {
            LOG.error("Could not retrieve quote: " + e.getMessage(), e);
        }

        if(command != null) {
            viewInterface = viewInterface.showCommand(command);
        }
        if(radioPlayer.isPlaying()) {
            viewInterface = viewInterface.addImageBelowClock("x3m.png",0.8);
        }
        viewInterface = viewInterface.addImageBelowClock("volume.png", soundController.getVolumeInPercent(),100,0.5);

        return viewInterface.addLockIcon(!voiceCommandsActive)
                .getView();
    }
}
