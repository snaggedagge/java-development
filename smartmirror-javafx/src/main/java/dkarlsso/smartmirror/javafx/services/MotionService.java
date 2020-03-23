package dkarlsso.smartmirror.javafx.services;

import dkarlsso.smartmirror.javafx.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

@Service
public interface MotionService {

    /**
     * Notifies service about a handMotion
     * @param motionUpdateRequest motionUpdateRequest
     */
    void notifyHandMotion(MotionUpdateRequest motionUpdateRequest, Consumer<MotionEvent> motionEventConsumer);
}
