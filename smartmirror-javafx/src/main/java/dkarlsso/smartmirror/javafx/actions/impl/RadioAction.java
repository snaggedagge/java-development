package dkarlsso.smartmirror.javafx.actions.impl;

import com.google.inject.Inject;
import dkarlsso.commons.multimedia.MediaPlayer;
import dkarlsso.commons.speechrecognition.CommandEnum;
import dkarlsso.commons.commandaction.CommandAction;
import dkarlsso.smartmirror.javafx.actions.annotation.Action;


@Action( commandName = CommandEnum.RADIO)
public class RadioAction implements CommandAction {

    @Inject
    private MediaPlayer radioPlayer;

    @Override
    public void execute() {
        radioPlayer.setPlaying(!radioPlayer.isPlaying());
    }
}
