package dkarlsso.commons.motion.motiondetection;

import dkarlsso.commons.model.CommonsException;
import dkarlsso.commons.raspberry.camera.Camera;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class MotionDetectionThread implements Runnable {

    private final MotionAction motionAction;

    private boolean isRunning = true;

    private final Camera camera;

    private final MotionDetector motionDetector = new MotionDetector(100);

    private File oldImage = null;

    private File newImage = null;

    private boolean sensedMotion = false;


    public MotionDetectionThread(final Camera camera, final MotionAction motionAction) {
        this.camera = camera;
        this.motionAction = motionAction;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                oldImage = newImage;
                newImage = camera.takePicture();

                if(oldImage != null && newImage != null) {
                    sensedMotion = motionDetector.senseMotion(oldImage, newImage);
                    if(sensedMotion) {
                        // Sense fingers, motions and stuff
                        motionAction.motionEvent(MotionType.MOTION_DETECTED);
                    }
                    oldImage.delete();
                }

                TimeUnit.SECONDS.sleep(3);
            } catch (CommonsException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
