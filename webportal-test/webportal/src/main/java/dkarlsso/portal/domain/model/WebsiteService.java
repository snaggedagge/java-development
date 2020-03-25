package dkarlsso.portal.domain.model;

import portalconnector.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WebsiteService {

    @Autowired
    private WebsiteRepository websiteRepository;

    public WebsiteDAO getWebsite(final String websiteId) throws WebsiteException {
        return websiteRepository.findById(websiteId)
                .orElseThrow(WebsiteException::new);
    }

    public WebsiteDAO getWebsite(final String websiteId,
                                       final Permission permission) throws WebsiteException, NotAuthorizedException {
        final WebsiteDAO websiteDAO = websiteRepository.findById(websiteId)
                .orElseThrow(WebsiteException::new);

        if(websiteDAO.getPermission().getPermissionLevel() <= permission.getPermissionLevel()) {
            return websiteDAO;
        }
        throw new NotAuthorizedException("Not authorized to see that page.");
    }

    public List<WebsiteDAO> getWebsites(final Permission permission) {
        final List<WebsiteDAO> allWebsites = websiteRepository.findAll();
        final List<WebsiteDAO> authorizedWebsites = new ArrayList<>();

        for(WebsiteDAO website : allWebsites) {
            if(website.getPermission().getPermissionLevel() <= permission.getPermissionLevel()) {
                authorizedWebsites.add(website);
            }
            else if (website.getSpecialUserPermissions().containsKey("TODO")) {
            // TODO: pickup from here
            }
        }
        return authorizedWebsites;
    }

    public List<WebsiteDAO> getWebsites() {
        return websiteRepository.findAll();
    }


    public void addWebsite(final WebsiteDAO websiteDAO) {
        if(websiteRepository.existsByWebsiteName(websiteDAO.getWebsiteName())) {
            websiteRepository.delete(websiteRepository.findByWebsiteName(websiteDAO.getWebsiteName()));
        }
        websiteRepository.save(websiteDAO);
    }

}
