package dkarlsso.smartmirror.javafx;

import com.google.inject.Inject;
import com.google.inject.Injector;
import dkarlsso.commons.annotation.AnnotationFinder;
import dkarlsso.commons.commandaction.CommandActionException;
import dkarlsso.commons.commandaction.CommandInvoker;
import dkarlsso.commons.model.CommonsException;
import dkarlsso.commons.motiondetection.MotionAction;
import dkarlsso.commons.motiondetection.MotionDetectionThread;
import dkarlsso.commons.motiondetection.MotionType;
import dkarlsso.commons.raspberry.OSHelper;
import dkarlsso.commons.raspberry.camera.impl.ThreadSafeCameraSingleton;
import dkarlsso.commons.raspberry.camera.impl.WebCam;
import dkarlsso.commons.raspberry.screen.ScreenHandler;
import dkarlsso.commons.raspberry.screen.ScreenHandlerException;
import dkarlsso.commons.speech.speechrecognition.SpeechException;
import dkarlsso.commons.speech.speechrecognition.SpeechRecognizer;
import dkarlsso.smartmirror.javafx.actions.ActionExecutor;
import dkarlsso.smartmirror.javafx.model.CommandEnum;
import dkarlsso.smartmirror.javafx.model.VoiceApplicationState;
import dkarlsso.smartmirror.javafx.model.application.ApplicationUtils;
import dkarlsso.smartmirror.javafx.model.interfaces.StateService;
import dkarlsso.smartmirror.javafx.threads.RunnableService;
import dkarlsso.smartmirror.javafx.view.ViewControllerInterface;
import javafx.application.Platform;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import java.util.List;

public class ApplicationManager implements CommandInvoker<CommandEnum>, Runnable, MotionAction {

    private final Logger LOG = LogManager.getLogger(ApplicationManager.class);

    private final ActionExecutor actionExecutor;

    @Inject
    private ViewControllerInterface viewInterface;

    @Inject
    private StateService stateService;

    @Inject
    private ScreenHandler screenHandler;

    private SpeechRecognizer<CommandEnum> speechRecognizer;

    private MotionDetectionThread motionDetectionThread;


    static {
        // TODO: Fix this to something decent
        try {
            System.load("/usr/local/share/OpenCV/java/libopencv_java345.so");
        } catch (Throwable e) {
            System.err.println("Native code library failed to load.\n" + e);
        }
    }

    public ApplicationManager(final Injector injector) {
        actionExecutor = new ActionExecutor(injector);
        try {
            speechRecognizer = new SpeechRecognizer<>(this, CommandEnum.class);
            final List<Runnable> listOfThreads = AnnotationFinder.findClassesWithAnnotation(getClass().getPackage().getName(), RunnableService.class);

            for(final Runnable runnable : listOfThreads) {
                injector.injectMembers(runnable);
                new Thread(runnable).start();
            }

        } catch (final CommonsException | SpeechException e) {
            LOG.error(e.getMessage(), e);
        }
    }


    @Override
    public void run() {
        viewInterface.initAnimation(5000, null);
        speechRecognizer.startRecognition();

        /** TODO: Should be moved to {@link RunnableService*/
        try {
            ThreadSafeCameraSingleton.setCamera(new WebCam(ApplicationUtils.getSubfolder("pictures")));
            motionDetectionThread = new MotionDetectionThread(ThreadSafeCameraSingleton.getCamera(), this);
            new Thread(motionDetectionThread).start();
        } catch (CommonsException e) {
            LOG.error("Could not start motion detection " + e.getMessage(), e);
        }


        while (true) {
            try {
                speechRecognizer.getResult();
            } catch (SpeechException e) {
                synchronized (LOG) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public void executeCommand(final CommandEnum commandEnum) {
        try {
            /** TODO: Should be moved to corresponding {@link dkarlsso.smartmirror.javafx.actions.Action} */
            if(CommandEnum.COMMAND.equals(commandEnum)) {
                stateService.setVoiceApplicationState(VoiceApplicationState.UNLOCKED);
                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) { }
                    stateService.setVoiceApplicationState(VoiceApplicationState.LOCKED);
                    viewInterface.displayStandardView(null);
                }).start();
            }

            final boolean shouldCallFunction = stateService.getVoiceApplicationState() == VoiceApplicationState.UNLOCKED;
            if(shouldCallFunction) {
                    LOG.info("Function was called: " + commandEnum.prettyName());
                    stateService.setLastActivated(new DateTime());
                    actionExecutor.executeCommand(commandEnum);
                    if (CommandEnum.COMMAND != commandEnum) {
                        viewInterface.displayStandardView(commandEnum.prettyName());
                    }
            }
            if (OSHelper.isRaspberryPi()) {
                if (shouldCallFunction && !screenHandler.isScreenActive()) {
                    screenHandler.setScreenPowerMode(true);
                }
            }
        }
        catch (final CommandActionException e) {
            LOG.error("Error occured while calling command " + commandEnum.prettyName());
        } catch (ScreenHandlerException e) {
            LOG.error("Could not change screen status", e);
        }
    }

    @Override
    public void motionEvent(MotionType motionType) {
        try {
            if (!screenHandler.isScreenActive()) {
                screenHandler.setScreenPowerMode(true);
            }

        } catch (ScreenHandlerException e) {
            LOG.error("Could not turn on screen");
        }
        LOG.error("Motion detected!!");

        viewInterface.showMessage("Hello you handsome devil!", 2);
    }
}
