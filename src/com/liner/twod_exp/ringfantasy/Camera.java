package com.liner.twod_exp.ringfantasy;

import com.liner.twod_exp.engine.core.ECore;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.liner.twod_exp.ringfantasy.RingRender.viewPortSize;

public class Camera {
    private int mapWidth;
    private int mapHeight;
    private int[][] mapData;
    private int[][] cameraData;

    public Camera(Map map, Player player) {
        this.mapData = map.getMapData();
        this.cameraData = new int[viewPortSize][viewPortSize];
        this.mapWidth = map.getMapWidth();
        this.mapHeight = map.getMapHeight();
        int leftBound = Math.min(mapWidth - viewPortSize, Math.max(0, Math.min(player.x - viewPortSize / 2, player.x)));
        int topBound = Math.min(mapHeight - viewPortSize, Math.max(0, Math.min(player.y - viewPortSize / 2, player.y)));
        for (int y = 0; y < viewPortSize; y++) {
            System.arraycopy(mapData[topBound + y], leftBound, cameraData[y], 0, viewPortSize);
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
        core.draw(player.playerImages[player.direction], (player.x - leftBound) * RingRender.tileSize, (player.y - topBound) * RingRender.tileSize);
    }
}
