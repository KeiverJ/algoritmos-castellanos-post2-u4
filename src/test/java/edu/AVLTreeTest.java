package edu;

import edu.trees.AVLTree;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para AVLTree con JUnit 5.
 * Verifica correctitud de inserción, búsqueda y propiedad AVL.
 */
class AVLTreeTest {

    @Test
    void testInsertYContains() {
        AVLTree<Integer> tree = new AVLTree<>();
        tree.insert(5);
        tree.insert(3);
        tree.insert(7);
        assertTrue(tree.contains(5));
        assertTrue(tree.contains(3));
        assertTrue(tree.contains(7));
        assertFalse(tree.contains(1));
        assertFalse(tree.contains(9));
    }

    @Test
    void testAlturaBalanceada10000Elementos() {
        AVLTree<Integer> tree = new AVLTree<>();
        Random rng = new Random(42);
        for (int i = 0; i < 10_000; i++) {
            tree.insert(rng.nextInt(100_000));
        }
        double maxHeight = 1.44 * Math.log(10_001) / Math.log(2);
        assertTrue(tree.height() <= maxHeight,
                "Altura " + tree.height() + " supera el máximo AVL " + maxHeight);
    }

    @Test
    void testContainsTodosLosInsertados() {
        AVLTree<Integer> tree = new AVLTree<>();
        for (int i = 0; i < 1000; i++)
            tree.insert(i);
        for (int i = 0; i < 1000; i++)
            assertTrue(tree.contains(i), "No encontró: " + i);
    }

    @Test
    void testNoContainsNoInsertados() {
        AVLTree<Integer> tree = new AVLTree<>();
        for (int i = 0; i < 100; i++)
            tree.insert(i * 2);
        for (int i = 0; i < 100; i++)
            assertFalse(tree.contains(i * 2 + 1),
                    "Falso positivo para: " + (i * 2 + 1));
    }

    @Test
    void testArbolVacio() {
        AVLTree<Integer> tree = new AVLTree<>();
        assertFalse(tree.contains(1));
        assertEquals(0, tree.height());
        assertEquals(0, tree.size());
    }

    @Test
    void testInsercionSecuencialNoDegenera() {
        AVLTree<Integer> tree = new AVLTree<>();
        // Inserción secuencial degeneraría un BST sin balanceo
        for (int i = 1; i <= 10_000; i++)
            tree.insert(i);
        double maxHeight = 1.44 * Math.log(10_001) / Math.log(2);
        assertTrue(tree.height() <= maxHeight,
                "Inserción secuencial degeneró el árbol: altura " + tree.height());
    }
}