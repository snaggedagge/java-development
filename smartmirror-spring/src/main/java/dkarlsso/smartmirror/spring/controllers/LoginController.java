package dkarlsso.smartmirror.spring.controllers;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import dkarlsso.smartmirror.javafx.model.application.ApplicationUtils;
import dkarlsso.commons.calendar.CalendarException;
import dkarlsso.commons.calendar.GoogleCalendar;
import dkarlsso.commons.calendar.dto.EventDTO;
import dkarlsso.commons.google.GoogleAuthorizationResponse;
import dkarlsso.commons.google.GoogleConnector;
import dkarlsso.commons.model.CommonsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/login")
public class LoginController {


    private final GoogleConnector connector =
            new GoogleConnector("133552654968-snj1ijaj7fkfhb8rllt38mftkdnf9tmn.apps.googleusercontent.com",
                    "QgwHSiYY-lJ1s4pKQhrKAKSR");


    @GetMapping("/")
    public String login(final Model model,final String error,final String logout) {

        if (error != null) {
            model.addAttribute("errorList", Arrays.asList("Are you sure you entered correct email and password?"));
        }
        if (logout != null) {
            model.addAttribute("infoList", Arrays.asList("Logged out successfully!"));
        }
        return "login";
    }


    @GetMapping("/googleLogin")
    public String googleLogin(Model model) {



        return "redirect:" + connector.getLoginUrl("http://localhost/login/googleOAUTH2",
                GoogleConnector.GoogleScope.CALENDAR, GoogleConnector.GoogleAccessType.OFFLINE);
    }

    @GetMapping("/googleOAUTH2")
    public String googleOAUTH2(Model model, @RequestParam(required = false) final String error,
                               @RequestParam(required = false) final String code) {

        if(error != null) {
            final List<String> errorList = new ArrayList<>();
            errorList.add(error);
            model.addAttribute("errorList", errorList);
            return "overview";
        }
        if (code != null) {

            try {

                // Real redirect http://localhost/login/googleOAUTH2Authorization DOESNT WORK ATM
                GoogleAuthorizationResponse response = connector.authorizeLogin(code,"http://localhost/login/googleOAUTH2");

                GoogleCredential credential = new GoogleCredential();

                credential.setAccessToken(response.getAccessToken());
                credential.setExpiresInSeconds(response.getExpiresIn());
                credential.setRefreshToken(response.getRefreshToken());


                try {
                    final GoogleCalendar schoolCalendar = new GoogleCalendar(ApplicationUtils.getRootFolder(), "testApi", credential);

                    schoolCalendar.setCalendars(schoolCalendar.getCalendars());

                    List<EventDTO> list = schoolCalendar.getEvents();


                    for(EventDTO dto : list) {
                        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                        String reportDate = df.format(dto.getStart());
                        System.out.println("Name " + dto.getEventName() + "Report Date: " + reportDate);
                    }
                } catch (CalendarException e) {
                    System.out.println(e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } catch (HttpClientErrorException e) {
                System.out.println(e.getCause() + "\n\n" + e.getMessage()+ "\n\n" + e.getResponseBodyAsString());
            } catch (CommonsException e) {
                System.out.println(e.getMessage());
            }


        }

        return "redirect:/";
    }

}
