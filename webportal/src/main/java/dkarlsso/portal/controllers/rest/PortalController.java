package dkarlsso.portal.controllers.rest;

import portalconnector.model.Permission;
import portalconnector.PortalConnector;
import portalconnector.model.WebsiteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import dkarlsso.portal.model.User;
import dkarlsso.portal.model.UserRepository;
import dkarlsso.portal.model.WebsiteMapper;
import dkarlsso.portal.model.WebsiteService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
public class PortalController {

    @Autowired
    WebsiteService websiteService;

    @Autowired
    UserRepository userRepository;

    @GetMapping(PortalConnector.URI_GET_ALL_WEBSITES)
    public List<WebsiteDTO> getAllWebsites(final Authentication authentication) {
        if(authentication != null && authentication.isAuthenticated()) {
            User user = userRepository.findByFacebookId((String)authentication.getCredentials());
            return WebsiteMapper.mapDaos(websiteService.getWebsites(user.getPermission()));
        }
        return WebsiteMapper.mapDaos(websiteService.getWebsites(Permission.UNAUTHORIZED));
    }

    @PostMapping(PortalConnector.URI_ADD_WEBSITE)
    public WebsiteDTO addWebsite(final HttpServletRequest request, @RequestBody WebsiteDTO websiteDTO) {
        websiteService.addWebsite(WebsiteMapper.map(websiteDTO));
        return websiteDTO;
    }
}
