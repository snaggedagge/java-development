package dkarlsso.commons.commandaction;

public class UnknownCommandException extends Exception {

    public UnknownCommandException() {
    }

    public UnknownCommandException(String message) {
        super(message);
    }

    public UnknownCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
