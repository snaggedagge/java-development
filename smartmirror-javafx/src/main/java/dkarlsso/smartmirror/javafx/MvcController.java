package dkarlsso.smartmirror.javafx;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import dkarlsso.commons.application.ApplicationUtils;
import dkarlsso.commons.raspberry.OSHelper;
import dkarlsso.commons.raspberry.camera.RPICam;
import dkarlsso.commons.raspberry.camera.RPICameraException;
import dkarlsso.commons.radio.RadioPlayer;
import dkarlsso.commons.raspberry.screen.ScreenHandler;
import dkarlsso.commons.raspberry.screen.ScreenHandlerException;
import dkarlsso.commons.speechrecognition.CommandEnum;
import dkarlsso.commons.speechrecognition.PlayEnum;
import dkarlsso.commons.speechrecognition.SpeechException;
import dkarlsso.commons.speechrecognition.SpeechRecognizer;
import dkarlsso.smartmirror.javafx.model.DataService;
import dkarlsso.smartmirror.javafx.view.ViewControllerInterface;
import dkarlsso.smartmirror.javafx.view.impl.LightViewBuilder;
import dkarlsso.smartmirror.javafx.view.impl.ViewBuilder;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.io.File;

public class MvcController extends BaseController implements Runnable{

    private final Logger LOG = LogManager.getLogger(MvcController.class);

    @Inject
    @Named("DefaultDataService")
    private DataService dataService;

    private SpeechRecognizer speechRecognizer;

    private final ScreenHandler screenHandler = new ScreenHandler();

    private RadioPlayer radioPlayer = new RadioPlayer(ApplicationUtils.getSubfolder("radiochannels"), ApplicationUtils.getSubfolder("vlc"));

    private final RPICam camera = new RPICam(ApplicationUtils.getSubfolder("selfies"));

    private Timeline timeline;

    private boolean isThreadActive = true;

    private int updateSequenceMillis = 60000;

    private DateTime lastActivated = new DateTime();


    public MvcController(final ViewControllerInterface viewControllerInterface) {
        super(viewControllerInterface);
        Injector injector = Guice.createInjector(new BasicModule());
        dataService = injector.getInstance(DataService.class);
        viewBuilder = new LightViewBuilder(dataService);
        camera.setPreview(true);
        camera.setWaitingTime(5);
        try {
            LOG.info("Starting speechrecogniser");
            speechRecognizer = new SpeechRecognizer(ApplicationUtils.getSubfolder("voicerecognition"), this, this);
        } catch (SpeechException e) {
            LOG.error(e.getMessage(), e);
            System.exit(0);
        }
    }



    @Override
    public void playCommand(PlayEnum playEnum) {

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
    public void shutdown() {
        try {
            LOG.info("Powering off screen");
            screenHandler.setScreenPowerMode(false);
        } catch (ScreenHandlerException e) {
            LOG.error(e.getMessage(), e);
        }
        //System.exit(0);
    }

    @Override
    public synchronized void menuCommand(CommandEnum commandEnum) {

        // TODO: IMPLEWMENT ALARM CLOCK?
        // TODO: IMPLEMENT BEFORE/AFTER ACTIONS?

        LOG.info("Called method:" + commandEnum.prettyName() +
                " VoiceCommandsActive: " + voiceCommandsActive + " activatedTwice: "+ activatedTwice);

        if(voiceCommandsActive) {
            powerOnScreen();
            if(CommandEnum.SLEEP.equals(commandEnum)) {
                voiceCommandsActive = false;
                activatedTwice = false;
            }
            else {
                if(CommandEnum.WEATHER.equals(commandEnum)) {
                    Platform.runLater(() -> {
                        if (viewBuilder instanceof ViewBuilder) {
                            viewBuilder = new LightViewBuilder(dataService);
                            updateSequenceMillis = 60000;
                        } else {
                            viewBuilder = new ViewBuilder(dataService);
                            updateSequenceMillis = 5000;
                        }
                        initAnimation();
                        Platform.runLater(() -> viewBuilder.showCommand(commandEnum.prettyName()));
                    });
                }
            }
        }
        else {

            if(CommandEnum.ACTIVATE.equals(commandEnum)) {

                powerOnScreen();
                if(!activatedTwice) {
                    Platform.runLater(() -> viewBuilder.showMessage("Say Activate again to unlock", 5));
                }
                else {
                    voiceCommandsActive = true;
                    lastActivated = new DateTime();
                }
                activatedTwice = true;
            }
            else {
                activatedTwice = false;
            }
        }
    }

    void powerOnScreen() {
        if (!screenHandler.isScreenActive()) {
            try {
                LOG.info("Powering on screen");
                screenHandler.setScreenPowerMode(true);
            } catch (ScreenHandlerException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public void run() {

        initAnimation();
        speechRecognizer.startRecognition();
        while(isThreadActive) {
            try {

                int minutesSinceActive = Minutes.minutesBetween(lastActivated,new DateTime()).getMinutes();
                LOG.info("Minutes since active: " + minutesSinceActive);
                if(minutesSinceActive > 1) {
                    LOG.info("Powering off screen");
                    screenHandler.setScreenPowerMode(false);
                }

                speechRecognizer.getResult();
            } catch (SpeechException | ScreenHandlerException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }


    public void initAnimation() {
        if(timeline != null) {
            timeline.stop();
        }

        timeline = new Timeline(new KeyFrame(Duration.millis(updateSequenceMillis),
                ae -> viewInterface.displayView(buildStandardView())));
        timeline.setCycleCount(Animation.INDEFINITE);
        Platform.runLater(() -> {
                    viewInterface.displayView(buildStandardView());
                    timeline.play();
                });
    }


    @Override
    public void selfie() {
        try {
            File picture;
            if(OSHelper.isRaspberryPi()) {
                picture = camera.takePicture();
            }
            else {
                picture = new File(ApplicationUtils.getSubfolder("selfies"),"test.jpg");
            }

            Platform.runLater(() ->
            {
                final ImageView selfieImage = new ImageView(new Image(picture.toURI().toString()));
                viewInterface.displayView(selfieImage);
                PauseTransition pt = new PauseTransition();
                pt.setDuration(Duration.millis(3000));
                pt.setOnFinished(e ->
                        initAnimation());
                pt.play();
            });

        } catch (RPICameraException e) {
            LOG.error(e.getMessage(), e);
        }
    }

}
