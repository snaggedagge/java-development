package dkarlsso.portal;


import dkarlsso.portal.model.WebsiteDAO;
import dkarlsso.portal.model.WebsiteRepository;
import portalconnector.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestDatabasePopulator {

    private final WebsiteRepository websiteRepository;

    @Autowired
    public TestDatabasePopulator(WebsiteRepository websiteRepository) {
        this.websiteRepository = websiteRepository;

        WebsiteDAO website = new WebsiteDAO();

        website.setWebsiteLink("https://localhost:8080/");
        website.setPermission(Permission.UNAUTHORIZED);
        website.setWebsiteName("Portal Connector");
        website.setWebsiteDescription("This website created in Java Spring is a dkarlsso.portal connecting every website and IoT device made by dkarlsso. " +
        "The idea is that the IoT devices with strange and changing IP adresses will update the dkarlsso.portal of their current location");

        websiteRepository.save(website);
    }

}
