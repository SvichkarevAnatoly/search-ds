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

        // assertThat(tree.delete(SOME_NODE), is(true));
    }

    @Test
    public void addTwoSame() throws Exception {
        final AVLTree<Integer> tree = new AVLTree<>();

        assertThat(tree.add(SOME_NODE), is(true));
        assertThat(tree.add(SOME_NODE), is(false));
    }

    @Test
    public void addTwoDifferent() throws Exception {
        final AVLTree<Integer> tree = new AVLTree<>();

        assertThat(tree.add(SOME_NODE), is(true));
        assertThat(tree.add(100), is(true));
    }

    @Test
    public void add3AndRotate() throws Exception {
        final AVLTree<Integer> tree = new AVLTree<>();
        assertThat(tree.add(3), is(true));
        assertThat(tree.add(2), is(true));
        assertThat(tree.add(1), is(true));

        final String treeString = AvlBuilder.serialize(tree);
        final String expectedTree = "2 1 -1 -1 3 -1 -1";
        assertThat(treeString, is(expectedTree));
    }

    @Test
    public void add4NodesAndRotate() throws Exception {
        final AVLTree<Integer> tree = new AVLTree<>();
        assertThat(tree.add(4), is(true));
        assertThat(tree.add(3), is(true));
        assertThat(tree.add(2), is(true));
        assertThat(tree.add(1), is(true));

        final String treeString = AvlBuilder.serialize(tree);
        final String expectedTree = "3 2 1 -1 -1 -1 4 -1 -1";
        assertThat(treeString, is(expectedTree));
    }

    @Test
    public void add4andFind() throws Exception {
        final AVLTree<Integer> tree = new AVLTree<>();
        tree.add(4);
        tree.add(3);
        tree.add(2);
        tree.add(1);

        for (int i = 1; i <= 4; i++) {
            assertThat(tree.contains(i), is(true));
        }
        assertThat(tree.contains(0), is(false));
        assertThat(tree.contains(5), is(false));
    }

    @Test
    public void deleteOneNodeTree() throws Exception {
        final AVLTree<Integer> tree = new AVLTree<>();
        tree.add(SOME_NODE);
        assertThat(tree.contains(SOME_NODE), is(true));
        assertThat(tree.remove(SOME_NODE), is(true));
        assertThat(tree.contains(SOME_NODE), is(false));
        assertThat(tree.remove(SOME_NODE), is(false));
    }

    @Test
    public void deleteTwoNodeTree() throws Exception {
        String treeString = "1 -1 2 -1 -1";
        final AVLTree<Integer> tree = AvlBuilder.deserialize(treeString);

        assertThat(tree.contains(2), is(true));
        assertThat(tree.remove(2), is(true));
        assertThat(tree.contains(2), is(false));
        assertThat(tree.contains(1), is(true));
    }

    @Test
    public void deleteRootInBigTree() throws Exception {
        final String treeString = "10 5 3 -1 -1 7 -1 -1 20 15 12 -1 -1 17 -1 -1 25 -1 -1";
        final AVLTree<Integer> tree = AvlBuilder.deserialize(treeString);

        assertThat(tree.remove(10), is(true));

        final String expectedTreeString= "12 5 3 -1 -1 7 -1 -1 20 15 -1 17 -1 -1 25 -1 -1";
        assertThat(AvlBuilder.serialize(tree), is(expectedTreeString));
    }

    @Test
    public void deleteRootLeftChildInBigTree() throws Exception {
        final AVLTree<Integer> tree = new AVLTree<>();
        for (int i = 2; i <= 10; i++) {
            assertThat(tree.add(i), is(true));
        }

        assertThat(tree.contains(7), is(true));
        assertThat(tree.remove(7), is(true));
        assertThat(tree.contains(7), is(false));

        assertThat(AvlBuilder.serialize(tree), is("5 3 2 -1 -1 4 -1 -1 8 6 -1 -1 9 -1 10 -1 -1"));
    }

    @Test
    public void add10Delete10() throws Exception {
        final AVLTree<Integer> tree = new AVLTree<>();
        for (int i = 1; i <= 10; i++) {
            assertThat(tree.add(i), is(true));
        }

        for (int i = 1; i <= 10; i++) {
            assertThat(tree.contains(i), is(true));
        }

        for (int i = 1; i <= 10; i++) {
            assertThat(tree.remove(i), is(true));
            assertThat(tree.contains(i), is(false));
        }

        assertThat(AvlBuilder.serialize(tree), is("-1"));
    }
}
