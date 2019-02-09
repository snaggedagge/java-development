package dkarlsso.portal.controllers.mvc;

import dkarlsso.portal.model.WebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import portalconnector.model.Permission;

@Controller
public class WebsiteController {

    @Autowired
    private WebsiteService websiteService;

//    @Autowired
//    private UserRepository userRepository;

    @RequestMapping("/")
    public String logindf(final Model model, Authentication authentication) {
        // TODO something about this


//        if(authentication != null && authentication.isAuthenticated()) {
//            authentication.getAuthorities().
//            User user = userRepository.findByFacebookId((String)authentication.getCredentials());
//            model.addAttribute("websites", websiteService.getWebsites(user.getPermission()));
//        }
//        else {
//            model.addAttribute("websites", websiteService.getWebsites(Permission.UNAUTHORIZED));
//        }

        // TODO: Websites should be checked if located on same IP as user, and routed to localhost
        model.addAttribute("websites", websiteService.getWebsites(Permission.UNAUTHORIZED));

        model.addAttribute("message", "Helloo");

        return "welcome";
    }

}
