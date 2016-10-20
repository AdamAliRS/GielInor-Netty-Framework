package com.gie.core.login;

/**
 * Created by Adam on 11/10/2016.
 */

public enum LoginResponses {
    LOGIN_SUCCESSFUL(2),
    FALSE_CREDENTIALS(3),
    ACCOUNT_DISABLED(4),
    ALREADY_LOGGED_IN(5),
    SERVER_UPDATED(6),
    WORLD_IS_FULL(7),
    LOGIN_SERVER_OFFLINE(8),
    LOGIN_LIMIT_EXCEEDED(9),
    BAD_SESSION_ID(10),
    LOGIN_SERVER_REJECTED(11),
    MEMBER_ACCOUNT_REQUIRED(12),
    COULD_NOT_COMPLETE_LOGIN(13),
    SERVER_IS_BEING_UPDATED(14),
    ATTEMPTED_TO_RECONNECT(15),
    LOGIN_ATTEMPTS_EXCEEDED(16),
    MEMBER_ONLY_ZONE(17),
    INVALID_LOGIN_SERVER_REQUESTED(20),
    PROFILE_IS_BEING_TRANSFERRED(21);

    private int id;

    LoginResponses(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
