package edu.trees;

/**
 * AVL Tree genérico con auto-balanceo por rotaciones.
 *
 * <p>
 * Mantiene la propiedad AVL: para todo nodo, la diferencia de altura
 * entre subárbol izquierdo y derecho es como máximo 1. Esto garantiza
 * altura O(log n) y operaciones de búsqueda e inserción en O(log n).
 *
 * <p>
 * Maneja los cuatro casos de desbalance:
 * <ul>
 * <li>LL — rotación simple derecha</li>
 * <li>RR — rotación simple izquierda</li>
 * <li>LR — rotación doble: izquierda luego derecha</li>
 * <li>RL — rotación doble: derecha luego izquierda</li>
 * </ul>
 *
 * @param <T> tipo de elementos, debe ser Comparable
 */
public class AVLTree<T extends Comparable<T>> {

    /**
     * Nodo interno del árbol AVL.
     * Almacena la clave, la altura del subárbol y referencias a hijos.
     */
    private static class Node<T> {
        T key;
        int height;
        Node<T> left, right;

        Node(T key) {
            this.key = key;
            this.height = 1;
        }
    }

    /** Raíz del árbol. */
    private Node<T> root;

    /**
     * Retorna la altura del nodo, 0 si es nulo.
     *
     * @param n nodo a consultar
     * @return altura del nodo o 0 si es nulo
     */
    private int height(Node<T> n) {
        return n == null ? 0 : n.height;
    }

    /**
     * Calcula el factor de balance de un nodo.
     * Positivo indica subárbol izquierdo más alto (left-heavy).
     * Negativo indica subárbol derecho más alto (right-heavy).
     *
     * @param n nodo a evaluar
     * @return diferencia height(left) - height(right)
     */
    private int bf(Node<T> n) {
        return n == null ? 0 : height(n.left) - height(n.right);
    }

    /**
     * Actualiza la altura del nodo basándose en sus hijos.
     *
     * @param n nodo a actualizar (no nulo)
     */
    private void updateHeight(Node<T> n) {
        n.height = 1 + Math.max(height(n.left), height(n.right));
    }

    /**
     * Rotación simple a la derecha para caso LL.
     *
     * @param z nodo desbalanceado (raíz del subárbol)
     * @return nueva raíz del subárbol tras la rotación
     */
    private Node<T> rotateRight(Node<T> z) {
        Node<T> y = z.left;
        z.left = y.right;
        y.right = z;
        updateHeight(z);
        updateHeight(y);
        return y;
    }

    /**
     * Rotación simple a la izquierda para caso RR.
     *
     * @param z nodo desbalanceado (raíz del subárbol)
     * @return nueva raíz del subárbol tras la rotación
     */
    private Node<T> rotateLeft(Node<T> z) {
        Node<T> y = z.right;
        z.right = y.left;
        y.left = z;
        updateHeight(z);
        updateHeight(y);
        return y;
    }

    /**
     * Balancea el nodo aplicando las rotaciones necesarias.
     * Detecta automáticamente el caso (LL, RR, LR, RL) por el
     * factor de balance del nodo y su hijo crítico.
     *
     * @param n nodo a balancear
     * @return raíz del subárbol balanceado
     */
    private Node<T> balance(Node<T> n) {
        updateHeight(n);
        int b = bf(n);

        if (b > 1) {
            // Left-heavy
            if (bf(n.left) < 0)
                n.left = rotateLeft(n.left); // caso LR
            return rotateRight(n);
        }
        if (b < -1) {
            // Right-heavy
            if (bf(n.right) > 0)
                n.right = rotateRight(n.right); // caso RL
            return rotateLeft(n);
        }
        return n;
    }

    /**
     * Inserta una clave en el subárbol con raíz n y retorna la nueva raíz.
     * Duplicados son ignorados silenciosamente.
     *
     * @param n   raíz del subárbol actual
     * @param key clave a insertar
     * @return nueva raíz del subárbol tras inserción y balanceo
     */
    private Node<T> insert(Node<T> n, T key) {
        if (n == null)
            return new Node<>(key);
        int cmp = key.compareTo(n.key);
        if (cmp < 0)
            n.left = insert(n.left, key);
        else if (cmp > 0)
            n.right = insert(n.right, key);
        // cmp == 0: duplicado, no insertar
        return balance(n);
    }

    /**
     * Inserta una clave en el árbol.
     * Complejidad: O(log n) por la propiedad AVL.
     *
     * @param key clave a insertar (no nula)
     * @post la clave está en el árbol y la propiedad AVL se mantiene
     */
    public void insert(T key) {
        root = insert(root, key);
    }

    /**
     * Verifica si una clave está en el árbol.
     * Complejidad: O(log n) — búsqueda BST en árbol balanceado.
     *
     * @param key clave a buscar (no nula)
     * @return true si la clave está en el árbol, false en caso contrario
     */
    public boolean contains(T key) {
        Node<T> cur = root;
        while (cur != null) {
            int cmp = key.compareTo(cur.key);
            if (cmp == 0)
                return true;
            else if (cmp < 0)
                cur = cur.left;
            else
                cur = cur.right;
        }
        return false;
    }

    /**
     * Retorna la altura actual del árbol.
     *
     * @return altura del árbol, 0 si está vacío
     */
    public int height() {
        return height(root);
    }

    /**
     * Retorna el número de elementos en el árbol.
     * Complejidad: O(n) — recorrido completo.
     *
     * @return número de nodos en el árbol
     */
    public int size() {
        return size(root);
    }

    private int size(Node<T> n) {
        return n == null ? 0 : 1 + size(n.left) + size(n.right);
    }
}