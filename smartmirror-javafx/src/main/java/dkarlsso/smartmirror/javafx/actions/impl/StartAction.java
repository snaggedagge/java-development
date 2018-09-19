package dkarlsso.smartmirror.javafx.actions.impl;

import dkarlsso.commons.speechrecognition.CommandEnum;
import dkarlsso.commons.commandaction.CommandAction;
import dkarlsso.smartmirror.javafx.actions.annotation.Action;

@Action(commandName = CommandEnum.START)
public class StartAction implements CommandAction {
    @Override
    public void execute() {
/*        powerOnScreen();
        if(!activatedTwice) {
            Platform.runLater(() -> viewBuilder.showMessage("Say " + CommandEnum.START.prettyName() + " again to unlock", 5));
        }
        else {
            voiceCommandsActive = true;
        }
        activatedTwice = true;
        }
        else {
            activatedTwice = false;
        }*/
    }
}
