package ru.mail.polis.tree.avl;

import org.apache.commons.lang3.mutable.MutableBoolean;
import ru.mail.polis.ISortedSet;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class AVLTree<E extends Comparable<E>> implements ISortedSet<E> {
    Node root;
    private int size;
    private final Comparator<E> comparator;

    public AVLTree() {
        this.comparator = null;
    }

    public AVLTree(Comparator<E> comparator) {
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
        List<E> list = new ArrayList<E>(size);
        inorderTraverse(root, list);
        return list;
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
            while (curr != null) {
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
        if (root == null) {
            root = new Node(value);
            size++;
            return true;
        } else {
            boolean isInserted = insert(root, value);
            root = balance(root);
            size += isInserted ? 1 : 0;
            return isInserted;
        }
    }

    @Override
    public boolean remove(E value) {
        final MutableBoolean isDeleted = new MutableBoolean(false);
        root = delete(root, value, isDeleted);
        return isDeleted.getValue();
    }

    @Override
    public String toString() {
        return "AVL{" + root + "}";
    }

    private int compare(E v1, E v2) {
        return comparator == null ? v1.compareTo(v2) : comparator.compare(v1, v2);
    }

    private void inorderTraverse(Node curr, List<E> list) {
        if (curr == null) {
            return;
        }
        inorderTraverse(curr.left, list);
        list.add(curr.value);
        inorderTraverse(curr.right, list);
    }

    // TODO: 17.12.16
    boolean insert(Node node, E value) {
        boolean isInserted;
        int cmp = compare(value, node.value);
        if (cmp == 0) {
            return false;
        } else if (cmp < 0) {
            if (node.left != null) {
                isInserted = insert(node.left, value);
                node.left = balance(node.left);
            } else {
                node.left = new Node(value);
                return true;
            }
        } else {
            if (node.right != null) {
                isInserted = insert(node.right, value);
                node.right = balance(node.right);
            } else {
                node.right = new Node(value);
                return true;
            }
        }
        return isInserted;
    }

    Node delete(Node p, E value, MutableBoolean isDeleted) {
        if (p == null) {
            return null;
        }

        int cmp = compare(value, p.value);
        if (cmp < 0) {
            p.left = delete(p.left, value, isDeleted);
        } else if (cmp > 0) {
            p.right = delete(p.right, value, isDeleted);
        } else {
            isDeleted.setTrue();
            Node q = p.left;
            Node r = p.right;
            if (r == null) {
                return q;
            }
            Node min = findMin(r);
            min.right = deleteMin(r);
            min.left = q;
            return balance(min);
        }
        return balance(p);
    }

    Node findMin(Node p) {
        if (p.left != null) {
            return findMin(p.left);
        }
        return p;
    }

    Node deleteMin(Node p) {
        if (p.left == null) {
            return p.right;
        }
        p.left = deleteMin(p.left);
        return balance(p);
    }

    Node balance(Node p) {
        p.fixHeight();
        if (p.balanceFactor() == 2) {
            if (p.right.balanceFactor() < 0) {
                p.right = rotateRight(p.right);
            }
            return rotateLeft(p);
        }
        if (p.balanceFactor() == -2) {
            if (p.left.balanceFactor() > 0) {
                p.left = rotateLeft(p.left);
            }
            return rotateRight(p);
        }
        return p;
    }

    Node rotateLeft(Node q) {
        final Node p = q.right;
        q.right = p.left;
        p.left = q;
        q.fixHeight();
        p.fixHeight();
        return p;
    }

    Node rotateRight(Node p) {
        final Node q = p.left;
        p.left = q.right;
        q.right = p;
        p.fixHeight();
        q.fixHeight();
        return q;
    }

    class Node {
        E value;
        Node left, right;
        int height = 1;

        Node(E value) {
            this.value = value;
        }

        Node(E value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
            fixHeight();
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("N{");
            sb.append("d=").append(value);
            if (left != null) {
                sb.append(", l=").append(left);
            }
            if (right != null) {
                sb.append(", r=").append(right);
            }
            sb.append('}');
            return sb.toString();
        }

        int balanceFactor() {
            final int hl = left != null ? left.height : 0;
            final int hr = right != null ? right.height : 0;
            return hr - hl;
        }

        void fixHeight() {
            final int hl = left != null ? left.height : 0;
            final int hr = right != null ? right.height : 0;
            height = Math.max(hl, hr) + 1;
        }
    }
}
