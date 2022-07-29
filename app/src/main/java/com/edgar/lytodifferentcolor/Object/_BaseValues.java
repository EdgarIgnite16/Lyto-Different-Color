package com.edgar.lytodifferentcolor.Object;

public class _BaseValues {
    // Custom Values GridView Which is container gameplay
    public static int numCol;
    public static int numRow;
    public static int totalPointGridView;

    // Base color point
    public final static String[] originalColor = new String[] {
            "#F3A92F",
            "#FF0000",
            "#FFFF00",
            "#3ADF00",
            "#0174DF",
            "#0101DF",
            "#A901DE",
            "#FF0040",
    };
    public final static String[] differentColor = new String[] {
            "#ECB459",
            "#FA5858",
            "#F3F781",
            "#ACFA58",
            "#2ECCFA",
            "#5858FA",
            "#D0A9F5",
            "#F7819F",
    };
    public final static int sizeOfListColor =  originalColor.length;

    // Thông số level màn chơi
    public static int level;
    public static int second;
    public static boolean isTimeout;
    public static int endgameScore;
}
