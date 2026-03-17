# AVL Tree y Union-Find — Benchmark

**Curso:** Diseño de Algoritmos y Sistemas — Unidad 4  
**Actividad:** Post-Contenido 2  
**Programa:** Ingeniería de Sistemas — Universidad de Santander (UDES)  
**Año:** 2026

---

## Descripción

Se implementa un AVL Tree genérico y un Union-Find (DSU) optimizado
con path splitting y union by size en Java 17+. Se comparan mediante
benchmarks JMH contra sus equivalentes de la biblioteca estándar
(TreeMap y aproximación naive), y se elabora un análisis de trade-offs
para justificar cuándo cada estructura es la elección correcta.

---

## Requisitos

- Java 17+
- Maven 3.8+

```bash
java --version
mvn --version
```

---

## Instrucciones de build y ejecución

```bash
# 1. Clonar el repositorio
git clone https://github.com/<usuario>/algoritmos-castellanos-post2-u4.git
cd algoritmos-castellanos-post2-u4

# 2. Compilar y ejecutar tests
mvn clean test

# 3. Empaquetar
mvn clean package -q

# 4. Ejecutar benchmark JMH
java -jar target/benchmarks.jar -rf text -rff results.txt
```

---

## Resultados del Benchmark JMH

Throughput medido en ops/ms, 5 iteraciones de medición,
3 de warmup, 1 fork, N = 100.000 elementos.

| Estructura         | Throughput (ops/ms) | Error (±) |
| ------------------ | ------------------- | --------- |
| AVLTree (propio)   | 3.489               | 1.512     |
| TreeMap (estándar) | 3.968               | 181       |
| DSU connected      | 56.374              | 878       |

---

## Estructura del proyecto

```
algoritmos-castellanos-post2-u4/
├── pom.xml
├── README.md
├── results.txt
└── src/
    ├── main/java/edu/
    │   ├── trees/AVLTree.java
    │   ├── dsu/DSU.java
    │   ├── dsu/GraphComponents.java
    │   └── bench/TreeBenchmark.java
    └── test/java/edu/
        ├── AVLTreeTest.java
        └── DSUTest.java
```

---

## Análisis de resultados

Ver documento completo en `algoritmos-castellanos-post2-u4.pdf`.
