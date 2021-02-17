package com.liner.twod_exp.engine.animator;

public class LinearInterpolator extends Interpolator{
    @Override
    public double getFactor(double value) {
        return value;
    }
}
