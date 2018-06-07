package dkarlsso.smartmirror.javafx;

import com.google.inject.AbstractModule;
import dkarlsso.smartmirror.javafx.model.DataService;
import dkarlsso.smartmirror.javafx.model.impl.DefaultDataService;

public class BasicModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DataService.class).to(DefaultDataService.class);
    }
}