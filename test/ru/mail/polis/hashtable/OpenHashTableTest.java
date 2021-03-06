package ru.mail.polis.hashtable;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.expectThrows;

public class OpenHashTableTest {
    private static final String SOME_VALUE = "a";
    private static final String COLLISION_FOR_SOME_VALUE = "i";
    private static final String ANOTHER_VALUE = "b";

    @Test
    void create() {
        final OpenHashTable<String> table = new OpenHashTable<>();

        assertThat(table.isEmpty(), is(true));
        assertThat(table.size(), is(0));
        assertThat(table.contains(SOME_VALUE), is(false));
    }

    @Test
    void size() {
        final OpenHashTable<String> table = new OpenHashTable<>();
        assertThat(table.size(), is(0));
        assertThat(table.add(SOME_VALUE), is(true));
        assertThat(table.size(), is(1));
    }

    @Test
    void sizeAfterRemove() {
        final OpenHashTable<String> table = new OpenHashTable<>();
        assertThat(table.add(SOME_VALUE), is(true));
        assertThat(table.size(), is(1));
        assertThat(table.remove(SOME_VALUE), is(true));
        assertThat(table.size(), is(0));
    }

    @Test
    void isEmpty() {
        final OpenHashTable<String> table = new OpenHashTable<>();
        assertThat(table.isEmpty(), is(true));
        assertThat(table.add(SOME_VALUE), is(true));
        assertThat(table.isEmpty(), is(false));
    }

    @Test
    void add() {
        final OpenHashTable<String> table = new OpenHashTable<>();
        assertThat(table.add(SOME_VALUE), is(true));
    }

    @Test
    void addNullValue() {
        final OpenHashTable<String> table = new OpenHashTable<>();
        Throwable exception = expectThrows(NullPointerException.class,
                () -> table.add(null));
        assertThat(exception.getMessage(), is("value is null"));
    }

    @Test
    void addTwoValues() {
        final OpenHashTable<String> table = new OpenHashTable<>();
        assertThat(table.add(SOME_VALUE), is(true));
        assertThat(table.add(ANOTHER_VALUE), is(true));
    }

    @Test
    void contains() {
        final OpenHashTable<String> table = new OpenHashTable<>();
        assertThat(table.contains(SOME_VALUE), is(false));
        assertThat(table.add(SOME_VALUE), is(true));
        assertThat(table.contains(SOME_VALUE), is(true));
    }

    @Test
    void containsAnotherValue() {
        final OpenHashTable<String> table = new OpenHashTable<>();
        assertThat(table.add(SOME_VALUE), is(true));
        assertThat(table.contains(ANOTHER_VALUE), is(false));
    }

    @Test
    void containsLookSecondPosition() {
        final OpenHashTable<String> table = new OpenHashTable<>();
        assertThat(table.add(SOME_VALUE), is(true));
        assertThat(table.contains(SOME_VALUE), is(true));
        assertThat(table.add(COLLISION_FOR_SOME_VALUE), is(true));
        assertThat(table.contains(SOME_VALUE), is(true));
        assertThat(table.contains(COLLISION_FOR_SOME_VALUE), is(true));
    }

    @Test
    void containsNullValue() {
        final OpenHashTable<String> table = new OpenHashTable<>();
        Throwable exception = expectThrows(NullPointerException.class,
                () -> table.contains(null));
        assertThat(exception.getMessage(), is("value is null"));
    }

    @Test
    void remove() {
        final OpenHashTable<String> table = new OpenHashTable<>();
        assertThat(table.add(SOME_VALUE), is(true));
        assertThat(table.remove(SOME_VALUE), is(true));
    }

    @Test
    void removeNullValue() {
        final OpenHashTable<String> table = new OpenHashTable<>();
        Throwable exception = expectThrows(NullPointerException.class,
                () -> table.remove(null));
        assertThat(exception.getMessage(), is("value is null"));
    }

    @Test
    void removeAnotherValue() {
        final OpenHashTable<String> table = new OpenHashTable<>();
        assertThat(table.add(SOME_VALUE), is(true));
        assertThat(table.remove(ANOTHER_VALUE), is(false));
    }

    @Test
    void removeLookSecondPosition() {
        final OpenHashTable<String> table = new OpenHashTable<>();
        assertThat(table.add(SOME_VALUE), is(true));
        assertThat(table.add(COLLISION_FOR_SOME_VALUE), is(true));
        assertThat(table.remove(COLLISION_FOR_SOME_VALUE), is(true));
        assertThat(table.remove(COLLISION_FOR_SOME_VALUE), is(false));
    }

    @Test
    void toStringEmptyHash() {
        final OpenHashTable<String> table = new OpenHashTable<>();
        assertThat(table.toString(), is(
                "OpenHashTable{\n"
                        + "\t0 free\n"
                        + "\t1 free\n"
                        + "\t2 free\n"
                        + "\t3 free\n"
                        + "\t4 free\n"
                        + "\t5 free\n"
                        + "\t6 free\n"
                        + "\t7 free\n"
                        + "}"
        ));
    }

