package com.gie.core.login;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by Adam on 11/10/2016.
 */
public class LoginResponsePacket extends MessageToByteEncoder<LoginResponseHandler> {


    @Override
    protected void encode(ChannelHandlerContext ctx, LoginResponseHandler msg, ByteBuf out) throws Exception {
        out.writeByte(msg.getLoginResponses().getId());
        //TODO: Sending proper rights/isFlagged bytes
        if (msg.getLoginResponses() == LoginResponses.LOGIN_SUCCESSFUL) {
            out.writeByte(0); //Player rights
            out.writeByte(0); //Flagged

        }
    }}
