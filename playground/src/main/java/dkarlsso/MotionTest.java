package dkarlsso;

import org.opencv.core.Core;

import java.io.File;

public class MotionTest {

    static {
        try{
            System.loadLibrary("opencv_java341");
        }
        catch (Throwable e) {
            System.out.println("Could not open lib opencv_java341");
        }

        try{
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        }
        catch (Throwable e) {
            System.out.println("Could not open lib");
        }
    }

    private static MotionDetector motionDetector = new MotionDetector();


    public static void main(String[] args) {

//        boolean motion = motionDetector.senseMotion(new File("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\bilder 2\\Image 4.jpg"),
//                new File("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\bilder 2\\test.jpg"));
//
//        motionDetector.writeDetectionImageToFile(new File("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\bilder 2\\motion.JPG"));
//
//
//        System.out.println("Motion detected: " + motion);


        FingerDetector fingerDetector = new FingerDetector();

        for(int i=5;i<9;i++) {
            fingerDetector.countFingers("Image " + i);
        }



    }

}
