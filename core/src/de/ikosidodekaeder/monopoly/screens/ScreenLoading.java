package de.ikosidodekaeder.monopoly.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import de.ikosidodekaeder.monopoly.Monopoly;
import de.ikosidodekaeder.monopoly.graphics.elements.UILabel;
import de.ikosidodekaeder.monopoly.graphics.elements.UiButton;
import de.ikosidodekaeder.monopoly.graphics.elements.UiImage;
import de.ikosidodekaeder.monopoly.graphics.util.MenuUtil;
import de.ikosidodekaeder.network.HexaServer;
import de.ikosidodekaeder.network.Packets.PacketRegister;

/**
 * Created by Sven on 09.04.2018.
 */

public class ScreenLoading extends MonopolyScreen {

    private String[] loadingStrings = {
            "Loading.",
            "Loading..",
            "Loading...",
            "Loading....",
            "Loading.....",
            "Loading......",
    };
    private float loadingCounter = 0;

    UILabel waitingLabel;

    public UiImage background;

    public Callback updateCallback;

    public ScreenLoading() {

    }

    @Override
    public void create() {
        background = new UiImage(0, 0, 0, 0, "menu/city.png");
        background.setDisplayX(Gdx.graphics.getWidth()/2 - background.getWidth()/2);
        background.setDisplayY(Gdx.graphics.getHeight()/2 - background.getHeight()/2);

        background.addToStage(stage);

        UILabel title = new UILabel(MenuUtil.getInstance().getX(), MenuUtil.getInstance().getY() + 600, 0, 0, 42, "Monopoly");
        addElement(title);

        waitingLabel = new UILabel(MenuUtil.getInstance().getX(), MenuUtil.getInstance().getY(), 0, 0, 62, loadingStrings[0]);
        waitingLabel.getLabel().getStyle().fontColor = Color.SKY;
        addElement(waitingLabel);
    }

    @Override
    public void show() {
        background.addToStage(stage);
        background.getImage().setZIndex(0);
        super.show();
    }

    @Override
    public void render(float delta) {
        waitingLabel.getLabel().setText(loadingStrings[(int) loadingCounter]);
        loadingCounter += 1;
        if (loadingCounter >= loadingStrings.length) {
            loadingCounter = 0;
        }

        Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();

        if (updateCallback != null)
            updateCallback.update();
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
        updateCallback = null;
    }

    @Override
    public void dispose() {

    }

    public abstract static class Callback {

        public abstract void update();

    }
}
