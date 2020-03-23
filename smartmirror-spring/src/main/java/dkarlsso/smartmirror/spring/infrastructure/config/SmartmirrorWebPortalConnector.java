package dkarlsso.smartmirror.spring.infrastructure.config;

import org.apache.commons.io.IOUtils;
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
                .permission(Permission.UNAUTHORIZED) // TODO: how to handle this? I would want it to show but login is required
                .websiteId("smartmirror-web")
                .websiteName("SmartMirror Webpage")
                .websiteDescription("Webpage for configuring the smart mirror. Plan is to be able to configure " +
                        "multiple mirrors from here if i ever create another one for friends.")
                .infoLink("https://github.com/snaggedagge/java-development/blob/master/smartmirror-javafx/README.md")
                .hasLogin(true)
                .build();
        try {
            final InputStream inputStream = new ClassPathResource("static/images/mirror.jpg").getInputStream();
            websiteDTO.setImageBase64(Base64.getEncoder().encodeToString(IOUtils.toByteArray(inputStream)));
        }
        catch (Exception e) {
            log.error("Could not get image " + e.getMessage(), e);
        }

        try {
            portalConnector.addWebsite(websiteDTO, false, 80);
        } catch (final PortalConnectorException e) {
            log.error("Could not update Webportal of servers location: " + e.getMessage());
        }
    }

}
