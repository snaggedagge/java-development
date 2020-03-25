package dkarlsso.portal.interfaces.webportal;

import dkarlsso.portal.domain.model.WebsiteDAO;
import dkarlsso.portal.domain.model.WebsiteService;
import dkarlsso.portal.domain.model.credentials.UserInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WebsiteController {

    private final Logger LOG = LogManager.getLogger(WebsiteController.class);

    @Autowired
    private WebsiteService websiteService;


    @RequestMapping("/")
    public String welcome(final HttpServletRequest request, final Model model, final UserInfo userInfo) {

        final List<WebsiteDAO> websites = websiteService.getWebsites(userInfo.getPermission());

        final List<WebsiteDAO> offlineWebsites = websites.stream()
                .filter(w -> Minutes.minutesBetween(new DateTime(w.getDateSinceLastConnection()), new DateTime()).isGreaterThan(Minutes.minutes(20)))
                .collect(Collectors.toList());


        final List<WebsiteDAO> onlineWebsites = websites.stream()
                .filter(w -> Minutes.minutesBetween(new DateTime(w.getDateSinceLastConnection()), new DateTime()).isLessThan(Minutes.minutes(20)))
                .collect(Collectors.toList());

        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        for (final WebsiteDAO website : onlineWebsites) {
            if (website.getWebsiteLink().contains(ipAddress)) {
                website.setWebsiteLink(website.getLocalWebsiteLink());
            }
        }

        model.addAttribute("websites", onlineWebsites);
        model.addAttribute("offlineWebsites", offlineWebsites);
        return "welcome";
    }

}
