package dkarlsso.smartmirror.javafx.actions.impl;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import dkarlsso.smartmirror.javafx.actions.Action;
import dkarlsso.commons.raspberry.relay.interfaces.RelayInterface;
import dkarlsso.commons.commandaction.CommandAction;
import dkarlsso.smartmirror.javafx.model.CommandEnum;
import dkarlsso.smartmirror.javafx.model.VoiceApplicationState;

public class LightsAction {

    @Inject
    @Named("LightsRelay")
    private RelayInterface lightsRelay;

    private boolean alexaListening = false;


    @Action(commandName = CommandEnum.ALEXA_LISTENING)
    public void executeListeningCommand() {
        if (lightsRelay.isHigh()) {
            lightsRelay.setLow();
            alexaListening = true;
        }
    }

    @Action(commandName = CommandEnum.ALEXA_STOPPED_LISTENING)
    public void executeStopListeningCommand() {
        if (alexaListening) {
            lightsRelay.setHigh();
            alexaListening = false;
        }
    }

    @Action(commandName = CommandEnum.LIGHTS)
    public void executeLightsCommand() {
        lightsRelay.switchState();
    }
}
