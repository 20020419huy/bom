package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.List;

import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.SubClass.Constant;
import uet.oop.bomberman.graphics.*;
import uet.oop.bomberman.graphics.Map;

public class GameViewManager {
    private Scene scene;
    private Stage gameStage;
    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities;
    public static List<Entity> stillObjects;
    private Bomber bomber = null;
    private KeyCode direc = null;
    private String Level;
    private int heal;
    private double speed;
    private HBox menu;
    private ImageView winImg;
    private int winGame = 0;
    private int winGame_ = 0;
    public boolean isClosed = false;
    private RunnerSubScene healScene;
    private Map map = new Map();
    private Text textHeal = new Text("0");
    private Text textSpeed  = new Text("0");
    private Text textPowerup = new Text("0");


    public GameViewManager() {
        Level = "Level1";
    }

    public void initializeStage() {
        entities = new ArrayList<>();
        stillObjects = new ArrayList<>();
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * Constant.WIDTH, Sprite.SCALED_SIZE * Constant.HEIGHT);
        gc = canvas.getGraphicsContext2D();

        int level;
        if (Level != null) {
            level = Integer.parseInt(Level.substring(5));
        } else {
            level = 1;
        }
        // Tao map
        bomber = (Bomber) stillObjects.get(map.createMap(level));
        //tao menu
        menu = new HBox(Sprite.SCALED_SIZE);
        menu.setPrefWidth(Sprite.SCALED_SIZE * (Constant.WIDTH / 2));
        menu.setPrefHeight(Sprite.SCALED_SIZE * Constant.HEIGHT_MENU);
        menu.setStyle("-fx-background-color: #FA8072");
        textHeal.setFont(new Font("verdana", 15));
        textHeal.setFill(Color.rgb(47,226,226));
        textHeal.setX(15);
        textHeal.setY(30);
        textPowerup.setFont(new Font("verdana", 15));
        textPowerup.setFill(Color.rgb(47,226,226));
        textPowerup.setX(150);
        textPowerup.setY(30);
        textSpeed.setFont(new Font("verdana", 15));
        textSpeed.setFill(Color.rgb(47,226,226));
        textSpeed.setX(250);
        textSpeed.setY(30);




        // Tao root container
        Group root = new Group();
        root.setClip(new Rectangle(Sprite.SCALED_SIZE * (Constant.WIDTH / 2), Sprite.SCALED_SIZE * (Constant.HEIGHT + Constant.HEIGHT_MENU)));
        root.getChildren().addAll(menu, canvas, textHeal,textPowerup, textSpeed);
        canvas.setLayoutY(Sprite.SCALED_SIZE * Constant.HEIGHT_MENU);

        // Tao scene
        scene = new Scene(root);

        // Them scene vao stage
        gameStage = new Stage();
        gameStage.setScene(scene);
        gameStage.show();
        // lang nghe di ban phim
        move(scene);

        String ulrGameOver = "/img/gameover.png";
        URL link = GameViewManager.class.getResource(ulrGameOver);
        ImageView imageView = new ImageView(link.toString());
        imageView.setFitHeight(500);
        imageView.setFitWidth(500);

        String ulrWinGame = "/img/youwin.png";
        URL link1 = GameViewManager.class.getResource(ulrWinGame);


        //WinGamer
        for (int i = 0 ; i < stillObjects.size(); i++) {
            if (stillObjects.get(i) instanceof Balloon ) {
                winGame+=2;
            } else if (stillObjects.get(i) instanceof Oneal) {
                winGame+=1;
            }
        }
        System.out.println(winGame);
        URL linkFile = GameViewManager.class.getResource("/img/score.png");
        String s = linkFile.toString().substring(6,linkFile.toString().length() - 3) + "txt";
        System.out.println(s);



        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
                bomber.updatePosition(direc);
                if (winGame == Constant.score) {
                    Scorelist scorelist = new Scorelist();
                    scorelist.setScore(Constant.score);
                    scorelist.setLevel(level);
                    MenuManager.scorelists.add(scorelist);
                    Collections.sort(MenuManager.scorelists, new Comparator<Scorelist>() {
                        @Override
                        public int compare(Scorelist o1, Scorelist o2) {
                            if (o1.getScore() > o2.getScore()) {
                                return -1;
                            }
                            return 0;
                        }
                    });
                    try {

                        FileWriter fw = new FileWriter(s, true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write( String.valueOf(level) + " " + String.valueOf(Constant.score) + "\n");
                        Constant.score = 0;
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    stop();
                    winImg = new ImageView(link1.toString());
                    winImg.setLayoutY(95);
                    winImg.setLayoutX(80);
                    System.out.println(winImg.getFitHeight());
                    root.getChildren().add(winImg);
                }

                if (heal == 0) {

                    Scorelist scorelist = new Scorelist();
                    scorelist.setScore(Constant.score);
                    scorelist.setLevel(level);
                    MenuManager.scorelists.add(scorelist);
                    try {
                        FileWriter fw = new FileWriter(s, true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write( String.valueOf(level) + " " + String.valueOf(Constant.score) + "\n");
                        Constant.score = 0;
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (bomber.status == Constant.STATUS_DESTROYED ) stop();
                    HBox gameover = new HBox();
                    gameover.setPrefHeight(500);
                    gameover.setPrefWidth(500);
                    gameover.getChildren().add(imageView);
                    root.getChildren().add(gameover);
                    if (bomber.status == Constant.STATUS_DESTROY) bomber.status = Constant.STATUS_DESTROYED;
                }
            }


        };
        timer.start();
    }
    public void move(Scene scene) {
        scene.setOnKeyPressed((KeyEvent e) -> {
//            if(e.getCode() != Bomber.KEY_BOMB) {
            direc = e.getCode();
//            }
        });
        scene.setOnKeyReleased((KeyEvent e) -> {
//            if(e.getCode() != Bomber.KEY_BOMB) {
            direc = null;
//            } else {
//                direc = e.getCode();
//            }
        });
    }

    public void update() {
        if (!isClosed) {
            for (int i = 0; i < stillObjects.size(); i++) {
                stillObjects.get(i).update();
            }
            updateCanvas();
            updateSocore();
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }

    private void updateCanvas() {
        if(-1 * bomber.x + Sprite.SCALED_SIZE * (Constant.WIDTH / 4) <= 0 && -1 * bomber.x + Sprite.SCALED_SIZE * (Constant.WIDTH / 4) >= -1 * Sprite.SCALED_SIZE * (Constant.WIDTH / 2 + 1)) {
            canvas.setLayoutX(- bomber.x + Sprite.SCALED_SIZE * (Constant.WIDTH / 4));
        }
    }

    private void updateSocore() {
        heal = bomber.getHeal();
        speed = bomber.getSpeed();
        textHeal.setText("Mạng: " + String.valueOf(heal));
        textPowerup.setText("Điểm: " + String.valueOf(Constant.score));
        textSpeed.setText("Tốc độ: " + String.valueOf(speed));

    }

    public void creatNewGame() {
        gameStage.show();
    }

    public void setLevel(String choose) {
        Level = choose;
    }

    public void gameOver() {


    }

    public String getLevel() {
        return Level;
    }

    private Button creatButtonBack() {
        Button buttonBack = new Button();
        buttonBack.setText("Back");
        buttonBack.setStyle("-fx-background-color: Red");
        buttonBack.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                gameStage.close();
            }
        });
        return buttonBack;
    }
}
