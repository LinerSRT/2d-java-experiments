package com.liner.twod_exp.engine;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

public abstract class Engine implements IRender, KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
    private final Renderer<Engine> renderer;
    protected LinkedList<Renderable> renderableStack;


    protected Engine() {
        this.renderableStack = new LinkedList<>();
        setup();
        renderer = new Renderer<>(this);
        renderer.addKeyListener(this);
        renderer.addMouseListener(this);
        renderer.addMouseMotionListener(this);
        renderer.addMouseWheelListener(this);
    }

    public abstract void setup();

    public void draw(Graphics2D graphics2D) {

    }

    public void update() {

    }

    public abstract EngineConfig getConfig();

    public Renderer<Engine> getRenderer() {
        return renderer;
    }

    public void addRenderObject(Renderable renderable) {
        this.renderableStack.add(renderable);
    }

    public void removeRenderObject(Renderable renderable) {
        this.renderableStack.remove(renderable);
    }

    public void setRenderablesStack(LinkedList<Renderable> renderablesStack) {
        this.renderableStack.clear();
        addRenderableStack(renderablesStack);
    }

    public void addRenderableStack(LinkedList<Renderable> renderablesStack) {
        this.renderableStack.addAll(renderablesStack);
    }

    @Override
    public LinkedList<Renderable> getRenderableStack() {
        return renderableStack;
    }


    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {

    }
}
