package dkarlsso.smartmirror.javafx.actions.impl;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import dkarlsso.smartmirror.javafx.actions.annotation.Action;
import dkarlsso.commons.raspberry.relay.interfaces.RelayInterface;
import dkarlsso.commons.commandaction.CommandAction;
import dkarlsso.commons.speechrecognition.CommandEnum;

@Action( commandName = CommandEnum.LIGHTS)
public class LightsAction implements CommandAction {

    @Inject
    @Named("LightsRelay")
    private RelayInterface lightsRelay;

    @Override
    public void execute() {
        lightsRelay.switchState();
    }
}
