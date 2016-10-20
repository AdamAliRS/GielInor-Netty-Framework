package com.gie.core.packet;

import io.netty.buffer.ByteBuf;

/**
 * Created by Adam on 05/10/2016.
 */
public class GameMessage {
    /**
     * Repersents a single packet
     */
    private int opCode;

    private int size;

    private ByteBuf buffer;

    public GameMessage(int opCode, int size, ByteBuf buffer) {
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
