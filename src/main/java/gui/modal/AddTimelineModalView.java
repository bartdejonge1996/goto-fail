package gui.modal;

import java.util.ArrayList;

import data.Camera;
import data.CameraType;
import gui.events.AddTimelineEvent;
import gui.root.RootPane;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AddTimelineModalView extends ModalView {
    
    private static final int width = 300;
    private static final int height = 300;
    
    private TextField descriptionField;
    private ListView<Label> cameraList;
    private Button addTimelineButton;
    private VBox viewPane;
    private EventHandler<AddTimelineEvent> eventHandler;
    private ArrayList<Camera> cameras;
    
    public AddTimelineModalView(RootPane rootPane,
                                EventHandler<AddTimelineEvent> handler, 
                                ArrayList<Camera> cameras) {
        this(rootPane, handler, cameras, width, height);
    }
    
    /**
     * Construct a new AddTimelineModalView.
     * @param rootPane the rootPane that calls this modal
     * @param handler the handler to handle the result of this modal
     * @param cameras the cameras that are available
     * @param width the width of the modal screen
     * @param height the height of the modal screen
     */
    public AddTimelineModalView(RootPane rootPane,
                                EventHandler<AddTimelineEvent> handler,
                                ArrayList<Camera> cameras,
                                int width, 
                                int height) {
        super(rootPane, width, height);
        this.eventHandler = handler;
        this.cameras = cameras;
        initializeView();
    }
    
    /**
     * Initialize the view of this modal.
     */
    private void initializeView() {
        this.viewPane = new VBox(20);
        this.viewPane.getChildren().add(new Text("Create a camera timeline"));
        
        initFields();
        initCameraList();
        
        addTimelineButton = new Button("Add Timeline");
        addTimelineButton.setOnMouseClicked(this::addTimeline);
        this.viewPane.getChildren().add(addTimelineButton);
        
        super.setModalView(this.viewPane);
        super.displayModal();
    }
    
    /**
     * Initialize the fields.
     */
    private void initFields() {
        final Label descriptionLabel = new Label("Description: ");
        descriptionField = new TextField();
        HBox descriptionBox = new HBox();
        descriptionBox.getChildren().addAll(descriptionLabel, descriptionField);
        descriptionBox.setSpacing(10);
        
        this.viewPane.getChildren().add(descriptionBox);
    }
    
    /**
     * Initialize the list of available cameras.
     */
    private void initCameraList() {  
        final Label cameraLabel = new Label("Camera: ");
        cameraList = new ListView<Label>();
        cameraList.setMaxHeight(100);
        for (Camera camera: cameras) {
            cameraList.getItems().add(new Label(camera.getName()));
        }
        
        this.viewPane.getChildren().addAll(cameraLabel, cameraList);
    }
    
    private void addTimeline(MouseEvent event) {
        super.hideModal();
        this.eventHandler.handle(this.buildEvent());
    }

    /**
     * Build an AddTimelineEvent from the data entered by the user.
     * @return an AddTimelineEvent that can be used to build a timeline.
     */
    private AddTimelineEvent buildEvent() {
        String description = this.descriptionField.getText();
        int selectedIndex = cameraList.getSelectionModel().getSelectedIndex();
        Camera camera = cameras.get(selectedIndex);
        return new AddTimelineEvent(description, camera);
    }
    
}