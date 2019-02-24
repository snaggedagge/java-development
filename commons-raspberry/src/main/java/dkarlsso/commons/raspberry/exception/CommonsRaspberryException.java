package dkarlsso.commons.raspberry.exception;

public class CommonsRaspberryException extends Exception {

    public CommonsRaspberryException() {
    }

    public CommonsRaspberryException(String message) {
        super(message);
    }

    public CommonsRaspberryException(String message, Throwable cause) {
        super(message, cause);
    }
}
