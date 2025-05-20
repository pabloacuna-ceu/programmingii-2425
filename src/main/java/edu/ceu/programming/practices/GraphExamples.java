package edu.ceu.programming.practices;

import java.util.List;

public class GraphExamples {

    public static void main(String[] args) {
        System.out.println("=== Integer Graph Example ===");
        Graph<Integer> intGraph = new Graph<>();
        intGraph.addEdge(1, 2);
        intGraph.addEdge(2, 3);
        intGraph.addEdge(3, 4);
        intGraph.addEdge(1, 5);
        intGraph.addEdge(5, 4);

        System.out.println("Adjacency List:");
        System.out.println(intGraph);

        System.out.println("Shortest path from 1 to 4:");
        List<Integer> intPath = intGraph.shortestPath(1, 4);
        System.out.println(intPath);

        System.out.println("\n=== String Graph Example ===");
        Graph<String> stringGraph = new Graph<>();
        stringGraph.addEdge("Entrance", "Hallway A");
        stringGraph.addEdge("Hallway A", "Room 101");
        stringGraph.addEdge("Entrance", "Hallway B");
        stringGraph.addEdge("Hallway B", "Room 102");
        stringGraph.addEdge("Room 101", "Library");

        System.out.println("Adjacency List:");
        System.out.println(stringGraph);

        System.out.println("Shortest path from Entrance to Library:");
        List<String> stringPath = stringGraph.shortestPath("Entrance", "Library");
        System.out.println(stringPath);

    }

}
