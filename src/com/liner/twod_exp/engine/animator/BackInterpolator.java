package com.liner.twod_exp.engine.animator;

public class BackInterpolator extends Interpolator{
    protected float TENSION = 1.70158f;

    @Override
    public double getFactor(double value) {

        return value*value*((TENSION+1)*value - TENSION);
    }
}
