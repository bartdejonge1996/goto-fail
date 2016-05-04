package gui;

import javafx.geometry.Pos;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import lombok.Getter;
import lombok.Setter;

/**
 * Class representing the center (main section) of the gui.
 * In other words, the time line view goes here.
 */
public class RootCenterArea extends StackPane {

    @Getter @Setter
    private int numberOfTimelines = 8;
    @Getter @Setter
    private int numberOfCounts = 100;

    @Getter
    private RootPane rootPane;


    /**
     * Trio of panes necessary for the main timeline.
     */
    @Getter
    private ScrollPane mainTimelineScrollpane;
    @Getter
    private AnchorPane mainTimeLineAnchorPane;
    @Getter
    private TimelinesGridPane mainTimeLineGridPane;

    /**
     * Combination between an HBox and double trio of panes, necessary
     * for the more detailed counter display, and the director timeline.
     */

    @Getter
    private HBox counterAndDirectorPane;

    @Getter
    private ScrollPane counterScrollpane;
    @Getter
    private AnchorPane counterAnchorPane;
    @Getter
    private CounterGridPane counterGridPane;

    @Getter
    private ScrollPane directorScrollpane;
    @Getter
    private AnchorPane directorAnchorPane;
    @Getter
    private DirectorGridPane directorGridPane;

    /**
     * Constructor class
     * @param rootPane parent pane passed through.
     */
    RootCenterArea(RootPane rootPane) {

        this.rootPane = rootPane;

        initMainTimeLinePane();

        counterAndDirectorPane = new HBox();
        getChildren().add(counterAndDirectorPane);
        setAlignment(counterAndDirectorPane, Pos.CENTER_LEFT);
        counterAndDirectorPane.setMaxWidth(200);
        counterAndDirectorPane.setStyle("-fx-background-color: red"); // debugcolor for now

        initCounterPane();
        initDirectorPane();
        initScrollbar();
    }

    /**
     * Initializes the central timeline in this stackpane.
     */
    private void initMainTimeLinePane() {
        // main timeline panes
        mainTimelineScrollpane = new ScrollPane();
        mainTimeLineAnchorPane = new AnchorPane();
        mainTimeLineGridPane = new TimelinesGridPane(numberOfTimelines, numberOfCounts, 1000, 1000);
        mainTimeLineAnchorPane.setLeftAnchor(mainTimeLineGridPane, 0.0);
        mainTimeLineAnchorPane.setRightAnchor(mainTimeLineGridPane, 0.0);
        mainTimeLineAnchorPane.setTopAnchor(mainTimeLineGridPane, 0.0);
        mainTimeLineAnchorPane.getChildren().add(mainTimeLineGridPane);
        mainTimeLineAnchorPane.setMinHeight(1000);
        mainTimeLineAnchorPane.setMinWidth(1000);
        mainTimelineScrollpane.setFitToWidth(true);
        mainTimelineScrollpane.setContent(mainTimeLineAnchorPane);
        mainTimelineScrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        getChildren().add(mainTimelineScrollpane);
    }

    /**
     * Initializes the counter timeline to the left.
     */
    private void initCounterPane() {
        counterScrollpane = new ScrollPane();
        counterAnchorPane = new AnchorPane();
        counterGridPane = new CounterGridPane(numberOfCounts, 100, 1000);
        counterAnchorPane.setLeftAnchor(counterGridPane, 0.0);
        counterAnchorPane.setRightAnchor(counterGridPane, 0.0);
        counterAnchorPane.setTopAnchor(counterGridPane, 0.0);
        counterAnchorPane.getChildren().add(counterGridPane);
        counterScrollpane.setContent(counterAnchorPane);
        counterScrollpane.setFitToWidth(true);
        counterScrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        counterAnchorPane.setMinHeight(1000);
        counterScrollpane.setStyle("-fx-background-color: yellow"); // debugcolor for now
        counterAndDirectorPane.getChildren().add(counterScrollpane);
    }

    /**
     * Initializes the director timeline to the left.
     */
    private void initDirectorPane() {
        directorScrollpane = new ScrollPane();
        directorAnchorPane = new AnchorPane();
        directorGridPane = new DirectorGridPane(numberOfCounts, 100, 1000);
        directorAnchorPane.setLeftAnchor(directorGridPane, 0.0);
        directorAnchorPane.setRightAnchor(directorGridPane, 0.0);
        directorAnchorPane.setTopAnchor(directorGridPane, 0.0);
        directorAnchorPane.getChildren().add(directorGridPane);
        directorScrollpane.setContent(directorAnchorPane);
        directorScrollpane.setFitToWidth(true);
        directorScrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        directorAnchorPane.setMinHeight(1000);
        directorScrollpane.setStyle("-fx-background-color: orange"); // debugcolor for now
        counterAndDirectorPane.getChildren().add(directorScrollpane);
    }

    /**
     * Initializes the scrollbar for the main timeline.
     */
    private void initScrollbar() {
        ScrollBar scrollbar = new ScrollBar();
        scrollbar.setMin(0);
        scrollbar.setMax(1);
        scrollbar.maxWidthProperty().bind(widthProperty().subtract(200));
        mainTimelineScrollpane.hvalueProperty().bind(scrollbar.valueProperty());
        getChildren().add(scrollbar);
        setAlignment(scrollbar, Pos.BOTTOM_RIGHT);
    }


}

