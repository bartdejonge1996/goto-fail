package gui;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Class that creates and displays a modal (popup) view.
 * @author alex
 */
public abstract class ModalView {

    private Stage modalStage;
    private Scene displayScene;

    private final int width;
    private final int height;

    /**
     * Constructor.
     * @param rootPane root pane on top of which to display the modal.
     * @param width width of the modal window.
     * @param height height of the modal window.
     */
    public ModalView(RootPane rootPane, int width, int height) {
        this.width = width;
        this.height = height;
        this.modalStage = new Stage();
        this.modalStage.initModality(Modality.APPLICATION_MODAL);
        this.modalStage.initOwner(rootPane.getPrimaryStage());
    }

    /**
     * If a child view has been added then the modal view is shown.
     */
    public void displayModal() {
        if (this.displayScene != null) {
            this.modalStage.show();
        }
    }

    /**
     * Hides the modal from view.
     */
    public void hideModal() {
        modalStage.close();
    }

    /**
     * Set the view that will be displayed in the modal.
     * @param modalView Modal view pane
     */
    public void setModalView(Pane modalView) {
        this.displayScene = new Scene(modalView, width, height);
        this.modalStage.setScene(displayScene);
    }
}