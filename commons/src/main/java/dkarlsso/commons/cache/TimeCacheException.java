package dkarlsso.commons.cache;

public class TimeCacheException extends Exception {

    public TimeCacheException() {
        super();
    }

    public TimeCacheException(String msg) {
        super(msg);
    }

    public TimeCacheException(String msg, Exception e) {
        super(msg,e);
    }

}
