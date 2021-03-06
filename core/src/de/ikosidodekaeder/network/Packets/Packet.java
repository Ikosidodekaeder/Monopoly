package de.ikosidodekaeder.network.Packets;

/**
 * Created by z003pksw on 09.04.2018.
 */

import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

import de.ikosidodekaeder.network.HexaServer;

/**
 * Created by Johannes on 19.02.2018.
 */

public abstract class Packet {

    private PacketType      type;
    private boolean         cancelled;
    /**
     * Issued by the server instance which hosts the game
     */
    private UUID senderId;



    Packet(PacketType type){
        this.type = type;
        this.cancelled = false;
        this.senderId = HexaServer.senderId;
    }

    Packet(PacketType type, UUID senderId){
        this.type = type;
        this.cancelled = false;
        this.senderId = senderId;
    }


    public PacketType getType() {
        return type;
    }

    public void setType(PacketType type) {
        this.type = type;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public UUID getSenderId() {
        return senderId;
    }

    public void setSenderId(UUID senderId) {
        this.senderId = senderId;
    }

    public String serialize() {
        return type.ID + ";"
                + senderId.toString() + ";"
                + (cancelled ? 1 : 0) + ";";
    }

    public static Packet deserialize(String string) {
        String[] arr = string.split(";");
        byte typeId = Byte.parseByte(arr[0]);
        PacketType packetType = PacketType.valueOf(typeId);
        if (packetType == null) {
            return null;
        }

        UUID senderId = UUID.fromString(arr[1]);

        boolean cancelled = arr[2].equals("1");

        if (packetType == PacketType.REGISTER) {
            return new PacketRegister(senderId, arr[3], cancelled); // arr[3] is the room name
        }


        int offset = 3;

        switch (packetType) {
            case KEEPALIVE:
                int sessionId = Integer.parseInt(arr[offset]);
                return new PacketKeepAlive(senderId, sessionId);
            case JOIN:
                UUID hostId = UUID.fromString(arr[offset]);
                String username = arr[offset+1];
                String version = arr[offset+2];
                return new PacketJoin(senderId, username, hostId, version);
            case SERVER_LIST:
                PacketServerList packetServerList = new PacketServerList(senderId);
                for (int i=offset; i<arr.length; i++) {
                    String[] strEntry = arr[i].split(",");
                    PacketServerList.Entry entry = new PacketServerList.Entry(
                            UUID.fromString(strEntry[0]),
                            strEntry[1]
                    );
                    packetServerList.entries.add(entry);
                }
                return packetServerList;

            case LEAVE:
                UUID leaverUuid = UUID.fromString(arr[offset]);
                boolean kick = arr[offset+1].equals("true");
                return new PacketLeave(senderId, leaverUuid, kick);
            case PLAYER_STATUS:{
                String[] values = arr[offset].split(",");
                Map<String,Integer> payload = new Hashtable<>();
                for (String stringResource : values) {
                    String[] arrResource = stringResource.split("=");
                    payload.put(arrResource[0], Integer.parseInt(arrResource[1]));
                }

                return new PacketPlayerStatus(senderId,UUID.fromString(arr[offset+1]),payload);
            }
        }

        return null;
    }
}