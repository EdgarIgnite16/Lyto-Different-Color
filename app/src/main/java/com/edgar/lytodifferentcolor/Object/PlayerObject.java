package com.edgar.lytodifferentcolor.Object;

import android.content.Context;
import android.content.SharedPreferences;

public class PlayerObject {
    private String name;
    private Context context;
    private String fileSave = "gameInfo";
    private int score = 100; // default điểm gốc của người chơi

    public PlayerObject(String name, Context context) {
        this.name = name;
        this.context = context;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    //===========================================================================================//
    public void setData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileSave, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("score", score);
        editor.apply();
    }

    public void getData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileSave, Context.MODE_PRIVATE);
        score = sharedPreferences.getInt("score", -1);
    }
}
