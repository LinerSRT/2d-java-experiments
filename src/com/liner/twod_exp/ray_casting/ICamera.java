package com.liner.twod_exp.ray_casting;

import com.liner.twod_exp.engine.primitives.Vector2;

import java.awt.*;

public interface ICamera {
    double getWidth();
    double getHeight();
    Vector2 getPosition();
}
