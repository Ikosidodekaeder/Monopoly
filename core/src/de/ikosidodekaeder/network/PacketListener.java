package de.ikosidodekaeder.network;

import java.util.HashMap;
import java.util.Map;

import de.ikosidodekaeder.network.HexaServer;
import de.ikosidodekaeder.network.Packets.Packet;
import de.ikosidodekaeder.network.Packets.PacketType;
import de.ikosidodekaeder.util.Delegate;

/**
 * Created by Johannes on 09.04.2018.
 */

public abstract class PacketListener {

    HexaServer server;
    protected Map<PacketType,Delegate> dispatchTable = new HashMap<>();

    public PacketListener(HexaServer server) {
        this.server = server;
    }

    public abstract void registerAll();

    public void call(Packet packet) throws Exception {
        /**
         * replaces All methods and will only invoke if the packet contains a type which is
         * implemented. Otherwise an exception is thrown
         */
        Delegate delegate = dispatchTable
                .get(
                        packet.getType()
                );

        if (delegate != null) {
            delegate.invoke(
                    packet
            );
        } else {
            System.err.println("ERROR: No delegate found for " + packet.getType().name());
            Thread.dumpStack();
        }

    }

}