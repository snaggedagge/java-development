package dkarlsso.portal.controllers.rest;

import dkarlsso.portal.model.WebsiteDAO;
import dkarlsso.portal.model.WebsiteException;
import dkarlsso.portal.model.WebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import portalconnector.model.Permission;

import java.util.Base64;

@RestController
public class ImageController {

    @Autowired
    private WebsiteService websiteService;

    @GetMapping("/api/image/{websiteId}")
    public ResponseEntity<byte[]> getImage(@PathVariable final String websiteId) throws WebsiteException {

        final WebsiteDAO website =  websiteService.getWebsite(websiteId);

        if (website.getImageBase64() == null) {
            return ResponseEntity
                    .notFound().build();
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(Base64.getDecoder().decode(website.getImageBase64()));
    }

}
