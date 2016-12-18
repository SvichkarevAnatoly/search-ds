package ru.mail.polis.hashtable;

class HashArray<E> {
    int capacity;

    private E[] elementArray;
    private boolean[] isDeleteArray;

    @SuppressWarnings("unchecked")
    HashArray(int capacity) {
        this.capacity = capacity;
        elementArray = (E[]) new Object[capacity];
        isDeleteArray = new boolean[capacity];
    }

    E get(int pos) {
        if (isDeleteArray[pos]) {
            return null;
        }
        return elementArray[pos];
    }

    void set(E value, int pos) {
        elementArray[pos] = value;
        isDeleteArray[pos] = false;
    }

    boolean isFree(int pos){
        return elementArray[pos] == null || isDeleteArray[pos];
    }

    void delete(int pos) {
        isDeleteArray[pos] = true;
    }

    boolean isDelete(int pos) {
        return isDeleteArray[pos];
    }

}
