package dkarlsso.smartmirror.javafx.threads.impl;

import dkarlsso.commons.raspberry.screen.ScreenHandler;
import dkarlsso.commons.raspberry.screen.ScreenHandlerException;
import dkarlsso.smartmirror.javafx.model.interfaces.StateService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class RunnableScreenSaver {

    private final StateService stateService;

    private final ScreenHandler screenHandler;

    @Autowired
    public RunnableScreenSaver(StateService stateService, ScreenHandler screenHandler) {
        this.stateService = stateService;
        this.screenHandler = screenHandler;
    }

    @Scheduled(fixedDelay = 1000 * 60)
    public void run() {
        try {
            int minutesSinceActive = Minutes.minutesBetween(stateService.getLastActivated(), new DateTime()).getMinutes();
            if(minutesSinceActive > 5) {
                if (screenHandler.isScreenActive()) {
                    log.info("Disabling screen due to {} min of inactivity", minutesSinceActive);
                }
                screenHandler.setScreenPowerMode(false);
            }
        } catch (ScreenHandlerException e) {
            log.error("Could not turn screen off", e);
        }
    }
}
