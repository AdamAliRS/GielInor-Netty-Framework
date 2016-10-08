package com.gie;

import com.gie.net.channel.PipelineInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.logging.Logger;

/**
 * Created by Adam on 05/10/2016.
 */
public final class ServerHandler {

    Logger LOGGER = Logger.getLogger(ServerHandler.class.getName());

    private final ServerBootstrap bootstrap = new ServerBootstrap();


    public ServerHandler bind(int port) {
        LOGGER.info("Attempting to estabilish connection on port " + port);
        EventLoopGroup mainEventLoopGroup = new NioEventLoopGroup(1);
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        bootstrap.group(mainEventLoopGroup, eventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new PipelineInitializer()).bind(port);
        LOGGER.info("Connection estabilished on port " + port);
        return this;
    }
}




