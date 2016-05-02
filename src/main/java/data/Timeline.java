package data;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Bart.
 * Abstract class for timelines.
 */
public abstract class Timeline {

    // Logger
    private static Logger logger = LogManager.getLogger();

    // Description of this Timeline.
    @Getter
    private String description;

    // The project this timeline is currently in
    @Setter
    private ScriptingProject project;

    /**
     * Constructor.
     * @param description the description of this timeline
     * @param project the project that contains this timeline
     */
    public Timeline(String description, ScriptingProject project) {
        logger.info("Creating new Timeline.");
        this.description = description;
        this.project = project;
    }

    /**
     * Checks overlap between two shots. If the two shots are overlapping, the two shots will have
     * their overlapping variables set to true.
     * @param s1 the first Shot to check overlap
     * @param s2 the other Shot to check overlap
     * @param seconds the seconds to use for the margin
     * @return true when the two shots are overlapping, false if not
     */
    public boolean checkOverlap(Shot s1, Shot s2, double seconds) {
        logger.info("Checking overlap between '{}' and '{}'", s1.getName(), s2.getName());
        if (s1.areOverlapping(s2, project.secondsToCounts(seconds))) {
            logger.info("Shots overlap.");
            s1.setOverlapping(true);
            s2.setOverlapping(true);
            return true;
        }
        logger.info("Shots don't overlap.");
        return false;
    }

    public ScriptingProject getProject() {
        return project;
    }
}
