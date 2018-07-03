import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {

    public static Mat result;

    public static void main(String[] args){

        WebCam webCam = new WebCam(new File(""));

        webCam.takePicture("test.png");

        //http://creat-tabu.blogspot.com/2013/08/opencv-python-hand-gesture-recognition.html

        System.out.printf("java.library.path: %s%n",
                System.getProperty("java.library.path"));
        System.loadLibrary("opencv_java341");

        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);



        Mat source = Imgcodecs.imread("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\test.JPG");




        Mat destination = new Mat(source.rows(),source.cols(),source.type());

        //Imgproc.cvtColor(source, destination, Imgproc.COLOR_BGR2GRAY);


        Imgcodecs.imwrite("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\cvt.JPG", destination);

        Mat destination2 = new Mat(source.rows(),source.cols(),source.type());

        Imgproc.GaussianBlur(source, destination2,new Size(45,45), 0);


        Imgcodecs.imwrite("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\gaussian.JPG", destination2);


        Mat destination3 = new Mat(source.rows(),source.cols(),source.type());

        Imgproc.threshold(destination2,destination3, 100, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C);

        Imgcodecs.imwrite("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\thresh.JPG", destination3);


        Size size = destination3.size();

        for(int i=0;i<size.height;i++) {

            for(int j=0;j<size.width;j++) {

                double[] pixel = destination3.get(i,j);

                if(pixel[0] > 240 && pixel[1] > 240 && pixel[2] > 240) {
                    byte[] newPixel = {0,0,0};
                    destination3.put(i,j,newPixel);
                }

           /*     for(double color : pixel) {
                    System.out.println("Col " + i + " Row " + j + "  == " +color);
                }*/

            }

        }

        Imgcodecs.imwrite("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\homemade.JPG", destination3);


        Mat destination4 = new Mat(source.rows(),source.cols(),source.type());

        Imgproc.cvtColor(destination3, destination4, Imgproc.COLOR_BGR2GRAY);

        Imgcodecs.imwrite("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\test2.JPG", destination4);




        Mat destination23 = new Mat(source.rows(),source.cols(),source.type());

        Imgproc.GaussianBlur(destination4, destination23,new Size(45,45), 0);


        Imgcodecs.imwrite("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\gaussian2.JPG", destination23);


        Mat destination33 = new Mat(source.rows(),source.cols(),source.type());

        Imgproc.threshold(destination23,destination33, 10, 255, Imgproc.THRESH_BINARY);

        Imgcodecs.imwrite("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\thresh2.JPG", destination33);



        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();

        Mat hierarchy = new Mat();

        Imgproc.findContours(destination33,contours, hierarchy,Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);


        // TODO SOME MORE THINGS




        System.out.println("Size " + contours.size());


        double maxArea = 0;

        MatOfPoint largestContour = new MatOfPoint();

        MatOfInt convexHull = new MatOfInt();

        for( MatOfPoint matOfPoint : contours) {
            double contourArea = Imgproc.contourArea(matOfPoint);
            if(maxArea < contourArea) {
                maxArea = contourArea;
                largestContour = matOfPoint;
                Imgproc.convexHull(matOfPoint, convexHull);
            }

        }



        result = new Mat(source.rows(),source.cols(),source.type());


        System.out.println("FINGERS " + convexHull.toArray().length);
        Imgproc.drawContours(result, Arrays.asList(largestContour), 0, new Scalar(255,255,255));

        Imgproc.drawContours(result, Arrays.asList(convertIndexesToPoints(largestContour, convexHull)), 0, new Scalar(0,0,255));













        Imgcodecs.imwrite("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\countours.JPG", result);










/*

        // 5) Finding convexity defects

        MatOfInt4 convexityDefects = new MatOfInt4();

        Imgproc.convexityDefects(largestContour, convexHull, convexityDefects);

        System.out.println("mat = " + convexityDefects.dump());


        for(int i=0;i<convexityDefects.cols();i++) {

            System.out.println("mat = " + convexityDefects.col(i).dump());



            for(int j=1;j<convexityDefects.col(i).rows();j+=2) {
                System.out.println("mat2 = " + convexityDefects.col(i).row(j).dump());
                Mat mat = convexityDefects.col(i).row(j-1);
                System.out.println("X " + mat.get(0, 0)[0] + " Y " + mat.get(0, 0)[1]);

                Point point = new Point();
                point.x = mat.get(0, 0)[1];
                point.y = mat.get(0, 0)[0];

                Mat mat2= convexityDefects.col(i).row(j);
                Point point2 = new Point();
                point2.x = mat2.get(0, 0)[1];
                point2.y = mat2.get(0, 0)[0];
                Imgproc.line(result,point,point2, new Scalar(255,0,0),10);

            }




        }

        Imgcodecs.imwrite("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\defects.JPG", result);
*/

  /*

        Imgproc.lin
        convexityDefects.r*/

    }


    public static MatOfPoint convertIndexesToPoints(MatOfPoint contour, MatOfInt indexes) {
        int[] arrIndex = indexes.toArray();
        Point[] arrContour = contour.toArray();
        Point[] arrPoints = new Point[arrIndex.length];

        for (int i=0;i<arrIndex.length;i++) {
            arrPoints[i] = arrContour[arrIndex[i]];
        }

        MatOfPoint hull = new MatOfPoint();
        hull.fromArray(arrPoints);

        //test(arrContour);
        test(arrPoints);

        System.out.println("FINGERS 2 " + arrPoints.length + "  " + arrContour.length + "  " + arrIndex.length);

        return hull;
    }

    private static void test(Point[] array) {

        List<Point> newList = new ArrayList<>();

        for(int i=1;i<array.length;i++) {

            Point point1 = array[i-1];
            Point point2 = array[i];
            if(euclideanDistance(point1, point2) < 0) {

            }
            else {
                newList.add(point1);
            }

        }






        for(Point point : newList) {
            Imgproc.circle(result, point, 10, new Scalar(0,255,255));
        }




        MatOfPoint mop = new MatOfPoint();
        mop.fromList(newList);

        Moments moments = Imgproc.moments(mop);

        Point centroid = new Point();

        centroid.x = moments.get_m10() / moments.get_m00();
        centroid.y = moments.get_m01() / moments.get_m00();

        Imgproc.circle(result, centroid, 30, new Scalar(0,255,255));

        System.out.println(" SIZE " + newList.size());
    }


    public static double euclideanDistance(Point a, Point b){
        double distance = 0.0;
        try{
            if(a != null && b != null){
                double xDiff = a.x - b.x;
                double yDiff = a.y - b.y;
                distance = Math.sqrt(Math.pow(xDiff,2) + Math.pow(yDiff, 2));
            }
        }catch(Exception e){
            System.err.println("Something went wrong in euclideanDistance function in "+e.getMessage());
        }
        return distance;
    }
}
