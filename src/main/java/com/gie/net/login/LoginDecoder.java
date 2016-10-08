package com.gie.net.login;

import com.gie.net.ISAACCipher;
import com.gie.net.packet.RSPacketBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.security.SecureRandom;
import java.util.List;

/**
 * Decodes all login requests
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
                prePhaseLogging(channelHandlerContext, buffer, list);
                break;
        }
    }

    private void prePhaseConnecting(ChannelHandlerContext channelHandlerContext, ByteBuf buffer) {
        if (buffer.readableBytes() >= 2) {
            int request = buffer.readUnsignedByte();

            if (request != 14 && request != 15) {
                return;
            }

            buffer.readUnsignedByte();
            ByteBuf buf = Unpooled.buffer(17);
            buf.writeLong(0);
            buf.writeByte(0);
            buf.writeLong(new SecureRandom().nextLong());
            channelHandlerContext.writeAndFlush(buf);
            process = ConnectionStatus.LOGGING_IN;
        }


    }

    private int blockDataLength;

    private void prePhaseLogging(ChannelHandlerContext channelHandlerContext, ByteBuf buffer, List<Object> list) {

        if (buffer.readableBytes() < 2) {
            return;
        }
        int request = buffer.readUnsignedByte();

        if (request != 16 && request != 18) {
            System.err.println("[Invalid login type " + request);
            return;
        }

        blockDataLength = buffer.readUnsignedByte();

        if (buffer.readableBytes() < blockDataLength) {
            System.err.println("[LOGINDECODER] Pre_Phase_Logging_In");
            return;
        }

        buffer.readUnsignedByte();

        int clientVersion = buffer.readShort();

        if (clientVersion != 317) {
            System.err.println("Invalid client version " + clientVersion);
            return;
        }

        buffer.readUnsignedByte();
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
        System.out.println("Logging in");
        list.add(new LoginDetails(username, password, inCipher, outCipher));
    }

}
