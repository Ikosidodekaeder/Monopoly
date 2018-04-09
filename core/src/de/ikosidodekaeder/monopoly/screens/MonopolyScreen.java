package de.ikosidodekaeder.monopoly.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;

import de.ikosidodekaeder.monopoly.graphics.elements.ElementContainer;
import de.ikosidodekaeder.monopoly.graphics.elements.UiElement;

/**
 * Created by Sven on 09.04.2018.
 */

public abstract class MonopolyScreen implements Screen {

    ElementContainer elements = new ElementContainer(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    Stage stage = new Stage();

    public void addElement(UiElement element) {
        element.addToStage(stage);
        elements.children.add(element);
    }

    public abstract void create();

    @Override
    public void show() {
        elements.show(stage);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
}
