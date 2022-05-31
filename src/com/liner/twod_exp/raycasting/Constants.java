package com.liner.twod_exp.raycasting;

public interface Constants {
    int textureSize = 16;
    int mapSizeX = 50;
    int mapSizeY = 50;
    int cellSize = 16;
    double FOV = Math.PI / 3;
    int screenWidth = 250;
    int screenHeight = 250;
    double sigma = .000005;
    double lookSensitivity = 250;


    static double validateAngle(double angle) {
        if (angle >= 2 * Math.PI) return angle % (2 * Math.PI);
        if (angle < 0) return 2 * Math.PI + angle;
        return angle;
    }
}
