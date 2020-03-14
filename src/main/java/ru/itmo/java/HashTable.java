package ru.itmo.java;

import java.util.Arrays;

public class HashTable {
    private final static int INITIAL_CAPACITY = 10;
    private double loadFactor;
    private int size;
    private Entry[] elements;
    private boolean[] delete;

    public HashTable() {
        this(INITIAL_CAPACITY);
    }

    public HashTable(int size) {
        this(size, 0.5);
    }

    public HashTable(int size, double loadFactor) {
        this.elements = new Entry[size];
        this.loadFactor = loadFactor;
        this.delete = new boolean[size];
    }

    public Object put(Object key, Object value) {
        int hs = Math.abs(key.hashCode() % elements.length);

        while (delete[hs] || elements[hs] != null && !elements[hs].key.equals(key)) {
            hs = (hs + 1) % elements.length;
        }

        if (elements[hs] == null) {
            hs = Math.abs(key.hashCode() % elements.length);

            while (elements[hs] != null) {
                hs = (hs + 1) % elements.length;
            }

            elements[hs] = new Entry(key, value);
            delete[hs] = false;
            size++;

            return null;
        }

        Object OutValue = elements[hs].value;
        elements[hs] = new Entry(key, value);
        delete[hs] = false;

        if ((double) size / elements.length > loadFactor) {
            IncreaseCapacity();
        }

        return OutValue;
    }

    public Object get(Object key) {
        int hs = Math.abs(key.hashCode() % elements.length);

        while (delete[hs] || elements[hs] != null && !elements[hs].key.equals(key)) {
            hs = (hs + 1) % elements.length;
        }

        if (elements[hs] != null) {
            return elements[hs].value;
        } else {
            return null;
        }
    }

    public Object remove(Object key) {
        int hs = Math.abs(key.hashCode() % elements.length);

        while (delete[hs] || elements[hs] != null && !elements[hs].key.equals(key)) {
            hs = (hs + 1) % elements.length;
        }

        if (elements[hs] == null) {
            return null;
        } else {
            Object OutValue = elements[hs].value;
            elements[hs] = null;
            delete[hs] = true;
            size--;
            return OutValue;
        }
    }

    public int size() {
        return size;
    }


    private void IncreaseCapacity() {
        Entry[] newElements = new Entry[elements.length * 2];
        delete = new boolean[elements.length * 2];

        for (int i = 0; i < elements.length; i++) {
            if (elements[i] == null) {
                continue;
            }

            int hs = Math.abs(elements[i].key.hashCode() % newElements.length);

            while (newElements[hs] != null) {
                hs = (hs + 1) % newElements.length;
            }

            newElements[hs] = elements[i];
        }

        elements = newElements;
    }

    private class Entry {
        Object key, value;

        public Entry(Object key, Object value) {
            this.key = key;
            this.value = value;
        }
    }
}