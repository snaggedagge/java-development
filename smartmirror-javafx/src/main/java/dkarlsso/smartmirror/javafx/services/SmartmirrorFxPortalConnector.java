package dkarlsso.smartmirror.javafx.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import portalconnector.PortalConnector;
import portalconnector.model.Permission;
import portalconnector.model.PortalConnectorException;
import portalconnector.model.WebsiteDTO;

@Service
public class SmartmirrorFxPortalConnector {

    private final static Logger log = LoggerFactory.getLogger(SmartmirrorFxPortalConnector.class);

    private static final int SCHEDULED_FIFTEEN_MINUTES = 15 * 60 * 1000;

    private PortalConnector portalConnector = new PortalConnector();

    @Scheduled(fixedDelay = SCHEDULED_FIFTEEN_MINUTES)
    public void scheduleFixedDelayTask() {
        final WebsiteDTO websiteDTO = WebsiteDTO.builder()
                .permission(Permission.SUPERUSER)
                .websiteId("smartmirror-javafx-dag")
                .websiteName("SmartMirror JavaFx Application")
                .websiteDescription("Currently used for AWS lam,bdas to find IP adress of application")
                .hasLogin(false)
                .build();
        try {
            portalConnector.addWebsite(websiteDTO, false, 8080);
            log.debug("Updated webportal of location");
        } catch (final PortalConnectorException e) {
            log.error("Could not update Webportal of servers location: " + e.getMessage());
        }
    }

}
