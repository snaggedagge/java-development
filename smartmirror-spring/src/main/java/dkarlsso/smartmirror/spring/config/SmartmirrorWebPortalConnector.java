package dkarlsso.smartmirror.spring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import portalconnector.PortalConnector;
import portalconnector.model.Permission;
import portalconnector.model.PortalConnectorException;
import portalconnector.model.WebsiteDTO;

import java.io.InputStream;
import java.util.Base64;

@Service
public class SmartmirrorWebPortalConnector {

    private final static Logger log = LoggerFactory.getLogger(SmartmirrorWebPortalConnector.class);

    private static final int SCHEDULED_FIFTEEN_MINUTES = 15 * 60 * 1000;

    private PortalConnector portalConnector = new PortalConnector();

    @Scheduled(fixedDelay = SCHEDULED_FIFTEEN_MINUTES)
    public void scheduleFixedDelayTask() {
        final WebsiteDTO websiteDTO = WebsiteDTO.builder()
                .permission(Permission.ADMIN)
                .websiteId("smartmirror-web")
                .websiteName("SmartMirror Webpage")
                .websiteDescription("Webpage for configuring the smartmirror")
                .hasLogin(true)
                .build();
        try {
            portalConnector.addWebsite(websiteDTO, false);
        } catch (final PortalConnectorException e) {
            log.error("Could not update Webportal of servers location: " + e.getMessage());
        }
    }

}
