package ru.mail.polis.hashtable;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.expectThrows;

public class OpenHashTableTest {
    private static final String SOME_VALUE = "a";
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

    @Ignore // TODO: 18.12.16
    @Test
    void containsLookSecondPosition() {
        final OpenHashTable<String> table = new OpenHashTable<>();
        assertThat(table.contains(SOME_VALUE), is(false));
        assertThat(table.add(SOME_VALUE), is(true));
        assertThat(table.contains(SOME_VALUE), is(true));
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

    @Ignore // TODO: 18.12.16
    @Test
    void removeLookSecondPosition() {
        final OpenHashTable<String> table = new OpenHashTable<>();
        assertThat(table.contains(SOME_VALUE), is(false));
        assertThat(table.add(SOME_VALUE), is(true));
        assertThat(table.contains(SOME_VALUE), is(true));
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
    void toStringHashTableAdd() {
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
    void add8Chars() {
        final OpenHashTable<String> table = new OpenHashTable<>();
        for (int i = 0; i < 8; i++) {
            final char charValue= (char) ('a' + i);
            assertThat(table.add(String.valueOf(charValue)), is(true));
        }
        assertThat(table.toString(), is(
                "OpenHashTable{\n"
                        + "\t0 h\n"
                        + "\t1 a\n"
                        + "\t2 b\n"
                        + "\t3 c\n"
                        + "\t4 d\n"
                        + "\t5 e\n"
                        + "\t6 f\n"
                        + "\t7 g\n"
                        + "}"
        ));
    }

    @Test
    void add9Chars() {
        final OpenHashTable<String> table = new OpenHashTable<>();
        for (int i = 0; i < 9; i++) {
            final char charValue= (char) ('a' + i);
            assertThat(table.add(String.valueOf(charValue)), is(true));
        }
        assertThat(table.toString(), is(
                "OpenHashTable{\n"
                        + "\t0 h\n"
                        + "\t1 a\n"
                        + "\t2 b\n"
                        + "\t3 c\n"
                        + "\t4 d\n"
                        + "\t5 e\n"
                        + "\t6 f\n"
                        + "\t7 g\n"
                        + "}"
        ));
    }
}
