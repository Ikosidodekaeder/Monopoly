package de.ikosidodekaeder.monopoly.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import de.ikosidodekaeder.monopoly.Monopoly;
import de.ikosidodekaeder.monopoly.graphics.elements.UILabel;
import de.ikosidodekaeder.monopoly.graphics.elements.UiButton;
import de.ikosidodekaeder.monopoly.graphics.elements.UiImage;
import de.ikosidodekaeder.monopoly.graphics.util.MenuUtil;

/**
 * Created by Sven on 09.04.2018.
 */

public class ScreenMenu extends MonopolyScreen {

    public UiImage background;

    public ScreenMenu() {

    }

    @Override
    public void create() {
        background = new UiImage(0, 0, 0, 0, "menu/city.png");
        background.setDisplayX(Gdx.graphics.getWidth()/2 - background.getWidth()/2);
        background.setDisplayY(Gdx.graphics.getHeight()/2 - background.getHeight()/2);

        background.addToStage(stage);
        
        UILabel title = new UILabel(MenuUtil.getInstance().getX(), MenuUtil.getInstance().getY() + 600, 0, 0, 42, "Monopoly");
        addElement(title);

        UiButton buttonHost = new UiButton("Host", MenuUtil.getInstance().getX(), MenuUtil.getInstance().getY() + 400, 0, 0, 32);
        UiButton buttonJoin = new UiButton("Join", MenuUtil.getInstance().getX(), MenuUtil.getInstance().getY() + 200, 0, 0, 32);
        addElement(buttonHost);
        addElement(buttonJoin);



        buttonHost.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Monopoly.instance.setScreen(Monopoly.instance.screenHost);
            }
        });

        buttonJoin.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Monopoly.instance.setScreen(Monopoly.instance.screenJoin);
            }
        });
    }

    @Override
    public void show() {
        background.addToStage(stage);
        background.getImage().setZIndex(0);
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
