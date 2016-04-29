package gui;

import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import lombok.Getter;

import java.util.ArrayList;

/**
 * Created by Bart.
 */
public class TimelinesGridPane extends GridPane {

    // The number of timelines in this custom gridpane
    @Getter
    private int numberOfTimelines;

    // The number of counts in this custom gridpane
    @Getter
    private int numberOfCounts;

    private ArrayList<SnappingPane> panes;

    /**
     * Constructor.
     * @param numberOfTimelines - The number of timelines in this gridpane
     * @param numberOfCounts - The number of counts in this gridpane
     * @param width - The width of this gridpane
     * @param height - the height of this gridpane
     */
    public TimelinesGridPane(int numberOfTimelines, int numberOfCounts,
                             double width, double height) {
        this.numberOfTimelines = numberOfTimelines;
        this.numberOfCounts = numberOfCounts;
        this.setWidth(width);
        this.setMinHeight(height);
        this.panes = new ArrayList<>();

        this.setMaxWidth(width);
        this.setMinWidth(width);
        this.setMaxHeight(height);
        this.setMinHeight(height);

        addPanes();

        this.setGridLinesVisible(true);
//        this.setHgap(50);
        this.setPadding(new Insets(25, 25, 25, 25));

        // set constraints
        for (int i = 0; i < numberOfTimelines; i++) {
            this.getColumnConstraints().add(new ColumnConstraints(width / numberOfTimelines));
        }
        for (int i = 0; i < numberOfCounts; i++) {
            this.getRowConstraints().add(new RowConstraints(50));
        }
    }

    /**
     * Add a camerashotblock to this gridpane.
     * @param block - the block to add
     */
    public void addCamerShotBlock(CameraShotBlock block) {
        this.add(block.getTimetableBlock(), block.getTimetableNumber(),
                (int) Math.round(block.getBeginCount()), 1,
                (int) Math.round(block.getEndCount() - block.getBeginCount()));
    }

    /**
     * Add snapping panes to grid.
     */
    private void addPanes() {
        panes = new ArrayList<>();

        for (int i = 0; i < numberOfCounts; i++) {
            for (int j = 0; j < numberOfTimelines; j++) {
                SnappingPane pane = new SnappingPane(i, j, 200, 50);
                this.add(pane, j, i);
                panes.add(pane);
            }
        }
    }

    /**
     * Get the pane in which the scene coordinates lie.
     * @param x - the x coordinate
     * @param y - the y coordinate
     * @return - the SnappingPane, null if none applicable
     */
    public SnappingPane getMyPane(double x, double y) {
        for (SnappingPane pane : panes) {
            Bounds bounds = pane.localToScene(pane.getBoundsInLocal());
            if (bounds.contains(x, y)) {
                if (((y - bounds.getMinY()) * 2) > pane.getHeight()) {
                    pane.setBottomHalf(true);
                } else {
                    pane.setBottomHalf(false);
                }
                return pane;
            }
        }
        return null;
    }
}