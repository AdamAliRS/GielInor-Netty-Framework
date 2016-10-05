package com.gie.net.login;

import com.gie.net.ISAACCipher;
import com.gie.net.LoginGameDecoder;
import com.gie.net.packet.RSPacketBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.security.SecureRandom;
import java.util.List;

/**
 * Created by Adam on 05/10/2016.
 */
public class LoginDecoder extends ByteToMessageDecoder {

    private enum ConnectionStatus {
        CONNECTING,
        LOGGING_IN
    }

    private ConnectionStatus process = ConnectionStatus.CONNECTING;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf buffer, List<Object> list) throws Exception {
        switch (process) {
            case CONNECTING:
                prePhaseConnecting(channelHandlerContext, buffer);
                break;

            case LOGGING_IN:
                prePhaseConnecting(channelHandlerContext, buffer, list);
                break;
        }
    }

    private void prePhaseConnecting(ChannelHandlerContext channelHandlerContext, ByteBuf buffer) {
        if (buffer.readableBytes() < 2) {
            return;
        }

        int request = buffer.readUnsignedByte();

        if (request != 14) {
            System.err.println("Invalid request " + request);
            return;
        }
        ByteBuf out = Unpooled.buffer(18);
        /**
         * Client ignores the first 7 bytes sent by the server
         * long - 7 bytes
         */
        out.writeLong(0);
        out.writeByte(0);
        out.writeLong(new SecureRandom().nextLong());
        channelHandlerContext.writeAndFlush(out);
        process = ConnectionStatus.LOGGING_IN;
    }

    private void prePhaseConnecting(ChannelHandlerContext channelHandlerContext, ByteBuf buffer, List<Object> list) {

        if (buffer.readableBytes() < 2) {
            return;
        }
        int loginType = buffer.readUnsignedByte();
        if (loginType != 16 && loginType != 18) {
            System.err.println("Invalid login type " + loginType);
            return;
        }

        int blockDataLength = buffer.readUnsignedByte();

        if (buffer.readableBytes() > blockDataLength) {
            System.err.println("[LOGINDECODER] Pre_Phase_Logging_In");
            return;
        }
        if (buffer.isReadable(blockDataLength)) {

            buffer.readUnsignedByte(); //Skips -> MagicId[255]

            int revision = buffer.readShort();

            if (revision != 317) {
                System.err.println("Invalid Client version " + revision);
                return;
            }

            buffer.readUnsignedByte(); //Skips -> Memory Usage

            for (int i = 0; i < 9; i++) {
                buffer.readInt(); //Skips -> CRC keys -> Looping same amount as Client
            }

            buffer.readUnsignedByte(); //Skips -> RSA Block TODO: RSA

            int rsaOpcode = buffer.readUnsignedByte();
            if (rsaOpcode != 10) {
                System.err.println("Invalid RSA opcode " + rsaOpcode);
                return;
            }

            long clientHalf = buffer.readLong();
            long serverHalf = buffer.readLong();
            int[] sessionKey = {(int) (clientHalf >> 32), (int) clientHalf, (int) (serverHalf >> 32), (int) serverHalf};
            ISAACCipher inCipher = new ISAACCipher(sessionKey);
            for (int i = 0; i < 4; i++) {
                sessionKey[i] += 50;
            }

            ISAACCipher outCipher = new ISAACCipher(sessionKey);

            buffer.readInt();

            String username = RSPacketBuilder.getRS2String(buffer);
            String password = RSPacketBuilder.getRS2String(buffer);

            list.add(new PlaceholderPlayer(username, password, inCipher, outCipher, channelHandlerContext));
        }
    }

}
