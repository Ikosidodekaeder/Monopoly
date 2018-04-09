package de.ikosidodekaeder.monopoly.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import de.ikosidodekaeder.monopoly.graphics.elements.UILabel;
import de.ikosidodekaeder.monopoly.graphics.util.MenuUtil;

/**
 * Created by Sven on 09.04.2018.
 */

public class ScreenMenu extends MonopolyScreen {

    public ScreenMenu() {

    }

    @Override
    public void create() {
        UILabel title = new UILabel(MenuUtil.getInstance().getX(), MenuUtil.getInstance().getY(), 0, 0, 32, "Monopoly");
        addElement(title);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
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
