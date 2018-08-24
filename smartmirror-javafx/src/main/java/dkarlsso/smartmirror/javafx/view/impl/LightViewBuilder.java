package dkarlsso.smartmirror.javafx.view.impl;

import dkarlsso.commons.application.ApplicationUtils;
import dkarlsso.commons.date.DayUtils;
import dkarlsso.commons.weather.LightWeatherDTO;
import dkarlsso.commons.weather.LightWeatherPrognosisDTO;
import dkarlsso.smartmirror.javafx.model.DataService;
import dkarlsso.smartmirror.javafx.model.DataServiceException;
import dkarlsso.smartmirror.javafx.view.AbstractViewBuilder;
import dkarlsso.smartmirror.javafx.view.ViewInterface;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Date;
import java.util.List;

public class LightViewBuilder extends AbstractViewBuilder implements ViewInterface {

    public LightViewBuilder(final DataService dataService) {
        super(dataService);
    }

    public ViewInterface addWeatherData() {
        final LightWeatherPrognosisDTO weatherPrognosis;
        try {
            weatherPrognosis = dataService.getLightWeatherPrognosis();

            for(int i=0;i<weatherPrognosis.getWeatherList().size();i++) {

                LightWeatherDTO weather = weatherPrognosis.getWeatherList().get(i);

                final ImageView imv = new ImageView();
                imv.setImage(dataService.getIcon(weather.getIconName()));
                imv.setScaleX(1.3);
                imv.setScaleY(1.3);

                gridPane.add(imv, i+1, rowIndex, 1, 1);

                final String DEGREE  = "\u00b0";


                final Text coldTemp = new Text(weather.getMinTemp() + DEGREE + "  ");
                coldTemp.setId("labelCold");
                final Text warmTemp = new Text(weather.getMaxTemp() + DEGREE + " ");
                warmTemp.setId("labelWarm");
                final Text textDay = new Text(weather.getWeather() + "\n" + DayUtils.getTime(weather.getDay()) + "\n");
                textDay.setId("label");
                final HBox horizontalBox = new HBox();
                horizontalBox.getChildren().addAll(coldTemp,warmTemp);
                horizontalBox.setAlignment(Pos.CENTER_LEFT);

                final VBox verticalBox = new VBox();
                verticalBox.getChildren().addAll(horizontalBox,textDay);

                gridPane.add(verticalBox, i+1, rowIndex+1, 1, 1);
            }
            rowIndex+=2;
        } catch (DataServiceException e) {
            LOG.error(e.getMessage(), e);
        }
        return this;
    }

    public ViewInterface addDayData() {
        try {
            final List<Date> dateList = dataService.getDateList();

            for(int i = 0; i< ApplicationUtils.getShowDayCount(); i++) {
                final Text textDate = new Text(DayUtils.getDate(dateList.get(i)));
                textDate.setId("label");

                final Text textDay = new Text(DayUtils.getDay(dateList.get(i)) + "\n");
                textDay.setId("day");

                gridPane.add(textDate, i+1, rowIndex, 1, 1);
                gridPane.add(textDay, i+1, rowIndex+1, 1, 1);
            }
            rowIndex+=2;
        } catch (DataServiceException e) {
            LOG.error(e.getMessage(), e);
        }
        return this;
    }

}



