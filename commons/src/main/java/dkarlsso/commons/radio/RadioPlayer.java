package dkarlsso.commons.radio;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import java.io.File;

public class RadioPlayer {

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

    public void play() {
        isPlaying = true;
        //final File x3mFile = new File(getClass().getClassLoader().getResource("x3m.m3u8").toURI());

        mediaPlayer.playMedia(radioplayerRootFolder.getAbsolutePath() + File.separator + "x3m.m3u8");
    }

    public void stop() {
        isPlaying = false;
        mediaPlayer.stop();
    }

    public boolean isPlaying() {
        return isPlaying;
    }
}
