package com.gie.game;

import com.gie.game.entity.player.PlayerAppearance;
import com.gie.game.entity.player.PlayerGender;

/**
 * Created by Adam on 13/10/2016.
 */
public class GameConstants {

    public static final PlayerAppearance DEFAULT_APPEARANCE = new PlayerAppearance(PlayerGender.MALE, new int[]{0, 10, 18, 26, 33, 36, 42}, new int[]{7, 8, 9, 5, 0});

    public static final Tile DEFAULT_LOCATION = new Tile(3093, 3493, 0);

    private GameConstants() {

    }
}
