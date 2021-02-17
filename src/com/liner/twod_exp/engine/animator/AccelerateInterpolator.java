package com.liner.twod_exp.engine.animator;

public class AccelerateInterpolator extends Interpolator{
    @Override
    public double getFactor(double value) {
        return Math.pow(value, 3);
    }
}
