package com.liner.twod_exp.engine;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.stream.IntStream;

@SuppressWarnings("unused")
public class Renderer<E extends Engine> extends Canvas implements Runnable {
    private Thread thread;
    private Window window;
    private E engine;
    private boolean isRunning = false;
    private final int updatesPerSecond;
    private final int framesPerSecond;
    private int FPS = 0;
    private int UPS = 0;
    private final int width;
    private final int height;

    public Renderer(E engine) {
        this.engine = engine;
        this.updatesPerSecond = engine.getConfig().getTicksPerSecond();
        this.framesPerSecond = engine.getConfig().getFramesPerSecond();
        this.width = engine.getConfig().getScreenWidth();
        this.height = engine.getConfig().getScreenHeight();
        window = new Window(
                width,
                height,
                engine.getConfig().getName(),
                "liner.png",
                this
        );
        start();
        requestFocus();
    }

    public Window getWindow() {
        return window;
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
                engine.update();
                IntStream.range(0, engine.getRenderableStack().size()).forEachOrdered(i -> engine.getRenderableStack().get(i).update(getUPS()));
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
                graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                graphics2D.setColor(Color.BLACK);
                graphics2D.fillRect(0, 0, width, height);
                engine.draw(graphics2D);
                IntStream.range(0, engine.getRenderableStack().size()).forEachOrdered(i -> engine.getRenderableStack().get(i).draw(graphics2D, engine.getConfig().getScreenHeight(), engine.getConfig().getScreenWidth()));

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
}
