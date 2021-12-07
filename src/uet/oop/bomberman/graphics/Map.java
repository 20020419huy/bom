package uet.oop.bomberman.graphics;

import uet.oop.bomberman.GameViewManager;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.SubClass.Constant;
import uet.oop.bomberman.entities.SubClass.Node;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

public class Map {
    public static Node[][] entityNodeList = new Node[Constant.WIDTH][Constant.HEIGHT];
    private int random() {
        int random = (int)(Math.random() * 100 + 1);
        if(random <= Constant.PERCENT_ITEM_HEAL) {
            return Constant.PERCENT_ITEM_HEAL;
        } else if(random > Constant.PERCENT_ITEM_HEAL && random <= Constant.PERCENT_ITEM_BOMB + Constant.PERCENT_ITEM_HEAL) {
            return Constant.PERCENT_ITEM_BOMB;
        } else if(random > Constant.PERCENT_ITEM_BOMB + Constant.PERCENT_ITEM_HEAL && random <= Constant.PERCENT_ITEM_SPEED + Constant.PERCENT_ITEM_BOMB + Constant.PERCENT_ITEM_HEAL) {
            return Constant.PERCENT_ITEM_SPEED;
        }
        return -1;
    }
    public int createMap(int level) {
        File file = null;
        int posBomber = -1;
        Scanner scanner = null;
        URL url = getClass().getResource(Constant.BASE_MAP_URL + Integer.toString(level) + ".txt");
        file = new File(url.getFile());
        //create grass
            for (int i = 0; i < Constant.HEIGHT; i++) {
                for (int j = 0; j < Constant.WIDTH; j++) {
                    GameViewManager.stillObjects.add(new Grass(j, i, Sprite.grass));
                    entityNodeList[j][i] = new Node(j, i, false);
                }
            }
        try {
            scanner = new Scanner(file);
            // random item
            for (int i = 0; i < Constant.HEIGHT; i++) {
                String data = scanner.nextLine();
                for (int j = 0; j < Constant.WIDTH; j++) {
                   if(data.charAt(j) == Constant.MAP_BRICK) {
                       int random = random();
                       if(random == Constant.PERCENT_ITEM_HEAL) {
                           GameViewManager.stillObjects.add(new Item(j, i, Sprite.powerup_detonator, Constant.TYPE_ITEM_HEAL));
                       } else if(random == Constant.PERCENT_ITEM_BOMB) {
                           GameViewManager.stillObjects.add(new Item(j, i, Sprite.powerup_bombs, Constant.TYPE_ITEM_BOMB));
                       } else if(random == Constant.PERCENT_ITEM_SPEED) {
                           GameViewManager.stillObjects.add(new Item(j, i, Sprite.powerup_speed, Constant.TYPE_ITEM_SPEED));
                       }
                   }
                   if(j - 1 >= 0)
                       entityNodeList[j][i].listNode.add(entityNodeList[j - 1][i]);
                   if(i - 1 >= 0)
                        entityNodeList[j][i].listNode.add(entityNodeList[j][i - 1]);
                   if(j + 1 < Constant.WIDTH)
                        entityNodeList[j][i].listNode.add(entityNodeList[j + 1][i]);
                   if(i + 1 < Constant.HEIGHT)
                        entityNodeList[j][i].listNode.add(entityNodeList[j][i + 1]);
                }
            }
            // load map
            scanner = new Scanner(file);
            for (int i = 0; i < Constant.HEIGHT; i++) {
                String data = scanner.nextLine();
                for (int j = 0; j < Constant.WIDTH; j++) {
                    if(data.charAt(j) == Constant.MAP_WALL) {
                        GameViewManager.stillObjects.add(new Wall(j, i, Sprite.wall));
                    } else if (data.charAt(j) == Constant.MAP_PLAYER) {
                        posBomber =  GameViewManager.stillObjects.size();
                        GameViewManager.stillObjects.add(new Bomber(j, i, Sprite.player_right));
                    } else if (data.charAt(j) == Constant.MAP_BRICK) {
                        GameViewManager.stillObjects.add(new Brick(j, i, Sprite.brick));
                    } else if (data.charAt(j) == Constant.MAP_PORTAL) {
                        GameViewManager.stillObjects.add(new Portal(j, i, Sprite.portal));
                    } else if (data.charAt(j) == Constant.MAP_BOLLOOM) {
                        GameViewManager.stillObjects.add(new Balloon(j, i, Sprite.balloom_right1));
                    } else if (data.charAt(j) == Constant.MAP_ONEAL) {
                        GameViewManager.stillObjects.add(new Oneal(j ,i, Sprite.oneal_right1));
                    }
                    if(data.charAt(j) == Constant.MAP_GRASS || data.charAt(j) == Constant.MAP_PLAYER || data.charAt(j) == Constant.MAP_BOLLOOM || data.charAt(j) == Constant.MAP_ONEAL) {
                        entityNodeList[j][i].isGrass  = true;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("lỗi load map của level " + Integer.toString(level));
        }
        return posBomber;
    }
}
