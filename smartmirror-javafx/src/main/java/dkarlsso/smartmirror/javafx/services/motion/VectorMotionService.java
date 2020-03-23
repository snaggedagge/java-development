package dkarlsso.smartmirror.javafx.services.motion;

import dkarlsso.smartmirror.javafx.model.*;
import dkarlsso.smartmirror.javafx.services.MotionService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
public class VectorMotionService implements MotionService {

    private final CachedList<MotionUpdate> motionUpdates = new CachedList<>(2);

    private Instant latestEvent = Instant.now();


    @Override
    public void notifyHandMotion(final MotionUpdateRequest motionUpdateRequest, final Consumer<MotionEvent> motionEventConsumer) {
        System.out.println(motionUpdateRequest.getMotionUpdates().get(0).getX() + "  " + motionUpdateRequest.getMotionUpdates().get(0).getY() + "  " + motionUpdateRequest.getMotionUpdates().get(0).getScore());

        if(motionUpdateRequest.getMotionUpdates().size() == 1 && latestEvent.plusSeconds(1).isBefore(Instant.now())) {
            this.motionUpdates.add(motionUpdateRequest.getMotionUpdates().get(0)); // Only support one hand


            // Removes disturbance of hand motion for holding hand in same place..
            /*
                        final Iterator<MotionUpdate> motionUpdateIterator = this.motionUpdates.iterator();
            final List<MotionUpdate> toRemove = new ArrayList<>();
            MotionUpdate previous = null;
            while (motionUpdateIterator.hasNext()) {
                MotionUpdate update = motionUpdateIterator.next();
                if (previous != null && isWithinInterval(update.getX(), previous.getX(), 50)
                        && isWithinInterval(update.getY(), previous.getY(), 30) ) {
                    toRemove.add(update);
                }
                previous = update;
            }
            toRemove.forEach(this.motionUpdates::remove);
             */


            double totalVectorX = 0;
            double totalVectorY = 0;
            for(MotionUpdate update : this.motionUpdates) {
                final double x = update.getX();
                final double y = update.getY();
                totalVectorX += x;
                totalVectorY += y;
            }


            double magnitude = pythagoras(totalVectorX, totalVectorY);
            double angle = angle(totalVectorX, totalVectorY);

            if(magnitude > 400) {
                if (angle < 40) {
                    motion(this.motionUpdates.getAll(), true, motionEventConsumer);
                    this.motionUpdates.clear();
                    System.out.println("Swipe side angle " + angle);

                }
                else if(angle < 90) {
                    // TODO: Should be false, or should be refactored altogether.... (This is up/down)
                    motion(this.motionUpdates.getAll(), true, motionEventConsumer);
                    this.motionUpdates.clear();
                    System.out.println("Swipe up/down angle " + angle);
                }
                else {
                    System.out.println("Lol?");
                }
            }


        }
    }

    private void motion(List<MotionUpdate> motionUpdates, boolean movementSideways, final Consumer<MotionEvent> motionEventConsumer) {
        System.out.println("Size " + motionUpdates.size());
        final List<MotionUpdate> beginning = new ArrayList<>();
        final List<MotionUpdate> end = new ArrayList<>();

        int i=0;
        for (final MotionUpdate motionUpdate: motionUpdates) {
            if(i < motionUpdates.size()/2) {
                beginning.add(motionUpdate);
            }
            else {
                end.add(motionUpdate);
            }
            i++;
        }

        final MovementDirection movementDirection;
        if (movementSideways) {
            double meanXBeginning = beginning.stream()
                    .mapToDouble(MotionUpdate::getX)
                    .reduce(0, Double::sum);

            double meanXEnd = end.stream()
                    .mapToDouble(MotionUpdate::getY)
                    .reduce(0, Double::sum);
            System.out.println("beginning " + meanXBeginning + " " + meanXEnd);
            movementDirection = meanXBeginning < meanXEnd ? MovementDirection.RIGHT : MovementDirection.LEFT;
        }
        else {
            double meanYBeginning = beginning.stream()
                    .mapToDouble(MotionUpdate::getY)
                    .reduce(0, Double::sum);

            double meanYEnd = end.stream()
                    .mapToDouble(MotionUpdate::getY)
                    .reduce(0, Double::sum);
            System.out.println("beginning " + meanYBeginning + " " + meanYEnd);
            movementDirection = meanYBeginning < meanYEnd ? MovementDirection.UP : MovementDirection.DOWN;
        }

        final MotionEvent motionEvent = MotionEvent.builder()
                .motionType(MotionType.HAND_MOTION)
                .movementDirection(movementDirection)
                .build();
        latestEvent = Instant.now();
        System.out.println("Movement " + motionEvent.getMovementDirection());

        motionEventConsumer.accept(motionEvent);
    }

    private boolean isWithinInterval(double pointA, double pointB, double delta) {
        return pointA + delta > pointB && pointA < pointB + delta;
    }

    private double angle(double x, double y) {
        return Math.atan(y/x) *180/3.14;
    }

    private double pythagoras(double x, double y) {
        return Math.sqrt(x*x + y*y);
    }
}
