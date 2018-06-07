package dkarlsso.smartmirror.javafx;

import dkarlsso.commons.speechrecognition.CommandEnum;
import dkarlsso.commons.speechrecognition.CommandInterface;
import dkarlsso.commons.speechrecognition.ListenerInterface;
import dkarlsso.commons.speechrecognition.PlayEnum;
import dkarlsso.smartmirror.javafx.view.ViewControllerInterface;
import dkarlsso.smartmirror.javafx.view.ViewInterface;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public abstract class BaseController implements ListenerInterface, CommandInterface {

    private final Logger LOG = LogManager.getLogger(BaseController.class);

    protected final ViewControllerInterface viewInterface;

    protected ViewInterface viewBuilder;

    protected boolean voiceCommandsActive = true;

    protected boolean activatedTwice = false;



    protected BaseController(final ViewControllerInterface viewControllerInterface) {
        viewInterface = viewControllerInterface;
    }

    @Override
    public void playCommand(PlayEnum playEnum) {

    }

    @Override
    public void menuCommand(CommandEnum commandEnum) {

    }

    @Override
    public void weather() {

    }

    @Override
    public void radio() {

    }


    @Override
    public void youtube() {
        if(Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=Ip7QZPw04Ks"));
            } catch (IOException | URISyntaxException e) {
                LOG.error("Youtube error:", e);
            }
        }
    }

    @Override
    public void activate() {

    }

    @Override
    public void sleep() {

    }


    @Override
    public boolean shouldCallFunction(final CommandEnum commandEnum) {
        boolean shouldCallFunction = false;
        if(voiceCommandsActive) {
            shouldCallFunction = true;
        }
        else {
            if(CommandEnum.ACTIVATE.equals(commandEnum)) {
                shouldCallFunction = true;
            }
        }

        if(shouldCallFunction) {
            Platform.runLater(() -> viewInterface.displayView(buildStandardView(commandEnum.prettyName())));
        }
        return shouldCallFunction;
    }

    protected Node buildStandardView(final String command) {
        return viewBuilder.clear()
                .addClock(Pos.TOP_LEFT)
                .addWeatherData()
                .addDayData()
                .addEventData()
                .showCommand(command)
                .addLockIcon(!voiceCommandsActive)
                .getView();
    }

    protected Node buildStandardView() {
        return viewBuilder.clear()
                .addClock(Pos.TOP_LEFT)
                .addWeatherData()
                .addDayData()
                .addEventData()
                .addLockIcon(!voiceCommandsActive)
                .getView();
    }

}
