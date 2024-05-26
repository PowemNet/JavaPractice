package com.powem.inv.algos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

//        Problem: Network Connectivity Check
//        Problem Statement:
//        Given a network represented as a graph where nodes represent servers and edges represent direct connections
//        between servers, develop a method to check if the entire network is still connected if any single server
//        fails.
//
//        Create a class that, given a network graph, can determine if removing any single node (server) still allows
//        every other node to reach each other.
//
//        The graph is undirected and may consist of multiple edges and self-loops.
//        The network should be checked for connectivity after the removal of each node individually.
//
//        Efficiently determine the resilience of the network to single-node failures, ensuring the solution scales
//        well with the number of nodes and connections.
//
//        Function Signature Suggestion:
//          public class NetworkResilienceChecker {
//            private Map<Integer, List<Integer>> adjacencyList;
//
//
//            public void addConnection(int from, int to) {
//                this.adjacencyList.putIfAbsent(from, new ArrayList<>());
//                this.adjacencyList.putIfAbsent(to, new ArrayList<>());
//                this.adjacencyList.get(from).add(to);
//                this.adjacencyList.get(to).add(from);
//            }
//
//            public boolean isNetworkResilient() {
//            }
//        }

public class NetworkResilienceChecker {
    private Map<Integer, List<Integer>> adjacencyList;

    public NetworkResilienceChecker() {
        this.adjacencyList = new HashMap<>();
    }

    public void addConnection(int from, int to) {
        adjacencyList.putIfAbsent(from, new ArrayList<>());
        adjacencyList.putIfAbsent(to, new ArrayList<>());
        adjacencyList.get(from).add(to);
        adjacencyList.get(to).add(from);
    }

    public boolean isNetworkResilient() {
        for (Integer node : adjacencyList.keySet()) {
            if (!isConnectedAfterRemoval(node)) {
                return false;
            }
        }
        return true;
    }

    private boolean isConnectedAfterRemoval(int skipNode) {
        Set<Integer> visited = new HashSet<>();
        List<Integer> nodes = new ArrayList<>(adjacencyList.keySet());
        nodes.remove((Integer) skipNode);

        if (nodes.isEmpty()) return true;

        Queue<Integer> queue = new LinkedList<>();
        queue.add(nodes.get(0));
        visited.add(nodes.get(0));

        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (int neighbor : adjacencyList.get(current)) {
                if (neighbor != skipNode && visited.add(neighbor)) {
                    queue.add(neighbor);
                }
            }
        }

        for (int node : nodes) {
            if (!visited.contains(node)) return false;
        }

        return true;
    }
}

//import com.powem.inv.algos.NetworkResilienceChecker;
//
//public class Main {
//    public static void main(String[] args) {
//        testSingleNodeNetwork();
//        testFullyConnectedNetwork();
//        testNetworkWithCriticalNode();
//    }
//
//    private static void testSingleNodeNetwork() {
//        NetworkResilienceChecker checker = new NetworkResilienceChecker();
//        checker.addConnection(1, 1);
//        //TEST
//        assert checker.isNetworkResilient();
//        //TEST END
//    }
//
//    private static void testFullyConnectedNetwork() {
//        NetworkResilienceChecker checker = new NetworkResilienceChecker();
//        checker.addConnection(1, 2);
//        checker.addConnection(2, 3);
//        checker.addConnection(1, 3);
//        //TEST
//        assert checker.isNetworkResilient();
//        //TEST END
//    }
//
//    private static void testNetworkWithCriticalNode() {
//        NetworkResilienceChecker checker = new NetworkResilienceChecker();
//        checker.addConnection(1, 2);
//        checker.addConnection(2, 3);
//        //TEST
//        assert !checker.isNetworkResilient();
//        //TEST END
//    }
//}

