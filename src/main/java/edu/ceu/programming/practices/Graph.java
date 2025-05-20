package edu.ceu.programming.practices;

import java.util.*;

public class Graph<V> {
    // Adjacency list
    private Map<V, Set<V>> adjacencyList = new HashMap<>();

    /**
     * Adds the vertex `v` to the graph.
     *
     * @param v the vertex to be added.
     * @return `true` if it wasn't already present, `false` otherwise.
     */
    public boolean addVertex(V v) {
        return true; // To be implemented
    }

    /**
     * Adds a directed edge from vertex `v1` to vertex `v2`.
     * If either vertex does not exist in the graph, it should be added as well.
     *
     * @param v1 the source vertex.
     * @param v2 the destination vertex.
     * @return `true` if the edge didn't previously exist, `false` otherwise.
     */
    public boolean addEdge(V v1, V v2) {
        return true; // To be implemented
    }

    /**
     * Retrieves the set of adjacent vertices to `v`.
     *
     * @param v the vertex whose adjacents are requested.
     * @return a set of adjacent vertices.
     * @throws Exception if the vertex does not exist.
     */
    public Set<V> getAdjacents(V v) throws Exception {
        return null; // To be implemented
    }

    /**
     * Checks whether the graph contains the specified vertex.
     *
     * @param v the vertex to check.
     * @return `true` if the vertex is part of the graph.
     */
    public boolean containsVertex(V v) {
        return true; // To be implemented
    }

    /**
     * Returns a string representation of the graph's adjacency list.
     *
     * @return a string showing the adjacency list.
     */
    @Override
    public String toString() {
        return ""; // To be implemented
    }

    /**
     * Computes, if it exists, the shortest path between `v1` and `v2`.
     * If no path exists, return `null`.
     *
     * @param v1 the source vertex.
     * @param v2 the destination vertex.
     * @return a list containing the sequence of vertices in the shortest path.
     */
    public List<V> shortestPath(V v1, V v2) {
        return null; // To be implemented
    }
}