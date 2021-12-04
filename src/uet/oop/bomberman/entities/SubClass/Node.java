package uet.oop.bomberman.entities.SubClass;

import java.util.ArrayList;

public class Node {
    public int coordinateX;
    public int coordinateY;
    public boolean isGrass;
    public ArrayList<Node> listNode = new ArrayList<Node>();

    public Node(int coordinateX, int coordinateY, boolean isGrass) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.isGrass = isGrass;
    }
}
