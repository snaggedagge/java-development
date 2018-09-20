package dkarlsso.smartmirror.javafx.actions.impl;

import com.google.inject.Inject;
import dkarlsso.commons.model.CommonsException;
import dkarlsso.commons.raspberry.settings.SoundController;
import dkarlsso.smartmirror.javafx.model.CommandEnum;
import dkarlsso.commons.commandaction.CommandAction;
import dkarlsso.smartmirror.javafx.actions.Action;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Action(commandName = CommandEnum.VOLUME)
public class VolumeAction implements CommandAction {

    private final Logger LOG = LogManager.getLogger(VolumeAction.class);

    @Inject
    private SoundController soundController;

    @Override
    public void execute() {
        try {
            soundController.increaseVolume(25);
            LOG.info("Current volume is " + soundController.getVolumeInPercent() + "%");
        } catch (CommonsException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
