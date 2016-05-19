package data;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

/**
 * Class that represents a camera type.
 */
@EqualsAndHashCode
@ToString
@XmlRootElement(name = "cameraType")
@Log4j2
public class CameraType {

    // Name of the cameraType
    @Getter @Setter
    private String name;

    // Description of the cameraType, things like serial number.
    @Getter @Setter
    private String description;

    /* Number of counts this camera needs at maximum to move to a new position.
     * This defines the minimum margin between two consecutive shots
     * Defined in seconds */
    @Getter @Setter
    private double movementMargin;
    
    /**
     * Default constructor.
     */
    public CameraType() {
        this("", "", -1);
    }
    
    public CameraType(CameraType type) {
        this.name = type.getName();
        this.description = type.getDescription();
        this.movementMargin = type.getMovementMargin();
    }

    /**
     * Constructor.
     * @param name - the name of this camera
     * @param description - a description of this camera
     * @param movementMargin - the minimum margin of this camera
     */
    public CameraType(String name, String description, double movementMargin) {
        this.name = name;
        this.description = description;
        this.movementMargin = movementMargin;
        log.debug("Created new CameraType(name={}, description={}, movementMargin={})",
            name, description, movementMargin);
    }
}
