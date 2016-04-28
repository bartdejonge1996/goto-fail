package gui;

import javafx.event.EventHandler;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Bart.
 */
public class CameraShotBlock extends ShotBlock {

    // The number of the timetable this shot belongs to
    @Getter
    private int timetableNumber;
    @Getter
    private String name;

    private CameraShotBlock thisBlock;

    /**
     * Constructor.
     * @param shotName the shot title
     * @param timetableNumber - the timeTableNumber this shot belongs to
     * @param rootCenterArea - the rootCenterArea this shot belongs to
     * @param beginCount - the begin count of this shot
     * @param endCount - the end count of this shot
     */
    public CameraShotBlock(String shotName, int timetableNumber, RootCenterArea rootCenterArea,
                           double beginCount, double endCount) {
        super(rootCenterArea, beginCount, endCount);
        this.name = shotName;
        this.timetableNumber = timetableNumber;
        thisBlock = this;

        this.getTimetableBlock().addEventHandler(ShotblockUpdatedEvent.SHOTBLOCK_UPDATED, e -> {
                this.setBeginCount(TimelinesGridPane.getRowIndex(
                        this.getTimetableBlock()), false);
                this.setEndCount(TimelinesGridPane.getRowSpan(
                        this.getTimetableBlock()) + this.getBeginCount(), false);
                this.timetableNumber = TimelinesGridPane.getColumnIndex(
                        this.getTimetableBlock());
            });
    }

    /**
     * Set the timetable number of this shotblock.
     * @param timetableNumber - the timetableNumber to set
     */
    public void setTimetableNumber(int timetableNumber) {
        this.timetableNumber = timetableNumber;
        this.recompute();
    }

    /**
     * Recompute the position in the grid and repaint with new values.
     */
    @Override
    public void recompute() {
        TimelinesGridPane.setColumnIndex(this.getTimetableBlock(), timetableNumber);
        super.recompute();
    }
}
