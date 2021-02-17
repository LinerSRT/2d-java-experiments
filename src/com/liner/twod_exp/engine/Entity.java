package com.liner.twod_exp.engine;


import com.liner.twod_exp.engine.animator.*;

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

    @Override
    public void moveBy(int x, int y) {
        positionX += x;
        positionY += y;
    }

    @Override
    public void moveTo(int x, int y) {
        positionX = x;
        positionY = y;
    }

    @Override
    public void moveUntil(int x, int y) {
        new Animator()
                .of(
                        new AnimationObject("posX", new int[]{positionX, x}),
                        new AnimationObject("posY", new int[]{positionY, y})
                )
                .setDuration(300)
                .setInterpolator(new OvershootInterpolator())
                .setStartDelay(0)
                .setAnimationListener(new AnimationListenerAdapter(){
                    @Override
                    public void onAnimate(Animator animator) {
                        int posX = (int) animator.getAnimatedValueOf("posX");
                        int posY = (int) animator.getAnimatedValueOf("posY");
                        positionX = posX;
                        positionY = posY;
                    }
                }).start();
    }
}
