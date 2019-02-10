package dkarlsso.portal.controllers.mvc;

import dkarlsso.portal.model.WebsiteDAO;
import dkarlsso.portal.model.WebsiteException;
import dkarlsso.portal.model.WebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import portalconnector.model.Permission;
import portalconnector.model.WebsiteDTO;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
class TestController {

    @Autowired
    private WebsiteService websiteService;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/hello/{microservice}")
    public String test(@PathVariable final String microservice) {
        System.out.println(microservice);

        return restTemplate.getForObject("http://localhost:8080/", String.class);
    }

    @GetMapping("/service/{microservice}")
    public String service(@PathVariable final String microservice,
                          final Authentication authentication) throws WebsiteException {

        Permission permission = Permission.UNAUTHORIZED;
        if (authentication.isAuthenticated()) {
            permission = Permission.AUTHORIZED;
            // TODO: Once users actually have a repo, fix this
        }

        final WebsiteDAO websiteDAO = websiteService.getWebsite(microservice, permission);
        return restTemplate.getForObject(websiteDAO.getWebsiteLink(), String.class);
    }

    @GetMapping("/{microservice}")
    public ResponseEntity service2(@PathVariable final String microservice, final HttpServletRequest request,
                          final Authentication authentication) throws WebsiteException, URISyntaxException {

        Permission permission = Permission.UNAUTHORIZED;
        if (authentication != null && authentication.isAuthenticated()) {
            permission = Permission.AUTHORIZED;
            // TODO: Once users actually have a repo, fix this
        }

        final WebsiteDAO websiteDAO = websiteService.getWebsite(microservice, permission);

        URI service = new URI(websiteDAO.getWebsiteLink());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(service);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }


}