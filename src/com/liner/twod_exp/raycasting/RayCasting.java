package com.liner.twod_exp.raycasting;

import com.liner.twod_exp.engine.animator.AnimationListenerAdapter;
import com.liner.twod_exp.engine.animator.AnimationObject;
import com.liner.twod_exp.engine.animator.Animator;
import com.liner.twod_exp.engine.animator.BounceInterpolator;
import com.liner.twod_exp.engine.core.ECore;
import com.liner.twod_exp.engine.core.Engine;
import com.liner.twod_exp.engine.core.Renderer;
import com.liner.twod_exp.engine.math.MathUtils;
import com.liner.twod_exp.engine.math.Vector2;
import com.liner.twod_exp.ringfantasy.Resource;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

import static com.liner.twod_exp.ringfantasy.Resource.resize;

public class RayCasting extends Renderer implements Constants {
    private static int[][] map;
    private Vector2 mousePosition;
    private Player player;
    private Texture texture;
    private BufferedImage bufferedImage;

    @Override
    public void create() {

        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        bufferedImage = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
        map = new int[mapSizeY][mapSizeX];
        iterateMap(new MapIterator() {
            @Override
            public void iterate(int x, int y, int data) {
                map[y][x] = new Random().nextFloat() > 0.9f ? new Random().nextInt(25) : 0;
                if (x == 0 || x == mapSizeX - 1)
                    map[y][x] = new Random().nextInt(25);
                if (y == 0 || y == mapSizeY - 1)
                    map[y][x] = new Random().nextInt(25);
            }
        });

        iterateMap(new MapIterator() {
            @Override
            public void iterate(int x, int y, int data) {
                if (data == 0 && player == null)
                    player = new Player(x * cellSize, y * cellSize, map);
            }
        });

        mousePosition = new Vector2(5, 5).multiply(cellSize).add(cellSize / 2f);
        texture = new Texture(Resource.tilesImages[1]);
    }

    private int textureIndex = 1;


    @Override
    public void render(ECore core) {
        core.getGraphics().drawImage(bufferedImage, 0, 0, null);


        drawCrosshair(core, 8, 0xFFFFFF);


    }

    private int getColor(int color, double distance) {
        int newColor = (int) (color * Math.pow(4, -1 * (distance) / 160));
        return Math.max(0, Math.min(255, newColor));
    }


    private void drawCrosshair(ECore core, int size, int color) {
        core.setColor(color);
        core.drawLine(screenWidth / 2 - size / 2, screenHeight / 2, screenWidth / 2 + size / 2, screenHeight / 2);
        core.drawLine(screenWidth / 2, screenHeight / 2 - size / 2, screenWidth / 2, screenHeight / 2 + size / 2);
    }


    @Override
    public void tick(ECore core, long frameTime) {
        if(core.isKeyPressed(KeyEvent.VK_SHIFT)){
            player.setVelocity(2);
        } else {
            player.setVelocity(0.5);
        }
        if (core.isKeyPressed(KeyEvent.VK_W)) {
            player.direction = player.direction.add(
                    player.getVelocity() * Math.cos(player.viewDirection.x + Math.toRadians(30)),
                    player.getVelocity() * Math.sin(player.viewDirection.x + Math.toRadians(30))
            );
        }
        if (core.isKeyPressed(KeyEvent.VK_S)) {
            player.direction = player.direction.subtract(
                    player.getVelocity() * Math.cos(player.viewDirection.x - Math.toRadians(-30)),
                    player.getVelocity() * Math.sin(player.viewDirection.x - Math.toRadians(-30))
            );
        }
        if (core.isKeyPressed(KeyEvent.VK_A)) {
            player.direction = player.direction.add(
                    player.getVelocity() * Math.cos(player.viewDirection.x + Math.toRadians(-45)),
                    player.getVelocity() * Math.sin(player.viewDirection.x + Math.toRadians(-45))
            );
        }
        if (core.isKeyPressed(KeyEvent.VK_D)) {
            player.direction = player.direction.add(
                    player.getVelocity() * Math.cos(player.viewDirection.x - Math.toRadians(245)),
                    player.getVelocity() * Math.sin(player.viewDirection.x - Math.toRadians(245))
            );
        }
        if (core.isKeyPressed(KeyEvent.VK_LEFT)) {
            player.viewDirection.x -= 2 * Math.PI / lookSensitivity;
        }
        if (core.isKeyPressed(KeyEvent.VK_RIGHT)) {
            player.viewDirection.x += 2 * Math.PI / lookSensitivity;
        }
        if (core.isKeyPressed(KeyEvent.VK_DOWN)) {
            player.viewDirection.y -= 4;
        }
        if (core.isKeyPressed(KeyEvent.VK_UP)) {
            player.viewDirection.y += 4;
        }

        player.update();
        mousePosition = new Vector2(core.getMouseX(), core.getMouseY());


        drawRayCast();

    }


