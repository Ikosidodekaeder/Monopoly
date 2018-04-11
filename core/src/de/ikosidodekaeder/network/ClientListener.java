package de.ikosidodekaeder.network;

import java.util.Hashtable;

import de.ikosidodekaeder.monopoly.Monopoly;
import de.ikosidodekaeder.network.HexaServer;
import de.ikosidodekaeder.network.PacketListener;
import de.ikosidodekaeder.network.Packets.PacketJoin;
import de.ikosidodekaeder.network.Packets.PacketKeepAlive;
import de.ikosidodekaeder.network.Packets.PacketLeave;
import de.ikosidodekaeder.network.Packets.PacketPlayerStatus;
import de.ikosidodekaeder.network.Packets.PacketRegister;
import de.ikosidodekaeder.network.Packets.PacketServerList;
import de.ikosidodekaeder.network.Packets.PacketType;
import de.ikosidodekaeder.util.ConsoleColours;
import de.ikosidodekaeder.util.Delegate;

public class ClientListener extends PacketListener {


    public ClientListener(HexaServer server) {
        super(server);
    }

    @Override
    public void registerAll() {
        dispatchTable = new Hashtable<PacketType, Delegate>() {{
            final Object lock = new Object();
            put(PacketType.KEEPALIVE, new Delegate() {
                @Override
                public void invoke(Object... args) throws Exception {
                    PacketKeepAlive packet = (PacketKeepAlive) args[0];
                    if (!server.isHost()) {
                        server.send(new PacketKeepAlive(packet.getSessionID()));
                        System.out.println("KEEPALIVE");
                    }
                }
            });

            put(PacketType.REGISTER, new Delegate() {
                @Override
                public void invoke(Object... args) throws Exception {
                    PacketRegister packet = (PacketRegister) args[0];
                }
            });

            put(PacketType.JOIN, new Delegate() {
                @Override
                public void invoke(Object... args) throws Exception {
                    ConsoleColours.Print(ConsoleColours.BLACK_BOLD+ConsoleColours.YELLOW_BACKGROUND,"Received JOIN" + HexaServer.WhatAmI(server));
                    System.out.println();

                    PacketJoin packet = (PacketJoin) args[0];

                    // I'm not the host, so either a new player has joined the game or I have joined the game
                    System.out.println(HexaServer.senderId.toString() + " ////// " + packet.getHostId().toString());
                    if (packet.getHostId().equals(HexaServer.senderId)) {
                        System.out.println("==== YOU HAVE JOINED THE GAME!! (Username: " + packet.getUsername() + ")");
                        Monopoly.instance.setScreen(Monopoly.instance.screenLobby);
                    } else {
                        System.out.println(packet.getUsername() + " has joined the game");
                        //GameManager.instance.messageUtil.add(packet.getUsername() + " has joined the room!");
                    }

                }
            });

            put(PacketType.LEAVE, new Delegate() {
                @Override
                public void invoke(Object... args) throws Exception {
                    //ConsoleColours.Print(ConsoleColours.BLACK_BOLD+ConsoleColours.YELLOW_BACKGROUND,"Received LEAVE" + HexaServer.WhatAmI(server));
                    PacketLeave packetLeave = (PacketLeave) args[0];

                    String message;
                    if (packetLeave.isKick()) {
                        message = "(" + packetLeave.getLeaverUuid() + ") has been kicked";
                    } else {
                        message = "(" + packetLeave.getLeaverUuid() + ") has left the game";
                    }

                    if (packetLeave.getLeaverUuid().equals(HexaServer.senderId)) {
                        ConsoleColours.Print(ConsoleColours.BLACK_BOLD+ConsoleColours.YELLOW_BACKGROUND,"##### YOU - " + message + HexaServer.WhatAmI(server));
                        server.disconnect();
                        Monopoly.instance.setScreen(Monopoly.instance.screenMenu);
                    } else {
                        ConsoleColours.Print(ConsoleColours.BLACK_BOLD+ConsoleColours.YELLOW_BACKGROUND,"##### SOMEONE ELSE - " + message + HexaServer.WhatAmI(server));

                    }


                }
            });



            put(PacketType.PLAYER_STATUS, new Delegate() {
                @Override
                public void invoke(Object... args) throws Exception {
                    ConsoleColours.Print(ConsoleColours.BLACK_BOLD+ConsoleColours.YELLOW_BACKGROUND,"Received PLAYER_STATUS" + HexaServer.WhatAmI(server));
                    System.out.println();
                    PacketPlayerStatus player = (PacketPlayerStatus)args[0];

                    ConsoleColours.Print(ConsoleColours.BLACK+ConsoleColours.YELLOW_BACKGROUND,"Received Packet for(PLAYERID): " + player.PlayerID + "|| I am(SERVERID): "+HexaServer.senderId  + HexaServer.WhatAmI(server));
                    //ConsoleColours.Print(ConsoleColours.BLACK+ConsoleColours.YELLOW_BACKGROUND,"   It contains this payload: " + player.Stats + HexaServer.WhatAmI(server));


                    /*if(player.PlayerID == player.getSenderId())
                        System.out.println("Status for HOST");
                    else
                        System.out.println("Status for Client");
                        */

                    if(player.PlayerID.equals(HexaServer.senderId)){
                        //ConsoleColours.Print(ConsoleColours.BLACK+ConsoleColours.YELLOW_BACKGROUND,"        Set given stats for:" + player.PlayerID + "|| I am: " + HexaServer.WhatAmI(server));

                        //GameManager.instance.setPlayerResources(player.Stats);
                    }
                    //if(!server.isHost())
                    //GameManager.instance.setPlayerResources(player.Stats);


                }
            });


            put(PacketType.SERVER_LIST, new Delegate() {
                @Override
                public void invoke(Object... args) throws Exception {
                    PacketServerList packetServerList = (PacketServerList) args[0];
                    ConsoleColours.Print(ConsoleColours.BLACK_BOLD+ConsoleColours.YELLOW_BACKGROUND,"Received SERVICE_LIST "+ packetServerList.entries.size() + HexaServer.WhatAmI(server));
                    System.out.println();
                    for(PacketServerList.Entry e : packetServerList.entries)
                        System.out.println(e);


                    Monopoly.instance.screenJoin.updateServerList(packetServerList.entries);
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
                                            ConsoleColours.Print(ConsoleColours.BLACK+ConsoleColours.YELLOW_BACKGROUND,"Clicked room " + entry.room + HexaServer.WhatAmI(server));
                                            screenJoin.joinRoom(entry.host, entry.room);
                                        }
                                    }), screenJoin.getStage());
                        }

                        screenJoin.subwindowServers.orderAllNeatly(1);
                        screenJoin.subwindowServers.updateElements();
                    }*/
                }
            });
        }}
        ;
    }
}