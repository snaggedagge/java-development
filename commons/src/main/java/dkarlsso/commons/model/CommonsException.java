package dkarlsso.commons.model;

import dkarlsso.commons.raspberry.exception.CommonsRaspberryException;

public class CommonsException extends CommonsRaspberryException {

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
