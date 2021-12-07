package uet.oop.bomberman.entities;

import uet.oop.bomberman.entities.SubClass.Constant;
import uet.oop.bomberman.graphics.AnimationFrame;
import uet.oop.bomberman.graphics.Map;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;

public class Brick extends DynamicEntity {
    private ArrayList<Sprite> frames = new ArrayList<Sprite>();
    private AnimationFrame animationFrame;
    private final int SPEED_TIME = 500;
    private long timeStartDestroy = -1;
    private long currentTime;
    private boolean crush = false;

    public Brick(int xUnit, int yUnit, Sprite sprite) {
        super(xUnit, yUnit, sprite);
        init();
    }

    @Override
    public void update() {
        if(status != Constant.STATUS_DESTROYED) {
            currentTime = System.currentTimeMillis();
            int collision = checkCollision();
            if (collision == Constant.COLLISION_WITH_FLAME && !crush) {
                crush = true;
                animationFrame.loadFrame();
                timeStartDestroy = System.currentTimeMillis();
            }
            if (currentTime - timeStartDestroy > 3 * SPEED_TIME && crush) {
                status = Constant.STATUS_DESTROYED;
                animationFrame.loadFrame();
                animationFrame.stopAnimation();
                Map.entityNodeList[x / Sprite.SCALED_SIZE][y / Sprite.SCALED_SIZE].isGrass = true;
            }
        }
    }
    void init() {
        status = Constant.STATUS_DESTROY;
        frames.add(Sprite.brick_exploded);
        frames.add(Sprite.brick_exploded1);
        frames.add(Sprite.brick_exploded2);
        animationFrame = new AnimationFrame(this, SPEED_TIME, frames);
    }
}
