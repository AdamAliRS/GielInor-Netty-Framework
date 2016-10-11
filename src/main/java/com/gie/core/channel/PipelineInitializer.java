package com.gie.core.channel;

import com.gie.core.NetworkConstants;
import com.gie.core.login.LoginDecoder;
import com.gie.core.login.LoginResponsePacket;
import com.gie.core.login.LoginSession;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Implementation of nettys {@link ChannelPipeline}
 * Deteremines what to add into the pipeline
 * Created by Adam on 05/10/2016.
 */
public class PipelineInitializer extends ChannelInitializer<SocketChannel> {

    private ChannelHandler handler;

    public PipelineInitializer(ChannelHandler handler) {
        this.handler = handler;
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        final ChannelPipeline pipeline = channel.pipeline();
        channel.attr(NetworkConstants.SESSION_KEY).set(new LoginSession(channel));
        pipeline.addLast("login-decoder", new LoginDecoder());
        pipeline.addLast("network-handler", handler);
        pipeline.addLast("login-encoder", new LoginResponsePacket());
    }
}
