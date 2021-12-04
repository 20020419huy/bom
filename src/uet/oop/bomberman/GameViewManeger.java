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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.SubClass.Constant;
import uet.oop.bomberman.graphics.Map;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameViewManeger {
    private Scene scene;
    private Stage gameStage;
    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities;
    public static List<Entity> stillObjects;
    private Map map;
    private Bomber bomber = null;
    private KeyCode direc = null;

    public GameViewManeger() {
        initializeStage();
    }

    private void initializeStage() {
        entities = new ArrayList<>();
        stillObjects = new ArrayList<>();
        map = new Map();

        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * Constant.WIDTH, Sprite.SCALED_SIZE * Constant.HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);
        // Tao map
        bomber = (Bomber) stillObjects.get(map.createMap(1));
        // Tao scene
        scene = new Scene(root);
        gameStage = new Stage();
        gameStage.setScene(scene);
        // lang nghe di ban phim
        move(scene);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
                bomber.updatePosition(direc);
            }
        };
        timer.start();
    }

    public void move(Scene scene) {
        scene.setOnKeyPressed((KeyEvent e) -> {
            if(e.getCode() != Bomber.KEY_BOMB) {
                direc = e.getCode();
            }
        });
        scene.setOnKeyReleased((KeyEvent e) -> {
            if(e.getCode() != Bomber.KEY_BOMB) {
                direc = null;
            } else {
                direc = e.getCode();
            }
        });
    }

    public void update() {
        for (int i = 0; i < stillObjects.size(); i++) {
            stillObjects.get(i).update();
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }

    public void creatNewGame() {
        gameStage.show();
        System.out.println(stillObjects.size());
        System.out.println(entities.size());
    }

    public void clearn() {
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
