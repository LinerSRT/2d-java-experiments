package com.liner.twod_exp.engine;


public abstract class Entity implements Renderable, Location{
    private int positionX;
    private int positionY;
    private int positionTempX;
    private int positionTempY;
    private int widthPixels;
    private int heightPixels;
    private int accelerationX;
    private int accelerationY;

    public Entity(int positionX, int positionY, int widthPixels, int heightPixels) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.positionTempX = positionX;
        this.positionTempY = positionY;
        this.accelerationX = 0;
        this.accelerationY = 0;
        this.widthPixels = widthPixels;
        this.heightPixels = heightPixels;
    }

    public Entity(int positionX, int positionY, int sizePixels){
        this(positionX, positionY, sizePixels, sizePixels);
    }

    @Override
    public int getX() {
        return positionX;
    }

    @Override
    public int getY() {
        return positionY;
    }

    @Override
    public int getWidth() {
        return widthPixels;
    }

    @Override
    public int getHeight() {
        return heightPixels;
    }

    @Override
    public int getCenterX() {
        return positionX + (widthPixels / 2);
    }

    @Override
    public int getCenterY() {
        return positionY + (heightPixels / 2);
    }

    @Override
    public int getLeft() {
        return positionX;
    }

    @Override
    public int getRight() {
        return positionX + widthPixels;
    }

    @Override
    public int getTop() {
        return positionY;
    }

    @Override
    public int getBottom() {
        return positionY + heightPixels;
    }

    @Override
    public int getDistanceTo(Location location) {
        return (int) Math.hypot(getCenterX()-location.getCenterX(), getCenterY()-location.getCenterY());
    }

    @Override
    public int getAngleTo(Location location) {
        return (int) Math.toDegrees(Math.atan2(location.getCenterY() - getCenterY(), location.getCenterX() - getCenterX()));
    }

    @Override
    public void moveBy(int x, int y) {
        positionX += x;
        positionY += y;
    }

    @Override
    public void moveTo(int x, int y) {
        positionX = x;
        positionY = y;
        positionTempX = x;
        positionTempY = y;
    }

    @Override
    public void moveUntil(int x, int y) {
        positionTempX = x;
        positionTempY = y;
    }

    @Override
    public void accelerate(int xVel, int yVel, int friction) {
        accelerationX = xVel;
        accelerationY = yVel;
    }

    @Override
    public void tick(double updatesPerSecond) {
        //TODO Move entity in this method using temp values
    }
}
