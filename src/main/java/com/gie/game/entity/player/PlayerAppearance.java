package com.gie.game.entity.player;

import com.google.common.base.Preconditions;

/**
 * Created by Adam on 13/10/2016.
 */
public class PlayerAppearance {

    /**
     * Players gender
     */
    private PlayerGender gender;

    /**
     * Array of styles for this player
     * Head/Jaw/Torso/Arms/Hands/Legs/Feet/
     */
    private int[] style = new int[7];

    /**
     * Array of colours for this player
     * Hair/Toros/Legs/Feet/Skin colours
     */
    private int[] colours = new int[5];


    /**
     * Creates appearance with given gender, style, colours.
     *
     * @param gender returns gender
     * @param style returns style
     * @param colours returns colours
     */
    public PlayerAppearance(PlayerGender gender, int[] style, int[] colours) {
        this.gender = gender;
        this.style = style;
        this.colours = colours;
    }


    public PlayerGender getGender() {
        return gender;
    }

    public int[] getStyle() {
        return style;
    }

    public int[] getColours() {
        return colours;
    }
}
