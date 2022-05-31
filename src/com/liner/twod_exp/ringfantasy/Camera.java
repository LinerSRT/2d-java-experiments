package com.liner.twod_exp.ringfantasy;

import com.liner.twod_exp.engine.core.ECore;
import com.liner.twod_exp.engine.math.Node;
import com.liner.twod_exp.engine.math.Vector2;

import java.util.LinkedList;

import static com.liner.twod_exp.engine.math.MathUtils.map;
import static com.liner.twod_exp.ringfantasy.RingRender.tileSize;
import static com.liner.twod_exp.ringfantasy.RingRender.viewPortSize;

public class Camera {
    private static int mapWidth;
    private static int mapHeight;
    private static int[][] mapData;
    private int[][] cameraData;
    private double fov = 90;
    private double viewDistance = 50;
    private double rayCount = tileSize * viewPortSize;
    private double segmentWidth;

    private int cameraSize = tileSize * viewPortSize;
    private Map map;

    private Camera3D camera3D;

    public Camera(Map map, Player player) {
        this.map = map;
        this.mapData = map.getMapData();
        this.cameraData = new int[viewPortSize][viewPortSize];
        this.mapWidth = map.getMapWidth();
        this.mapHeight = map.getMapHeight();
        int leftBound = Math.min(mapWidth - viewPortSize, Math.max(0, Math.min(player.x - viewPortSize / 2, player.x)));
        int topBound = Math.min(mapHeight - viewPortSize, Math.max(0, Math.min(player.y - viewPortSize / 2, player.y)));
        for (int y = 0; y < viewPortSize; y++) {
            System.arraycopy(mapData[topBound + y], leftBound, cameraData[y], 0, viewPortSize);
        }
        camera3D = new Camera3D(player.x, player.y, 60, cameraSize, cameraSize);

        for (int i = 0; i < rayCount; i++) {
            rays.add(new Node(
                            new Vector2(player.x, player.y).multiply(tileSize),
                            map(i, new double[]{0, rayCount}, new double[]{player.angle - fov / 2f, player.angle + fov / 2f}),
                            viewDistance
                    )
            );
        }
    }


    public void draw(ECore core, Player player) {
        int leftBound = Math.min(mapWidth - viewPortSize, Math.max(0, Math.min(player.x - viewPortSize / 2, player.x)));
        int topBound = Math.min(mapHeight - viewPortSize, Math.max(0, Math.min(player.y - viewPortSize / 2, player.y)));
        for (int y = 0; y < viewPortSize; y++) {
            System.arraycopy(mapData[topBound + y], leftBound, cameraData[y], 0, viewPortSize);
        }
        new Looper(cameraData) {
            @Override
            public void loop(int x, int y, int data) {
                Map.getTile(data, x, y).draw(core);
            }
        };
        core.draw(player.playerImages[player.direction], (player.x - leftBound) * tileSize, (player.y - topBound) * tileSize);

        draw3d(core, player);
    }


    private final LinkedList<Node> rays = new LinkedList<>();


    private double POV = Math.PI/4+0.3;
    private void draw3d(ECore core, Player player) {
        core.save();
        core.translate(tileSize * viewPortSize, 0);
        int cameraSize = tileSize * viewPortSize;
        int leftBound = Math.min(mapWidth - viewPortSize, Math.max(0, Math.min(player.x - viewPortSize / 2, player.x)));
        int topBound = Math.min(mapHeight - viewPortSize, Math.max(0, Math.min(player.y - viewPortSize / 2, player.y)));
        Vector2 playerPosition = new Vector2(player.x, player.y).multiply(tileSize).add(8,8);


        camera3D.setPosition(playerPosition);
        camera3D.setRotation(Math.toRadians(player.angle));
        camera3D.renderScene(core,map, player);

        core.restore();
    }

    public String toString() {
        /* implement way to print map to console using ascii white and black blocks or just 1/0. */

        StringBuilder out = new StringBuilder();
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                if (map.getTile(x, y).haveCollision()) {
                    out.append("▓");
                } else {
                    out.append("░");
                }
            }
            out.append("\n");
        }
        return out.toString();
    }



    public static double boundAngle(double a) {
        if (a < 0) {
            return a+2*Math.PI;
        }
        if (a > 2*Math.PI) {
            return a-2*Math.PI;
        }
        return a;
    }

    public static double constrain (double n, double min, double max) {
        if (n < min) return min;
        if (n > max) return max;
        return n;
    }

}
