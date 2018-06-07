package dkarlsso.commons.raspberry.camera;

public class RPICameraException extends Exception {

    public RPICameraException() {
        super();
    }

    public RPICameraException(String msg) {
        super(msg);
    }

    public RPICameraException(String msg, Exception e) {
        super(msg,e);
    }
}
