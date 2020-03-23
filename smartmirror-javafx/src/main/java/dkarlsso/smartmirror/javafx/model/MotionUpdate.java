package dkarlsso.smartmirror.javafx.model;

import lombok.Data;

@Data
public class MotionUpdate {
    private final double[] bbox;
    private final double score;

    public double getX() {
        return bbox[0];
    }

    public double getY() {
        return bbox[1];
    }
}