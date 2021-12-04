package uet.oop.bomberman.entities;

import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.entities.SubClass.Constant;
import uet.oop.bomberman.graphics.AnimationFrame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Balloon extends Alien{
    private double speedAnimation = 100;
    private double speedAnimationDead = 300;
    private AnimationFrame animationFrame;
    private ArrayList<Sprite> frameRight = new ArrayList<Sprite>();
    private ArrayList<Sprite> frameDown = new ArrayList<Sprite>();
    private ArrayList<Sprite> frameLeft = new ArrayList<Sprite>();
    private ArrayList<Sprite> frameUp = new ArrayList<Sprite>();
    private ArrayList<Sprite> frameDestroy = new ArrayList<Sprite>();
    private long timeStartDead;
    private long currentTime;
    public Balloon(int xUnit, int yUnit,Sprite sprite) {
        super(xUnit, yUnit, sprite);
        init();
    }

    private void init() {
        frameUp.add(Sprite.balloom_right1);
        frameUp.add(Sprite.balloom_right2);
        frameUp.add(Sprite.balloom_right3);

        frameRight.add(Sprite.balloom_right1);
        frameRight.add(Sprite.balloom_right2);
        frameRight.add(Sprite.balloom_right3);

        frameDown.add(Sprite.balloom_left1);
        frameDown.add(Sprite.balloom_left2);
        frameDown.add(Sprite.balloom_left3);

        frameLeft.add(Sprite.balloom_left1);
        frameLeft.add(Sprite.balloom_left2);
        frameLeft.add(Sprite.balloom_left3);

        frameDestroy.add(Sprite.balloom_dead);
        frameDestroy.add(Sprite.balloom_dead);
        frameDestroy.add(Sprite.balloom_dead);

        animationFrame = new AnimationFrame(this,speedAnimation, frameUp, frameRight, frameDown, frameLeft, frameDestroy);
    }

    @Override
    public void update() {
        if (status != Constant.STATUS_DESTROYED) {
            currentTime = System.currentTimeMillis();
            if (status != Constant.STATUS_DESTROY) {
                fantasticMove();
                animationFrame.loadFrame();
                timeStartDead = System.currentTimeMillis();
            } else {
                if (currentTime - timeStartDead > 3 * speedAnimationDead) {
                    status = Constant.STATUS_DESTROYED;
                    animationFrame.loadFrame();
                    animationFrame.stopAnimation();
                } else {
                    animationFrame.setTime(speedAnimationDead);
                    animationFrame.loadFrame();
                }
            }
        }
    }
}
