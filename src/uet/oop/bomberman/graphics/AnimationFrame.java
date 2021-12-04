package uet.oop.bomberman.graphics;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import uet.oop.bomberman.GameViewManager;
import uet.oop.bomberman.entities.DynamicEntity;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.SubClass.Constant;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class AnimationFrame {
    private boolean isPressed = false;
    private Timeline timeline = null;
    private Entity entity = null;
    private double time;
    private ArrayList<Sprite> frames;
    private ArrayList<Sprite> framesUp;
    private ArrayList<Sprite> framesRight;
    private ArrayList<Sprite> framesDown;
    private ArrayList<Sprite> framesLeft;
    private ArrayList<Sprite> framesDestroy;
    public AnimationFrame(Entity entity, double time, ArrayList<Sprite> framesUp, ArrayList<Sprite> framesRight, ArrayList<Sprite> framesDown, ArrayList<Sprite> framesLeft, ArrayList<Sprite> frameDestroy) {
        this.entity = entity;
        this.time = time;
        this.framesUp = framesUp;
        this.framesRight = framesRight;
        this.framesDown = framesDown;
        this.framesLeft = framesLeft;
        this.framesDestroy = frameDestroy;
    }

    public AnimationFrame(Entity entity, int time ,ArrayList<Sprite> framesDestroy) {
        this.entity = entity;
        this.time = time;
        this.framesDestroy = framesDestroy;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public void loadFrame() {
        if(entity.status == Constant.STATUS_UP) {
            frames = framesUp;
        } else if (entity.status == Constant.STATUS_RIGHT) {
            frames = framesRight;
        } else if (entity.status == Constant.STATUS_DOWN) {
            frames = framesDown;
        } else if(entity.status == Constant.STATUS_LEFT) {
            frames = framesLeft;
        } else if (entity.status == Constant.STATUS_DESTROY) {
            frames = framesDestroy;
        } else if(entity.status == Constant.STATUS_DESTROYED) {
            frames = Constant.getTransparent();
        }

        if(entity.status != Constant.STATUS_STAND && entity.status != Constant.STATUS_SET_BOMB){
            if(isPressed == false) {
                isPressed = true;
                timeline = new Timeline(
                    new KeyFrame(Duration.millis(0), (ActionEvent actionEvent) -> {
                        entity.sprite = frames.get(0);
                    }),
                    new KeyFrame(Duration.millis(1 * time), (ActionEvent actionEvent) -> {
                        entity.sprite = frames.get(1);
                    }),
                    new KeyFrame(Duration.millis(2 * time), (ActionEvent actionEvent) -> {
                        entity.sprite = frames.get(2);
                    }),
                    new KeyFrame(Duration.millis(3 * time), (ActionEvent actionEvent) -> {
                        entity.sprite = Sprite.transparent;
                    })
                );
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.play();
            }
        } else {
            if(frames != null) {
               stopAnimation();
            }
            isPressed = false;
        }
    }

    public void stopAnimation() {
        entity.sprite = frames.get(0);
        timeline.pause();
    }

}
