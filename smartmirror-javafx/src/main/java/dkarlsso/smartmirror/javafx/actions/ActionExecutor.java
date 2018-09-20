package dkarlsso.smartmirror.javafx.actions;

import com.google.inject.Injector;
import dkarlsso.commons.annotation.AnnotationFinder;
import dkarlsso.commons.commandaction.CommandAction;
import dkarlsso.commons.commandaction.CommandActionException;
import dkarlsso.commons.commandaction.CommandInvoker;
import dkarlsso.commons.model.CommonsException;
import dkarlsso.smartmirror.javafx.model.CommandEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;

public class ActionExecutor implements CommandInvoker<CommandEnum> {

    private final Logger LOG = LogManager.getLogger(ActionExecutor.class);

    private final Map<CommandEnum, CommandAction> actionMap = new LinkedHashMap<>();

    public ActionExecutor(final Injector injector) {
        final String packageName = this.getClass().getPackage().getName();

        // Let the magic happen
        try {
            actionMap.putAll(AnnotationFinder.findClassesWithAnnotation(packageName, Action.class, Action::commandName));
            actionMap.putAll(AnnotationFinder.findMethodsWithAnnotation(packageName, Action.class, Action::commandName));
        } catch (CommonsException e) {
            LOG.error("Could not create calls to all annotated methods", e);
        }
        for (final Object object : AnnotationFinder.getAllInstantiatedObjects()) {
            injector.injectMembers(object);
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
