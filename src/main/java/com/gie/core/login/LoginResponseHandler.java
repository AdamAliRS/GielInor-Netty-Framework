package com.gie.core.login;
/**
 * Created by Adam on 11/10/2016.
 */
public class LoginResponseHandler {

    private LoginResponses loginResponses;

    private final boolean flagged;

    private int rights;

    public LoginResponseHandler(LoginResponses loginResponses, int rights, boolean flagged) {
        this.loginResponses = loginResponses;
        this.rights = rights;
        this.flagged = flagged;
    }

    public LoginResponses getLoginResponses() {
        return loginResponses;
    }

    public int getRights() {
        return rights;
    }

    public boolean isFlagged() {
        return flagged;
    }

}

