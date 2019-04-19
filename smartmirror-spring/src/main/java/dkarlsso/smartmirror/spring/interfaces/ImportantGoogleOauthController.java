package dkarlsso.smartmirror.spring.interfaces;

import dkarlsso.commons.google.GoogleConnector;
import dkarlsso.commons.model.CommonsException;
import dkarlsso.commons.oauth2.Oauth2Credential;
import dkarlsso.commons.oauth2.Oauth2Scope;
import dkarlsso.smartmirror.spring.infrastructure.repository.Oauth2UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

@Controller
@RequestMapping("/login")
public class ImportantGoogleOauthController {

    private final Logger LOG = LogManager.getLogger(ImportantGoogleOauthController.class);

    @Autowired
    private Oauth2UserRepository oauth2UserRepository;

    private final GoogleConnector connector =
            new GoogleConnector("133552654968-snj1ijaj7fkfhb8rllt38mftkdnf9tmn.apps.googleusercontent.com",
                    "QgwHSiYY-lJ1s4pKQhrKAKSR");

    @GetMapping("/googleLogin")
    public String googleLogin(Model model) {

        return "redirect:" + connector.getLoginUrl("http://localhost:8080/login/googleOAUTH2",
                GoogleConnector.GoogleAccessType.OFFLINE, Oauth2Scope.GOOGLE_CALENDAR);
    }

    @GetMapping("/googleOAUTH2")
    public String googleOAUTH2(Model model, @RequestParam(required = false) final String error,
                               @RequestParam(required = false) final String code) {

        if(error != null) {
            LOG.error("Could not authorize google oauth2: " + error);
        }
        else if (code != null) {
            try {
                final Oauth2Credential credential = connector.authorizeLogin(code,"http://localhost:8080/login/googleOAUTH2");
                oauth2UserRepository.save(credential);
                // TODO: Store in DB somewhere
            } catch (HttpClientErrorException e) {
                LOG.error(e.getMessage() + " Response body: " + e.getResponseBodyAsString(), e);
            } catch (CommonsException e) {
                LOG.error("Could not authorize google oauth2: " + error);
            }
        }

        return "redirect:/";
    }



//    @GetMapping("/googleOAUTH2")
//    public String googleOAUTH2(Model model, @RequestParam(required = false) final String error,
//                               @RequestParam(required = false) final String code) {
//
//        if(error != null) {
//            final List<String> errorList = new ArrayList<>();
//            errorList.add(error);
//            model.addAttribute("errorList", errorList);
//            return "overview";
//        }
//        if (code != null) {
//
//            try {
//
//                // Real redirect http://localhost/login/googleOAUTH2Authorization DOESNT WORK ATM
//                GoogleAuthorizationResponse response = connector.authorizeLogin(code,"http://localhost:8080/login/googleOAUTH2");
//
//                final GoogleCredential credential = new GoogleCredential();
//
//                credential.setAccessToken(response.getAccessToken());
//                credential.setExpiresInSeconds(response.getExpiresIn());
//                credential.setRefreshToken(response.getRefreshToken());
//
//
//                try {
//                    final CalendarService schoolCalendar = new SimpleGoogleCalendar("SmartMirror", credential);
//
//                    schoolCalendar.setCalendars(schoolCalendar.getCalendars());
//
//                    List<EventDTO> list = schoolCalendar.getEvents();
//
//
//                    for(EventDTO dto : list) {
//                        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
//                        String reportDate = df.format(dto.getStart());
//                        System.out.println("Name " + dto.getEventName() + " Report Date: " + reportDate);
//                    }
//                } catch (CalendarException e) {
//                    System.out.println(e.getMessage());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//
//            } catch (HttpClientErrorException e) {
//                System.out.println(e.getCause() + "\n\n" + e.getMessage()+ "\n\n" + e.getResponseBodyAsString());
//            } catch (CommonsException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//
//        return "redirect:/";
//    }

}
