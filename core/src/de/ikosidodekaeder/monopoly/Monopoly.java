package de.ikosidodekaeder.monopoly;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.ikosidodekaeder.monopoly.graphics.util.FontManager;
import de.ikosidodekaeder.monopoly.graphics.util.MenuUtil;
import de.ikosidodekaeder.monopoly.screens.ScreenGame;
import de.ikosidodekaeder.monopoly.screens.ScreenMenu;

public class Monopoly extends Game {

	ScreenGame screenGame;
    ScreenMenu screenMenu;

	@Override
	public void create() {
		//batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");

        FontManager.init();
        new MenuUtil();

        screenMenu = new ScreenMenu();
        screenGame = new ScreenGame();

        screenMenu.create();
        screenGame.create();

        this.setScreen(screenMenu);

		Board b = new Board("Map.txt");

		System.out.println(b);
	}

	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose () {

	}
}
