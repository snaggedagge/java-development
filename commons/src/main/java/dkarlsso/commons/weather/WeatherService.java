package dkarlsso.commons.weather;

import dkarlsso.commons.date.DayUtils;
import dkarlsso.commons.weather.json.JsonPayload;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class WeatherService {

    private final String USER_AGENT = "Mozilla/5.0";

    private static final String JSON_STORAGE_FILENAME = "weather-json.txt";

    private final File JSON_FILE;

    private final File ICON_FOLDER;

    private static final String ICON_FORMAT = "png";

    private Date lastUpdatedDate = Calendar.getInstance().getTime();

    private final int updateIntervalInHours;

    private final ObjectMapper objectMapper;

    public WeatherService(final File weatherServiceRootFolder, int updateIntervalInHours) {

        JSON_FILE = new File(weatherServiceRootFolder, JSON_STORAGE_FILENAME);
        ICON_FOLDER  = new File(weatherServiceRootFolder, "icons");

        this.updateIntervalInHours = updateIntervalInHours;
        this.objectMapper = new ObjectMapper()
                .configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        if(!ICON_FOLDER.exists()) {
            ICON_FOLDER.mkdir();
        }
    }

    public File getWeatherIcon(final String iconName) throws WeatherException {

        final File file = new File(ICON_FOLDER.getAbsolutePath() + File.separator + iconName + "." + ICON_FORMAT);

        if(!file.exists()) {
            downloadIconToFile(iconName,file);
        }

        return file;
    }

    public WeatherPrognosis getWeather() throws WeatherException {
        return WeatherMapper.map(getRawWeathers());
    }

    public LightWeatherPrognosisDTO getLightWeather() throws WeatherException {
        return WeatherMapper.lightMap(getRawWeathers());
    }

    public JsonPayload getRawWeathers() throws WeatherException {
        try {

            String jsonPayloadString;
            if(DayUtils.isTimeBetween(lastUpdatedDate,updateIntervalInHours) && JSON_FILE.exists()
                    && DayUtils.isTimeBetween(new Date(JSON_FILE.lastModified()),updateIntervalInHours)) {
                jsonPayloadString = readFromFile();
            }
            else {
                jsonPayloadString = getWeatherFromApi();
            }
            return objectMapper.readValue(jsonPayloadString, JsonPayload.class);

        } catch (Exception e) {
            throw new WeatherException(e.getMessage(), e);
        }
    }

    private String getWeatherFromApi() throws Exception {

        lastUpdatedDate = Calendar.getInstance().getTime();

        String url = "http://api.openweathermap.org/data/2.5/forecast?id=3041732&APPID=daa2a7e64efc26779b5e289a17f7bf7b&units=metric";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        writeToFile(response.toString());
        return response.toString();
    }


    private void writeToFile(String value) throws WeatherException {
        try {
            FileOutputStream fos = new FileOutputStream(JSON_FILE);
            DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos));
            outStream.write(value.getBytes());
            outStream.close();
        } catch (IOException e) {
            throw new WeatherException(e.getMessage(),e);
        }

    }


    private String readFromFile() throws IOException {
        final List<String> list = Files.readAllLines(JSON_FILE.toPath());

        final StringBuilder stringBuilder= new StringBuilder();

        for(String str : list) {
            stringBuilder.append(str);
        }

        return stringBuilder.toString();
    }


    private void downloadIconToFile(final String iconName, final File iconFile) throws WeatherException {
        try {
            final URL url = new URL("http://openweathermap.org/img/w/" + iconName + ".png");
            final BufferedImage image = ImageIO.read(url);

            ImageIO.write(image,ICON_FORMAT,iconFile);
        } catch (IOException e) {
            throw new WeatherException(e.getMessage(), e);
        }
    }

}
