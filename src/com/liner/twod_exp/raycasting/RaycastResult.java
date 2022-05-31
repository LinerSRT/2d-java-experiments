package com.liner.twod_exp.raycasting;

import com.liner.twod_exp.engine.math.Vector2;

public class RaycastResult {
    public double distance;
    public Vector2 origin;
    public Vector2 intersection;
    public int mapX;
    public int mapY;
    public Direction direction;

    public RaycastResult(double distance, Vector2 origin,Vector2 intersection, int mapX, int mapY, Direction direction) {
        this.distance = distance;
        this.origin = origin;
        this.intersection = intersection;
        this.mapX = mapX;
        this.mapY = mapY;
        this.direction = direction;
    }

    public enum Direction{
        EAST,
        WEST,
        NORTH,
        SOUTH
    }
}
