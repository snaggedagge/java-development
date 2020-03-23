package dkarlsso.smartmirror.javafx.model.interfaces.impl;

import dkarlsso.commons.multimedia.MediaPlayer;
import dkarlsso.commons.multimedia.settings.SoundController;
import dkarlsso.smartmirror.javafx.model.interfaces.StateService;
import org.joda.time.DateTime;

public class DefaultStateService implements StateService {

    private final MediaPlayer radioPlayer;

    private final SoundController soundController;

    private static DateTime lastActivated = new DateTime();

    public DefaultStateService(final MediaPlayer radioPlayer, final SoundController soundController) {
        this.radioPlayer = radioPlayer;
        this.soundController = soundController;
    }

    @Override
    public boolean isRadioPlaying() {
        return radioPlayer.isPlaying();
    }

    @Override
    public int getVolumeInPercent() {
        return soundController.getVolumeInPercent();
    }

    @Override
    public DateTime getLastActivated() {
        synchronized (this) {
            return DefaultStateService.lastActivated;
        }
    }

    @Override
    public void setLastActivated(final DateTime lastActivated) {
        synchronized (this) {
            DefaultStateService.lastActivated = lastActivated;
        }
    }
}
