package com.liner.twod_exp.engine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Resources {
    public static Resources resources;
    public static final File resourceDirectory = new File(System.getProperty("user.dir"), "res/");
    public static Resources get() {
        if (resources == null)
            return resources = new Resources();
        return resources;
    }
    private Resources() {
    }
    public BufferedImage image(String name) {
        try {
            return ImageIO.read(new File(resourceDirectory, name));
        } catch (IOException e) {
            e.printStackTrace();
            return new BufferedImage(10, 10, 10);
        }
    }

    public String text(String name) {
        try {
            StringBuilder stringBuffer = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(new File(resourceDirectory, name).getAbsolutePath()));
            char[] buffer = new char[1024];
            int read;
            while ((read = reader.read(buffer)) != -1)
                stringBuffer.append(String.valueOf(buffer, 0, read));
            reader.close();
            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "Cannot read file!";
        }
    }

    public List<String> textLines(String string) {
        try {
            final BufferedReader stringReader = new BufferedReader(new StringReader(string));
            final List<String> list = new ArrayList<>();
            String line = stringReader.readLine();
            while (line != null) {
                list.add(line);
                line = stringReader.readLine();
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
