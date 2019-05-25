package dkarlsso.smartmirror.javafx;

import com.google.inject.Inject;
import com.google.inject.Injector;
import dkarlsso.commons.annotation.AnnotationFinder;
import dkarlsso.commons.commandaction.CommandActionException;
import dkarlsso.commons.commandaction.CommandInvoker;
import dkarlsso.commons.model.CommonsException;
import dkarlsso.commons.raspberry.OSHelper;
import dkarlsso.commons.raspberry.screen.ScreenHandler;
import dkarlsso.commons.raspberry.screen.ScreenHandlerException;
import dkarlsso.smartmirror.javafx.actions.ActionExecutor;
import dkarlsso.smartmirror.javafx.model.CommandEnum;
import dkarlsso.smartmirror.javafx.model.VoiceApplicationState;
import dkarlsso.smartmirror.javafx.model.interfaces.StateService;
import dkarlsso.smartmirror.javafx.threads.RunnableService;
import dkarlsso.smartmirror.javafx.view.ViewControllerInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ApplicationManager implements CommandInvoker<CommandEnum>, Runnable {

    private final Logger LOG = LogManager.getLogger(ApplicationManager.class);

    private final ActionExecutor actionExecutor;

    @Inject
    private ViewControllerInterface viewInterface;

    @Inject
    private StateService stateService;

    @Inject
    private ScreenHandler screenHandler;

    private final static List<CommandEnum> commandQuenue = Collections.synchronizedList(new ArrayList<>());

//    private MotionDetectionThread motionDetectionThread;


//    static {
//        // TODO: Fix this to something decent
//        try {
//            System.load("/usr/local/share/OpenCV/java/libopencv_java345.so");
//        } catch (Throwable e) {
//            System.err.println("Native code library failed to load.\n" + e);
//        }
//    }

    public ApplicationManager(final Injector injector) {
        actionExecutor = new ActionExecutor(injector);
        try {
            final List<Runnable> listOfThreads = AnnotationFinder.findClassesWithAnnotation(getClass().getPackage().getName(), RunnableService.class);

            for(final Runnable runnable : listOfThreads) {
                injector.injectMembers(runnable);
                new Thread(runnable).start();
            }

        } catch (final CommonsException e) {
            LOG.error(e.getMessage(), e);
        }
    }


    @Override
    public void run() {
        viewInterface.initAnimation(5000, null);

//        /** TODO: Should be moved to {@link RunnableService*/
//        try {
//            ThreadSafeCameraSingleton.setCamera(new WebCam(ApplicationUtils.getSubfolder("pictures")));
//            motionDetectionThread = new MotionDetectionThread(ThreadSafeCameraSingleton.getCamera(), this::motionEvent);
//            new Thread(motionDetectionThread).start();
//        } catch (CommonsRaspberryException e) {
//            LOG.error("Could not start motion detection " + e.getMessage(), e);
//        }

        while (true) {
            try {
                // TODO: Eventhandling??
                if (screenHandler.isScreenActive()) {
                    Thread.sleep(200);
                }
                else {
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                LOG.error("Dealing with action ", e);
            }
            final Iterator<CommandEnum> it = commandQuenue.iterator();
            while (it.hasNext()) {
                final CommandEnum command = it.next();
                LOG.debug("Dealing with action " + command);
                execute(command);
                it.remove();
            }
        }
    }

    @Override
    public void executeCommand(final CommandEnum commandEnum) {
        LOG.debug("Adding action " + commandEnum);
        commandQuenue.add(commandEnum);
    }

    private void execute(final CommandEnum commandEnum) {
        try {
            LOG.info("Function was called: " + commandEnum.prettyName());
            stateService.setLastActivated(new DateTime());
            actionExecutor.executeCommand(commandEnum);
            viewInterface.displayStandardView(commandEnum.prettyName());

            if (OSHelper.isRaspberryPi()) { // TODO: should be injected with something else
                screenHandler.setScreenPowerMode(true);
            }
        }
        catch (final CommandActionException e) {
            LOG.error("Error occured while calling command " + commandEnum.prettyName());
        } catch (ScreenHandlerException e) {
            LOG.error("Could not change screen status", e);
        }
    }

//
//    public void motionEvent(MotionType motionType) {
//        try {
//            if (!screenHandler.isScreenActive()) {
//                screenHandler.setScreenPowerMode(true);
//            }
//
//        } catch (ScreenHandlerException e) {
//            LOG.error("Could not turn on screen");
//        }
//        LOG.error("Motion detected!!");
//
//        viewInterface.showMessage("Hello you handsome devil!", 2);
//    }
}
