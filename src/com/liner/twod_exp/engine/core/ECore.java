package com.liner.twod_exp.engine.core;


import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("unused")
public class ECore implements IRender, KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
    private final Graphics2D graphics2D;
    private AffineTransform transform;
    private final Rectangle bounds;
    private final boolean[] keysPressed;
    private final boolean[] mousePressed;
    private double mouseX;
    private double mouseY;
    private int mouseWheelDirection;
    private double mouseWheelAmount;
    private final LinkedList<InputListener> inputListeners;

    public ECore(Graphics2D graphics2D, Canvas canvas) {
        this.graphics2D = graphics2D;
        this.bounds = graphics2D.getDeviceConfiguration().getBounds();
        this.keysPressed = new boolean[255];
        this.mousePressed = new boolean[10];
        this.mouseX = 0;
        this.mouseY = 0;
        this.mouseWheelDirection = 0;
        this.mouseWheelAmount = 0;
        this.inputListeners = new LinkedList<>();
        canvas.addKeyListener(this);
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
        canvas.addMouseWheelListener(this);
    }

    public void addListener(InputListener inputListener) {
        if (!inputListeners.contains(inputListener))
            inputListeners.add(inputListener);
    }

    public void removeListener(InputListener inputListener) {
        inputListeners.add(inputListener);
    }


    @Override
    public void drawShape(Shape shape) {
        graphics2D.draw(shape);
    }

    @Override
    public void fillShape(Shape shape) {
        graphics2D.fill(shape);
    }

    @Override
    public void drawRect(double x, double y, double width, double height) {
        graphics2D.drawRect((int) x, (int) y, (int) width, (int) height);
    }

    @Override
    public void drawRect(float x, float y, float width, float height) {
        graphics2D.drawRect((int) x, (int) y, (int) width, (int) height);
    }

    @Override
    public void drawRect(int x, int y, int width, int height) {
        graphics2D.drawRect(x, y, width, height);
    }

    @Override
    public void fillRect(double x, double y, double width, double height) {
        graphics2D.fillRect((int) x, (int) y, (int) width, (int) height);
    }

    @Override
    public void fillRect(float x, float y, float width, float height) {
        graphics2D.fillRect((int) x, (int) y, (int) width, (int) height);
    }

    @Override
    public void fillRect(int x, int y, int width, int height) {
        graphics2D.fillRect(x, y, width, height);
    }

    @Override
    public void drawCircle(double x, double y, double width, double height) {
        graphics2D.drawOval((int) x, (int) y, (int) width, (int) height);
    }

    @Override
    public void drawCircle(float x, float y, float width, float height) {
        graphics2D.drawOval((int) x, (int) y, (int) width, (int) height);
    }

    @Override
    public void drawCircle(int x, int y, int width, int height) {
        graphics2D.drawOval(x, y, width, height);
    }

    @Override
    public void fillCircle(double x, double y, double width, double height) {
        graphics2D.fillOval((int) x, (int) y, (int) width, (int) height);
    }

    @Override
    public void fillCircle(float x, float y, float width, float height) {
        graphics2D.fillOval((int) x, (int) y, (int) width, (int) height);
    }

    @Override
    public void fillCircle(int x, int y, int width, int height) {
        graphics2D.fillOval(x, y, width, height);
    }

    @Override
    public void drawLine(double x, double y, double x2, double y2) {
        graphics2D.drawLine((int) x, (int) y, (int) x2, (int) y2);
    }

    @Override
    public void drawLine(float x, float y, float x2, float y2) {
        graphics2D.drawLine((int) x, (int) y, (int) x2, (int) y2);
    }

    @Override
    public void drawLine(int x, int y, int x2, int y2) {
        graphics2D.drawLine(x, y, x2, y2);
    }

    public void drawString(String string, double x, double y) {
        graphics2D.drawString(string, (int) x, (int) y);
    }

    public void drawString(String string, float x, float y) {
        graphics2D.drawString(string, (int) x, (int) y);
    }

    public void drawString(String string, int x, int y) {
        graphics2D.drawString(string, x, y);
    }

    @Override
    public void setColor(Color color) {
        graphics2D.setColor(color);
    }

    @Override
    public void setColor(float red, float green, float blue, float alpha) {
        setColor(new Color(red, green, blue, alpha));
    }

    @Override
    public void setColor(float red, float green, float blue) {
        setColor(new Color(red, green, blue));
    }

    @Override
    public void setColor(int red, int green, int blue, int alpha) {
        setColor(new Color(red, green, blue, alpha));
    }

    @Override
    public void setColor(int red, int green, int blue) {
        setColor(new Color(red, green, blue));
    }

    @Override
    public Graphics2D getGraphics() {
        return graphics2D;
    }

    @Override
    public void save() {
        transform = graphics2D.getTransform();
    }

    @Override
    public void restore() {
        if (transform != null)
            graphics2D.setTransform(transform);
    }

    @Override
    public void scale(double factor) {
        graphics2D.scale(factor, factor);
    }

    @Override
    public void scale(int factor) {
        graphics2D.scale(factor, factor);
    }

    @Override
    public void scale(double factorX, double factorY) {
        graphics2D.scale(factorX, factorY);
    }

    @Override
    public void scale(int factorX, int factorY) {
        graphics2D.scale(factorX, factorY);
    }

    @Override
    public void translate(double x, double y) {
        graphics2D.translate(x, y);
    }

    @Override
    public void translate(int x, int y) {
        graphics2D.translate(x, y);
    }

    @Override
    public double getWidth() {
        return bounds.getWidth();
    }

    @Override
    public double getHeight() {
        return bounds.getHeight();
    }

    @Override
    public double getCenterX() {
        return bounds.getCenterX();
    }

    @Override
    public double getCenterY() {
        return bounds.getCenterY();
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    public Rectangle2D getTextBounds(String text){
        return graphics2D.getFont().getStringBounds(text, new FontRenderContext(transform,true,true));
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        keysPressed[keyEvent.getKeyCode()] = true;
        for (InputListener inputListener : inputListeners)
            inputListener.keyPress(this, keyEvent.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        keysPressed[keyEvent.getKeyCode()] = false;
        for (InputListener inputListener : inputListeners)
            inputListener.keyRelease(this, keyEvent.getKeyCode());
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        for (InputListener inputListener : inputListeners)
            inputListener.mouseClick(this, mouseEvent.getButton());
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        mousePressed[mouseEvent.getButton()] = true;
        for (InputListener inputListener : inputListeners)
            inputListener.mousePress(this, mouseEvent.getButton());
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        mousePressed[mouseEvent.getButton()] = true;
        for (InputListener inputListener : inputListeners)
            inputListener.mouseRelease(this, mouseEvent.getButton());
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        mouseX = mouseEvent.getX();
        mouseY = mouseEvent.getY();
        for (InputListener inputListener : inputListeners)
            inputListener.mouseDrag(this, mouseX, mouseY);
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        mouseX = mouseEvent.getX();
        mouseY = mouseEvent.getY();
        for (InputListener inputListener : inputListeners)
            inputListener.mouseMove(this, mouseX, mouseY);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        mouseWheelDirection = mouseWheelEvent.getWheelRotation();
        mouseWheelAmount = mouseWheelEvent.getScrollAmount();
        for (InputListener inputListener : inputListeners)
            inputListener.mouseWheelMove(this, mouseWheelDirection, mouseWheelAmount);
    }

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public double getMouseWheelAmount() {
        return mouseWheelAmount;
    }

    public int getMouseWheelDirection() {
        return mouseWheelDirection;
    }

    public boolean isKeyPressed(int keycode) {
        return keysPressed[keycode];
    }

}
