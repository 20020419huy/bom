package uet.oop.bomberman.entities;

import uet.oop.bomberman.GameViewManager;
import uet.oop.bomberman.entities.SubClass.Constant;
import uet.oop.bomberman.graphics.AnimationFrame;
import uet.oop.bomberman.graphics.*;

import java.util.ArrayList;

public class Bomb extends Entity {
    private ArrayList<Sprite> frames = new ArrayList<Sprite>();
    AnimationFrame animationFrame = new AnimationFrame(this, Constant.SPEED_ANIMATION_BOMB, frames);
    private Flame center;
    private Flame top;
    private Flame right;
    private Flame bottom;
    private Flame left;

 /*   private final int MAX_BOM = 3;
    private int currentBomb = 0;*/
    private ArrayList<Flame> betweenTop = new ArrayList<>();
    private ArrayList<Flame> betweenRight = new ArrayList<>();
    private ArrayList<Flame> betweenBottom = new ArrayList<>();
    private ArrayList<Flame> betweenLeft = new ArrayList<>();

    private int powerUp;
    private long startTime;
    private long currentTime;
    private boolean startBigBang = false;
    public Bomb(int xUnit, int yUnit, Sprite sprite, int powerUp) {
        super(xUnit, yUnit, sprite);
        status = Constant.STATUS_DESTROY;
        this.powerUp = powerUp;
        init();
        GameViewManager.stillObjects.add(this);
        animationFrame.loadFrame();
        currentTime = System.currentTimeMillis();
        startTime = System.currentTimeMillis();
        Map.entityNodeList[x / Sprite.SCALED_SIZE][y / Sprite.SCALED_SIZE].isGrass = false;
    }

    private void init() {
        frames.add(Sprite.bomb_2);
        frames.add(Sprite.bomb_1);
        frames.add(Sprite.bomb);

       ArrayList<Integer> calculatePower = calculatePower();
       for(int i = 0; i < calculatePower.size(); i++) {
           betweenTop.add(new Flame(x / Sprite.SCALED_SIZE, (y - calculatePower.get(i)) / Sprite.SCALED_SIZE, Sprite.explosion_vertical));
           betweenRight.add(new Flame((x + calculatePower.get(i)) / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE, Sprite.explosion_horizontal));
           betweenBottom.add(new Flame(x / Sprite.SCALED_SIZE, (y + calculatePower.get(i) ) / Sprite.SCALED_SIZE,Sprite.explosion_vertical));
           betweenLeft.add(new Flame((x - calculatePower.get(i)) / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE, Sprite.explosion_horizontal));
       }

       int distance = 0;
       if(calculatePower.size() != 0) {
           distance = calculatePower.get(calculatePower.size() - 1);
       }
        center = new Flame(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE, Sprite.bomb_exploded);
        top = new Flame(x / Sprite.SCALED_SIZE, (y - (Sprite.SCALED_SIZE + distance )) / Sprite.SCALED_SIZE, Sprite.explosion_vertical_top_last);
        right = new Flame((x + (Sprite.SCALED_SIZE + distance)) / Sprite.SCALED_SIZE, y  / Sprite.SCALED_SIZE, Sprite.explosion_horizontal_right_last);
        bottom = new Flame(x / Sprite.SCALED_SIZE, (y + (Sprite.SCALED_SIZE + distance )) / Sprite.SCALED_SIZE, Sprite.explosion_vertical_down_last);
        left = new Flame((x - (Sprite.SCALED_SIZE + distance )) / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE, Sprite.explosion_horizontal_left_last);
    }

    private ArrayList<Integer> calculatePower() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        if(powerUp == Constant.POWER_UP_2) {
            result.add(Sprite.SCALED_SIZE);
            result.add(Sprite.SCALED_SIZE);
            result.add(Sprite.SCALED_SIZE);
        } else if(powerUp == Constant.POWER_UP_3) {
           result.add(Sprite.SCALED_SIZE);
           result.add(2 * Sprite.SCALED_SIZE);
           result.add(2 * Sprite.SCALED_SIZE);
        } else if(powerUp == Constant.POWER_UP_MAX) {
            result.add(Sprite.SCALED_SIZE);
            result.add(2 * Sprite.SCALED_SIZE);
            result.add(3 * Sprite.SCALED_SIZE);
        }
        return result;
    }

    public void bigBang () {
        center.startFlame();
        top.startFlame();
        right.startFlame();
        bottom.startFlame();
        left.startFlame();
        for (int i = 0;i < betweenTop.size(); i++) {
            betweenTop.get(i).startFlame();;
            betweenRight.get(i).startFlame();
            betweenBottom.get(i).startFlame();
            betweenLeft.get(i).startFlame();
        }
        Map.entityNodeList[x / Sprite.SCALED_SIZE][y / Sprite.SCALED_SIZE].isGrass = true;

    }

    private boolean preventFlame(ArrayList<Flame> between) {
        for(int i = 0; i < between.size(); i++) {
            if(between.get(i).status != Constant.STATUS_DESTROYED) {
                int isCollision = between.get(i).checkCollision();
                if(isCollision != Constant.NO_COLLISION) {
                    int j = 0;
                    if(isCollision == Constant.COLLISION_WITH_WALL) {
                        j = i;
                    } else if(isCollision == Constant.COLLISION_WITH_BRICK) {
                        j = i + 1;
                    }
                    while (j < between.size()) {
                        between.get(j).collision = true;
                        j++;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void update() {
        currentTime = System.currentTimeMillis();
        if(currentTime - startTime >= 3 * Constant.SPEED_ANIMATION_BOMB && !startBigBang) {
            status = Constant.STATUS_DESTROYED;
            animationFrame.loadFrame();
            animationFrame.stopAnimation();
            bigBang();
            startBigBang = true;
        }
        if(startBigBang) {
            if(top.status != Constant.STATUS_DESTROYED) {
                if (preventFlame(betweenTop)) {
                    top.collision = true;
                } else {
                    if(top.checkCollision() == Constant.COLLISION_WITH_WALL) {
                        top.collision = true;
                    }
                }
            }

            if(right.status != Constant.STATUS_DESTROYED) {
                if(preventFlame(betweenRight)){
                    right.collision = true;
                } else {
                    if(right.checkCollision() == Constant.COLLISION_WITH_WALL) {
                        right.collision = true;
                    }
                }
            }
            if(bottom.status != Constant.STATUS_DESTROYED) {
                if( preventFlame(betweenBottom)) {
                    bottom.collision = true;
                } else {
                    if(bottom.checkCollision() == Constant.COLLISION_WITH_WALL) {
                        bottom.collision = true;
                    }
                }
            }
            if(left.status != Constant.STATUS_DESTROYED) {
                if(preventFlame(betweenLeft)) {
                    left.collision = true;
                } else {
                    if(left.checkCollision() == Constant.COLLISION_WITH_WALL) {
                        left.collision = true;
                    }
                }
            }
        }
    }
}
