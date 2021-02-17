package com.liner.twod_exp.engine;

public interface Location {
    int getX();
    int getY();
    int getWidth();
    int getHeight();
    int getCenterX();
    int getCenterY();
    int getLeft();
    int getRight();
    int getTop();
    int getBottom();
    int getDistanceTo(Location location);
    int getAngleTo(Location location);
    void moveTo(int x, int y);
    void moveBy(int x, int y);
    void moveUntil(int x, int y);
}
