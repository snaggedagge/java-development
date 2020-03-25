package dkarlsso.commons.multimedia.settings;

import dkarlsso.commons.model.CommonsException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SoundController {


    // RUN alsamixer, get card info from F2
    // amixer -c 1 set PCM 15%

    private static final String SOUND_PROGRAM = "amixer";

    private int currentSoundVolume = 49;
    // amixer -c 0 -- sset PCM playback 10% For USB
    // amixer -D pulse sset Master 40% Works only on standard device it seems --> Hdmi

    public void setVolume(int volumeInPercentage) throws CommonsException {
        synchronized (this) {
            try {
                currentSoundVolume = volumeInPercentage;

                final ProcessBuilder builder = new ProcessBuilder(createCommand(volumeInPercentage));
                final Process process = builder.start();
                process.waitFor();
            } catch (IOException | InterruptedException e) {
                throw new CommonsException("Error while changing volume: " + e.getMessage(), e);
            }
        }
    }

    public void increaseVolume(int stepInPercent) throws CommonsException {
        synchronized(this) {
            currentSoundVolume = (currentSoundVolume + stepInPercent) % 100;
            setVolume(currentSoundVolume);
        }
    }

    public int getVolumeInPercent() {
        synchronized (this) {
            return currentSoundVolume;
        }
    }

    private List<String> createCommand(int volumeInPercentage) {
        final List<String> command = new ArrayList<String>();
        command.add(SOUND_PROGRAM);
        command.add("-c");

        // -c 1 set for orange
        // -c 0 sset rpi?

        command.add("2");
        command.add("sset");
        command.add("PCM");
        command.add("playback");
        command.add(volumeInPercentage + "%");
        return command;
    }

    /*
    To Mute:

amixer -D pulse sset Master mute

To Unmute:

amixer -D pulse sset Master unmute

To turn volume up 5%:

amixer -D pulse sset Master 5%+

To turn volume down 5%:

amixer -D pulse sset Master 5%-
     */

}
