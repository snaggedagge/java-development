package dkarlsso.smartmirror.javafx.actions.impl;

import dkarlsso.commons.commandaction.CommandActionException;
import dkarlsso.commons.multimedia.MediaPlayer;
import dkarlsso.smartmirror.javafx.actions.ActionAwareInvoker;
import dkarlsso.smartmirror.javafx.model.CommandEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


@Component
public class RadioAction implements ActionAwareInvoker {

    private final MediaPlayer radioPlayer;

    @Autowired
    public RadioAction(MediaPlayer radioPlayer) {
        this.radioPlayer = radioPlayer;
    }

    @Override
    public Set<CommandEnum> supports() {
        return new HashSet<>(Collections.singletonList(CommandEnum.RADIO));
    }

    @Override
    public void executeCommand(CommandEnum commandEnum) throws CommandActionException {
        radioPlayer.setPlaying(!radioPlayer.isPlaying());
    }
}
