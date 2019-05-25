package dkarlsso.smartmirror.javafx.actions.impl;

import com.google.inject.Inject;
import dkarlsso.commons.raspberry.screen.ScreenHandler;
import dkarlsso.commons.raspberry.screen.ScreenHandlerException;
import dkarlsso.smartmirror.javafx.actions.Action;
import dkarlsso.smartmirror.javafx.model.CommandEnum;
import dkarlsso.smartmirror.javafx.model.VoiceApplicationState;
import dkarlsso.smartmirror.javafx.model.interfaces.DataService;
import dkarlsso.smartmirror.javafx.model.interfaces.StateService;
import dkarlsso.smartmirror.javafx.view.ViewBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LockedAction {

    private final Logger LOG = LogManager.getLogger(LockedAction.class);

    private ViewBuilder viewBuilder;

    @Inject
    private ScreenHandler screenHandler;

    @Inject
    private DataService dataService;

    @Inject
    private StateService stateService;

    private void powerOnScreen() {
        if (!screenHandler.isScreenActive()) {
            try {
                LOG.info("Powering on screen");
                screenHandler.setScreenPowerMode(true);
            } catch (ScreenHandlerException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

}
