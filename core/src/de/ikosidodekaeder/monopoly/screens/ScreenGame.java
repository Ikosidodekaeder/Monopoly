package de.ikosidodekaeder.monopoly.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import de.ikosidodekaeder.monopoly.graphics.board.RenderBoard;
import de.ikosidodekaeder.monopoly.input.KeyListener;
import de.ikosidodekaeder.monopoly.input.MonopolyCamera;

/**
 * Created by Sven on 09.04.2018.
 */

public class ScreenGame extends MonopolyScreen {

    public MonopolyCamera   monopolyCamera;
    public KeyListener      keyListener;

    RenderBoard renderBoard;

    @Override
    public void create() {
        monopolyCamera = new MonopolyCamera(new OrthographicCamera());

        renderBoard = new RenderBoard(0, 0, 0, 0);
        renderBoard.createBoard();

        renderBoard.addToStage(this.stage);
        renderBoard.show(this.stage);

        keyListener = new KeyListener();

        monopolyCamera.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        FitViewport viewp = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), monopolyCamera.camera); // change this to your needed viewport
        stage.setViewport(viewp);
        Gdx.input.setInputProcessor(keyListener);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {

        monopolyCamera.update();

        Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.draw();
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
