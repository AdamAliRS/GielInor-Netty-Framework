package com.gie.game.entity;

import com.gie.ServerConfiguration;
import com.gie.game.GameConstants;
import com.gie.game.Tile;

/**
 * Created by Adam on 02/10/2016.
 */


public abstract class Entity {

    private int index = -1;

    private Tile tile;

    public Entity() {
        setTile(GameConstants.DEFAULT_LOCATION);
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public int getX() {
        return tile.getX();
    }

    public int getY() {
        return tile.getY();
    }

    public int getHeight() {
        return tile.getHeight();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}

