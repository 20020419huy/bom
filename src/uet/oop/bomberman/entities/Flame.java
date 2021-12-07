package uet.oop.bomberman.entities;

import uet.oop.bomberman.GameViewManager;
import uet.oop.bomberman.entities.SubClass.Constant;
import uet.oop.bomberman.graphics.AnimationFrame;
import uet.oop.bomberman.graphics.Sprite;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


public class Flame extends DynamicEntity  {
    private final int SPEED_ANIMATION = 100;
    private long  startTime;
    private long currentTime;
    private boolean stopFlame = false;
    public boolean collision = false;
    private ArrayList<Sprite> frames = new ArrayList<Sprite>();
    private AnimationFrame animationFrame;

    public Flame(int xUnit, int yUnit, Sprite sprite) {
        super(xUnit, yUnit, sprite);
        status = Constant.STATUS_DESTROY;
        init();
        animationFrame =  new AnimationFrame(this, SPEED_ANIMATION, frames);
    }

    @Override
    public void update() {
        if(status != Constant.STATUS_DESTROYED) {
            currentTime = System.currentTimeMillis();
            if (currentTime - startTime > SPEED_ANIMATION * 3 && !stopFlame) {
                stopFlame();
            }
            if (collision) {
                stopFlame();
                collision = false;
            }
        }
    }
    private  void init() {
        if(sprite == Sprite.bomb_exploded) {
            frames.add(Sprite.bomb_exploded);
            frames.add(Sprite.bomb_exploded1);
            frames.add(Sprite.bomb_exploded2);
        } else if (sprite == Sprite.explosion_horizontal) {
            frames.add(Sprite.explosion_horizontal);
            frames.add(Sprite.explosion_horizontal1);
            frames.add(Sprite.explosion_horizontal2);
        } else if (sprite == Sprite.explosion_vertical) {
            frames.add(Sprite.explosion_vertical);
            frames.add(Sprite.explosion_vertical1);
            frames.add(Sprite.explosion_vertical2);
        } else if(sprite == Sprite.explosion_vertical_top_last) {
            frames.add(Sprite.explosion_vertical_top_last);
            frames.add(Sprite.explosion_vertical_top_last1);
            frames.add(Sprite.explosion_vertical_top_last2);
        } else if(sprite == Sprite.explosion_horizontal_right_last) {
            frames.add(Sprite.explosion_horizontal_right_last);
            frames.add(Sprite.explosion_horizontal_right_last1);
            frames.add(Sprite.explosion_horizontal_right_last2);
        } else if(sprite == Sprite.explosion_vertical_down_last) {
            frames.add(Sprite.explosion_vertical_down_last);
            frames.add(Sprite.explosion_vertical_down_last1);
            frames.add(Sprite.explosion_vertical_down_last2);
        } else if(sprite == Sprite.explosion_horizontal_left_last) {
            frames.add(Sprite.explosion_horizontal_left_last);
            frames.add(Sprite.explosion_horizontal_left_last1);
            frames.add(Sprite.explosion_horizontal_left_last2);
        }
    }
    public void startFlame() {
        startTime = System.currentTimeMillis();
        GameViewManager.stillObjects.add(this);
        animationFrame.loadFrame();
        Constant.getSound(Constant.URL_SOUND_BOMB_EXPLOSION).start();
    }
    private void stopFlame() {
        status = Constant.STATUS_DESTROYED;
        animationFrame.loadFrame();
        animationFrame.stopAnimation();
        stopFlame = true;
    }
}