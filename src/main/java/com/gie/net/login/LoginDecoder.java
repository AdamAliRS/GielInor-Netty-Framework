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
 * Created by Adam on 05/10/2016.
 */
public class LoginDecoder extends ByteToMessageDecoder {

    private enum ConnectionStatus {
        CONNECTING,
        LOGGING_IN
    }

    private ConnectionStatus process = ConnectionStatus.CONNECTING;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        switch (process) {
            case CONNECTING:
                Pre_Phase_Connecting(channelHandlerContext, byteBuf);
                break;

            case LOGGING_IN:
                Pre_Phase_Logging_In(channelHandlerContext, byteBuf, list);
                break;
        }
    }

    private void Pre_Phase_Connecting(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        if (byteBuf.readableBytes() < 2) {
            return;
        }

        int request = byteBuf.readUnsignedByte();

        if (request != 14) {
            System.err.println("Invalid request " + request);
            return;
        }
        ByteBuf out = Unpooled.buffer(18);
        out.writeLong(0);
        out.writeByte(0);
        out.writeLong(new SecureRandom().nextLong());
        channelHandlerContext.writeAndFlush(out);
        process = ConnectionStatus.LOGGING_IN;
    }

    private void Pre_Phase_Logging_In(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {

        if (byteBuf.readableBytes() < 2) {
            return;
        }
        int loginType = byteBuf.readUnsignedByte();
        if (loginType != 16 && loginType != 18) {
            System.err.println("Invalid login type " + loginType);
            return;
        }

        int blockDataLength = byteBuf.readUnsignedByte();

        if (byteBuf.readableBytes() > blockDataLength) {
            System.err.println("[LOGINDECODER] Pre_Phase_Logging_In");
            return;
        }
        if (byteBuf.isReadable(blockDataLength)) {

            byteBuf.readUnsignedByte(); //Skips -> MagicId[255]

            int revision = byteBuf.readShort();

            if (revision != 317) {
                System.err.println("Invalid Client version " + revision);
                return;
            }

            byteBuf.readUnsignedByte(); //Skips -> Memory Usage

            for (int i = 0; i < 9; i++) {
                byteBuf.readInt(); //Skips -> CRC keys -> Looping same amount as Client
            }

            byteBuf.readUnsignedByte(); //Skips -> RSA Block TODO: RSA

            int rsaOpcode = byteBuf.readUnsignedByte();
            if (rsaOpcode != 10) {
                System.err.println("Invalid RSA opcode " + rsaOpcode);
                return;
            }

            long clientHalf = byteBuf.readLong();
            long serverHalf = byteBuf.readLong();
            int[] sessionKey = {(int) (clientHalf >> 32), (int) clientHalf, (int) (serverHalf >> 32), (int) serverHalf};
            ISAACCipher inCipher = new ISAACCipher(sessionKey);
            for (int i = 0; i < 4; i++) {
                sessionKey[i] += 50;
            }

            ISAACCipher outCipher = new ISAACCipher(sessionKey);

            byteBuf.readInt();

            String username = RSPacketBuilder.getRS2String(byteBuf);
            String password = RSPacketBuilder.getRS2String(byteBuf);

            list.add(new PlaceholderPlayer(username, password, inCipher, outCipher, channelHandlerContext));
        }
    }


}
