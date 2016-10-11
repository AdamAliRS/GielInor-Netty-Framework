package com.gie.core.packet;

import io.netty.buffer.ByteBuf;

public class RSPacketBuilder {

    /**
     * Reads a RuneScape string from a buffer.
     *
     * @param buf
     *            The buffer.
     * @return The string.
     */
        public static String getRS2String(final ByteBuf buf) {
        final StringBuilder bldr = new StringBuilder();
        byte b;
        while (buf.isReadable() && (b = (byte) buf.readUnsignedByte()) != 10) {
            bldr.append((char) b);
        }
        return bldr.toString();
    }


}
