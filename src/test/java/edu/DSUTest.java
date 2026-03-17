package edu;

import edu.dsu.DSU;
import edu.dsu.GraphComponents;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para DSU y GraphComponents con JUnit 5.
 * Verifica correctitud de find, union, connected y countComponents.
 */
class DSUTest {

    @Test
    void testInicialmenteDesconectados() {
        DSU dsu = new DSU(5);
        assertEquals(5, dsu.components());
        for (int i = 0; i < 5; i++)
            for (int j = i + 1; j < 5; j++)
                assertFalse(dsu.connected(i, j));
    }

    @Test
    void testUnionConecta() {
        DSU dsu = new DSU(5);
        dsu.union(0, 1);
        assertTrue(dsu.connected(0, 1));
        assertEquals(4, dsu.components());
    }

    @Test
    void testUnionTransitiva() {
        DSU dsu = new DSU(5);
        dsu.union(0, 1);
        dsu.union(1, 2);
        assertTrue(dsu.connected(0, 2));
        assertEquals(3, dsu.components());
    }

    @Test
    void testUnionMismoConjunto() {
        DSU dsu = new DSU(5);
        dsu.union(0, 1);
        assertFalse(dsu.union(0, 1)); // ya estaban unidos
        assertEquals(4, dsu.components());
    }

    @Test
    void testCountComponentsSinAristas() {
        assertEquals(5, GraphComponents.countComponents(5, new int[][] {}));
    }

    @Test
    void testCountComponentsGrafoCompleto() {
        int[][] edges = { { 0, 1 }, { 1, 2 }, { 2, 3 }, { 3, 4 } };
        assertEquals(1, GraphComponents.countComponents(5, edges));
    }

    @Test
    void testCountComponents10Vertices() {
        // Grafo conocido: 3 componentes
        // {0,1,2}, {3,4}, {5,6,7,8,9}
        int[][] edges = {
                { 0, 1 }, { 1, 2 },
                { 3, 4 },
                { 5, 6 }, { 6, 7 }, { 7, 8 }, { 8, 9 }
        };
        assertEquals(3, GraphComponents.countComponents(10, edges));
    }

    @Test
    void testCreatesCycle() {
        DSU dsu = new DSU(4);
        assertFalse(GraphComponents.createsCycle(dsu, 0, 1));
        assertFalse(GraphComponents.createsCycle(dsu, 1, 2));
        assertTrue(GraphComponents.createsCycle(dsu, 0, 2)); // ciclo
    }
}