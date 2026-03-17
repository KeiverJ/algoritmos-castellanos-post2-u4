package edu.bench;

import edu.dsu.DSU;
import edu.trees.AVLTree;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.Arrays;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * Benchmark comparativo de AVLTree vs TreeMap y DSU vs aproximación naive.
 *
 * <p>
 * Mide el throughput (ops/ms) de operaciones de búsqueda en AVLTree
 * personalizado vs TreeMap de la biblioteca estándar, y de operaciones
 * connected en DSU vs aproximación con TreeMap.
 *
 * <p>
 * Configuración JMH:
 * <ul>
 * <li>Modo: Throughput (ops/ms)</li>
 * <li>Warmup: 3 iteraciones de 1 segundo</li>
 * <li>Medición: 5 iteraciones de 2 segundos</li>
 * <li>Fork: 1 proceso JVM independiente</li>
 * <li>N: 100.000 elementos</li>
 * </ul>
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 2)
@Fork(1)
public class TreeBenchmark {

    /** Número de elementos insertados en ambas estructuras. */
    static final int N = 100_000;

    /** Datos de entrada generados aleatoriamente. */
    Integer[] data;

    /** AVL Tree personalizado. */
    AVLTree<Integer> avl;

    /** TreeMap de la biblioteca estándar Java (Red-Black Tree). */
    TreeMap<Integer, Integer> treeMap;

    /** DSU con path splitting y union by size. */
    DSU dsu;

    /** Generador de números aleatorios con semilla fija para reproducibilidad. */
    Random rng = new Random(42);

    /**
     * Inicializa todas las estructuras con N elementos antes de cada trial.
     * Precarga AVLTree, TreeMap y DSU con los mismos datos para
     * garantizar comparación justa.
     */
    @Setup(Level.Trial)
    public void setup() {
        data = new Integer[N];
        for (int i = 0; i < N; i++) {
            data[i] = rng.nextInt(N * 10);
        }

        // Construir AVLTree y TreeMap con los mismos datos
        avl = new AVLTree<>();
        treeMap = new TreeMap<>();
        Arrays.stream(data).forEach(x -> {
            avl.insert(x);
            treeMap.put(x, x);
        });

        // Construir DSU y unir pares aleatorios
        dsu = new DSU(N);
        for (int i = 0; i < N / 2; i++) {
            dsu.union(rng.nextInt(N), rng.nextInt(N));
        }

        System.out.printf("%n=== Configuración del Benchmark ===%n");
        System.out.printf("N: %,d elementos%n", N);
        System.out.printf("Altura AVL:     %d (máx teórico: %.0f)%n",
                avl.height(), 1.44 * Math.log(N + 1) / Math.log(2));
        System.out.printf("Tamaño AVL:     %,d%n", avl.size());
        System.out.printf("Tamaño TreeMap: %,d%n", treeMap.size());
        System.out.printf("Componentes DSU: %,d%n%n", dsu.components());
    }

    /**
     * Benchmark de búsqueda en AVLTree personalizado — O(log n).
     *
     * @return resultado de la búsqueda
     */
    @Benchmark
    public boolean avlContains() {
        return avl.contains(data[rng.nextInt(N)]);
    }

    /**
     * Benchmark de búsqueda en TreeMap estándar (Red-Black Tree) — O(log n).
     *
     * @return resultado de la búsqueda
     */
    @Benchmark
    public boolean treeMapContains() {
        return treeMap.containsKey(data[rng.nextInt(N)]);
    }

    /**
     * Benchmark de consulta connected en DSU — O(α(n)).
     *
     * @return resultado de la consulta de conectividad
     */
    @Benchmark
    public boolean dsuConnected() {
        return dsu.connected(rng.nextInt(N), rng.nextInt(N));
    }
}