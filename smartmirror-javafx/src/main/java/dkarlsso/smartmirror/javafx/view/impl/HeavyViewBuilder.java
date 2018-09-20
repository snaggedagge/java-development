package dkarlsso.smartmirror.javafx.view.impl;


import dkarlsso.commons.application.ApplicationUtils;
import dkarlsso.commons.date.DayUtils;
import dkarlsso.commons.weather.Weather;
import dkarlsso.commons.weather.WeatherPrognosis;
import dkarlsso.smartmirror.javafx.model.interfaces.DataService;
import dkarlsso.smartmirror.javafx.model.interfaces.DataServiceException;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.apache.commons.lang.StringUtils;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

public class HeavyViewBuilder extends AbstractViewBuilder implements dkarlsso.smartmirror.javafx.view.ViewBuilder {

    private int weatherIndex = 0; // TODO This is not viable, since it will grow really big..

    public HeavyViewBuilder(final DataService dataService) {
        super(dataService);
    }

    public dkarlsso.smartmirror.javafx.view.ViewBuilder addWeatherData() {
        final WeatherPrognosis weatherPrognosis;

        int largestWeatherListSize = 0;

        try {
            weatherPrognosis = dataService.getWeatherPrognosis();

            for(int i = 0; i<weatherPrognosis.getWeatherList().size() && i < ApplicationUtils.getShowDayCount(); i++) {

                int weatherListSize = weatherPrognosis.getWeatherList().get(i).size();

                if(weatherListSize > largestWeatherListSize) {
                    largestWeatherListSize = weatherListSize;
                }

                int loc = weatherIndex % weatherListSize;

                Weather weather = weatherPrognosis.getWeatherList().get(i).get(loc);

                final VBox verticalBox = new VBox();
                final ImageView weatherIconView = new ImageView(dataService.getIcon(weather.getIconName()));
                weatherIconView.setScaleX(1.3);
                weatherIconView.setScaleY(1.3);

                verticalBox.getChildren().addAll(weatherIconView,createText(weather.getDownfall()));


                gridPane.add(verticalBox, i+1, rowIndex, 1, 1);

                final VBox temperatureBox = new VBox();


                temperatureBox.getChildren().addAll(createText("\n" + weather.getTemp() + DEGREE_STRING,20,Color.ROYALBLUE),
                        createText(DayUtils.getTime(weather.getTime()),20),
                        createText(StringUtils.capitalize(weather.getWeatherDescription())));

                gridPane.add(temperatureBox, i+1, rowIndex+1, 1, 1);


                final ImageView compassImage = getImageView("compass.png");
                compassImage.setRotate(compassImage.getRotate() + weather.getWindDegrees());

                final StackPane compassNode = new StackPane();
                final Text windSpeedText = createText(String.format("%.1f",weather.getWindSpeed()),20);
                final Circle compassCircle = new Circle(20,Color.ROYALBLUE);

                compassNode.getChildren().addAll(compassImage,compassCircle,windSpeedText);
                compassNode.setAlignment(Pos.CENTER);

                final HBox compassContainer = new HBox();
                compassContainer.getChildren().add(compassNode);
                compassContainer.setAlignment(Pos.CENTER_LEFT);
                gridPane.add(compassContainer, i+1, rowIndex+2, 1, 1);

                final ImageView cloudinessImageView = getImageView("cloudiness.png");

                cloudinessImageView.setOpacity((double)weather.getCloudinessInPercent()/100);
                double scale = cloudinessImageView.getImage().getWidth()/compassImage.getImage().getWidth();

                cloudinessImageView.setFitWidth(cloudinessImageView.getImage().getWidth()/scale);
                cloudinessImageView.setFitHeight(cloudinessImageView.getImage().getHeight()/scale);
                gridPane.add(cloudinessImageView, i+1, rowIndex+3, 1, 1);
            }
            rowIndex+=4;
        } catch (FileNotFoundException | DataServiceException e) {
            LOG.error(e.getMessage(), e);
        }
        weatherIndex++;
        weatherIndex = weatherIndex%largestWeatherListSize;
        return this;
    }

    @SuppressWarnings("Duplicates")
    public dkarlsso.smartmirror.javafx.view.ViewBuilder addDayData() {
        try {
            final List<Date> dateList = dataService.getDateList();

            for(int i=0;i<ApplicationUtils.getShowDayCount();i++) {
                final Text textDate = new Text(DayUtils.getDate(dateList.get(i)));
                textDate.setId("label");

                final Text textDay = new Text(DayUtils.getDay(dateList.get(i)) + "\n");
                textDay.setId("day");

                gridPane.add(textDate, i+1, rowIndex+1, 1, 1);
                gridPane.add(textDay, i+1, rowIndex+2, 1, 1);
            }
            rowIndex+=3;
        } catch (DataServiceException e) {
            LOG.error(e.getMessage(), e);
        }
        return this;
    }


}
