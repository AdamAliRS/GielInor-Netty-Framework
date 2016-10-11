package com.gie.game;

import com.gie.game.entity.Entity;
import com.gie.game.entity.EntityList;
import com.gie.game.entity.player.Player;

/**
 * Created by Adam on 09/10/2016.
 */
public class World {

    private static final World world = new World();

    private EntityList<Player> players = new EntityList<>(1000);

    public void addPlayer(Entity entity) {
        if (entity.isPlayer()) {
            Player player = (Player) entity;
            players.add(player);
            System.out.println("Player Added " + player.getUsername());
        }
    }

    public static World getWorld() {
        return world;
    }
}
