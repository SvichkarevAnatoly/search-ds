package ru.mail.polis.hashtable;

class Hash {
    static <E> int h1(E value) {
        if (value instanceof String) {
            return StringHash.h1((String) value);
        } else {
            throw new UnsupportedOperationException(
                    "Support hash only for String now");
        }
    }

    static <E> int h2(E value, int m) {
        if (value instanceof String) {
            return StringHash.h2((String) value, m);
        } else {
            throw new UnsupportedOperationException(
                    "Support hash only for String now");
        }
    }
}
