package dkarlsso.portal.model;

import portalconnector.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WebsiteService {

    @Autowired
    private WebsiteRepository websiteRepository;

    public List<WebsiteDAO> getWebsites(final Permission permission) {
        final List<WebsiteDAO> allWebsites = websiteRepository.findAll();
        final List<WebsiteDAO> authorizedWebsites = new ArrayList<>();

        for(WebsiteDAO website : allWebsites) {
            if(website.getPermission().getPermissionLevel() <= permission.getPermissionLevel()) {
                authorizedWebsites.add(website);
            }
        }
        return authorizedWebsites;
    }

    public void addWebsite(final WebsiteDAO websiteDAO) {
        if(websiteRepository.existsByWebsiteName(websiteDAO.getWebsiteName())) {
            websiteRepository.delete(websiteRepository.findByWebsiteName(websiteDAO.getWebsiteName()));
        }
        websiteRepository.save(websiteDAO);
    }

}
