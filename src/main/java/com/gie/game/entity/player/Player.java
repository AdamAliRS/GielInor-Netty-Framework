package com.gie.game.entity.player;

import com.gie.game.entity.Entity;
import com.gie.core.login.LoginSession;

/**
 * Created by Adam on 08/10/2016.
 */
public class Player extends Entity {

    private LoginSession session;

    private String username;
    private String password;
    private String address;

    public Player(LoginSession session) {
        super();
        this.session = session;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public LoginSession getSession() {
        return session;
    }
}