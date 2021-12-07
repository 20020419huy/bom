package uet.oop.bomberman.entities;

import javafx.scene.input.KeyCode;
import uet.oop.bomberman.GameViewManager;
import uet.oop.bomberman.entities.SubClass.Constant;
import uet.oop.bomberman.graphics.AnimationFrame;
import uet.oop.bomberman.graphics.Sprite;

import javax.sound.sampled.*;
import java.util.ArrayList;


public class Bomber extends DynamicEntity {
    private int heal = 1;
    private double speed = 4;
    private final double MAX_SPEED = 4;
    private int power_up = Constant.POWER_UP_1;
    private double speedAnimation = 100;
    private final int CAN_FIX_POS = Sprite.SCALED_SIZE / 2;
    private final int DISTANCE_FIX_POS = 1;
    private int oldPosX;
    private int oldPosY;

    public static int coordinateX;
    public static int coordinateY;
    public static boolean isDead = false;
    private long oldTime = 0;
    private long currentTime = 0;
    private long duration = 1000;

    //    private int sumBomb = 0;
    private final int MAX_BOMB = 2000;
    public static KeyCode KEY_BOMB = KeyCode.SPACE;

    private Clip soundMoveUPDown = Constant.getSound(Constant.URL_SOUND_MOVE_UP_DOWN);
    private Clip soundMoveLeftRight = Constant.getSound(Constant.URL_SOUND_MOVE_LEFT_RIGHT);

    private AnimationFrame animationFrame;
    private ArrayList<Sprite> frameRight = new ArrayList<Sprite>();
    private ArrayList<Sprite> frameDown = new ArrayList<Sprite>();
    private ArrayList<Sprite> frameLeft = new ArrayList<Sprite>();
    private ArrayList<Sprite> frameUp = new ArrayList<Sprite>();
    private ArrayList<Sprite> frameDestroy = new ArrayList<Sprite>();
    public Bomber(int x, int y, Sprite sprite) {
        super( x, y, sprite);
        oldPosX = x;
        oldPosY = y;
        init();
    }
    private void init() {
        frameUp.add(Sprite.player_up);
        frameUp.add(Sprite.player_up_1);
        frameUp.add(Sprite.player_up_2);

        frameRight.add(Sprite.player_right);
        frameRight.add(Sprite.player_right_1);
        frameRight.add(Sprite.player_right_2);

        frameDown.add(Sprite.player_down);
        frameDown.add(Sprite.player_down_1);
        frameDown.add(Sprite.player_down_2);

        frameLeft.add(Sprite.player_left);
        frameLeft.add(Sprite.player_left_1);
        frameLeft.add(Sprite.player_left_2);

        frameDestroy.add(Sprite.player_dead1);
        frameDestroy.add(Sprite.player_dead2);
        frameDestroy.add(Sprite.player_dead3);

        animationFrame = new AnimationFrame(this, speedAnimation, frameUp, frameRight, frameDown, frameLeft, frameDestroy);
    }
    @Override
    public void update() {
        animationFrame.loadFrame();
        if(status == Constant.STATUS_DESTROYED || status == Constant.STATUS_DESTROY) {
            isDead = true;
        } else {
            isDead = false;
        }
    }

