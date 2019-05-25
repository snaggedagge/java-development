package dkarlsso.smartmirror.javafx.module;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import dkarlsso.commons.raspberry.screen.EmptyScreenHandler;
import dkarlsso.commons.raspberry.screen.ScreenHandler;
import dkarlsso.smartmirror.javafx.model.application.ApplicationUtils;
import dkarlsso.commons.container.BasicContainer;
import dkarlsso.commons.multimedia.MediaPlayer;
import dkarlsso.commons.multimedia.radio.RadioPlayer;
import dkarlsso.commons.raspberry.OSHelper;
import dkarlsso.commons.raspberry.enums.GPIOPins;
import dkarlsso.commons.raspberry.relay.MockRelay;
import dkarlsso.commons.raspberry.relay.OptoRelay;
import dkarlsso.commons.raspberry.relay.interfaces.RelayInterface;
import dkarlsso.commons.raspberry.screen.DefaultScreenHandler;
import dkarlsso.commons.multimedia.settings.SoundController;
import dkarlsso.smartmirror.javafx.model.interfaces.DataService;
import dkarlsso.smartmirror.javafx.model.interfaces.StateService;
import dkarlsso.smartmirror.javafx.model.interfaces.impl.DefaultDataService;
import dkarlsso.smartmirror.javafx.model.interfaces.impl.DefaultStateService;
import dkarlsso.smartmirror.javafx.view.ViewBuilder;
import dkarlsso.smartmirror.javafx.view.ViewControllerInterface;
import dkarlsso.smartmirror.javafx.view.impl.HeavyViewBuilder;

public class SmartMirrorModule extends AbstractModule {

    private final ViewControllerInterface viewControllerInterface;

    public SmartMirrorModule(final ViewControllerInterface viewControllerInterface) {
        this.viewControllerInterface = viewControllerInterface;
    }

    @Override
    protected void configure() {
        final MediaPlayer radioPlayer = new RadioPlayer(ApplicationUtils.getSubfolder("radiochannels"), ApplicationUtils.getSubfolder("vlc"));
        bind(MediaPlayer.class).toInstance(radioPlayer);


        // TODO: Remove this, should somehow get volume status from alexa/internal OS state
        final SoundController soundController = new SoundController();
        bind(SoundController.class).toInstance(soundController);

        final DataService dataService = new DefaultDataService();
        bind(DataService.class).toInstance(dataService);

        final BasicContainer<ViewBuilder> viewBuilder = new BasicContainer<>();
        viewBuilder.set(new HeavyViewBuilder(dataService));
        bind(new TypeLiteral<BasicContainer<ViewBuilder>>(){}).toInstance(viewBuilder);

        bind(StateService.class).toInstance(new DefaultStateService(radioPlayer, soundController));

        bind(ViewControllerInterface.class).toInstance(viewControllerInterface);

        final RelayInterface lightsRelay;
        final ScreenHandler screenHandler;
        if (OSHelper.isRaspberryPi()) {
            screenHandler = new DefaultScreenHandler();
            //lightsRelay = new MockRelay();
            // TODO: THIS IS NOW MOCKED
            lightsRelay = new OptoRelay(GPIOPins.GPIO14_TXDO);
            lightsRelay.setHigh();
        }
        else {
            screenHandler = new EmptyScreenHandler();
            lightsRelay = new MockRelay();
        }
        bind(ScreenHandler.class).toInstance(screenHandler);
        bind(RelayInterface.class).annotatedWith(Names.named("LightsRelay")).toInstance(lightsRelay);
    }
}