package ru.mail.polis.tree.avl;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.expectThrows;

public class AVLTreeTest {
    private static final int SOME_NODE = 5;

    @Test
    void emptyTree() {
        final AVLTree<Integer> tree = new AVLTree<>();

        assertThat(tree.isEmpty(), is(true));
        assertThat(tree.size(), is(0));
        assertThat(tree.toString(), is("AVL{null}"));

        assertThat(tree.remove(SOME_NODE), is(false));
        assertThat(tree.contains(SOME_NODE), is(false));

        Throwable exception = expectThrows(NoSuchElementException.class, tree::first);
        assertThat(exception.getMessage(), is("set is empty, no first element"));

        exception = expectThrows(NoSuchElementException.class, tree::last);
        assertThat(exception.getMessage(), is("set is empty, no last element"));
    }

    @Test
    void addNode() {
        final AVLTree<Integer> tree = new AVLTree<>();
        tree.add(SOME_NODE);

        assertThat(tree.isEmpty(), is(false));
        assertThat(tree.size(), is(1));
        assertThat(tree.toString(), is("AVL{N{d=" + SOME_NODE + "}}"));

        assertThat(tree.contains(SOME_NODE), is(true));
        assertThat(tree.first(), is(SOME_NODE));
        assertThat(tree.last(), is(SOME_NODE));

        // assertThat(tree.remove(SOME_NODE), is(true));
    }
}
