package com.liner.twod_exp.engine;


public abstract class CollidableEntity extends Entity implements Collidable {

    public CollidableEntity(int positionX, int positionY, int widthPixels, int heightPixels) {
        super(positionX, positionY, widthPixels, heightPixels);
    }

    public CollidableEntity(int positionX, int positionY, int sizePixels) {
        super(positionX, positionY, sizePixels);
    }

    @Override
    public boolean isCollideWith(CollidableEntity entity) {
        return getDistanceTo(entity) <= Math.max(getWidth(), getHeight()) / 2;
    }

    @Override
    public boolean isNearTo(CollidableEntity entity) {
        return getDistanceTo(entity) <= Math.max(getWidth(), getHeight());
    }
}
