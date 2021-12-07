package uet.oop.bomberman;


import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class MenuManager {
    private static final double WIDTH = 800;
    private static final double HEIGHT = 600;
    public AnchorPane mainPane;
    public Scene mainScene;
    public Stage mainStage;
    private BackgroundImage background;
    private List<MenuButton> buttons = new ArrayList<>();
    private RunnerSubScene LevelSubScene;
    private RunnerSubScene scoreSubscene =  new RunnerSubScene();

    private String choose;
    private String urlBackGround = "/img/background.png";
    private String urlLogo = "/img/logo.png";
    private String urlLogoScore = "/img/score.png";

    private Text stt_ = new Text("0");
    private Text score_text = new Text("0");
    private Text level_text = new Text("0");

    public static List<Text> sttLists = new ArrayList<>();
    public static List<Text> scoreTextLists = new ArrayList<>();
    public static List<Text> levelTextLists = new ArrayList<>();
    public static List<Scorelist> scorelists = new ArrayList<>();

    private ArrayList<List> listScore = new ArrayList<List>();

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

        //scoreSubscene.getPane().setStyle("-fx-background-color: #2a2d93");
        URL urlLogo = MenuManager.class.getResource(urlLogoScore);

        ImageView logoScore = new ImageView(urlLogo.toString());
        logoScore.setLayoutX(100);
        logoScore.setLayoutY(2);
        logoScore.setFitWidth(200);
        logoScore.setFitHeight(100);

        logoScore.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                logoScore.setEffect(new DropShadow());
            }
        });

        logoScore.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                logoScore.setEffect(null);
            }
        });

        Text stt = new Text("STT");
        stt.setLayoutX(100);
        stt.setLayoutY(100);

        Text level = new Text("LEVEL");
        level.setLayoutX(160);
        level.setLayoutY(100);

        Text kill = new Text("SCORE");
        kill.setLayoutX(250);
        kill.setLayoutY(100);

        updateScoreList();
        for (int i = 0; i < 5; i++) {
            Text newText = new Text("0");
            sttLists.add(newText);
            scoreTextLists.add(newText);
            levelTextLists.add(newText);
        }
        for (int i = 0; i < 5; i++) {
            Text newText = new Text(String.valueOf(i));
            newText.setLayoutX(100);
            newText.setLayoutY(i*30 + 130);
            sttLists.set(i, newText);
            newText = new Text(String.valueOf(scorelists.get(i).getLevel()));
            newText.setLayoutX(170);
            newText.setLayoutY(i*30 + 130);
            levelTextLists.set(i, newText);
            newText = new Text(String.valueOf(scorelists.get(i).getScore()));
            newText.setLayoutX(260);
            newText.setLayoutY(i*30 + 130);
            scoreTextLists.set(i, newText);
            scoreSubscene.getPane().getChildren().addAll(sttLists.get(i), levelTextLists.get(i), scoreTextLists.get(i));
        }


        scoreSubscene.getPane().getChildren().addAll(stt, level, kill, logoScore);
        mainPane.getChildren().add(scoreSubscene);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                updateScore();
            }
        };
        timer.start();


    }
    public void updateScore() {
        for (int i = 0; i < 5; i++) {
            scoreTextLists.get(i).setText(String.valueOf(scorelists.get(i).getScore()));
            levelTextLists.get(i).setText(String.valueOf(scorelists.get(i).getLevel()));
        }
    }

    public void updateScoreList() {
        try {
            String urlScoreFile = "/img/score.png";
            URL linkFile = MenuManager.class.getResource(urlScoreFile);
            String s = linkFile.toString().substring(0,linkFile.toString().length()-3) + "txt";
            s = s.substring(6);
            System.out.println(s);

            FileInputStream fileInputStream = new FileInputStream(s);
            Scanner sc = new Scanner(fileInputStream);
            while (sc.hasNextInt() ) {
                Scorelist newScorelist  = new Scorelist();
                int a = sc.nextInt();
                int b = sc.nextInt();
                newScorelist.setLevel(a);
                newScorelist.setScore(b);
                scorelists.add(newScorelist);
            }
            Collections.sort(scorelists, new Comparator<Scorelist>() {
                @Override
                public int compare(Scorelist o1, Scorelist o2) {
                    if (o1.getScore() > o2.getScore()) {
                        return -1;
                    }
                    return 0;
                }
            });

        } catch (Exception e) {
            System.out.println("Lỗi mở file");
        }
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