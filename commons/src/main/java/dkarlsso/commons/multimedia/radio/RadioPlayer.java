package dkarlsso.commons.multimedia.radio;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import dkarlsso.commons.multimedia.MediaPlayer;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import java.io.File;

public class RadioPlayer implements MediaPlayer {

    private final File radioplayerRootFolder;

    private final EmbeddedMediaPlayer mediaPlayer;

    private final MediaPlayerFactory factory;

    private boolean isPlaying = false;

    public RadioPlayer(final File radioplayerRootFolder, final File vlcFolder) {

        this.radioplayerRootFolder = radioplayerRootFolder;
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), vlcFolder.getAbsolutePath());
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
        factory = new MediaPlayerFactory();
        mediaPlayer = factory.newEmbeddedMediaPlayer();
        factory.release();
    }

    @Override
    public void play() {
        synchronized (this) {
            if (!isPlaying) {
                isPlaying = true;
                //final File x3mFile = new File(getClass().getClassLoader().getResource("x3m.m3u8").toURI());
                mediaPlayer.playMedia(radioplayerRootFolder.getAbsolutePath() + File.separator + "x3m.m3u8");
            }
        }
    }

    @Override
    public void stop() {
        synchronized (this) {
            if (isPlaying) {
                isPlaying = false;
                mediaPlayer.stop();

            }
        }
    }

    @Override
    public boolean isPlaying() {
        synchronized (this) {
            return isPlaying;
        }
    }

    @Override
    public void setPlaying(boolean shouldPlay) {
        if (shouldPlay) {
            play();
        }
        else {
            stop();
        }
    }
}
