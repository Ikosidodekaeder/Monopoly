package de.ikosidodekaeder.network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import de.ikosidodekaeder.network.Packets.Packet;
import de.ikosidodekaeder.network.Packets.PacketKeepAlive;
import de.ikosidodekaeder.network.Packets.PacketPlayerStatus;
import de.ikosidodekaeder.network.Packets.PacketType;

/**
 * Created by z003pksw on 09.04.2018.
 */

public class HexaServer {

    private static final int    TIMEOUT = 20_000;
    public static final String  VERSION = "0.1";

    public static UUID senderId = UUID.randomUUID();

    /**
     * connection stuff
     */
    private Socket socket;
    private InetSocketAddress address;

    /**
     * BUFFER SEND - RECEIVE
     */
    private List<Packet>        toSend = new ArrayList<>();
    private final Object        sendingLock = new Object();

    private List<Packet>        toCall = new ArrayList<>();
    private final Object        receivingLock = new Object();

    /**
     * PacketHandler
     */
    private boolean             running = false;

    private ServerListener      hostListener;
    private ClientListener      clientListener;

    public UUID                 LocalClientID = senderId;

    /**
     * Coordination
     */
    public long                 lastKeepAliveSent = System.currentTimeMillis();
    private boolean             offlineGame = false;
    private boolean             isHost = false;




    public static String WhatAmI(HexaServer server) {
        return (server.isHost() ? " (Host)" : " (Client) ");
    }

    public HexaServer(String address, int port) {
        if (address == null) return; // If the address is null, the player wants to play offline
        this.address    = new InetSocketAddress(address, port);
        this.socket     = new Socket();
    }

    public HexaServer(String address, int port, boolean isHost) {
        this(address, port);
        this.isHost = isHost;
        if (isHost) {
            ;//sessionData = new SessionData();
        }
        offlineGame = false;
    }

    public void hostOffline() {
        System.out.println("=== Hosting offline game ===");
        hostListener = new ServerListener(this);
        hostListener.registerAll();
        clientListener = new ClientListener(this);
        clientListener.registerAll();

        /*sessionData = new SessionData();
        sessionData.addNewPlayer(HexaServer.senderId,"OFFLINE_HOST",
                GameManager.instance.colorUtil.getNext());*/

        socket = new Socket(); // Just an empty socket instance to prevent nullpointers
        offlineGame = true;
    }

    public void connect(int timeout) throws IOException {
        socket.connect(address, timeout);
        offlineGame = false;
        running = true;
        startSendingThread();
        startReceivingThread();
        System.out.println("=======   Connected to Router   =======");
        //serverSocket = new ServerSocket(this.address.getPort());
        //serverSocket.setSoTimeout(TIMEOUT);

        if (isHost()) {
            hostListener = new ServerListener(this);
            hostListener.registerAll();
        }
        clientListener = new ClientListener(this);
        clientListener.registerAll();

    }

    public Packet receive() {
        synchronized (receivingLock){
            if(!toCall.isEmpty())
                return toCall.remove(toCall.size());
        }

        return null;
    }

    public void send(Packet packet) {
        if (isHost() &&
                        packet.getSenderId().equals(HexaServer.senderId) &&
                        packet.getType() != PacketType.KEEPALIVE
                ) {
            try {
                clientListener.call(packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (socket == null || !socket.isConnected()) {
            return;
        }
        synchronized (sendingLock) {
            toSend.add(packet);
        }
    }

    /**
     * Starts asynchronous thread. continuously sends everything contained in the "toSend" buffer.
     */
    public void startSendingThread() {
        final List<Packet>        sendBuffer = new ArrayList<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    synchronized (sendingLock) {
                        sendBuffer.addAll(toSend);
                        toSend.clear();
                    }
                    if (sendBuffer.isEmpty()) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }
                    try {
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        //ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

                        for (int i = 0; i < sendBuffer.size(); i++) {

                            Packet packet = sendBuffer.get(i);

                            System.out.println("(" + isHost() + ") Sending " + packet.getClass().getName() + " --> '" + packet.serialize() + "'");
                            out.println(packet.serialize());
                            out.flush();
                        }
                        Thread.sleep(300);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    } finally {

                        sendBuffer.clear();
                    }

                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Starts asynchronous thread. continuously receives everything and saves it in the "toCall" buffer.
     */
    public void startReceivingThread() {
        final List<Packet>        receiveBuffer = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String disconnectReason = "Disconnected";
                while (running) {
                    receiveBuffer.clear();

                    try {
                        Scanner in = new Scanner(socket.getInputStream());
                        //ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                        int readAmount = 0;

                        socket.setSoTimeout(TIMEOUT);

                        while (in.hasNext()) {
                            socket.setSoTimeout(TIMEOUT);
                            readAmount++;
                            String stringPacket = in.nextLine();
                            if (stringPacket.equals("")) {
                                System.out.println("I received an empty packet.");
                            } else {
                                System.out.println("I received a packet! " + stringPacket);
                                Packet packet = Packet.deserialize(stringPacket);
                                if (packet == null) {
                                    System.out.println("Packet is null :(");
                                } else {
                                    System.out.println(packet.getType().name());
                                    synchronized (receivingLock) {
                                        toCall.add(packet);
                                    }
                                }
                            }
                        }

                        if (readAmount == 0) {
                            running = false;
                            //System.out.println("Connection lost: Server closed connection");
                            disconnectReason = "Disconnected: Server closed connection";
                        }

                    } catch (SocketTimeoutException e) {
                        running = false;
                        //System.out.println("Connection to the Server lost: Timeout (" + TIMEOUT + ")");
                        disconnectReason = "Disconnected: Timeout (" + TIMEOUT + ")";
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    receiveBuffer.clear();
                }

                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                final String finalDisconnectReason = disconnectReason;

            }
        }).start();
    }

    public void callEvents() {
        if (socket.isConnected() && isHost()) {
            if (System.currentTimeMillis() - lastKeepAliveSent >= 3_500) {
                broadcastKeepAlive();
            }
        }

        synchronized (receivingLock) {
            for (int i=0; i<toCall.size(); i++) {
                try {
                    //packetListener.call(toCall.get(i));
                    if (isHost()) {
                        System.out.println("calling host " + toCall.get(i).getType().name());
                        hostListener.call(toCall.get(i));
                    } else {
                        System.out.println("calling client " + toCall.get(i).getType().name());
                        clientListener.call(toCall.get(i));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            toCall.clear();
        }
    }

    public boolean disconnect() {
        if (socket.isClosed()) {
            System.out.println("Could not disconnect because the socket is already closed");
            return false;
        }
        running = false;
        try {
            socket.close();
            System.out.println("You have disconnected from the game");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public UUID getLocalClientID() {
        return LocalClientID;
    }

    public void broadcastKeepAlive() {
        lastKeepAliveSent = System.currentTimeMillis();
        send(new PacketKeepAlive(senderId, 1));
    }

    public boolean isHost() {
        return isHost || offlineGame;
    }

    public ServerListener getHostListener() {
        return hostListener;
    }

    public ClientListener getClientListener() {
        return clientListener;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isOfflineGame() {
        return offlineGame;
    }
}