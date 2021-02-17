package com.liner.twod_exp.engine;

public interface Collidable {
    boolean isCollideWith(CollidableEntity entity);
    boolean isNearTo(CollidableEntity entity);
}
