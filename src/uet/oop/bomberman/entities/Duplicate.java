package uet.oop.bomberman.entities;

import uet.oop.bomberman.graphics.Sprite;

public class Duplicate {
    public static boolean CanMoveRight;

    public static boolean CanMoveLeft;

    public static boolean CanMoveUp;

    public static boolean CanMovedown;

    public static void CheckDuplicate(Entity bomber, Entity entity) {
        double rightBomber = bomber.x + bomber.sprite._realWidth;
        double leftBomber = bomber.x;
        double aboveBomber = bomber.y;
        double belowBomber = bomber.y + bomber.sprite._realHeight;

        double rightEntity = entity.x + entity.sprite._realWidth;
        double leftEntity = entity.x;
        double aboveEntyti = entity.y;
        double belowEntyti = entity.y + entity.sprite._realHeight;
        if (rightBomber >= leftEntity && rightBomber <= rightEntity) {
            CanMoveRight = false;
        } else if (leftBomber >= leftEntity && leftBomber <= rightEntity) {
            CanMoveLeft = false;
        }
        else if (belowBomber >= aboveEntyti && belowBomber <= belowEntyti) {
            CanMovedown = false;
        }
        else if (aboveBomber >= aboveEntyti && aboveBomber <= belowEntyti) {
            CanMoveUp = false;
        }
        else {
            CanMoveRight = true;
            CanMoveLeft = true;
            CanMovedown = true;
            CanMoveUp = true;
        }
    }

    public static boolean Colletion(Entity bomber, Entity entity) {
        double rightBomber = bomber.x + bomber.sprite._realWidth;
        double leftBomber = bomber.x;
        double aboveBomber = bomber.y;
        double belowBomber = bomber.y + bomber.sprite._realHeight;

        double rightEntity = entity.x + entity.sprite._realWidth;
        double leftEntity = entity.x;
        double aboveEntyti = entity.y;
        double belowEntyti = entity.y + entity.sprite._realHeight;

        if (belowBomber <= aboveEntyti) {
            return false;
        }
        else if (aboveBomber >= belowEntyti) {
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
