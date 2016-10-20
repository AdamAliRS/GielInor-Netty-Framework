package com.gie.game;

import com.gie.ServerConfiguration;
import com.gie.game.entity.EntityCollection;
import com.gie.game.entity.player.Player;

import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

/**
 * Created by Adam on 12/10/2016.
 */
public class World {

    private static final Logger LOGGER = getLogger(World.class.getName());

    private EntityCollection<Player> players = new EntityCollection(ServerConfiguration.PLAYER_MAXIMUM);

    /**
     * TODO later
     * Dummy updating variable for login responses
     */
    private boolean updating;

    public void addPlayer(Player player) {
        players.add(player);
        LOGGER.info("Player added " + player.getUsername());
    }

    public boolean isUpdating() {
        return updating;
    }

    public EntityCollection<Player> getPlayers() {
        return players;
    }

}
