package dkarlsso.commons.calendar;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import dkarlsso.commons.calendar.dto.CalendarDTO;
import dkarlsso.commons.calendar.dto.EventDTO;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class GoogleCalendar implements CalendarService {


    private final String ACCOUNT_NAME;

    private final String APPLICATION_NAME = "SmartMirror";

    /** Directory to store user credentials for this dkarlsso.application. */
    private final File DATA_STORE_DIR;

    private final File APPLICATION_ROOT_FOLDER;

    /** Global instance of the {@link FileDataStoreFactory}. */
    private FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private HttpTransport HTTP_TRANSPORT;

    private final List<String> calendarIdList = new ArrayList<>();

    private DateTime futureTime;

    private com.google.api.services.calendar.Calendar calendarService;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/dkarlsso.calendar-java-quickstart
     */
    private final List<String> SCOPES =
            Arrays.asList(CalendarScopes.CALENDAR_READONLY);





    private GoogleCalendar(final File applicationRootFolder, final String user, int internal) throws CalendarException {
        APPLICATION_ROOT_FOLDER = applicationRootFolder;

        calendarIdList.add("primary");

        ACCOUNT_NAME = user;
        DATA_STORE_DIR = new File(APPLICATION_ROOT_FOLDER, ".credentials"+ File.separator + "calendar" + File.separator + ACCOUNT_NAME);
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            throw new CalendarException(t.getMessage());
        }

        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(new Date());
        c.add(java.util.Calendar.DATE, 8);
        futureTime = new DateTime(c.getTime().getTime());
    }

    public GoogleCalendar(final File applicationRootFolder, final String user, final Credential credential) throws CalendarException {
        this(applicationRootFolder, user, 0);
        try {
            calendarService = getCalendarService(credential);
        } catch (IOException e) {
            throw new CalendarException(e.getMessage(), e);
        }
    }

    public GoogleCalendar(final File applicationRootFolder, final String user) throws CalendarException {
        this(applicationRootFolder, user, 0);

        try {
            calendarService = getCalendarService(authorize());
        } catch (IOException e) {
            throw new CalendarException(e.getMessage(), e);
        }

    }



    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    private Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in = new FileInputStream(APPLICATION_ROOT_FOLDER + File.separator + "client_secret.json");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize(ACCOUNT_NAME);
        //System.out.println(
        //        "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());

        return credential;
    }

    /**
     * Build and return an authorized Calendar client service.
     * @return an authorized Calendar client service
     * @throws IOException
     */
    private com.google.api.services.calendar.Calendar
    getCalendarService(Credential credential ) throws IOException {
        return new com.google.api.services.calendar.Calendar.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }



    public List<EventDTO> getEvents() throws IOException {
        final DateTime now = new DateTime(System.currentTimeMillis());

        final List<Event> eventsList = new ArrayList<>();
        for(String id : calendarIdList) {
            Events even = calendarService.events().list(id)
                    .setMaxResults(20)
                    .setTimeMin(now)
                    .setTimeMax(futureTime)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();

            eventsList.addAll(even.getItems());
        }

        final List<EventDTO> eventDTOList = new ArrayList<>();
        for(Event event : eventsList) {
            eventDTOList.add(CalendarMapper.map(event));
        }

        return eventDTOList;
    }


    public List<CalendarDTO> getCalendars() throws IOException {
        List<CalendarListEntry> entryList =  calendarService.calendarList().list()
                .setMaxResults(10)
                .setShowDeleted(true)
                .setShowHidden(true)
                .execute().getItems();

        final List<CalendarDTO> calendarList = new ArrayList<>();
        for(CalendarListEntry entry : entryList) {
            calendarList.add(CalendarMapper.map(entry));
        }

        return calendarList;
    }


    public void setCalendars(final List<String> newCalendarIds) {
        if(newCalendarIds != null && !newCalendarIds.isEmpty()) {
            calendarIdList.clear();
            calendarIdList.addAll(newCalendarIds);
        }
    }

    public void setDaysInFuture(final int days) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(new Date());
        c.add(java.util.Calendar.DATE, days);
        futureTime = new DateTime(c.getTime().getTime());
    }
}
