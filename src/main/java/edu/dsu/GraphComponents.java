package edu.dsu;

/**
 * Utilidades para análisis de grafos no dirigidos usando DSU.
 *
 * <p>
 * Provee métodos para detección de componentes conexas y ciclos
 * en grafos no dirigidos, aprovechando la eficiencia amortizada O(α(n))
 * del DSU con path splitting y union by size.
 *
 * <p>
 * Ventaja sobre BFS/DFS: el DSU procesa cada arista en O(α(n))
 * sin necesidad de construir listas de adyacencia ni mantener
 * arreglos de visitados, reduciendo el uso de memoria a O(n).
 */
public class GraphComponents {

    /**
     * Cuenta el número de componentes conexas de un grafo no dirigido.
     *
     * <p>
     * Complejidad: O(m·α(n)) donde m = número de aristas y n = vértices.
     * En la práctica equivalente a O(m) por la constante α(n).
     *
     * <p>
     * Comparación con BFS/DFS:
     * <ul>
     * <li>BFS/DFS: O(n + m) tiempo, O(n + m) espacio (lista de adyacencia)</li>
     * <li>DSU: O(m·α(n)) tiempo, O(n) espacio (solo arreglos parent y size)</li>
     * </ul>
     *
     * @param n     número de vértices (0..n-1)
     * @param edges lista de aristas, cada arista es int[]{u, v}
     * @return número de componentes conexas
     * @pre n > 0, todos los vértices en edges están en [0, n-1]
     * @post retorna n si edges está vacío (n componentes aisladas)
     */
    public static int countComponents(int n, int[][] edges) {
        DSU dsu = new DSU(n);
        for (int[] e : edges) {
            dsu.union(e[0], e[1]);
        }
        return dsu.components();
    }

    /**
     * Verifica si agregar la arista (u, v) crearía un ciclo en el grafo.
     * Útil para el algoritmo de Kruskal en construcción de MST.
     *
     * <p>
     * Complejidad: O(α(n)) por la operación connected del DSU.
     *
     * @param dsu DSU que representa el estado actual del grafo
     * @param u   primer vértice de la arista candidata
     * @param v   segundo vértice de la arista candidata
     * @return true si u y v ya están conectados (agregar la arista crea ciclo),
     *         false si estaban desconectados (la arista se agrega sin ciclo)
     * @post si retorna false, u y v quedan unidos en el DSU
     */
    public static boolean createsCycle(DSU dsu, int u, int v) {
        if (dsu.connected(u, v))
            return true;
        dsu.union(u, v);
        return false;
    }
}