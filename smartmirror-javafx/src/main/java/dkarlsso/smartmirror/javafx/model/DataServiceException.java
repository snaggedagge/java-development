package dkarlsso.smartmirror.javafx.model;

public class DataServiceException extends Exception {

    public DataServiceException() {
        super();
    }

    public DataServiceException(String msg) {
        super(msg);
    }

    public DataServiceException(String msg, Exception e) {
        super(msg,e);
    }
}
