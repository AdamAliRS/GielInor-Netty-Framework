package com.gie.net.login;

import com.gie.net.ISAACCipher;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Adam on 05/10/2016.
 */
public class LoginDetails {

    private String username;
    private String password;
    private ISAACCipher in;
    private ISAACCipher out;
    private ChannelHandlerContext channel;

    public LoginDetails(String username, String password, ISAACCipher in, ISAACCipher out) {
        this.username = username;
        this.password = password;
        this.in = in;
        this.out =    out;
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

    public ISAACCipher getOut() {
        return out;
    }
}
