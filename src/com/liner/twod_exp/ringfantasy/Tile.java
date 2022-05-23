package com.liner.twod_exp.ringfantasy;

import com.liner.twod_exp.engine.core.ECore;
import com.liner.twod_exp.engine.math.Node;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage image;
    public BufferedImage objectImage;
    public Node[] nodes;
    public int x;
    public int y;


    public Tile(BufferedImage image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.nodes = new Node[4];
        nodes[0] = new Node(x, y, x + 1, y);
        nodes[1] = new Node(x+1, y, x+1, y+1);
        nodes[2] = new Node(x+1, y+1, x, y+1);
        nodes[3] = new Node(x, y+1, x, y);
    }


    public void draw(ECore core) {
        core.draw(image, x * RingRender.tileSize, y * RingRender.tileSize);
        if (objectImage != null)
            core.draw(objectImage, x * RingRender.tileSize, y * RingRender.tileSize);
    }
}
