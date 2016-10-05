package com.gie;

import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Adam on 05/10/2016.
 */
public class PlaceholderPlayer {

    private String username;
    private String password;
    private ISAACCipher in;
    private ISAACCipher out;
    private ChannelHandlerContext channel;

    public PlaceholderPlayer(String username, String password, ISAACCipher in, ISAACCipher out, ChannelHandlerContext channel) {
        this.username = username;
        this.password = password;
        this.in = in;
        this.out = out;
        this.channel = channel;
    }

}
