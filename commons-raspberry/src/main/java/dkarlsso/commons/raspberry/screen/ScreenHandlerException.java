package dkarlsso.commons.raspberry.screen;

public class ScreenHandlerException extends Exception {
    public ScreenHandlerException() {
        super();
    }

    public ScreenHandlerException(String msg) {
        super(msg);
    }

    public ScreenHandlerException(String msg, Exception e) {
        super(msg,e);
    }
}
