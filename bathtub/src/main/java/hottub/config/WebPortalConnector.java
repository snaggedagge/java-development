package hottub.config;

import org.apache.commons.io.FileUtils;
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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Base64;

@Service
public class WebPortalConnector {

    private final static Logger log = LoggerFactory.getLogger(WebPortalConnector.class);

    private static final int SCHEDULED_FIFTEEN_MINUTES = 15 * 60 * 1000;

    private PortalConnector portalConnector = new PortalConnector("http://dkarlsso-webportal.eu-west-1.elasticbeanstalk.com:8080");

    @Scheduled(fixedDelay = SCHEDULED_FIFTEEN_MINUTES)
    public void scheduleFixedDelayTask() {
        final WebsiteDTO websiteDTO = WebsiteDTO.builder()
                .permission(Permission.UNAUTHORIZED)
                .websiteId("hottub")
                .websiteName("Hottub Time Machine")
                .infoLink("https://github.com/snaggedagge/java-development/tree/master/bathtub")
                .hasLogin(true)
                .websiteDescription("Hottub website, used for monitoring and controlling my own hottub functionality and temperatures.")
                .build();

        try {
            final InputStream inputStream = new ClassPathResource("static/images/realHottub.jpg").getInputStream();
            websiteDTO.setImageBase64(Base64.getEncoder().encodeToString(IOUtils.toByteArray(inputStream)));
        }
        catch (Exception e) {
            log.error("Could not get image " + e.getMessage(), e);
        }

        try {
            portalConnector.addWebsite(websiteDTO, false);
        } catch (final PortalConnectorException e) {
            log.error("Could not update Webportal of servers location: " + e.getMessage());
        }
    }

}