    private void drawRayCast() {
        Draw.clear(bufferedImage, 0);
        Draw.fill(bufferedImage,
                new Vector2(0, 0),
                new Vector2(screenWidth, screenHeight / 2.0 + player.viewDirection.y),
                new Color(65, 154, 253).getRGB()
        );
        Draw.fill(bufferedImage,
                new Vector2(0, screenHeight / 2.0 + player.viewDirection.y),
                new Vector2(screenWidth, screenHeight),
                new Color(100, 100, 100).getRGB()
        );
        double rayAngle;
        double height;

        for (int i = 0; i < screenWidth; i++) {
            rayAngle = Constants.validateAngle(player.viewDirection.x + i * FOV / screenWidth);
            Raycast ray = new Raycast(player.position, rayAngle, map, screenWidth * 2);
            RaycastResult raycastResult = ray.raycast();
            if (raycastResult == null)
                continue;

            height = cellSize * screenHeight / Math.pow((raycastResult.distance), 1.0);
            Texture texture = new Texture(Resource.tilesImages[map[raycastResult.mapY][raycastResult.mapX]]);
            int startY = (int) ((screenHeight / 2.0 - height / 2) + player.viewDirection.y);
            floorCast(startY + height, raycastResult);
            int textureX = 0;
            switch (raycastResult.direction) {
                case SOUTH -> textureX = (int) (raycastResult.intersection.x % textureSize);
                case NORTH -> textureX = textureSize - (int) (raycastResult.intersection.x % textureSize);
                case WEST -> textureX = textureSize - (int) (raycastResult.intersection.y % textureSize);
                case EAST -> textureX = (int) (raycastResult.intersection.y % textureSize);
            }
            for (int y = 0; y < height; y++) {
                int textureColor = texture.getRGB(textureX, (int) (y * (textureSize / height)));
                switch (raycastResult.direction) {
                    case EAST, SOUTH -> textureColor = Draw.manipulateColor(textureColor, 0.7f);
                }
                bufferedImage.setRGB(i, MathUtils.clamp(startY + y, 0, screenHeight - 1), textureColor);
            }
        }
    }

    private void floorCast(double drawEnd, RaycastResult raycastResult) {
//
//        double floorXWall, floorYWall;
//        if (raycastResult.direction == RaycastResult.Direction.NORTH && raycastResult.intersection.x > 0) {
//            floorXWall = raycastResult.mapX;
//            floorYWall = raycastResult.mapY + raycastResult.intersection.x;
//        } else if (raycastResult.direction == RaycastResult.Direction.EAST && raycastResult.intersection.x < 0) {
//            floorXWall = raycastResult.mapX + 1.0;
//            floorYWall = raycastResult.mapY + raycastResult.intersection.x;
//        } else if (raycastResult.direction == RaycastResult.Direction.WEST && raycastResult.intersection.x > 0) {
//            floorXWall = raycastResult.mapX + raycastResult.intersection.x;
//            floorYWall = raycastResult.mapY;
//        } else {
//            floorXWall = raycastResult.mapX + raycastResult.intersection.x;
//            floorYWall = raycastResult.mapY + 1.0;
//        }
//
//        for (int y = (int) drawEnd; y < screenHeight; y++) {
//            double currentDist = (screenHeight) / (2.0 * y - (screenHeight));
//            double weight = Math.abs(currentDist / (raycastResult.distance + raycastResult.distance));
//            double floorX = Math.abs(weight * floorXWall + (1.0 - weight) * raycastResult.origin.x);
//            double floorY = Math.abs(weight * floorYWall + (1.0 - weight) * raycastResult.origin.y);
//            int u = (int) (floorX * textureSize) % textureSize;
//            int v = (int) (floorY * textureSize) % textureSize;
//            int color = Resource.tilesImages[1].getRGB(u,v);
//            int tx = (int) (floorX - Math.floor(floorX));
//            int ty = (int) (floorY - Math.floor(floorY));
//            bufferedImage.setRGB(tx, ty, color);
//        }
    }

    @Override
    public void mouseWheelMove(ECore core, int direction, double amount) {
        if (direction == 1) {
            textureIndex = Math.max(0, textureIndex - 1);
        } else {
            textureIndex = Math.min(textureIndex + 1, Resource.tilesImages.length - 1);
        }
        texture = new Texture(Resource.tilesImages[textureIndex]);
    }

    private Robot robot;
    double oldX = 0;

    @Override
    public void mouseMove(ECore core, double x, double y) {
        super.mouseMove(core, x, y);
        if (oldX != x) {
            double diff = oldX - x;
            if (diff < 0) {
                player.viewDirection.x += Math.toRadians(2);
            } else {

                player.viewDirection.x -= Math.toRadians(2);
            }
            oldX = x;
        }

    }

    @Override
    public int getWindowWidth() {
        return screenWidth;
    }

    @Override
    public int getWindowHeight() {
        return screenHeight;
    }


    public static void main(String[] args) {
        start(new RayCasting(), 2);
        Engine.setLocation(0, 0);
    }


    interface MapIterator {
        void iterate(int x, int y, int data);
    }

    private void iterateMap(MapIterator iterator) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                iterator.iterate(x, y, map[y][x]);
            }
        }
    }


    private Vector2 getCellFor(Vector2 position) {
        int x = (int) Math.floor(position.x / 16);
        int y = (int) Math.floor(position.y / 16);
        return new Vector2(Math.max(0, Math.min(x, map[0].length - 1)), Math.max(0, Math.min(y, map.length - 1)));
    }
}
