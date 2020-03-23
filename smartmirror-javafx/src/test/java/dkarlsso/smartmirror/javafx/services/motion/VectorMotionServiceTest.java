package dkarlsso.smartmirror.javafx.services.motion;

import dkarlsso.smartmirror.javafx.model.MotionUpdate;
import dkarlsso.smartmirror.javafx.model.MotionUpdateRequest;
import dkarlsso.smartmirror.javafx.model.MovementDirection;
import dkarlsso.smartmirror.javafx.services.MotionService;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class VectorMotionServiceTest {

    private final MotionService vectorMotionService = new DirectionMotionService();

    @Test
    public void testFastRightSwipeIsOnlyCalledOnce() {
        testCase(5, 1000, true);
    }

    @Test
    public void testReallyFastRightSwipeIsOnlyCalledOnce() {
        testCase(3, 600, true);
    }

    @Test
    public void testSlowRightSwipeIsOnlyCalledOnce() {
        testCase(10, 1500, true);
    }

    @Test
    public void testReallyFastLeftSwipeIsOnlyCalledOnce() {
        testCase(3, 600, false);
    }

    @Test
    public void testFastLeftSwipeIsOnlyCalledOnce() {
        testCase(5, 1000, false);
    }

    @Test
    public void testSlowLeftSwipeIsOnlyCalledOnce() {
        testCase(10, 1500, false);
    }

    private void testCase(int numberOfPoints, int millisecondsSwipeDuration, final boolean rightDirection) {

        // Sleep due to instant initiated in class creation..
        try {
            Thread.sleep(1600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        AtomicInteger counter = new AtomicInteger();
        AtomicBoolean hasBeenApprovedOnce = new AtomicBoolean(false);
        generateMotion(numberOfPoints, rightDirection).forEach(motionUpdateRequest -> {
            System.out.println("X " + motionUpdateRequest.getMotionUpdates().get(0).getX());
            vectorMotionService.notifyHandMotion(motionUpdateRequest, (event) -> {
                System.out.println(counter.get());
                Assert.assertFalse(hasBeenApprovedOnce.get());
                Assert.assertEquals(rightDirection ? MovementDirection.RIGHT : MovementDirection.LEFT, event.getMovementDirection());
                hasBeenApprovedOnce.set(true);
            });

            counter.getAndIncrement();
            try {
                Thread.sleep(millisecondsSwipeDuration/ numberOfPoints);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Assert.assertTrue(hasBeenApprovedOnce.get());
    }



    // Around 180 Y position in real life for swipe. 5 - 10 points in real lif it seems
    private List<MotionUpdateRequest> generateMotion(int numberOfPoints, final boolean rightDirection) {
        final List<MotionUpdateRequest> motionUpdateRequests = new ArrayList<>();

        // Images are inverted so left is right and right is left -_- Up is still up though
        if (!rightDirection) {
            for(int i = 0; i < numberOfPoints; i++) {
                motionUpdateRequests.add(create(numberOfPoints, i));
            }
        }
        else {
            for(int i = numberOfPoints; i > -1; i--) {
                motionUpdateRequests.add(create(numberOfPoints, i));
            }
        }



        return motionUpdateRequests;
    }

    private MotionUpdateRequest create(int numberOfPoints, int i) {
        final MotionUpdateRequest motionUpdateRequest = new MotionUpdateRequest();

        motionUpdateRequest.setMaxX(320);
        motionUpdateRequest.setMaxY(240);
        motionUpdateRequest.setMinX(0);
        motionUpdateRequest.setMinY(0);
        double averagePointDistance = (double)motionUpdateRequest.getMaxX()/numberOfPoints;
        motionUpdateRequest.setMotionUpdates(Arrays.asList(new MotionUpdate(new double[]{averagePointDistance * i, 180}, 0.80)));
        return motionUpdateRequest;
    }
}
