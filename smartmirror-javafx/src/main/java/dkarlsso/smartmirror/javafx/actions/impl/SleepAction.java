package dkarlsso.smartmirror.javafx.actions.impl;

import dkarlsso.commons.speechrecognition.CommandEnum;
import dkarlsso.commons.commandaction.CommandAction;
import dkarlsso.smartmirror.javafx.actions.annotation.Action;

@Action(commandName = CommandEnum.SLEEP)
public class SleepAction implements CommandAction {
    @Override
    public void execute() {
//        voiceCommandsActive = false;
//        activatedTwice = false;
    }
}
