package dkarlsso.commons.multimedia;

public interface MediaPlayer {

    void play();

    void stop();

    boolean isPlaying();

    void setPlaying(final boolean shouldPlay);
}
