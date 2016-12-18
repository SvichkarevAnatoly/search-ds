package ru.mail.polis.tree.redblack;

import ru.mail.polis.ISortedSet;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class RedBlackTree<E extends Comparable<E>> implements ISortedSet<E> {
    private final Node nil = new Node(true);

    private Node root;
    private int size;
    private final Comparator<E> comparator;

    public RedBlackTree() {
        this.comparator = null;
    }

    public RedBlackTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    @Override
    public E first() {
        if (isEmpty()) {
            throw new NoSuchElementException("set is empty, no first element");
        }
        Node curr = root;
        while (curr.left != null) {
            curr = curr.left;
        }
        return curr.value;
    }

    @Override
    public E last() {
        if (isEmpty()) {
            throw new NoSuchElementException("set is empty, no last element");
        }
        Node curr = root;
        while (curr.right != null) {
            curr = curr.right;
        }
        return curr.value;
    }

    @Override
    public List<E> inorderTraverse() {
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public boolean contains(E value) {
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        if (root != null) {
            Node curr = root;
            while (!curr.isNil()) {
                int cmp = compare(curr.value, value);
                if (cmp == 0) {
                    return true;
                } else if (cmp < 0) {
                    curr = curr.right;
                } else {
                    curr = curr.left;
                }
            }
        }
        return false;
    }

    @Override
    public boolean add(E value) {
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        if (root == null){
            root = new Node(value, nil, true);
            size++;
            return true;
        }

        Node y = nil;
        Node x = root;
        while (!x.isNil()){
            y = x;
            final int cmp = compare(value, x.value);
            if (cmp < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        final Node z = new Node(value);
        z.p = y;
        if (y.isNil()){
            root = z;
        } else {
            final int cmp = compare(value, y.value);
            if (cmp < 0){
                y.left = z;
            } else {
                y.right = z;
            }
        }
        z.left = nil;
        z.right = nil;

        fixUp(z);

        return true;
    }

    @Override
    public boolean remove(E value) {
        return false;
    }

    private int compare(E v1, E v2) {
        return comparator == null ? v1.compareTo(v2) : comparator.compare(v1, v2);
    }

    private void fixUp(Node z) {
        // TODO: 18.12.16
    }

    class Node {
        E value;
        Node left, right, p;
        boolean isBlack;

        Node(E value) {
            this.value = value;
        }

        Node(boolean isBlack) {
            this.isBlack = isBlack;
        }

        Node(E value, Node parent, boolean isLeaf) {
            this.value = value;
            p = parent;
            if (isLeaf) {
                left = nil;
                right = nil;
            }
        }

        boolean isNil() {
            return value == null;
        }
    }
}
