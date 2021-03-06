package gui.modal;

import gui.misc.TweakingHelper;
import gui.root.RootPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Superclass for a modal view that has a title, and a number of button-choices, nothing more.
 */
public class ButtonsOnlyModalView extends ModalView {

    /*
     * Tweakable styling variables.
     */

    // width and height of screen. 550 and 200 work very, very well.
    private static final int width = 550;
    private static final int height = 200;

    // variables for spacing
    private static final int topAreaHeight = 80;

    // simple background styles of the three main areas.
    private String topStyle = ModalUtilities.constructDefaultModalTopStyle(20);

    // variables for the buttons
    private int buttonSpacing = 20;

    // variables for the title label
    private static final int titlelabelOffsetFromLeft = 20;

    /*
     * Other variables
     */

    private VBox viewPane;
    protected Label titleLabel; // child classes can modify title
    protected HBox buttonPane; // child classes can modify buttons

    /**
     * Class constructor.
     * @param rootPane Parent modal pane.
     */
    public ButtonsOnlyModalView(RootPane rootPane) {
        this(rootPane, width, height);
    }

    /**
     * Class constructor.
     * @param rootPane Parent modal pane.
     * @param width width of modal.
     * @param height height of modal.
     */
    public ButtonsOnlyModalView(RootPane rootPane, int width, int height) {
        super(rootPane, width, height);
        initialize();
    }

    /**
     * Initializes all content and layout for this modal view.
     */
    private void initialize() {
        // Create a new VBox for vertical layout
        this.viewPane = new VBox();

        // force minimum size
        forceBounds(height, width);

        // Add content
        initializeTitleLabel();
        initializeButtonArea();

        // Display modal
        super.setModalView(this.viewPane);
        super.displayModal();
    }

    /**
     * Initializes title area content and layout.
     */
    private void initializeTitleLabel() {
        titleLabel = ModalUtilities.constructTitleLabel(topStyle, topAreaHeight);
        this.viewPane.getChildren().add(titleLabel);
    }

    /**
     * Initializes button area content and layout.
     */
    private void initializeButtonArea() {
        buttonPane = new HBox(TweakingHelper.GENERAL_SPACING);
        buttonPane.setSpacing(buttonSpacing);
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setPrefHeight(TweakingHelper.GENERAL_SIZE);
        buttonPane.setPadding(new Insets(0, titlelabelOffsetFromLeft,
                0, titlelabelOffsetFromLeft));

        this.viewPane.getChildren().add(buttonPane);
    }

}
