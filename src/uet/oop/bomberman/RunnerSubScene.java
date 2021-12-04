package uet.oop.bomberman;

import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.File;
import java.net.MalformedURLException;

public class RunnerSubScene extends SubScene {
    public boolean isHidden;
    private static String SUBSCENEBACKGROUND =  "-fx-background-color: #FF7F50";

    public RunnerSubScene() {
        super(new AnchorPane(), 400, 400);
        prefWidth(400);
        prefHeight(400);

        AnchorPane root2 = (AnchorPane) this.getRoot();

        root2.setStyle(SUBSCENEBACKGROUND);
        isHidden = true ;

        setLayoutX(1024);
        setLayoutY(130);

    }

    public void moveSubScene() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(this);

        if (isHidden) {

            transition.setToX(-700);
            isHidden = false;

        } else {

            transition.setToX(0);
            isHidden = true ;
        }


        transition.play();
    }

    public AnchorPane getPane() {
        return (AnchorPane) this.getRoot();
    }
}
