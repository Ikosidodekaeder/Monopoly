package de.ikosidodekaeder.network;

/**
 * Created by z003pksw on 09.04.2018.
 */

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Hashtable;

import de.ikosidodekaeder.monopoly.Monopoly;
import de.ikosidodekaeder.monopoly.graphics.elements.UiButton;
import de.ikosidodekaeder.monopoly.graphics.util.MenuUtil;
import de.ikosidodekaeder.monopoly.screens.ScreenJoin;
import de.ikosidodekaeder.network.Packets.Packet;
import de.ikosidodekaeder.network.Packets.PacketJoin;
import de.ikosidodekaeder.network.Packets.PacketKeepAlive;
import de.ikosidodekaeder.network.Packets.PacketLeave;
import de.ikosidodekaeder.network.Packets.PacketPlayerStatus;
import de.ikosidodekaeder.network.Packets.PacketRegister;
import de.ikosidodekaeder.network.Packets.PacketServerList;
import de.ikosidodekaeder.network.Packets.PacketType;
import de.ikosidodekaeder.util.ConsoleColours;
import de.ikosidodekaeder.util.Delegate;

/**
 * Created by Sven on 26.02.2018.
 */

public class ServerListener extends PacketListener {

    public long keepAliveSent = 0;

    public ServerListener(HexaServer server) {
        super(server);
    }


    @Override
    public void registerAll() {
        dispatchTable = new Hashtable<PacketType, Delegate>() {{
            put(PacketType.KEEPALIVE, new Delegate() {
                @Override
                public void invoke(Object... args) throws Exception {
                    long diff = System.currentTimeMillis() - server.lastKeepAliveSent;
                    ConsoleColours.Print(ConsoleColours.WHITE_BOLD + ConsoleColours.PURPLE_BACKGROUND,"Received Keep Alive (" + diff + " ms)");

                    PacketKeepAlive packet = (PacketKeepAlive) args[0];
                    //server.send(new PacketKeepAlive(1));
                    System.out.println("KEEPALIVE");
                }
            });

            put(PacketType.REGISTER, new Delegate() {
                @Override
                public void invoke(Object... args) throws Exception {
                    PacketRegister packet = (PacketRegister) args[0];
                    ConsoleColours.Print(ConsoleColours.WHITE_BOLD + ConsoleColours.PURPLE_BACKGROUND,"==== RECEIVED REGISTER ====");
                    System.out.println(ConsoleColours.BLACK_BOLD + "==== RECEIVED REGISTER ==== " + packet.isCancelled() + ConsoleColours.RESET);
                    if (Monopoly.instance.getScreen().equals(Monopoly.instance.screenMenu)) {
                        Monopoly.instance.setScreen(Monopoly.instance.screenHost);
                    }
                }
            });

            put(PacketType.JOIN, new Delegate() {
                @Override
                public void invoke(Object... args) throws Exception {
                    ConsoleColours.Print(ConsoleColours.WHITE_BOLD+ConsoleColours.PURPLE_BACKGROUND,"Received JOIN" + HexaServer.WhatAmI(server));
                    PacketJoin packet = (PacketJoin) args[0];

                    /*server.getSessionData().addNewPlayer(packet.getSenderId(), packet.getUsername(),
                            GameManager.instance.colorUtil.getNext());
                    System.out.println(packet.getUsername() + " has joined the game (I AM THE SERVER)");
                    */

                    // I'm the host, so I have to broadcast to my players that a new player has joined the game
                    server.send(new PacketJoin(packet.getUsername(), packet.getSenderId(), packet.getVersion()));
                }
            });

            put(PacketType.LEAVE, new Delegate() {
                @Override
                public void invoke(Object... args) throws Exception {
                    ConsoleColours.Print(ConsoleColours.WHITE_BOLD+ConsoleColours.PURPLE_BACKGROUND,"Received LEAVE" + HexaServer.WhatAmI(server));
                    PacketLeave leave = (PacketLeave) args[0];

                    // Confirm the Leave Packet by sending it to the router
                    // (The router sends it to all clients)
                    server.send(new PacketLeave(leave.getLeaverUuid(), leave.isKick()));

                    //server.getSessionData().removePlayer(leave.getSenderId());
                }
            });


            put(PacketType.SERVER_LIST, new Delegate() {
                @Override
                public void invoke(Object... args) throws Exception {
                    PacketServerList packetServerList = (PacketServerList) args[0];
                    ConsoleColours.Print(ConsoleColours.WHITE_BOLD+ConsoleColours.PURPLE_BACKGROUND,"Received SERVER_LIST " + packetServerList.entries.size() + HexaServer.WhatAmI(server));
                    /*if (ScreenManager.getInstance().getCurrentScreen().getScreenType() == ScreenType.JOIN) {
                        final ScreenJoin screenJoin = (ScreenJoin) ScreenManager.getInstance().getCurrentScreen();

                        screenJoin.subwindowServers.removeButtons(screenJoin.getStage());

                        for (final PacketServerList.Entry entry : packetServerList.entries) {
                            screenJoin.subwindowServers.add(new UiButton(entry.room,
                                    30, 0, 100, 40,
                                    screenJoin.getStage(),
                                    new ChangeListener() {
                                        @Override
                                        public void changed(ChangeEvent event, Actor actor) {
                                            System.out.println("Clicked room " + entry.room);
                                            screenJoin.joinRoom(entry.host, entry.room);
                                        }
                                    }), screenJoin.getStage());
                        }

                        screenJoin.subwindowServers.orderAllNeatly(1);
                        screenJoin.subwindowServers.updateElements();
                    }*/
                }
            });


            put(PacketType.PLAYER_STATUS, new Delegate() {


                @Override
                public void invoke(Object... args) throws Exception {
                    ConsoleColours.Print(ConsoleColours.WHITE_BOLD+ConsoleColours.PURPLE_BACKGROUND,"Received PLAYER_STATUS" + HexaServer.WhatAmI(server));
                    PacketPlayerStatus player = (PacketPlayerStatus)args[0];

                    server.send(new PacketPlayerStatus(
                            HexaServer.senderId,player.PlayerID,player.Stats
                    ));
                    //GameManager.instance.setPlayerResources(player.Stats);

                    /*if(server.isHost() && player.PlayerID.equals( HexaServer.senderId))
                        ;GameManager.instance.setPlayerResources(player.Stats);
                    else
                        server.send(player);*/

                }
            });
        }};
    }
}