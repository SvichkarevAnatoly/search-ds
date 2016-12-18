package ru.mail.polis.hashtable;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class OpenHashTableTest {
    private static final String SOME_VALUE = "a";

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
    void isEmpty() {
        final OpenHashTable<String> table = new OpenHashTable<>();
        assertThat(table.isEmpty(), is(true));
        assertThat(table.add(SOME_VALUE), is(true));
        assertThat(table.isEmpty(), is(false));
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
}
