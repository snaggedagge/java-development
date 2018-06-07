package dkarlsso.commons.model;

public class CommonsException extends Exception {

    public CommonsException() {
        super();
    }

    public CommonsException(String msg) {
        super(msg);
    }

    public CommonsException(String msg, Exception e) {
        super(msg,e);
    }

}
