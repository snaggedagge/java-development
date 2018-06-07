package dkarlsso.smartmirror.javafx.view;

import dkarlsso.commons.application.ApplicationUtils;
import dkarlsso.commons.calendar.dto.EventDTO;
import dkarlsso.commons.date.DayUtils;
import dkarlsso.commons.userinfo.DayInfo;
import dkarlsso.commons.userinfo.UserWeekInfo;
import dkarlsso.smartmirror.javafx.model.DataService;
import dkarlsso.smartmirror.javafx.model.DataServiceException;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.List;

public abstract class AbstractViewBuilder implements ViewInterface {

    protected Logger LOG = LogManager.getLogger(getClass());

    protected BorderPane borderPane = new BorderPane();

    protected GridPane gridPane = new GridPane();

    final VBox vBoxForClock = new VBox();

    final HBox hBoxAroundClock = new HBox();

    final Text commandText = createText("",20,Color.ROYALBLUE);

    protected DataService dataService;

    protected int rowIndex = 0;

    protected static final int FONT_SIZE = 16;

    protected static final Paint COLOR = Color.WHITESMOKE;

    public static final String DEGREE_STRING  = "\u00b0";

    public static final String PATH_IMAGE  = "images";

    public AbstractViewBuilder(final DataService dataService) {
        this.dataService = dataService;
        gridPane.setHgap(40);
        hBoxAroundClock.getChildren().add(vBoxForClock);
        vBoxForClock.getChildren().add(commandText);

    }

    protected ImageView getImageView(final String imageName) throws FileNotFoundException {
        //PATH_IMAGE + "/" +
        //getClass().getClassLoader().getResource( imageName).getPath()
        final Image image = new Image(getClass().getClassLoader().getResourceAsStream("images" + File.separator + imageName));
        return new ImageView(image);
    }

    protected Text createText(final String message,final int fontSize, final Paint color) {
        final Text tempText = new Text(message);
        tempText.setFont(new Font(fontSize));
        tempText.setFill(color);
        return tempText;
    }

    protected Text createText(final String message,final int fontSize) {
        final Text tempText = new Text(message);
        tempText.setFont(new Font(fontSize));
        tempText.setFill(COLOR);
        return tempText;
    }

    protected Text createText(final String message) {
        final Text tempText = new Text(message);
        tempText.setFont(new Font(FONT_SIZE));
        tempText.setFill(COLOR);
        return tempText;
    }

    @Override
    public ViewInterface addClock(Pos position) {

        vBoxForClock.setAlignment(position);
        vBoxForClock.setPadding(new Insets(10));

        final Text time = new Text(DayUtils.getTime(Calendar.getInstance().getTime()));
        time.setId("time");
        vBoxForClock.getChildren().add(time);


        return this;
    }

    @Override
    public ViewInterface showCommand(String command) {

        if(!vBoxForClock.getChildren().contains(commandText)) {
            vBoxForClock.getChildren().add(commandText);
        }

        commandText.setText(command);
        FadeTransition ft = new FadeTransition(Duration.millis(2000), commandText);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.play();
        return this;
    }

    @Override
    public ViewInterface showMessage(final String message, final int secondsToShow) {
        final Text messageText = createText(message,40,Color.RED);
        borderPane.setCenter(messageText);

        FadeTransition ft = new FadeTransition(Duration.millis(secondsToShow*1000), messageText);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.play();
        return this;
    }

    @Override
    public ViewInterface addLockIcon(boolean locked) {
        try {
            hBoxAroundClock.setPadding(new Insets(10));
            if(locked) {
                hBoxAroundClock.getChildren().add(getImageView("lock.png"));
            }
            else {
                hBoxAroundClock.getChildren().add(getImageView("unlock.png"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public ViewInterface addEventData() {
        try {
            final List<UserWeekInfo> userWeekInfos = dataService.getUserWeekInfoList();

            int eventMaxCount = getMaxEventsForADay(userWeekInfos);

            int userIndex =0;

            for(UserWeekInfo userWeekInfo : userWeekInfos) {

                final Text userText = new Text(userWeekInfo.getName());
                userText.setId(userWeekInfo.getName());

                gridPane.add(userText, 0, rowIndex +(userIndex*eventMaxCount), 1, 1);

                int dayIndex =0;
                for(int i = 0; i< ApplicationUtils.getShowDayCount() && i<userWeekInfo.getDayInfos().length; i++ ) {

                    DayInfo dayInfo = userWeekInfo.getDayInfos()[i];

                    int eventIndex=0;
                    for(EventDTO event : dayInfo.getEvents()) {

                        String eventTextString = event.getEventName();
                        if(eventTextString.length() > 20) {
                            int lastSpaceIndex = eventTextString.lastIndexOf(' ');
                            eventTextString = eventTextString.substring(0, lastSpaceIndex) + "\n"
                                    + eventTextString.substring(lastSpaceIndex, eventTextString.length());
                        }
                        final Text eventText = new Text(eventTextString + "\n" + DayUtils.getTime(event.getStart()) + "\n");
                        eventText.setId(userWeekInfo.getName());

                        gridPane.add(eventText, dayIndex+1, rowIndex+eventIndex +(userIndex*eventMaxCount), 1, 1);

                        eventIndex++;
                    }
                    dayIndex++;
                }
                userIndex++;
            }
            rowIndex++;
        } catch (DataServiceException e) {
            LOG.error("Error in view" + e.getMessage(), e);
        }
        return this;
    }


    @Override
    public Node getView() {

        borderPane.getChildren().add(hBoxAroundClock);
        gridPane.setAlignment(Pos.TOP_RIGHT);
        borderPane.setTop(gridPane);

        return borderPane;
    }

    @Override
    public ViewInterface clear() {
        rowIndex=0;
        borderPane.getChildren().clear();
        gridPane.getChildren().clear();
        vBoxForClock.getChildren().clear();
        hBoxAroundClock.getChildren().clear();
        hBoxAroundClock.getChildren().add(vBoxForClock);
        commandText.setText("");
        return this;
    }

    protected int getMaxEventsForADay(final List<UserWeekInfo> userWeekInfos) {

        int maxCount = 0;
        for(UserWeekInfo userWeekInfo : userWeekInfos) {
            for(DayInfo dayInfo : userWeekInfo.getDayInfos()) {

                int count = 0;
                for(EventDTO event : dayInfo.getEvents()) {
                    count++;
                }
                if(count > maxCount) {
                    maxCount = count;
                }
            }
        }
        return maxCount+1;
    }
}
