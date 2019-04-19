package dkarlsso.smartmirror.spring.interfaces;

import dkarlsso.authentication.CustomAuthentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OverviewController {

    //@Autowired
    //OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> test;


    @GetMapping("/")
    public String overview(final Model model, final CustomAuthentication customAuthentication) {

       //test.getTokenResponse()

        //lol.getTokenResponse().getAccessToken().getTokenValue();

        //GoogleCredential sa = new GoogleCredential();

        //sa.setAccessToken()
        model.addAttribute("user", customAuthentication);
        return "welcome";
    }



}
