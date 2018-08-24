package dkarlsso;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.*;
import java.io.File;

@SuppressWarnings("Duplicates")
public class FingerDetector {


    public int countFingers(final String fileName) {

        final Mat background = Imgcodecs.imread("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\bilder 2\\Image 4.jpg");

        final Mat originalImage = Imgcodecs.imread("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\bilder 2\\" + fileName + ".jpg");

        final Mat preparedPicture = new Mat(originalImage.size(), originalImage.type());

//
//        //applyHomemadeFilter(originalImage);
//        //applyHomemadeFilter(background);
//
//        writeImageToFile(originalImage, new File("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\bilder 2\\res\\"+ fileName +"homemadeFilter.jpg"));
//
//        Imgproc.cvtColor(originalImage, originalImage, Imgproc.COLOR_BGR2GRAY);
//
//        Imgproc.cvtColor(background, background, Imgproc.COLOR_BGR2GRAY);
//        Core.subtract(background, originalImage, preparedPicture);
//        writeImageToFile(preparedPicture, new File("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\bilder 2\\res\\"+ fileName +"subtract.jpg"));
//
//
//        writeImageToFile(preparedPicture, new File("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\bilder 2\\res\\"+ fileName + " cvt.jpg"));


        //Imgproc.cvtColor(originalImage, originalImage, Imgproc.COLOR_BGR2HSV);

        //Imgproc.GaussianBlur(originalImage,originalImage, new Size(25,25), 0);
        //Imgproc.GaussianBlur(background,background, new Size(25,25), 0);

        Core.absdiff(background, originalImage, preparedPicture);
        Core.subtract(preparedPicture, originalImage, preparedPicture);
        //Core.add(preparedPicture, new Scalar(0,50,50), preparedPicture);
        //Imgproc.cvtColor(preparedPicture,preparedPicture, Imgproc.COLOR_BGR2GRAY);
        //Core.add(background, originalImage, preparedPicture);
        writeImageToFile(preparedPicture, new File("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\bilder 2\\res\\"+ fileName +"subtract.jpg"));



        final Mat cutoffOriginalContent = new Mat(originalImage.size(), originalImage.type());

        Size size2 = originalImage.size();
        for (int i = 0; i < size2.height; i++) {
            for (int j = 0; j < size2.width; j++) {
                double[] pixelBackground = background.get(i, j);
                double[] pixelImage = originalImage.get(i, j);
                // AND MIGHT GIVE BETTER PRECISION
                // BLUE, GREEN,  RED

                if((pixelBackground[0] > pixelImage[0]-10 && pixelBackground[0] < pixelImage[0]+10) &&
                        (pixelBackground[1] > pixelImage[1]-10 && pixelBackground[1] < pixelImage[1]+10) &&
                        pixelBackground[2] > pixelImage[2]-10 && pixelBackground[2] < pixelImage[2]+10) {
                    byte[] newPixel = {0, 0, 0};
                    originalImage.put(i, j ,newPixel);
                }

//                if(pixel[0] > 1 || pixel[1] > 1 || pixel[2] > 1) {
////                    byte[] newPixel = {127, 127, 127};
////                    image.put(i, j, newPixel);
//                } else {
//                    byte[] newPixel = {127, 127, 127};
//                    originalImage.put(i, j ,newPixel);
//                }
            }

        }

        //Core.multiply(background,new Scalar(1.2,1.2,1.2),background);

//        Core.subtract(background, originalImage, originalImage);

        Imgproc.cvtColor(originalImage,originalImage, Imgproc.COLOR_BGR2GRAY);


//        for (int i = 0; i < size2.height; i++) {
//            for (int j = 0; j < size2.width; j++) {
//                double[] pixelImage = originalImage.get(i, j);
//                // AND MIGHT GIVE BETTER PRECISION
//                // BLUE, GREEN,  RED
//                if (pixelImage[0] > 50 && pixelImage[1] < 250 && pixelImage[2] < 250) {
//                    byte[] newPixel = {0, 0, 0};
//                    originalImage.put(i, j, newPixel);
//                }
//
//            }
//        }

//        Core.inRange(originalImage, new Scalar(50,50,50), new Scalar(200,100,100),originalImage);
        writeImageToFile(originalImage, new File("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\bilder 2\\res\\"+ fileName +"original.jpg"));


//
//        Core.bitwise_and();

//        Imgproc.threshold(preparedPicture,preparedPicture, 10, 255, Imgproc.THRESH_OTSU);
//
//        Imgproc.GaussianBlur(preparedPicture,preparedPicture, new Size(25,25), 0);
//
//        writeImageToFile(preparedPicture, new File("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\bilder 2\\res\\"+ fileName + "thresh.jpg"));


//
//
//        Imgproc.threshold(originalImage,originalImage, 10, 255, Imgproc.THRESH_OTSU);
//
//        Imgproc.GaussianBlur(originalImage,originalImage, new Size(25,25), 0);
//
//        writeImageToFile(originalImage, new File("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\bilder 2\\res\\"+ fileName + "thresh.jpg"));

        return 5;
    }



    private void applyHomemadeFilter(final Mat image) {
        Size size2 = image.size();

        int maxRed = 100    /2;
        int minRed = 15     /2;

        int maxGreen = 80   /2;
        int minGreen = 15   /2;

        int maxBlue = 70    /2;
        int minBlue = 8     /2;

        for (int i = 0; i < size2.height; i++) {
            for (int j = 0; j < size2.width; j++) {
                double[] pixel = image.get(i, j);
                // AND MIGHT GIVE BETTER PRECISION
                // BLUE, GREEN,  RED
                if((pixel[0] > maxBlue || pixel[0] < minBlue) &&
                        (pixel[1] > maxGreen || pixel[1] < minGreen) &&
                        (pixel[2] > maxRed || pixel[2] < minRed)) {
                    byte[] newPixel = {127, 127, 127};
                    image.put(i, j, newPixel);
                }
            }

        }
    }

    private void writeImageToFile(final Mat image, final File destination) {
        Imgcodecs.imwrite(destination.getAbsolutePath(), image);
    }

}
