package dkarlsso.portal.controllers.mvc;

import dkarlsso.portal.facebook.FacebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FacebookController {

    @Autowired
    FacebookService facebookService;

    @GetMapping("/createFacebookAuthorization")
    public String createFacebookAuthorization(){
        return facebookService.createFacebookAuthorizationURL();
    }


    /**
     * Not working ATM, spring has issues with facebook
     * @param code
     */
    @RequestMapping("/facebook")
    public void createFacebookAccessToken(@RequestParam("code") String code){
        facebookService.createFacebookAccessToken(code);
    }

    @GetMapping("/getName")
    public String getNameResponse(){
        return facebookService.getName();
    }
}
