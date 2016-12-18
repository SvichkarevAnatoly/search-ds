package ru.mail.polis.hashtable;

import ru.mail.polis.ISet;

import java.util.Comparator;

public class OpenHashTable<E extends Comparable<E>> implements ISet<E> {
    private static final int INITIAL_CAPACITY = 8;
    private static final float LOAD_FACTOR = 0.5f; // TODO: 18.12.16

    private HashArray<E> arr = new HashArray<>(INITIAL_CAPACITY);
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
        checkValue(value);

        final int h1 = Hash.h1(value);
        final int h2 = Hash.h2(value);
        int h = (h1) % arr.capacity;
        for (int i = 0; i < arr.capacity; i++) {
            final E v2 = arr.get(h);
            if (v2 == null) {
                return false;
            } else {
                final int cmp = compare(value, v2);
                if (cmp == 0) {
                    return true;
                }
                h = (h + h2) % arr.capacity;
            }
        }
        return false;
    }

    @Override
    public boolean add(E value) {
        checkValue(value);

        final int h1 = Hash.h1(value);
        final int h2 = Hash.h2(value);
        int h = (h1) % arr.capacity;
        for (int i = 0; i < arr.capacity; i++) {
            if (arr.isFree(h)) {
                arr.set(value, h);
                size++;
                resize();
                return true;
            }
            h = (h + h2) % arr.capacity;
        }
        return false;
    }

    @Override
    public boolean remove(E value) {
        checkValue(value);

        final int h1 = Hash.h1(value);
        final int h2 = Hash.h2(value);
        int h = (h1) % arr.capacity;
        for (int i = 0; i < arr.capacity; i++) {
            final E v2 = arr.get(h);
            if (v2 == null) {
                return false;
            } else {
                final int cmp = compare(value, v2);
                if (cmp == 0) {
                    arr.delete(h);
                    size--;
                    return true;
                }
                h = (h + h2) % arr.capacity;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OpenHashTable{\n");
        for (int i = 0; i < arr.capacity; i++) {
            sb.append('\t').append(i).append(' ');
            if (arr.isFree(i)) {
                sb.append("free");
            } else if (!arr.isDelete(i)) {
                final E value = arr.get(i);
                sb.append(value);
            } else {
                sb.append("delete");
            }
            sb.append('\n');
        }
        sb.append("}");
        return sb.toString();
    }

    private int compare(E v1, E v2) {
        return comparator == null ?
                v1.compareTo(v2) :
                comparator.compare(v1, v2);
    }

    private void checkValue(E value) {
        if (value == null) {
            throw new NullPointerException("value is null");
        }
    }

    private void resize() {
        if (size < arr.capacity * LOAD_FACTOR) {
            return;
        }

        final HashArray<E> oldArr = arr;
        size = 0;
        arr = new HashArray<>(2 * INITIAL_CAPACITY);
        for (int i = 0; i < oldArr.capacity; i++) {
            if (!oldArr.isFree(i)) {
                final E value = oldArr.get(i);
                add(value);
            }
        }
    }
}
