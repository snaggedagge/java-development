package dkarlsso.commons.motion.motiondetection;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MotionDetector {

//    private Mat resultImage = new Mat();

    private final double maxArea;

    public MotionDetector(final double maxAreaToChange) {
        maxArea = maxAreaToChange;
    }

    public boolean senseMotion(final File oldFile, final File newFile) {
        final Mat matOldImage = preparePicture(
                Imgcodecs.imread(oldFile.getAbsolutePath()));
        final Mat matNewImage = preparePicture(
                Imgcodecs.imread(newFile.getAbsolutePath()));

//        resultImage = matOldImage.clone();
        return getDifferance(matOldImage, matNewImage);
    }

//    public void writeDetectionImageToFile(final File file) {
//        Imgcodecs.imwrite(file.getAbsolutePath(), resultImage);
//    }

    private Mat preparePicture(final Mat originalImage) {
        final Mat preparedPicture = new Mat(originalImage.size(), CvType.CV_8UC1);

        Imgproc.cvtColor(originalImage, preparedPicture, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(preparedPicture, preparedPicture, new Size(3, 3), 0);
        return preparedPicture;
    }

    private boolean getDifferance(final Mat firstImage, final Mat secondImage) {
        final Mat diff_frame = new Mat(firstImage.size(), CvType.CV_8UC1);
        Core.subtract(firstImage, secondImage, diff_frame);

        Imgproc.adaptiveThreshold(diff_frame, diff_frame, 255,
                Imgproc.ADAPTIVE_THRESH_MEAN_C,
                Imgproc.THRESH_BINARY_INV, 5, 2);

        List<Rect> contoursList = detection_contours(diff_frame);
        if (contoursList.size() > 0) {

//            Iterator<Rect> it2 = contoursList.iterator();
//            while (it2.hasNext()) {
//                Rect obj = it2.next();
//                Imgproc.rectangle(resultImage, obj.br(), obj.tl(),
//                        new Scalar(0, 255, 0), 1);
//            }
            return true;
        }
        return false;
    }

    private List<Rect> detection_contours(Mat outmat) {
        Mat v = new Mat();
        Mat vv = outmat.clone();
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Imgproc.findContours(vv, contours, v, Imgproc.RETR_LIST,
                Imgproc.CHAIN_APPROX_SIMPLE);


        Rect r = null;
        ArrayList<Rect> rect_array = new ArrayList<Rect>();

        for (int idx = 0; idx < contours.size(); idx++) {
            Mat contour = contours.get(idx);
            double contourarea = Imgproc.contourArea(contour);
            if (contourarea > maxArea) {
                // maxArea = contourarea;
                r = Imgproc.boundingRect(contours.get(idx));
                rect_array.add(r);

                // Draw image??
                //Imgproc.drawContours(resultImage, contours, idx, new Scalar(0,0, 255));
            }
        }

        v.release();

        return rect_array;

    }
}
