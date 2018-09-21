package dkarlsso.commons.speech.speechrecognition;

public class SpeechException extends Exception {

    public SpeechException() {
        super();
    }

    public SpeechException(String msg) {
        super(msg);
    }

    public SpeechException(String msg, Exception e) {
        super(msg,e);
    }
}
