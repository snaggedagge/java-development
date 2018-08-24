package dkarlsso.smartmirror.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OverviewController {

    //@Autowired
    //OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> test;


    @GetMapping("/")
    public String overview(final Model model) {

       //test.getTokenResponse()

        //lol.getTokenResponse().getAccessToken().getTokenValue();

        //GoogleCredential sa = new GoogleCredential();

        //sa.setAccessToken()

        return "overview";
    }



}
