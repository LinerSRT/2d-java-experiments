package com.liner.twod_exp.ringfantasy;

public abstract class Looper {
    public Looper(int[][] data) {
        for (int y = 0; y < data.length; y++) {
            for (int x = 0; x < data[y].length; x++) {
                loop(x, y, data[y][x]);
            }
        }
    }

    public abstract void loop(int x, int y, int data);
}
