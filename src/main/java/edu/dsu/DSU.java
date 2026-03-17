package edu.dsu;

/**
 * Disjoint Set Union (Union-Find) con path splitting y union by size.
 *
 * <p>
 * Implementa dos optimizaciones clásicas:
 * <ul>
 * <li><b>Path splitting:</b> en cada operación find, cada nodo apunta
 * a su abuelo en lugar de la raíz. Más eficiente que path compression
 * completo en implementaciones iterativas porque evita una segunda
 * pasada sobre el camino.</li>
 * <li><b>Union by size:</b> el árbol de menor tamaño siempre se une
 * bajo el de mayor tamaño, manteniendo la altura en O(log n).</li>
 * </ul>
 *
 * <p>
 * Complejidad amortizada: O(α(n)) por operación, donde α es la
 * función inversa de Ackermann, prácticamente constante para n realista.
 */
public class DSU {

    /** Arreglo de padres: parent[i] es el padre del nodo i. */
    private final int[] parent;

    /** Tamaño del árbol enraizado en cada nodo representante. */
    private final int[] size;

    /** Número actual de componentes disjuntas. */
    private int components;

    /**
     * Construye un DSU con n elementos, cada uno en su propio conjunto.
     *
     * <p>
     * Complejidad: O(n) para inicializar los arreglos.
     *
     * @param n número de elementos (0..n-1)
     * @pre n > 0
     * @post cada elemento i es su propio representante: parent[i] = i
     */
    public DSU(int n) {
        parent = new int[n];
        size = new int[n];
        components = n;
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    /**
     * Encuentra el representante del conjunto que contiene x.
     * Aplica path splitting: cada nodo visitado apunta a su abuelo,
     * comprimiendo el camino sin necesidad de una segunda pasada.
     *
     * <p>
     * Complejidad amortizada: O(α(n)).
     *
     * @param x elemento a buscar (0 <= x < n)
     * @return representante (raíz) del conjunto de x
     */
    public int find(int x) {
        while (parent[x] != x) {
            int next = parent[x];
            parent[x] = parent[next]; // path splitting: apuntar al abuelo
            x = next;
        }
        return x;
    }

    /**
     * Une los conjuntos que contienen x e y usando union by size.
     * El árbol de menor tamaño se une bajo el de mayor tamaño.
     *
     * <p>
     * Complejidad amortizada: O(α(n)).
     *
     * @param x primer elemento (0 <= x < n)
     * @param y segundo elemento (0 <= y < n)
     * @return true si x e y estaban en conjuntos distintos, false si ya estaban
     *         unidos
     * @post si retorna true, el número de componentes decrece en 1
     */
    public boolean union(int x, int y) {
        int rx = find(x);
        int ry = find(y);
        if (rx == ry)
            return false;

        // Union by size: el árbol más pequeño se une bajo el más grande
        if (size[rx] < size[ry]) {
            int tmp = rx;
            rx = ry;
            ry = tmp;
        }
        parent[ry] = rx;
        size[rx] += size[ry];
        components--;
        return true;
    }

    /**
     * Verifica si x e y pertenecen al mismo conjunto.
     *
     * <p>
     * Complejidad amortizada: O(α(n)).
     *
     * @param x primer elemento (0 <= x < n)
     * @param y segundo elemento (0 <= y < n)
     * @return true si x e y están en el mismo conjunto
     */
    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }

    /**
     * Retorna el número actual de componentes disjuntas.
     *
     * @return número de componentes
     */
    public int components() {
        return components;
    }

    /**
     * Retorna el tamaño del conjunto que contiene x.
     *
     * @param x elemento a consultar (0 <= x < n)
     * @return tamaño del conjunto de x
     */
    public int sizeOf(int x) {
        return size[find(x)];
    }
}