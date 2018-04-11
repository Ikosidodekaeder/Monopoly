package de.ikosidodekaeder.monopoly.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import de.ikosidodekaeder.logic.PlayerFigure;
import de.ikosidodekaeder.logic.interfaces.Player;
import de.ikosidodekaeder.monopoly.graphics.board.RenderBoard;
import de.ikosidodekaeder.monopoly.graphics.elements.UiButton;
import de.ikosidodekaeder.monopoly.graphics.elements.UILabel;
import de.ikosidodekaeder.monopoly.input.KeyListener;
import de.ikosidodekaeder.monopoly.input.MonopolyCamera;
import de.ikosidodekaeder.util.Pair;

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
        //Gdx.input.setInputProcessor(uiStage);

        final UiButton Wuerfel = new UiButton(
                "Würfel",
                Gdx.graphics.getWidth()-200,
                Gdx.graphics.getHeight()-50,
                200,
                50,
                30
        );

        final UiButton BeendeRunde = new UiButton(
                "BeendeRunde",
                Gdx.graphics.getWidth()-200,
                Gdx.graphics.getHeight()-100,
                200,
                50,
                30
        );

        final UiButton KaufeStrasse = new UiButton(
                "Kaufe Strasse",
                Gdx.graphics.getWidth()-200,
                Gdx.graphics.getHeight()-150,
                200,
                50,
                30
        );
        final UiButton KaufeHaus = new UiButton(
                "Kaufe Haus",
                Gdx.graphics.getWidth()-200,
                Gdx.graphics.getHeight()-200,
                200,
                50,
                30
        );


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

        BeendeRunde.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                for(Player p :   renderBoard.board.Players){
                    if(p.PlayerID() == renderBoard.board.getClientFigure()){
                        p.endTurn();
                        break;
                    }
                }
            }
        });


        KaufeStrasse.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                for(Player p :   renderBoard.board.Players){
                    if(p.PlayerID() == renderBoard.board.getClientFigure()){
                        if(! p.finishedTurn())
                        {
                            p.buyProperty(renderBoard.board.actualBoard.get(p.getPosition()));
                        }
                        break;
                    }
                }
            }
        });


        addGuiElement(Wuerfel);
        addGuiElement(BeendeRunde);
        addGuiElement(KaufeHaus);
        addGuiElement(KaufeStrasse);
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
