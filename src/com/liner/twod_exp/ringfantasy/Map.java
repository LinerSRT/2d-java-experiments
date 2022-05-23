package com.liner.twod_exp.ringfantasy;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private String mapName;
    private final int mapWidth;
    private final int mapHeight;
    private int[][] mapData;

    public Map(String name) {
        this.mapName = name;
        List<String> mapLines = Resource.getText(name);
        this.mapWidth = Integer.parseInt(mapLines.get(0).split(",")[0]);
        this.mapHeight = Integer.parseInt(mapLines.get(0).split(",")[1]);
        mapLines.remove(0);
        mapData = new int[mapHeight][mapWidth];
        for (int y = 0; y < mapLines.size(); y++) {
            String lineY = mapLines.get(y);
            String[] lineX = lineY.split(",");
            for (int x = 0; x < lineX.length; x++) {
                mapData[y][x] = toInt(lineX[x]);
            }
        }
    }

    private int getIndexFrom(int x, int y) {
        return x * y + x;
    }

    private int[] getPositionFrom(int index) {
        int x = index / mapWidth;
        int y = index % mapWidth;
        return new int[]{x, y};
    }


    public static Tile getTile(int mapData, int x, int y) {
        int tileID = mapData % 100;
        Tile tile = new Tile(Resource.tilesImages[tileID], x, y);
        int objectID = mapData % 10000 / 100;
        int eventID = mapData % 1000000 / 100000;

        if (objectID != 0) {
            if(eventID == 6 || eventID == 7){

                int monsterIndex = Resource.getInt(Resource.eventList.get(mapData / 10000).data, 1);
                tile.objectImage = Resource.monsterImages[monsterIndex];
            } else {
                tile.objectImage = Resource.objectImages[objectID];
            }
        }
        return tile;
    }

    public Tile getTile(int x, int y) {
        return getTile(mapData[y][x], x, y);
    }


    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }


    public int[][] getMapData() {
        return mapData;
    }


    public int getDataAt(int x, int y) {
        return mapData[y][x];
    }

    public void setDataAt(int data, int x, int y) {
        mapData[y][x] = data;
    }

    public void setData(int data, Player player) {
        switch (player.direction) {
            case 0 -> setDataAt(data, player.x, player.y - 1);
            case 1 -> setDataAt(data, player.x - 1, player.y);
            case 2 -> setDataAt(data, player.x + 1, player.y);
            case 3 -> setDataAt(data, player.x, player.y + 1);
        }
    }

    public int getData(Player player) {
        int data = 0;
        switch (player.direction) {
            case 0 -> data = mapData[player.y-1][player.x];
            case 1 -> data = mapData[player.y][player.x-1];
            case 2 -> data = mapData[player.y][player.x+1];
            case 3 -> data = mapData[player.y+1][player.x];
        }
        return data;
    }

    private int toInt(String value) {
        return Integer.parseInt(value);
    }

    public String getMapName() {
        return mapName;
    }
}
