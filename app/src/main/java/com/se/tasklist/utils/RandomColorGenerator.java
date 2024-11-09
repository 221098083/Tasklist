package com.se.tasklist.utils;

import android.graphics.Color;

public class RandomColorGenerator{
    public RandomColorGenerator(){

    }

    public int generateRandomColor() {
        int alpha = 255;
        int red = (int) (Math.random() * 255);
        int green = (int) (Math.random() * 255);
        int blue = (int) (Math.random() * 255);

        return Color.argb(alpha, red, green, blue);
    }
}

