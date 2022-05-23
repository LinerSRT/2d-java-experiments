package com.liner.twod_exp.ringfantasy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Resource {
    private static final int tileSize = 16;
    private static final File resourceDir = new File(System.getProperty("user.dir"), "resources");
    private static int tilesCount = 54;
    private static int objectsCount = 54;
    private static int monsterCount = 16;
    public static BufferedImage[] tilesImages;
    public static BufferedImage[] objectImages;
    public static BufferedImage[] monsterImages;
    public static List<Event> eventList;
    public static int[] weaponAgility;
    public static int[] armorAgility;
    public static Monster[] monsters;
    public static boolean reloadingResources = false;


    static {
        reloadResources();
    }

    public static void reloadResources(){
        reloadingResources = true;
        weaponAgility = new int[]{0, 2, 5, 8, 12, 16, 20, 24, 28, 32};
        armorAgility = new int[]{0, 2, 5, 8, 12, 16, 20, 24, 28, 32};
        tilesImages = new BufferedImage[tilesCount];
        objectImages = new BufferedImage[objectsCount];
        monsterImages = new BufferedImage[monsterCount];
        monsters = new Monster[15];
        monsters[0] = new Monster("Bat", 12, 8, 8, 100, 100, 20, 50, 50);
        monsters[1] = new Monster("Slime", 16, 12, 12, 80, 120, 20, 50, 50);
        monsters[2] = new Monster("Scorpion", 20, 16, 16, 80, 120, 20, 100, 50);
        monsters[3] = new Monster("Wolf", 30, 20, 20, 100, 100, 20, 100, 50);
        monsters[4] = new Monster("Skeleton", 40, 24, 24, 100, 100, 20, 100, 100);
        monsters[5] = new Monster("Fire Fox", 50, 28, 28, 120, 80, 40, 200, 200);
        monsters[6] = new Monster("Orc", 60, 32, 32, 100, 100, 20, 200, 100);
        monsters[7] = new Monster("Ghost", 70, 36, 36, 100, 100, 20, 300, 100);
        monsters[8] = new Monster("Death", 80, 40, 40, 120, 80, 20, 400, 100);
        monsters[9] = new Monster("Hydra", 90, 44, 44, 80, 120, 40, 500, 500);
        monsters[10] = new Monster("Green Dragon", 100, 48, 48, 80, 120, 20, 1000, 1000);
        monsters[11] = new Monster("Fire Dragon", 120, 56, 48, 120, 80, 20, 2000, 2000);
        monsters[12] = new Monster("Earth Dragon", 140, 56, 56, 100, 100, 20, 3000, 3000);
        monsters[13] = new Monster("Robot", 160, 56, 56, 80, 80, 40, 5000, 5000);
        monsters[14] = new Monster("Dark King", 240, 64, 64, 100, 100, 40, 0, 0);


        eventList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            int tileCount = i == 5 ? 3 : 10;
            for (int j = 0; j < tileCount; j++) {
                int tileIndex = (i * 10) + j;
                tilesImages[tileIndex] = getImage("tile" + i + ".png", j);
            }
        }
        for (int i = 1; i < objectsCount; i++) {
            objectImages[i] = getImage("obj" + i + ".png");
        }
        for (int i = 0; i < monsterImages.length; i++) {
            monsterImages[i] = getImage("mona.png", i);
        }
        for (String event : getText("event")) {
            if (event.trim().length() != 0 && !event.trim().startsWith("#")) eventList.add(new Event(event));
        }

        reloadingResources = false;
    }


    private static BufferedImage getImage(String name) {
        try {
            return ImageIO.read(new File(resourceDir, name));
        } catch (IOException e) {
            e.printStackTrace();
            BufferedImage bufferedImage = new BufferedImage(tileSize, tileSize, BufferedImage.TYPE_INT_ARGB);
            for (int x = 0; x < bufferedImage.getWidth(); x++) {
                for (int y = 0; y < bufferedImage.getHeight(); y++) {
                    bufferedImage.setRGB(x, y, Color.RED.getRGB());
                }
            }
            return bufferedImage;
        }
    }

    private static BufferedImage getImage(String name, int index) {
        BufferedImage bufferedImage = getImage(name);
        return bufferedImage.getSubimage(index * tileSize, 0, tileSize, tileSize);
    }

    public static List<String> getText(String name) {
        try {
            return Files.readAllLines(new File(resourceDir, name).toPath(), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    public static int getInt(String data, int index) {
        try {
            return Integer.parseInt(getString(data, index).trim());
        } catch (Exception e) {
            return -1;
        }
    }

    public static int getInt(String data, int index, char delimiter) {
        try {
            return Integer.parseInt(get(data, index, delimiter).trim());
        } catch (Exception e) {
            return -1;
        }
    }

    public static String getString(String data, int index) {
        return get(data, index, ',');
    }


    public static String get(String data, int index, char delimiter) {
        int startIndex = -1;
        int foundIndex = 1;
        while (true) {
            int endIndex = data.indexOf(delimiter, startIndex + 1);
            if (index == foundIndex) {
                if (endIndex != -1) {
                    return data.substring(startIndex + 1, endIndex);
                }
                return data.substring(startIndex + 1);
            } else if (endIndex == -1) {
                return "";
            } else {
                foundIndex++;
                startIndex = endIndex;
            }
        }
    }

    public static BufferedImage resize(BufferedImage bufferedImage, int width, int height) {
        if(bufferedImage == null)
            return new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = result.createGraphics();
        graphics2D.drawImage(bufferedImage.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING), 0, 0, null);
        graphics2D.dispose();
        return result;
    }
}
