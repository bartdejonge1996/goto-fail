package gui;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Class that represents the whole of top-level elements in the gui.
 * In other words, the file-edit-view-help menus and any buttons below that.
 */
class RootHeaderArea extends VBox {

    private RootPane rootPane;

    /**
     * RootHeaderArea Constructor.
     */
    RootHeaderArea(RootPane rootPane) {
        this.rootPane = rootPane;
        // border style to mark it, for debugging for now.
        setStyle("-fx-border-style: solid inside;"
                + "-fx-border-width: 1;");

        getChildren().add(initMenus());

        getChildren().add(initButtons());
        this.setPrefHeight(50);
    }

    private MenuBar initMenus() {
        MenuBar topMenuBar = new MenuBar();
        Menu menu1 = new Menu("File");
        Menu menu2 = new Menu("Edit");
        Menu menu3 = new Menu("View");
        Menu menu4 = new Menu("Help");
        topMenuBar.getMenus().addAll(menu1, menu2, menu3, menu4);
        return topMenuBar;
    }

    private HBox initButtons() {
        HBox topButtonBar = new HBox();
        topButtonBar.setSpacing(15);
        topButtonBar.setPadding(new Insets(5, 10, 5, 10));

        Button btn1 = new Button("A button");

        EventHandler eventHandler = new EventHandler() {
            @Override
            public void handle(Event evt) {
                String text = "";
                String eventType = evt.getEventType().toString();
                switch (eventType) {
                    case "MOUSE_CLICKED":
                        text = "Mouse Clicked";
                        break;
                    case "MOUSE_ENTERED":
                        text = "Mouse entered";
                        break;
                    case "MOUSE_EXITED":
                        text = "Mouse exited";
                        break;
                }
                System.out.println(text);
                rootPane.getRootFooterArea().getTextOutputLabel().setText(text);
            }
        };


        btn1.setOnMouseClicked(eventHandler);
        btn1.setOnMouseEntered(eventHandler);
        btn1.setOnMouseExited(eventHandler);

        Button btn2 = new Button("Another button");
        topButtonBar.getChildren().addAll(btn1, btn2);
        return topButtonBar;
    }

}