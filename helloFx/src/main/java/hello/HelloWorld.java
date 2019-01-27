package hello;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HelloWorld extends Application {

    private final Background focusBackground = new Background( new BackgroundFill( Color.web( "#000000" ), CornerRadii.EMPTY, Insets.EMPTY ) );

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });

        StackPane root = new StackPane();
        Canvas canvas = new Canvas(400,  300);
        root.getChildren().add(btn);
        root.getChildren().add(canvas);
        root.setStyle("-fx-background-color: rgba(11,11,11,0.96)");
        canvas.setStyle("-fx-background-color: rgba(11,11,11,0.96)");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }
}