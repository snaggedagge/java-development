package hottub.utils;

public class CustomTimer {

    private long startTime = System.currentTimeMillis()/1000;
    private long elapsedTime = startTime;

    public void reset() {
        startTime = System.currentTimeMillis()/1000;
    }

    public boolean hasTimePassed(long seconds) {
        elapsedTime = System.currentTimeMillis()/1000;

        return (elapsedTime- startTime) > seconds;
    }
}
