package de.ikosidodekaeder.monopoly.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;

import de.ikosidodekaeder.monopoly.graphics.board.RenderBoard;

/**
 * Created by Sven on 09.04.2018.
 */

public class ScreenGame extends MonopolyScreen {

    OrthographicCamera camera;

    RenderBoard renderBoard;

    @Override
    public void create() {
        camera = new OrthographicCamera();


    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
