package edu.ceu.programming.classes.graphs;

import java.util.*;

public class UniversityMap {

    private static class Edge {
        String target; 
        int weight; 

        Edge(String target, int weight) {
            this.target = target; 
            this.weight = weight;
        }
    }

    static class Graph {

        // I'm going to create an Adjacency LIST (not matrix), using a HashMap. 
        // I.e. the key is the node name and the value is a list of edges

        private Map<String, List<Edge>> adjList = new HashMap<String, List<Edge>>();

        public void addNode(String name) {
            if (!adjList.containsKey(name)) {
                adjList.put(name, new ArrayList<Edge>());
            }
        }

        public void addEdge(String from, String target, int weight) {
            if (adjList.containsKey(from)) {
                adjList.get(from).add(new Edge(target, weight));
            }
        }

        @Override
        public String toString() {
            String toReturn = "";
            for (Map.Entry<String, List<Edge>> entry : adjList.entrySet()) {
                toReturn += "Node: " + entry.getKey() + ": ";
                for (Edge e : entry.getValue()) {
                    toReturn += entry.getKey() + "-" + e.weight + "->" + e.target + "; ";
                }
                toReturn += "\n";
            }
            return toReturn;
        }

        // Before Priority Queues. 
        public void dijkstraOldSchool(String start, String target) {
            Map<String, Integer> distances = new HashMap<>();
            Map<String, String> predecessor = new HashMap<>();

            Set<String> visited = new HashSet<>();

            for (String node : adjList.keySet()) {
                distances.put(node, Integer.MAX_VALUE);
                predecessor.put(node, null);
            }
            distances.put(start, 0);

            while (visited.size() < adjList.size()) {
                int minDistance = Integer.MAX_VALUE;
                String current = null;

                for (String node : adjList.keySet()) {
                    if (!visited.contains(node) && distances.get(node) < minDistance) {
                        minDistance = distances.get(node);
                        current = node;
                    }
                }

                if (current == null) break;
                visited.add(current); 

                for (Edge edge : adjList.get(current)) {
                    if (visited.contains(edge.target)) continue;

                    int newDist = distances.get(current) + edge.weight;
                    if (newDist < distances.get(edge.target)) {
                        distances.put(edge.target, newDist);
                        predecessor.put(edge.target, current);
                    }
                }

                System.out.println("Current: " + current);
                System.out.println("Distances: " + distances.toString());
                System.out.println("Predecessors: " + predecessor.toString());



            }
        }

   }

    public static void main(String [] args) {
        Graph graph = new Graph(); 

        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");

        graph.addEdge("A", "B", 2);
        graph.addEdge("B", "E", 4);
        graph.addEdge("A", "C", 3);
        graph.addEdge("C", "D", 6);
        graph.addEdge("D", "E", 1);

        System.out.println(graph);

        graph.dijkstraOldSchool("A", "E");




    }



}