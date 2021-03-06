package gui.root;

import gui.centerarea.CounterGridPane;
import gui.centerarea.DirectorGridPane;
import gui.centerarea.TimelinesGridPane;
import gui.misc.TweakingHelper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

/**
 * Class representing the center (main section) of the gui.
 * In other words, the time line view goes here.
 */
public class RootCenterArea extends VBox {
    
    private static final int DEFAULT_TIMELINES = 0;

    @Getter @Setter
    private int numberOfTimelines = 8;
    @Getter @Setter
    private int numberOfCounts = 200;
    @Getter
    private int countHeight = 10; // 10 works well, if you've changed this.
    @Getter
    private int counterWidth = 40;
    @Getter
    private int directorTimelineWidth = 150;
    @Getter
    private int timelineWidth = 100; // 100 works well, if you've changed this.
    @Getter
    private int topBarHeight = 20;
    @Getter
    private RootPane rootPane;
    @Getter
    private HBox timelinesPane; // stores the three timelines next to each other
    @Getter
    private HBox topPane; // stores the window above the timelines
    @Getter
    private ScrollPane topScrollPane; // stores scrollable part of window above timelines
    @Getter
    private HBox topScrollPaneContent; // stores content of scrollable part above timelines
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
            this.topScrollPane = new ScrollPane();
            this.topScrollPaneContent = new HBox();
            this.timelinesPane = new HBox();
            this.getChildren().addAll(topPane, timelinesPane);

            initCounterPane();
            initDirectorPane();
            initMainTimeLinePane();
            initTopPane();
            initScrollbinding();

            this.setOnMousePressed(
                event -> {
                    rootPane.getControllerManager().setActiveShotBlock(null);
                });
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
     * Binds pane scrolling together bidirectionally,
     * so all the major scrollpanes (top, and the timelines, and the counter)
     * can be scrolled as one huge area.
     */
    private void initScrollbinding() {
        counterScrollpane.vvalueProperty().bindBidirectional(
                mainTimelineScrollpane.vvalueProperty());
        directorScrollpane.vvalueProperty().bindBidirectional(
                mainTimelineScrollpane.vvalueProperty());
        topScrollPane.hvalueProperty().bindBidirectional(
                mainTimelineScrollpane.hvalueProperty());
    }

    /**
     * Adds content to top pane.
     */
    private void initTopPane() {
        // basic toppane style: white with a gray border. Simple.
        this.topPane.setStyle("-fx-border-width: 0 0 1px 0; -fx-border-color: rgba(0,0,0,0.25);");
        // filler for the counter timeline.
        this.topPane.getChildren().add(new Rectangle(counterWidth,
                topBarHeight, Color.WHITE));
        // label for director timeline: white with gray border again. Simple.
        Label directorLabel = new Label("Director");
        directorLabel.setStyle("-fx-border-width: 0 1px 0 0; -fx-border-color: rgba(0,0,0,0.40);");
        directorLabel.setMinWidth(directorTimelineWidth);
        directorLabel.setPrefWidth(directorTimelineWidth);
        directorLabel.setMaxWidth(directorTimelineWidth);
        directorLabel.setPrefHeight(topBarHeight);
        directorLabel.setAlignment(Pos.CENTER);
        directorLabel.setPadding(new Insets(topBarHeight / 2.0, 0,
                topBarHeight / 2.0, 0));

        // initialize scrollable part of toppane.
        initTopScrollablePane();

        // add everything together.
        this.topPane.getChildren().addAll(directorLabel, topScrollPane);
    }

    /**
     * Adds content to scrollable part of top pane.
     */
    private void initTopScrollablePane() {
        // scrollable pane
        this.topScrollPaneContent.minWidthProperty().bind(mainTimeLineAnchorPane.widthProperty());
        this.topScrollPaneContent.prefWidthProperty().bind(mainTimeLineAnchorPane.widthProperty());
        this.topScrollPaneContent.maxWidthProperty().bind(mainTimeLineAnchorPane.widthProperty());
        this.topScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.topScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // labels for camera timelines
        for (int i = 0; i < numberOfTimelines; i++) {
            String name = getRootPane().getControllerManager().getScriptingProject()
                    .getCameras().get(i).getName();
            Label label = new Label(name);
            label.setAlignment(Pos.CENTER);
            label.setPrefWidth(TweakingHelper.GENERAL_SIZE);
            label.setPadding(new Insets(topBarHeight / 2.0, 0,
                    topBarHeight / 2.0, 0));

            this.topScrollPaneContent.getChildren().add(label);
        }

        // Consume vertical scrolling event, which basically disables vertical scrolling.
        this.topScrollPane.addEventFilter(ScrollEvent.SCROLL,
            e -> {
                if (e.getDeltaY() != 0) {
                    e.consume();
                }
            });

        // add everything together
        this.topScrollPane.setContent(topScrollPaneContent);
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
        mainTimelineScrollpane.setPadding(new Insets(0,0,0,0));
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
        counterScrollpane.setPadding(new Insets(0,0,0,0));
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
        directorScrollpane.setContent(directorAnchorPane);
        directorScrollpane.setMinWidth(directorTimelineWidth);
        directorScrollpane.setFitToWidth(true);
        directorScrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        directorScrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        directorGridPane.setGridLinesVisible(false);
        directorScrollpane.setPadding(new Insets(0,0,0,0));
        directorScrollpane.setStyle("-fx-border-width: 0 1px 0 0.5px;"
            + "-fx-border-color: rgba(0,0,0,0.40);");
        timelinesPane.getChildren().add(directorScrollpane);
    }

}

