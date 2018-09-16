package dkarlsso.smartmirror.javafx;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import dkarlsso.commons.application.ApplicationUtils;
import dkarlsso.commons.model.CommonsException;
import dkarlsso.commons.motiondetection.MotionAction;
import dkarlsso.commons.motiondetection.MotionDetectionThread;
import dkarlsso.commons.motiondetection.MotionType;
import dkarlsso.commons.raspberry.OSHelper;
import dkarlsso.commons.raspberry.camera.Camera;
import dkarlsso.commons.raspberry.camera.impl.ThreadSafeCamera;
import dkarlsso.commons.raspberry.camera.impl.ThreadSafeCameraSingleton;
import dkarlsso.commons.raspberry.camera.impl.WebCam;
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

public class MvcController extends BaseController implements Runnable, MotionAction {

    private final Logger LOG = LogManager.getLogger(MvcController.class);



    private SpeechRecognizer speechRecognizer;

//    private MotionDetectionThread motionDetectionThread;


    private final ScreenHandler screenHandler = new ScreenHandler();

    //private Camera camera = null;

    private Timeline timeline;

    private boolean isThreadActive = true;

    private int updateSequenceMillis = 60000;

    public MvcController(final ViewControllerInterface viewControllerInterface) {
        super(viewControllerInterface);

        try {
            LOG.info("Starting speechrecogniser");
            speechRecognizer = new SpeechRecognizer(ApplicationUtils.getSubfolder("voicerecognition"), this, this);
        } catch (SpeechException e) {
            LOG.error(e.getMessage(), e);
            System.exit(0);
        }

        if(OSHelper.isRaspberryPi()) {

/*            //ThreadSafeCameraSingleton.setCamera(new WebCam(ApplicationUtils.getSubfolder("selfies")));

            try {
                //camera = ThreadSafeCameraSingleton.getCamera();
                //motionDetectionThread = new MotionDetectionThread(ThreadSafeCameraSingleton.getCamera(), this);
            } catch (CommonsException e) {
                LOG.error("Could not get camera: " + e.getMessage(), e);
            }
            //camera.setPreview(true);
            //camera.setWaitingTime(5);*/
        }
    }



    @Override
    public void playCommand(PlayEnum playEnum) {

    }


    @Override
    public synchronized void menuCommand(CommandEnum commandEnum) {

        // TODO: IMPLEWMENT ALARM CLOCK?
        // TODO: IMPLEMENT BEFORE/AFTER ACTIONS?

        LOG.info("Called method:" + commandEnum.prettyName() +
                " VoiceCommandsActive: " + voiceCommandsActive + " activatedTwice: "+ activatedTwice);

        if(voiceCommandsActive) {
            powerOnScreen();
        }
        else {
            if(CommandEnum.START.equals(commandEnum)) {
                powerOnScreen();
                if(!activatedTwice) {
                    Platform.runLater(() -> viewBuilder.showMessage("Say " + CommandEnum.START.prettyName() + " again to unlock", 5));
                }
                else {
                    voiceCommandsActive = true;
                }
                activatedTwice = true;
            }
            else {
                activatedTwice = false;
            }
        }
    }

    @Override
    public void sleep() {
        voiceCommandsActive = false;
        activatedTwice = false;
    }

    @Override
    public void weather() {
        Platform.runLater(() -> {
            if (viewBuilder instanceof ViewBuilder) {
                viewBuilder = new LightViewBuilder(dataService);
                updateSequenceMillis = 60000;
            } else {
                viewBuilder = new ViewBuilder(dataService);
                updateSequenceMillis = 5000;
            }
            initAnimation();
            Platform.runLater(() -> viewBuilder.showCommand(CommandEnum.WEATHER.prettyName()));
        });
    }

    @Override
    public void selfie() {
/*        try {
            File picture = null;
            if(OSHelper.isRaspberryPi()) {
                //picture = camera.takePicture();
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

        } catch (CommonsException e) {
            LOG.error(e.getMessage(), e);
        }*/
    }

    @Override
    public void run() {
        viewBuilder = new ViewBuilder(dataService);

        initAnimation();
        speechRecognizer.startRecognition();
//        new Thread(motionDetectionThread).start();
        while(isThreadActive) {
            try {

                int minutesSinceActive = Minutes.minutesBetween(lastActivated,new DateTime()).getMinutes();
                LOG.info("Minutes since active: " + minutesSinceActive);
                if(minutesSinceActive > 5) {
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
                ae -> viewInterface.displayView(buildStandardView(null))));
        timeline.setCycleCount(Animation.INDEFINITE);
        Platform.runLater(() -> {
                    viewInterface.displayView(buildStandardView(null));
                    timeline.play();
                });
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
    public void motionEvent(MotionType motionType) {
        LOG.error("FELT MOTION!");
        Platform.runLater(() -> viewBuilder.showMessage("Hello stranger! I know you are there", 5));
    }
}
