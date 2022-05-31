package com.liner.twod_exp.engine.core;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface IRender {

    void draw(BufferedImage bufferedImage);

    void draw(BufferedImage bufferedImage, int x, int y);

    void drawShape(Shape shape);

    void fillShape(Shape shape);

    void drawRect(double x, double y, double width, double height);

    void drawRect(float x, float y, float width, float height);

    void drawRect(int x, int y, int width, int height);

    void fillRect(double x, double y, double width, double height);

    void fillRect(float x, float y, float width, float height);

    void fillRect(int x, int y, int width, int height);


    void drawCircle(double x, double y, double width, double height);

    void drawCircle(float x, float y, float width, float height);

    void drawCircle(int x, int y, int width, int height);

    void fillCircle(double x, double y, double width, double height);

    void fillCircle(float x, float y, float width, float height);

    void fillCircle(int x, int y, int width, int height);


    void drawLine(double x, double y, double width, double height);

    void drawLine(float x, float y, float width, float height);

    void drawLine(int x, int y, int width, int height);


    void setColor(Color color);

    void setColor(float red, float green, float blue, float alpha);

    void setColor(float red, float green, float blue);

    void setColor(int red, int green, int blue, int alpha);

    void setColor(int red, int green, int blue);

    Graphics2D getGraphics();

    void save();

    void restore();

    void scale(double factor);

    void scale(int factor);

    void scale(double factorX, double factorY);

    void scale(int factorX, int factorY);

    void translate(double x, double y);

    void translate(int x, int y);

    double getWidth();

    double getHeight();

    double getCenterX();

    double getCenterY();

    Rectangle getBounds();
}
