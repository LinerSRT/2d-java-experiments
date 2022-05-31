package com.liner.twod_exp.ringfantasy;

import com.liner.twod_exp.engine.core.ECore;
import com.liner.twod_exp.engine.math.MathUtils;
import com.liner.twod_exp.engine.math.Node;
import com.liner.twod_exp.engine.math.Vector2;
import org.joml.Math;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.security.Key;
import java.util.List;
import java.util.Random;

public class Camera3D {
    private static final int RAY_COUNT = 13 * 16;
    private static final int LUMINANCE_RATIO = 50;
    private static final int RENDER_HEIGHT_RATIO = 16;
    private static final float MAP_SCALE = 0.1f;
    private Vector2 position;
    private double FOV;
    private double screenWidth;
    private double screenHeight;
    private double angle;

    public Camera3D(double x, double y, double fov, double screenWidth, double screenHeight) {
        this.position = new Vector2(x, y);
        this.FOV = (float) java.lang.Math.toRadians(fov);
        this.screenWidth = screenWidth;
        this.screenHeight = screenWidth;
        this.angle = 0;
    }


    public void setPosition(Vector2 position) {
        this.position = position;
    }

    private interface CastResult {
        void cast(double distance, double angle, double factor, double xFactor, Node hitNode);
    }

    private void castRay(List<Node> nodes, Ray ray, CastResult castResult) {
        Node hitNode = null;
        double minDistance = 500;
        double xFactor = 0;
        for (Node node : nodes) {
            Vector2 intersection = ray.cast(node);
            if (intersection != null) {
                double distance = position.distance(intersection);
                if (distance < minDistance) {
                    hitNode = node;
                    minDistance = distance;
                    xFactor = hitNode.point1.distance(intersection);
                }
            }
        }

        double distance = minDistance * (float) Math.cos(ray.getAngle() - angle);
        double factor = (float) MathUtils.map(distance, 0, 500, 1, 0);
        castResult.cast(distance, ray.getAngle(), factor, xFactor, hitNode);
    }

    public void renderScene(ECore core, Map map, Player player) {
        Ray[] rays = new Ray[(int) screenWidth];
        double rayAngle = angle - (FOV / 2);
        double angleStep = FOV / screenWidth;
        for (int i = 0; i < screenWidth; i++) {
            rays[i] = new Ray(position, rayAngle, i);
            rayAngle += angleStep;
        }
        for (Ray ray : rays) {
            castRay(map.getAllNodes(), ray, new CastResult() {
                @Override
                public void cast(double distance, double angle, double factor, double xFactor, Node hitNode) {
                    double projectionPlane = (screenWidth / 8) / (float) Math.tan(FOV / 2);
                    double stripWidth = 0.5;
                    double stripHeight = (RENDER_HEIGHT_RATIO / distance) * projectionPlane;
                    Tile tile = map.getAssociatedTile(hitNode);
                    if (tile != null) {
                        double startX = ray.getIndex() * stripWidth;
                        double startY = screenHeight / 2 - stripHeight / 2;
                        try {
                            if (tile.getObject() != null) {
                                core.draw(resize(tile.getObject().getSubimage((int) xFactor, 0, 1, 16), 1, (int) stripHeight), (int) startX, (int) startY);
                            } else {
                                core.draw(resize(tile.getGround().getSubimage((int) xFactor, 0, 1, 16), 1, (int) stripHeight), (int) startX, (int) startY);
                            }
                        } catch (RasterFormatException e){

                        }

                    } else {
                        core.setColor(new Color(makeColorDarker(0x990000, factor)));
                        core.fillRect(ray.getIndex() * stripWidth, screenHeight / 2 - stripHeight / 2, 1, stripHeight);
                    }
                }
            });
        }
    }


    public static BufferedImage resize(BufferedImage bufferedImage, int width, int height) {
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = result.createGraphics();
        graphics2D.drawImage(bufferedImage.getScaledInstance(width, height, Image.SCALE_FAST), 0, 0, null);
        graphics2D.dispose();
        return result;
    }

    private void drawTextureSlice(BufferedImage bufferedImage, BufferedImage texture, int xPosition, int textureOffset, int sliceHeight) {
        if (texture == null)
            return;
        int[] slice = new int[(int) MathUtils.clamp(sliceHeight, 0, screenWidth)];
        double texRatio = 16f / sliceHeight;
        if (sliceHeight > screenWidth) { // if the wallHeight is greater than the height of the screen then only use part of the sliceHeight value.
            int start = (int) (sliceHeight / 2 - screenWidth / 2);
            for (int y = 0; y < screenWidth; y++) {
                slice[y] = texture.getRGB(java.lang.Math.max(textureOffset, 16), (int) (texRatio * (start + y)));
            }
        } else {
            for (int y = 0; y < sliceHeight; y++)
                slice[y] = texture.getRGB(java.lang.Math.max(textureOffset, 16), (int) (texRatio * (y)));
        }
        for (int s = 0; s < slice.length; s++) {
            bufferedImage.setRGB(xPosition, (int) (screenWidth / 2 - slice.length / 2 + s), slice[s]);
        }
    }

    public static double constrain(double n, double min, double max) {
        if (n < min) return min;
        if (n > max) return max;
        return n;
    }

    private int makeColorDarker(int color, double brightness) {
        if (brightness < 1.0) {
            int a = (color/* >> 24*/) & 0xFF;
            if (a > 0 && a < 255) {
                a += brightness;
            }
            int r = (int) (((color >> 24) & 0xFF) * brightness);
            int g = (int) (((color >> 16) & 0xFF) * brightness);
            int b = (int) (((color >> 8) & 0xFF) * brightness);

            return (a/* << 24*/) | (r << 24) | (g << 16) | (b << 8);
            //return (color & 0xFF000000) | (r << 16) | (g << 8) | b;
            //return (0xFF000000) | (r << 16) | (g << 8) | b;
        }
        return color;
    }

    public double getRotation() {
        return angle;
    }

    public void setRotation(double angle) {
        this.angle = angle;
    }

    private class RaycastResult {
        private Vector2 intersection;
        private Node hitWall;
        private double distance;

        public RaycastResult(Vector2 intersection, Node hitWall, double distance) {
            this.intersection = intersection;
            this.hitWall = hitWall;
            this.distance = distance;
        }

        public Vector2 getIntersection() {
            return intersection;
        }

        public Node getHitWall() {
            return hitWall;
        }

        public double getDistance() {
            return distance;
        }
    }
}
