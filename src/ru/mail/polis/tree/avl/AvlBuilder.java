package ru.mail.polis.tree.avl;

import java.util.Scanner;

public class AvlBuilder {
    private static final int EMPTY_MARKER = -1;

    public static String serialize(AVLTree<Integer> tree) {
        return serialize(tree.root);
    }

    public static AVLTree<Integer> deserialize(String treeString) {
        final Scanner scanner = new Scanner(treeString);
        final AVLTree<Integer> tree = new AVLTree<>();
        tree.root = deserialize(tree, scanner);
        return tree;
    }

    private static String serialize(AVLTree<Integer>.Node root) {
        final StringBuilder sb = new StringBuilder();
        serialize(root, sb);
        return sb.toString().trim();
    }

    private static void serialize(AVLTree<Integer>.Node node, StringBuilder sb) {
        if (node == null) {
            sb.append(EMPTY_MARKER).append(' ');
        } else {
            sb.append(node.value).append(' ');
            serialize(node.left, sb);
            serialize(node.right, sb);
        }
    }

    private static AVLTree<Integer>.Node deserialize(AVLTree<Integer> tree, Scanner scanner) {
        if (scanner.hasNextInt()) {
            final int value = scanner.nextInt();
            if (value == EMPTY_MARKER) {
                return null;
            } else {
                return tree.new Node(value,
                        deserialize(tree, scanner),
                        deserialize(tree, scanner));
            }
        }
        return null;
    }
}
