package gui.headerarea;

import gui.root.RootHeaderArea;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import lombok.Getter;

/**
 * Class that is responsible for managing the actions associated with a tool.
 * @author alex
 */
public class ToolButton {

    private String name;
    @Getter
    private Button button;

    private InnerShadow firstEffect;
    private InnerShadow secondEffect;
    private DropShadow thirdEffect;

    private EventHandler<MouseEvent> clickHandler;

    /**
     * Constructor.
     * @param toolName the tool's name
     * @param headerArea Header area to which to add this tool button
     * @param clickHandler Event handler for click on the tool button
     */
    public ToolButton(String toolName, RootHeaderArea headerArea,
                      EventHandler<MouseEvent> clickHandler) {
        this.name = toolName;
        this.clickHandler = clickHandler;
        initializeButton(headerArea);
    }

    /**
     * Instantiates the button and adds it to the tool view.
     * @param headerArea Area in which the tool view is found.
     */
    private void initializeButton(RootHeaderArea headerArea) {
        this.button = new Button(this.name);

        this.button.setOnMouseClicked(clickHandler);

        firstEffect = new InnerShadow(BlurType.GAUSSIAN, Color.rgb(0, 0, 0, 0.15),
                1, 1, -2, -1);
        thirdEffect = new DropShadow(BlurType.GAUSSIAN, Color.rgb(0, 0, 0, 0.15),
                10, 0.1, 2, 1);

        thirdEffect.setInput(firstEffect);
        this.button.setEffect(thirdEffect);

        headerArea.getToolView().addToolButton(this);
    }

    /**
     * Enable the button.
     */
    public void enableButton() {
        this.button.setDisable(false);
    }

    /**
     * Disable the button.
     */
    public void disableButton() {
        this.button.setDisable(true);
    }
}
