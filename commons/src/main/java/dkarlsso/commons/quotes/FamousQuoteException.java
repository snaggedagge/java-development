package dkarlsso.commons.quotes;


public class FamousQuoteException extends Exception {

    public FamousQuoteException() {
        super();
    }

    public FamousQuoteException(String msg) {
        super(msg);
    }

    public FamousQuoteException(String msg, Exception e) {
        super(msg,e);
    }
}
