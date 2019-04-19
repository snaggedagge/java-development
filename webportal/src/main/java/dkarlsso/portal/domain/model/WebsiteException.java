package dkarlsso.portal.domain.model;

public class WebsiteException extends Exception {
    public WebsiteException() {
    }

    public WebsiteException(String message) {
        super(message);
    }

    public WebsiteException(String message, Throwable cause) {
        super(message, cause);
    }
}
