package dkarlsso.commons.commandaction;

public class CommandActionException extends Exception{

    public CommandActionException() {
        super();
    }

    public CommandActionException(final String msg) {
        super(msg);
    }

    public CommandActionException(final String msg, final Exception e) {
        super(msg,e);
    }

}
