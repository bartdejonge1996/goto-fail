package gui.centerarea;

import java.util.ArrayList;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;


/**
 * Class that represents the grid pane in the scrollable camera centerarea.
 */
public class TimelinesGridPane extends ScrollableGridPane {


    private ArrayList<SnappingPane> panes;
    private int gridLineSkips = 4;

    /**
     * Constructor.
     * @param numberOfHorizontalGrids - number of horizontal grid lanes.
     * @param numberOfVerticalGrids - number of vertical grid lanes.
     * @param horizontalElementMinimumSize -  minimal size of horizontal grid lanes.
     * @param verticalElementSize -  size of vertical grid lanes.
     * @param offsetFromLeft - how much offset from the left due to coverage by directortimeline.
     */
    public TimelinesGridPane(int numberOfHorizontalGrids, int numberOfVerticalGrids,
                             int horizontalElementMinimumSize, int verticalElementSize,
                             int offsetFromLeft) {

        super(numberOfHorizontalGrids, numberOfVerticalGrids,
                horizontalElementMinimumSize, verticalElementSize);

        // add padding to the left for overlapping sidebars
        this.setPadding(new Insets(0,0,0,offsetFromLeft));

        // add snapping panes
        addPanes();
    }

    /**
     * Add a camerashotblock to this gridpane.
     * @param block - the block to add
     */
    public void addCameraShotBlock(CameraShotBlock block) {
        this.add(block.getTimetableBlock(), block.getTimetableNumber(),
                (int) Math.round(block.getBeginCount()), 1,
                (int) Math.round(block.getEndCount() - block.getBeginCount()));
    }

    /**
     * Remove a CameraShotBlock from this gridpane.
     * @param block the block to remove
     */
    public void removeCameraShotBlock(CameraShotBlock block) {
        this.getChildren().remove(block.getTimetableBlock());
    }

    /**
     * Add snapping panes to grid. Also apply line separators to grid, once every few skips.
     */
    private void addPanes() {
        panes = new ArrayList<>();
        int c;
        for (int i = 0; i < getNumberOfHorizontalGrids(); i++) {
            c = 1;
            for (int j = 0; j < getNumberOfVerticalGrids(); j++) {
                SnappingPane pane = new SnappingPane(j, i);
                this.add(pane, i, j);
                panes.add(pane);
                if (c > gridLineSkips) {
                    pane.getStyleClass().add("timeline_Background_Lines");
                    c = 2;
                } else {
                    pane.getStyleClass().add("timeline_Background_Empty");
                    c++;
                }
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
