package com.liner.twod_exp.engine.math;

public class MathUtils {
    public static <T extends Comparable<T>> T clamp(T val, T min, T max) {
        if (val.compareTo(min) < 0) return min;
        else if (val.compareTo(max) > 0) return max;
        else return val;
    }
    public static double map(double value, double[] from, double[] to) {
        return (to[1] - to[0]) / (from[1] - from[0]) * (value - from[0]) + to[0];
    }

}
