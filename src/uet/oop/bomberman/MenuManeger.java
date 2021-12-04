package uet.oop.bomberman;


import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import uet.oop.bomberman.graphics.SpriteSheet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MenuManeger {
    private static final double WIDTH = 800;
    private static final double HEIGHT = 600;
    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;
    private BackgroundImage background;
    private List<MenuButton> buttons = new ArrayList<>();
    private String url = "/textures/classic.png";


    private void creatBackground() {
        File file = new File("E:\\dictionary\\menu1\\src\\main\\java\\com\\example\\menu1\\img\\background.jpg");
        String url = null;
        try {
            url = file.toURI().toURL().toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        javafx.scene.image.Image backgroundImage = new Image(url);
        background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(background));
        /*try {
            URL a = MenuManeger.class.getResource(url);
            Image backgroundImage = new Image(a);

            background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
            mainPane.setBackground(new Background(background));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }*/
    }

    private void creatButton() {
        creatButtonPlay();
        creatButtonLevel();
        creatButtonExit();
        creatButtonScore();
    }

    private void creatButtonPlay() {
        MenuButton buttonPlay = new MenuButton("Play");
        buttonPlay.setLayoutX(100);
        buttonPlay.setLayoutY(100);
        buttonPlay.setOnMouseClicked(new EventHandler<MouseEvent>() {
            GameViewManeger gameViewManeger;
            @Override
            public void handle(MouseEvent event) {
                gameViewManeger = new GameViewManeger();
                gameViewManeger.creatNewGame();
                System.out.println("Clicked");
                //mainStage.close();
            }
        });
        buttons.add(buttonPlay);
    }

    private void creatButtonLevel() {
        MenuButton buttonLevel = new MenuButton("Level");
        buttonLevel.setLayoutX(100);
        buttonLevel.setLayoutY(100 + 100);
        buttons.add(buttonLevel);
    }

    private void creatButtonScore() {
        MenuButton buttonScore = new MenuButton("Score");
        buttonScore.setLayoutX(100);
        buttonScore.setLayoutY(100 + 200);
        buttons.add(buttonScore);
    }

    private void creatButtonExit() {
        MenuButton buttonExit = new MenuButton("Exit");
        buttonExit.setLayoutX(100);
        buttonExit.setLayoutY(100 + 300);
        buttonExit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mainStage.close();
            }
        });
        buttons.add(buttonExit);
    }

    public MenuManeger() {
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        creatButton();
        mainPane.getChildren().addAll(buttons);
        mainStage.setScene(mainScene);
        creatBackground();
    }

    public void setplayStage(Stage stage) {
        mainStage = stage;
    }

    public Stage getMainStage() {
        return mainStage;
    }

}
