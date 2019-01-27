package dkarlsso.smartmirror.javafx.view.impl;

import dkarlsso.commons.quotes.FamousQuoteDTO;
import dkarlsso.smartmirror.javafx.view.*;
import javafx.scene.Node;
import javafx.scene.layout.*;

public class AbstractViewBuilder2 implements ViewBuilder2 {

    protected BorderPane borderPane = new BorderPane();

    protected GridPane gridPane = new GridPane();

    @Override
    public ViewBuilder clear() {
        return null;
    }

    @Override
    public ViewBuilder addNode(Node node, ViewPosition viewPosition, ViewPlacement viewPlacement) {

        final Pane box;

        if(ViewPlacement.HORIZONTAL == viewPlacement) {
            box = new HBox();
        }
        else {
            box = new VBox();
        }

        box.getChildren().add(node);

        return null;
    }

    @Override
    public ViewBuilder addComponent(ViewComponent viewComponent, ViewPosition viewPosition, ViewPlacement viewPlacement) {
        return null;
    }

    @Override
    public Node getView() {

//        gridPane.se

        return null;
    }

}
