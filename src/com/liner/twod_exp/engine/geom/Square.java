package com.liner.twod_exp.engine.geom;

import com.liner.twod_exp.engine.math.Node;
import com.liner.twod_exp.engine.math.Vector2;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

public class Square extends Shape{
    private Vector2 position;
    private double width;
    private double height;
    private boolean fromCenter;



    public Square(Vector2 position, double width, double height, int color, boolean fromCenter) {
        super(color);
        this.position = position;
        this.width = width;
        this.height = height;
        this.fromCenter = fromCenter;
        calculateNodes();
    }

    public Square(Vector2 position, double width, double height, int color) {
        this(position, width, height, color, false);
    }

    private void calculateNodes() {
        this.nodes = new ArrayList<>();
        if(fromCenter){
            nodes.add(new Node(position.subtract(width/2, height/2), (position.subtract(width/2, height/2).add(width, 0))));
            nodes.add(new Node(position.subtract(width/2, height/2).add(width, 0),position.subtract(width/2, height/2).add(width, height)));
            nodes.add(new Node(position.subtract(width/2, height/2).add(width, height),position.subtract(width/2, height/2).add(0, height)));
            nodes.add(new Node(position.subtract(width/2, height/2).add(0, height),position.subtract(width/2, height/2)));

        } else {
            nodes.add(new Node(position, position.add(width, 0)));
            nodes.add(new Node(position.add(width, 0), position.add(width, height)));
            nodes.add(new Node(position.add(width, height), position.add(0, height)));
            nodes.add(new Node(position.add(0, height), position));
        }
    }

    public Square(Vector2 position, double width, double height) {
        this(position, width, height, 0xFFFFFF);
    }

    public Square(double x, double y, double width, double height, int color) {
        this(new Vector2(x, y), width, height, color);
    }

    public Square(double x, double y, double width, double height) {
        this(new Vector2(x, y), width, height, 0xFFFFFF);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
        calculateNodes();
    }

    public void setColor(int color) {
        this.color = color;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public int getColor() {
        return color;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setWidth(double width) {
        this.width = width;
        calculateNodes();
    }

    public void setHeight(double height) {
        this.height = height;
        calculateNodes();
    }

}
