package de.ikosidodekaeder.monopoly.graphics.elements;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by Sven on 19.12.2017.
 */

public abstract class UiElement {

    float x;
    float y;

    float width;
    float height;

    private UpdateEvent updateEvent;

    public UiElement(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(ShapeRenderer shapeRenderer) {

    }

    public abstract void addToStage(Stage stage);

    public abstract void removeFromStage(Stage stage);

    public abstract void show(Stage stage);

    public abstract void hide(Stage stage);

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void update() {
        if (updateEvent != null) {
            updateEvent.onUpdate();
        }
    }

    public void setUpdateEvent(UpdateEvent updateEvent) {
        this.updateEvent = updateEvent;
    }
}
