package ru.mail.polis.hashtable;

import ru.mail.polis.ISet;

import java.util.Comparator;

public class OpenHashTable<E extends Comparable<E>> implements ISet<E> {
    private static final int INITIAL_CAPACITY = 8;
    private static final float LOAD_FACTOR = 0.5f;

    private HashArray<E> array = new HashArray<>(INITIAL_CAPACITY);
    private int size;
    private Comparator<E> comparator;

    public OpenHashTable() {
        this(null);
    }

    public OpenHashTable(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(E value) {
        return false;
    }

    @Override
    public boolean add(E value) {
        final int h1 = Hash.h1(value);
        final int h2 = Hash.h2(value);
        int h = h1;
        for (int i = 0; i < array.capacity; i++) {
            if (array.isFree(i)) {
                array.set(value, h);
                return true;
            }
            h = (h + h2) % array.capacity;
        }
        return false;
    }

    @Override
    public boolean remove(E value) {
        return false;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OpenHashTable{\n");
        for (int i = 0; i < array.capacity; i++) {
            sb.append('\t').append(i).append(' ');
            if (array.isFree(i)) {
                sb.append("free");
            } else if (!array.isDelete(i)) {
                final E value = array.get(i);
                sb.append(value);
            } else {
                sb.append("delete");
            }
            sb.append('\n');
        }
        sb.append("}");
        return sb.toString();
    }
}
