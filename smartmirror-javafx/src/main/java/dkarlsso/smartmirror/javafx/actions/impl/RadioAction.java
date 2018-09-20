package dkarlsso.smartmirror.javafx.actions.impl;

import com.google.inject.Inject;
import dkarlsso.commons.multimedia.MediaPlayer;
import dkarlsso.smartmirror.javafx.model.CommandEnum;
import dkarlsso.commons.commandaction.CommandAction;
import dkarlsso.smartmirror.javafx.actions.Action;


@Action( commandName = CommandEnum.RADIO)
public class RadioAction implements CommandAction {

    @Inject
    private MediaPlayer radioPlayer;

    @Override
    public void execute() {
        radioPlayer.setPlaying(!radioPlayer.isPlaying());
    }
}
