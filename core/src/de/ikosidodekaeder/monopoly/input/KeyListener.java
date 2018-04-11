package de.ikosidodekaeder.monopoly.input;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import de.ikosidodekaeder.monopoly.Monopoly;

/**
 * Created by Sven on 09.04.2018.
 *
 * 374 8:30 EH Welcome
 */

public class KeyListener implements InputProcessor {

    public static final float SPEED = 5.0f;

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.A) {
            Monopoly.instance.screenGame.monopolyCamera.velX = -SPEED;
        } else if (keycode == Input.Keys.D) {
            Monopoly.instance.screenGame.monopolyCamera.velX = SPEED;
        }
        if (keycode == Input.Keys.W) {
            Monopoly.instance.screenGame.monopolyCamera.velY = SPEED;
        } else if (keycode == Input.Keys.S) {
            Monopoly.instance.screenGame.monopolyCamera.velY = -SPEED;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Monopoly.instance.screenGame.monopolyCamera.downX = screenX;
        Monopoly.instance.screenGame.monopolyCamera.downY = screenY;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        float zoom = Monopoly.instance.screenGame.monopolyCamera.camera.zoom * 0.5f;
        if (zoom > 1.0f) {
            zoom = 1;
        }
        Monopoly.instance.screenGame.monopolyCamera.velX = (Monopoly.instance.screenGame.monopolyCamera.downX - screenX) * zoom;
        Monopoly.instance.screenGame.monopolyCamera.velY = -(Monopoly.instance.screenGame.monopolyCamera.downY - screenY) * zoom;
        Monopoly.instance.screenGame.monopolyCamera.downX = screenX;
        Monopoly.instance.screenGame.monopolyCamera.downY = screenY;
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        Monopoly.instance.screenGame.monopolyCamera.velZ += amount*0.05f;
        return true;
    }
}
