package com.liner.twod_exp.ringfantasy;

import com.liner.twod_exp.engine.math.Node;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.liner.twod_exp.ringfantasy.RingRender.tileSize;

public class Map {
    public double cellSize = 16;
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
        return new Tile(x, y, mapData);
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
            case 0 -> data = mapData[player.y - 1][player.x];
            case 1 -> data = mapData[player.y][player.x - 1];
            case 2 -> data = mapData[player.y][player.x + 1];
            case 3 -> data = mapData[player.y + 1][player.x];
        }
        return data;
    }

    private int toInt(String value) {
        return Integer.parseInt(value);
    }

    public String getMapName() {
        return mapName;
    }


    public List<Tile> getAllTiles() {
        List<Tile> tileList = new ArrayList<>();
        new Looper(mapData) {
            @Override
            public void loop(int x, int y, int data) {
                Tile tile = new Tile(x, y, data);
                if (tile.haveCollision())
                    tileList.add(tile);
            }
        };
        return tileList;
    }

    public List<Node> getAllNodes() {
        List<Node> nodes = new ArrayList<>();
        for (Tile tile : getAllTiles()) {
            nodes.add(tile.getNodes()[0]);
            nodes.add(tile.getNodes()[1]);
            nodes.add(tile.getNodes()[2]);
            nodes.add(tile.getNodes()[3]);
        }
        return nodes;
    }

    public List<Node> getAllFloorNodes() {
        List<Node> nodes = new ArrayList<>();
        new Looper(mapData) {
            @Override
            public void loop(int x, int y, int data) {
                Tile tile = new Tile(x, y, data);
                if (tile.getGround() != null) {
                    nodes.add(tile.getNodes()[0]);
                    nodes.add(tile.getNodes()[1]);
                    nodes.add(tile.getNodes()[2]);
                    nodes.add(tile.getNodes()[3]);

                }
            }
        };
        return nodes;
    }

    public Tile getAssociatedTile(Node node) {
        for (Tile tile : getAllTiles()) {
            for (Node n : tile.getNodes()) {
                if (n.equals(node))
                    return tile;
            }
        }
        return null;
    }
}
