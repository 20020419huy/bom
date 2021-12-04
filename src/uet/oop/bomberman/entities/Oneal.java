package uet.oop.bomberman.entities;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.SubClass.Constant;
import uet.oop.bomberman.graphics.AnimationFrame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Oneal extends Alien {
    private double speedAnimation = 100;
    private double speedAnimationDead = 300;
    private AnimationFrame animationFrame;
    private ArrayList<Sprite> frameRight = new ArrayList<Sprite>();
    private ArrayList<Sprite> frameDown = new ArrayList<Sprite>();
    private ArrayList<Sprite> frameLeft = new ArrayList<Sprite>();
    private ArrayList<Sprite> frameUp = new ArrayList<Sprite>();
    private ArrayList<Sprite> frameDestroy = new ArrayList<Sprite>();
    private long currentTime;
    private long timeStartDead;
    public Oneal(int xUnit, int yUnit, Sprite sprite) {
        super(xUnit, yUnit, sprite);
        init();
    }

    private void init() {
        frameUp.add(Sprite.oneal_right1);
        frameUp.add(Sprite.oneal_right2);
        frameUp.add(Sprite.oneal_right3);

        frameRight.add(Sprite.oneal_right1);
        frameRight.add(Sprite.oneal_right2);
        frameRight.add(Sprite.oneal_right3);

        frameDown.add(Sprite.oneal_left1);
        frameDown.add(Sprite.oneal_left2);
        frameDown.add(Sprite.oneal_left3);

        frameLeft.add(Sprite.oneal_left1);
        frameLeft.add(Sprite.oneal_left2);
        frameLeft.add(Sprite.oneal_left3);

        frameDestroy.add(Sprite.oneal_dead);
        frameDestroy.add(Sprite.oneal_dead);
        frameDestroy.add(Sprite.oneal_dead);

        animationFrame = new AnimationFrame(this, speedAnimation, frameUp, frameRight, frameDown, frameLeft, frameDestroy);
    }

    @Override
    public void update() {
        if (status != Constant.STATUS_DESTROYED) {
            currentTime = System.currentTimeMillis();
            if (status != Constant.STATUS_DESTROY) {
                autoMove();
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
