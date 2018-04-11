package de.ikosidodekaeder.monopoly.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import de.ikosidodekaeder.logic.PlayerFigure;
import de.ikosidodekaeder.monopoly.graphics.board.RenderBoard;
import de.ikosidodekaeder.monopoly.graphics.elements.UiButton;
import de.ikosidodekaeder.monopoly.graphics.elements.UILabel;
import de.ikosidodekaeder.monopoly.input.KeyListener;
import de.ikosidodekaeder.monopoly.input.MonopolyCamera;
import de.ikosidodekaeder.monopoly.input.TouchListener;
import de.ikosidodekaeder.util.Pair;

/**
 * Created by Sven on 09.04.2018.
 */

public class ScreenGame extends MonopolyScreen {

    public MonopolyCamera   monopolyCamera;
    public InputProcessor   keyListener;

    RenderBoard renderBoard;

    @Override
    public void create() {
        monopolyCamera = new MonopolyCamera(new OrthographicCamera());

        renderBoard = new RenderBoard(0, 0, 0, 0);
        renderBoard.createBoard();

        renderBoard.addToStage(this.stage);
        renderBoard.show(this.stage);

        switch (Gdx.app.getType()) {
            case Android:
                keyListener = new GestureDetector(new TouchListener());
                break;
            default:
                keyListener = new KeyListener();
                break;
        }

        monopolyCamera.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        FitViewport viewp = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), monopolyCamera.camera); // change this to your needed viewport
        stage.setViewport(viewp);

        InputMultiplexer multiplexer = new InputMultiplexer(keyListener, stage, uiStage);
        Gdx.input.setInputProcessor(multiplexer);

        final UiButton Wuerfel = new UiButton(
                "Würfel",
                Gdx.graphics.getWidth()-200,
                Gdx.graphics.getHeight()-50,
                200,
                50,
                30);

        final UiButton BeendeRunde = new UiButton(
                "BeendeRunde",
                Gdx.graphics.getWidth()-200,
                Gdx.graphics.getHeight()-50,
                200,
                50,
                30);

        Wuerfel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PlayerFigure ClientPlayer = renderBoard.board.getClientFigure();

                Pair<Integer,Integer> res = renderBoard.board.ThrowDices();

                renderBoard.board.movePlayer(ClientPlayer,res.getFirst()+res.getSecond());
                System.out.println("Würfel -> " + res.getFirst() + " " + res.getSecond());
                Wuerfel.setText("Würfel -> " + res.getFirst() + " " + res.getSecond());

            }
        });


        addGuiElement(Wuerfel);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {

        monopolyCamera.update();
        stage.act(Gdx.graphics.getDeltaTime());

        Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.draw();
        this.uiStage.draw();
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
