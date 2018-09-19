package dkarlsso.commons.commandaction;

public interface CommandInvoker<T extends Enum> {

    void executeCommand(final T commandEnum) throws CommandActionException;

}
