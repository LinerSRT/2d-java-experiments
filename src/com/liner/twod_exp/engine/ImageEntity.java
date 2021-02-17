package com.liner.twod_exp.engine;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageEntity extends CollidableEntity{
    private final BufferedImage bufferedImage;
    private float scale = 1f;

    public ImageEntity(int positionX, int positionY, int widthPixels, int heightPixels, BufferedImage bufferedImage) {
        super(positionX, positionY, widthPixels, heightPixels);
        this.bufferedImage = bufferedImage;
    }

    @Override
    public void render(Graphics2D graphics2D) {
        graphics2D.drawImage(bufferedImage, getLeft(), getTop(), Math.round(getWidth()*scale), Math.round(getHeight()*scale), null);
        graphics2D.setColor(Color.RED);
        graphics2D.drawString(getX()+"|"+getY(), getRight(), getBottom());
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getScale() {
        return scale;
    }
}
