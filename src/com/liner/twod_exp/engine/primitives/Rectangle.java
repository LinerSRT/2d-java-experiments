package com.liner.twod_exp.engine.primitives;


public class Rectangle extends Shape {
    private Vector2 position;
    private double width;
    private double height;

    public Rectangle(Vector2 position, double width, double height) {
        super(position,
                position.copy().add(0, height),
                position.copy().add(width, height),
                position.copy().add(width, 0)
        );
        this.position = position;
        this.width = width;
        this.height = height;
    }

    private Rectangle(Rectangle rectangle){
        this(rectangle.getPosition(), rectangle.getWidth(), rectangle.getHeight());
    }

    public void setPosition(Vector2 position) {
        this.position = position;
        setVertices(position,
                position.copy().add(0, height),
                position.copy().add(width, height),
                position.copy().add(width, 0));
    }

    public void setWidth(double width) {
        this.width = width;
        setVertices(position,
                position.copy().add(0, height),
                position.copy().add(width, height),
                position.copy().add(width, 0));
    }

    public void setHeight(double height) {
        this.height = height;
        setVertices(position,
                position.copy().add(0, height),
                position.copy().add(width, height),
                position.copy().add(width, 0));
    }

    public Vector2 getPosition() {
        return position;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Rectangle copy(){
        return new Rectangle(this);
    }
}
