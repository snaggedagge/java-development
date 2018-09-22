package dkarlsso.smartmirror.javafx;

import com.google.inject.Inject;
import com.google.inject.Injector;
import dkarlsso.commons.speech.speechrecognition.SpeechException;
import dkarlsso.commons.speech.speechrecognition.SpeechRecognizer;
import dkarlsso.smartmirror.javafx.model.application.ApplicationUtils;
import dkarlsso.commons.commandaction.CommandActionException;
import dkarlsso.commons.commandaction.CommandInvoker;
import dkarlsso.commons.raspberry.screen.ScreenHandler;
import dkarlsso.commons.raspberry.screen.ScreenHandlerException;
import dkarlsso.smartmirror.javafx.actions.ActionExecutor;
import dkarlsso.smartmirror.javafx.model.CommandEnum;
import dkarlsso.smartmirror.javafx.model.VoiceApplicationState;
import dkarlsso.smartmirror.javafx.model.interfaces.StateService;
import dkarlsso.smartmirror.javafx.threads.RunnableAlarmClock;
import dkarlsso.smartmirror.javafx.view.ViewControllerInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

public class ApplicationManager implements CommandInvoker<CommandEnum>, Runnable{

    private final Logger LOG = LogManager.getLogger(ApplicationManager.class);

    private final ActionExecutor actionExecutor;

    @Inject
    private ViewControllerInterface viewInterface;

    @Inject
    private StateService stateService;

    @Inject
    private ScreenHandler screenHandler;

    private SpeechRecognizer<CommandEnum> speechRecognizer;

    private DateTime lastActivated = new DateTime();

    private final RunnableAlarmClock runnableAlarmClock = new RunnableAlarmClock();


    public ApplicationManager(final Injector injector) {
        actionExecutor = new ActionExecutor(injector);
        try {
            speechRecognizer = new SpeechRecognizer<>(ApplicationUtils.getSubfolder("voicerecognition"), this, CommandEnum.class);
        } catch (final SpeechException e) {
            LOG.error(e.getMessage(), e);
        }
        injector.injectMembers(runnableAlarmClock);
    }


    @Override
    public void run() {
        viewInterface.initAnimation(5000, null);
        speechRecognizer.startRecognition();


//        new Thread(motionDetectionThread).start();
        new Thread(runnableAlarmClock).start();

        new Thread(() -> {
            boolean isRunning = true;
            while (isRunning) {
                try {
                    int minutesSinceActive = Minutes.minutesBetween(lastActivated,new DateTime()).getMinutes();
                    LOG.info("Minutes since active: " + minutesSinceActive);
                    if(minutesSinceActive > 5) {
                        LOG.info("Powering off screen");
                        screenHandler.setScreenPowerMode(false);
                    }
                    Thread.sleep(1000 * 60);
                } catch (InterruptedException e) {
                    isRunning = false;
                } catch (ScreenHandlerException e) {
                    synchronized (LOG) {
                        LOG.error("Could not turn screen off", e);
                    }
                }
            }
        });

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
    public void executeCommand(final CommandEnum commandEnum) throws CommandActionException {

        boolean shouldCallFunction = false;
        if(stateService.getVoiceApplicationState() == VoiceApplicationState.UNLOCKED) {
            shouldCallFunction = true;
        }
        else {
            if(CommandEnum.START.equals(commandEnum)) {
                shouldCallFunction = true;
            }
        }

        if(shouldCallFunction) {
            try {
                LOG.info("Function was called: " + commandEnum.prettyName());
                lastActivated = new DateTime();
                actionExecutor.executeCommand(commandEnum);
                viewInterface.displayStandardView(commandEnum.prettyName());
            }
            catch (final CommandActionException e) {
                LOG.error("Error occured while calling command " + commandEnum.prettyName(), e);
            }
        }
    }
}
