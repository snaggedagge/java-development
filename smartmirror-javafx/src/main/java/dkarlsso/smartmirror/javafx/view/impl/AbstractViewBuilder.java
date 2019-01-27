package dkarlsso.smartmirror.javafx.view.impl;

import dkarlsso.smartmirror.javafx.model.application.ApplicationUtils;
import dkarlsso.commons.calendar.dto.EventDTO;
import dkarlsso.commons.date.DayUtils;
import dkarlsso.commons.javafx.font.CustomFont;
import dkarlsso.commons.javafx.font.FontLoader;
import dkarlsso.commons.quotes.FamousQuoteDTO;
import dkarlsso.commons.userinfo.DayInfo;
import dkarlsso.commons.userinfo.UserWeekInfo;
import dkarlsso.smartmirror.javafx.model.interfaces.DataService;
import dkarlsso.smartmirror.javafx.model.interfaces.DataServiceException;
import dkarlsso.smartmirror.javafx.view.ViewBuilder;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.List;

public abstract class AbstractViewBuilder implements ViewBuilder {

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

    private Text timeText;

    public AbstractViewBuilder(final DataService dataService) {
        this.dataService = dataService;
        gridPane.setHgap(40);
        hBoxAroundClock.getChildren().add(vBoxForClock);
        vBoxForClock.getChildren().add(commandText);
    }

    protected ImageView getImageView(final String imageName) throws FileNotFoundException {
        //PATH_IMAGE + "/" +
        //getClass().getClassLoader().getResource( imageName).getPath()
        final Image image = new Image(getClass().getClassLoader().getResourceAsStream(PATH_IMAGE + File.separator + imageName));
        return new ImageView(image);
    }

    protected Text createText(final String message, final int fontSize, final Paint color, final FontPosture fontPosture) {
        final Text tempText = new Text(message);
        tempText.setFont(Font.font(Font.getDefault().getFamily(),fontPosture,fontSize));
        tempText.setFill(color);
        return tempText;
    }

    protected Text createText(final String message, final Paint color, final Font font) {
        final Text tempText = new Text(message);
        tempText.setFont(font);
        tempText.setFill(color);
        return tempText;
    }

    protected Text createText(final String message,final int fontSize, final Paint color) {
        final Text tempText = new Text(message);
        tempText.setFont(FontLoader.getFont(CustomFont.OPENSANS_REGULAR, fontSize));
        //tempText.setFont(Font.font(Font.getDefault().getFamily(),fontSize));
        //tempText.setFont(new Font(fontSize));
        tempText.setFill(color);
        return tempText;
    }

    protected Text createText(final String message,final int fontSize) {
        final Text tempText = new Text(message);
        tempText.setFont(FontLoader.getFont(CustomFont.OPENSANS_REGULAR, fontSize));
        //tempText.setFont(Font.font(Font.getDefault().getFamily(),fontSize));
        tempText.setFill(COLOR);
        return tempText;
    }

    protected Text createText(final String message) {
        final Text tempText = new Text(message);
        tempText.setFont(FontLoader.getFont(CustomFont.OPENSANS_REGULAR, FONT_SIZE));
        //tempText.setFont(Font.font(Font.getDefault().getFamily(),FONT_SIZE));
        tempText.setFill(COLOR);
        return tempText;
    }

    @Override
    public ViewBuilder addClock(Pos position) {

        vBoxForClock.setAlignment(position);
        vBoxForClock.setPadding(new Insets(10));

        timeText = createText(DayUtils.getTime(Calendar.getInstance().getTime()), Color.WHITESMOKE, FontLoader.getFont(CustomFont.URBAN_JUNGLE, 54));
        timeText.setId("time");
        vBoxForClock.getChildren().add(timeText);


        return this;
    }

    @Override
    public ViewBuilder addDailyQuote(final FamousQuoteDTO famousQuote) {
        final VBox vBox = new VBox();

        vBox.getChildren().add(createText(famousQuote.getQuote(), Color.LIGHTBLUE, FontLoader.getFont(CustomFont.CASSANDRA, 20)));
        vBox.getChildren().add(createText(famousQuote.getAuthor(),Color.LIGHTCYAN,FontLoader.getFont(CustomFont.BULGATTI, 18)));
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(100));
        vBox.setSpacing(20);

        borderPane.setCenter(vBox);
        return this;
    }


    @Override
    public ViewBuilder addImageBelowClock(final String image, double reducementScale) {
        try {
            ImageView imageView = getImageView(image);
            double scale = imageView.getImage().getWidth()/timeText.getBoundsInLocal().getWidth();

            imageView.setFitWidth(imageView.getImage().getWidth()/scale*reducementScale);
            imageView.setFitHeight(imageView.getImage().getHeight()/scale*reducementScale);

            vBoxForClock.getChildren().add(imageView);
        } catch (FileNotFoundException e) {
        }
        return this;
    }


    @Override
    public ViewBuilder addImageBelowClock(final String image, int targetWidthPercent, int targetHeightPercent, double reducementScale) {
        try {
            final ImageView imageView = getImageView(image);

            int width = (int)imageView.getImage().getWidth();
            int height = (int)imageView.getImage().getHeight();
            byte[] buffer = new byte[width* height * 4];

            imageView.getImage().getPixelReader().getPixels(0, 0, width, height,
                    PixelFormat.getByteBgraInstance(), buffer, 0, 4*width);

            int targetWidth = (int)(((double) targetWidthPercent/100) * width);
            int targetHeight = (int)(((double) targetHeightPercent/100) * height);

            final WritableImage writableImage = new WritableImage(width, height);
            writableImage.getPixelWriter().setPixels( 0, 0, targetWidth, targetHeight,
                    PixelFormat.getByteBgraInstance(), buffer, 0, 4*width);

            final ImageView croppedImage = new ImageView(writableImage);
            double scale = imageView.getImage().getWidth()/timeText.getBoundsInLocal().getWidth();

            croppedImage.setFitWidth(imageView.getImage().getWidth()/scale*reducementScale);
            croppedImage.setFitHeight(imageView.getImage().getHeight()/scale*reducementScale);

            vBoxForClock.getChildren().add(croppedImage);
        } catch (FileNotFoundException e) {
        }
        return this;
    }

    @Override
    public ViewBuilder showCommand(String command) {

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
    public ViewBuilder showMessage(final String message, final int secondsToShow) {
        final Text messageText = createText(message,40,Color.RED);
        borderPane.setCenter(messageText);

        FadeTransition ft = new FadeTransition(Duration.millis(secondsToShow*1000), messageText);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.play();
        return this;
    }

    @Override
    public ViewBuilder addLockIcon(boolean locked) {
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
    public ViewBuilder addEventData() {
        try {
            final List<UserWeekInfo> userWeekInfos = dataService.getUserWeekInfoList();

            int eventMaxCount = getMaxEventsForADay(userWeekInfos);

            int userIndex =0;

            for(UserWeekInfo userWeekInfo : userWeekInfos) {

                final Text userText = new Text(userWeekInfo.getName());
                userText.setFont(FontLoader.getFont(CustomFont.BULGATTI, 16));
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
//                        final Text eventText = new Text(eventTextString + "\n" + DayUtils.getTime(event.getStart()) + "\n");

                        final Text eventText = createText(eventTextString + "\n" + DayUtils.getTime(event.getStart()) + "\n"
                                , 14, Color.GREEN);

                        eventText.setId(userWeekInfo.getName() + "-events");
//                        eventText.setFont(Font.getDefault());

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
    public ViewBuilder clear() {
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
