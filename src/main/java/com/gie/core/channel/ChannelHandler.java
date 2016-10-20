package com.gie.core.channel;

import com.gie.core.NetworkConstants;
import com.gie.core.login.LoginSession;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Adam on 09/10/2016.
 */
public class ChannelHandler extends SimpleChannelInboundHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        if (channelHandlerContext.channel().isOpen()) {
            SessionHandler session = channelHandlerContext.channel().attr(NetworkConstants.SESSION_KEY).get();
            session.receieveMessage(o);
        }
    }
}
