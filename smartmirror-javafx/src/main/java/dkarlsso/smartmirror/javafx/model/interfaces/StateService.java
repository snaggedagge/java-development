package dkarlsso.smartmirror.javafx.model.interfaces;

import dkarlsso.smartmirror.javafx.model.VoiceApplicationState;
import org.joda.time.DateTime;

public interface StateService {

    VoiceApplicationState getVoiceApplicationState();

    void setVoiceApplicationState(VoiceApplicationState state);

    boolean isRadioPlaying();

    int getVolumeInPercent();

    DateTime getLastActivated();

    void setLastActivated(DateTime lastActivated);

}
