package dkarlsso.smartmirror.javafx.model.interfaces.impl;

import dkarlsso.commons.multimedia.MediaPlayer;
import dkarlsso.commons.raspberry.settings.SoundController;
import dkarlsso.smartmirror.javafx.model.interfaces.StateService;
import dkarlsso.smartmirror.javafx.model.VoiceApplicationState;

public class DefaultStateService implements StateService {

    private final MediaPlayer radioPlayer;

    private final SoundController soundController;

    public DefaultStateService(final MediaPlayer radioPlayer, final SoundController soundController) {
        this.radioPlayer = radioPlayer;
        this.soundController = soundController;
    }

    private VoiceApplicationState voiceApplicationState = VoiceApplicationState.UNLOCKED;

    @Override
    public VoiceApplicationState getVoiceApplicationState() {
        synchronized (this) {
            return voiceApplicationState;
        }
    }

    @Override
    public void setVoiceApplicationState(VoiceApplicationState state) {
        synchronized (this) {
            voiceApplicationState = state;
        }
    }

    @Override
    public boolean isRadioPlaying() {
        return radioPlayer.isPlaying();
    }

    @Override
    public int getVolumeInPercent() {
        return soundController.getVolumeInPercent();
    }
}
