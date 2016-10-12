package com.gie.game;

import com.gie.game.entity.EntityCollection;

/**
 * Created by Adam on 12/10/2016.
 */
public class World {

    private static EntityCollection entity = new EntityCollection();

    public static EntityCollection getEntity() {
        return entity;
    }

}
