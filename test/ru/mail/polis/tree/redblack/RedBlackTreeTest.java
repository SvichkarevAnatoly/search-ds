package ru.mail.polis.tree.redblack;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.expectThrows;

public class RedBlackTreeTest {
    private static final int SOME_NODE = 5;
    private static final int ANOTHER_NODE = 3;

    @Test
    void create() {
        final RedBlackTree<Integer> tree = new RedBlackTree<>();

        assertThat(tree.isEmpty(), is(true));
        assertThat(tree.size(), is(0));

        assertThat(tree.remove(SOME_NODE), is(false));
        assertThat(tree.contains(SOME_NODE), is(false));

        Throwable exception = expectThrows(NoSuchElementException.class, tree::first);
        assertThat(exception.getMessage(), is("set is empty, no first element"));

        exception = expectThrows(NoSuchElementException.class, tree::last);
        assertThat(exception.getMessage(), is("set is empty, no last element"));
    }

    @Test
    void addRoot() {
        final RedBlackTree<Integer> tree = new RedBlackTree<>();

        assertThat(tree.add(SOME_NODE), is(true));
    }

    @Test
    void addSecond() {
        final RedBlackTree<Integer> tree = new RedBlackTree<>();

        assertThat(tree.add(SOME_NODE), is(true));
        assertThat(tree.add(ANOTHER_NODE), is(true));
    }

    @Test
    void contains() {
        final RedBlackTree<Integer> tree = new RedBlackTree<>();

        assertThat(tree.contains(SOME_NODE), is(false));
        assertThat(tree.add(SOME_NODE), is(true));
        assertThat(tree.contains(SOME_NODE), is(true));
    }

    @Test
    void containsNotEmptyTree() {
        final RedBlackTree<Integer> tree = new RedBlackTree<>();

        assertThat(tree.add(SOME_NODE), is(true));
        assertThat(tree.contains(ANOTHER_NODE), is(false));
    }
}
