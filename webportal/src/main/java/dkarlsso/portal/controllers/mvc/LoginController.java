package dkarlsso.portal.controllers.mvc;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login() {
        return "facebook";
    }

}
