package com.liner.twod_exp.engine;


public abstract class Entity implements Renderable, Location{
    private int positionX;
    private int positionY;
    private int widthPixels;
    private int heightPixels;

    public Entity(int positionX, int positionY, int widthPixels, int heightPixels) {
        this.positionX = positionX;
        this.positionY = positionY;
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
}
