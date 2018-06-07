package dkarlsso.commons.weather;

import java.util.Date;

public class Weather {

    private double temp;

    private double minTemp;

    private double maxTemp;

    private double pressure;

    private int humidity;

    private String weather;

    private String weatherDescription;

    private String iconName;

    private int cloudinessInPercent;

    private double windSpeed;

    private int windDegrees;

    private double rainVolumeThreeHours;

    private double snowVolumeThreeHours;

    private Date time;


    public Weather() {
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public int getCloudinessInPercent() {
        return cloudinessInPercent;
    }

    public void setCloudinessInPercent(int cloudinessInPercent) {
        this.cloudinessInPercent = cloudinessInPercent;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getWindDegrees() {
        return windDegrees;
    }

    public void setWindDegrees(int windDegrees) {
        this.windDegrees = windDegrees;
    }

    public double getRainVolumeThreeHours() {
        return rainVolumeThreeHours;
    }

    public void setRainVolumeThreeHours(double rainVolumeThreeHours) {
        this.rainVolumeThreeHours = rainVolumeThreeHours;
    }

    public double getSnowVolumeThreeHours() {
        return snowVolumeThreeHours;
    }

    public void setSnowVolumeThreeHours(double snowVolumeThreeHours) {
        this.snowVolumeThreeHours = snowVolumeThreeHours;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getDownfall() {
        String downfall = "";
        if(getRainVolumeThreeHours() > 0.1) {
            downfall = String.format("%.2f",getRainVolumeThreeHours()) + "mm";
        }
        if(getSnowVolumeThreeHours() > 0.1) {
            downfall = String.format("%.2f",getSnowVolumeThreeHours()) + "mm";
        }
        return downfall;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "temp=" + temp +
                ", minTemp=" + minTemp +
                ", maxTemp=" + maxTemp +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", dkarlsso.weather='" + weather + '\'' +
                ", weatherDescription='" + weatherDescription + '\'' +
                ", iconName='" + iconName + '\'' +
                ", cloudinessInPercent=" + cloudinessInPercent +
                ", windSpeed=" + windSpeed +
                ", windDegrees=" + windDegrees +
                ", rainVolumeThreeHours=" + rainVolumeThreeHours +
                ", snowVolumeThreeHours=" + snowVolumeThreeHours +
                ", time=" + time +
                '}';
    }
}
