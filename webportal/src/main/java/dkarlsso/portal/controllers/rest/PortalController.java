package dkarlsso.portal.controllers.rest;

import dkarlsso.portal.model.WebsiteMapper;
import dkarlsso.portal.model.WebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import portalconnector.PortalConnector;
import portalconnector.model.Permission;
import portalconnector.model.WebsiteDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
public class PortalController {

    @Autowired
    WebsiteService websiteService;
//
//    @Autowired
//    UserRepository userRepository;

    @GetMapping(PortalConnector.URI_GET_ALL_WEBSITES)
    public List<WebsiteDTO> getAllWebsites(final Authentication authentication) {
//        if(authentication != null && authentication.isAuthenticated()) {
//            User user = userRepository.findByFacebookId((String)authentication.getCredentials());
//            return WebsiteMapper.mapDaos(websiteService.getWebsites(user.getPermission()));
//        }
        return WebsiteMapper.mapDaos(websiteService.getWebsites(Permission.UNAUTHORIZED));
    }

    @PutMapping(PortalConnector.URI_ADD_WEBSITE)
    public WebsiteDTO addWebsite(final HttpServletRequest request,
                                 @PathVariable final String websiteId,
                                 @RequestBody WebsiteDTO websiteDTO) {
        websiteDTO.setWebsiteId(websiteId);
        websiteService.addWebsite(WebsiteMapper.map(websiteDTO));
        return websiteDTO;
    }
}
