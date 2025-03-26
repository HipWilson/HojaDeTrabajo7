package edu.uvg.util;

public class BinarySearchTree<E extends Comparable<E>> {
    private Node<E> root;

    private static class Node<E> {
        E data;
        Node<E> left;
        Node<E> right;

        Node(E data) {
            this.data = data;
            left = null;
            right = null;
        }
    }

    public BinarySearchTree() {
        root = null;
    }

    public void insert(E element) {
        root = insertRecursive(root, element);
    }

    private Node<E> insertRecursive(Node<E> current, E element) {
        if (current == null) {
            return new Node<>(element);
        }

        if (element.compareTo(current.data) < 0) {
            current.left = insertRecursive(current.left, element);
        } else if (element.compareTo(current.data) > 0) {
            current.right = insertRecursive(current.right, element);
        }

        return current;
    }

    public E search(E key) {
        return searchRecursive(root, key);
    }

    private E searchRecursive(Node<E> current, E key) {
        if (current == null) {
            return null;
        }

        int comparison = key.compareTo(current.data);

        if (comparison == 0) {
            return current.data;
        }

        if (comparison < 0) {
            return searchRecursive(current.left, key);
        }

        return searchRecursive(current.right, key);
    }
}
