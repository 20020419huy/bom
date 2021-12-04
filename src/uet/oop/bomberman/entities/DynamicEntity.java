package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.GameViewManager;
import uet.oop.bomberman.entities.SubClass.Constant;
import uet.oop.bomberman.entities.SubClass.Duplicate;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class DynamicEntity extends Entity {

    public DynamicEntity(int xUnit, int yUnit, Sprite sprite) {
        super(xUnit, yUnit, sprite);
    }

    @Override
    public void update() {

    }

    protected int checkCollision() {
        if (this instanceof Alien) {
            for (int i = 0; i <  GameViewManager.stillObjects.size(); i++) {
                boolean checkCollision = Duplicate.collision(this, GameViewManager.stillObjects.get(i));
                if ((GameViewManager.stillObjects.get(i) instanceof Wall) && checkCollision) {
                    return Constant.COLLISION_WITH_WALL; // va cham voi Wall va brick
                }
                if(GameViewManager.stillObjects.get(i) instanceof Brick && checkCollision) {
                    return Constant.COLLISION_WITH_BRICK;
                }
                if ((GameViewManager.stillObjects.get(i) instanceof Alien) && checkCollision && this !=  GameViewManager.stillObjects.get(i)) {
                    return Constant.COLLISION_WITH_ALIEN; 
                }
                if((GameViewManager.stillObjects.get(i) instanceof Flame) && checkCollision && this !=  GameViewManager.stillObjects.get(i)) {
                    return Constant.COLLISION_WITH_FLAME;
                }
                if((GameViewManager.stillObjects.get(i) instanceof Bomb) && checkCollision && this !=  GameViewManager.stillObjects.get(i)) {
                    return Constant.COLLISION_WITH_BOMB;
                }
            }
        }
        if (this instanceof Bomber) {
            for (int i = 0; i <  GameViewManager.stillObjects.size(); i++) {
                boolean checkCollision = Duplicate.collision(this, GameViewManager.stillObjects.get(i));
                if ((GameViewManager.stillObjects.get(i) instanceof Wall) && checkCollision) {
                    return Constant.COLLISION_WITH_WALL; // va cham voi Wall va brick
                }
                if(GameViewManager.stillObjects.get(i) instanceof Brick && checkCollision) {
                    return Constant.COLLISION_WITH_BRICK;
                }
                if ((GameViewManager.stillObjects.get(i) instanceof Alien) && checkCollision) {
                    return Constant.COLLISION_WITH_ALIEN; // va cham voi alien
                }
                if((GameViewManager.stillObjects.get(i) instanceof Flame) && checkCollision && this !=  GameViewManager.stillObjects.get(i)) {
                    return Constant.COLLISION_WITH_FLAME;
                }
            }
        }
        if(this instanceof  Flame) {
            for (int i = 0; i <  GameViewManager.stillObjects.size(); i++) {
                if (GameViewManager.stillObjects.get(i) instanceof Wall || GameViewManager.stillObjects.get(i) instanceof Brick) {
                    boolean checkCollision = Duplicate.collision(this, GameViewManager.stillObjects.get(i));
                    if ((GameViewManager.stillObjects.get(i) instanceof Wall) && checkCollision) {
                        return Constant.COLLISION_WITH_WALL; // va cham voi Wall va brick
                    }
                    if (GameViewManager.stillObjects.get(i) instanceof Brick && checkCollision) {
                        return Constant.COLLISION_WITH_BRICK;
                    }
                }
            }
        }
        if(this instanceof Brick) {
            for (int i = 0; i < GameViewManager.stillObjects.size(); i++) {
                if (GameViewManager.stillObjects.get(i) instanceof Flame) {
                    boolean checkCollision = Duplicate.collision(this, GameViewManager.stillObjects.get(i));
                    if(checkCollision) {
                        return Constant.COLLISION_WITH_FLAME;
                    }
                }
            }
        }
        if(this instanceof Item) {
            boolean isDestroyedBrick = false;
            for (int i = 0; i < GameViewManager.stillObjects.size(); i++) {
                if(GameViewManager.stillObjects.get(i) instanceof Brick && GameViewManager.stillObjects.get(i).x == x && GameViewManager.stillObjects.get(i).y == y) {
                    if(GameViewManager.stillObjects.get(i).status == Constant.STATUS_DESTROYED) {
                        isDestroyedBrick = true;
                    }
                    break;
                }
            }
            if(isDestroyedBrick) {
                for (int i = 0; i < GameViewManager.stillObjects.size(); i++) {
                    if (GameViewManager.stillObjects.get(i) instanceof Bomber) {
                        boolean checkCollision = Duplicate.collision(this, GameViewManager.stillObjects.get(i));
                        if (checkCollision) {
                            System.out.println("collision bomber");
                            return Constant.COLLISION_WITH_BOMBER;
                        }
                        break;
                    }
                }
            }
        }

        return Constant.NO_COLLISION;
    }

    protected Entity subCheckCollision() {
        for(int i = 0; i < GameViewManager.stillObjects.size(); i++) {
            if(GameViewManager.stillObjects.get(i) instanceof Wall || GameViewManager.stillObjects.get(i) instanceof Brick || GameViewManager.stillObjects.get(i) instanceof Flame || GameViewManager.stillObjects.get(i) instanceof Alien) {
                boolean checkCollision = Duplicate.collision(this, GameViewManager.stillObjects.get(i));
                if(checkCollision) {
                    return GameViewManager.stillObjects.get(i);
                }
            } else if(GameViewManager.stillObjects.get(i) instanceof Item) {
                boolean checkCollision = Duplicate.collision(this, GameViewManager.stillObjects.get(i));
                boolean isDestroyedBrick = false;
                for (int j = 0; i < GameViewManager.stillObjects.size(); j++) {
                    if(GameViewManager.stillObjects.get(j) instanceof Brick && GameViewManager.stillObjects.get(j).x == GameViewManager.stillObjects.get(i).x && GameViewManager.stillObjects.get(j).y == GameViewManager.stillObjects.get(i).y) {
                        if(GameViewManager.stillObjects.get(j).status == Constant.STATUS_DESTROYED) {
                            isDestroyedBrick = true;
                        }
                        break;
                    }
                }
                if(isDestroyedBrick) {
                    if(checkCollision) {
                        return GameViewManager.stillObjects.get(i);
                    }
                }
            }
        }
        //if no collision
        return null;
    }
}
