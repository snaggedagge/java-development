package dkarlsso.smartmirror.javafx.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledUpdatesOnTopic{

    @Autowired
    private SimpMessagingTemplate template;

    @Scheduled(fixedDelay=60000)
    public void publishUpdates(){
        template.convertAndSend("/topic/update", "{}");
    }
}