    public void updatePosition (KeyCode direc) {
        currentTime = System.currentTimeMillis();
        if((status == Constant.STATUS_DESTROYED || status == Constant.STATUS_DESTROY) && currentTime - oldTime < duration){
            return;
        }
        coordinateX = x / Sprite.SCALED_SIZE;
        coordinateY = y / Sprite.SCALED_SIZE;
        oldPosY = y;
        oldPosX = x;
        if (direc == null && status != Constant.STATUS_DESTROYED && status != Constant.STATUS_DESTROY) {
            status = Constant.STATUS_STAND;
        } else if(direc == KeyCode.UP) {
            y -=  speed;
            status = Constant.STATUS_UP;
            if(!soundMoveUPDown.isRunning()) {
                soundMoveUPDown.loop(1);
            }
        } else if(direc == KeyCode.RIGHT) {
            x +=  speed;
            status = Constant.STATUS_RIGHT;
            if(!soundMoveLeftRight.isRunning()) {
                soundMoveLeftRight.loop(1);
            }
        } else if(direc == KeyCode.DOWN) {
            y +=  speed;
            status = Constant.STATUS_DOWN;
            if(!soundMoveUPDown.isRunning()) {
                soundMoveUPDown = Constant.getSound(Constant.URL_SOUND_MOVE_UP_DOWN);
                soundMoveUPDown.loop(1);
            }
        } else if(direc == KeyCode.LEFT) {
            x -=  speed;
            status = Constant.STATUS_LEFT;
            if(!soundMoveLeftRight.isRunning()) {
                soundMoveLeftRight.loop(1);
            }
        }  else if(direc == KEY_BOMB) {
            if(status == Constant.STATUS_STAND) {
                setBomb();
                status = Constant.STATUS_SET_BOMB;
            }
        }
        Entity entity = subCheckCollision();
        if(entity != null) {
            if(entity instanceof Alien || entity instanceof Flame) {
                if(status != Constant.STATUS_DESTROY && status != Constant.STATUS_DESTROYED) {
                    heal--;
                    oldTime = System.currentTimeMillis();
                    Constant.getSound(Constant.URL_SOUND_PLAYER_DIED).start();
                }
                status = Constant.STATUS_DESTROY;
            } else if (entity instanceof Wall || entity instanceof Brick) {
                x = oldPosX;
                y = oldPosY;
                if(entity.x + entity.sprite._realWidth <= x || x + sprite._realWidth <= entity.x) {
                    if(entity.y + entity.sprite._realHeight - y <= CAN_FIX_POS) {
                        y = y + DISTANCE_FIX_POS;
                    } else if (y + sprite._realHeight - entity.y <= CAN_FIX_POS) {
                        y = y - DISTANCE_FIX_POS;
                    }
                } else if (y + sprite._realHeight <= entity.y || entity.y + entity.sprite._realHeight <= y) {
                    if(entity.x + entity.sprite._realWidth - x <= CAN_FIX_POS) {
                        x += DISTANCE_FIX_POS;
                    } else if(x + sprite._realWidth - entity.x <= CAN_FIX_POS) {
                        x -= DISTANCE_FIX_POS;
                    }
                }
            } else if(entity instanceof Item) {
                x = oldPosX;
                y = oldPosY;
                Constant.getSound(Constant.URL_SOUND_GET_ITEM).start();
                if(((Item) entity).typeItem == Constant.TYPE_ITEM_SPEED) {
                    if(speed < MAX_SPEED) {
                        speed += 1;
                        System.out.println(speed);
                    }
                } else if(((Item) entity).typeItem == Constant.TYPE_ITEM_BOMB) {
                    if(power_up < Constant.POWER_UP_MAX) {
                        power_up += 1;
                    }
                } else if (((Item) entity).typeItem == Constant.TYPE_ITEM_HEAL) {
                    heal += 1;
                    System.out.println(heal);
                }
                entity.status = Constant.STATUS_DESTROYED;
                entity.sprite = Sprite.transparent;
            }
        }
    }

    private void setBomb() {
        System.out.println("x:" + x / Sprite.SCALED_SIZE);
        System.out.println("y: " + y / Sprite.SCALED_SIZE);
        Constant.getSound(Constant.URL_SOUND_SET_BOMB).start();
        int currentBomb = 0;
        for(int i = 0; i < GameViewManager.stillObjects.size(); i++) {
            if(GameViewManager.stillObjects.get(i) instanceof  Bomb && GameViewManager.stillObjects.get(i).status != Constant.STATUS_DESTROYED) {
                currentBomb++;
            }
        }
        if(currentBomb < MAX_BOMB) {
            Bomb bomb = new Bomb((x + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE, (y + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE, Sprite.bomb, power_up);
        }
    }

    public int getHeal() {
        return heal;
    }

    public double getSpeed() {
        return speed;
    }

    public int getPower_up() {
        return power_up;
    }
}
