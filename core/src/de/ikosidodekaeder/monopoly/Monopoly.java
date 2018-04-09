package de.ikosidodekaeder.monopoly;

import com.badlogic.gdx.Game;

import de.ikosidodekaeder.logic.Board;
import de.ikosidodekaeder.monopoly.graphics.util.FontManager;
import de.ikosidodekaeder.monopoly.graphics.util.MenuUtil;
import de.ikosidodekaeder.monopoly.screens.ScreenGame;
import de.ikosidodekaeder.monopoly.screens.ScreenMenu;

public class Monopoly extends Game {

    public static Monopoly instance;

	public ScreenGame screenGame;
    public ScreenMenu screenMenu;

    public Monopoly() {
        instance = this;
    }

    @Override
	public void create() {

        FontManager.init();
        new MenuUtil();

        screenMenu = new ScreenMenu();
        screenGame = new ScreenGame();

        screenMenu.create();
        screenGame.create();

        this.setScreen(screenGame);


	}

	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {

	}
}
