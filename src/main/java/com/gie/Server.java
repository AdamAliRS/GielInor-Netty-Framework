package com.gie;

/**
 * Created by Adam on 05/10/2016.
 */
public class Server {

    public static void main(String[] args) {
        new ServerHandler().bind(NetworkConstants.PORT);
    }
}
