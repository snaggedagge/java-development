package dkarlsso.smartmirror.javafx.threads.impl;

import com.google.inject.Inject;
import dkarlsso.commons.raspberry.screen.ScreenHandler;
import dkarlsso.commons.raspberry.screen.ScreenHandlerException;
import dkarlsso.smartmirror.javafx.model.interfaces.StateService;
import dkarlsso.smartmirror.javafx.threads.RunnableService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

@RunnableService
public class RunnableScreenSaver implements Runnable {

    private final Logger LOG = LogManager.getLogger(RunnableScreenSaver.class);

    @Inject
    private StateService stateService;

    @Inject
    private ScreenHandler screenHandler;

    @Override
    public void run() {
        boolean isRunning = true;
        while (isRunning) {
            try {
                Thread.sleep(1000 * 60);
                int minutesSinceActive = Minutes.minutesBetween(stateService.getLastActivated(), new DateTime()).getMinutes();
                LOG.info("Minutes since active: " + minutesSinceActive);
                if(minutesSinceActive > 5) {
                    screenHandler.setScreenPowerMode(false);
                }
            } catch (InterruptedException e) {
                isRunning = false;
            } catch (ScreenHandlerException e) {
                LOG.error("Could not turn screen off", e);
            }
        }
    }
}
