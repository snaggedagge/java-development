package dkarlsso.smartmirror.javafx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MotionUpdateRequest {

    private List<MotionUpdate> motionUpdates;

    private int minX;

    private int maxX;

    private int minY;

    private int maxY;
}
