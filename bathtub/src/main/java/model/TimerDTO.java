package model;

import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

public class TimerDTO {

    private int hottubTemperature = 37;

    private int circulationTemperature = 45;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private DateTime startHeatingTime = new DateTime();

    public TimerDTO() {
    }

    public TimerDTO(int hottubTemperature, int circulationTemperature, DateTime startHeatingTime) {
        this.hottubTemperature = hottubTemperature;
        this.circulationTemperature = circulationTemperature;
        this.startHeatingTime = startHeatingTime;
    }

    public int getHottubTemperature() {
        return hottubTemperature;
    }

    public int getCirculationTemperature() {
        return circulationTemperature;
    }

    public DateTime getStartHeatingTime() {
        return startHeatingTime;
    }

    public void setHottubTemperature(int hottubTemperature) {
        this.hottubTemperature = hottubTemperature;
    }

    public void setCirculationTemperature(int circulationTemperature) {
        this.circulationTemperature = circulationTemperature;
    }

    public void setStartHeatingTime(DateTime startHeatingTime) {
        this.startHeatingTime = startHeatingTime;
    }
}
