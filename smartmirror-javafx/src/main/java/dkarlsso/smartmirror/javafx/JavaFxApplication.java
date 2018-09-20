package dkarlsso.smartmirror.javafx;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.pi4j.io.gpio.GpioFactory;
import dkarlsso.commons.container.BasicContainer;
import dkarlsso.commons.raspberry.OSHelper;
import dkarlsso.smartmirror.javafx.model.VoiceApplicationState;
import dkarlsso.smartmirror.javafx.model.interfaces.DataService;
import dkarlsso.smartmirror.javafx.model.interfaces.DataServiceException;
import dkarlsso.smartmirror.javafx.model.interfaces.StateService;
import dkarlsso.smartmirror.javafx.module.SmartMirrorModule;
import dkarlsso.smartmirror.javafx.view.ViewBuilder;
import dkarlsso.smartmirror.javafx.view.ViewControllerInterface;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class JavaFxApplication extends Application implements ViewControllerInterface {

    private final Logger LOG = LogManager.getLogger(JavaFxApplication.class);

    private static final int SCREEN_WIDTH = 1920;

    private static final int SCREEN_HEIGHT = 1080;

    private BorderPane borderPane = new BorderPane();

    private Stage primaryStage;

    private Scene scene;

    private Timeline timeline;

    private ApplicationManager applicationManager;

    @Inject
    private BasicContainer<ViewBuilder> viewBuilder;

    @Inject
    private DataService dataService;

    @Inject
    private StateService stateService;



    public JavaFxApplication() {
        if (OSHelper.isRaspberryPi()) {
            GpioFactory.getInstance();
        }

        final Injector injector = Guice.createInjector(new SmartMirrorModule(this));

        applicationManager = new ApplicationManager(injector);
        injector.injectMembers(applicationManager);
        injector.injectMembers(this);
    }


    @Override
    public void start(final Stage primaryStage){
        LOG.info("Starting FX application");

        this.primaryStage = primaryStage;
        scene = new Scene(borderPane, SCREEN_WIDTH, SCREEN_HEIGHT);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("textStyles.css").toExternalForm());

        borderPane.setId("hbox");
        primaryStage.setTitle("Smart Mirror");
        primaryStage.setFullScreen(true);

        primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                primaryStage.close();
                System.exit(0);
            }
        });

        new Thread(applicationManager).start();
    }

    @Override
    public void initAnimation(int updateSequenceMillis, final String command) {
        Platform.runLater(() -> {
            if(timeline != null) {
                timeline.stop();
            }

            timeline = new Timeline(new KeyFrame(Duration.millis(updateSequenceMillis),
                    ae -> this.displayStandardView(command)));
            timeline.setCycleCount(Animation.INDEFINITE);
            this.displayStandardView(command);
        });
    }

    @Override
    public void displayStandardView(final String command) {
        Platform.runLater(() -> {
            final ViewBuilder builder = viewBuilder.get().clear()
                    .addClock(Pos.TOP_LEFT)
                    .addWeatherData()
                    .addDayData()
                    .addEventData();

            try {
                builder.addDailyQuote(dataService.getDailyQuote());
            } catch (DataServiceException e) {
                LOG.error("Could not retrieve quote: " + e.getMessage(), e);
            }

            if(command != null) {
                builder.showCommand(command);
            }
            if(stateService.isRadioPlaying()) {
                builder.addImageBelowClock("x3m.png",0.8);
            }
            builder.addImageBelowClock("volume.png", stateService.getVolumeInPercent(),100,0.5);
            builder.addLockIcon(stateService.getVoiceApplicationState() == VoiceApplicationState.LOCKED);
            viewBuilder.set(builder);
            this.displayView(builder.getView());
            timeline.play();
        });
    }

    @Override
    public synchronized void displayView(final Node node) {
        Platform.runLater(() -> {
            borderPane.setTop(node);
            primaryStage.setScene(scene);
            primaryStage.show();
        });
    }

}
