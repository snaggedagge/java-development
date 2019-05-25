package dkarlsso.portal.interfaces.webportal;

import dkarlsso.authentication.AuthorityType;
import dkarlsso.authentication.UserAuthority;
import dkarlsso.portal.domain.model.*;
import dkarlsso.portal.domain.model.credentials.UserInfo;
import dkarlsso.portal.domain.service.JwtLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import portalconnector.PortalConnector;
import portalconnector.model.Permission;
import portalconnector.model.WebsiteDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@RestController
public class PortalController {

    @Autowired
    WebsiteService websiteService;


    @Autowired
    private JwtLoginService jwtLoginService;
//
//    @Autowired
//    UserRepository userRepository;

    @GetMapping(PortalConnector.URI_GET_ALL_WEBSITES)
    public List<WebsiteDTO> getAllWebsites(final UserInfo userInfo) {
        return WebsiteMapper.mapDaos(websiteService.getWebsites(userInfo.getPermission()));
    }

    @GetMapping(PortalConnector.URI_GET_ALL_WEBSITES + "/{websiteId}")
    public WebsiteDTO getWebsite(@PathVariable final String websiteId) throws WebsiteException {
        return WebsiteMapper.map(websiteService.getWebsite(websiteId));
    }

    @PutMapping(PortalConnector.URI_ADD_WEBSITE)
    public WebsiteDTO addWebsite(final HttpServletRequest request,
                                 @PathVariable final String websiteId,
                                 @RequestBody WebsiteDTO websiteDTO) {
        websiteDTO.setWebsiteId(websiteId);
        websiteService.addWebsite(WebsiteMapper.map(websiteDTO));
        return websiteDTO;
    }

    @GetMapping("/jwt/{websiteId}")
    public String getWebsiteJwt(final UserInfo userInfo,
                                @PathVariable final String websiteId) throws WebsiteException, NotAuthorizedException {

        final WebsiteDAO websiteDAO = websiteService.getWebsite(websiteId, userInfo.getPermission());
        if (!userInfo.isLoggedIn()) {
            throw new RuntimeException("Not logged in");
        }

        // TODO: Fix something clean for each website, somehow
        List<UserAuthority> userAuthorities = new ArrayList<>();
        if (userInfo.getPermission() == Permission.ADMIN) {
            userAuthorities.add(new UserAuthority(AuthorityType.WRITE_PRIVILEGE));
        }
        else if (userInfo.getPermission() == Permission.SUPERUSER) {
            userAuthorities.add(new UserAuthority(AuthorityType.CAN_EXECUTE_AS_ROOT));
        }
        else {
            userAuthorities.add(new UserAuthority(AuthorityType.READ_AUTHORITY));
        }
        return jwtLoginService.getJwt(websiteDAO.getWebsiteName(), userInfo, userAuthorities);
    }

    @GetMapping("/jwt/valid/{jwt}")
    public boolean isJwtValid(final UserInfo userInfo,
                             @PathVariable final String jwt) throws WebsiteException, NotAuthorizedException {
        return jwtLoginService.isJwtValid(jwt);
    }
}
