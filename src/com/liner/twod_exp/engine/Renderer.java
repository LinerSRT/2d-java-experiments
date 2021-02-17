package com.liner.twod_exp.engine;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

@SuppressWarnings("unused")
public class Renderer<E extends Engine> extends Canvas implements Runnable, java.awt.event.KeyListener, MouseMotionListener, MouseListener, MouseWheelListener {
    private Thread thread;
    private boolean isRunning = false;
    private final int updatesPerSecond;
    private final int framesPerSecond;
    private int FPS = 0;
    private int UPS = 0;
    private int width;
    private int height;
    private final E engine;

    public Renderer(E engine) {
        this.updatesPerSecond = engine.getConfig().getTicksPerSecond();
        this.framesPerSecond = engine.getConfig().getFramesPerSecond();
        this.width = engine.getConfig().getScreenWidth();
        this.height = engine.getConfig().getScreenHeight();
        this.engine = engine;
        addKeyListener(this);
        addMouseListener(this);
        addMouseWheelListener(this);
        addMouseMotionListener(this);
        new Window<>(
                width,
                height,
                engine.getConfig().getName(),
                "liner.png",
                this
        );
        start();
        requestFocus();
    }

    @Override
    public void run() {
        long initialTime = System.nanoTime();
        final double timeU = 1000000000D / updatesPerSecond;
        final double timeF = 1000000000D / framesPerSecond;
        double deltaU = 0, deltaF = 0;
        int tUPS = 0;
        int tFPS = 0;
        long timer = System.currentTimeMillis();
        long debugTimer = System.currentTimeMillis();
        while (isRunning) {
            long currentTime = System.nanoTime();
            deltaU += (currentTime - initialTime) / timeU;
            deltaF += (currentTime - initialTime) / timeF;
            initialTime = currentTime;
            if (deltaU >= 1) {
                engine.tick(getUPS());
                tUPS++;
                deltaU--;
            }
            if (deltaF >= 1) {
                BufferStrategy bufferStrategy = getBufferStrategy();
                if (bufferStrategy == null) {
                    createBufferStrategy(3);
                    return;
                }
                Graphics2D graphics2D = (Graphics2D) bufferStrategy.getDrawGraphics();
                graphics2D.setColor(Color.BLACK);
                graphics2D.fillRect(0, 0, width, height);
                engine.render(graphics2D);
                graphics2D.dispose();
                bufferStrategy.show();
                tFPS++;
                deltaF--;
            }
            if (System.currentTimeMillis() - timer > 1000) {
                FPS = tFPS;
                UPS = tUPS;
                tFPS = 0;
                tUPS = 0;
                timer += 1000;
            }
        }
    }

    public synchronized void start() {
        if (thread != null)
            stop();
        thread = new Thread(this);
        thread.start();
        isRunning = true;
    }

    public synchronized void stop() {
        if (thread == null)
            return;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            thread.interrupt();
        }
        thread = null;
        isRunning = false;
    }

    public int getFPS() {
        return FPS;
    }

    public int getUPS() {
        return UPS;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        engine.keyPressed(keyEvent);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

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
