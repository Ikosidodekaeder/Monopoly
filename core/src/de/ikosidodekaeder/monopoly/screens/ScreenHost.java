package de.ikosidodekaeder.monopoly.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import de.ikosidodekaeder.monopoly.Monopoly;
import de.ikosidodekaeder.monopoly.graphics.elements.UILabel;
import de.ikosidodekaeder.monopoly.graphics.elements.UiButton;
import de.ikosidodekaeder.monopoly.graphics.util.MenuUtil;

/**
 * Created by Sven on 09.04.2018.
 */

public class ScreenHost extends MonopolyScreen {

    ScreenMenu screenMenu;

    public ScreenHost(ScreenMenu screenMenu) {
        this.screenMenu = screenMenu;
    }

    @Override
    public void create() {
        UILabel title = new UILabel(MenuUtil.getInstance().getX(), MenuUtil.getInstance().getY() + 600, 0, 0, 42, "Monopoly - Host");
        addElement(title);

        UiButton buttonStart = new UiButton("Start Game", MenuUtil.getInstance().getX(), MenuUtil.getInstance().getY() + 400, 0, 0, 32);
        UiButton buttonBack = new UiButton("Back", MenuUtil.getInstance().getX() - 100, MenuUtil.getInstance().getY() + 600, 0, 0, 32);
        buttonBack.getTextButton().getStyle().fontColor = Color.RED;
        addElement(buttonStart);
        addElement(buttonBack);


        buttonStart.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Monopoly.instance.setScreen(Monopoly.instance.screenGame);
            }
        });

        buttonBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Monopoly.instance.setScreen(screenMenu);
            }
        });
    }

    @Override
    public void show() {
        screenMenu.background.addToStage(stage);
        screenMenu.background.getImage().setZIndex(0);
        super.show();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        Monopoly.instance.server.callEvents();
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
