package com.liner.twod_exp.engine.primitives;


public class Rectangle extends Shape {
    public Rectangle(Vector2 position, double width, double height) {
        super(position,
                position.copy().add(0, height),
                position.copy().add(width, height),
                position.copy().add(width, 0)
        );
    }
}
