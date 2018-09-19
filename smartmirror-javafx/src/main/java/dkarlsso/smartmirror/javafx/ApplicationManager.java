package dkarlsso.smartmirror.javafx;

import com.google.inject.Inject;
import com.google.inject.Injector;
import dkarlsso.commons.multimedia.MediaPlayer;
import dkarlsso.commons.multimedia.alarm.AlarmClock;
import dkarlsso.commons.multimedia.alarm.AlarmTimeSetting;
import dkarlsso.commons.raspberry.settings.SoundController;
import dkarlsso.commons.commandaction.CommandActionException;
import dkarlsso.commons.speechrecognition.CommandEnum;
import dkarlsso.commons.speechrecognition.CommandInterface;
import dkarlsso.smartmirror.javafx.actions.ActionExecutor;
import dkarlsso.smartmirror.javafx.view.MvcProxy;
import org.joda.time.DateTime;

import java.util.*;
import java.util.function.Function;

public class ApplicationManager {

    @Inject
    private MediaPlayer radioPlayer;

    @Inject
    private SoundController soundController;

    private final ActionExecutor actionExecutor;

    private AlarmClock alarmClock;

    private final Injector injector;

    public ApplicationManager(final Injector injector) {
        this.injector = injector;
        actionExecutor = new ActionExecutor(injector);

        injector.injectMembers(actionExecutor);

    }

    public void test(Function<? super CommandInterface, Void> function) {

        MvcProxy mvcProxy = null;

//
//        ViewInterface viewInterface = mvcProxy.getClass(ViewInterface.class);
//
//        mvcProxy.executeFunction(ViewInterface.class, ViewInterface::showCommand);
//
//
        List<String> list = new ArrayList<>();
        list.stream().sorted(Comparator.comparing(String::length));
        //Comparator.comparing(ViewInterface::getView);
    }


    private Map<Class, Object> classMap;

    <T> T getClass(Class<T> implementationToGet) {
        
        return (T) classMap.get(implementationToGet);
    }

    public void start() {


        List<AlarmTimeSetting> list = Arrays.asList(new AlarmTimeSetting(new DateTime(2018, 9, 17, 6, 45)
                , 30, 60, 100),

                new AlarmTimeSetting(new DateTime(2018, 9, 18, 6, 45)
                        , 30, 60, 100),

                new AlarmTimeSetting(new DateTime(2018, 9, 19, 6, 45)
                        , 30, 60, 100),

                new AlarmTimeSetting(new DateTime(2018, 9, 20, 6, 45)
                        , 30, 60, 100),

                new AlarmTimeSetting(new DateTime(2018, 9, 21, 6, 45)
                        , 30, 60, 100));



        alarmClock = new AlarmClock(soundController, radioPlayer, list);

        new Thread(alarmClock).start();
    }
}
