package com.gie.core.login;

import com.gie.core.channel.SessionHandler;
import com.gie.core.login.game.GameDecoder;
import com.gie.game.entity.player.Player;
import com.gie.game.World;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.SocketChannel;

import java.util.concurrent.ExecutionException;

/**
 * Created by Adam on 09/10/2016.
 */
public class LoginSession extends SessionHandler {


    private Player player;

    public LoginSession(Channel channel) {
        super(channel);
        this.player = new Player(this);
    }

    public Player getPlayer() {
        return player;
    }


    private LoginResponses responses = LoginResponses.LOGIN_SUCCESSFUL;

    @Override
    public void receieveMessage(Object o) throws ExecutionException, InterruptedException {
        if (!(o instanceof LoginHandler)) {
            return;
        }
        LoginHandler details = (LoginHandler) o;
        SocketChannel channel = (SocketChannel) details.getChannel().channel();

        Player player = this.getPlayer();

        String username = details.getUsername();
        String password = details.getPassword();
        String address = channel.remoteAddress().getAddress().getHostAddress();

        player.setUsername(username);
        player.setPassword(password);
        player.setAddress(address);

        ChannelFuture future = channel.writeAndFlush(new LoginResponseHandler(responses, 0, false));

        if (responses != LoginResponses.LOGIN_SUCCESSFUL) {
            future.addListener(ChannelFutureListener.CLOSE);
        }

        future.awaitUninterruptibly();
        channel.pipeline().replace("login-decoder", "incoming-decoder", new GameDecoder(details.getOut()));

        System.out.println("Registered " + player.getUsername());
        World.getWorld().addPlayer(player);
    }
}
