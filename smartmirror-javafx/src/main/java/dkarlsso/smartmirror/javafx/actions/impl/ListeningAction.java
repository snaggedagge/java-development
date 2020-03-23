package dkarlsso.smartmirror.javafx.actions.impl;

import dkarlsso.commons.commandaction.CommandActionException;
import dkarlsso.commons.model.CommonsException;
import dkarlsso.commons.multimedia.settings.SoundController;
import dkarlsso.commons.raspberry.relay.interfaces.RelayInterface;
import dkarlsso.smartmirror.javafx.actions.ActionAwareInvoker;
import dkarlsso.smartmirror.javafx.model.CommandEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ListeningAction implements ActionAwareInvoker {

    private final RelayInterface lightsRelay;

    private final SoundController soundController;

    private boolean alexaListening = false;

    private int previousVolume;

    private final Set<CommandEnum> supportedCommands = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            CommandEnum.ALEXA_LISTENING, CommandEnum.ALEXA_STOPPED_LISTENING, CommandEnum.LIGHTS)));

    private final Map<CommandEnum, Runnable> commandFunctionMap;

    @Autowired
    public ListeningAction(RelayInterface lightsRelay, SoundController soundController) {
        this.lightsRelay = lightsRelay;
        this.soundController = soundController;

        this.commandFunctionMap = new HashMap<>();
        commandFunctionMap.put(CommandEnum.LIGHTS, this::executeLightsCommand);
        commandFunctionMap.put(CommandEnum.ALEXA_LISTENING, this::executeListeningCommand);
        commandFunctionMap.put(CommandEnum.ALEXA_STOPPED_LISTENING, this::executeStopListeningCommand);
    }

    public void executeListeningCommand() {
        previousVolume = soundController.getVolumeInPercent();
        try {
            soundController.setVolume(20);
        } catch (CommonsException e) {
        }
        if (lightsRelay.isHigh()) {
            lightsRelay.setLow();
            alexaListening = true;
        }
    }

    public void executeStopListeningCommand() {
        try {
            soundController.setVolume(previousVolume);
        } catch (CommonsException e) {
        }

        if (alexaListening) {
            lightsRelay.setHigh();
            alexaListening = false;
        }
    }

    public void executeLightsCommand() {
        lightsRelay.switchState();
    }

    @Override
    public void executeCommand(CommandEnum commandEnum) throws CommandActionException {
        commandFunctionMap.get(commandEnum).run();
    }

    @Override
    public Set<CommandEnum> supports() {
        return supportedCommands;
    }
}
