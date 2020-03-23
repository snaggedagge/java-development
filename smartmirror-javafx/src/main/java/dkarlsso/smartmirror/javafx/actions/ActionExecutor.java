package dkarlsso.smartmirror.javafx.actions;

import dkarlsso.commons.commandaction.CommandActionException;
import dkarlsso.commons.commandaction.CommandInvoker;
import dkarlsso.commons.raspberry.screen.ScreenHandler;
import dkarlsso.commons.raspberry.screen.ScreenHandlerException;
import dkarlsso.smartmirror.javafx.model.CommandEnum;
import dkarlsso.smartmirror.javafx.model.interfaces.StateService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component("ActionExecutor")
@Slf4j
public class ActionExecutor implements CommandInvoker<CommandEnum> {

    private final Map<CommandEnum, CommandInvoker<CommandEnum>> actionMap = new LinkedHashMap<>();

    private final StateService stateService;

    private final ScreenHandler screenHandler;

    @Autowired
    public ActionExecutor(List<ActionAwareInvoker> actionList, StateService stateService, ScreenHandler screenHandler) {
        this.stateService = stateService;
        this.screenHandler = screenHandler;
        actionList.forEach(action -> action.supports().forEach(supportedAction -> actionMap.put(supportedAction, action)));
    }

    @Override
    public void executeCommand(CommandEnum commandEnum) throws CommandActionException {
        if (!actionMap.containsKey(commandEnum)) {
            throw new CommandActionException("This operation has not been implemented or added. Action: " + commandEnum.prettyName());
        }

        stateService.setLastActivated(new DateTime());
        actionMap.get(commandEnum).executeCommand(commandEnum);
        try {
            screenHandler.setScreenPowerMode(true);
        }
        catch (final ScreenHandlerException e) {
            log.error("Could not change screen status", e);
        }
    }
}
