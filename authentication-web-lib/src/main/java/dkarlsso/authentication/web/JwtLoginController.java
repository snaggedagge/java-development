package dkarlsso.authentication.web;

import dkarlsso.authentication.CustomAuthentication;
import dkarlsso.authentication.jwt.JwtAuthenticationParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class JwtLoginController {
    private final static Logger LOG = LoggerFactory.getLogger(JwtLoginController.class);

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${dkarlsso.security.jwt.baseURL}")
    private String webportalBaseUrl;

    @Value("${dkarlsso.security.jwt.subject}")
    private String expectedSubject;

    @GetMapping("/login/{jwt}")
    public String login(HttpServletRequest req, @PathVariable final String jwt) {
        try {
            final CustomAuthentication auth = JwtAuthenticationParser.authenticationFromJwt(jwt, expectedSubject, "Webportal");
            final Boolean jwtValid = restTemplate.getForObject(webportalBaseUrl + "/jwt/valid/" + jwt, Boolean.class);

            if (jwtValid != null && jwtValid) {
                auth.setAuthenticated(true);
                SecurityContext sc = SecurityContextHolder.getContext();
                sc.setAuthentication(auth);
                HttpSession session = req.getSession(true);
                session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
                return "redirect:/";
            }
        } catch (final Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return "redirect:/error";
    }



}
