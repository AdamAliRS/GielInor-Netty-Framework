package com.gie.net.packet.in;

import com.gie.NetworkConstants;
import com.gie.net.ISAACCipher;
import com.gie.net.packet.in.HandleIncomingPackets;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by Adam on 05/10/2016.
 */
public class IncomingPacketDecoder extends ByteToMessageDecoder {


    private ISAACCipher isaacDecrypt;

    public IncomingPacketDecoder(ISAACCipher isaacDecrypt) {
        this.isaacDecrypt = isaacDecrypt;
    }

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() > 0) {
            int opcode = byteBuf.readUnsignedByte() - isaacDecrypt.getNextValue() & 0xFF;
            int size = NetworkConstants.PACKET_SIZES[opcode];

            if (size == -1) {
                size = byteBuf.readUnsignedByte();
            }
            if (byteBuf.readableBytes() >= size) {
                final byte[] buf = new byte[size];
                byteBuf.readBytes(buf);

                ByteBuf buffer = Unpooled.buffer(size);
                buffer.writeBytes(buf);

                list.add(new HandleIncomingPackets(opcode, size, buffer));
            }
        }

    }
}
