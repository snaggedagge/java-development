package dkarlsso.smartmirror.javafx.actions;

import com.google.inject.Injector;
import dkarlsso.commons.annotation.AnnotationFinder;
import dkarlsso.commons.commandaction.CommandAction;
import dkarlsso.commons.commandaction.CommandActionException;
import dkarlsso.commons.speechrecognition.CommandEnum;
import dkarlsso.commons.commandaction.CommandInvoker;
import dkarlsso.smartmirror.javafx.actions.annotation.Action;

import java.util.Map;

public class ActionExecutor implements CommandInvoker<CommandEnum> {

    private final Map<CommandEnum, CommandAction> actionMap;

    public ActionExecutor(final Injector injector) {
        final String packageName = this.getClass().getPackage().getName();

        // Let the magic happen
        actionMap = AnnotationFinder.findClassesWithAnnotation(packageName, Action.class, Action::commandName);
        for (final CommandAction commandAction : actionMap.values()) {
            injector.injectMembers(commandAction);
        }
    }

    @Override
    public void executeCommand(CommandEnum commandEnum) throws CommandActionException {
        if (!actionMap.containsKey(commandEnum)) {
            throw new CommandActionException("This operation has not been implemented or added. Action: " + commandEnum.prettyName());
        }
        actionMap.get(commandEnum).execute();
    }
}
