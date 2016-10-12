package com.gie.game;

import com.gie.Constants;
import com.gie.game.entity.EntityCollection;
import com.gie.game.entity.player.Player;

/**
 * Created by Adam on 12/10/2016.
 */
public class World {

    private EntityCollection<Player> players = new EntityCollection(Constants.PLAYER_MAXIMUM);

    public void addPlayer(Player player) {
        String username = player.getUsername();
        players.add(player);
        System.out.println(username + " has registered");
    }

    public EntityCollection<Player> getPlayers() {
        return players;
    }

}
