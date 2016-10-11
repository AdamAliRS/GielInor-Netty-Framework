package com.gie.core.channel;

import io.netty.channel.Channel;

import java.util.concurrent.ExecutionException;

/**
 * Created by Adam on 09/10/2016.
 */
public abstract class SessionHandler {

    private final Channel channel;

    public SessionHandler(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    public abstract void receieveMessage(Object o) throws ExecutionException, InterruptedException;


}
