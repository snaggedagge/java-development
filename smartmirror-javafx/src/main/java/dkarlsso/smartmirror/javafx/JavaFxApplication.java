package dkarlsso.smartmirror.javafx;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.pi4j.io.gpio.GpioFactory;
import dkarlsso.commons.raspberry.OSHelper;
import dkarlsso.smartmirror.javafx.view.ViewControllerInterface;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class JavaFxApplication extends Application implements ViewControllerInterface {

    private final Logger LOG = LogManager.getLogger(JavaFxApplication.class);

    private BorderPane borderPane = new BorderPane();

    private Stage primaryStage;

    private Scene scene;

    private MvcController mvcController;

    private ApplicationManager applicationManager;

    int width = 1920;
    int height = 1080;


    public JavaFxApplication() {
        final Injector injector = Guice.createInjector(new BasicModule());

        applicationManager = new ApplicationManager(injector);
        mvcController = new MvcController(this);
        injector.injectMembers(applicationManager);
        injector.injectMembers(mvcController);
    }

    public static void main(String[] args) {
        if (OSHelper.isRaspberryPi()) {
            GpioFactory.getInstance();
        }

        launch(args);
    }

    @Override
    public void start(final Stage primaryStage){
        LOG.info("Starting FX application");

        this.primaryStage = primaryStage;
        scene = new Scene(borderPane, width, height);

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

        applicationManager.start();
        new Thread(mvcController).start();
    }

    @Override
    public synchronized void displayView(final Node node) {
        borderPane.setTop(node);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public synchronized void refreshView() {

    }
}
