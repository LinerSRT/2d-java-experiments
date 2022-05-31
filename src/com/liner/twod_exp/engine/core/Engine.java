package com.liner.twod_exp.engine.core;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

@SuppressWarnings("unused")
public class Engine implements Runnable {
    private static Engine engine;
    private final LinkedList<Renderer> renderers;
    private Renderer currentRenderer = null;
    private ECore ECore;
    private final JFrame window;
    private Thread renderThread;
    private final Canvas canvas;
    private BufferStrategy bufferStrategy;
    private Graphics2D graphics2D;
    private BufferedImage bufferedImage;
    private int frameRate;
    private int tickRate;
    private int windowWidth;
    private int windowHeight;
    private float windowScale;
    private long frameTime;
    private boolean isRendering;
    private String windowName;

    public static JFrame getWindow(){
        return engine.window;
    }

    public static Engine init(int windowWidth, int windowHeight) {
        return init(windowWidth, windowHeight, 1);
    }

    public static Engine init(int windowWidth, int windowHeight, float windowScale) {
        if (engine == null)
            return engine = new Engine(windowWidth, windowHeight, windowScale);
        return engine;
    }

    private Engine(int windowWidth, int windowHeight, float windowScale) {
        this.bufferedImage = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_ARGB);
        this.renderers = new LinkedList<>();
        this.canvas = new Canvas();
        window = new JFrame("2D Engine");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.windowWidth = (int) ((windowWidth  + (window.getInsets().left + window.getInsets().right) + 1)*windowScale);
        this.windowHeight = (int) ((windowHeight  + (window.getInsets().top + window.getInsets().bottom)+ 24)*windowScale);
        window.setSize(
                this.windowWidth,
                this.windowHeight
        );
        canvas.setSize(
                this.windowWidth,
                this.windowHeight
        );
        window.setLocation((int) (dimension.getWidth() / 2 - window.getWidth() / 2), (int) (dimension.getHeight() / 2 - window.getHeight()) / 2);
        window.setResizable(false);
        window.add(canvas);
        frameRate = 10000;
        tickRate = 60;
        isRendering = false;
    }

    public static void register(Renderer renderer) {
        if (!engine.renderers.contains(renderer)) {
            renderer.setCore(engine.ECore);
            engine.renderers.add(renderer);
        }
    }

    public static void unregister(Renderer renderer) {
        renderer.setCore(null);
        engine.renderers.remove(renderer);
    }

    public static void show(String identifier) {
        Renderer target = null;
        for (Renderer renderer : engine.renderers) {
            if (renderer.getIdentifier().equals(identifier)) {
                target = renderer;
                break;
            }
        }
        if (target != null)
            engine.currentRenderer = target;
        else
            throw new RuntimeException("Cannot find renderer[" + identifier + "] !");
    }
    public static void show(Renderer renderer) {
        Renderer target = null;
        for (Renderer r : engine.renderers) {
            if (r.getIdentifier().equals(renderer.getIdentifier())) {
                target = renderer;
                break;
            }
        }
        if (target != null)
            engine.currentRenderer = target;
        else
            throw new RuntimeException("Cannot find renderer[" + renderer.getIdentifier() + "] !");
    }

    public static void hide() {
        engine.currentRenderer = null;
    }

    public static void displayWindow(String windowName) {
        engine.windowName = windowName;
        engine.window.setTitle(windowName);
        engine.window.setVisible(true);
        engine.window.requestFocus();
        engine.canvas.createBufferStrategy(3);
        engine.bufferStrategy = engine.canvas.getBufferStrategy();
        engine.graphics2D = (Graphics2D) engine.bufferStrategy.getDrawGraphics();
        engine.graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        engine.graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        engine.ECore = new ECore(engine.graphics2D, engine.canvas);
    }

    public static synchronized void start() {
        if (engine.renderThread != null)
            stop();
        engine.renderThread = new Thread(engine);
        engine.renderThread.start();
        engine.isRendering = true;
    }

    public static synchronized void stop() {
        if (engine.renderThread == null)
            return;
        try {
            engine.renderThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            engine.renderThread.interrupt();
        }
        engine.renderThread = null;
        engine.isRendering = false;
    }


    public static void setFrameRate(int frameRate) {
        engine.frameRate = frameRate;
    }

    public static void setTickRate(int tickRate) {
        engine.tickRate = tickRate;
    }

    public static int getFrameRate() {
        return engine.frameRate;
    }

    public static int getTickRate() {
        return engine.tickRate;
    }

    public static int getWindowHeight() {
        return engine.windowHeight;
    }

    public static int getWindowWidth() {
        return engine.windowWidth;
    }

    public static void setScale(float scale){
        engine.ECore.scale(scale);
    }

    public static void setLocation(int x, int y){
        engine.window.setLocation(x, y);

    }

    @Override
    public void run() {
        long initialTime = System.nanoTime();
        final double timeU = 1000000000D / tickRate;
        final double timeF = 1000000000D / frameRate;
        double deltaU = 0, deltaF = 0;
        int tUPS = 0;
        int tFPS = 0;
        long timer = System.currentTimeMillis();
        while (isRendering) {
            long currentTime = System.nanoTime();
            deltaU += (currentTime - initialTime) / timeU;
            deltaF += (currentTime - initialTime) / timeF;
            initialTime = currentTime;
            if (deltaU >= 1) {
                if (currentRenderer != null && ECore != null)
                    ECore.addListener(currentRenderer);
                    currentRenderer.tick(ECore, engine.frameTime);
                tUPS++;
                deltaU--;
            }
            if (deltaF >= 1) {
                if (currentRenderer != null && ECore != null) {
                    ECore.setColor(new Color(0, 0, 0, 218));
                    ECore.fillRect(0, 0, ECore.getWidth(), ECore.getHeight());
                    currentRenderer.render(ECore);
                    bufferStrategy.show();
                }
                tFPS++;
                deltaF--;
            }
            if (System.currentTimeMillis() - timer > 500) {
                frameRate = tFPS;
                frameRate = tUPS;
                window.setTitle(windowName + " | " + (tFPS * 2) + " FPS");
                tFPS = 0;
                tUPS = 0;
                timer += 500;
            }
            engine.frameTime = currentTime;
        }
    }
}
