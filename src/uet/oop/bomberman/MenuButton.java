package uet.oop.bomberman;


import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import uet.oop.bomberman.entities.SubClass.Constant;

import javax.sound.sampled.Clip;

public class MenuButton extends Button{

    //private final String FONT_PATH = "/img/kenvector_future.ttf";
    private final String BUTTON_PRESSED_STYLE = "-fx-background-color: #FA8072";
    private final String BUTTON_FREE_STYLE = "-fx-background-color: #FFA07A";
    private Clip SOUND_BUTTON = Constant.getSound(Constant.URL_SOUND_BUTTON);

    public boolean isPress = false;
    public MenuButton(String text) {
        setText(text);
        //setButtonFont();
        setPrefWidth(190);
        setPrefHeight(49);
        setStyle(BUTTON_FREE_STYLE);
        initializeButtonListeners();

    }

 /*   private void setButtonFont() {

        //setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 23));
        setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 23));
    }*/

    public void setButtonPressedStyle() {
        setStyle(BUTTON_PRESSED_STYLE);
        setPrefHeight(45);
        setLayoutY(getLayoutY() + 4);

    }

    public void setButtonReleasedStyle() {
        setStyle(BUTTON_FREE_STYLE);
        setPrefHeight(45);
        setLayoutY(getLayoutY() - 4);

    }

    private void initializeButtonListeners() {

        setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonPressedStyle();
                }
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonReleasedStyle();
                }

            }
        });

        setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                setEffect(new DropShadow());
                if (!SOUND_BUTTON.isRunning()) {
                    SOUND_BUTTON.loop(1);
                }
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                setEffect(null);

            }
        });




    }


}