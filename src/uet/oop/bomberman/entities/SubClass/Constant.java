package uet.oop.bomberman.entities.SubClass;

import javafx.application.Application;
import javafx.stage.Stage;
import uet.oop.bomberman.graphics.Sprite;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class Constant {
    public static final int COLLISION_WITH_WALL = 1;
    public static final int COLLISION_WITH_BRICK = 2;
    public static final int COLLISION_WITH_ALIEN = 3;
    public static final int COLLISION_WITH_BOMBER = 4;
    public static final int COLLISION_WITH_FLAME = 5;
    public static final int COLLISION_WITH_BOMB = 6;
    public static final int NO_COLLISION = 0;

    public static final int STATUS_STAND = 24;
    public static final int STATUS_UP = 25;
    public static final int STATUS_RIGHT = 26;
    public static final int STATUS_DOWN = 27;
    public static final int STATUS_LEFT = 28;
    public static final int STATUS_DESTROY = 29;
    public static final int STATUS_SET_BOMB = 30;
    public static final int STATUS_DESTROYED = 31;

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    public static final double HEIGHT_MENU = 1.5;

    public static final int PERCENT_ITEM_HEAL = 10;
    public static final int PERCENT_ITEM_BOMB = 15;
    public static final int PERCENT_ITEM_SPEED = 25;

    public static final int TYPE_ITEM_SPEED = 1;
    public static final int TYPE_ITEM_BOMB = 2;
    public static final int TYPE_ITEM_HEAL = 3;

    public static final int POWER_UP_MAX = 4;
    public static final int POWER_UP_1 = 1;
    public static final int POWER_UP_2 = 2;
    public static final int POWER_UP_3 = 3;

    public static final int SPEED_ANIMATION_BOMB = 1000;

    public static final char MAP_PLAYER = 'p';
    public static final char MAP_WALL = '#';
    public static final char MAP_BRICK = '*';
    public static final char MAP_PORTAL = 'X';
    public static final char MAP_BOLLOOM = '1';
    public static final char MAP_ONEAL = '2';
    public static final char MAP_GRASS =' ';

    public static final String URL_SOUND_MOVE_UP_DOWN = "/sounds/move_up_down.wav";
    public static final String URL_SOUND_MOVE_LEFT_RIGHT = "/sounds/move_left_right.wav";
    public static final String URL_SOUND_SET_BOMB = "/sounds/set_bomb.wav";
    public static final String URL_SOUND_PLAYER_DIED = "/sounds/player_died.wav";
    public static final String URL_SOUND_GET_ITEM = "/sounds/get_item.wav";
    public static final String URL_SOUND_BOMB_EXPLOSION = "/sounds/bomb_explosion.wav";
    public static final String URL_SOUND_BUTTON = "/sounds/get_item.wav";

    public static int score = 0;
    public static Clip test;

    public static final String BASE_MAP_URL = "/levels/Level";

    public static ArrayList<Sprite> getTransparent() {
        ArrayList<Sprite> result = new ArrayList<Sprite>();
        result.add(Sprite.transparent);result.add(Sprite.transparent);
        result.add(Sprite.transparent);
        return result;
    }

    public static Clip getSound(String url) {
        try {
            // Open an audio input stream.
            URL file = Constant.class.getResource(url);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            return clip;
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        return null;
    }


}