package com.gie.game;

/**
 * Created by Adam on 11/10/2016.
 */
public final class Tile {

    private final int x;

    private final int y;

    private final int height;

    /**
     * Contructs a new tile with an x/y/height coordinate
     * @param x
     * @param y
     * @param height
     */
    public Tile(int x, int y, int height) {
        this.x = (short) x;
        this.y = (short) y;
        this.height = (short) height;
    }

    /**
     * Constructs a new tile with an x/y coordinate
     * @param x
     * @param y
     */
    public Tile(int x, int y) {
        this(x, y, 0);
    }

    /**
     * Construcuts a new tile which is based on another tile
     * @param newTile
     */
    public Tile(Tile newTile) {
        this.x = newTile.x;
        this.y = newTile.y;
        this.height = newTile.height;
    }

    public int getRegionX() {
        return (x >> 3) - 6;
    }

    public int getRegionY() {
        return (y >> 3) - 6;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

}
