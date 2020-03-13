package ru.itmo.java;

import java.lang.annotation.ElementType;
import java.util.Arrays;
import java.util.EventListener;

public class HashTable {
    private final static int INITIAL_CAPACITY = 10;
    private double loadFactor;
    private int Size;
    private Entry[] Elements;

    public HashTable() {
        this(INITIAL_CAPACITY);
    }

    public HashTable(int size) {
        this(size, 0.5);
    }

    public HashTable(int size, double loadFactor) {
        Elements = new Entry[size];
        this.loadFactor = loadFactor;
    }

    public Object put(Object key, Object value) {
        int hs = Math.abs(key.hashCode() % Elements.length);

        Object OutValue = null;

        for (int i = 0; i < Elements.length && OutValue == null; i++, hs = (hs + 1) % Elements.length) {
            if ((Elements[hs] != null) && (Elements[hs].key.equals(key))) {
                OutValue = Elements[hs].value;
                Elements[hs++] = new Entry(key, value);
            }
        }

        if (OutValue != null)
            return OutValue;

        while (Elements[hs] != null) {
            hs = (hs + 1) % Elements.length;
        }

        Elements[hs] = new Entry(key, value);
        Size++;

        if ((double) Size / Elements.length > loadFactor)
            IncreaseCapacity();

        return null;
    }

    public Object get(Object key) {
        Object OutValue = null;

        for (int i = 0, hs = Math.abs(key.hashCode() % Elements.length); i < Elements.length && OutValue == null; i++, hs = (hs + 1) % Elements.length) {
            if ((Elements[hs] != null) && (Elements[hs].key.equals(key))) {
                OutValue = Elements[hs].value;
            }
        }

        return OutValue;
    }

    public Object remove(Object key) {
        Object OutValue = null;

        for (int i = 0, hs = Math.abs(key.hashCode() % Elements.length); i < Elements.length && OutValue == null; i++, hs = (hs + 1) % Elements.length) {
            if ((Elements[hs] != null) && (Elements[hs].key.equals(key))) {
                OutValue = Elements[hs].value;
                Elements[hs] = null;
                Size--;
            }
        }

        return OutValue;
    }

    public int size() {
        return Size;
    }


    private void IncreaseCapacity() {
        Entry[] newElements = new Entry[Elements.length * 2];

        for (int i = 0; i < Elements.length; i++) {
            if (Elements[i] == null)
                continue;

            int hs = Math.abs(Elements[i].key.hashCode() % newElements.length);

            while (newElements[hs] != null)
                hs = (hs + 1) % newElements.length;

            newElements[hs] = Elements[i];
        }

        Elements = newElements;
    }


    @Override
    public String toString() {

        Object[] m = new Object[Elements.length];

        for (int i = 0; i < Elements.length; i++)
            if (Elements[i] != null)
                m[i] = Elements[i].value;

        return Arrays.toString(m);
    }


    private class Entry {
        Object key, value;

        public Entry(Object key, Object value) {
            this.key = key;
            this.value = value;
        }
    }
}

