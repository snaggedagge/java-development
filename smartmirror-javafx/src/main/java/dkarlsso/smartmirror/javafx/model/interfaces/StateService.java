package dkarlsso.smartmirror.javafx.model.interfaces;

import dkarlsso.smartmirror.javafx.model.VoiceApplicationState;

public interface StateService {

    VoiceApplicationState getVoiceApplicationState();

    void setVoiceApplicationState(VoiceApplicationState state);

    boolean isRadioPlaying();

    int getVolumeInPercent();

}
