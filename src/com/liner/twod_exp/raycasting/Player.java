package com.liner.twod_exp.raycasting;

import com.liner.twod_exp.engine.math.Vector2;

public class Player implements Constants {
    public Vector2 position;
    public Vector2 direction;
    public Vector2 viewDirection;
    public final int[][] map;
    private double velocity = 0.5;

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getVelocity() {
        return velocity;
    }

    public Player(double x, double y, int[][] map) {
        this.position = new Vector2(x, y);
        this.direction = new Vector2();
        this.map = map;
        this.viewDirection = new Vector2();
    }

    public void normalizeVelocity() {
        double cv = direction.length();
        if (cv == 0) return;
        direction.x = direction.x / cv * velocity;
        direction.y = direction.y / cv * velocity;
    }

    private double stepX() {
        if (position.x + direction.x >= map[0].length * cellSize)
            return 0;
        if (position.x + direction.x < 0)
            return map[0].length * cellSize - 1;
        return position.x + direction.x;
    }

    private double stepY() {
        if (position.y + direction.y >= map.length * cellSize)
            return 0;
        if (position.y + direction.y < 0)
            return map.length * cellSize - 1;
        return position.y + direction.y;
    }

    private double edgeX() {
        double distanceToTop = (int) (position.x / cellSize) * cellSize - position.x;
        double distanceToBottom = (int) (position.x / cellSize + 1) * cellSize - position.x;
        return Math.abs(distanceToTop) > Math.abs(distanceToBottom) ?
                distanceToBottom - 1 :
                distanceToTop + 1;
    }

    private double edgeY() {
        double distanceToLeft = (int) (position.y / cellSize) * cellSize - position.y;
        double distanceToRight = (int) (position.y / cellSize + 1) * cellSize - position.y;
        return Math.abs(distanceToLeft) > Math.abs(distanceToRight) ?
                distanceToRight - 1 :
                distanceToLeft + 1;
    }

    private void boundInMap() {
        if (position.x >= map[0].length * cellSize)
            position.x = 0;
        if (position.x < 0)
            position.x = map[0].length * cellSize - 1;
        if (position.y >= map.length * cellSize)
            position.y = 0;
        if (position.y < 0)
            position.y = map.length * cellSize - 1;
    }

    public void update() {
        normalizeVelocity();
        boundInMap();
        if (map[(int) (position.y / cellSize)][(int) (stepX() / cellSize)] == 1)
            direction.x = edgeX();
        if (map[(int) (stepY() / cellSize)][(int) (position.x / cellSize)] == 1)
            direction.y = edgeY();
        if (viewDirection.x < 0)
            viewDirection.x += 2 * Math.PI;
        viewDirection.x %= 2 * Math.PI;
        position.x = stepX();
        position.y = stepY();
        direction.x = 0;
        direction.y = 0;
    }
}
