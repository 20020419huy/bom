package uet.oop.bomberman.entities.SubClass;

import uet.oop.bomberman.entities.Entity;

public class Duplicate {
    public static boolean collision(Entity bomber, Entity entity) {
        if(entity.status == Constant.STATUS_DESTROYED) {
            return false;
        }
        double rightBomber = bomber.x + bomber.sprite._realWidth;
        double leftBomber = bomber.x;
        double aboveBomber = bomber.y;
        double belowBomber = bomber.y + bomber.sprite._realHeight;

        double rightEntity = entity.x + entity.sprite._realWidth;
        double leftEntity = entity.x;
        double aboveEntity = entity.y;
        double belowEntity = entity.y + entity.sprite._realHeight;

        if (belowBomber <= aboveEntity) {
            return false;
        }
        else if (aboveBomber >= belowEntity) {
            return false;
        }
        else if (rightBomber <= leftEntity) {
            return false;
        }
        else if (leftBomber >= rightEntity) {
            return false;
        }
        return true;
    }
}
