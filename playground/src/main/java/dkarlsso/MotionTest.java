package dkarlsso;

import dkarlsso.commons.model.CommonsException;
import dkarlsso.commons.motion.motiondetection.MotionAction;
import dkarlsso.commons.motion.motiondetection.MotionDetectionThread;
import dkarlsso.commons.motion.motiondetection.MotionType;
import dkarlsso.commons.raspberry.camera.impl.ThreadSafeCameraSingleton;
import dkarlsso.commons.raspberry.camera.impl.WebCam;

import java.io.File;

public class MotionTest {

    static {


        try {
            System.load("/usr/local/share/OpenCV/java/libopencv_java345.so");
        } catch (Throwable e) {
            System.err.println("Native code library failed to load.\n" + e);
        }
//
//        try{
//            System.loadLibrary("opencv_java345");
//        }
//        catch (Throwable e) {
//            System.out.println("Could not open lib opencv_java345");
//        }
//
//        try{
//            System.loadLibrary("libopencv_java345");
//        }
//        catch (Throwable e) {
//            System.out.println("Could not open lib libopencv_java345");
//        }
//
//        try{
//            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        }
//        catch (Throwable e) {
//            System.out.println("Could not open lib");
//        }
    }

    private static MotionDetector motionDetector = new MotionDetector();


    public static void main(String[] args) {

        final File file = new File("/home/pi/motionTest");

        if (!file.exists()) {
            file.mkdir();
        }

        try {
            ThreadSafeCameraSingleton.setCamera(new WebCam(file));
            new Thread(new MotionDetectionThread(ThreadSafeCameraSingleton.getCamera(), MotionTest::motionEvent)).start();
        } catch (CommonsException e) {
            System.out.println("Could not start motion detection " + e.getMessage());
        }

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//
////        boolean motion = motionDetector.senseMotion(new File("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\bilder 2\\Image 4.jpg"),
////                new File("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\bilder 2\\test.jpg"));
////
////        motionDetector.writeDetectionImageToFile(new File("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\bilder 2\\motion.JPG"));
////
////
////        System.out.println("Motion detected: " + motion);
//
//
//        FingerDetector fingerDetector = new FingerDetector();
//
//        for(int i=5;i<9;i++) {
//            fingerDetector.countFingers("Image " + i);
//        }
    }

    public static void motionEvent(MotionType motionType) {
        System.out.println("Detected motion!!");
    }
}
