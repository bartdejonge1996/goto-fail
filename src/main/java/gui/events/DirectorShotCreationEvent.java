package gui.events;

import lombok.Getter;

import java.util.List;

/**
 * Class that represents a director shot creation event.
 * @author alex
 */
public class DirectorShotCreationEvent extends ModalEvent {

    @Getter
    private String shotName;
    @Getter
    private String shotDescription;
    @Getter
    private List<Integer> camerasInShot;
    @Getter
    private double shotStart;
    @Getter
    private double shotEnd;
    @Getter
    private double frontPadding;
    @Getter
    private double endPadding;

    /**
     * Constructor.
     * @param shotName Name of the shot
     * @param shotDescription Description of the shot
     * @param camerasInShot List of cameras in the shot
     * @param start Start count of the shot
     * @param end End count of the shot
     * @param frontPadding Padding at the front
     * @param endPadding Padding at the back
     */
    public DirectorShotCreationEvent(String shotName, String shotDescription,
                                     List<Integer> camerasInShot, double start,
                                     double end, double frontPadding, double endPadding) {
        super(ModalEventType.CONFIRM);
        this.shotName = shotName;
        this.shotDescription = shotDescription;
        this.camerasInShot = camerasInShot;
        this.shotStart = start;
        this.shotEnd = end;
        this.frontPadding = frontPadding;
        this.endPadding = endPadding;
    }
}
