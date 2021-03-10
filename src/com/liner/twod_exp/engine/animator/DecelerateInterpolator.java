package com.liner.twod_exp.engine.animator;

public class DecelerateInterpolator extends Interpolator {
    @Override
    public double getFactor(double value) {
        return Math.pow(value, (1.0 / 3));
    }
}
