package de.ikosidodekaeder.monopoly.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.List;

import de.ikosidodekaeder.logic.PlayerFigure;
import de.ikosidodekaeder.monopoly.Monopoly;
import de.ikosidodekaeder.monopoly.graphics.ClickEvent;
import de.ikosidodekaeder.monopoly.graphics.board.RenderBoard;
import de.ikosidodekaeder.monopoly.graphics.board.RenderTile;
import de.ikosidodekaeder.monopoly.graphics.elements.UiButton;
import de.ikosidodekaeder.monopoly.graphics.elements.UILabel;
import de.ikosidodekaeder.monopoly.graphics.elements.UiCard;
import de.ikosidodekaeder.monopoly.graphics.elements.UiElement;
import de.ikosidodekaeder.monopoly.graphics.elements.UiImage;
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

    public RenderTile selectedTile;

    List<UiElement> selectionElements = new ArrayList<>();

    @Override
    public void create() {
        monopolyCamera = new MonopolyCamera(new OrthographicCamera());

        renderBoard = new RenderBoard(0, 0, 0, 0);
        renderBoard.createBoard(this);

        renderBoard.addToStage(this.stage);
        //renderBoard.show(this.stage);

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

        multiplexer.addProcessor(keyListener);
        multiplexer.addProcessor(stage);

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
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            displaySelections();
        }

        monopolyCamera.update();
        stage.act(Gdx.graphics.getDeltaTime());

        Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.draw();
        this.uiStage.draw();

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

    public void displaySelections() {
        for (UiElement image : selectionElements) {
            image.removeFromStage(stage);
        }
        selectionElements.clear();

        float x = 0;

        // DICE
        UiImage imageDice = new UiImage(x, 0, 0, 0, "board/dice.png");
        imageDice.addClickEvent(new ClickEvent() {
            @Override
            public void click() {
                System.out.println("a");
            }
        });
        imageDice.addToStage(uiStage);
        x += imageDice.getWidth();

        // Straße kaufen
        //if (selectedTile != null) {
            //UiImage imageBuyStreet = new UiImage(imageDice.getWidth(), 0, 0, 0, "board/empty.png");
            UiCard cardBuy = new UiCard(x, 0, 0, 0, "Straße\nkaufen", 32);
            cardBuy.addToStage(uiStage);

            cardBuy.addClickEvent(new ClickEvent() {

                @Override
                public void click() {
                    System.out.println("b");
                }
            });

            selectionElements.add(cardBuy);

            x += cardBuy.getWidth();
        //}

        selectionElements.add(imageDice);


    }

}
