package com.gie;

import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;

/**
 * Created by Adam on 05/10/2016.
 */
public class Server {

    public static void main(String[] args) {
        Stopwatch timer = Stopwatch.createStarted();
        new ServerHandler().bind(NetworkConstants.PORT);
        timer.stop();
        System.out.println("Time taken to start server: " + timer.elapsed(TimeUnit.MILLISECONDS) +"ms");
    }
}
