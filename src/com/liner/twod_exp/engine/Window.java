package com.liner.twod_exp.engine;

import javax.swing.*;
import java.awt.*;

public class Window extends Canvas {
    public JFrame frame;

    public Window(int width, int height, String name, String icon, Renderer renderer) {
        frame = new JFrame(name);
        if (icon != null)
            frame.setIconImage(new ImageIcon(System.getProperty("user.dir") + "/res/" + icon).getImage());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(width, height);
        frame.setLocation((int) (dimension.getWidth() / 2 - frame.getWidth() / 2), (int) (dimension.getHeight() / 2 - frame.getHeight() / 2));
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


    public JFrame getFrame() {
        return frame;
    }
}
