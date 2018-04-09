package de.ikosidodekaeder.monopoly.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by Sven on 09.04.2018.
 */

public class MonopolyCamera {

    public OrthographicCamera camera;

    public float velX = 0, velY = 0;
    public float velZ = 0;

    public int             downX = 0;
    public int             downY = 0;

    public MonopolyCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    public void update() {
        if (velX == 0 && velY == 0 && velZ == 0) {
            return;
        }
        camera.position.x += velX;
        camera.position.y += velY;
        camera.zoom += velZ;
        if (camera.zoom < 0.5f) {
            camera.zoom = 0.5f;
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.W) && !Gdx.input.isKeyPressed(Input.Keys.S)) {
            velY *= 0.87;
            if (velY >= -0.01 && velY <= 0.01) {
                velY = 0;
            }
        }

        if (!Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D)) {
            velX *= 0.87;
            if (velX >= -0.01 && velX <= 0.01) {
                velX = 0;
            }
        }
        velZ *= 0.8f;
        if (velZ >= -0.01 && velZ <= 0.01) {
            velZ = 0;
        }
        camera.update();
    }



}
