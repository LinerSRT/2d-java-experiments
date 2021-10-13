package com.liner.twod_exp.engine.core;

public class Memory {
    public static long used() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    public static String usedString() {
        return format(used());
    }

    public static long free() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.freeMemory();
    }

    public static String freeString() {
        return format(free());
    }

    public static long total() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory();
    }

    public static String totalString() {
        return format(total());
    }

    public static String usedTotalString() {
        return "Used: " + usedString()
                + "   Total: " + totalString();
    }

    public static String allString() {
        return "Usage: " + usedString() + "/" + totalString();
    }

    public static final double KB = 1024;
    public static final double MB = 1048576;
    public static final double GB = 1073741824;

    public static String format(long mem) {
        if (mem < 2 * KB)
            return mem + " bytes";
        if (mem < 2 * MB)
            return round(mem / KB) + " KB";
        if (mem < 2 * GB)
            return round(mem / MB) + " MB";
        return round(mem / GB) + " GB";
    }

    public static double round(double d) {
        return Math.ceil(d * 100) / 100;
    }
}
