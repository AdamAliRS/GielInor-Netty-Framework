package com.gie.core.login;

import com.gie.core.codec.ISAACCipher;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Adam on 05/10/2016.
 */
public class LoginHandler {

    private String username;
    private String password;
    private ISAACCipher in;
    private ISAACCipher out;
    private ChannelHandlerContext channel;

    public LoginHandler(String username, String password, ISAACCipher in, ISAACCipher out, ChannelHandlerContext channel) {
        this.username = username;
        this.password = password;
        this.in = in;
        this.out = out;
        this.channel = channel;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ChannelHandlerContext getChannel() {
        return channel;
    }

    public ISAACCipher getIn() {
        return out;
    }

    public ISAACCipher getOut() {
        return out;
    }
}
