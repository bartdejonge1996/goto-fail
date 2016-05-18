package gui.root;

import gui.centerarea.CounterGridPane;
import gui.centerarea.DirectorGridPane;
import gui.centerarea.TimelinesGridPane;
import gui.misc.TweakingHelper;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

/**
 * Class representing the center (main section) of the gui.
 * In other words, the time line view goes here.
 */
public class RootCenterArea extends VBox {
    
    private static final int DEFAULT_TIMELINES = 0;

    @Getter @Setter
    private int numberOfTimelines = 8;
    @Getter @Setter
    private int numberOfCounts = 400;
    @Getter
    private int countHeight = 10; // 10 works well, if you've changed this.
    @Getter
    private int counterWidth = 40;
    @Getter
    private int directorTimelineWidth = 100;
    @Getter
    private int timelineWidth = 100; // 100 works well, if you've changed this.
    @Getter
    private int topBarHeight = 100;
    @Getter
    private RootPane rootPane;
    @Getter
    private HBox timelinesPane; // stores the three timelines next to each other
    @Getter
    private HBox topPane; // stores the window above the timelines
    @Getter
    private Button newButton;
    @Getter
    private Button loadButton;

    /**
     * Trio of panes necessary for the main timeline, counter timeline, director timeline.
     */
    @Getter
    private ScrollPane mainTimelineScrollpane;
    @Getter
    private AnchorPane mainTimeLineAnchorPane;
    @Getter
    private TimelinesGridPane mainTimeLineGridPane;

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
     * Construct a new RootCenterArea.
     * @param rootPane the rootPane that this RootCenterArea is a part of.
     * @param numberOfTimelines the number of timelines in this RootCenterArea.
     * @param empty if the RootCenterArea should be initialized empty or not. Empty in this case
     means that there are buttons shown to create/load a project instead of timelines.
     */
    public RootCenterArea(RootPane rootPane, int numberOfTimelines, boolean empty) {
        if (empty) {
            /*
             * Technically speaking, these buttons will never ever be shown again.
             * We shouldn't remove them, however, as they are a nice catch
             * if the StartupModalView ever refuses to load.
             * Which still happens from time to time.
             */
            this.rootPane = rootPane;
            this.numberOfTimelines = numberOfTimelines;
            HBox buttonBox = new HBox();
            buttonBox.setSpacing(10);
            newButton = new Button("Create new project");
            loadButton = new Button("Load project");
            buttonBox.getChildren().addAll(newButton, loadButton);
            this.getChildren().addAll(buttonBox);
        } else {
            this.rootPane = rootPane;
            this.numberOfTimelines = numberOfTimelines;
            this.topPane = new HBox();
            this.timelinesPane = new HBox();
            this.getChildren().addAll(topPane, timelinesPane);

            this.topPane.getChildren().add(new Rectangle(counterWidth + directorTimelineWidth, topBarHeight));

            initCounterPane();
            initDirectorPane();
            initMainTimeLinePane();

            counterScrollpane.vvalueProperty().bindBidirectional(
                    mainTimelineScrollpane.vvalueProperty());
            directorScrollpane.vvalueProperty().bindBidirectional(
                    mainTimelineScrollpane.vvalueProperty());
        }
    }

    /**
     * Constructor class
     * @param rootPane parent pane passed through.
     */
    public RootCenterArea(RootPane rootPane) {
        this(rootPane, DEFAULT_TIMELINES, false);
    }

    /**
     * Initializes the central timeline in this stackpane.
     */
    private void initMainTimeLinePane() {
        mainTimelineScrollpane = new ScrollPane();
        mainTimeLineAnchorPane = new AnchorPane();
        mainTimeLineGridPane = new TimelinesGridPane(numberOfTimelines, numberOfCounts,
                timelineWidth,  countHeight);
        mainTimeLineAnchorPane.setLeftAnchor(mainTimeLineGridPane, 0.0);
        mainTimeLineAnchorPane.setRightAnchor(mainTimeLineGridPane, 0.0);
        mainTimeLineAnchorPane.setTopAnchor(mainTimeLineGridPane, 0.0);
        mainTimeLineAnchorPane.getChildren().add(mainTimeLineGridPane);
        mainTimelineScrollpane.setFitToWidth(true);
        mainTimelineScrollpane.setPrefWidth(TweakingHelper.GENERAL_SIZE);
        mainTimelineScrollpane.setContent(mainTimeLineAnchorPane);
        timelinesPane.getChildren().add(mainTimelineScrollpane);
    }

    /**
     * Initializes the counter timeline to the left.
     */
    private void initCounterPane() {
        counterScrollpane = new ScrollPane();
        counterAnchorPane = new AnchorPane();
        counterGridPane = new CounterGridPane(numberOfCounts, counterWidth, countHeight);
        counterAnchorPane.setLeftAnchor(counterGridPane, 0.0);
        counterAnchorPane.setRightAnchor(counterGridPane, 0.0);
        counterAnchorPane.setTopAnchor(counterGridPane, 0.0);
        counterAnchorPane.getChildren().add(counterGridPane);
        counterScrollpane.setContent(counterAnchorPane);
        counterScrollpane.setMinWidth(counterWidth);
        counterScrollpane.setFitToWidth(true);
        counterScrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        counterScrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        timelinesPane.getChildren().add(counterScrollpane);
    }

    /**
     * Initializes the director timeline to the left.
     */
    private void initDirectorPane() {
        directorScrollpane = new ScrollPane();
        directorAnchorPane = new AnchorPane();
        directorGridPane = new DirectorGridPane(numberOfCounts, directorTimelineWidth, countHeight);
        directorAnchorPane.setLeftAnchor(directorGridPane, 0.0);
        directorAnchorPane.setRightAnchor(directorGridPane, 0.0);
        directorAnchorPane.setTopAnchor(directorGridPane, 0.0);
        directorAnchorPane.getChildren().add(directorGridPane);
        directorAnchorPane.setStyle("-fx-background-color: green;");
        directorScrollpane.setContent(directorAnchorPane);
        directorScrollpane.setMinWidth(directorTimelineWidth);
        directorScrollpane.setFitToWidth(true);
        directorScrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        directorScrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        directorGridPane.setGridLinesVisible(false);
        timelinesPane.getChildren().add(directorScrollpane);
    }


}

