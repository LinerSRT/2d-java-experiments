package com.liner.twod_exp.ray_casting;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ComponentAdapter;

public class RayCastSettings {
    private JSlider cameraFov;
    private JCheckBox fishCorrection;
    private JSlider viewDistance;
    private JLabel cameraFovText;
    private JLabel viewDistanceText;
    private JPanel panel;
    private RayCast rayCast;

    public RayCastSettings(RayCast rayCast, Camera3D camera3D) {
        this.rayCast = rayCast;
        createWindow();
        cameraFov.setValue((int) rayCast.getFov());
        viewDistance.setValue((int) rayCast.getLength());

        cameraFov.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                cameraFovText.setText("Camera FOV: "+cameraFov.getValue());
                rayCast.setFov(cameraFov.getValue());
            }
        });
        viewDistance.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                viewDistanceText.setText("View distance: "+viewDistance.getValue());
                rayCast.setLength(viewDistance.getValue());
            }
        });
        fishCorrection.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                camera3D.setEnableFishEyeCorrection(fishCorrection.isSelected());
            }
        });

    }

    private void createWindow(){
        JFrame frame = new JFrame("Camera settings");
        frame.setIconImage(new ImageIcon(System.getProperty("user.dir") + "/res/liner.png").getImage());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(
                400 + (frame.getInsets().left + frame.getInsets().right),
                200 + (frame.getInsets().top + frame.getInsets().bottom) + 27
        );
        frame.setLocation((int) (dimension.getWidth() / 2 - frame.getWidth() / 2), (int) (dimension.getHeight() / 2 - frame.getHeight() / 2));
        frame.setResizable(false);
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
