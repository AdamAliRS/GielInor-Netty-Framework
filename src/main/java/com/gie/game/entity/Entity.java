package com.gie.game.entity;

import com.gie.game.entity.player.Player;

/**
 * Created by Adam on 02/10/2016.
 */
public abstract class Entity {

    private int index = -1;

    public Entity() {

    }



    public boolean isPlayer() {
        return getClass() == Player.class;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}