    @Test
    void toStringAdd() {
        final OpenHashTable<String> table = new OpenHashTable<>();
        assertThat(table.add(SOME_VALUE), is(true));
        assertThat(table.toString(), is(
                "OpenHashTable{\n"
                        + "\t0 free\n"
                        + "\t1 " + SOME_VALUE + '\n'
                        + "\t2 free\n"
                        + "\t3 free\n"
                        + "\t4 free\n"
                        + "\t5 free\n"
                        + "\t6 free\n"
                        + "\t7 free\n"
                        + "}"
        ));
    }

    @Test
    void toStringRemove() {
        final OpenHashTable<String> table = new OpenHashTable<>();
        assertThat(table.add(SOME_VALUE), is(true));
        assertThat(table.remove(SOME_VALUE), is(true));
        assertThat(table.toString(), is(
                "OpenHashTable{\n"
                        + "\t0 free\n"
                        + "\t1 delete\n"
                        + "\t2 free\n"
                        + "\t3 free\n"
                        + "\t4 free\n"
                        + "\t5 free\n"
                        + "\t6 free\n"
                        + "\t7 free\n"
                        + "}"
        ));
    }

    @Test
    void resize() {
        final OpenHashTable<String> table = new OpenHashTable<>();
        for (int i = 0; i < 4; i++) {
            final char charValue= (char) ('a' + i);
            assertThat(table.add(String.valueOf(charValue)), is(true));
        }
        assertThat(table.toString(), is(
                "OpenHashTable{\n"
                        + "\t0 free\n"
                        + "\t1 a\n"
                        + "\t2 b\n"
                        + "\t3 c\n"
                        + "\t4 d\n"
                        + "\t5 free\n"
                        + "\t6 free\n"
                        + "\t7 free\n"
                        + "\t8 free\n"
                        + "\t9 free\n"
                        + "\t10 free\n"
                        + "\t11 free\n"
                        + "\t12 free\n"
                        + "\t13 free\n"
                        + "\t14 free\n"
                        + "\t15 free\n"
                        + "}"
        ));
    }

    @Test
    void duplicates() {
        final OpenHashTable<String> table = new OpenHashTable<>();
        assertThat(table.add(SOME_VALUE), is(true));
        assertThat(table.add(SOME_VALUE), is(false));
    }

    @Test
    void add3andRemoveAll() {
        final int numberOfValues = 3;

        final OpenHashTable<String> table = new OpenHashTable<>();
        for (int i = 0; i < numberOfValues; i++) {
            final char charValue= (char) ('a' + i);
            assertThat(table.add(String.valueOf(charValue)), is(true));
        }
        assertThat(table.size(), is(numberOfValues));
        for (int i = 0; i < numberOfValues; i++) {
            final char charValue= (char) ('a' + i);
            assertThat(table.remove(String.valueOf(charValue)), is(true));
        }
        assertThat(table.size(), is(0));
        assertThat(table.isEmpty(), is(true));
    }

    @Test
    void add3RandomAndRemoveAll() {
        final int numberOfValues = 3;
        assertFullLifeCycle(numberOfValues);
    }

    @Test
    void add10RandomAndRemoveAll() {
        final int numberOfValues = 10;
        assertFullLifeCycle(numberOfValues);
    }

    @Test
    void add20RandomAndRemoveAll() {
        final int numberOfValues = 20;
        assertFullLifeCycle(numberOfValues);
    }

    @Test
    void add100RandomAndRemoveAll() {
        final int numberOfValues = 100;
        assertFullLifeCycle(numberOfValues);
    }

    private void assertFullLifeCycle(int numberOfValues) {
        final RandomString random = new RandomString(0);

        final OpenHashTable<String> table = new OpenHashTable<>();
        final ArrayList<String> values = new ArrayList<>(numberOfValues);
        for (int i = 0; i < numberOfValues; i++) {
            final String value = random.get(10);
            values.add(value);
            assertThat(table.add(value), is(true));
        }

        assertThat(table.size(), is(numberOfValues));

        for (String value : values) {
            assertThat(table.contains(value), is(true));
        }

        for (String value : values) {
            assertThat(table.remove(value), is(true));
        }

        for (String value : values) {
            assertThat(table.contains(value), is(false));
        }

        assertThat(table.size(), is(0));
        assertThat(table.isEmpty(), is(true));
    }

    static class RandomString{
        private static final char[] CHARSET_AZ =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase().toCharArray();

        private Random random;

        RandomString(long seed) {
            random = new Random(seed);
        }

        String get(int length) {
            char[] result = new char[length];
            for (int i = 0; i < result.length; i++) {
                int randomCharIndex = random.nextInt(CHARSET_AZ.length);
                result[i] = CHARSET_AZ[randomCharIndex];
            }
            return new String(result);
        }
    }
}
