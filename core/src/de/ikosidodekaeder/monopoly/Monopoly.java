package de.ikosidodekaeder.monopoly;

import com.badlogic.gdx.Game;

import java.io.IOException;

import de.ikosidodekaeder.monopoly.graphics.util.FontManager;
import de.ikosidodekaeder.monopoly.graphics.util.MenuUtil;
import de.ikosidodekaeder.monopoly.screens.MonopolyScreen;
import de.ikosidodekaeder.monopoly.screens.ScreenGame;
import de.ikosidodekaeder.monopoly.screens.ScreenHost;
import de.ikosidodekaeder.monopoly.screens.ScreenJoin;
import de.ikosidodekaeder.monopoly.screens.ScreenLoading;
import de.ikosidodekaeder.monopoly.screens.ScreenLobby;
import de.ikosidodekaeder.monopoly.screens.ScreenMenu;
import de.ikosidodekaeder.network.HexaServer;
import de.ikosidodekaeder.network.Packets.PacketJoin;
import de.ikosidodekaeder.network.Packets.PacketRegister;

public class Monopoly extends Game {

    public static Monopoly instance;
    public HexaServer server;

	public ScreenGame  screenGame;
    public ScreenMenu  screenMenu;
    public ScreenHost  screenHost;
    public ScreenJoin  screenJoin;
    public ScreenLobby screenLobby;

    public ScreenLoading    screenLoading;

    public Monopoly() {
        instance = this;
    }

    @Override
	public void create() {

        FontManager.init();
        new MenuUtil();

        screenLoading = new ScreenLoading();
        screenLoading.create();

        screenMenu  = new ScreenMenu();
        screenHost  = new ScreenHost(screenMenu);
        screenJoin  = new ScreenJoin(screenMenu);
        screenLobby = new ScreenLobby(screenMenu);
        screenGame  = new ScreenGame();

        screenLoading.updateCallback = new ScreenLoading.Callback() {
            private int i = 0;
            private MonopolyScreen[] toLoad = new MonopolyScreen[]{screenMenu, screenHost, screenJoin, screenLobby, screenGame};
            @Override
            public void update() {
                toLoad[i].create();
                i++;
                if (i >= toLoad.length) {
                    setScreen(screenMenu);
                }
            }
        };


        this.setScreen(screenLoading);

	}

	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {

	}

	public void connect(boolean isHost) {
        server = new HexaServer(
                "svdragster.dtdns.net",
                //"localhost",
                25565,
                isHost
        );

        try {
            server.connect(10_000);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (isHost) {
            server.send(new PacketRegister("Room 1", false));
        }
    }
}
