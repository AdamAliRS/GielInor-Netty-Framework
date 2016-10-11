package com.gie.core.login;

/**
 * Created by Adam on 11/10/2016.
 */

public enum LoginResponses {
    LOGIN_SUCCESSFUL(2);

    private int id;

    LoginResponses(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
