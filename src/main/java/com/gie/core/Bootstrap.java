package com.gie.core;

import com.gie.core.channel.ChannelHandler;
import com.gie.core.channel.PipelineInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.logging.Logger;

/**
 * Created by Adam on 05/10/2016.
 */
public final class Bootstrap {

    Logger LOGGER = Logger.getLogger(Bootstrap.class.getName());

    private final ServerBootstrap bootstrap = new ServerBootstrap();

    public Bootstrap bind(int port) {
        LOGGER.info("Attempting to establish connection on port " + port);
        EventLoopGroup mainEventLoopGroup = new NioEventLoopGroup(1);
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        bootstrap.group(mainEventLoopGroup, eventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new PipelineInitializer(new ChannelHandler())).bind(port);
        LOGGER.info("Connection established on port " + port);
        return this;
    }
}




