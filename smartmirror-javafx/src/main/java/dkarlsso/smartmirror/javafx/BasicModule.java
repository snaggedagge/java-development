package dkarlsso.smartmirror.javafx;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import dkarlsso.commons.application.ApplicationUtils;
import dkarlsso.commons.multimedia.MediaPlayer;
import dkarlsso.commons.multimedia.radio.RadioPlayer;
import dkarlsso.commons.raspberry.settings.SoundController;
import dkarlsso.smartmirror.javafx.model.DataService;
import dkarlsso.smartmirror.javafx.model.impl.DefaultDataService;

public class BasicModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DataService.class).toInstance(new DefaultDataService());
        bind(MediaPlayer.class).toInstance(new RadioPlayer(ApplicationUtils.getSubfolder("radiochannels"), ApplicationUtils.getSubfolder("vlc")));
        bind(SoundController.class).toInstance(new SoundController());
    }
}