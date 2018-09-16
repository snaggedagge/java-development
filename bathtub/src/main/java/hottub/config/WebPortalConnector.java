package hottub.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import portalconnector.model.Permission;
import portalconnector.PortalConnector;
import portalconnector.model.PortalConnectorException;
import portalconnector.model.WebsiteDTO;

@Service
public class WebPortalConnector {

    private final static Logger log = LoggerFactory.getLogger(WebPortalConnector.class);

    private static final int SCHEDULED_FIFTEEN_MINUTES = 15 * 60 * 1000;

    private PortalConnector portalConnector = new PortalConnector("http://localhost:8080");


    @Scheduled(fixedDelay = SCHEDULED_FIFTEEN_MINUTES)
    public void scheduleFixedDelayTask() {

        final WebsiteDTO websiteDTO = new WebsiteDTO();
        websiteDTO.setPermission(Permission.UNAUTHORIZED);
        websiteDTO.setWebsiteName("Hottub");
        websiteDTO.setWebsiteDescription("Hottub website, used for monitoring and controlling hottub functionality and temperatures.");
        websiteDTO.setWebsiteLink("http://localhost");

        try {
            portalConnector.addWebsite(websiteDTO);
        } catch (final PortalConnectorException e) {
            log.error("Could not update Webportal of servers location: " + e.getMessage());
        }
    }

}
