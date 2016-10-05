package com.gie.net.packet;

/*
 * This file is part of RuneSource.
 *
 * RuneSource is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * RuneSource is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with RuneSource.  If not, see <http://www.gnu.org/licenses/>.
 */

import io.netty.buffer.ByteBuf;

import java.io.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author blakeman8192
 */
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
