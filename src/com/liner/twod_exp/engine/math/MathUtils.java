package com.liner.twod_exp.engine.math;

public class MathUtils {
    public static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }
    public static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }
    public static double map(double value, double[] from, double[] to) {
        return (to[1] - to[0]) / (from[1] - from[0]) * (value - from[0]) + to[0];
    }
    public static double map(double val, double inLowerBound,
                            double inUpperBound, double outLowerBound, double outUpperBound) {
        return (((val - inLowerBound) * (outUpperBound - outLowerBound))
                / ((inUpperBound - inLowerBound))) + outLowerBound;
    }

}
