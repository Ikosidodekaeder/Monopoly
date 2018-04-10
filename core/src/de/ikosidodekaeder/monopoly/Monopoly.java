package de.ikosidodekaeder.monopoly;

import com.badlogic.gdx.Game;

import java.io.IOException;

import de.ikosidodekaeder.logic.Board;
import de.ikosidodekaeder.monopoly.graphics.util.FontManager;
import de.ikosidodekaeder.monopoly.graphics.util.MenuUtil;
import de.ikosidodekaeder.monopoly.screens.ScreenGame;
import de.ikosidodekaeder.monopoly.screens.ScreenMenu;
import de.ikosidodekaeder.network.HexaServer;
import de.ikosidodekaeder.network.Packets.PacketJoin;
import de.ikosidodekaeder.network.Packets.PacketRegister;
import de.ikosidodekaeder.network.Packets.PacketServerList;

public class Monopoly extends Game {

    public static Monopoly instance;
    HexaServer Server;

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

        Server = new HexaServer(
                "svdragster.dtdns.net",
                25565,
                true
        );

        try {
            Server.connect(10_000);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Server.broadcastKeepAlive();
        Server.send(new PacketRegister("Room 1",false));
        Server.send(new PacketJoin("Test",HexaServer.senderId,"1"));


	}

	@Override
	public void render() {
        //Because i can
        //Server.broadcastKeepAlive();
        Server.send(new PacketServerList(HexaServer.senderId));
		super.render();
	}
	
	@Override
	public void dispose() {

	}
}
