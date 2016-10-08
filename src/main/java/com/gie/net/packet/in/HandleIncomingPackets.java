package com.gie.net.packet.in;

import io.netty.buffer.ByteBuf;

/**
 * Created by Adam on 05/10/2016.
 */
public class HandleIncomingPackets {
    /**
     * Repersents a single packet
     */
    private int opCode;

    private int size;

    private ByteBuf buffer;

    public HandleIncomingPackets(int opCode, int size, ByteBuf buffer) {
        this.opCode = opCode;
        this.size = size;
        this.buffer = buffer;
    }

    public int getOpCode() {
        return opCode;
    }

    public int getSize() {
        return size;
    }

    public ByteBuf getBuffer() {
        return buffer;
    }

}
