package dkarlsso;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SuppressWarnings("Duplicates")
public class Test2 {


    static {
        try{
            System.out.printf("java.library.path: %s%n",
                    System.getProperty("java.library.path"));
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


    static MatOfInt convexHullMatOfInt = new MatOfInt();
    static List<Point> convexHullPointArrayList = new ArrayList<Point>();
    static MatOfPoint convexHullMatOfPoint = new MatOfPoint();
    static List<MatOfPoint> convexHullMatOfPointArrayList = new ArrayList<MatOfPoint>();
    static MatOfPoint aproximatedContour = new MatOfPoint();

    static List<Point> fingerTips = new ArrayList<Point>();

    static int[] mConvexityDefectsIntArrayList;

    private static final int MAX_FINGER_DEPTH = 200;
    private static final int MIN_FINGER_DEPTH = 100;
    private static final int MAX_FINGER_ANGLE = 60;   // degrees
    private static final int MIN_FINGER_ANGLE = 0;   // degrees

    public static Mat result;

    public static void main(String[] args) {

        //http://creat-tabu.blogspot.com/2013/08/opencv-python-hand-gesture-recognition.html

        Mat source = Imgcodecs.imread("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\Image 5.jpg");





        Size size2 = source.size();

        int maxRed = 100    /2;
        int minRed = 15     /2;

        int maxGreen = 80   /2;
        int minGreen = 15   /2;

        int maxBlue = 70    /2;
        int minBlue = 8     /2;


        for (int i = 0; i < size2.height; i++) {

            for (int j = 0; j < size2.width; j++) {

                double[] pixel = source.get(i, j);


                // AND MIGHT GIVE BETTER PRECISION
                // BLUE, GREEN,  RED
                if((pixel[0] > maxBlue || pixel[0] < minBlue) ||
                        (pixel[1] > maxGreen || pixel[1] < minGreen) ||
                        (pixel[2] > maxRed || pixel[2] < minRed)) {

                    //System.out.println("COLORS " + pixel[0] + " " + pixel[1] + " " + pixel[2]);

                    byte[] newPixel = {0, 0, 0};
                    source.put(i, j, newPixel);
                }

//                if (pixel[0] > 240 && pixel[1] > 240 && pixel[2] > 240) {
//
//                }
//                // BLUE, GREEN,  RED
//                byte[] newPixel = {0, 0, 0};
//                destination3.put(i, j, newPixel);
           /*     for(double color : pixel) {
                    System.out.println("Col " + i + " Row " + j + "  == " +color);
                }*/

            }

        }

        Imgcodecs.imwrite("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\homemade.JPG", source);
        //System.exit(0);

        //Imgproc.cvtColor(source, destination, Imgproc.COLOR_BGR2GRAY);

        Mat destination2 = new Mat(source.rows(), source.cols(), source.type());

        Imgproc.GaussianBlur(source, destination2, new Size(45, 45), 0);


        Imgcodecs.imwrite("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\gaussian.JPG", destination2);


        Mat destination3 = new Mat(source.rows(), source.cols(), source.type());

        Imgproc.threshold(destination2, destination3, 15, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C);

        Imgcodecs.imwrite("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\thresh.JPG", destination3);


        Size size = destination3.size();

        for (int i = 0; i < size.height; i++) {

            for (int j = 0; j < size.width; j++) {

                double[] pixel = destination3.get(i, j);

                if ( pixel[1] > 150 || pixel[2] > 200 || (pixel[0] > 11 && pixel[2] > 11)) {
                    byte[] newPixel = {0, 0, 0};
                    destination3.put(i, j, newPixel);
                }/**/
                else {
                    double[] newPixel = {255, 255, 255};
                    destination3.put(i, j, newPixel);
                }
                // BLUE, GREEN,  RED
                //byte[] newPixel = {0, 0, 0};
                //destination3.put(i, j, newPixel);


            }

        }

        Imgcodecs.imwrite("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\homemade2.JPG", destination3);


        //Mat destination4 = new Mat(source.rows(), source.cols(), source.type());
        //Imgproc.cvtColor(destination3, destination4, Imgproc.COLOR_BGR2GRAY);
        //Imgcodecs.imwrite("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\test2.JPG", destination4);


        //Mat destination23 = new Mat(source.rows(), source.cols(), source.type());
        //Imgproc.GaussianBlur(destination4, destination23, new Size(45, 45), 0);
        //Imgcodecs.imwrite("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\gaussian2.JPG", destination23);


        Mat destination33 = new Mat(source.rows(), source.cols(), source.type());

        Imgproc.threshold(destination3, destination33, 70, 255, Imgproc.THRESH_BINARY);

        Imgcodecs.imwrite("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\thresh2.JPG", destination33);


        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();

        Mat hierarchy = new Mat();

        Imgproc.findContours(destination33, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);


        // TODO SOME MORE THINGS




        double maxArea = 0;

        MatOfPoint largestContour = new MatOfPoint();

        MatOfInt convexHull = new MatOfInt();

        System.out.println("CONTOUR SIZE = " + contours.size());

        for (MatOfPoint matOfPoint : contours) {
            double contourArea = Imgproc.contourArea(matOfPoint);
            if (maxArea < contourArea) {
                maxArea = contourArea;
                largestContour = matOfPoint;
                Imgproc.convexHull(matOfPoint, convexHull);
                aproximatedContour = largestContour;
            }

        }


        result = new Mat(source.rows(), source.cols(), source.type());


        //System.out.println("FINGERS " + convexHull.toArray().length);
        Imgproc.drawContours(result, Arrays.asList(largestContour), 0, new Scalar(255, 255, 255));

        Imgcodecs.imwrite("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\countours1.JPG", result);


        Imgproc.drawContours(result, Arrays.asList(convertIndexesToPoints(largestContour, convexHull)), 0, new Scalar(0, 0, 255));


        Imgcodecs.imwrite("C:\\Users\\Dag Karlsson\\Desktop\\New folder (2)\\countours.JPG", result);












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

    private static MatOfPoint test(Point[] array) {

        List<Point> newList = new ArrayList<>();

        for(int i=1;i<array.length;i++) {
            Point point1 = array[i-1];
            Point point2 = array[i];
            if(euclideanDistance(point1, point2) > 10) {
                newList.add(point1);
            }
        }


        calculateConvexHulls();
        calculateConvexityDefects();
        filterCalculatedPoints();


        return new MatOfPoint(array);
//        for(Point point : newList) {
//            Imgproc.circle(result, point, 10, new Scalar(0,255,255));
//        }
//
//        MatOfPoint mop = new MatOfPoint();
//        mop.fromList(newList);
//
//        Moments moments = Imgproc.moments(mop);
//
//        Point centroid = new Point();
//
//        centroid.x = moments.get_m10() / moments.get_m00();
//        centroid.y = moments.get_m01() / moments.get_m00();
//
//        Imgproc.circle(result, centroid, 30, new Scalar(0,255,255));
//
//        System.out.println(" SIZE " + newList.size());
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


    public static void calculateConvexHulls()
    {
        convexHullMatOfInt = new MatOfInt();
        convexHullPointArrayList = new ArrayList<Point>();
        convexHullMatOfPoint = new MatOfPoint();
        convexHullMatOfPointArrayList = new ArrayList<MatOfPoint>();

        try {
            //Calculate convex hulls
            if(aproximatedContour != null)
            {
                Imgproc.convexHull( aproximatedContour, convexHullMatOfInt, false);

                for(int j=0; j < convexHullMatOfInt.toList().size(); j++)
                    convexHullPointArrayList.add(aproximatedContour.toList().get(convexHullMatOfInt.toList().get(j)));
                convexHullMatOfPoint.fromList(convexHullPointArrayList);
                convexHullMatOfPointArrayList.add(convexHullMatOfPoint);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("Calculate convex hulls failed. + Details below" + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void calculateConvexityDefects()
    {
        MatOfInt4 mConvexityDefectsMatOfInt4 = new MatOfInt4();

        try {
            Imgproc.convexityDefects(aproximatedContour, convexHullMatOfInt, mConvexityDefectsMatOfInt4);

            if(!mConvexityDefectsMatOfInt4.empty())
            {
                mConvexityDefectsIntArrayList = new int[mConvexityDefectsMatOfInt4.toArray().length];
                mConvexityDefectsIntArrayList = mConvexityDefectsMatOfInt4.toArray();
            }
        } catch (Exception e) {
            System.out.println("Calculate convex hulls failed. + Details below" + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void filterCalculatedPoints()
    {
        ArrayList<Point> tipPts = new ArrayList<Point>();
        ArrayList<Point> foldPts = new ArrayList<Point>();
        ArrayList<Integer> depths = new ArrayList<Integer>();

        fingerTips = new ArrayList<Point>();

        for (int i = 0; i < mConvexityDefectsIntArrayList.length/4; i++)
        {
            tipPts.add(aproximatedContour.toList().get(mConvexityDefectsIntArrayList[4*i]));
            tipPts.add(aproximatedContour.toList().get(mConvexityDefectsIntArrayList[4*i+1]));
            foldPts.add(aproximatedContour.toList().get(mConvexityDefectsIntArrayList[4*i+2]));
            depths.add(mConvexityDefectsIntArrayList[4*i+3]);
        }


        ArrayList<Point> tipPts2 = new ArrayList<Point>();
        ArrayList<Point> foldPts2 = new ArrayList<Point>();
        ArrayList<Integer> depths2 = new ArrayList<Integer>();

        for(int i=1;i<tipPts.size();i++) {
            Point point1 = tipPts.get(i-1);
            Point point2 = tipPts.get(i);
            if(euclideanDistance(point1, point2) > 10) {
                tipPts2.add(point1);
            }
        }
        for(int i=1;i<foldPts.size();i++) {
            Point point1 = foldPts.get(i-1);
            Point point2 = foldPts.get(i);
            if(euclideanDistance(point1, point2) > 10) {
                foldPts2.add(point1);
            }
        }




        int numPoints = foldPts2.size() > tipPts2.size() ? tipPts2.size() : foldPts2.size();
        for (int i=0; i < numPoints; i++) {



            if ((depths.get(i).intValue()) < MIN_FINGER_DEPTH)
                continue;
            if ((depths.get(i).intValue()) > MAX_FINGER_DEPTH)
                continue;


            // look at fold points on either side of a tip
            int pdx = (i == 0) ? (numPoints-1) : (i - 1);
            int sdx = (i == numPoints-1) ? 0 : (i + 1);

            int angle = angleBetween(tipPts.get(i), foldPts2.get(pdx), foldPts2.get(sdx));

            System.out.println("ANGLE : " + angle + " DEPTH " + depths.get(i).intValue());

            if (angle >= MAX_FINGER_ANGLE)   // angle between finger and folds too wide
                continue;
            if (angle < MIN_FINGER_ANGLE)   // angle between finger and folds too wide
                continue;

            // this point is probably a fingertip, so add to list
            fingerTips.add(tipPts2.get(i));
        }

        for(Point point : fingerTips){
            Imgproc.circle(result, point, 20, new Scalar(255,0,255));
        }

        for(Point point : tipPts2){
            Imgproc.circle(result, point, 10, new Scalar(0,255,255));
        }
//        for(Point point : tipPts2){
//            Imgproc.circle(result, point, 10, new Scalar(255,255,255));
//        }

        System.out.println("ULTIMATE NUMBER OF FINGERS: " + fingerTips.size());


    }



    // calculate the angle between the tip and its neighboring folds
    // (in integer degrees)
    private static int angleBetween(Point tip, Point next, Point prev)
    {
        return Math.abs( (int)Math.round(
                Math.toDegrees(
                        Math.atan2(next.x - tip.x, next.y - tip.y) -
                                Math.atan2(prev.x - tip.x, prev.y - tip.y)) ));
    }

}
