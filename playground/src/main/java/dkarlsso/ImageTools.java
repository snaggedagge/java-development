package dkarlsso;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class ImageTools {


    public static Mat applyGaussianBlur(final Mat originalImage, final int width, final int height){
        final Mat result = new Mat(originalImage.rows(), originalImage.cols(), originalImage.type());
        Imgproc.GaussianBlur(originalImage, result, new Size(width, height), 0);
        return result;
    }

    public static Mat convertColor(final Mat originalImage, final int imgprocColor) {
        final Mat result = new Mat(originalImage.rows(), originalImage.cols(), originalImage.type());
        Imgproc.cvtColor(originalImage, result, imgprocColor);
        return result;
    }



}
