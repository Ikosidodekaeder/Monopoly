package de.ikosidodekaeder.monopoly.input;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import de.ikosidodekaeder.monopoly.Monopoly;

/**
 * Created by Sven on 10.04.2018.
 */

public class TouchListener implements GestureDetector.GestureListener {

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        float zoom = Monopoly.instance.screenGame.monopolyCamera.camera.zoom;
        if (zoom > 2.0f) {
            zoom = 2;
        }
        Monopoly.instance.screenGame.monopolyCamera.velX = -(deltaX) * zoom;
        Monopoly.instance.screenGame.monopolyCamera.velY = (deltaY) * zoom;
        return true;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    private float lastZoom = 0;

    @Override
    public boolean zoom(float initialDistance, float distance) {
        System.out.println("Zooooom " + initialDistance + "      " + distance);
        float diff;
        if (distance > lastZoom + 10) {
            diff = -0.02f;
        } else if (distance < lastZoom - 10){
            diff = 0.02f;
        } else {
            return false;
        }
        Monopoly.instance.screenGame.monopolyCamera.velZ += diff;
        lastZoom = distance;
        return true;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
