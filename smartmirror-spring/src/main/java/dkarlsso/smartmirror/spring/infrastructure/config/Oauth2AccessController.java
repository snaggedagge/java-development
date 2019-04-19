package dkarlsso.smartmirror.spring.infrastructure.config;

import dkarlsso.authentication.CustomAuthentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Oauth2AccessController {



    @GetMapping("/access")
    public String oauth2Access(final CustomAuthentication customAuthentication) {

        return "access";
    }
}
