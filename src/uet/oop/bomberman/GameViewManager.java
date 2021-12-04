package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.SubClass.Constant;
import uet.oop.bomberman.graphics.Map;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
    private int power_up;
    private double speed;
    public boolean isClosed = false;
    private HBox menu;
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
        menu.getChildren().add(textHeal);
        menu.getChildren().add(textPowerup);
        menu.getChildren().add(textSpeed);

        // Tao root container
        Group root = new Group();
        root.setClip(new Rectangle(Sprite.SCALED_SIZE * (Constant.WIDTH / 2), Sprite.SCALED_SIZE * (Constant.HEIGHT + Constant.HEIGHT_MENU)));
        root.getChildren().addAll(menu, canvas);
        canvas.setLayoutY(Sprite.SCALED_SIZE * Constant.HEIGHT_MENU);

        // Tao scene
        scene = new Scene(root);

        // Them scene vao stage
        gameStage = new Stage();
        gameStage.setScene(scene);
        gameStage.show();
        // lang nghe di ban phim
        move(scene);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
                bomber.updatePosition(direc);
                if (heal < 0) {
                    stop();
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
        power_up = bomber.getPower_up();
        speed = bomber.getSpeed();
        textHeal.setText("Mạng: " + String.valueOf(heal));
        textPowerup.setText("Bom: " + String.valueOf(power_up));
        textSpeed.setText("Tốc độ: " + String.valueOf(speed));
    }

    public void creatNewGame() {
        gameStage.show();
    }

    public void setLevel(String choose) {
        Level = choose;
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
