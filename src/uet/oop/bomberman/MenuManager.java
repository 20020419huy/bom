package uet.oop.bomberman;


import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import uet.oop.bomberman.graphics.SpriteSheet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.TextEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MenuManager {
    private static final double WIDTH = 800;
    private static final double HEIGHT = 600;
    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;
    private BackgroundImage background;
    private List<MenuButton> buttons = new ArrayList<>();
    private RunnerSubScene LevelSubScene;
    private RunnerSubScene scoreSubscene;
    private String choose;
    private String urlBackGround = "/img/background.png";
    private String urlLogo = "/img/logo.png";
    private void creatBackground() {
        URL link = MenuManager.class.getResource(urlBackGround);
        javafx.scene.image.Image backgroundImage = new Image(link.toString());
        background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(background));
    }

    private void creatLevelSubScene() {
        LevelSubScene = new RunnerSubScene();
        Label text = new Label("LEVEL");
        text.setTextFill(Color.web("#0076a3"));
        text.setLayoutX(150);
        text.setLayoutY(20);
        text.setFont(new Font("kenvector", 30 ));
        text.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                text.setEffect(new DropShadow());
            }
        });

        text.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                text.setEffect(null);
            }
        });

        List<MenuButton> arrButton = new ArrayList<>();
        double layoutY = 100;
        for (int i = 0; i < 3; i++) {
            MenuButton button = new MenuButton("Level" + (i + 1));
            button.setLayoutX(100);
            button.setLayoutY(layoutY);
            layoutY += 70;
            arrButton.add(button);
        }

        MenuButton Level1 = arrButton.get(0);
        MenuButton Level2 = arrButton.get(1);
        MenuButton Level3 = arrButton.get(2);

        Level1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                choose = Level1.getText();
                if (!Level1.isPress && !Level2.isPress && !Level3.isPress) {
                    Level1.setButtonPressedStyle();
                    Level1.isPress = true;
                    Level2.isPress = false;
                    Level3.isPress = false;
                } else if (Level1.isPress && !Level2.isPress && !Level3.isPress) {
                    Level1.setButtonReleasedStyle();
                    Level1.isPress = false;
                }
                System.out.println("1" +Level1.isPress);
            }

        });

        Level2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                choose = Level2.getText();
                if (!Level1.isPress && !Level2.isPress && !Level3.isPress) {
                    Level2.setButtonPressedStyle();
                    Level2.isPress = true;
                    Level1.isPress = false;
                    Level3.isPress = false;
                } else if (!Level1.isPress && Level2.isPress && !Level3.isPress) {
                    Level2.setButtonReleasedStyle();
                    Level2.isPress = false;
                }
                System.out.println("2" + Level2.isPress);
            }
        });

        Level3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                choose = Level3.getText();
                if (!Level1.isPress && !Level2.isPress && !Level3.isPress) {
                    Level3.setButtonPressedStyle();
                    Level3.isPress = true;
                    Level1.isPress = false;
                    Level2.isPress = false;
                } else if (!Level1.isPress && !Level2.isPress && Level3.isPress) {
                    Level3.setButtonReleasedStyle();
                    Level3.isPress = false;
                }
                System.out.println("3" + Level3.isPress);
            }
        });

        LevelSubScene.getPane().getChildren().addAll(arrButton);
        LevelSubScene.getPane().getChildren().add(text);
        mainPane.getChildren().add(LevelSubScene);
    }

    private void creatScoreSubscene() {
        scoreSubscene = new RunnerSubScene();
        scoreSubscene.getPane().setStyle("-fx-background-color: #2a2d93");
        mainPane.getChildren().add(scoreSubscene);
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
            GameViewManager gameViewManager = null;
            @Override
            public void handle(MouseEvent event) {
                if (gameViewManager != null) {
                    gameViewManager.isClosed = true;
                }
                gameViewManager = new GameViewManager();
                gameViewManager.setLevel(choose);
                gameViewManager.initializeStage();
                gameViewManager.creatNewGame();
                //System.out.println(gameViewManager.getLevel());
                //System.out.println(choose.substring(4));
                //mainStage.close();
            }
        });
        buttons.add(buttonPlay);
    }

    private void creatButtonLevel() {
        MenuButton buttonLevel = new MenuButton("Level");
        buttonLevel.setLayoutX(100);
        buttonLevel.setLayoutY(100 + 100);
        buttonLevel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
               LevelSubScene.moveSubScene();
               if (!LevelSubScene.isHidden && !scoreSubscene.isHidden) {
                   scoreSubscene.moveSubScene();
               }
            }
        });
        buttons.add(buttonLevel);
    }

    private void creatButtonScore() {
        MenuButton buttonScore = new MenuButton("Score");
        buttonScore.setLayoutX(100);
        buttonScore.setLayoutY(100 + 200);
        buttonScore.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                scoreSubscene.moveSubScene();
               if(!scoreSubscene.isHidden && !LevelSubScene.isHidden) {
                   LevelSubScene.moveSubScene();
               }
            }
        });
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

    private void creatLogo() {
        URL link = MenuManager.class.getResource(urlLogo);
        ImageView imageLogo = new ImageView(link.toString());

        imageLogo.setLayoutX(400);
        imageLogo.setLayoutY(0);

        imageLogo.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                imageLogo.setEffect(new DropShadow());
            }
        });

        imageLogo.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                imageLogo.setEffect(null);
            }
        });

        mainPane.getChildren().add(imageLogo);
    }

    public MenuManager() {
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        creatButton();
        creatLogo();
        creatLevelSubScene();
        creatScoreSubscene();
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

    public String getChoose() {
        return choose;
    }

}
