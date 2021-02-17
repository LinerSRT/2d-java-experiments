package com.liner.twod_exp.engine;

import javax.swing.*;
import java.awt.*;

public class Window<E extends Engine> extends Canvas {
    public JFrame frame;

    public Window(int width, int height, String name, String icon, Renderer<E> renderer) {
        frame = new JFrame(name);
        if (icon != null)
            frame.setIconImage(new ImageIcon(System.getProperty("user.dir") + "/res/" + icon).getImage());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        setSize(width, height);
        frame.setResizable(false);
        frame.add(renderer);
        frame.setVisible(true);
        renderer.start();
    }

    public void setSize(int width, int height) {
        frame.setSize(
                width + (frame.getInsets().left + frame.getInsets().right),
                height + (frame.getInsets().top + frame.getInsets().bottom) + 27
        );
    }

}
