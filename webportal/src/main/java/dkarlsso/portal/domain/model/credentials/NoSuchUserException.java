package dkarlsso.portal.domain.model.credentials;

public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException() {
    }

    public NoSuchUserException(String message) {
        super(message);
    }
}
