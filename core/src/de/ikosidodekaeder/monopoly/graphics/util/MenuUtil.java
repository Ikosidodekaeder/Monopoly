package de.ikosidodekaeder.monopoly.graphics.util;

import com.badlogic.gdx.Gdx;

/**
 * Created by Sven on 20.12.2017.
 */

public class MenuUtil {

    private static MenuUtil instance;

    private float sizeX = 800;
    private float sizeY = 600;

    private float x;
    private float y;

    public MenuUtil() {
        instance = this;

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        x = width/2  - sizeX/2;
        y = height/2 - sizeY/2;
    }

    public float getSizeX() {
        return sizeX;
    }

    public float getSizeY() {
        return sizeY;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public static MenuUtil getInstance() {
        return instance;
    }
}
