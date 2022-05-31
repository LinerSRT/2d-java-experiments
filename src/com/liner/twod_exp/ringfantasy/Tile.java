package com.liner.twod_exp.ringfantasy;

import com.liner.twod_exp.engine.core.ECore;
import com.liner.twod_exp.engine.math.Node;
import com.liner.twod_exp.engine.math.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.liner.twod_exp.ringfantasy.RingRender.tileSize;

public class Tile {
    private final BufferedImage ground;
    private BufferedImage object;
    private final Event event;
    private final int x;
    private final int y;
    private final int data;
    private final Node[] nodes;

    public Tile(int x, int y, int data) {
        this.x = x;
        this.y = y;
        this.data = data;
        ground = Resource.tilesImages[data % 100];
        event = data / 10000 != 0 ? Resource.eventList.get(data / 10000) : null;
        object = data % 10000 / 100 != 0 ? Resource.objectImages[data % 10000 / 100] : null;
        if (event != null && event.type == 7) {
            object = Resource.monsterImages[Resource.getInt(event.data, 1)];
        }
        nodes = new Node[4];
        Vector2 topLeft = new Vector2(x, y).multiply(tileSize);
        Vector2 topRight = new Vector2(x + 1, y).multiply(tileSize);
        Vector2 bottomLeft = new Vector2(x, y + 1).multiply(tileSize);
        Vector2 bottomRight = new Vector2(x + 1, y + 1).multiply(tileSize);
        nodes[0] = new Node(topLeft, topRight);
        nodes[1] = new Node(topRight, bottomRight);
        nodes[2] = new Node(bottomRight, bottomLeft);
        nodes[3] = new Node(bottomLeft, topLeft);
    }

    public void draw(ECore core) {
        core.draw(ground, x * tileSize, y * tileSize);
        if (object != null)
            core.draw(object, x * tileSize, y * tileSize);
    }

    public int getScaled(int i) {
        return tileSize * i;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public Event getEvent() {
        return event;
    }

    public int getData() {
        return data;
    }

    public BufferedImage getGround() {
        return ground;
    }

    public BufferedImage getObject() {
        return object;
    }

    public boolean haveCollision() {
        int tileID = data % 100;
        int objectID = (data % 10000) / 100;
        int eventID = data / 10000;
        if (eventID != 0) {
            for (Event event : Resource.eventList) {
                if (event.id == eventID) {
                    if (eventID == 59)
                        return true;
                    if (eventID == 97 || eventID == 100 || eventID == 105)
                        return false;
                }
            }
        }
        if (objectID == 0) {
            return tileID > 18;
        } else return objectID > 1;
    }

    public Node[] getNodes() {
        return nodes;
    }

    public Node getClosest(Node hitNode){
        double distance = Double.MAX_VALUE;
        Node closestNode = null;
        for (Node node : nodes) {
            Vector2 intersection = node.intersects(hitNode);
            if (intersection != null) {
                if(closestNode == null || hitNode.point1.distance(intersection) < distance){
                    closestNode = node;
                    distance = distance;
                }
            }
        }
        return closestNode;
    }

}
