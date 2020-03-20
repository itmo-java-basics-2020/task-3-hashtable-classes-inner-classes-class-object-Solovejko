package ru.itmo.java;

import java.lang.annotation.ElementType;
import java.util.Arrays;

public class HashTable {
    private final static int INITIAL_CAPACITY = 10;
    private double loadFactor;
    private int size;
    private Entry[] elements;
    private boolean[] delete;
    private int length;

    public HashTable() {
        this(INITIAL_CAPACITY);
    }

    public HashTable(int size) {
        this(size, 0.5);
    }

    public HashTable(int size, double loadFactor) {
        this.elements = new Entry[size];
        this.length = size;
        this.loadFactor = loadFactor;
        this.delete = new boolean[size];
    }

    public Entry getElement(int ind){
        return elements[ind];
    }

    public Object put(Object key, Object value) {
        int hc = Math.abs(key.hashCode() % length);

        while (delete[hc] || elements[hc] != null && !elements[hc].key.equals(key)) {
            hc = (hc + 1) % length;
        }

        if (elements[hc] == null) {
            hc = Math.abs(key.hashCode() % length);

            while (elements[hc] != null) {
                hc = (hc + 1) % length;
            }

            elements[hc] = new Entry(key, value);
            delete[hc] = false;
            size++;

            return null;
        }

        Object outValue = elements[hc].value;
        elements[hc] = new Entry(key, value);
        delete[hc] = false;

        if ((double) size / length > loadFactor) {
            IncreaseCapacity();
        }

        return outValue;
    }

    public Object get(Object key) {
        int hc = Math.abs(key.hashCode() % length);

        while (delete[hc] || elements[hc] != null && !elements[hc].key.equals(key)) {
            hc = (hc + 1) % length;
        }

        if (elements[hc] != null) {
            return elements[hc].value;
        } else {
            return null;
        }
    }

    public Object remove(Object key) {
        int hc = Math.abs(key.hashCode() % length);

        while (delete[hc] || elements[hc] != null && !elements[hc].key.equals(key)) {
            hc = (hc + 1) % length;
        }

        if (elements[hc] == null) {
            return null;
        } else {
            Object outValue = elements[hc].value;
            elements[hc] = null;
            delete[hc] = true;
            size--;
            return outValue;
        }
    }

    public int size() {
        return size;
    }


    private void IncreaseCapacity() {
        Entry[] newElements = new Entry[length * 2];
        delete = new boolean[length * 2];

        for (int i = 0; i < length; i++) {
            if (elements[i] == null) {
                continue;
            }

            int hс = Math.abs(elements[i].key.hashCode() % (length * 2));

            while (newElements[hс] != null) {
                hс = (hс + 1) % (length * 2);
            }

            newElements[hс] = elements[i];
        }

        elements = newElements;
        length *= 2;
    }

    private class Entry {
        private final Object key,
                             value;

        public Object getKey(){
            return key;
        }

        public Object getValue(){
            return value;
        }

        public Entry(Object key, Object value) {
            this.key = key;
            this.value = value;
        }
    }
}