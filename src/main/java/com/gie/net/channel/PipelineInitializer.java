package com.gie.net.channel;

import com.gie.net.login.LoginDecoder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

/**
 * Implementation of nettys {@link ChannelPipeline}
 * Deteremines what to add into the pipeline
 * Created by Adam on 05/10/2016.
 */
public class PipelineInitializer extends ChannelInitializer {

    protected void initChannel(Channel channel) throws Exception {
        final ChannelPipeline pipeline = channel.pipeline();


        pipeline.addLast("decoder", new LoginDecoder());

    }
}
