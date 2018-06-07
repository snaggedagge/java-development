package dkarlsso.commons.raspberry.settings;

import dkarlsso.commons.model.CommonsException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SoundController {

    private static final String SOUND_PROGRAM = "amixer";

    public void setVolume(int volumePercentage) throws CommonsException {
        try {
            final List<String> command = new ArrayList<String>();
            command.add(SOUND_PROGRAM);
            command.add("-D");
            command.add("pulse");
            command.add("sset");
            command.add("Master");
            command.add(volumePercentage + "%");

            final ProcessBuilder builder = new ProcessBuilder(command);
            final Process process = builder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new CommonsException(e.getMessage(), e);
        }
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
