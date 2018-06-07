package dkarlsso.portal.controllers.mvc;

import dkarlsso.portal.model.User;
import dkarlsso.portal.model.UserRepository;
import dkarlsso.portal.model.WebsiteService;
import portalconnector.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebsiteController {

    @Autowired
    private WebsiteService websiteService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/")
    public String logindf(final Model model, Authentication authentication) {
        // TODO something about this


        if(authentication != null && authentication.isAuthenticated()) {
            User user = userRepository.findByFacebookId((String)authentication.getCredentials());
            model.addAttribute("websites", websiteService.getWebsites(user.getPermission()));
        }
        else {
            model.addAttribute("websites", websiteService.getWebsites(Permission.UNAUTHORIZED));
        }
        return "welcome";
    }

}
