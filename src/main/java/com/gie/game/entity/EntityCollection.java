package com.gie.game.entity;


import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Adam on 12/10/2016.
 */

public class EntityCollection<T extends Entity> implements Collection<T> {
    //TODO : Complete
    private Entity[] entity;

    public EntityCollection(int capacity) {
        entity = new Entity[capacity];
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
        for (int i = 0; i < capacity(); i++) {
            entity[i] = t;
            t.setIndex(i);
            return true;
        }
        return false;
    }

    public int capacity() {
        return entity.length;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }
}
