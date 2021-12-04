package uet.oop.bomberman.entities;

import uet.oop.bomberman.GameViewManager;
import uet.oop.bomberman.entities.SubClass.Constant;
import uet.oop.bomberman.graphics.Sprite;

public class Item extends DynamicEntity{
    public int typeItem;
    public Item(int xUnit, int yUnit, Sprite sprite, int typeItem) {
        super(xUnit, yUnit, sprite);
        this.typeItem = typeItem;
    }

    @Override
    public void update() {
    }
}
