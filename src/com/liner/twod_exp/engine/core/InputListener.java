package com.liner.twod_exp.engine.core;

public interface InputListener {
    void keyPress(ECore eCore, int keyCode);
    void mousePress(ECore eCore, int keyCode);
    void keyRelease(ECore eCore, int keyCode);
    void mouseRelease(ECore eCore, int keyCode);
    void mouseClick(ECore eCore, int keyCode);
    void mouseMove(ECore eCore, double x, double y);
    void mouseDrag(ECore eCore, double x, double y);
    void mouseWheelMove(ECore eCore, int direction, double amount);
}
