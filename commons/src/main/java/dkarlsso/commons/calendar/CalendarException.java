package dkarlsso.commons.calendar;

public class CalendarException extends Exception {

    public CalendarException() {
        super();
    }

    public CalendarException(String msg) {
        super(msg);
    }

    public CalendarException(String msg, Exception e) {
        super(msg,e);
    }
}
