package uet.oop.bomberman.entities;

import uet.oop.bomberman.entities.SubClass.Constant;
import uet.oop.bomberman.entities.SubClass.Node;
import uet.oop.bomberman.graphics.*;
import uet.oop.bomberman.graphics.Map;

import java.util.*;

public class Alien extends DynamicEntity {
    protected double speed = 2;
    private final int MOVE_NO_COLLISION = 1;
    private final int MOVE_WITH_COLLISION = -1;
    private Node nodeNext = null;
    private ArrayList <Node> minWay = null;
    private long currentTime = 0;
    private long oldTime = 0;
    private long durationFindMinWay = 1000;
    public Alien(int xUnit, int yUnit, Sprite sprite) {
        super(xUnit, yUnit, sprite);
    }

    @Override
    public void update() {}

    private int randomDirection() {
        return (int) (Math.random() * (Constant.STATUS_LEFT - Constant.STATUS_UP + 1) + Constant.STATUS_UP);
    }

    protected void fantasticMove() {
        currentTime = System.currentTimeMillis();
        int collision = checkCollision();
        if(currentTime - oldTime > durationFindMinWay) {
            oldTime = System.currentTimeMillis();
            minWay = initForMethodFindWay();
            if(minWay != null) {
                nodeNext = minWay.get(0);
            }
        }
        if(collision == Constant.COLLISION_WITH_ALIEN || collision == Constant.COLLISION_WITH_BOMB) {
            oldTime = currentTime;
            minWay = null;
            nodeNext = null;
        }
        if(minWay != null) {
            if(moveFlowMinWay() == true) {
                minWay = initForMethodFindWay();
                if(minWay != null) {
                    nodeNext = minWay.get(0);
                }
            }
        } else {
            autoMove();
        }
    }

    protected void autoMove() {
        if(status == Constant.STATUS_STAND) {
            status = randomDirection();
        }
        move(MOVE_NO_COLLISION);
        int collection = checkCollision();
        if(collection != Constant.NO_COLLISION) {
            if(collection == Constant.COLLISION_WITH_FLAME) {
                status = Constant.STATUS_DESTROY;
                if (this instanceof Oneal) {
                    Constant.score+=1;
                } else {
                    Constant.score+=2;
                }
                return;
            }
            move(MOVE_WITH_COLLISION);
            int tempStatus = status;
            while (true) {
                tempStatus = randomDirection();
                if(tempStatus != status) {
                    break;
                }
            }
            status = tempStatus;
        }
    }

    private void move(int type) {
        if(status == Constant.STATUS_UP) {
            y -= type * speed;
        } else if(status == Constant.STATUS_RIGHT) {
            x += type * speed;
        } else if(status == Constant.STATUS_DOWN) {
            y += type * speed;
        } else if (status == Constant.STATUS_LEFT) {
            x -= type * speed;
        }
    }

    private boolean moveFlowMinWay() {
//        System.out.println("martmove");
        if(checkCollision() == Constant.COLLISION_WITH_FLAME) {
            if (this instanceof Oneal) {
                Constant.score+=1;
            } else {
                Constant.score+=2;
            }
            status = Constant.STATUS_DESTROY;
            return true;
        }
        if(moveOneNode() == true) {
            int i = 0;
            for (; i < minWay.size(); i++) {
                if(minWay.get(i).equals(nodeNext)) {
                    if(i == minWay.size() - 1) {
                        return true;
                    } else {
                        nodeNext = minWay.get(i + 1);
                        break;
                    }
                }
            }
        }
        return false;
    }

    private boolean moveOneNode() {
        if(nodeNext.coordinateX * Sprite.SCALED_SIZE == x && nodeNext.coordinateY * Sprite.SCALED_SIZE == y) {
            return true;
        }
        if(nodeNext.coordinateX * Sprite.SCALED_SIZE != x) {
            int direction = (nodeNext.coordinateX * Sprite.SCALED_SIZE - x) / Math.abs(nodeNext.coordinateX * Sprite.SCALED_SIZE - x);
            x += direction * speed;
            if(direction == 1) {
                status = Constant.STATUS_RIGHT;
            } else if(direction == -1) {
                status = Constant.STATUS_LEFT;
            }
        }
        if(nodeNext.coordinateY * Sprite.SCALED_SIZE != y) {
            int direction = (nodeNext.coordinateY * Sprite.SCALED_SIZE - y) / Math.abs(nodeNext.coordinateY * Sprite.SCALED_SIZE - y);
            y += direction * speed;
            if(direction == 1) {
                status = Constant.STATUS_DOWN;
            } else {
                status = Constant.STATUS_UP;
            }
        }
        return false;
    }

    private ArrayList<Node> initForMethodFindWay() {
        if(Bomber.isDead) {
            return null;
        }
        ArrayList<Node> listNode = new ArrayList<Node>();
        listNode.add(Map.entityNodeList[x / Sprite.SCALED_SIZE][y / Sprite.SCALED_SIZE]);
        boolean[][] isVisited = new boolean[Constant.WIDTH][Constant.HEIGHT];
        Node[][] listPrev = new Node[Constant.WIDTH][Constant.HEIGHT];
        isVisited[x / Sprite.SCALED_SIZE][y / Sprite.SCALED_SIZE] = true;
        ArrayList<Node> result = findMinWayToBomber(listNode, listPrev, isVisited);
        if(result != null) {
            Collections.reverse(result);
            System.out.println("find way");
        } else {
            System.out.println("no find way");
        }
        return result;
    }

    private ArrayList<Node> findMinWayToBomber(ArrayList<Node>queue,Node[][]listPrev, boolean[][] isVisited) {
        ArrayList<Node> temp = new ArrayList<Node>();
        for(int i = 0; i < queue.size(); i++) {
            temp.add(queue.get(i));
        }
        queue.clear();
        for(int i = 0; i < temp.size(); i++) {
            Node parentNode = temp.get(i);
            for(int j = 0; j < parentNode.listNode.size(); j++) {
                Node childNode = parentNode.listNode.get(j);
                if(childNode.isGrass && isVisited[childNode.coordinateX][childNode.coordinateY] == false) {
                    listPrev[childNode.coordinateX][childNode.coordinateY] = parentNode;
                    isVisited[childNode.coordinateX][childNode.coordinateY] = true;
                    queue.add(childNode);
                    if (childNode.coordinateX == Bomber.coordinateX && childNode.coordinateY == Bomber.coordinateY) {
                        ArrayList<Node> result = new ArrayList<Node>();
                        Node prevNode = childNode;
                        while (true) {
                            if(prevNode == null) {
                                break;
                            }
                            result.add(prevNode);
                            prevNode = listPrev[prevNode.coordinateX][prevNode.coordinateY];
                        }
                        return result;
                    }
                }
            }
        }
        temp.clear();
        if(queue.size() > 0) {
            return findMinWayToBomber(queue, listPrev,isVisited);
        }
        return null;
    }
}
